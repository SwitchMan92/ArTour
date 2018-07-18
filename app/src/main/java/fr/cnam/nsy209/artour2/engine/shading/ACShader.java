package fr.cnam.nsy209.artour2.engine.shading;

import android.opengl.GLES20;
import android.opengl.GLES30;
import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by NG6FD11 on 18/05/2018.
 */

public abstract class ACShader implements IShader {

    protected int       m_Shader;
    protected String    m_ShaderCode;

    protected static int loadShader(int type, String shaderCode){

        // create a vertex shader type (GLES20.GL_VERTEX_SHADER)
        // or a fragment shader type (GLES20.GL_FRAGMENT_SHADER)
        int shader = GLES20.glCreateShader(type);

        // add the source code to the shader and compile it
        GLES20.glShaderSource(shader, shaderCode);
        GLES20.glCompileShader(shader);

        final int[] compileStatus = new int[1];
        GLES20.glGetShaderiv(shader, GLES20.GL_COMPILE_STATUS, compileStatus, 0);

        if (compileStatus[0] == 0) {
            String l_Error = GLES20.glGetShaderInfoLog(shader);
            Log.e("shader", l_Error);
            throw new RuntimeException("error compiling shader : \n" + l_Error);
        }
        else
            Log.d("shader", GLES20.glGetShaderInfoLog(shader));

        return shader;
    }

    public void loadFromString(String p_ShaderCode){
        this.m_ShaderCode = p_ShaderCode;
    };

    public void loadFromFile(InputStream p_ShaderFile) throws java.io.IOException {
        InputStreamReader inputreader = new InputStreamReader(p_ShaderFile);
        BufferedReader buffreader = new BufferedReader(inputreader);
        String line;
        StringBuilder text = new StringBuilder();

        while (( line = buffreader.readLine()) != null) {
            text.append(line);
            text.append('\n');
        }

        this.loadFromString(text.toString());
    };

    public int getShader(){
        return this.m_Shader;
    };

    public void unload(){
        GLES20.glDeleteShader(this.m_Shader);
    };

}
