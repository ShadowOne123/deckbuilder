package io.github.ShadowOne123.Statuses;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.utils.Align;
import io.github.ShadowOne123.Main;

public class StatusInputListener extends InputListener {

    Status status;

    public StatusInputListener (Status status){
        this.status = status;
    }

    @Override
    public boolean touchDown(InputEvent event, float x, float y, int pointer, int button){
        if(button == 1) {
            Main.tooltip.updateText(status.descriptiveToString());
            Main.tooltip.pack();
            Main.tooltip.setPosition(status.getX() + status.getWidth() / 2, status.getTop(), Align.bottom);
            Main.tooltip.setVisible(true);
        }
        else{
            Main.tooltip.setVisible(false);
        }
        return false;
    }
}
