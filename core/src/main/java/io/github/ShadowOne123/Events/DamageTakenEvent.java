package io.github.ShadowOne123.Events;

import com.badlogic.gdx.Game;
import io.github.ShadowOne123.Creature;

public class DamageTakenEvent implements GameEvent {

    public final Creature target;

    public final int amount;

    public DamageTakenEvent(Creature target, int amount) {
        this.target = target;
        this.amount = amount;

    }
}
