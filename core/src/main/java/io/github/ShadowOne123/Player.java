package io.github.ShadowOne123;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;

public class Player extends Creature{
    //statuses probably

    public Player(SpriteBatch spriteBatch, Viewport viewport, BitmapFont text, float centerX, float centerY, Texture texture){
        super(spriteBatch, viewport, text, centerX, centerY, texture);
        hp = 60;
        maxHP = 60;
    }

    public void drawPlayer(){
        super.draw();
    }

    public void takeStatuses(){
        for(int i = 0; i < statuses.size(); i++){
            statuses.get(i).apply(this);
            if(statuses.get(i).getIntensity() == 0){
                statuses.remove(i);
                i--;
            }
        }
    }
}
