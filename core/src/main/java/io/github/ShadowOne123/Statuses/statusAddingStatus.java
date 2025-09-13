package io.github.ShadowOne123.Statuses;


import io.github.ShadowOne123.Creature;
import io.github.ShadowOne123.Actions.statusAddingAction;

//adds a specified status every turn, in the amount equal to its intensity
public class statusAddingStatus extends Status{

    private String addedStatus;

    public statusAddingStatus(int intensity, String name, String addedStatus, boolean endTurnDecay){
        super(name, endTurnDecay);
        this.intensity = intensity;
        this.addedStatus = addedStatus;
    }

    @Override
    public void apply(Creature target){
        statusAddingAction action;
        switch (addedStatus){
            case "luck":
                String desc = "x,4," + intensity + ",luck,dealt,false";
                action = new statusAddingAction(desc.split(","));
                action.apply(target);
        }
    }

    @Override
    public String descriptiveToString(){
        StringBuilder sb = new StringBuilder();
        sb.append(name);
        sb.append("\n");
        sb.append("gain ");
        sb.append(intensity);
        sb.append(" stacks of ");
        sb.append(addedStatus);
        sb.append(" every turn");
        return sb.toString();
    }

}
