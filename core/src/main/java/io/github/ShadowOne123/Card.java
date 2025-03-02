package io.github.ShadowOne123;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import static io.github.ShadowOne123.Main.cardDictionary;

public class Card {
    private Sprite sprite;
    private Effect effect;
    private String name;

    public Card(Texture texture, Effect effect){
        sprite = new Sprite(texture);
        sprite.setCenter(-10,-10);
    }

    public Card(Texture texture, int id){
        sprite = new Sprite(texture);
        sprite.setCenter(-10,-10);
        String cardDescription = cardDictionary.get(id);
        //splits it into the name and each individual action
        String[] splitDesc = cardDescription.split(";");
        this.name = splitDesc[0];
        this.effect = new Effect(splitDesc);
    }

    public Sprite getSprite(){
        return sprite;
    }

    public void setSize(float width, float height){
        sprite.setSize(width, height);
    }

    public Effect getEffect() {
        return effect;
    }

    public String toString(){
        return "Name: " + name + "\n effect: " + effect.toString();
    }
}
