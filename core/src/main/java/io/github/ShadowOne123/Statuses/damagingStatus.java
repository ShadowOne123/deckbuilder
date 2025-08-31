package io.github.ShadowOne123.Statuses;

import io.github.ShadowOne123.Creature;
import io.github.ShadowOne123.DamageType;
import io.github.ShadowOne123.Events.DamageEvent;
import io.github.ShadowOne123.Events.DamageTakenEvent;
import io.github.ShadowOne123.Main;

public class damagingStatus extends Status {

    DamageType damageType;

    public damagingStatus(int intensity, DamageType damageType, String name, boolean endTurnDecay){
        super(name, endTurnDecay);
        this.intensity = intensity;
        this.damageType = damageType;
    }

    @Override
    public void apply(Creature target){
        Main.eventBus.emit(new DamageEvent(target, null, intensity, damageType));
    }

    @Override
    public String descriptiveToString(){
        StringBuilder sb = new StringBuilder();
        sb.append(name);
        sb.append("\n");
        sb.append("deals ");
        sb.append(intensity);
        sb.append(" ");
        sb.append(damageType.toString().toLowerCase());
        sb.append(" damage every turn");
        return sb.toString();
    }
}
