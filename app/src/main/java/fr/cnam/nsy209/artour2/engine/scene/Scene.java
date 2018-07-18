package fr.cnam.nsy209.artour2.engine.scene;

import android.opengl.GLES20;
import android.opengl.GLU;
import android.util.Log;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;

import fr.cnam.nsy209.artour2.IEngineObject;
import fr.cnam.nsy209.artour2.engine.rendering.ACRenderable;
import fr.cnam.nsy209.artour2.engine.rendering.IMesh;
import fr.cnam.nsy209.artour2.engine.rendering.IRenderable;
import fr.cnam.nsy209.artour2.engine.rendering.Mesh;
import fr.cnam.nsy209.artour2.engine.shading.program.IProgram;
import fr.cnam.nsy209.artour2.engine.shading.program.loader.ProgramLoader;

public class Scene implements IScene {

    private ArrayList<IMesh> m_NoDepthMeshes;
    private ArrayList<IMesh> m_DepthMeshes;

    public Scene() {
        this.m_NoDepthMeshes = new ArrayList<IMesh>();
        this.m_DepthMeshes = new ArrayList<IMesh>();
    }

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

    private void loadObjects(Collection<IEngineObject> p_Objects) {
        Iterator<IEngineObject> l_ObjectIterator = p_Objects.iterator();

        while(l_ObjectIterator.hasNext()) {
            IEngineObject l_Object = l_ObjectIterator.next();
            l_Object.loadInEngine();
        }
    }

    private void unloadObjects(Collection<IEngineObject> p_Objects) {
        Iterator<IEngineObject> l_ObjectIterator = p_Objects.iterator();

        while(l_ObjectIterator.hasNext()) {
            IEngineObject l_Object = l_ObjectIterator.next();
            l_Object.unload();
        }
    }

    private void renderMeshes(ArrayList<IMesh> p_Meshes) {
        Iterator<IMesh> l_MeshIterator = p_Meshes.iterator();

        while(l_MeshIterator.hasNext()) {
            IMesh l_Mesh = l_MeshIterator.next();
            l_Mesh.bindBuffers();
            l_Mesh.render();
            l_Mesh.unbindBuffers();
        }
    }

    public void render(){
        GLES20.glClear(GLES20.GL_DEPTH_BUFFER_BIT | GLES20.GL_COLOR_BUFFER_BIT);

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

    public void loadInEngine() {

        Collection<IProgram> l_Programs = ProgramLoader.getPrograms().values();

        for(IProgram l_Program : l_Programs) {
            l_Program.loadInEngine();
        }

        ArrayList<IEngineObject> l_Meshes = new ArrayList<IEngineObject>(this.m_NoDepthMeshes);
        this.loadObjects(l_Meshes);

        l_Meshes = new ArrayList<IEngineObject>(this.m_DepthMeshes);
        this.loadObjects(l_Meshes);
    };

    public void bindBuffers(){};

    public void unbindBuffers(){};

    public void unload() {

        Collection<IProgram> l_Programs = ProgramLoader.getPrograms().values();

        for(IProgram l_Program : l_Programs) {
            l_Program.unload();
        }

        ArrayList<IEngineObject> l_Meshes = new ArrayList<IEngineObject>(this.m_NoDepthMeshes);
        this.unloadObjects(l_Meshes);

        l_Meshes = new ArrayList<IEngineObject>(this.m_DepthMeshes);
        this.unloadObjects(l_Meshes);
    };

    public IProgram getProgram() { return null; }
    public void setProgram(IProgram p_Program) {};
}
