package io.github.ShadowOne123.Events;

import io.github.ShadowOne123.Creature;

public class HealEvent implements GameEvent{
    public Creature target;
    public int amount;

    public HealEvent(Creature target, int amount){
        this.target = target;
        this.amount = amount;
    }
}
