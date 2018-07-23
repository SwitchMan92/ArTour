package fr.cnam.nsy209.artour2;

import android.content.Context;
import android.opengl.GLES11Ext;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.opengl.Matrix;
import android.util.Log;

import com.google.ar.core.Camera;
import com.google.ar.core.Frame;
import com.google.ar.core.Session;
import com.google.ar.core.exceptions.CameraNotAvailableException;

import java.io.IOException;
import java.io.InputStream;

import javax.microedition.khronos.opengles.GL10;

import fr.cnam.nsy209.artour2.engine.rendering.texture.Texture;
import fr.cnam.nsy209.artour2.engine.scene.ArTourScene;
import fr.cnam.nsy209.artour2.engine.shading.fragment.FragmentShader;
import fr.cnam.nsy209.artour2.engine.shading.program.Program;
import fr.cnam.nsy209.artour2.engine.shading.vertex.VertexShader;
import fr.common.helpers.DisplayRotationHelper;

/**
 * Created by ng6fd11 on 17/05/2018.
 */

public class MyGLRenderer implements GLSurfaceView.Renderer {

    private Program                 m_Program;
    private Session                 m_Session;
    private Context                 m_Context;
    private ArTourScene             m_Scene;
    private DisplayRotationHelper   m_DisplayRotationHelper;


    public MyGLRenderer(Context p_Context) {
        this.m_Context = p_Context;
        this.m_DisplayRotationHelper = new DisplayRotationHelper(/*context=*/ this.m_Context);
    }

    public void onResume() {
        this.m_DisplayRotationHelper.onResume();
    }

    public void onPause() {
        this.m_DisplayRotationHelper.onPause();
    }

    public void setSession(Session p_Session) {
        this.m_Session = p_Session;
        this.m_Scene = new ArTourScene(this.m_Session);
    }

    public void onSurfaceCreated(GL10 gl, javax.microedition.khronos.egl.EGLConfig config) {
        GLES20.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);

        if(this.m_Session != null) {
            this.m_Scene.loadInEngine();
        }
    }

    public void onDrawFrame(GL10 unused) {

        try {
            this.m_DisplayRotationHelper.updateSessionIfNeeded(this.m_Session);

            Frame l_Frame = this.m_Session.update();
            Camera l_Camera = l_Frame.getCamera();

            float[] l_ProjectionMatrix = new float[16];
            l_Camera.getProjectionMatrix(l_ProjectionMatrix, 0, 0.1f, 100.0f);

            float[] l_ViewMatrix = new float[16];
            l_Camera.getViewMatrix(l_ViewMatrix, 0);

            float[] l_ViewProjMatrix = new float[16];
            Matrix.multiplyMM(l_ViewProjMatrix, 0, l_ProjectionMatrix, 0, l_ViewMatrix, 0);

            this.m_Scene.render(l_ViewMatrix, l_ProjectionMatrix, l_ViewProjMatrix);
        }
        catch(CameraNotAvailableException e) {
            Log.e("camera error", e.toString());
        }
    }

    public void onSurfaceChanged(GL10 unused, int width, int height) {
        this.m_DisplayRotationHelper.onSurfaceChanged(width, height);
        GLES20.glViewport(0, 0, width, height);
    }

}
