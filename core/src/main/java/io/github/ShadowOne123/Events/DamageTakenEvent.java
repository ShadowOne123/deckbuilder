package io.github.ShadowOne123.Events;

import com.badlogic.gdx.Game;
import io.github.ShadowOne123.Creature;
import io.github.ShadowOne123.DamageType;


//post block and for things that bypass blocking/thorns/etc. Basically this is the damage after an attack has landed, or from status effects.
//Damage mitigation no longer applies, but stuff that procs from taking damage should proc from this and not from generic DamageEvent
public class DamageTakenEvent implements GameEvent {

    public final Creature target;

    public final int amount;

    public final DamageType damageType;

    public DamageTakenEvent(Creature target, int amount, DamageType damageType) {
        this.target = target;
        this.amount = amount;
        this.damageType = damageType;
    }
}
