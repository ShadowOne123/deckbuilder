package io.github.ShadowOne123;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.Viewport;

import java.util.ArrayList;

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
    private ArrayList<Status> statuses;

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
        statuses = new ArrayList<Status>();
    }

    public void draw(){
        sprite.draw(spriteBatch);
        sprite.setCenter(centerX,centerY);
        text.draw(spriteBatch, ""+hp, sprite.getX()+sprite.getWidth()/3.5f, sprite.getY());
    }


    public void takeDamage(int damage){
        if(this.hp - damage > 0) {
            this.hp = this.hp - damage;
        }
        else{
            this.hp = 0;
            die();
        }
    }

    public void getHealed(int healing){this.hp += healing;}

    public void die(){
        System.out.println("blerghh, I'm dead!!");
    }

    public Sprite getSprite(){
        return this.sprite;
    }

    public int getHp(){
        return hp;
    }

    public ArrayList<Status> getStatuses() {
        return statuses;
    }

    public void addStatus(Status status){
        this.statuses.add(status);
    }

    public void setStatuses(ArrayList<Status> statuses) {
        this.statuses = statuses;
    }

    public void setHealth(int hp){
        this.hp = hp;
    }

    public int getMaxHP(){
        return this.maxHP;
    }
}
