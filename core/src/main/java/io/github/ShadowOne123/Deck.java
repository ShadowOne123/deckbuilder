package io.github.ShadowOne123;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Random;
import java.util.concurrent.atomic.AtomicReference;

import static io.github.ShadowOne123.Main.atlas;
import static io.github.ShadowOne123.Main.hand;

public class Deck extends Actor {

    private ArrayList<Card> cards;
    protected ArrayList<Card> discard;
    int totalCards;
    Sprite deckSprite;
    SpriteBatch spriteBatch;

    public Deck( SpriteBatch spriteBatch){
        this.spriteBatch = spriteBatch;
        cards = new ArrayList<Card>();
        discard = new ArrayList<Card>();
        this.totalCards = 0;
        deckSprite = new Sprite(atlas.findRegion("cards/rose"));
        deckSprite.setSize(hand.cardWidth, hand.cardHeight);
        deckSprite.setPosition(hand.worldWidth/15-hand.cardWidth/2, hand.worldHeight/25);
        setSize(hand.cardWidth, hand.cardHeight);
        setPosition(deckSprite.getX(), deckSprite.getY());
        addListener(new PileInputListener(cards));
        Main.stage.addActor(this);
    }

    public void drawDeck(){
        if(!cards.isEmpty()) {
            deckSprite.draw(spriteBatch);
        }
    }

    public Deck(ArrayList<Card> cards){
        this.cards = cards;
        this.totalCards = cards.size();
    }

    public Card draw(Hand hand){
        Card toReturn = null;
        if(cards.isEmpty() && !discard.isEmpty()){
            for(Card card : discard){
                cards.add(card);
            }
            discard.clear();
            shuffle();
        }
        if(!cards.isEmpty()) {
            hand.addCard(cards.get(0));
            toReturn = cards.remove(0);
        }

        return toReturn;
    }

    public void addToDiscard(Card card){
        if(card != null){
            discard.add(card);
        }
    }

    public ArrayList<Card> getCards(){
        return cards;
    }

    public ArrayList<Card> getDiscard(){
        return discard;
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
        Random random = new Random();
        for (int i = cards.size() - 1; i > 0; i--) {
            int j = random.nextInt(i + 1); // random integer from 0 to i (inclusive)
            // Swap elements at indices i and j
            Card temp = cards.get(i);
            cards.set(i, cards.get(j));
            cards.set(j, temp);
        }
    }

    public Sprite getSprite(){
        return deckSprite;
    }

    public void exitMenu(){
        for(Card card : cards){
            Main.stage.addActor(card);
        }
        for(Card card : discard){
            Main.stage.addActor(card);
        }
    }
}
