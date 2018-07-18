package fr.cnam.nsy209.artour2.engine.scene;

import android.opengl.GLES20;
import android.opengl.GLU;
import android.util.Log;

import java.util.ArrayList;
import java.util.Iterator;

import fr.cnam.nsy209.artour2.engine.rendering.ACRenderable;
import fr.cnam.nsy209.artour2.engine.rendering.IMesh;
import fr.cnam.nsy209.artour2.engine.shading.program.IProgram;

public class Scene extends ACRenderable implements IScene {

    private ArrayList<IMesh> m_NoDepthMeshes;
    private ArrayList<IMesh> m_DepthMeshes;

    public void addMesh                     (IMesh p_Mesh){
        if (p_Mesh.getDepthRendering())
            m_DepthMeshes.add(p_Mesh);
        else
            m_NoDepthMeshes.add(p_Mesh);
    };

    public void removeMesh                  (IMesh p_Mesh){
        if (p_Mesh.getDepthRendering())
            m_DepthMeshes.remove(p_Mesh);
        else
            m_NoDepthMeshes.remove(p_Mesh);
    };

    private void renderMeshes(ArrayList<IMesh> p_Meshes) {
        Iterator<IMesh> l_MeshIterator = p_Meshes.iterator();

        while(l_MeshIterator.hasNext()) {
            IMesh l_Mesh = l_MeshIterator.next();
            l_Mesh.render();
        }
    }

    public void render(){
        GLES20.glClear(GLES20.GL_DEPTH_BUFFER_BIT | GLES20.GL_COLOR_BUFFER_BIT);

        this.getProgram().setActive(true);
        this.bindBuffers();

        if (this.m_NoDepthMeshes.size() > 0) {
            GLES20.glDisable(GLES20.GL_DEPTH_TEST);
            this.renderMeshes(this.m_NoDepthMeshes);
        }

        if (this.m_DepthMeshes.size() > 0) {
            GLES20.glEnable(GLES20.GL_DEPTH_TEST);
            this.renderMeshes(this.m_DepthMeshes);
        }

        this.unbindBuffers();

        int err = GLES20.glGetError();
        if (err != GLES20.GL_NO_ERROR) {
            Log.e("error", GLU.gluErrorString(err));
        }
    };

    public void loadInEngine() {};
    public void bindBuffers(){};
    public void unbindBuffers(){};

    public void unload() {};

    public void setProgram(IProgram p_Program) {};
}
