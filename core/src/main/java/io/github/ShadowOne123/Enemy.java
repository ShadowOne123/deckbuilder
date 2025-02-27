package io.github.ShadowOne123;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.Viewport;

public class Enemy extends Creature{
    public Enemy(SpriteBatch spriteBatch, Viewport viewport, BitmapFont text, float centerX, float centerY, Texture texture, float width, float height){
        super(spriteBatch, viewport, text, centerX, centerY, texture);
        sprite.setSize(width, height);
        hp = 10;
        maxHP = hp;
        creatureTexture = texture;
    }
}
