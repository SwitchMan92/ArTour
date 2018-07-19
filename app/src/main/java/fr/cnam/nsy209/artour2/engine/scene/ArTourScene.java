package fr.cnam.nsy209.artour2.engine.scene;

import android.opengl.GLES11Ext;
import android.opengl.GLES20;
import android.util.Log;

import com.google.ar.core.Session;

import fr.cnam.nsy209.artour2.MyGLRenderer;
import fr.cnam.nsy209.artour2.TexturedMeshSquare;
import fr.cnam.nsy209.artour2.engine.rendering.IMesh;
import fr.cnam.nsy209.artour2.engine.rendering.texture.Texture;
import fr.cnam.nsy209.artour2.engine.shading.program.IProgram;
import fr.cnam.nsy209.artour2.engine.shading.program.Program;
import fr.cnam.nsy209.artour2.engine.shading.program.loader.ProgramLoader;

public class ArTourScene extends Scene {

    public ArTourScene(Session p_Session) {
        super();

        try {
            IProgram l_Program = ProgramLoader.getProgram("texture");

            Texture l_Texture = new Texture();
            l_Texture.setTextureBind(GLES11Ext.GL_TEXTURE_EXTERNAL_OES);
            l_Texture.addTextureParameterI(GLES11Ext.GL_TEXTURE_EXTERNAL_OES, GLES20.GL_TEXTURE_MIN_FILTER, GLES20.GL_LINEAR_MIPMAP_LINEAR);
            l_Texture.addTextureParameterI(GLES11Ext.GL_TEXTURE_EXTERNAL_OES, GLES20.GL_TEXTURE_MAG_FILTER, GLES20.GL_LINEAR);

            TexturedMeshSquare l_TexturedSquare = new TexturedMeshSquare();
            l_TexturedSquare.setTexture(l_Texture);
            l_TexturedSquare.setProgram(l_Program);
            l_TexturedSquare.setDepthRendering(false);
            p_Session.setCameraTextureName(l_TexturedSquare.getTextureId());

            this.addMesh((IMesh)l_TexturedSquare);
        }
        catch(java.io.IOException e){
            Log.e("Load scene", e.toString());
        }
    }

}