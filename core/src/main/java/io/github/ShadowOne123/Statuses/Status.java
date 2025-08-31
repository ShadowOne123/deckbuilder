package io.github.ShadowOne123.Statuses;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Actor;
import io.github.ShadowOne123.Creature;
import io.github.ShadowOne123.DamageType;
import io.github.ShadowOne123.Events.StatusAddedEvent;
import io.github.ShadowOne123.Main;

import static io.github.ShadowOne123.Main.atlas;

public abstract class Status extends Actor {
    protected int intensity;
    protected boolean endTurnDecay;
    protected String name;
    protected Sprite sprite;

    public Status(){

    }

    public Status(String name, boolean endTurnDecay){
        this.sprite = new Sprite(atlas.findRegion("statuses/" + name));
        this.name = name;
        this.endTurnDecay = endTurnDecay;
        sprite.setSize(Main.viewport.getWorldWidth()/40, Main.viewport.getWorldWidth()/40);
        setSize(Main.viewport.getWorldWidth()/40, Main.viewport.getWorldWidth()/40);
        addListener(new StatusInputListener(this));
        Main.stage.addActor(this);
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

    public void decay() {
        this.intensity--;
    }

    public void apply(Creature target){
        System.out.println("Unknown status being applied, panic!");
    }

    public void onAdded(Creature target){Main.eventBus.emit(new StatusAddedEvent(target, name, intensity));}

    public String toString(){
        return "add " + intensity + " " + name;
    }

    public String descriptiveToString(){
        return "Come back with a warrant";
    }

    //builds and output a status based on parameters handed through string array
    //called mostly by statusAddingAction.java when creating new actions
    public static Status makeStatus(String[] desc){
        //useful parameters start at index 1 because index 0 was the action identifier
        switch(desc[1]){
            case "1":
                //Eg bleed, just deals damage at the end of the turn
                //intensity, damage type, name, endTurnDecay
                return new damagingStatus(Integer.parseInt(desc[2]), DamageType.valueOf(desc[3]), desc[4], Boolean.parseBoolean(desc[5]));
            case "2":
                //Eg block, a status that doesn't do anything on its own but triggers something in combat resolution
                //intensity, name, endTurnDecay
                return new NonDamagingStatus(Integer.parseInt(desc[2]), desc[3], Boolean.parseBoolean(desc[4]));
            case "3":
                //Status that increases how many stacks of a certain status are applied
                //intensity, increase, name, affected status, endTurnDecay
                return new StatusIncreasingStatus(Integer.parseInt(desc[2]), Integer.parseInt(desc[3]), desc[4], desc[5], Boolean.parseBoolean(desc[6]));
            case "4":
                //Status that affects damage dealt either to or by the target. The type is determined by the last argument
                //intensity, name, direction of damage affected, endTurnDecay
                return new damageModifyingStatus(Integer.parseInt(desc[2]), desc[3], desc[4], Boolean.parseBoolean(desc[5]));
            case "5":
                //Status that adds a specified status every turn, equal to its own intensity. Intensity decays over turn end.
                //intensity, name, status to add, endTurnDecay
                return new statusAddingStatus(Integer.parseInt(desc[2]), desc[3], desc[4], Boolean.parseBoolean(desc[5]));
        }
        return null;
    }

    public Sprite getSprite() {
        return sprite;
    }

    public float getWidth(){
        return sprite.getWidth();
    }

    public boolean hasEndTurnDecay(){
        return this.endTurnDecay;
    }
}
