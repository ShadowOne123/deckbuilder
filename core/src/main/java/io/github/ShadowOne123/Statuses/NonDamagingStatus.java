package io.github.ShadowOne123.Statuses;

import io.github.ShadowOne123.Creature;

public class NonDamagingStatus extends Status {

    public NonDamagingStatus(int intensity, String name){
        super(name);
        this.intensity = intensity;
    }

    @Override
    public void apply(Creature target){
        switch (name) {
            case "regen":
                target.getHealed(intensity);
                intensity--;
                break;
        }

    }
}
