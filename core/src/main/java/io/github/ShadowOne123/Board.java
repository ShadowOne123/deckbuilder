package io.github.ShadowOne123;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.Viewport;

import java.util.ArrayList;

public abstract class Board {

    protected SpriteBatch spriteBatch;
    protected Sprite card;
    protected Texture cardTexture;
    protected Viewport viewport;
    protected float worldWidth;
    protected float worldHeight;
    protected Sprite boardArea;
    protected ArrayList<Card> cards = new ArrayList<Card>();

    public Board(SpriteBatch spriteBatch, Viewport viewport){
        this.worldHeight = viewport.getWorldHeight();
        this.worldWidth = viewport.getWorldWidth();
        this.viewport = viewport;
        this.spriteBatch = spriteBatch;
        cardTexture = new Texture("card.png");
        boardArea = new Sprite(cardTexture);
    }

    public void addCard(Card card){
        cards.add(card);
    }

    public void resize(Viewport viewport){
        this.viewport = viewport;
        this.worldHeight = viewport.getWorldHeight();
        this.worldWidth = viewport.getWorldWidth();
    }

    public void drawBoard(){
        boardArea.draw(spriteBatch);
        boardArea.setSize(worldWidth*4/5,worldHeight/3);
        //boardArea.setAlpha(0);
        float left = boardArea.getX();
        float centerY = boardArea.getY() + boardArea.getHeight()/2;
        Sprite temp;
        float offset = 0.2f;
        if(!cards.isEmpty()) {
            offset = (boardArea.getWidth() - cards.get(0).getWidth()*0.75f) / cards.size(); //the 0.1 is there for arcane reasons, only matters in edge case when number of cards exceeds like 20
            if(offset > cards.get(0).getWidth() + 0.2f){
                offset = cards.get(0).getWidth() + 0.2f;
            }
            for (int i = 0; i < cards.size(); i++) {
                temp = cards.get(i).getSprite();
                temp.draw(spriteBatch);
                temp.setCenter(left + (temp.getWidth() / 2) + i * offset, centerY);
            }
        }
    }

    public ArrayList<Card> getCards(){
        return cards;
    }

    public void removeCard(Card card){
        cards.remove(card);
    }

    public Sprite getSprite(){
        return boardArea;
    }
}
