package fr.cnam.nsy209.artour2.engine.scene;

import android.opengl.GLES11Ext;
import android.opengl.GLES20;
import android.util.Log;

import com.google.ar.core.Session;

import java.util.List;

import fr.cnam.nsy209.artour2.TexturedPlaneMesh;
import fr.cnam.nsy209.artour2.engine.rendering.mesh.IMesh;
import fr.cnam.nsy209.artour2.engine.rendering.texture.TextRenderingTexture;
import fr.cnam.nsy209.artour2.engine.rendering.texture.Texture;
import fr.cnam.nsy209.artour2.engine.shading.program.loader.ProgramLoader;
import fr.cnam.nsy209.artour2.location.ListPlacesTask;
import fr.cnam.nsy209.artour2.location.ListPlacesTaskListener;
import fr.cnam.nsy209.artour2.location.data.Place;

public class ArTourScene extends Scene implements ListPlacesTaskListener{

    private ListPlacesTask m_PlacesTask;

    public void onPostExecute(List<Place> p_Places) {
        for (Place l_Place: p_Places) {
            Log.d("IPlacesService", l_Place.name);
        }
    }

    private void initBackground(Session p_Session) {
        Texture l_Texture = new Texture();
        l_Texture.setTextureBind(GLES11Ext.GL_TEXTURE_EXTERNAL_OES);
        l_Texture.addTextureParameterI(GLES11Ext.GL_TEXTURE_EXTERNAL_OES, GLES20.GL_TEXTURE_MIN_FILTER, GLES20.GL_NEAREST);
        l_Texture.addTextureParameterI(GLES11Ext.GL_TEXTURE_EXTERNAL_OES, GLES20.GL_TEXTURE_MAG_FILTER, GLES20.GL_NEAREST);

        try {
            TexturedPlaneMesh l_TexturedSquare = new TexturedPlaneMesh();
            l_TexturedSquare.setProgram(ProgramLoader.getProgram("background"));
            l_TexturedSquare.setTexture(l_Texture);
            l_TexturedSquare.setDepthRendering(false);
            l_TexturedSquare.setWireframe(false);
            p_Session.setCameraTextureName(l_TexturedSquare.getTextureId());
            this.addMesh((IMesh)l_TexturedSquare);
        }
        catch(Exception e) {
            Log.e("TexturedSquareMesh error", e.toString());
        }
    }

    public ArTourScene(Session p_Session) {
        super();

        this.m_PlacesTask = new ListPlacesTask(this);
        this.m_PlacesTask.execute("48.858093", "2.294694");

        this.initBackground(p_Session);

        TextRenderingTexture l_Texture2 = new TextRenderingTexture();
        l_Texture2.setTextureBind(GLES20.GL_TEXTURE_2D);
        l_Texture2.addTextureParameterI(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MIN_FILTER, GLES20.GL_NEAREST);
        l_Texture2.addTextureParameterI(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MAG_FILTER, GLES20.GL_NEAREST);
        l_Texture2.setText("this is a test");

        try {
            TexturedPlaneMesh l_PlaneMesh = new TexturedPlaneMesh();
            l_PlaneMesh.setTexture(l_Texture2);
            l_PlaneMesh.setPosition(0f, 0f, 0f);
            this.addMesh(l_PlaneMesh);
        }
        catch(Exception e) {
            Log.e("TexturedSquareMesh error", e.toString());
        }
    }

}
