package io.github.ShadowOne123.Actions;

import io.github.ShadowOne123.Creature;
import io.github.ShadowOne123.Events.HealEvent;
import io.github.ShadowOne123.Main;

public class healingAction extends Action {

    public healingAction(int intensity){
        super(intensity);
    }

    @Override
    public void apply(Creature target){
        Main.eventBus.emit(new HealEvent(target, intensity));
    }

    @Override
    public String toString(){
        return "heal for " + intensity;
    }

    @Override
    public boolean combine(Action action){
        this.intensity += action.getIntensity();
        return true;
    }
}
