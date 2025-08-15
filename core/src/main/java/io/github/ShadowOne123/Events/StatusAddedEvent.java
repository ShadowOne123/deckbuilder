package io.github.ShadowOne123.Events;

import com.badlogic.gdx.Game;
import io.github.ShadowOne123.Creature;

public class StatusAddedEvent implements GameEvent {
    public final String name;
    public final Creature target;
    public final int intensity;

    public StatusAddedEvent(Creature target, String name, int intensity) {
        this.target = target;
        this.intensity = intensity;
        this.name = name;
    }
}
