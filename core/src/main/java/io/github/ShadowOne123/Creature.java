package io.github.ShadowOne123;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.Actor;

import static io.github.ShadowOne123.Main.atlas;

import com.badlogic.gdx.utils.viewport.Viewport;
import io.github.ShadowOne123.Statuses.Status;

import java.util.ArrayList;

abstract public class Creature extends Actor {

    protected int hp;
    protected int maxHP;
    private boolean selected;
    protected Sprite sprite;
    Viewport viewport;
    SpriteBatch spriteBatch;
    BitmapFont text;
    protected boolean dead;
    BitmapFont statusStackFont;
    float centerX;
    float centerY;
    protected ArrayList<Status> statuses;
    protected CombatController combatController;

    public Creature(SpriteBatch spriteBatch, Viewport viewport, FreeTypeFontGenerator textGen, FreeTypeFontGenerator.FreeTypeFontParameter textParam, float centerX, float centerY, String texture){
        this.spriteBatch = spriteBatch;
        this.viewport = viewport;
        sprite = new Sprite(atlas.findRegion(texture));
        sprite.setSize(viewport.getWorldWidth()/10, viewport.getWorldHeight()/5);
        this.text = textGen.generateFont(textParam);
        text.getData().setScale(0.5f);
        this.centerX = centerX;
        this.centerY = centerY;
        sprite.setCenter(centerX, centerY);
        statuses = new ArrayList<Status>();
        statusStackFont = FontManager.stackFont;
        setBounds(sprite.getX(), sprite.getY(), sprite.getWidth(), sprite.getHeight());
        dead = false;
    }

    public void draw(){
        if(!dead) {
            sprite.draw(spriteBatch);
            sprite.setCenter(getX() + sprite.getWidth() / 2, getY() + sprite.getHeight() / 2);
            text.draw(spriteBatch, "" + hp, sprite.getX() + sprite.getWidth() / 3.5f, sprite.getY());
            Sprite statusSprite;
            Status status;
            for (int i = 0; i < statuses.size(); i++) {
                status = statuses.get(i);
                statusSprite = status.getSprite();
                status.setPosition(sprite.getX() + i * statusSprite.getWidth(), sprite.getY() - statusSprite.getHeight() * 2f);
                statusSprite.setPosition(status.getX(), status.getY());
                statusSprite.draw(spriteBatch);
                statusStackFont.draw(spriteBatch, String.valueOf(status.getIntensity()), statusSprite.getX() + statusSprite.getWidth() / 1.5f, statusSprite.getY() + statusSprite.getHeight() * 0.4f);
            }
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

    protected Runnable takeStatuses(Creature target){
        return new Runnable() {
            @Override
            public void run() {
                int originalSize = statuses.size();
                for(int i = 0; i < statuses.size(); i++){
                    statuses.get(i).apply(target);
                }
                //if a new status has been added
                if(statuses.size() > originalSize){
                    for(int i = originalSize; i < statuses.size(); i++){
                        statuses.get(i).apply(target);
                    }
                }
            }
        };
    }

    protected Runnable decayStatuses(){
        return new Runnable() {
            @Override
            public void run() {
                for(int i = 0; i < statuses.size(); i++){
                    if(statuses.get(i).hasEndTurnDecay()) {
                        statuses.get(i).decay();
                    }
                    if (statuses.get(i).getIntensity() <= 0) {
                        statuses.remove(i);
                        i--;
                    }
                }
            }
        };
    }

    protected Runnable incrementTurn(){
        return new Runnable() {
            @Override
            public void run() {
                combatController.incrementTurn();
            }
        };
    }

    public void addController(CombatController controller){
        this.combatController = controller;
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

    public void getHealed(int healing){
        this.hp += healing;
        if(this.hp > this.maxHP){
            this.hp = this.maxHP;
        }
    }

    public void die(){
        this.dead = true;
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

    public Status searchStatuses(String name){
        if(!statuses.isEmpty()) {
            for (Status status : statuses) {
                if (status.getName().equals(name)) {
                    return status;
                }
            }
        }
        return null;
    }

    public void addStatus(Status status){
        this.statuses.add(status);
    }

    public void removeStatus(String name){
        statuses.remove(searchStatuses(name));
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
