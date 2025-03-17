package io.github.ShadowOne123;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class damagingStatus extends Status{

    public damagingStatus(int intensity, String name, boolean midTurn, boolean endOfTurn, String textureName){
        super(textureName);
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
