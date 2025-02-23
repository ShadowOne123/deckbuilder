package io.github.ShadowOne123;

import java.util.ArrayList;
import java.util.LinkedList;

public class Deck {

    private ArrayList<Card> cards;
    int totalCards;

    public Deck(){
        cards = new ArrayList<Card>();
        this.totalCards = 0;
    }

    public Deck(ArrayList<Card> cards){
        this.cards = cards;
        this.totalCards = cards.size();
    }

    public Card draw(Hand hand){
        hand.addCard(cards.get(0));
        return cards.remove(0);
    }

    public Card getCard(int index){
        return this.cards.get(index);
    }

    public int getCardsLeft(){
        return cards.size();
    }

    public void addCard(Card card){
        cards.add(card);
    }

    public boolean isEmpty(){
        return cards.isEmpty();
    }

    public int getSize(){
        return cards.size();
    }

    public void shuffle(){
        int first;
        int second;
        for(int i = 0; i < 1000; i++){
            first = (int)(Math.random()*(cards.size()-1));
            second = (int)(Math.random()*(cards.size()-1));
            Card temp = cards.get(first);
            cards.set(first, cards.get(second));
            cards.set(second, temp);
        }
    }
}
