package fr.cnam.nsy209.artour2.engine.rendering.texture;


public class TextureParameterI {
    private int     m_Target;
    private int     m_Pname;
    private int     m_Param;

    public int getTarget()  { return this.m_Target; }
    public int getPname()   { return this.m_Pname;  }
    public int getParam()   { return this.m_Param;  }

    public TextureParameterI(int p_Target, int p_Pname, int p_Param) {
        this.m_Target   = p_Target;
        this.m_Pname    = p_Pname;
        this.m_Param    = p_Param;
    }
}