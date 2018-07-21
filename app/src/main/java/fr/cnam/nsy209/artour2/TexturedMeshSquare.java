package fr.cnam.nsy209.artour2;

import android.util.Log;

import fr.cnam.nsy209.artour2.engine.rendering.mesh.TexturedMesh;
import fr.cnam.nsy209.artour2.engine.shading.program.IProgram;
import fr.cnam.nsy209.artour2.engine.shading.program.loader.ProgramLoader;

/**
 * Created by ng6fd11 on 17/05/2018.
 */

public class TexturedMeshSquare extends TexturedMesh {

    // number of coordinates per vertex in this array
    static final int COORDS_PER_VERTEX = 3;
    static float squareCoords[] = {
            -1f,  1f, 0.0f,   // top left
            -1f, -1f, 0.0f,   // bottom left
            1f, -1f, 0.0f,   // bottom right
            1f,  1f, 0.0f }; // top right

    static float texCoords[] = {
                0f, 0f,   // top left
                0f, 1f,   // bottom left
                1f, 1f,   // bottom right
                1f, 0f };

    static short drawOrder[] = { 0, 1, 2, 0, 2, 3 }; // order to draw vertices

    public TexturedMeshSquare() {
        super();
        this.setColor(new float[]{ 0f, 0f, 0f, 0f });
        super.setVertices(squareCoords);
        super.setIndices(drawOrder);
        super.setTextureCoordinates(texCoords);
        super.setDepthRendering(false);

        try {
            this.setProgram(ProgramLoader.getProgram("texture"));
        }
        catch(Exception e) {
            Log.e("TexturedSquareMesh error", e.toString());
        }
    }
}