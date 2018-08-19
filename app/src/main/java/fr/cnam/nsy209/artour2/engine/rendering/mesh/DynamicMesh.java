package fr.cnam.nsy209.artour2.engine.rendering.mesh;

import android.opengl.GLES20;
import android.opengl.GLU;
import android.util.Log;

import com.google.ar.core.PointCloud;

public class DynamicMesh extends Mesh {

    public void updateMesh(PointCloud p_PointCloud) {

        GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, this.m_VertexBufferObject[0]);

        int numPoints = p_PointCloud.getPoints().remaining() / BYTES_PER_FLOAT;
        if (numPoints * BYTES_PER_POINT > this.m_VboSize) {
            while (numPoints * BYTES_PER_POINT > this.m_VboSize) {
                this.m_VboSize *= 2;
            }
            GLES20.glBufferData(GLES20.GL_ARRAY_BUFFER, this.m_VboSize, null, GLES20.GL_DYNAMIC_DRAW);
        }
        GLES20.glBufferSubData(
                GLES20.GL_ARRAY_BUFFER, 0, numPoints * BYTES_PER_POINT, p_PointCloud.getPoints());
        GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, 0);
    }

    public void loadInEngine() {
        GLES20.glGenBuffers(1, this.m_VertexBufferObject, 0);
        GLES20.glGenBuffers(1, this.m_IndexBufferObject, 0);

        if (this.m_VertexBufferObject[0] > 0 && this.m_IndexBufferObject[0] > 0) {

            GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, this.m_VertexBufferObject[0]);

            GLES20.glBufferData(GLES20.GL_ARRAY_BUFFER, this.getVertexArraySize()
                    , null, GLES20.GL_DYNAMIC_DRAW);

            this.loadBuffers();

            GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, 0);

            GLES20.glBindBuffer(GLES20.GL_ELEMENT_ARRAY_BUFFER, this.m_IndexBufferObject[0]);
            GLES20.glBufferData(GLES20.GL_ELEMENT_ARRAY_BUFFER, this.m_IndexBuffer.capacity()
                    * 2, m_IndexBuffer, GLES20.GL_DYNAMIC_DRAW);

            GLES20.glBindBuffer(GLES20.GL_ELEMENT_ARRAY_BUFFER, 0);

            int err = GLES20.glGetError();
            if (err != GLES20.GL_NO_ERROR) {
                Log.e("loadInEngine error", GLU.gluErrorString(err));
            }
        }
        else {
            int err = GLES20.glGetError();
            if (err != GLES20.GL_NO_ERROR) {
                Log.e("loadInEngine error", GLU.gluErrorString(err));
            }
        }
    }

    public void bindBuffers() {
        GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, this.m_VertexBufferObject[0]);

        GLES20.glVertexAttribPointer(this.getProgram().getAttributes().get("v_Position"), 4,
                GLES20.GL_FLOAT,false, BYTES_PER_POINT, 0);

        GLES20.glEnableVertexAttribArray(this.getProgram().getAttributes().get("v_Position"));

        if (this.getProgram().getUniforms().containsKey("v_Color"))
            GLES20.glUniform4fv(this.getProgram().getUniforms().get("v_Color"), 1, this.m_Color, 0);

        if (this.getProgram().getUniforms().containsKey("m_Model"))
            GLES20.glUniformMatrix4fv(this.getProgram().getUniforms().get("m_Model"), 1, false, this.getModelMatrix(), 0);

        int err = GLES20.glGetError();
        if (err != GLES20.GL_NO_ERROR) {
            Log.e("Mesh bindBuffers error", GLU.gluErrorString(err));
        }

    }

}
