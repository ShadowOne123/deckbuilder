package io.github.ShadowOne123.Events;

import io.github.ShadowOne123.Creature;
import io.github.ShadowOne123.DamageType;

public class DamageEvent implements GameEvent {
    public final Creature target;

    public final DamageType type;

    public final int amount;

    public DamageEvent(Creature target, int amount, DamageType type) {
        this.target = target;
        this.amount = amount;
        this.type = type;
    }
}
