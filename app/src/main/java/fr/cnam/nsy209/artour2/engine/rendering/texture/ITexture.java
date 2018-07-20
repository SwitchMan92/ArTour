package fr.cnam.nsy209.artour2.engine.rendering.texture;

import java.util.ArrayList;

import fr.cnam.nsy209.artour2.engine.rendering.common.IEngineObject;

public interface ITexture extends IEngineObject {

    public int                              getTextureBind              ();
    public void                             setTextureBind              (int p_TextureBind);
    public void                             addTextureParameterI        (int p_Target, int p_Pname, int p_Param);
    public void                             removeTextureParameterI     (TextureParameterI p_TexParam);
    public void                             clearTextureParameters      ();
    public ArrayList<TextureParameterI>     getTextureParameters        ();
    public void                             loadBuffers                 ();
}
