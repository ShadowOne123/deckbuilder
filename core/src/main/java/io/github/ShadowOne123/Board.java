package io.github.ShadowOne123;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.viewport.Viewport;

import java.util.ArrayList;
import java.util.Dictionary;
import java.util.Hashtable;

public abstract class Board extends Actor {

    protected SpriteBatch spriteBatch;
    protected Texture cardTexture;
    protected Viewport viewport;
    protected float worldWidth;
    protected float worldHeight;
    protected Sprite boardArea;
    protected ArrayList<Card> cards = new ArrayList<Card>();
    protected Texture temperanceTexture;
    protected Dictionary<String, Texture> textureDict = new Hashtable<>();
    protected float cardHeight, cardWidth;

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
        cardHeight = worldHeight/5;
        cardWidth = cardHeight*0.7f;
    }

    public void addCard(Card card){
        card.setSize(cardWidth, cardHeight);
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
        boardArea.setSize(worldWidth*0.40f,worldHeight/4);
        float left = boardArea.getX();
        float centerY = boardArea.getY() + boardArea.getHeight()/2;
        Sprite temp;
        float offset = 0.2f;
        if(!cards.isEmpty()) {
            //formula to make cards stay in board when more are added. Forces overlap
            offset = (boardArea.getWidth() - cardWidth*0.75f) / cards.size();
            //regulates maximum distance between cards
            if(offset > cardWidth + 0.2f){
                offset = cardWidth + 0.2f;
            }
            //draws each card
            for (int i = 0; i < cards.size(); i++) {
                temp = cards.get(i).getSprite();
                temp.setCenter(left + (temp.getWidth() / 2) + i * offset, centerY);
                temp.draw(spriteBatch);
            }
        }
    }


    public void draw(){

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
