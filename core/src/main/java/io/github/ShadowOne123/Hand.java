package io.github.ShadowOne123;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.Viewport;

import java.util.ArrayList;

public class Hand extends Board{

    public Hand(SpriteBatch spriteBatch, Viewport viewport){
        super(spriteBatch, viewport);
        cards.add(new Card(viewport));
        cards.add(new Card(viewport));
        cards.add(new Card(viewport));
    }

    public void drawBucket(Sprite bucketSprite){
        bucketSprite.draw(spriteBatch);
    }

    public void drawHand(){
        boardArea.setCenter(worldWidth/2, boardArea.getHeight()/2);
        super.drawBoard();
    }



}
