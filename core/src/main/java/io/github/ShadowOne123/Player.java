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

public class Player {
    int health;
    Sprite sprite;
    Sprite healthBar;
    Viewport viewport;
    SpriteBatch spriteBatch;
    Texture playerTexture;
    BitmapFont text;
    //statuses probably

    public Player(SpriteBatch spriteBatch, Viewport viewport, BitmapFont text){
        this.spriteBatch = spriteBatch;
        this.viewport = viewport;
        playerTexture = new Texture("bucket.png");
        sprite = new Sprite(playerTexture);
        sprite.setSize(viewport.getWorldWidth()/10, viewport.getWorldHeight()/5);
        this.text = text;
        text.getData().setScale(0.1f);
    }

    public void drawPlayer(){
        sprite.draw(spriteBatch);
        sprite.setCenter(viewport.getWorldWidth()/7,viewport.getWorldHeight()/3);
        text.draw(spriteBatch, "6 0", sprite.getX()+sprite.getWidth()/3.5f, sprite.getY());
    }
}
