package fr.cnam.nsy209.artour2.engine.rendering.texture;

import java.util.ArrayList;

public class Texture implements ITexture {

    private int m_TextureBind;

    private ArrayList<TextureParameterI> m_TextureParameters;

    public int getTextureBind() { return this.m_TextureBind; }

    public void setTextureBind(int p_TextureBind) { this.m_TextureBind = p_TextureBind; }

    public void addTextureParameterI(int p_Target, int p_Pname, int p_Param) {
        this.m_TextureParameters.add(new TextureParameterI(p_Target, p_Pname, p_Param));
    }

    public void removeTextureParameterI(TextureParameterI p_TexParam) {
        this.m_TextureParameters.remove(p_TexParam);
    }

    public void clearTextureParameters() {
        this.m_TextureParameters = new ArrayList<TextureParameterI>();
    }

    public ArrayList<TextureParameterI> getTextureParameters() {
        return this.m_TextureParameters;
    }

    public Texture() {
        this.m_TextureParameters = new ArrayList<TextureParameterI>();
    }

    public void loadInEngine() {};
    public void unload      () {};
}
