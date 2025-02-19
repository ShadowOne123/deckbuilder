package io.github.ShadowOne123;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;


/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class Main extends ApplicationAdapter {
    final int HEIGHT = 20;
    public SpriteBatch spriteBatch;
    FitViewport viewport;
    Texture bucketTexture;
    Sprite bucketSprite;
    private SpriteBatch batch;
    private Texture image;
    Sprite cardArea;
    Color background;
    Hand hand;
    PlayArea playArea;

    @Override
    public void create() {
        viewport = new FitViewport(HEIGHT * 16/9, HEIGHT);
        background = Color.BLACK;
        bucketTexture = new Texture("bucket.png");
        cardArea = new Sprite(bucketTexture);
        cardArea.setSize(10,5);
        cardArea.setCenter(10,10);
        bucketSprite = new Sprite(bucketTexture); // Initialize the sprite based on the texture
        bucketSprite.setSize(1, 1); // Define the size of the sprite
        spriteBatch = new SpriteBatch();
        batch = new SpriteBatch();
        image = new Texture("libgdx.png");
        hand = new Hand(spriteBatch, viewport);
        playArea = new PlayArea(spriteBatch, viewport);
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
        hand.resize(viewport);
        playArea.resize(viewport);
    }

    private void draw(){
        ScreenUtils.clear(background);

        viewport.apply();
        spriteBatch.setProjectionMatrix(viewport.getCamera().combined);
        spriteBatch.begin();

        bucketSprite.draw(spriteBatch);
        //cardArea.draw(spriteBatch);
        hand.drawHand();
        playArea.drawPlayArea();
        spriteBatch.end();
    }

    private void input(){
        float delta = Gdx.graphics.getDeltaTime();
        if(Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)){
            Vector2 clickCoords = new Vector2(Gdx.input.getX(), Gdx.input.getY());
            clickCoords = viewport.unproject(clickCoords);
            //unproject converts between click(absolute coords, measured in pixels) to world coords measured in viewport units
            for(Card card : hand.getCards()){
                if(card.getSprite().getBoundingRectangle().contains(clickCoords)){
                    Card temp = card;
                    hand.removeCard(card);
                    playArea.addCard(temp);
                    break;

                }
            }
            for(Card card : playArea.getCards()){
                if(card.getSprite().getBoundingRectangle().contains(clickCoords)){
                    if(playArea.getSprite().getBoundingRectangle().contains(clickCoords)){
                        Card temp = card;
                        playArea.removeCard(card);
                        hand.addCard(temp);
                        break;
                    }
                }
            }
        }
        else if(Gdx.input.isButtonPressed(Input.Buttons.RIGHT)){
            bucketSprite.translateX(-2f * delta);
        }
        else if(Gdx.input.isKeyJustPressed(Input.Keys.A)){
            hand.addCard(new Card(viewport));
        }
        else if(Gdx.input.isKeyJustPressed(Input.Keys.R)){
            hand.getCards().clear();
            playArea.getCards().clear();
        }

    }


}
