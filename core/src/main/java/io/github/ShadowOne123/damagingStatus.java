package io.github.ShadowOne123;

public class damagingStatus extends Status{

    public damagingStatus(int intensity, String name, boolean midTurn, boolean endOfTurn){
        this.intensity = intensity;
        this.name = name;
        this.midTurn = midTurn;
        this.endOfTurn = endOfTurn;
    }

    @Override
    public void apply(Creature target){
        target.takeDamage(this.intensity);
    }
}
