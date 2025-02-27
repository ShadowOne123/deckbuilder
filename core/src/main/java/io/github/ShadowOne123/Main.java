package io.github.ShadowOne123;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;


/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class Main extends ApplicationAdapter {
    final int HEIGHT = 200;
    public SpriteBatch spriteBatch;
    FitViewport viewport;
    Texture bucketTexture;
    Sprite bucketSprite;
    Sprite cardArea;
    Color background;
    Hand hand;
    PlayArea playArea;
    Texture backgroundTexture;
    Texture backgroundTexture2;
    Texture backgroundTexture3;
    Sprite Background;
    Player player;
    Enemy enemyTest;
    Texture enemyTexture;
    //text
    BitmapFont healthFont;
    FreeTypeFontGenerator healthFontGenerator;
    FreeTypeFontGenerator.FreeTypeFontParameter healthFontParameter;

    @Override
    public void create() {
        viewport = new FitViewport(HEIGHT * 16f/9f, HEIGHT);
        background = Color.BLACK;
        bucketTexture = new Texture("bucket.png");
        cardArea = new Sprite(bucketTexture);
        cardArea.setSize(10,5);
        cardArea.setCenter(10,10);
        bucketSprite = new Sprite(bucketTexture); // Initialize the sprite based on the texture
        bucketSprite.setSize(1, 1); // Define the size of the sprite
        spriteBatch = new SpriteBatch();
        hand = new Hand(spriteBatch, viewport);
        playArea = new PlayArea(spriteBatch, viewport);
        backgroundTexture = new Texture("GothicCastleBackground3.png");
        backgroundTexture2 = new Texture("MagicalForestBackground1.png");
        backgroundTexture3 = new Texture("steampunkBackground1.png");
        Background = new Sprite(backgroundTexture3);
        //text
        healthFontGenerator = new FreeTypeFontGenerator(Gdx.files.internal("FreeSerif.ttf"));
        healthFontParameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        healthFontParameter.size = 128;
        healthFontParameter.genMipMaps = true;
        healthFont = healthFontGenerator.generateFont(healthFontParameter);
        player = new Player(spriteBatch, viewport, healthFont, viewport.getWorldWidth()/7, viewport.getWorldHeight()/3, bucketTexture);
        enemyTexture = new Texture("fireElemental.png");
        enemyTest = new Enemy(spriteBatch, viewport, healthFont, 5.5f*viewport.getWorldWidth()/7,
            viewport.getWorldHeight()/3, enemyTexture, viewport.getWorldWidth()/10, viewport.getWorldHeight()/4);
    }

    @Override
    public void render() {
        input();
        draw();
    }

    @Override
    public void dispose() {
        spriteBatch.dispose();
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

        Background.draw(spriteBatch);
        Background.setSize(viewport.getWorldWidth(), viewport.getWorldHeight());
        bucketSprite.draw(spriteBatch);
        hand.drawHand();
        playArea.drawPlayArea();
        player.draw();
        enemyTest.draw();

        spriteBatch.end();
    }

    private void input(){
        float delta = Gdx.graphics.getDeltaTime();
        if(Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)){
            Vector2 clickCoords = new Vector2(Gdx.input.getX(), Gdx.input.getY());
            clickCoords = viewport.unproject(clickCoords);
            //unproject converts between click(absolute coords, measured in pixels) to world coords measured in viewport units

            //checks for clicks in hand, if there are any moves clicked card to played cards
            for(int i = hand.getCards().size()-1; i >= 0; i--){
                Card temp = hand.getCards().get(i);
                if(temp.getSprite().getBoundingRectangle().contains(clickCoords)) {
                    hand.removeCard(i);
                    playArea.addCard(temp);
                    break;
                }
            }
            //move cards to hand on-click from played area
            for(int i = playArea.getCards().size()-1; i >= 0; i--){
                Card temp = playArea.getCards().get(i);
                if(playArea.getSprite().getBoundingRectangle().contains(clickCoords)) {
                    if (temp.getSprite().getBoundingRectangle().contains(clickCoords)) {
                        playArea.removeCard(i);
                        hand.addCard(temp);
                        break;
                    }
                }
            }

        }

        else if(Gdx.input.isKeyJustPressed(Input.Keys.A)){
            hand.addCard(new Card(hand.findTexture("temperance")));
        }
        else if(Gdx.input.isKeyJustPressed(Input.Keys.S)){
            hand.addCard(new Card(hand.findTexture("king")));
        }
        else if(Gdx.input.isKeyJustPressed(Input.Keys.ENTER)){

        }
        //reset board
        else if(Gdx.input.isKeyJustPressed(Input.Keys.R)){
            hand.getCards().clear();
            playArea.getCards().clear();
        }

    }


}
