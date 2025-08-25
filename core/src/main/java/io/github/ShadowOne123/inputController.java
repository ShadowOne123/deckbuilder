package io.github.ShadowOne123;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.Viewport;

import java.util.ArrayList;

public class inputController {
    private Stage stage;
    private InputMultiplexer multiplexer;
    public inputController(Stage stage){
        this.stage = stage;
        multiplexer = new InputMultiplexer();
    }

    public void setInputModeBattle(Hand hand, PlayArea playArea, CombatController combatController, Viewport viewport, Deck deck){
        Gdx.input.setInputProcessor(multiplexer);
        multiplexer.addProcessor(stage);
        multiplexer.addProcessor(new InputAdapter(){

            @Override
            public boolean touchDown(int screenX, int screenY, int pointer, int button) {
                if(button == Input.Buttons.LEFT && combatController.getTurn() == CombatController.Turn.PLAYER){
                    Creature target;
                    Vector2 clickCoords = new Vector2(Gdx.input.getX(), Gdx.input.getY());
                    clickCoords = viewport.unproject(clickCoords);
                    //unproject converts between click(absolute coords, measured in pixels) to world coords measured in viewport units

                    //check if there's currently a card attached to the cursor
                    if(hand.heldCard != null){
                        if(playArea.getSprite().getBoundingRectangle().contains(clickCoords)){
                            if(playArea.addCard(hand.heldCard)){
                                hand.heldCard = null;
                            }
                        }
                        if(hand.getSprite().getBoundingRectangle().contains(clickCoords)){
                            ArrayList<Card> cards = hand.getCards();
                            //what if 0 cards
                            if(cards.isEmpty()){
                                hand.addCard(hand.heldCard, 0);
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
                                    hand.addCard(hand.heldCard, 1);
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
                                        hand.addCard(hand.heldCard, cards.size());
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
                            Card[] cards = playArea.getCardsAsArray();
                            for (int i = cards.length - 1; i >= 0; i--) {
                                temp = cards[i];
                                if(temp != null) {
                                    if (temp.getSprite().getBoundingRectangle().contains(clickCoords)) {
                                        playArea.removeCard(i);
                                        hand.heldCard = temp;
                                        break;
                                    }
                                }
                            }
                        }
                    }
                    //TODO: Remove on production
                    else if(deck.getSprite().getBoundingRectangle().contains(clickCoords)){
                        deck.draw(hand);
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
                    case Input.Keys.A: hand.addCard(new Card("king", Main.stage)); break;
                    case Input.Keys.S: hand.addCard(new Card("barbed_wire", Main.stage)); break;
                    case Input.Keys.ENTER: combatController.resolveTurn(); break;
                    case Input.Keys.D: debug(hand, playArea, deck);
                }

                return false;
            }
        });
    }

    private void debug(Hand hand, PlayArea playArea, Deck deck){
        /*
        System.out.println("Debug report:\nCards held in hand:");
        for(Card card : hand.getCards()){
            if(card == null){
                System.out.println("augh!!! AHHHH!!");
                break;
            }
            System.out.println(card.toString());
        }
        System.out.println("Cards in deck:");
        for(Card card : deck.getCards()){
            if(card == null){
                System.out.println("augh!!! AHHHH!!");
                break;
            }
            System.out.println(card.toString());
        }
        System.out.println("Cards in discard:");
        for(Card card : deck.getDiscard()){
            if(card == null){
                System.out.println("augh!!! AHHHH!!");
                break;
            }
            System.out.println(card.toString());
        }

         */
        System.out.println("Debug report");
        for(Card card : playArea.getCards()){
            if(card == null){
                System.out.println("augh!!! AHHHH!!");
                break;
            }
            System.out.println(card);
        }
    }


}
