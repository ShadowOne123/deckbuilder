package io.github.ShadowOne123;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.viewport.FitViewport;
import io.github.ShadowOne123.Enemies.Enemy;
import io.github.ShadowOne123.Enemies.EnemyData;
import io.github.ShadowOne123.Events.EventBus;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;


/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class Main extends ApplicationAdapter {
    final int HEIGHT = 1000;
    public SpriteBatch spriteBatch;
    public static FitViewport viewport;
    public static TextureAtlas atlas;
    public static Hand hand;
    public static PlayArea playArea;
    Sprite Background;
    public static Player player;
    public static String characterClass;
    public static Deck deck;
    public static DiscardActor discardActor;
    Enemy enemyTest;
    ArrayList<EnemyData> enemies;
    //text
    BitmapFont healthFont;
    FreeTypeFontGenerator healthFontGen;
    FreeTypeFontGenerator.FreeTypeFontParameter healthFontParam;
    public static Stage stage;
    public static Stage uiStage;
    public static Stage menuStage;
    public static String activeStage;
    public static Tooltip tooltip;
    public static SpellbookActor spellbookActor;
    SpellbookButton spellbookButton;

    //Card creation and file reading
    public static HashMap<String, String> cardDictionary = new HashMap<String,String>();

    public static InputController inputController;
    public static CombatController combatController;
    public static EventBus eventBus;

    public HashMap<String, String> decklists;
    private boolean gameInitialized;

    @Override
    public void create() {
        gameInitialized = false;
        FontManager.init();
        eventBus = new EventBus();
        viewport = new FitViewport(HEIGHT * 16f/9f, HEIGHT);
        atlas = new TextureAtlas(Gdx.files.internal("textureAtlas.atlas"));
        spriteBatch = new SpriteBatch();
        Background = new Sprite(atlas.findRegion("steampunkBackground1"));
        Background.setSize(viewport.getWorldWidth(), viewport.getWorldHeight());
        //text
        healthFontGen = FontManager.healthFontGenerator;
        healthFontParam = FontManager.healthFontParameter;
        healthFont = FontManager.healthFont;
        stage = new Stage(viewport);
        uiStage = new Stage(viewport);
        menuStage = new Stage(viewport);
        activeStage = "none";

        //starting decklists:
        decklists = new HashMap<>();
        decklists.put("rogue", "temperance, temperance, butcher, pocket_sand, bite, bite, bite, vial");
        decklists.put("druid", "strike, strike, heal, buckler, rose, magpie, magpie, barbed_wire");
        decklists.put("mage", "magic_missile, magic_missile, hypothermia, blaze, blaze, blaze, lightning_strike, lightning_strike");

        //initGame();
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
    }

    private void draw(){

        viewport.apply();
        spriteBatch.setProjectionMatrix(viewport.getCamera().combined);
        spriteBatch.begin();

        Background.draw(spriteBatch);

        if(activeStage.equals("main")) {
            playArea.drawPlayArea();
            player.draw();
            enemyTest.draw();
            hand.drawHand();
            deck.drawDeck();
            discardActor.draw();
            stage.act();
            spriteBatch.end();
        }
        else if(activeStage.equals("menu")) {
            spriteBatch.end();
            menuStage.draw();
            menuStage.act();
        }
        else{
            spriteBatch.end();
        }

        uiStage.draw();
        uiStage.act();
    }

    private void input(){
        if(!gameInitialized) {
            if (Gdx.input.isKeyJustPressed(Input.Keys.D)) {
                characterClass = "druid";
                initGame();
            }
            else if(Gdx.input.isKeyJustPressed(Input.Keys.R)){
                characterClass = "rogue";
                initGame();
            }
            else if(Gdx.input.isKeyJustPressed(Input.Keys.M)){
                characterClass = "mage";
                initGame();
            }
        }
    }

    public void initGame(){
        populateCardDictionary("cardDictionary.txt");
        SpellResolver.populateSpellbook("/spellbook.txt");

        hand = new Hand(spriteBatch, viewport);
        playArea = new PlayArea(3, spriteBatch, viewport);
        player = new Player(spriteBatch, viewport, healthFontGen, healthFontParam, viewport.getWorldWidth()/5.5f, viewport.getWorldHeight()/2.5f, "bucket");
        Json json = new Json();
        enemies = json.fromJson(ArrayList.class, EnemyData.class, Gdx.files.internal("enemies.json"));
        enemyTest = new Enemy(enemies.get(0),spriteBatch, viewport, healthFontGen, healthFontParam, 5.5f*viewport.getWorldWidth()/7,
            viewport.getWorldHeight()/2.5f, "fireElemental");
        tooltip = new Tooltip(genLabel());
        tooltip.maxWidth(hand.cardWidth * 2);
        tooltip.width(hand.cardWidth * 2);
        uiStage.addActor(tooltip);
        spellbookActor = new SpellbookActor();
        spellbookButton = new SpellbookButton(spellbookActor);
        stage.addActor(enemyTest);
        stage.addActor(player);
        ArrayList<Enemy> activeEnemies = new ArrayList<Enemy>();
        activeEnemies.add(enemyTest);
        deck = new Deck(spriteBatch);
        discardActor = new DiscardActor(deck, hand, spriteBatch);
        combatController = new CombatController(activeEnemies);
        stage.addActor(combatController);
        enemyTest.addController(combatController);
        player.addController(combatController);
        inputController = new InputController();
        InputController.setInputModeBattle();
        activeStage = "main";

        String[] decklist = decklists.get(characterClass).split(", ");
        for(String card : decklist){
            deck.addCard(new Card(card, stage));
        }
        deck.shuffle();
        for(int i = 0; i < 4; i++){
            deck.draw(hand);
        }
        gameInitialized = true;
    }


    public void populateCardDictionary(String filepath){

        try(BufferedReader reader = new BufferedReader(Gdx.files.internal(filepath).reader());){
            String line;

            while ((line = reader.readLine()) != null) {
                String[] attributes = line.split(":");

                // Make sure the line contains exactly one key-value pair
                if (attributes.length == 3) {
                    String cardClass = attributes[0].trim();
                    if (cardClass.equals(characterClass)) {
                        String key = attributes[1].trim();
                        String value = attributes[2].trim();
                        cardDictionary.put(key, value);
                    }

                } else {
                    System.out.println("Skipping invalid entry: " + line);
                }
            }
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }

    private Label genLabel(){
        FreeTypeFontGenerator.FreeTypeFontParameter param = new FreeTypeFontGenerator.FreeTypeFontParameter();
        FreeTypeFontGenerator gen = new FreeTypeFontGenerator(Gdx.files.internal("FreeSerif.ttf"));
        param.size = 64;
        BitmapFont font;
        font = gen.generateFont(param);
        font.getData().setScale(0.4f);
        gen.dispose();

        Pixmap pixmap = new Pixmap(1, 1, Pixmap.Format.RGBA8888);

        pixmap.setColor(0.2f, 0.2f, 0.2f, 0.95f);

        pixmap.fill();

        Texture tex = new Texture(pixmap);

        tex.setFilter(Texture.TextureFilter.Nearest, Texture.TextureFilter.Nearest);

        Drawable bg = new TextureRegionDrawable(new TextureRegion(tex));

        pixmap.dispose();

        Skin skin = new Skin();

        skin.add("default-font", font);

        skin.add("default-background", bg);

        Label.LabelStyle labelStyle = new Label.LabelStyle();

        labelStyle.font = font;

        labelStyle.background = bg;

        skin.add("default", labelStyle);

        Label label = new Label("", skin);
        label.setWrap(true);
        return label;
    }

}
