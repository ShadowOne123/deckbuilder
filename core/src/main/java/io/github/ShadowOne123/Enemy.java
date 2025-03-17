package io.github.ShadowOne123;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.utils.viewport.Viewport;

public class Enemy extends Creature{
    public boolean selected;

    public Enemy(SpriteBatch spriteBatch, Viewport viewport, BitmapFont text, float centerX, float centerY, Texture texture, float width, float height){
        super(spriteBatch, viewport, text, centerX, centerY, texture);
        sprite.setSize(width, height);
        hp = 10;
        maxHP = hp;
        creatureTexture = texture;

        //this.setBounds(0,0,creatureTexture.getWidth(), creatureTexture.getHeight());
        this.setBounds(sprite.getX(), sprite.getY(), sprite.getWidth(), sprite.getHeight());
        this.addListener(new InputListener() {
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                if(button == 0){
                    if(!selected){
                        sprite.setSize(sprite.getWidth()*1.25f, sprite.getHeight()*1.25f);
                    }
                    else{
                        sprite.setSize(sprite.getWidth()*0.8f, sprite.getHeight()*0.8f);
                    }
                    selected = !selected;
                    System.out.println(selected);
                }
                if(button == 1 && selected){
                    hp -= 1;
                }
                return true;
            }
        });
    }

    public boolean isSelected(){
        return selected;
    }

    public void unselect(){
        this.selected = false;
        sprite.setSize(sprite.getWidth()*0.8f, sprite.getHeight()*0.8f);
    }
}
