package io.github.ShadowOne123;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Container;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.graphics.Pixmap;

import com.badlogic.gdx.graphics.Texture;

import com.badlogic.gdx.scenes.scene2d.utils.Drawable;

import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

import com.badlogic.gdx.graphics.Texture.TextureFilter;


public class Tooltip extends Container<Label> {

    public Tooltip(Label label) {
        super(label);
    }

    public void updateText(String text) {

        getActor().setText(text);

    }


}
