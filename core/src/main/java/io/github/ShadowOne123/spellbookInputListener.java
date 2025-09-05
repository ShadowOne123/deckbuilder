package io.github.ShadowOne123;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;

import static io.github.ShadowOne123.Main.combatController;
import static io.github.ShadowOne123.Main.hand;

public class spellbookInputListener extends InputListener {

    SpellbookActor spellbookActor;

    public spellbookInputListener(SpellbookActor spellbookActor){
        this.spellbookActor = spellbookActor;
    }

    @Override
    public boolean touchDown(InputEvent event, float x, float y, int pointer, int button){
        InputController.setInputModeMenu();
        spellbookActor.viewSpellbook();
        return false;
    }
}
