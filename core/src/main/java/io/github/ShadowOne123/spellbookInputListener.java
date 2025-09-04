package io.github.ShadowOne123;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;

public class spellbookInputListener extends InputListener {

    public spellbookInputListener(){

    }

    @Override
    public boolean touchDown(InputEvent event, float x, float y, int pointer, int button){
        System.out.println("ping");
        InputController.setInputModeMenu();
        SpellResolver.getSpellbook().forEach((key,value) -> {
            System.out.println(key);
        });
        return false;
    }
}
