package io.github.ShadowOne123;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;

public class CardInputListener extends InputListener {

    Card card;

    public CardInputListener(Card card){
        this.card = card;
    }

    @Override
    public boolean touchDown(InputEvent event, float x, float y, int pointer, int button){
        if(button == 1) {
            Main.tooltip.updateText(card.toString());
            Main.tooltip.setPosition(card.getX() + card.getWidth() / 2, card.getTop() + card.getHeight() / 4);
            Main.tooltip.setVisible(true);
        }
        else{
            Main.tooltip.setVisible(false);
        }
        return false;
    }



}
