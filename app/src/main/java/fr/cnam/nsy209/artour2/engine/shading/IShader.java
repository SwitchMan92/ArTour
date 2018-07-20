package fr.cnam.nsy209.artour2.engine.shading;

import java.io.InputStream;

import fr.cnam.nsy209.artour2.engine.rendering.common.IEngineObject;

/**
 * Created by NG6FD11 on 18/05/2018.
 */

public interface IShader extends IEngineObject {
    void loadFromString(String p_ShaderCode);
    void loadFromFile(InputStream p_ShaderFile) throws Exception;

    void loadInEngine();

    int getShader();

}
