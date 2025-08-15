package io.github.ShadowOne123;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.utils.viewport.Viewport;

import java.util.ArrayList;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.moveTo;

public abstract class Board extends Actor {

    protected SpriteBatch spriteBatch;
    protected Texture cardTexture;
    protected Viewport viewport;
    protected float worldWidth;
    protected float worldHeight;
    protected Sprite boardArea;
    protected ArrayList<Card> cards = new ArrayList<Card>();
    protected float cardHeight, cardWidth;

    public Board(SpriteBatch spriteBatch, Viewport viewport){
        this.worldHeight = viewport.getWorldHeight();
        this.worldWidth = viewport.getWorldWidth();
        this.viewport = viewport;
        this.spriteBatch = spriteBatch;
        cardTexture = new Texture("king.png");
        boardArea = new Sprite(cardTexture);
        boardArea.setSize(worldWidth*0.40f,worldHeight/4);
        cardHeight = worldHeight/4.5f;
        cardWidth = cardHeight*0.65f;
    }

    public void addCard(Card card){
        card.setSize(cardWidth, cardHeight);
        card.setCenter(worldWidth/15, cardHeight/2 + worldHeight/25);
        cards.add(card);
        reposition();
        card.setY(cardHeight/2 + worldHeight/25 + worldHeight/4);
        card.addAction(Actions.moveBy(0,-worldHeight/4, 0.2f));
    }

    public void addCard(Card card, int index){
        card.setSize(cardWidth, cardHeight);
        card.setCenter(0, cardHeight/2 + worldHeight/25);
        cards.add(index, card);
        reposition();
    }

    //resize method. Idk if it actually matters, but we'll keep it here just in case
    public void resize(Viewport viewport){
        this.viewport = viewport;
        this.worldHeight = viewport.getWorldHeight();
        this.worldWidth = viewport.getWorldWidth();
    }

    public void drawBoard(){
        boardArea.setSize(worldWidth*0.40f,worldHeight/4);
        if(!cards.isEmpty()){
            for(Card card : cards){
                card.getSprite().setCenter(card.getX(), card.getY());
                card.getSprite().draw(spriteBatch);
            }
        }
    }


    private void reposition(){
        float step;
        if(cards.isEmpty()){
            return;
        }
        if(cards.size() * cardWidth < boardArea.getWidth()){
            step = cardWidth;
        }
        else{
            step = (boardArea.getWidth() - cardWidth)/(cards.size()-1);
        }
        for(int i = 0; i < cards.size(); i++){
            cards.get(i).setLeft(boardArea.getX() + (i * step));
        }
    }

    //gets full list of cards in board
    public ArrayList<Card> getCards(){
        return cards;
    }

    //removes card from list of cards held in area
    public void removeCard(Card card){
        cards.remove(card);
    }

    public void removeCard(int index){
        cards.remove(index);
        reposition();
    }

    public Sprite getSprite(){
        return boardArea;
    }
}
