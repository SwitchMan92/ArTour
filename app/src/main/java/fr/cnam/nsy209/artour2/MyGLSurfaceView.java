package fr.cnam.nsy209.artour2;

import android.content.Context;
import android.opengl.GLSurfaceView;

import com.google.ar.core.Session;

/**
 * Created by ng6fd11 on 17/05/2018.
 */

public class MyGLSurfaceView extends GLSurfaceView {

    private final MyGLRenderer mRenderer;

    public MyGLSurfaceView(Context context){
        super(context);

        // Create an OpenGL ES 2.0 context
        setEGLContextClientVersion(2);
        setPreserveEGLContextOnPause(true);

        mRenderer = new MyGLRenderer(getContext());

        // Set the Renderer for drawing on the GLSurfaceView
        setRenderer(mRenderer);
        setRenderMode(GLSurfaceView.RENDERMODE_CONTINUOUSLY);
    }

    public void setSession(Session p_Session) {
        this.mRenderer.setSession(p_Session);
    }

}
