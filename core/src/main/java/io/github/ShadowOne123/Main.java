package io.github.ShadowOne123;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.FitViewport;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;


/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class Main extends ApplicationAdapter {
    final int HEIGHT = 200;
    public SpriteBatch spriteBatch;
    FitViewport viewport;
    Texture bucketTexture;
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
    Stage stage;

    //Card creation and file reading
    public static HashMap<String, String> cardDictionary = new HashMap<String,String>();

    CombatController combatController;

    @Override
    public void create() {
        //populate card dictionary
        populateCardDictionary("/cardDictionary.txt");
        //init fonts
        FontManager.init();

        viewport = new FitViewport(HEIGHT * 16f/9f, HEIGHT);
        bucketTexture = new Texture("bucket.png");
        spriteBatch = new SpriteBatch();
        hand = new Hand(spriteBatch, viewport);
        playArea = new PlayArea(spriteBatch, viewport);
        backgroundTexture = new Texture("GothicCastleBackground3.png");
        backgroundTexture2 = new Texture("MagicalForestBackground1.png");
        backgroundTexture3 = new Texture("steampunkBackground1.png");
        Background = new Sprite(backgroundTexture3);
        //text
        healthFont = FontManager.healthFont;
        player = new Player(spriteBatch, viewport, healthFont, viewport.getWorldWidth()/7, viewport.getWorldHeight()/3, bucketTexture);
        enemyTexture = new Texture("fireElemental.png");
        enemyTest = new Enemy(spriteBatch, viewport, healthFont, 5.5f*viewport.getWorldWidth()/7,
            viewport.getWorldHeight()/3, enemyTexture,viewport.getWorldWidth()/10, viewport.getWorldHeight()/4);

        stage = new Stage(viewport);
        Gdx.input.setInputProcessor(stage);
        stage.addActor(enemyTest);
        stage.addActor(player);
        ArrayList<Enemy> enemies = new ArrayList<Enemy>();
        enemies.add(enemyTest);
        combatController = new CombatController(player, playArea, enemies);
        inputController inpt = new inputController();
        inpt.setInputModeBattle(hand, playArea, combatController, viewport);
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

        viewport.apply();
        spriteBatch.setProjectionMatrix(viewport.getCamera().combined);
        spriteBatch.begin();

        Background.draw(spriteBatch);
        Background.setSize(viewport.getWorldWidth(), viewport.getWorldHeight());
        playArea.drawPlayArea();
        player.draw();
        enemyTest.draw();
        healthFont.draw(spriteBatch, combatController.getTurn(), viewport.getWorldWidth()/2.7f, viewport.getWorldHeight()/1.1f);
        hand.drawHand();
        stage.act();
        spriteBatch.end();
    }

    private void input(){
        //reset board
        if(Gdx.input.isKeyJustPressed(Input.Keys.R)){
            hand.getCards().clear();
            playArea.getCards().clear();
            enemyTest.setHealth(enemyTest.getMaxHP());
            player.setHealth(player.getMaxHP());
        }

    }


    public void populateCardDictionary(String filepath){

        try(InputStream inputStream = getClass().getResourceAsStream(filepath);){
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            String line;

            while ((line = reader.readLine()) != null) {
                String[] keyValue = line.split(":");

                // Make sure the line contains exactly one key-value pair
                if (keyValue.length == 2) {
                    String key = keyValue[0].trim();
                    String value = keyValue[1].trim();

                    cardDictionary.put(key, value);
                } else {
                    System.out.println("Skipping invalid entry: " + line);
                }
            }
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }

}
