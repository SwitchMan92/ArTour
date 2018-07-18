package fr.cnam.nsy209.artour2.engine.rendering;

import fr.cnam.nsy209.artour2.IEngineObject;
import fr.cnam.nsy209.artour2.engine.shading.program.IProgram;

/**
 * Created by NG6FD11 on 18/05/2018.
 */

public interface IRenderable extends IEngineObject {
    void        render          ();
    void        setProgram      (IProgram p_Program);
    IProgram    getProgram      ();
    void        bindBuffers     ();
    void        unbindBuffers   ();
}
