package io.github.ShadowOne123;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.Viewport;

import java.util.ArrayList;

public class Hand extends Board{

    protected Card heldCard;

    public Hand(SpriteBatch spriteBatch, Viewport viewport){
        super(spriteBatch, viewport);
        drawHand();
        heldCard = null;
    }

    public void drawHand(){
        boardArea.setCenter(worldWidth/2, boardArea.getHeight()/2.5f);
        super.drawBoard();
        if(heldCard != null) {
            Vector2 clickCoords = new Vector2(Gdx.input.getX(), Gdx.input.getY());
            clickCoords = viewport.unproject(clickCoords);
            heldCard.getSprite().setCenter(clickCoords.x, clickCoords.y);
            heldCard.getSprite().draw(spriteBatch);
        }
    }

    public void clear(){
        cards.clear();
        heldCard = null;
    }






}
