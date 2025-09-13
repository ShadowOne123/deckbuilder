package io.github.ShadowOne123.Actions;

import io.github.ShadowOne123.Creature;
import io.github.ShadowOne123.DamageType;
import io.github.ShadowOne123.Events.DamageEvent;
import io.github.ShadowOne123.Main;

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
        //temporarily always returning false, meaning it's always treated as a fresh action and executed separately
        return false;
    }
}
