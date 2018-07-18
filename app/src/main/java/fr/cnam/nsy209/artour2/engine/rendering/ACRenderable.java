package fr.cnam.nsy209.artour2.engine.rendering;

import fr.cnam.nsy209.artour2.engine.shading.program.IProgram;

public abstract class ACRenderable implements IRenderable {

    private IProgram m_Program;

    public void setProgram(IProgram p_Program) {
        this.m_Program = p_Program;
    }

    public IProgram getProgram() { return this.m_Program; }

}
