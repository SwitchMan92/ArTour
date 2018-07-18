package fr.cnam.nsy209.artour2.engine.shading.fragment;

import android.opengl.GLES20;
import android.opengl.GLES30;

import fr.cnam.nsy209.artour2.engine.shading.ACShader;

/**
 * Created by NG6FD11 on 18/05/2018.
 */

public class FragmentShader extends ACShader {

    public void loadInEngine() {
        this.m_Shader = ACShader.loadShader(GLES20.GL_FRAGMENT_SHADER, this.m_ShaderCode);
        this.m_ShaderCode = null;
    }

}



