package fr.cnam.nsy209.artour2;

import android.content.Context;
import android.opengl.GLES11Ext;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.util.Log;

import com.google.ar.core.Session;

import java.io.IOException;
import java.io.InputStream;

import javax.microedition.khronos.opengles.GL10;

import fr.cnam.nsy209.artour2.engine.rendering.texture.Texture;
import fr.cnam.nsy209.artour2.engine.shading.fragment.FragmentShader;
import fr.cnam.nsy209.artour2.engine.shading.program.Program;
import fr.cnam.nsy209.artour2.engine.shading.vertex.VertexShader;

/**
 * Created by ng6fd11 on 17/05/2018.
 */

public class MyGLRenderer implements GLSurfaceView.Renderer {

    private Program m_Program;
    private Session m_Session;
    private TexturedMeshSquare m_TexturedSquare;
    private Context m_Context;


    public MyGLRenderer(Context p_Context) {
        this.m_Context = p_Context;
    }

    public void setSession(Session p_Session) {
        this.m_Session = p_Session;
    }

    public void onSurfaceCreated(GL10 gl, javax.microedition.khronos.egl.EGLConfig config) {
        GLES20.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);

        VertexShader l_VertexShader = new VertexShader();
        FragmentShader l_FragmentShader = new FragmentShader();

        this.m_Program = new Program();

        try {
            InputStream l_VertexShaderStream = this.m_Context.getAssets().open("shaders/vertex/texture.vs");
            l_VertexShader.loadFromFile(l_VertexShaderStream);

            InputStream l_FragmentShaderStream = this.m_Context.getAssets().open("shaders/fragment/texture.ps");
            l_FragmentShader.loadFromFile(l_FragmentShaderStream);

            l_VertexShader.loadInEngine();
            l_FragmentShader.loadInEngine();

            this.m_Program.setVertexShader(l_VertexShader);
            this.m_Program.setFragmentShader(l_FragmentShader);

            this.m_Program.loadInEngine();

            Texture l_Texture = new Texture();
            l_Texture.setTextureBind(GLES11Ext.GL_TEXTURE_EXTERNAL_OES);
            l_Texture.addTextureParameterI(GLES11Ext.GL_TEXTURE_EXTERNAL_OES, GLES20.GL_TEXTURE_MIN_FILTER, GLES20.GL_LINEAR_MIPMAP_LINEAR);
            l_Texture.addTextureParameterI(GLES11Ext.GL_TEXTURE_EXTERNAL_OES, GLES20.GL_TEXTURE_MAG_FILTER, GLES20.GL_LINEAR);

            m_TexturedSquare = new TexturedMeshSquare();
            m_TexturedSquare.setTexture(l_Texture);
            m_TexturedSquare.setProgram(this.m_Program);

            if (this.m_Session != null) {
                this.m_Session.setCameraTextureName(this.m_TexturedSquare.getTextureId());
            }

            m_TexturedSquare.loadInEngine();

        }
        catch(IOException e) {
            Log.e("Error", e.toString());
            System.exit(-1);
        }


    }

    public void onDrawFrame(GL10 unused) {

        try {
            this.m_Session.update();
            m_TexturedSquare.render();
        }
        catch(Exception e) {
            Log.e("error rendering object", e.getMessage());
        }
    }

    public void onSurfaceChanged(GL10 unused, int width, int height) {
        GLES20.glViewport(0, 0, width, height);
    }

}
