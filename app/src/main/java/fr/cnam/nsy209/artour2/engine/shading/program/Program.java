package fr.cnam.nsy209.artour2.engine.shading.program;

import android.opengl.GLES20;
import android.opengl.GLES30;
import android.util.Log;

import java.nio.IntBuffer;
import java.util.HashMap;

import fr.cnam.nsy209.artour2.engine.shading.ACShader;

/**
 * Created by NG6FD11 on 18/05/2018.
 */

public class Program implements IProgram {

    private ACShader                    m_VertexShader;
    private ACShader                    m_FragmentShader;
    private int                         m_Program;
    private HashMap<String, Integer>    m_Attributes;
    private HashMap<String, Integer>    m_Uniforms;

    public void setVertexShader(ACShader p_VertexShader){
        this.m_VertexShader = p_VertexShader;
    };

    public void setFragmentShader(ACShader p_FragmentShader){
        this.m_FragmentShader = p_FragmentShader;
    };

    public int getProgram() {
        return this.m_Program;
    }

    public HashMap<String, Integer> getAttributes(){
        return this.m_Attributes;
    };

    public HashMap<String, Integer> getUniforms(){
        return this.m_Uniforms;
    };

    public void setActive(boolean p_Active){
        if (p_Active)
            GLES20.glUseProgram(this.m_Program);
        else
            GLES20.glUseProgram(0);
    };

    public void loadInEngine(){
        this.m_Program = GLES20.glCreateProgram();
        GLES20.glAttachShader(this.m_Program, this.m_VertexShader.getShader());
        GLES20.glAttachShader(this.m_Program, this.m_FragmentShader.getShader());
        GLES20.glLinkProgram(this.m_Program);

        GLES20.glDeleteShader(this.m_VertexShader.getShader());
        GLES20.glDeleteShader(this.m_FragmentShader.getShader());

        final int[] linkStatus = new int[1];
        GLES20.glGetProgramiv(this.m_Program, GLES20.GL_LINK_STATUS, linkStatus, 0);

        // If the link failed, delete the program.
        if (linkStatus[0] == 0) {
            GLES20.glDeleteProgram(this.m_Program);
            String l_Error = GLES20.glGetProgramInfoLog(this.m_Program);
            Log.d("program", l_Error);
            throw new RuntimeException("error compiling program : " + l_Error);
        }
        else {
            Log.d("program", GLES20.glGetProgramInfoLog(this.m_Program));

            this.m_Attributes   = new HashMap<String, Integer>();
            this.m_Uniforms     = new HashMap<String, Integer>();

            IntBuffer l_Attribs = IntBuffer.allocate(1);

            int[] l_AttribMaxSize = new int[]{16};
            int[] type = new int[1];

            GLES20.glGetProgramiv(this.m_Program, GLES20.GL_ACTIVE_ATTRIBUTES, l_Attribs);

            for (int i=0; i < l_Attribs.get(0); i++) {
                String l_AttribName = GLES20.glGetActiveAttrib(this.m_Program, i, l_AttribMaxSize, 0, type, 0);
                this.m_Attributes.put(l_AttribName, i);
            }

            IntBuffer l_UniformsCount = IntBuffer.allocate(1);

            GLES20.glGetProgramiv(this.m_Program, GLES20.GL_ACTIVE_UNIFORMS, l_UniformsCount);

            IntBuffer l_UniformSize = IntBuffer.allocate(1);
            IntBuffer l_UniformType = IntBuffer.allocate(1);

            for (int i = 0; i < l_UniformsCount.get(0); i++) {
                String l_UniformName = GLES20.glGetActiveUniform(this.m_Program, i, l_UniformSize, l_UniformType);
                this.m_Uniforms.put(l_UniformName, i);
            }
        }
    };

    public void unload(){
        GLES20.glDeleteProgram(this.m_Program);
    };

}
