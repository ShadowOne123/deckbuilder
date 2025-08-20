package io.github.ShadowOne123;

import io.github.ShadowOne123.Events.DamageEvent;

public class damagingAction extends Action{

    DamageType damageType;

    @Override
    public void apply(Creature target){
        Main.eventBus.emit(new DamageEvent(target, Main.player, intensity, damageType));
    }

    public damagingAction(int intensity, DamageType damageType){
        super(intensity);
        this.damageType = damageType;
        id = 1;
    }

    @Override
    public String toString(){
        return "Damaging action with intensity " + intensity;
    }

    @Override
    public void combine(Action action){
        this.intensity += action.getIntensity();
    }
}
