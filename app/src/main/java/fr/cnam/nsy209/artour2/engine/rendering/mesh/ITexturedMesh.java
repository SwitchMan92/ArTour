package fr.cnam.nsy209.artour2.engine.rendering.mesh;

import fr.cnam.nsy209.artour2.engine.rendering.mesh.IMesh;

/**
 * Created by ng6fd11 on 21/05/2018.
 */

public interface ITexturedMesh extends IMesh {
    void setTextureCoordinates(float[] p_TextureCoordinates);
}
