package io.github.ShadowOne123;

import com.badlogic.gdx.math.Vector2;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class CombatController {

    private ArrayList<Enemy> enemies;
    private ArrayList<Creature> targets;
    private Player player;
    private PlayArea playArea;
    private enum Turn {ENEMY, PLAYER}
    private Turn turn = Turn.PLAYER;

    public CombatController(Player player, PlayArea playArea, ArrayList<Enemy> enemies){
        this.enemies = enemies;
        this.playArea = playArea;
        this.player = player;
        this.targets = new ArrayList<Creature>();
    }

    public void resolveTurn(){
        if(turn == Turn.PLAYER){
            if(resolvePlayerTurn()) {
                turn = Turn.ENEMY;
            }
        }
        else{
            resolveEnemyTurn();
        }
    }

    //called when "enter" is pressed, ie when the spell is cast
    public boolean resolvePlayerTurn(){
        player.takeStatuses();
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
        //apply player statuses
        playArea.getCards().clear();
        return true;
    }

    public void resolveEnemyTurn(){
        for(Enemy enemy : enemies){
            //enemy action logic/calls to it goes here
            enemy.attackAnim(player);
        }
        turn = Turn.PLAYER;
    }

    public void selectTarget(Vector2 clickCoords){
        if(player.getSprite().getBoundingRectangle().contains(clickCoords)){
            if(player.toggleSelected()){
                targets.add(player);
            }
            else{
                targets.remove(player);
            }
            return;
        }
        for(int i = 0; i < enemies.size(); i++){
            if(enemies.get(i).getSprite().getBoundingRectangle().contains(clickCoords)){
                if(enemies.get(i).toggleSelected()) {
                    targets.add(enemies.get(i));
                }
                else{
                    targets.remove(enemies.get(i));
                }
                return;
            }
        }
    }

    public String getTurn(){
        return turn.toString();
    }

    public Player getPlayer(){
        return player;
    }

    public ArrayList<Enemy> getEnemies(){
        return enemies;
    }
}
