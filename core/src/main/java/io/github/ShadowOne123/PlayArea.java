package io.github.ShadowOne123;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.Viewport;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class PlayArea{

    private Sprite sprite;
    private SpriteBatch spriteBatch;
    private Card[] cards;

    public PlayArea(int slots, SpriteBatch spriteBatch, Viewport viewport){
        this.spriteBatch = spriteBatch;
        cards = new Card[slots];
        sprite = new Sprite(new Texture("king.png"));
        sprite.setSize(viewport.getWorldWidth()*0.40f, viewport.getWorldHeight()/4);
        sprite.setCenter(viewport.getWorldWidth()/2, viewport.getWorldHeight()/2);
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




   public Sprite getSprite(){
        return sprite;
   }

   public boolean addCard(Card card){
        boolean added = false;
        for(int i = 0; i < cards.length; i++){
            if(cards[i] == null){
                cards[i] = card;
                card.getSprite().setCenter(sprite.getX() + ((sprite.getWidth()/cards.length) * (i+0.5f)), sprite.getY() + sprite.getHeight()/2);
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
        for(Card card : cards){
            if(card != null){
            card.getSprite().draw(spriteBatch);
            }
        }
   }

   public void clear(){
        for(int i = 0; i < cards.length; i++){
            cards[i] = null;
        }
   }


   /*
   Old, Board-extending play area
   public PlayArea(SpriteBatch spriteBatch, Viewport viewport){
        super(spriteBatch, viewport);
        cardHeight = worldHeight/4;
        cardWidth = cardHeight*0.7f;
    }

    public void drawPlayArea(){
        boardArea.setCenter(worldWidth/2, worldHeight/2);
        super.drawBoard();

    }

    */
}
