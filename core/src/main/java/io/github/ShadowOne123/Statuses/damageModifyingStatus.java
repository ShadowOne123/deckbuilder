package io.github.ShadowOne123.Statuses;

import io.github.ShadowOne123.Creature;
import io.github.ShadowOne123.Events.DamageEvent;
import io.github.ShadowOne123.Events.EventBus;
import io.github.ShadowOne123.Events.GameEventListener;
import io.github.ShadowOne123.Main;

public class damageModifyingStatus extends Status{

    /*
    needs to encompass these possible scenarios:
    Increases damage dealt by the holder
    Decreases damage dealt by the holder
    Increases damage dealt *to* the holder
    Decreases damage dealt *to* the holder

    Fields needed: Intensity, obviously. Decrease/increase can probably just be handled by negative intensity, no need for a new status
    Direction affected. "dealt"/"taken" should work
     */
    private final String direction;
    private GameEventListener<DamageEvent> listener;
    private int mult = 1;

    public damageModifyingStatus(int intensity, String name, String direction){
        super(name);
        //if intensity is negative, we remember this but register it as positive for turn counting reasons
        //otherwise mult is 1, which does nothing
        if(intensity < 0){
            mult = -1;
        }
        this.intensity = intensity * mult;
        this.direction = direction;
    }

    @Override
    public void onAdded(Creature target){
        super.onAdded(target);
        if(direction.equals("taken")){
            listener = event -> {
                if(event.target.equals(target)) {
                    event.amount += intensity * mult;
                }
            };
        }
        else if(direction.equals("dealt")){
            listener = event -> {
                if(event.source.equals(target)) {
                    event.amount += intensity * mult;
                    if(name.equals("luck")){
                        intensity = 0;
                    }
                }
            };
        }
        Main.eventBus.register(DamageEvent.class, listener, 5);
    }

    @Override
    public void apply(Creature target){
        if(name.equals("frost")){
            intensity--;
        }
        if(intensity <= 0){
            Main.eventBus.unregister(DamageEvent.class, listener);
        }
    }
}
