package io.github.ShadowOne123;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;

import static io.github.ShadowOne123.Main.atlas;
import static io.github.ShadowOne123.Main.viewport;

public class SpellbookButton extends Actor {
    Sprite sprite;

    public SpellbookButton(){
        this.sprite = new Sprite(atlas.findRegion("bucket"));
        setPosition(viewport.getWorldWidth()/10, viewport.getWorldHeight()/5*4);
        sprite.setPosition(getX(), getY());
        setSize(viewport.getWorldWidth()/10, viewport.getWorldWidth()/10);
        sprite.setSize(getWidth(), getHeight());
        Main.uiStage.addActor(this);
        addListener(new spellbookInputListener());
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.draw(sprite, getX(), getY(), getWidth(), getHeight());
    }

}
