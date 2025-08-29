package io.github.ShadowOne123;
/* generic action class, to be extended by all possible actions that a card effect can perpetuate
*/
public abstract class Action {
    public int intensity;
    public int id;

    public Action(int intensity){
        this.intensity = intensity;
        id = 0;
    }

    public void apply(Creature target){
        System.out.println("Unidentified action type, an error has occurred");
    }

    public void apply(Effect target){
        System.out.println("Unidentified action type, an error has occurred");
    }

    public String toString(){
        return "unidentified Action, seek help";
    }

    public int getIntensity(){
        return this.intensity;
    }

    public boolean combine(Action action){
        System.out.println("Unspecified action combining requested, abort");
        return false;
    }

}

