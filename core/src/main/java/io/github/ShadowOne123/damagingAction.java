package io.github.ShadowOne123;

public class damagingAction extends Action{

    @Override
    public void apply(Creature target){
        target.takeDamage(intensity);
    }

    public damagingAction(int intensity){
        super(intensity);
        id = 1;
    }

    @Override
    public String toString(){
        return "Damaging action with intensity " + intensity;
    }

    @Override
    public void combine(Action action){
        this.intensity += action.getIntensity();
    }
}
