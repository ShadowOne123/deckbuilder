package io.github.ShadowOne123;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.viewport.Viewport;

import java.util.ArrayList;

import static io.github.ShadowOne123.Main.atlas;
import static io.github.ShadowOne123.Main.viewport;


public class PlayArea{

    private Sprite sprite;
    private TextureRegion slotBorder;
    private SpriteBatch spriteBatch;
    private Card[] cards;
    public Card spell;

    public PlayArea(int slots, SpriteBatch spriteBatch, Viewport viewport){
        this.spriteBatch = spriteBatch;
        cards = new Card[slots];
        sprite = new Sprite();
        sprite.setSize(viewport.getWorldWidth()*0.40f, viewport.getWorldHeight()/4);
        sprite.setCenter(viewport.getWorldWidth()/2, viewport.getWorldHeight()/1.7f);
        spell = null;
        slotBorder = atlas.findRegion("cards/king");
    }

   public ArrayList<Card> getCards(){
        ArrayList<Card> toReturn = new ArrayList<Card>();
        for(int i = 0; i < cards.length; i++){
            if(cards[i] != null){
                toReturn.add(cards[i]);
            }
        }
        return toReturn;
   }

   public Card[] getCardsAsArray(){
        return cards;
   }

   public Sprite getSprite(){
        return sprite;
   }

   public boolean addCard(Card card){
        boolean added = false;
        Sprite temp;
        for(int i = 0; i < cards.length; i++){
            if(cards[i] == null){
                cards[i] = card;
                temp = card.getSprite();
                temp.setCenter(sprite.getX() + ((sprite.getWidth()/cards.length) * (i+0.5f)), sprite.getY() + sprite.getHeight()/2);
                card.setPosition(temp.getX(), temp.getY());
                added = true;
                break;
            }
        }
        return added;
   }



   public Card removeCard(int index){
        Card removed = cards[index];
        cards[index] = null;
        return removed;
   }

   public void drawPlayArea(){
        Color color;
        //card borders
        for(int i = 0; i < cards.length; i++){
            spriteBatch.draw(slotBorder,sprite.getX() + ((sprite.getWidth()/cards.length) * (i+0.16f)), sprite.getY(), viewport.getWorldHeight()/4f*0.65f, viewport.getWorldHeight()/4f);
        }

        //cards
        for(Card card : cards){
            if(card != null){
                color = card.getColor();
                card.getSprite().setColor(color);
                card.getSprite().draw(spriteBatch);
            }
        }
        //spell card if present
        if(spell != null){
            color = spell.getColor();
            spell.getSprite().setColor(color);
            spell.getSprite().draw(spriteBatch);
        }
   }

   public void clear(){
        for(int i = 0; i < cards.length; i++){
            cards[i] = null;
        }
   }
}
