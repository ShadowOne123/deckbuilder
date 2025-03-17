package io.github.ShadowOne123;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;

public class FontManager {
    public static BitmapFont healthFont;
    public static BitmapFont stackFont;


    public static void init(){
        FreeTypeFontGenerator healthFontGenerator = new FreeTypeFontGenerator(Gdx.files.internal("FreeSerif.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter healthFontParameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        healthFontParameter.size = 128;
        healthFontParameter.genMipMaps = true;
        healthFont = healthFontGenerator.generateFont(healthFontParameter);
        healthFontGenerator.dispose();

        FreeTypeFontGenerator stackFontGenerator = new FreeTypeFontGenerator(Gdx.files.internal("FreeSerif.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter stackFontParameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        stackFontParameter.size = 128;
        stackFontParameter.genMipMaps = true;
        stackFontParameter.color = Color.GREEN;
        stackFont = stackFontGenerator.generateFont(stackFontParameter);
        stackFont.getData().setScale(0.05f, 0.05f);
        stackFontGenerator.dispose();
    }
}
