package io.github.ShadowOne123;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.utils.viewport.Viewport;

public class Card {
    private float height;
    private float width;
    private Sprite sprite;
    private Texture cardTexture = new Texture("card.png");;

    public Card(Viewport viewport){
        height = viewport.getWorldHeight()/4;
        width = height*0.7f;
        sprite = new Sprite(cardTexture);
        sprite.setSize(width, height);
        sprite.setCenter(-10,-10);
    }

    public Card(Float size){
        height = size;
        width = 0.7f*height;
        sprite = new Sprite(cardTexture);
        sprite.setSize(width, height);
        sprite.setCenter(-10,-10);
    }

    public float getHeight() {
        return height;
    }

    public float getWidth() {
        return width;
    }

    public Sprite getSprite(){
        return sprite;
    }
}
