package io.github.ShadowOne123;

import io.github.ShadowOne123.Events.DamageEvent;

public class damagingAction extends Action{

    @Override
    public void apply(Creature target){

        Main.eventBus.emit(new DamageEvent(target, intensity));
    }

    public damagingAction(int intensity){
        super(intensity);
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
