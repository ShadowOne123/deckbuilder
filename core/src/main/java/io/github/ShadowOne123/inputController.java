package io.github.ShadowOne123;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.Viewport;

import java.util.ArrayList;

public class inputController {
    public inputController(){}

    public void setInputModeBattle(Hand hand, PlayArea playArea, CombatController combatController, Viewport viewport){
        Gdx.input.setInputProcessor(new InputAdapter(){
            @Override
            public boolean touchDown(int screenX, int screenY, int pointer, int button) {
                if(button == Input.Buttons.LEFT){
                    Creature target;
                    Vector2 clickCoords = new Vector2(Gdx.input.getX(), Gdx.input.getY());
                    clickCoords = viewport.unproject(clickCoords);
                    //unproject converts between click(absolute coords, measured in pixels) to world coords measured in viewport units

                    //check if there's currently a card attached to the cursor
                    if(hand.heldCard != null){
                        if(playArea.getSprite().getBoundingRectangle().contains(clickCoords)){
                            playArea.addCard(hand.heldCard);
                            hand.heldCard = null;
                        }
                        if(hand.getSprite().getBoundingRectangle().contains(clickCoords)){
                            ArrayList<Card> cards = hand.getCards();
                            //what if 0 cards
                            if(cards.isEmpty()){
                                hand.addCard(hand.heldCard);
                                hand.heldCard = null;
                            }
                            //what if 1 card
                            else if(cards.size() == 1){
                                Sprite temp = cards.get(0).getSprite();
                                double dist = clickCoords.x - (temp.getX() + temp.getWidth()/2);
                                if(dist < 0){
                                    hand.addCard(hand.heldCard, 0);
                                }
                                else{
                                    hand.addCard(hand.heldCard);
                                }
                                hand.heldCard = null;
                            }
                            else{
                                Sprite temp = cards.get(0).getSprite();
                                double minDist = Math.abs(clickCoords.x - (temp.getX() + temp.getWidth()/2));
                                double dist;
                                for (int i = 1; i < cards.size(); i++) {
                                    temp = cards.get(i).getSprite();
                                    dist = Math.abs(clickCoords.x - (temp.getX() + temp.getWidth()/2));
                                    if(dist < minDist){
                                        minDist = dist;
                                    }
                                    else{
                                        temp = cards.get(i-1).getSprite();
                                        dist = clickCoords.x - (temp.getX() + temp.getWidth()/2);
                                        if(dist > 0){
                                            hand.addCard(hand.heldCard, i);
                                        }
                                        else{
                                            hand.addCard(hand.heldCard, i-1);
                                        }
                                        hand.heldCard = null;
                                        break;
                                    }
                                }
                                if(hand.heldCard != null){
                                    temp = cards.get(cards.size()-1).getSprite();
                                    dist = clickCoords.x - (temp.getX() + temp.getWidth()/2);
                                    if(dist < 0){
                                        hand.addCard(hand.heldCard, cards.size()-1);
                                    }
                                    else{
                                        hand.addCard(hand.heldCard);
                                    }
                                    hand.heldCard = null;
                                }
                            }
                        }
                    }
                    else if(hand.getSprite().getBoundingRectangle().contains(clickCoords) || playArea.getSprite().getBoundingRectangle().contains(clickCoords)){
                        //checks for clicks in hand, if there are any moves clicked card to played cards
                        Card temp;
                        //moves cards from hand to play area
                        if(hand.getSprite().getBoundingRectangle().contains(clickCoords)) {
                            for (int i = hand.getCards().size() - 1; i >= 0; i--) {
                                temp = hand.getCards().get(i);
                                if (temp.getSprite().getBoundingRectangle().contains(clickCoords)) {
                                    hand.removeCard(i);
                                    hand.heldCard = temp;
                                    break;
                                }
                            }
                        }
                        //move cards to hand on-click from played area
                        if(playArea.getSprite().getBoundingRectangle().contains(clickCoords)) {
                            for (int i = playArea.getCards().size() - 1; i >= 0; i--) {
                                temp = playArea.getCards().get(i);
                                if (temp.getSprite().getBoundingRectangle().contains(clickCoords)) {
                                    playArea.removeCard(i);
                                    hand.heldCard = temp;
                                    break;
                                }
                            }
                        }
                    }
                    else{
                        combatController.selectTarget(clickCoords);
                    }

                }
                return false;
            }

            @Override
            public boolean keyUp(int keycode) {
                switch(keycode){
                    case Input.Keys.A: hand.addCard(new Card("temperance")); break;
                    case Input.Keys.S: hand.addCard(new Card("king")); break;
                    case Input.Keys.ENTER: combatController.resolveTurn(); break;
                }

                return false;
            }
        });
    }

}
