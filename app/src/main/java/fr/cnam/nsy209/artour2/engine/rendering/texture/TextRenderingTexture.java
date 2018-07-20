package fr.cnam.nsy209.artour2.engine.rendering.texture;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;


public class TextRenderingTexture extends Texture {

    private Canvas  m_Canvas;
    private Paint   m_Paint;
    private Bitmap  m_Bitmap;

    public TextRenderingTexture() {
        super();

        this.m_Canvas = new Canvas();
        this.m_Paint = new Paint();

        this.m_Paint.setColor(Color.BLACK);
        this.setTextSize(32);

        this.m_Bitmap = Bitmap.createBitmap(256, 256, Bitmap.Config.ARGB_8888);
        this.m_Canvas.setBitmap(this.m_Bitmap);
        this.m_Paint.setAntiAlias(true);
    }

    public void setTextSize(float p_TextSize) {
        this.m_Paint.setTextSize(p_TextSize);
    }

    public float getTextSize() { return this.m_Paint.getTextSize(); }

    public void setText(String p_Text) {

        this.m_Paint.setColor(Color.BLACK);
        this.m_Canvas.drawPaint(this.m_Paint);

        this.m_Paint.setColor(Color.WHITE);
        this.m_Paint.setTextAlign(Paint.Align.CENTER);
        this.m_Canvas.drawText(p_Text, 128, 128, this.m_Paint);
    }


}