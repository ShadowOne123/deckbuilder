package io.github.ShadowOne123;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.Viewport;

abstract public class Creature{

    protected int hp;
    protected int maxHP;
    protected Sprite sprite;
    Sprite healthBar;
    Viewport viewport;
    SpriteBatch spriteBatch;
    Texture creatureTexture;
    BitmapFont text;
    float centerX;
    float centerY;
    //statuses probably

    public Creature(SpriteBatch spriteBatch, Viewport viewport, BitmapFont text, float centerX, float centerY, Texture texture){
        this.spriteBatch = spriteBatch;
        this.viewport = viewport;
        creatureTexture = texture;
        sprite = new Sprite(creatureTexture);
        sprite.setSize(viewport.getWorldWidth()/10, viewport.getWorldHeight()/5);
        this.text = text;
        text.getData().setScale(0.1f);
        this.centerX = centerX;
        this.centerY = centerY;
    }

    public void draw(){
        sprite.draw(spriteBatch);
        sprite.setCenter(centerX,centerY);
        text.draw(spriteBatch, ""+hp, sprite.getX()+sprite.getWidth()/3.5f, sprite.getY());
    }

    public Sprite getSprite(){
        return this.sprite;
    }

    public int getHp(){
        return hp;
    }

    public void takeDamage(int damage){
        this.hp = this.hp - damage;
    }
}
