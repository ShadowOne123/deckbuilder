package io.github.ShadowOne123;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;

import static io.github.ShadowOne123.Main.atlas;

public class DiscardActor extends Actor {

    Deck deck;
    Sprite sprite;
    SpriteBatch spriteBatch;


    public DiscardActor(Deck deck, Hand hand, SpriteBatch spriteBatch){
        this.deck = deck;
        this.spriteBatch = spriteBatch;
        sprite = new Sprite(atlas.findRegion("cards/rose"));
        sprite.setSize(hand.cardWidth, hand.cardHeight);
        sprite.setCenter((hand.worldWidth/15)*14, hand.cardHeight/2 + hand.worldHeight/25);
        addListener(new PileInputListener(deck.discard));
        Main.stage.addActor(this);
    }

    public void draw(){
        if(!deck.getDiscard().isEmpty()){
            sprite.draw(spriteBatch);
        }
    }
}
