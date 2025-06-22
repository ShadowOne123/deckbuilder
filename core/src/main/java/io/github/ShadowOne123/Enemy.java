package io.github.ShadowOne123;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.utils.viewport.Viewport;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.*;

public class Enemy extends Creature{
    public boolean selected;

    public Enemy(SpriteBatch spriteBatch, Viewport viewport, BitmapFont text, float centerX, float centerY, Texture texture, float width, float height){
        super(spriteBatch, viewport, text, centerX, centerY, texture);
        sprite.setSize(width, height);
        hp = 10;
        maxHP = hp;



        this.addListener(new InputListener() {
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                //on left click
                if(button == 0){
                    if(!selected){
                        text.setColor(Color.RED);
                    }
                    else{
                        unselect();
                    }
                    selected = !selected;
                    System.out.println(selected);
                }
                if(button == 1 && selected){
                    hp -= 1;
                }
                return true;
            }
        });
    }

    public boolean isSelected(){
        return selected;
    }

    public void unselect(){
        this.selected = false;
        text.setColor(Color.WHITE);
    }


    public void attackAnim(Player player){
        float originX = getX();
        addAction(sequence(
            moveTo(getX() + 10, getY(), 0.06f),
            delay(0.1f),
            moveTo(getX() - 20, getY(), 0.1f),
            moveTo(originX, getY(), 0.3f),
            run(attack(player)),
            delay(0.1f),
            run(takeStatuses(this))
        ));
    }

    private Runnable attack(Player player){
        return new Runnable() {
            @Override
            public void run() {
                player.takeDamage(5);
            }
        };
    }

    protected Runnable takeStatuses(Creature target){
        return new Runnable() {
            @Override
            public void run() {
                for(int i = 0; i < statuses.size(); i++){
                    statuses.get(i).apply(target);
                    if(statuses.get(i).getIntensity() == 0){
                        statuses.remove(i);
                        i--;
                    }
                }
            }
        };
    }
}
