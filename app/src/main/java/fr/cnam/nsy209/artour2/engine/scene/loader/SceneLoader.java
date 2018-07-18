package fr.cnam.nsy209.artour2.engine.scene.loader;

import java.util.concurrent.ConcurrentHashMap;
import org.json.*;
import fr.cnam.nsy209.artour2.engine.scene.IScene;
import fr.cnam.nsy209.artour2.engine.shading.program.loader.ProgramLoader;

public class SceneLoader {

    private static ConcurrentHashMap<String, IScene>    g_Scenes;
    private static SceneLoader                          g_SceneLoader = new SceneLoader();

    private SceneLoader() {}

    public SceneLoader getInstance() { return g_SceneLoader; }

    public static IScene getScene(String p_SceneName) throws java.io.IOException {
        return g_SceneLoader.loadScene(p_SceneName);
    }

    public static IScene loadScene(String p_SceneName) throws java.io.IOException {



        return null;
    }

}
