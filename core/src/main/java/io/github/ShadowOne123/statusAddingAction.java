package io.github.ShadowOne123;

import java.util.ArrayList;

public class statusAddingAction extends Action{
    ArrayList<Status> statuses;

    public statusAddingAction(String[] status){
        super(0);
        id = 2;
        statuses = new ArrayList<Status>();
        statuses.add(Status.makeStatus(status));
    }

    @Override
    public void apply(Creature target){
        ArrayList<Status> targetStatuses = target.getStatuses();
        mergeStatuses(statuses, targetStatuses, target);
    }

    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder("Status inflicting action with statuses:\n");
        for(Status status : statuses){
            sb.append(status.toString() + "\n");
        }
        return(sb.toString());
    }

    @Override
    public void combine(Action action){
        mergeStatuses(((statusAddingAction)action).getStatuses(), this.statuses, null);
    }

    public ArrayList<Status> getStatuses(){
        return this.statuses;
    }

    public static void mergeStatuses(ArrayList<Status> newStatuses, ArrayList<Status> oldStatuses, Creature target){
        boolean merged;
        for(Status newStatus : newStatuses){
            merged = false;
            for(Status existingStatus: oldStatuses){
                if(newStatus.getName().equals(existingStatus.getName())){
                    existingStatus.addIntensity(newStatus.getIntensity());
                    if(target != null){
                        existingStatus.onAdded(target);
                    }
                    merged = true;
                }
            }
            if(!merged){
                oldStatuses.add(newStatus);
                if(target != null){
                    newStatus.onAdded(target);
                }
            }
        }
    }
}
