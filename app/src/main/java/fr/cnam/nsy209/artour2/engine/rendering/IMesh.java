package fr.cnam.nsy209.artour2.engine.rendering;

public interface IMesh extends IRenderable {
    void    setColor            (float[] Color);
    void    setVertices         (float[] p_Vertices);
    void    setIndices          (short[] p_Indices);
    void    setDepthRendering   (boolean p_DepthRendering);
    void    setWireframe        (boolean p_Wireframe);
    boolean getDepthRendering   ();
    boolean getWireframe        ();
}
