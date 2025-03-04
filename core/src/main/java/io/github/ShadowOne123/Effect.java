package io.github.ShadowOne123;

import java.awt.image.AreaAveragingScaleFilter;
import java.util.ArrayList;

public class Effect {
    private ArrayList<Action> actions;

    public Effect(String[] actionDescriptions){
        actions = new ArrayList<Action>();
        String[] actionDescription;
        //starts at one, because actions[0] is the name of the card
        //this is where the big switch-case goes for action types
        for(int i = 1; i < actionDescriptions.length; i++){
            actionDescription = actionDescriptions[i].split(",");
            switch(actionDescription[0]){
                case "1":
                    actions.add(new damagingAction(Integer.parseInt(actionDescription[1])));
            }
        }
    }

    public Effect(){
        actions = new ArrayList<Action>();
    }

    public void addAction(Action action){
        actions.add(action);
    }

    public ArrayList<Action> getActions(){
        return this.actions;
    }

    public String toString(){
        String toReturn = "";
        for(Action action : actions){
            toReturn = toReturn + action.toString() + "\n";
        }
        return toReturn;
    }















    /*
    Old effect implementation, to be deleted later
    public Effect(int damage, int healing, ArrayList<Status> statuses){
        this.damage = damage;
        this.healing = healing;
        this.statuses = statuses;
    }

    public Effect combine(Effect newEffect){
        this.damage += newEffect.getDamage();
        this.healing += newEffect.getHealing();
        this.statuses = mergeStatuses(this.statuses, newEffect.getStatuses());
        return this;
    }

    public int getDamage(){
        return this.damage;
    }

    public int getHealing(){
        return this.healing;
    }

    public ArrayList<Status> getStatuses(){
        return statuses;
    }

    public ArrayList<Status> mergeStatuses(ArrayList<Status> arr1, ArrayList<Status> arr2){
        //loops through every status in first array, if there's already a status with that name in the second array combines them(adds together intensities)
        //otherwise add the new status to the second array
        boolean combined;
        for(Status newS : arr1){
            combined = false;
            for(Status status : arr2){
                if(status.name.equals(newS.getName())){
                    status.addIntensity(newS.getIntensity());
                    combined = true;
                }
            }
            if(!combined){
                arr2.add(newS);
            }
        }
        return arr1;
    }
*/
}
