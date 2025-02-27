package io.github.ShadowOne123;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.Viewport;


public class PlayArea extends Board{

    public PlayArea(SpriteBatch spriteBatch, Viewport viewport){
        super(spriteBatch, viewport);
        cardHeight = worldHeight/4;
        cardWidth = cardHeight*0.7f;
    }

    public void drawPlayArea(){
        boardArea.setCenter(worldWidth/2, worldHeight/2);
        super.drawBoard();

    }
}
