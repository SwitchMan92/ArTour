package fr.cnam.nsy209.artour2;

import android.content.Context;
import android.opengl.GLSurfaceView;

import com.google.ar.core.Session;

/**
 * Created by ng6fd11 on 17/05/2018.
 */

public class MyGLSurfaceView extends GLSurfaceView {

    //private final MyGLRenderer mRenderer;

    public MyGLSurfaceView(Context context) {
        super(context);

        setEGLContextClientVersion(2);
        setPreserveEGLContextOnPause(true);
    }
}
