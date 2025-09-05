package io.github.ShadowOne123;

import com.badlogic.gdx.scenes.scene2d.Actor;

import java.util.ArrayList;
import java.util.Map;

public class SpellbookActor extends Actor {

    ArrayList<Card> cards;

    public SpellbookActor(){
        Main.menuStage.addActor(this);
        cards = new ArrayList<>();
        ArrayList<String> keys = new ArrayList<>();
        for(String string : SpellResolver.getSpellbook().keySet()){
            keys.add(string);
        }

        for(int i = 0; i < keys.size(); i++){
            String[] splitKey = keys.get(i).split(" ");
            for(int j = 0; j < splitKey.length; j++){
                Card card = new Card(splitKey[j], Main.menuStage);
                card.setPosition(Main.viewport.getWorldWidth()/4 + Main.hand.cardWidth*(j*1.3f), Main.viewport.getWorldHeight()/5 + Main.hand.cardHeight*i*1.4f);
                setCardParams(card);
            }
            Card card = new Card(SpellResolver.getSpellbook().get(keys.get(i)), Main.menuStage);
            card.setPosition(Main.viewport.getWorldWidth()/5*4, Main.viewport.getWorldHeight()/5 + Main.hand.cardHeight*i*1.4f);
            setCardParams(card);
        }
    }

    public void viewSpellbook(){
        for(Card card : cards){
            card.setVisible(true);
        }
    }

    public void hideSpellbook(){
        for(Card card : cards){
            card.setVisible(false);
        }
    }

    private void setCardParams(Card card){
        cards.add(card);
        card.setSize(Main.hand.cardWidth, Main.hand.cardHeight);
        card.getSprite().setSize(card.getWidth(), card.getHeight());
        card.getSprite().setPosition(card.getX(), card.getY());
        card.setVisible(false);
    }


}
