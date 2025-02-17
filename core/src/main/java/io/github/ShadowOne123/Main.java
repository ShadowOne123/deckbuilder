package io.github.ShadowOne123;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class Main extends ApplicationAdapter {
    SpriteBatch spriteBatch;
    FitViewport viewport;
    Texture bucketTexture;
    Sprite bucketSprite;
    private SpriteBatch batch;
    private Texture image;

    @Override
    public void create() {
        bucketTexture = new Texture("bucket.png");
        bucketSprite = new Sprite(bucketTexture); // Initialize the sprite based on the texture
        bucketSprite.setSize(1, 1); // Define the size of the sprite
        spriteBatch = new SpriteBatch();
        viewport = new FitViewport(8, 5);
        batch = new SpriteBatch();
        image = new Texture("libgdx.png");
    }

    @Override
    public void render() {

        input();
        draw();
    }

    @Override
    public void dispose() {
        batch.dispose();
        image.dispose();
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true); // true centers the camera
    }

    private void draw(){
        ScreenUtils.clear(Color.BLACK);
        viewport.apply();
        spriteBatch.setProjectionMatrix(viewport.getCamera().combined);
        spriteBatch.begin();
        float worldWidth = viewport.getWorldWidth();
        float worldHeight = viewport.getWorldHeight();

        bucketSprite.draw(spriteBatch); // Sprites have their own draw method
        spriteBatch.end();
    }

    private void input(){
        float delta = Gdx.graphics.getDeltaTime();
        if(Gdx.input.isButtonPressed(Input.Buttons.LEFT)){
            bucketSprite.translateX(2f * delta);
        }
        else if(Gdx.input.isButtonPressed(Input.Buttons.RIGHT)){
            bucketSprite.translateX(-2f * delta);
        }
    }


}
