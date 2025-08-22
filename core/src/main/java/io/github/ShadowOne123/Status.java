package io.github.ShadowOne123;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import io.github.ShadowOne123.Events.StatusAddedEvent;
import io.github.ShadowOne123.Statuses.NonDamagingStatus;
import io.github.ShadowOne123.Statuses.StatusIncreasingStatus;
import io.github.ShadowOne123.Statuses.damagingStatus;

public abstract class Status {
    protected int intensity;
    protected String name;
    protected boolean midTurn;
    protected boolean endOfTurn;
    protected Sprite sprite;
    protected Texture texture;

    public Status(){

    }

    public Status(String textureName, String name){
        this.texture = new Texture(textureName);
        this.sprite = new Sprite(texture);
        this.name = name;
        sprite.setSize(Main.viewport.getWorldWidth()/50, Main.viewport.getWorldWidth()/50);
    }

    public int getIntensity(){
        return this.intensity;
    }

    public String getName(){
        return this.name;
    }

    public void addIntensity(int intensity){
        this.intensity += intensity;
    }

    public boolean isMidTurn(){
        return midTurn;
    }

    public boolean isEndOfTurn() {
        return endOfTurn;
    }

    public void apply(Creature target){
        System.out.println("Unknown status being applied, panic!");
    }

    public void onAdded(Creature target){Main.eventBus.emit(new StatusAddedEvent(target, name, intensity));}

    public String toString(){
        return " " + intensity + " " + name;
    }

    //builds and output a status based on parameters handed through string array
    //called mostly by statusAddingAction.java when creating new actions
    public static Status makeStatus(String[] desc){
        //useful parameters start at index 1 because index 0 was the action identifier
        switch(desc[1]){
            case "1":
                //Eg bleed, just deals damage at the end of the turn
                //intensity, damage type, name, texture
                return new damagingStatus(Integer.parseInt(desc[2]), DamageType.valueOf(desc[3]), desc[4], desc[5]);
            case "2":
                //Eg block, a status that doesn't do anything on its own but triggers something in combat resolution
                //intensity, name, texture
                return new NonDamagingStatus(Integer.parseInt(desc[2]), desc[3], desc[4]);
            case "3":
                //Status that increases how many stacks of a certain status are applied
                //intensity, increase, name, affected status, texture
                return new StatusIncreasingStatus(Integer.parseInt(desc[2]), Integer.parseInt(desc[3]), desc[4], desc[5], desc[6]);
        }
        return null;
    }

    public Sprite getSprite() {
        return sprite;
    }

    public float getWidth(){
        return sprite.getWidth();
    }
}
