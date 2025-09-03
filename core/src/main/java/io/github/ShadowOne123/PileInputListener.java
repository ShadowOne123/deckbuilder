package io.github.ShadowOne123;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;

import java.util.ArrayList;

import static io.github.ShadowOne123.Main.*;

public class PileInputListener extends InputListener {

    private ArrayList<Card> cards;

    public PileInputListener(ArrayList<Card> cards){
        this.cards = cards;
    }

    @Override
    public boolean touchDown(InputEvent event, float x, float y, int pointer, int button){
        System.out.println("ping");
        InputController.setInputModeMenu();
        Card temp;
        for(int i = 0; i < cards.size(); i++){
            temp = cards.get(i);
            menuStage.addActor(temp);
            temp.setVisible(true);
            temp.setSize(hand.cardWidth, hand.cardHeight);
            temp.setPosition(hand.cardWidth*i*1.5f + hand.cardWidth/1.5f, viewport.getWorldHeight()/2);
            temp.getSprite().setPosition(temp.getX(), temp.getY());
        }
        return false;
    }
}
