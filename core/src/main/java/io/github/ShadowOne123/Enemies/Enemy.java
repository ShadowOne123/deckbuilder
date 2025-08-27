package io.github.ShadowOne123.Enemies;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.utils.viewport.Viewport;
import io.github.ShadowOne123.*;
import io.github.ShadowOne123.Events.DamageEvent;

import java.util.ArrayList;
import java.util.Random;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.*;

public class Enemy extends Creature {

    ArrayList<AttackData> attacks;

    public Enemy(EnemyData data, SpriteBatch spriteBatch, Viewport viewport, FreeTypeFontGenerator textGen, FreeTypeFontGenerator.FreeTypeFontParameter textParam,
                 float centerX, float centerY, String texture){
        super(spriteBatch, viewport, textGen, textParam, centerX, centerY, texture);
        sprite.setSize(viewport.getWorldWidth()/data.width, viewport.getWorldHeight()/data.height);
        hp = data.health;
        maxHP = hp;
        attacks = data.attacks;
    }




    public void attackAnim(Player player){
        float originX = getX();
        addAction(sequence(
            moveTo(getX() + 10, getY(), 0.06f),
            delay(0.1f),
            moveTo(getX() - 20, getY(), 0.1f),
            moveTo(originX, getY(), 0.3f),
            run(attack(player)),
            delay(0.1f),
            run(takeStatuses(this)),
            run(incrementTurn())
        ));
    }

    private Runnable attack(Player player){
        return new Runnable() {
            @Override
            public void run() {
                Random random = new Random();
                int attackIndex = random.nextInt(attacks.size());
                AttackData attack = attacks.get(attackIndex);
                Creature target = player;
                if(attack.target.equals("self")){
                    target = Enemy.this;
                }
                Main.eventBus.emit(new DamageEvent(target, Enemy.this, attack.damage, DamageType.valueOf(attack.damageType)));
                if(!(attack.statuses == null)){
                    ArrayList<Status> attackStatuses = new ArrayList<>();
                    for(String status : attack.statuses) {
                        attackStatuses.add(Status.makeStatus(status.split(",")));
                    }
                    statusAddingAction.mergeStatuses(attackStatuses, target.getStatuses(), target);
                }
            }
        };
    }


}
