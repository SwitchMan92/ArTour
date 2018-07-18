package fr.cnam.nsy209.artour2.engine.rendering;

import android.opengl.GLES20;
import android.opengl.GLU;
import android.util.Log;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.util.Iterator;

import fr.cnam.nsy209.artour2.engine.rendering.texture.ITexture;
import fr.cnam.nsy209.artour2.engine.rendering.texture.Texture;
import fr.cnam.nsy209.artour2.engine.rendering.texture.TextureParameterI;

/**
 * Created by ng6fd11 on 21/05/2018.
 */


public class TexturedMesh extends Mesh implements ITextured {

    protected FloatBuffer   m_TextureCoordinatesBuffer;
    private int[]           m_Textures = new int[1];
    private ITexture        m_Texture;

    public TexturedMesh() {
        super();
    }

    public int getTextureId() {
        return this.m_Textures[0];
    }

    public void setTexture(ITexture p_Texture) { this.m_Texture = p_Texture; }
    public ITexture getTexture() { return this.m_Texture; }

    public void setTextureCoordinates(float[] p_TextureCoordinates) {
        ByteBuffer bb = ByteBuffer.allocateDirect(p_TextureCoordinates.length * 4);
        bb.order(ByteOrder.nativeOrder());
        m_TextureCoordinatesBuffer = bb.asFloatBuffer();
        m_TextureCoordinatesBuffer.put(p_TextureCoordinates);
        m_TextureCoordinatesBuffer.position(0);
    }

    protected int getVertexArraySize() {
        return super.getVertexArraySize() + this.m_TextureCoordinatesBuffer.limit() * 4;
    }

    public void loadBuffers() {
        super.loadBuffers();

        GLES20.glBufferSubData(
                GLES20.GL_ARRAY_BUFFER,
                this.m_VertexBuffer.capacity() * 4,
                4 * this.m_TextureCoordinatesBuffer.capacity(),
                this.m_TextureCoordinatesBuffer
        );

        GLES20.glGenTextures(m_Textures.length, m_Textures, 0);

        if ((m_Textures.length > 0) && (this.m_Texture != null)) {

            GLES20.glBindTexture(this.m_Texture.getTextureBind(), m_Textures[0]);

            Iterator<TextureParameterI> l_TextureParameterIterator = this.m_Texture.getTextureParameters().iterator();

            while (l_TextureParameterIterator.hasNext()) {
                TextureParameterI l_TextureParameter = l_TextureParameterIterator.next();
                GLES20.glTexParameteri(l_TextureParameter.getTarget(), l_TextureParameter.getPname(), l_TextureParameter.getParam());
            }

            /*
            GLES20.glBindTexture(GLES11Ext.GL_TEXTURE_EXTERNAL_OES, m_Textures[0]);
            GLES20.glTexParameteri(GLES11Ext.GL_TEXTURE_EXTERNAL_OES, GLES20.GL_TEXTURE_MIN_FILTER, GLES20.GL_LINEAR_MIPMAP_LINEAR);
            GLES20.glTexParameteri(GLES11Ext.GL_TEXTURE_EXTERNAL_OES, GLES20.GL_TEXTURE_MAG_FILTER, GLES20.GL_LINEAR);
            */

            /*
            GLES20.glTexParameteri(GLES11Ext.GL_TEXTURE_EXTERNAL_OES, GLES20.GL_TEXTURE_WRAP_S, GLES20.GL_CLAMP_TO_EDGE);
            GLES20.glTexParameteri(GLES11Ext.GL_TEXTURE_EXTERNAL_OES, GLES20.GL_TEXTURE_WRAP_T, GLES20.GL_CLAMP_TO_EDGE);
            GLES20.glTexParameteri(GLES11Ext.GL_TEXTURE_EXTERNAL_OES, GLES20.GL_TEXTURE_MIN_FILTER, GLES20.GL_NEAREST);
            GLES20.glTexParameteri(GLES11Ext.GL_TEXTURE_EXTERNAL_OES, GLES20.GL_TEXTURE_MAG_FILTER, GLES20.GL_NEAREST);
            */
            /*
            GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, m_Textures[0]);
            GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MIN_FILTER, GLES20.GL_LINEAR_MIPMAP_LINEAR);
            GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MAG_FILTER, GLES20.GL_LINEAR);
            GLUtils.texImage2D(GLES20.GL_TEXTURE_2D, 0, m_TextureBitmap, 0);
            GLES20.glGenerateMipmap(GLES20.GL_TEXTURE_2D);
            GLES20.glBindTexture(GLES11Ext.GL_TEXTURE_EXTERNAL_OES, 0);
            */
            //this.m_TextureBitmap.recycle();
        }
        else {
            int err = GLES20.glGetError();
            if (err != GLES20.GL_NO_ERROR) {
                Log.e("error", GLU.gluErrorString(err));
            }
        }
    }

    public void bindBuffers() {
        super.bindBuffers();

        GLES20.glActiveTexture(GLES20.GL_TEXTURE0);
        GLES20.glBindTexture(this.m_Texture.getTextureBind(), this.m_Textures[0]); //GLES11Ext.GL_TEXTURE_EXTERNAL_OES
        GLES20.glUniform1i(this.getProgram().getUniforms().get("u_Texture"), 0);

        if (this.getProgram().getAttributes().containsKey("a_TexCoordinate")) {

            GLES20.glVertexAttribPointer(this.getProgram().getAttributes().get("a_TexCoordinate"), 2,
                    GLES20.GL_FLOAT, false, 0, super.getVertexArraySize());
            GLES20.glEnableVertexAttribArray(this.getProgram().getAttributes().get("a_TexCoordinate"));

            int err = GLES20.glGetError();
            if (err != GLES20.GL_NO_ERROR) {
                Log.e("error", GLU.gluErrorString(err));
            }
        }
    }

    public void unbindBuffers() {
        GLES20.glBindTexture(this.m_Texture.getTextureBind(), 0);

        if (this.getProgram().getAttributes().containsKey("a_TexCoordinate"))
            GLES20.glDisableVertexAttribArray(this.getProgram().getAttributes().get("a_TexCoordinate"));

        super.unbindBuffers();
    }

}
