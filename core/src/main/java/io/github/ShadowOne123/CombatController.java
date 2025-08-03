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
    private int maxTargets;
    private int currentTargets;

    public CombatController(Player player, PlayArea playArea, ArrayList<Enemy> enemies){
        this.enemies = enemies;
        this.playArea = playArea;
        this.player = player;
        this.targets = new ArrayList<Creature>();
        this.maxTargets = 1;
        this.currentTargets = 0;
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
        playArea.clear();
        unselectAll();
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
