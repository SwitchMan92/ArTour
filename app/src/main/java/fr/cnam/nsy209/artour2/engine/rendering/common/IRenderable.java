package fr.cnam.nsy209.artour2.engine.rendering.common;

import com.google.ar.core.Camera;

import fr.cnam.nsy209.artour2.engine.rendering.common.IEngineObject;
import fr.cnam.nsy209.artour2.engine.shading.program.IProgram;

/**
 * Created by NG6FD11 on 18/05/2018.
 */

public interface IRenderable extends IEngineObject {
    void        render          (float[] p_ViewMatrix, float[] p_ProjectionMatrix, float[] p_ViewProjMatrix);
    void        setProgram      (IProgram p_Program);
    IProgram    getProgram      ();
    void        bindBuffers     ();
    void        unbindBuffers   ();
}
