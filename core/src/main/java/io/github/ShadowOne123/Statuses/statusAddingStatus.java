package io.github.ShadowOne123.Statuses;


import io.github.ShadowOne123.Creature;
import io.github.ShadowOne123.statusAddingAction;

//adds a specified status every turn, in the amount equal to its intensity
public class statusAddingStatus extends Status{

    private String addedStatus;

    public statusAddingStatus(int intensity, String name, String addedStatus){
        super(name);
        this.intensity = intensity;
        this.addedStatus = addedStatus;
    }

    @Override
    public void apply(Creature target){
        statusAddingAction action;
        switch (addedStatus){
            case "luck":
                String desc = "4," + intensity + ",luck,dealt";
                action = new statusAddingAction(desc.split(","));
                action.apply(target);
        }
        intensity--;
    }
}
