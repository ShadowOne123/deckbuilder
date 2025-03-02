package io.github.ShadowOne123;

public class Status {
    int intensity;
    String name;

    public int getIntensity(){
        return this.intensity;
    }

    public String getName(){
        return this.name;
    }

    public void addIntensity(int intensity){
        this.intensity += intensity;
    }

    public void resolve(Creature target){
        //resolve the status' effect
        //one giant switch case statement for every effect name? That feels so shit ngl. Is there a way to do this through a map or smth?
        //Although I guess since there's going to be a limited number of statuses it's not that bad.
        //How many will I realistically even come up with, like 30 max probably.
        //A 30 line switch case statement isn't quite firing squad worthy
    }

    public String toString(){
        return "Status " + name + " at intensity " + intensity;
    }
}
