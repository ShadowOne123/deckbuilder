package io.github.ShadowOne123;

public abstract class Status {
    protected int intensity;
    protected String name;
    protected boolean midTurn;
    protected boolean endOfTurn;


    public int getIntensity(){
        return this.intensity;
    }

    public String getName(){
        return this.name;
    }

    public void addIntensity(int intensity){
        this.intensity += intensity;
    }

    public boolean isMidTurn(){
        return midTurn;
    }

    public boolean isEndOfTurn() {
        return endOfTurn;
    }

    public void apply(Creature target){
        System.out.println("Unknown status being applied, panic!");
    }

    public String toString(){
        return "Status " + name + " at intensity " + intensity;
    }

    //builds and output a status based on parameters handed through string array
    //called mostly by statusAddingAction.java when creating new actions
    public static Status makeStatus(String[] desc){
        //useful parameters start at index 1 because index 0 was the action identifier
        boolean tempMidTurn = Boolean.parseBoolean(desc[3]);
        boolean tempEndTurn = Boolean.parseBoolean(desc[4]);
        switch(desc[1]){
            case "1":
                return new damagingStatus(Integer.parseInt(desc[1]), desc[2], tempMidTurn, tempEndTurn);
        }
        return null;
    }
}
