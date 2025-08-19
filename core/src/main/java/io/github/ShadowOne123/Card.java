package io.github.ShadowOne123;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.moveTo;
import static io.github.ShadowOne123.Main.cardDictionary;

public class Card extends Actor {
    private final Sprite sprite;
    private final Effect effect;
    private final String name;


    public Card(String id, Stage stage){
        addListener(new CardInputListener(this));
        stage.addActor(this);

        if(Gdx.files.internal(id + ".png").exists()) {
            sprite = new Sprite(new Texture(Gdx.files.internal(id + ".png")));
        }
        else{
            sprite = new Sprite(new Texture(Gdx.files.internal("bucket.png")));
        }
        sprite.setCenter(-10,-10);
        String cardDescription = cardDictionary.get(id);
        //splits it into the name and each individual action
        String[] splitDesc = cardDescription.split(";");
        this.name = id;
        this.effect = new Effect(splitDesc);
    }

    public Sprite getSprite(){
        return sprite;
    }

    public Effect getEffect() {
        return effect;
    }

    public String toString(){
        return "Name: " + name + "\n effect: " + effect.toString();
    }

    public String getName(){
        return name;
    }

}
