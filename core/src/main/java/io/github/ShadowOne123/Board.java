package io.github.ShadowOne123;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.Viewport;

import java.util.ArrayList;
import java.util.Dictionary;
import java.util.Hashtable;

public abstract class Board {

    protected SpriteBatch spriteBatch;
    protected Sprite card;
    protected Texture cardTexture;
    protected Viewport viewport;
    protected float worldWidth;
    protected float worldHeight;
    protected Sprite boardArea;
    protected ArrayList<Card> cards = new ArrayList<Card>();
    protected Texture temperanceTexture;
    protected Dictionary<String, Texture> textureDict = new Hashtable<>();

    public Board(SpriteBatch spriteBatch, Viewport viewport){
        this.worldHeight = viewport.getWorldHeight();
        this.worldWidth = viewport.getWorldWidth();
        this.viewport = viewport;
        this.spriteBatch = spriteBatch;
        cardTexture = new Texture("card.png");
        temperanceTexture = new Texture("Temperance.png");
        boardArea = new Sprite(cardTexture);
        textureDict.put("temperance", temperanceTexture);
        textureDict.put("king", cardTexture);
    }

    public void addCard(Card card){
        cards.add(card);
    }

    //resize method. Idk if it actually matters, but we'll keep it here just in case
    public void resize(Viewport viewport){
        this.viewport = viewport;
        this.worldHeight = viewport.getWorldHeight();
        this.worldWidth = viewport.getWorldWidth();
    }

    //render method, called by child classes to make their own render methods
    public void drawBoard(){
        boardArea.draw(spriteBatch);
        boardArea.setSize(worldWidth*2.5f/5,worldHeight/3);
        boardArea.setAlpha(0);
        float left = boardArea.getX();
        float centerY = boardArea.getY() + boardArea.getHeight()/2;
        Sprite temp;
        float offset = 0.2f;
        if(!cards.isEmpty()) {
            //formula to make cards stay in board when more are added. Forces overlap
            offset = (boardArea.getWidth() - cards.get(0).getWidth()*0.75f) / cards.size();
            //regulates maximum distance between cards
            if(offset > cards.get(0).getWidth() + 0.2f){
                offset = cards.get(0).getWidth() + 0.2f;
            }
            //draws each card
            for (int i = 0; i < cards.size(); i++) {
                temp = cards.get(i).getSprite();
                temp.draw(spriteBatch);
                temp.setCenter(left + (temp.getWidth() / 2) + i * offset, centerY);
            }
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
    }

    public Sprite getSprite(){
        return boardArea;
    }

    //fetches texture with specified name from texture dictionary. Used for creating new cards
    public Texture findTexture(String textureName){
        return textureDict.get(textureName);
    }
}
