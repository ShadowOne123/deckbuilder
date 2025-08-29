package io.github.ShadowOne123;

import io.github.ShadowOne123.Events.DamageEvent;

public class damagingAction extends Action{

    private DamageType damageType;

    @Override
    public void apply(Creature target){
        Main.eventBus.emit(new DamageEvent(target, Main.player, intensity, damageType));
    }

    public damagingAction(int intensity, DamageType damageType){
        super(intensity);
        this.damageType = damageType;
        id = 1;
    }

    public DamageType getDamageType(){
        return damageType;
    }

    @Override
    public String toString(){
        return "deals " + intensity + " " + damageType.toString().toLowerCase() + " damage";
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
