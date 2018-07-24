package fr.cnam.nsy209.artour2.engine.scene;

import android.opengl.GLES11Ext;
import android.opengl.GLES20;

import com.google.ar.core.Session;

import fr.cnam.nsy209.artour2.TexturedPlaneMesh;
import fr.cnam.nsy209.artour2.engine.rendering.mesh.IMesh;
import fr.cnam.nsy209.artour2.engine.rendering.texture.TextRenderingTexture;
import fr.cnam.nsy209.artour2.engine.rendering.texture.Texture;
import fr.cnam.nsy209.artour2.engine.shading.program.loader.ProgramLoader;

public class ArTourScene extends Scene {

    public ArTourScene(Session p_Session) {
        super();

        Texture l_Texture = new Texture();
        l_Texture.setTextureBind(GLES11Ext.GL_TEXTURE_EXTERNAL_OES);
        l_Texture.addTextureParameterI(GLES11Ext.GL_TEXTURE_EXTERNAL_OES, GLES20.GL_TEXTURE_MIN_FILTER, GLES20.GL_NEAREST);
        l_Texture.addTextureParameterI(GLES11Ext.GL_TEXTURE_EXTERNAL_OES, GLES20.GL_TEXTURE_MAG_FILTER, GLES20.GL_NEAREST);

        TexturedPlaneMesh l_TexturedSquare = new TexturedPlaneMesh();

        try {
            l_TexturedSquare.setProgram(ProgramLoader.getProgram("background"));
        }
        catch(Exception e) {

        }

        l_TexturedSquare.setTexture(l_Texture);
        l_TexturedSquare.setDepthRendering(false);
        l_TexturedSquare.setWireframe(false);
        p_Session.setCameraTextureName(l_TexturedSquare.getTextureId());
        this.addMesh((IMesh)l_TexturedSquare);


        TextRenderingTexture l_Texture2 = new TextRenderingTexture();
        l_Texture2.setTextureBind(GLES20.GL_TEXTURE_2D);
        l_Texture2.addTextureParameterI(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MIN_FILTER, GLES20.GL_NEAREST);
        l_Texture2.addTextureParameterI(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MAG_FILTER, GLES20.GL_NEAREST);
        l_Texture2.setText("this is a test");

        TexturedPlaneMesh l_PlaneMesh = new TexturedPlaneMesh();
        l_PlaneMesh.setTexture(l_Texture2);
        l_PlaneMesh.setPosition(0f, 0f, 0f);
        this.addMesh(l_PlaneMesh);
    }

}
