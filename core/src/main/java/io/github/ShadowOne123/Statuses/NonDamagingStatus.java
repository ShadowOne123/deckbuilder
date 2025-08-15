package io.github.ShadowOne123.Statuses;

import io.github.ShadowOne123.Creature;
import io.github.ShadowOne123.Status;

public class NonDamagingStatus extends Status {

    public NonDamagingStatus(int intensity, String name, String textureName){
        super(textureName, name);
        this.intensity = intensity;
    }

    @Override
    public void apply(Creature target){
        if(name.equals("block")){
            //do nothing
        }
        else if(name.equals("regen")){
            target.getHealed(intensity);
            intensity--;
        }
    }
}
