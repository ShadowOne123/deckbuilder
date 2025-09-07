package io.github.ShadowOne123.Actions;

import io.github.ShadowOne123.Creature;
import io.github.ShadowOne123.DamageType;
import io.github.ShadowOne123.Events.DamageEvent;
import io.github.ShadowOne123.Events.HealEvent;
import io.github.ShadowOne123.Main;

public class statusDependentAction extends Action{

    private String status;
    private String type;
    private DamageType damageType;

    public statusDependentAction(int intensity, String type, String status){
        super(intensity);
        String[] typeSplit = type.split(">");
        this.type = typeSplit[0]; //interaction type, ie heal heals for intensity * status in question
        if(this.type.equals("damage")){
            this.damageType = DamageType.valueOf(typeSplit[1]);
        }
        this.status = status; //status that the action depends on
    }

    @Override
    public void apply(Creature target){
        if(target.searchStatuses(status) != null) {
            int statusIntensity = target.searchStatuses(status).getIntensity();
            switch (type){
                case "heal":
                    Main.eventBus.emit(new HealEvent(Main.player, statusIntensity * intensity));
                    break;
                case "damage":
                    Main.eventBus.emit(new DamageEvent(target, Main.player, intensity * statusIntensity, damageType));
            }
        }
    }
}
