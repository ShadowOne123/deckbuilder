package io.github.ShadowOne123;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Actor;

import static io.github.ShadowOne123.Main.atlas;
import static io.github.ShadowOne123.Main.uiStage;

public class IconButton extends Actor {
    public Sprite buttonSprite;
    public Sprite icon;


    public IconButton(String iconTexture, float x, float y, float width){
        buttonSprite = new Sprite(atlas.findRegion("button_background"));
        icon = new Sprite(atlas.findRegion(iconTexture));
        buttonSprite.setPosition(x, y);
        this.setPosition(x,y);
        buttonSprite.setSize(width, width);
        this.setSize(width, width);
        icon.setSize(width*.5f, width*.5f);
        icon.setCenter(x+width/2,y+width/1.7f);
        uiStage.addActor(this);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.draw(buttonSprite, getX(), getY(), getWidth(), getHeight());
        batch.draw(icon, icon.getX(), icon.getY(), icon.getWidth(), icon.getHeight());
    }
}
