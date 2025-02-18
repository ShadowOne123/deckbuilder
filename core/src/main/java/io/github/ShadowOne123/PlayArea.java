package io.github.ShadowOne123;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.Viewport;


public class PlayArea extends Board{

    public PlayArea(SpriteBatch spriteBatch, Viewport viewport){
        super(spriteBatch, viewport);

    }


    public void drawPlayArea(){
        boardArea.setCenter(worldWidth/2, worldHeight/2);
        boardArea.setAlpha(0);
        super.drawBoard();

    }
}
