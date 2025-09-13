package io.github.ShadowOne123;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import io.github.ShadowOne123.Enemies.Enemy;
import io.github.ShadowOne123.Events.DamageEvent;
import io.github.ShadowOne123.Events.DamageTakenEvent;
import io.github.ShadowOne123.Events.GameEventListener;
import io.github.ShadowOne123.Events.HealEvent;
import io.github.ShadowOne123.Statuses.Status;

import java.util.ArrayList;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.*;
import static io.github.ShadowOne123.Main.eventBus;
import static io.github.ShadowOne123.Main.viewport;

public class CombatController extends Actor {

    private ArrayList<Enemy> enemies;
    private ArrayList<Creature> targets;
    private Player player;
    private PlayArea playArea;
    public enum Turn {ENEMY, PLAYER, LOADING_PLAYER, LOADING_ENEMY}
    private Turn turn = Turn.PLAYER;
    private int maxTargets;
    private int currentTargets;
    private int enemyTurnProgress;
    private Deck deck;
    private Hand hand;

    public CombatController(Player player, PlayArea playArea, Hand hand, Deck deck, ArrayList<Enemy> enemies){
        this.enemies = enemies;
        this.playArea = playArea;
        this.hand = hand;
        this.deck = deck;
        this.player = player;
        this.targets = new ArrayList<Creature>();
        this.maxTargets = 1;
        this.currentTargets = 0;
        enemyTurnProgress = 0;

        //basic combat event listener registration
        GameEventListener<DamageEvent> damageListener = event -> {
            int finalDmg = event.amount;
            Status block = event.target.searchStatuses("block");
            Status thorns = event.target.searchStatuses("thorns");
            if(block != null){
                System.out.println("block found!");
                if(block.getIntensity() <= event.amount){
                    finalDmg -= block.getIntensity();
                    block.addIntensity(-1 * block.getIntensity());
                    if(block.getIntensity() == 0){
                        event.target.removeStatus("block");
                    }
                }
                else{
                    block.addIntensity(-1 * finalDmg);
                    finalDmg = 0;
                }
            }
            if(thorns != null && event.source != null){
                event.source.takeDamage(thorns.getIntensity());
                thorns.decay();
                eventBus.emit(new DamageTakenEvent(event.source, thorns.getIntensity(), DamageType.SLASHING));
            }
            if(finalDmg < 0){
                finalDmg = 0;
            }
            event.target.takeDamage(finalDmg);
            eventBus.emit(new DamageTakenEvent(event.target, finalDmg, event.damageType));
        };
        eventBus.register(DamageEvent.class, damageListener, 10);
        GameEventListener<HealEvent> healingListener = event -> {
            event.target.getHealed(event.amount);
        };
        eventBus.register(HealEvent.class, healingListener, 10);


    }

    public void resolveTurn(){
        if(turn == Turn.PLAYER){
            resolvePlayerTurn();
        }
        else if (turn == Turn.ENEMY){
            resolveEnemyTurn();
        }
        //if the turn is loading just don't do anything
    }

    //called when "enter" is pressed, ie when the spell is cast
    public boolean resolvePlayerTurn(){
        turn = Turn.LOADING_PLAYER;
        //check for no cards played
        if(playArea.getCards().isEmpty()){
            System.out.println("No cards played!");
            return false;
        }
        //check for no targets
        else if (targets.isEmpty()){
            System.out.println("No targets!");
            return false;
        }
        //build spell
        Card tempCard;
        Effect effect;
        if((tempCard = SpellResolver.checkForSpell(playArea.getCards())) != null){
            playArea.spell = tempCard;
            tempCard.setSize(hand.cardWidth*2, hand.cardHeight*2);
            tempCard.getSprite().setSize(hand.cardWidth*2, hand.cardHeight*2);
            tempCard.setPosition(-1 * viewport.getWorldWidth(), viewport.getWorldHeight()/2);
            tempCard.addAction(sequence(
                fadeOut(0),
                delay(0.6f),
                moveTo(viewport.getWorldWidth()/2.4f, viewport.getWorldHeight()/2.6f, 0),
                fadeIn(0.8f),
                delay(1.2f),
                fadeOut(0.2f)
            ));
            runSpellAnim(tempCard.getEffect(), 2.8f);
        }
        else{
            effect = SpellResolver.buildEffect(playArea.getCards());
            runSpellAnim(effect, 0.6f);
        }

        return true;
    }

    private Runnable resolvePlayerTurnHelper(Effect effect){
        return new Runnable() {
            @Override
            public void run() {
                //apply to targets
                for (Creature target : targets) {
                    effect.apply(target);
                }
                //discard everything from hand and play area
                for(Card card : playArea.getCards()){
                    deck.addToDiscard(card);
                    card.addAction(fadeIn(0.1f));
                }
                playArea.clear();
                for(Card card : hand.getCards()){
                    deck.addToDiscard(card);
                }
                deck.addToDiscard(hand.heldCard);
                hand.clear();
                unselectAll();
                targets.clear();
            }
        };
    }

    private Runnable fadeCards(){
        return new Runnable() {
            @Override
            public void run() {
                for (Card card : playArea.getCards()){
                    card.addAction(fadeOut(0.5f));
                }
            }
        };
    }

    //depending on whether a spell was activated or not changes the delay between fading out the cards and passing the turn
    private void runSpellAnim(Effect effect, Float delay){
        addAction(sequence(
            run(fadeCards()),
            delay(delay),
            run(resolvePlayerTurnHelper(effect)),
            delay(0.2f),
            run(player.takeStatuses(player)),
            run(player.decayStatuses()),
            run(player.incrementTurn())
        ));
    }

    public void resolveEnemyTurn(){
        turn = Turn.LOADING_ENEMY;
        for(Enemy enemy : enemies){
            //enemy action logic/calls to it goes here
            enemy.attackAnim(player);
        }
    }

    public void incrementTurn(){
        if(turn == Turn.LOADING_PLAYER){
            turn = Turn.ENEMY;
        }
        else if(turn == Turn.LOADING_ENEMY){
            enemyTurnProgress++;
            if(enemyTurnProgress == enemies.size()){
                enemyTurnProgress = 0;
                turn = Turn.PLAYER;
                for(int i = 0; i < 3; i++) {
                    deck.draw(hand);
                }
            }
        }
    }

    public void selectTarget(Vector2 clickCoords){
        int targetDiff = maxTargets - currentTargets;
        if(player.getSprite().getBoundingRectangle().contains(clickCoords)){
            if (!player.isSelected()) {
                if(targetDiff > 0) {
                    player.select();
                    targets.add(player);
                    currentTargets++;
                }
                else{
                    System.out.println("maximum targets reached!");
                }
            } else {
                player.unselect();
                targets.remove(player);
                currentTargets--;
            }
            return;
        }
        for(int i = 0; i < enemies.size(); i++){
            if(enemies.get(i).getSprite().getBoundingRectangle().contains(clickCoords)){
                if(!enemies.get(i).isSelected()) {
                    if(targetDiff > 0) {
                        enemies.get(i).select();
                        targets.add(enemies.get(i));
                        currentTargets++;
                    }
                    else{
                        System.out.println("max targets");
                    }
                }
                else{
                    enemies.get(i).unselect();
                    targets.remove(enemies.get(i));
                    currentTargets--;
                }
                return;
            }
        }
    }

    private void unselectAll(){
        player.unselect();
        if(!enemies.isEmpty()){
            for(Enemy enemy : enemies){
                enemy.unselect();
            }
        }
        currentTargets = 0;
    }

    public Turn getTurn(){
        return turn;
    }

    public Player getPlayer(){
        return player;
    }

    public ArrayList<Enemy> getEnemies(){
        return enemies;
    }
}
