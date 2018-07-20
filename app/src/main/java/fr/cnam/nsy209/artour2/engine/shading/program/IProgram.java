package fr.cnam.nsy209.artour2.engine.shading.program;

import java.util.HashMap;

import fr.cnam.nsy209.artour2.engine.rendering.common.IEngineObject;
import fr.cnam.nsy209.artour2.engine.shading.ACShader;

/**
 * Created by NG6FD11 on 18/05/2018.
 */

public interface IProgram extends IEngineObject {
    void    setVertexShader(ACShader p_VertexShader);
    void    setFragmentShader(ACShader p_FragmentShader);
    void    setActive(boolean p_Active);
    int     getProgram();
    HashMap<String, Integer> getAttributes();
    HashMap<String, Integer> getUniforms();
}
