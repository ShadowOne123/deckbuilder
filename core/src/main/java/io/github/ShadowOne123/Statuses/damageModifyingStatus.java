package io.github.ShadowOne123.Statuses;

import io.github.ShadowOne123.Creature;
import io.github.ShadowOne123.Events.DamageEvent;
import io.github.ShadowOne123.Events.EventBus;
import io.github.ShadowOne123.Events.GameEventListener;
import io.github.ShadowOne123.Main;

public class damageModifyingStatus extends Status{

    /*
    All versions except luck decay over the end turn
     */
    private final String direction;
    private GameEventListener<DamageEvent> listener;
    private int mult = 1;

    public damageModifyingStatus(int intensity, String name, String direction, boolean endTurnDecay){
        super(name, endTurnDecay);
        //if intensity is negative, we remember this but register it as positive for turn counting reasons
        //This means intensity is *always* positive
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
                if(event.source != null) {
                    if (event.source.equals(target)) {
                        event.amount += intensity * mult;
                        if (name.equals("luck")) {
                            intensity = 0;
                        }
                    }
                }
            };
        }
        Main.eventBus.register(DamageEvent.class, listener, 5);
    }

    @Override
    public void apply(Creature target){
        //do nothing
    }

    @Override
    public String descriptiveToString(){
        StringBuilder sb = new StringBuilder();
        sb.append(name);
        sb.append("\n");
        if(mult == 1){
            sb.append("increases damage ");
        }
        else{
            sb.append("decreases damage ");
        }
        sb.append(direction);
        sb.append(" by ");
        sb.append(intensity);
        return sb.toString();
    }

    @Override
    public void decay(){
        intensity--;
        if(intensity <= 0){
            Main.eventBus.unregister(DamageEvent.class, listener);
        }
    }
}
