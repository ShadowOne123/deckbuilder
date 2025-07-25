package io.github.ShadowOne123;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.*;
import com.badlogic.gdx.utils.viewport.Viewport;

import java.util.ArrayList;

abstract public class Creature extends Actor {

    protected int hp;
    protected int maxHP;
    private boolean selected;
    protected Sprite sprite;
    Sprite healthBar;
    Viewport viewport;
    SpriteBatch spriteBatch;
    Texture creatureTexture;
    BitmapFont text;
    BitmapFont statusStackFont;
    float centerX;
    float centerY;
    //statuses probably
    protected ArrayList<Status> statuses;

    public Creature(SpriteBatch spriteBatch, Viewport viewport, FreeTypeFontGenerator textGen, FreeTypeFontGenerator.FreeTypeFontParameter textParam, float centerX, float centerY, Texture texture){
        this.spriteBatch = spriteBatch;
        this.viewport = viewport;
        creatureTexture = texture;
        sprite = new Sprite(creatureTexture);
        sprite.setSize(viewport.getWorldWidth()/10, viewport.getWorldHeight()/5);
        this.text = textGen.generateFont(textParam);
        text.getData().setScale(0.1f);
        this.centerX = centerX;
        this.centerY = centerY;
        sprite.setCenter(centerX, centerY);
        statuses = new ArrayList<Status>();
        statusStackFont = FontManager.stackFont;
        setBounds(sprite.getX(), sprite.getY(), sprite.getWidth(), sprite.getHeight());
    }

    public void draw(){
        sprite.draw(spriteBatch);
        sprite.setCenter(getX()+sprite.getWidth()/2,getY()+ sprite.getHeight()/2);
        text.draw(spriteBatch, ""+hp, sprite.getX()+sprite.getWidth()/3.5f, sprite.getY());
        Sprite statusSprite;
        Status status;
        for(int i = 0; i < statuses.size(); i++){
            status = statuses.get(i);
            statusSprite = status.getSprite();
            statusSprite.setCenter(sprite.getX()+i*statusSprite.getWidth(), sprite.getY()-statusSprite.getHeight()*1.5f);
            statusSprite.draw(spriteBatch);
            statusStackFont.draw(spriteBatch, String.valueOf(status.getIntensity()), statusSprite.getX()+statusSprite.getWidth(), statusSprite.getY()+statusSprite.getHeight()*0.5f);
        }
    }

    public boolean isSelected(){
        return selected;
    }

    public boolean select(){
        this.selected = true;
        text.setColor(Color.RED);
        return true;

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

    public void unselect(){
        this.selected = false;
        text.setColor(Color.WHITE);
    }




}
