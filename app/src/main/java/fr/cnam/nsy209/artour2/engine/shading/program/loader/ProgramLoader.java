package fr.cnam.nsy209.artour2.engine.shading.program.loader;

import android.content.Context;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;

import fr.cnam.nsy209.artour2.engine.shading.fragment.FragmentShader;
import fr.cnam.nsy209.artour2.engine.shading.program.IProgram;
import fr.cnam.nsy209.artour2.engine.shading.program.Program;
import fr.cnam.nsy209.artour2.engine.shading.vertex.VertexShader;

public class ProgramLoader {

    private static ConcurrentHashMap<String, IProgram>  g_Programs              =   new ConcurrentHashMap<String, IProgram>();
    private static ProgramLoader                        g_ProgramLoaderInstance =   new ProgramLoader();
    private static Context                              g_Context;

    private ProgramLoader() {}

    public ProgramLoader getInstance() { return g_ProgramLoaderInstance; }

    public static IProgram getProgram(String p_ProgramName) throws java.io.IOException {
        return ProgramLoader.loadProgram(p_ProgramName);
    }

    public static IProgram loadProgram(String p_ProgramName) throws java.io.IOException {

        if (ProgramLoader.g_Programs.containsKey(p_ProgramName))
            return ProgramLoader.g_Programs.get(p_ProgramName);

        String l_ProgramDescriptorPath = "shaders/program/" + p_ProgramName + ".prg";

        InputStream l_ProgramDescriptorStream  = g_Context.getAssets().open(l_ProgramDescriptorPath);
        InputStreamReader l_ProgramDescriptorReader = new InputStreamReader(l_ProgramDescriptorStream);
        BufferedReader buffreader = new BufferedReader(l_ProgramDescriptorReader);

        String line;
        StringBuilder text = new StringBuilder();

        HashMap<String, String> l_ProgramDescriptor = new HashMap<String, String>();

        while (( line = buffreader.readLine()) != null) {
            String[] l_LineDescriptor = line.split("=");
            l_ProgramDescriptor.put(l_LineDescriptor[0], l_LineDescriptor[1]);
        }

        String l_VertexShaderPath = "shaders/vertex/" + l_ProgramDescriptor.get("vertex_shader");
        String l_FragmentShaderPath = "shaders/fragment/" + l_ProgramDescriptor.get("fragment_shader");

        VertexShader l_VertexShader = new VertexShader();
        InputStream l_VertexShaderStream = g_Context.getAssets().open(l_VertexShaderPath);
        l_VertexShader.loadFromFile(l_VertexShaderStream);

        FragmentShader l_FragmentShader = new FragmentShader();
        InputStream l_FragmentShaderStream = g_Context.getAssets().open(l_FragmentShaderPath);
        l_FragmentShader.loadFromFile(l_FragmentShaderStream);

        Program l_Program = new Program();

        l_Program.setVertexShader(l_VertexShader);
        l_Program.setFragmentShader(l_FragmentShader);

        ProgramLoader.g_Programs.put(p_ProgramName, l_Program);

        return l_Program;
    }

}
