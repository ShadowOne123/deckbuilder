package io.github.ShadowOne123.Actions;

import io.github.ShadowOne123.Creature;
import io.github.ShadowOne123.DamageType;
import io.github.ShadowOne123.Events.DamageEvent;
import io.github.ShadowOne123.Main;

public class multiHitAction extends Action{

    private DamageType damageType;
    private int numHits;

    @Override
    public void apply(Creature target){
        for(int i = 0; i < numHits; i++) {
            Main.eventBus.emit(new DamageEvent(target, Main.player, intensity, damageType));
        }
    }

    public multiHitAction(int intensity, int numHits, DamageType damageType){
        super(intensity);
        this.damageType = damageType;
        this.numHits = numHits;
        id = 1;
    }

    public DamageType getDamageType(){
        return damageType;
    }

    @Override
    public String toString(){
        return "deals " + intensity + " " + damageType.toString().toLowerCase() + " damage " + numHits + " times";
    }

    @Override
    public boolean combine(Action action){
        if(((damagingAction)action).getDamageType() == this.damageType) {
            this.intensity += action.getIntensity();
            return true;
        }
        else{
            return false;
        }
    }
}
