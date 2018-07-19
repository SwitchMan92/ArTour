package fr.cnam.nsy209.artour2.engine.rendering;

import android.opengl.GLES10;
import android.opengl.GLES11Ext;
import android.opengl.GLES20;
import android.opengl.GLU;
import android.util.Log;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;

import fr.cnam.nsy209.artour2.engine.shading.program.IProgram;


/**
 * Created by ng6fd11 on 17/05/2018.
 */

public class Mesh extends ACRenderable implements IMesh {

    protected FloatBuffer       m_VertexBuffer;
    protected ShortBuffer       m_IndexBuffer;

    protected   int[]           m_VertexBufferObject;
    protected   int[]           m_IndexBufferObject;
    protected   float[]         m_Color;
    protected   boolean         m_DepthRendering;
    protected   int             m_DrawMode;


    public void setWireframe(boolean p_Wireframe){
        if (p_Wireframe)
            this.m_DrawMode = GLES20.GL_LINES;
        else
            this.m_DrawMode = GLES20.GL_TRIANGLES;
    }

    public boolean getWireframe() { return this.m_DrawMode == GLES20.GL_LINES; }

    public void setDepthRendering(boolean p_DepthRendering) {
        this.m_DepthRendering = p_DepthRendering;
    }

    public boolean getDepthRendering() { return this.m_DepthRendering; }

    public void setColor(float[] p_Color) {
        this.m_Color = p_Color;
    }

    public Mesh() {
        this.m_VertexBufferObject = new int[1];
        this.m_IndexBufferObject = new int[1];
        this.setDepthRendering(true);
        this.setWireframe(false);
    }

    public void setVertices(float[] p_Vertices) {
        ByteBuffer bb = ByteBuffer.allocateDirect(p_Vertices.length * 4);
        bb.order(ByteOrder.nativeOrder());
        m_VertexBuffer = bb.asFloatBuffer();
        m_VertexBuffer.put(p_Vertices);
        m_VertexBuffer.position(0);
    }

    public void setIndices(short[] p_Indices) {
        ByteBuffer dlb = ByteBuffer.allocateDirect(p_Indices.length * 2);
        dlb.order(ByteOrder.nativeOrder());
        m_IndexBuffer = dlb.asShortBuffer();
        m_IndexBuffer.put(p_Indices);
        m_IndexBuffer.position(0);
    }

    public void bindBuffers() {
        GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, this.m_VertexBufferObject[0]);

        GLES20.glVertexAttribPointer(this.getProgram().getAttributes().get("a_Position"), 3,
                GLES20.GL_FLOAT,false, 0, 0);

        GLES20.glEnableVertexAttribArray(this.getProgram().getAttributes().get("a_Position"));

        if (this.getProgram().getUniforms().containsKey("a_Color"))
            GLES20.glUniform4fv(this.getProgram().getUniforms().get("a_Color"), 1, this.m_Color, 0);
    }

    public void render() {
        this.getProgram().setActive(true);

        this.bindBuffers();

        GLES20.glBindBuffer(GLES20.GL_ELEMENT_ARRAY_BUFFER, this.m_IndexBufferObject[0]);
        GLES20.glDrawElements(this.m_DrawMode, this.m_IndexBuffer.capacity(), GLES20.GL_UNSIGNED_SHORT, 0);

        this.unbindBuffers();

        int err = GLES20.glGetError();
        if (err != GLES20.GL_NO_ERROR) {
            Log.e("error", GLU.gluErrorString(err));
        }
    }

    public void unbindBuffers() {

        if (this.getProgram().getUniforms().containsKey("a_Color"))
            GLES20.glDisableVertexAttribArray(this.getProgram().getAttributes().get("a_Position"));

        GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, 0);
        GLES20.glBindBuffer(GLES20.GL_ELEMENT_ARRAY_BUFFER, 0);
    }

    protected int getVertexArraySize() {
        return this.m_VertexBuffer.capacity() * 4;
    }

    public void loadInEngine() {
        GLES20.glGenBuffers(1, this.m_VertexBufferObject, 0);
        GLES20.glGenBuffers(1, this.m_IndexBufferObject, 0);

        if (this.m_VertexBufferObject[0] > 0 && this.m_IndexBufferObject[0] > 0) {

            GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, this.m_VertexBufferObject[0]);

            GLES20.glBufferData(GLES20.GL_ARRAY_BUFFER, this.getVertexArraySize()
                    , null, GLES20.GL_STATIC_DRAW);

            this.loadBuffers();

            GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, 0);

            GLES20.glBindBuffer(GLES20.GL_ELEMENT_ARRAY_BUFFER, this.m_IndexBufferObject[0]);
            GLES20.glBufferData(GLES20.GL_ELEMENT_ARRAY_BUFFER, this.m_IndexBuffer.capacity()
                    * 2, m_IndexBuffer, GLES20.GL_STATIC_DRAW);

            GLES20.glBindBuffer(GLES20.GL_ELEMENT_ARRAY_BUFFER, 0);

            int err = GLES20.glGetError();
            if (err != GLES20.GL_NO_ERROR) {
                Log.e("error", GLU.gluErrorString(err));
            }
        }
        else {
            int err = GLES20.glGetError();
            if (err != GLES20.GL_NO_ERROR) {
                Log.e("error", GLU.gluErrorString(err));
            }
        }
    }

    public void loadBuffers(){
        GLES20.glBufferSubData(
                GLES20.GL_ARRAY_BUFFER,
                0,
                4 * this.m_VertexBuffer.capacity(),
                this.m_VertexBuffer
        );
    }

    public void unload(){
        GLES20.glBindBuffer(GLES20.GL_ELEMENT_ARRAY_BUFFER, 0);
        GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, 0);

        GLES20.glDeleteBuffers(1, this.m_IndexBufferObject, 0);
        GLES20.glDeleteBuffers(1, this.m_VertexBufferObject, 0);

        int err = GLES20.glGetError();
        if (err != GLES20.GL_NO_ERROR) {
            Log.e("error", GLU.gluErrorString(err));
        }

        this.m_IndexBuffer.clear();
        this.m_VertexBuffer.clear();

        this.m_IndexBuffer = null;
        this.m_VertexBuffer = null;
    }
}
