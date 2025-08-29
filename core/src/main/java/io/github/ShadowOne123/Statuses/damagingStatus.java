package io.github.ShadowOne123.Statuses;

import io.github.ShadowOne123.Creature;
import io.github.ShadowOne123.DamageType;
import io.github.ShadowOne123.Events.DamageTakenEvent;
import io.github.ShadowOne123.Main;

public class damagingStatus extends Status {

    DamageType damageType;

    public damagingStatus(int intensity, DamageType damageType, String name){
        super(name);
        this.intensity = intensity;
        this.damageType = damageType;
    }

    @Override
    public void apply(Creature target){
        Main.eventBus.emit(new DamageTakenEvent(target, intensity, damageType));
        this.intensity--;
    }
}
