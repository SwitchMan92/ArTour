package fr.cnam.nsy209.artour2.engine.rendering.mesh;

import android.util.Log;

import fr.cnam.nsy209.artour2.engine.shading.program.loader.ProgramLoader;

public class PlaneMesh extends Mesh {

    // number of coordinates per vertex in this array
    static final int COORDS_PER_VERTEX = 3;

    static float squareCoords[] = {
            -1f,  1f, 0.0f,   // top left
            -1f, -1f, 0.0f,   // bottom left
            1f, -1f, 0.0f,   // bottom right
            1f,  1f, 0.0f }; // top right

    static short drawOrder[] = { 0, 1, 2, 0, 2, 3 }; // order to draw vertices

    public PlaneMesh() {
        super();
        super.setVertices(squareCoords);
        super.setIndices(drawOrder);
        super.setDepthRendering(false);


        try {
            this.setProgram(ProgramLoader.getProgram("color"));
        }
        catch(Exception e) {
            Log.e("PlaneMesh", e.toString());
        }
    }
}
