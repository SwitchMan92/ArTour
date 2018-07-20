package fr.cnam.nsy209.artour2.engine.scene;

import fr.cnam.nsy209.artour2.engine.rendering.mesh.IMesh;
import fr.cnam.nsy209.artour2.engine.rendering.IRenderable;

public interface IScene extends IRenderable {
    public void addMesh                     (IMesh p_Mesh);
    public void removeMesh                  (IMesh p_Mesh);
}
