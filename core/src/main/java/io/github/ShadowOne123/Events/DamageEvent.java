package io.github.ShadowOne123.Events;

import io.github.ShadowOne123.Creature;
import io.github.ShadowOne123.DamageType;

public class DamageEvent implements GameEvent {
    public final Creature target;

    public final Creature source;

    public final DamageType damageType;

    public int amount;

    public DamageEvent(Creature target, Creature source, int amount, DamageType damageType) {
        this.target = target;
        this.amount = amount;
        this.damageType = damageType;
        this.source = source;
    }

    public String toString(){
        return amount + " " + damageType + " damage, event launched";
    }
}
