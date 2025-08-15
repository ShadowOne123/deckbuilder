package io.github.ShadowOne123.Events;

import io.github.ShadowOne123.Creature;

public class DamageEvent implements GameEvent {
    public final Creature target;

    public final int amount;

    public DamageEvent(Creature target, int amount) {
        this.target = target;
        this.amount = amount;
    }
}
