package io.github.ShadowOne123;

import com.badlogic.gdx.math.Vector2;
import io.github.ShadowOne123.Events.DamageEvent;
import io.github.ShadowOne123.Events.DamageTakenEvent;

import java.lang.reflect.Array;
import java.util.ArrayList;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.*;
import static io.github.ShadowOne123.Main.eventBus;

public class CombatController {

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
        eventBus.register(DamageEvent.class, event -> {
            int finalDmg = event.amount;
            Status block = event.target.searchStatuses("block");
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
            event.target.takeDamage(finalDmg);
        });


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
        //build spell
        Effect effect = SpellResolver.buildEffect(playArea.getCards());
        //check for no cards played
        if(effect.getActions().isEmpty()){
            System.out.println("No cards played!");
            //allows no cards played
            return true;
        }
        //apply to targets
        if(!targets.isEmpty()) {
            for (Creature target : targets) {
                effect.apply(target);
            }
        }
        //check for no targets
        else{
            System.out.println("No targets!");
            return false;
        }
        //discard everything from hand and play area
        for(Card card : playArea.getCards()){
            deck.addToDiscard(card);
        }
        playArea.clear();
        for(Card card : hand.getCards()){
            deck.addToDiscard(card);
        }
        deck.addToDiscard(hand.heldCard);
        hand.clear();
        unselectAll();
        targets.clear();

        return player.playerAnim();
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
