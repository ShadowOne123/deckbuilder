package io.github.ShadowOne123.Statuses;

import io.github.ShadowOne123.Creature;
import io.github.ShadowOne123.Events.*;
import io.github.ShadowOne123.Main;

public class StatusIncreasingStatus extends Status {
    public String affectedStatus;
    public int increase;
    private GameEventListener<StatusAddedEvent> listener;

    public StatusIncreasingStatus(int intensity, int increase, String name, String affectedStatus, boolean endTurnDecay){
        super(name, endTurnDecay);
        this.intensity = intensity;
        this.affectedStatus = affectedStatus;
        this.increase = increase;
    }

    @Override
    public void onAdded(Creature target){
        super.onAdded(target);
        //if this is the initial addition of the status. Subsequent stacks
        if(target.searchStatuses(this.name).getIntensity() == this.intensity) {
            listener = event -> {
                if (event.target.equals(target) && event.name.equals(affectedStatus)) {
                    target.searchStatuses(affectedStatus).addIntensity(increase);
                }
            };
            Main.eventBus.register(StatusAddedEvent.class, listener, 10);
        }
    }

    @Override
    public void decay(){
        intensity--;
        if(intensity == 0){
            Main.eventBus.unregister(StatusAddedEvent.class, listener);
        }
    }

    @Override
    public void apply(Creature target){
        //do nothing
    }

    @Override
    public String toString(){
        return "increase " + affectedStatus + " applications by " + increase;
    }

    @Override
    public String descriptiveToString(){
        StringBuilder sb = new StringBuilder();
        sb.append(name);
        sb.append("\n");
        sb.append(this.toString());
        return sb.toString();
    }
}
