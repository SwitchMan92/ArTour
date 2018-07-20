package fr.cnam.nsy209.artour2.engine.rendering.mesh;

import com.google.ar.core.Pose;

import fr.cnam.nsy209.artour2.engine.rendering.common.IRenderable;

public interface IMesh extends IRenderable {
    void    setColor            (float[] Color);
    void    setVertices         (float[] p_Vertices);
    void    setIndices          (short[] p_Indices);
    void    setDepthRendering   (boolean p_DepthRendering);
    void    setWireframe        (boolean p_Wireframe);
    boolean getDepthRendering   ();
    boolean getWireframe        ();
    void    setPose             (Pose p_Pose);
    Pose    getPose             ();
    void    setPosition         (float p_X, float p_Y, float p_Z);
    void    setRotation         (float p_Yaw, float p_Pitch, float p_Roll);
    float[] getPosition         ();
    Pose    getRotation         ();
    float[] getModelMatrix      ();
}
