package io.github.ShadowOne123;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.utils.viewport.Viewport;

public class Card {
    private Sprite sprite;

    public Card(Texture texture){
        sprite = new Sprite(texture);
        sprite.setCenter(-10,-10);
    }

    public Sprite getSprite(){
        return sprite;
    }

    public void setSize(float width, float height){
        sprite.setSize(width, height);
    }
}
