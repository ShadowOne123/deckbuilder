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
        System.out.println(card.toString());
        //Main.tooltip.setVisible(true);
        Main.tooltip.updateText("bing");
        Main.tooltip.setPosition(card.getX(), card.getTop()-card.getHeight()/20);
        return false;
    }



}
