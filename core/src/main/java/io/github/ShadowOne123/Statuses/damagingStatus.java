package io.github.ShadowOne123.Statuses;

import io.github.ShadowOne123.Creature;
import io.github.ShadowOne123.Events.DamageEvent;
import io.github.ShadowOne123.Main;
import io.github.ShadowOne123.Status;

public class damagingStatus extends Status {

    public damagingStatus(int intensity, String name, String textureName){
        super(textureName, name);
        this.intensity = intensity;
    }

    @Override
    public void apply(Creature target){
        Main.eventBus.emit(new DamageEvent(target, intensity));
        this.intensity--;
    }
}
