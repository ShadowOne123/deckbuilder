package io.github.ShadowOne123;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.moveTo;
import static io.github.ShadowOne123.Main.atlas;
import static io.github.ShadowOne123.Main.cardDictionary;

public class Card extends Actor {
    private final Sprite sprite;
    private final Effect effect;
    private final String name;


    public Card(String id, Stage stage){
        addListener(new CardInputListener(this));
        stage.addActor(this);

        if(atlas.findRegion("cards/" + id) != null) {
            sprite = new Sprite(atlas.findRegion("cards/" + id));
        }
        else{
            sprite = new Sprite(atlas.findRegion("bucket"));
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
        String Temp_name = name.replace('_',' ');
        return Temp_name + "\n" + effect.toString();
    }

    public String getName(){
        return name;
    }

}
