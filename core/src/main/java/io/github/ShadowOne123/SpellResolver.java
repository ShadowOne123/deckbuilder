package io.github.ShadowOne123;

import java.util.ArrayList;

public class SpellResolver {

    public SpellResolver(){

    }

    public static void applyEffect(Creature[] targets, Effect effect){
        ArrayList<Action> actions = effect.getActions();
        for(int i = 0; i < targets.length; i++){
            for(int j = 0; j < actions.size(); j++){
                actions.get(j).apply(targets[i]);
            }
        }
    }


    public static Effect buildEffect(ArrayList<Card> cards){
        Effect[] effectsArr = new Effect[cards.size()];
        for(int i = 0; i < effectsArr.length; i++){
            effectsArr[i] = cards.get(i).getEffect();
        }
        Effect product = new Effect();
        for(Effect effect : effectsArr){
            mergeActions(product.getActions(), effect.getActions());
        }
        return product;
    }

    public void apply(Effect effect, Creature target){

    }

    public static void mergeActions(ArrayList<Action> currentArr, ArrayList<Action> newArr){
        //loops through every action in current array for each new action, if there's an action with the same class, combines them
        //otherwise add the new action to the current array
        boolean combined;
        for(Action newA : newArr){
            combined = false;
            for(Action action : currentArr){
                if(action.getClass().equals(newA.getClass())){
                    action.combine(newA);
                    combined = true;
                }
            }
            if(!combined){
                currentArr.add(newA);
            }
        }
    }
}
