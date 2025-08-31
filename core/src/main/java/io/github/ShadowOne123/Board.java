package io.github.ShadowOne123;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.utils.viewport.Viewport;

import java.util.ArrayList;

import static io.github.ShadowOne123.Main.atlas;

public abstract class Board extends Actor {

    protected SpriteBatch spriteBatch;
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
        boardArea = new Sprite(atlas.findRegion("cards/king"));
        boardArea.setSize(worldWidth*0.40f,worldHeight/4);
        cardHeight = worldHeight/4.5f;
        cardWidth = cardHeight*0.65f;
    }

    public void addCard(Card card){
        card.setSize(cardWidth, cardHeight);
        card.getSprite().setSize(cardWidth, cardHeight);
        card.setPosition(worldWidth/15, worldHeight/25);
        cards.add(card);
        reposition();
        card.setY(worldHeight/25 + worldHeight/4);
        card.addAction(Actions.moveBy(0,-worldHeight/4, 0.2f));
    }

    public void addCard(Card card, int index){
        card.setPosition(0, worldHeight/25);
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
        Color color = getColor();
        boardArea.setSize(worldWidth*0.40f,worldHeight/4);
        if(!cards.isEmpty()){
            for(Card card : cards){
                card.getSprite().setColor(color);
                card.getSprite().setPosition(card.getX(), card.getY());
                card.getSprite().draw(spriteBatch);
            }
        }
    }


    private void reposition(){
        float step;
        Card temp;
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
            temp = cards.get(i);
            temp.setX(boardArea.getX() + (i * step));
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
