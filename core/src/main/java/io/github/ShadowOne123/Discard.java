package io.github.ShadowOne123;

import java.util.ArrayList;

public class Discard {
    private ArrayList<Card> cards;
    int size;

    public Discard(){
        this.cards = new ArrayList<Card>();
        this.size = 0;
    }

    public void addCard(Card card){
        cards.add(card);
        this.size++;
    }

    public ArrayList<Card> getCards(){
        return this.cards;
    }

    public void clear(){
        while(!cards.isEmpty()){
            cards.remove(0);
        }
    }
}
