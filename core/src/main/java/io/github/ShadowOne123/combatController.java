package io.github.ShadowOne123;

import java.util.ArrayList;

public class combatController {

    private ArrayList<Enemy> enemies;
    private Player player;
    private PlayArea playArea;
    private Effect effect;
    private enum Turn {ENEMY, PLAYER}
    private Turn turn = Turn.PLAYER;

    public combatController(Player player, PlayArea playArea, ArrayList<Enemy> enemies){
        this.enemies = enemies;
        this.playArea = playArea;
        this.player = player;
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
        boolean noneSelected = true;
        //build spell
        effect = SpellResolver.buildEffect(playArea.getCards());
        //check for no cards played
        if(effect.getActions().isEmpty()){
            System.out.println("No cards played!");
            //allows no cards played
            return true;
        }
        //apply to targets
        for(Enemy enemy : enemies){
            if(enemy.isSelected()){
                noneSelected = false;
                effect.apply(enemy);
                enemy.unselect();
            }
        }
        //check for no targets
        if(noneSelected){
            System.out.println("No targets!");
            return false;
        }
        //call all relevant statuses at every point
            //start with just end of turn statuses. Branch out later
        for(Enemy enemy : enemies){
            enemy.applyEndTurnStatuses();
        }

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

    public String getTurn(){
        return turn.toString();
    }
}
