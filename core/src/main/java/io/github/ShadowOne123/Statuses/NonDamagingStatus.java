package io.github.ShadowOne123.Statuses;

import io.github.ShadowOne123.Creature;

public class NonDamagingStatus extends Status {

    public NonDamagingStatus(int intensity, String name, boolean endTurnDecay){
        super(name, endTurnDecay);
        this.intensity = intensity;
    }

    @Override
    public void apply(Creature target){
        switch (name) {
            case "regen":
                target.getHealed(intensity);
                break;
        }
    }

    @Override
    public String descriptiveToString(){
        StringBuilder sb = new StringBuilder();
        sb.append(name);
        sb.append("\n");
        switch(name){
            case "regen":
                sb.append("restores ");
                sb.append(intensity);
                sb.append(" health every turn");
                break;
            case "thorns":
                sb.append("deals ");
                sb.append(intensity);
                sb.append(" damage to attackers");
                break;
            case "block":
                sb.append("blocks the next ");
                sb.append(intensity);
                sb.append("damage");
                break;
        }
        return sb.toString();
    }
}
