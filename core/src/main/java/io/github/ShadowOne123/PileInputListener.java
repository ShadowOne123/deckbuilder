package io.github.ShadowOne123;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;

import java.util.ArrayList;

public class PileInputListener extends InputListener {

    private ArrayList<Card> cards;

    public PileInputListener(ArrayList<Card> cards){
        this.cards = cards;
    }

    @Override
    public boolean touchDown(InputEvent event, float x, float y, int pointer, int button){
        System.out.println("ping");
        InputController.setInputModeMenu();
        for(int i = 0; i < cards.size(); i++){

        }
        return false;
    }
}
