package io.github.ShadowOne123;

import io.github.ShadowOne123.Actions.Action;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static io.github.ShadowOne123.Main.characterClass;

public class SpellResolver {

    private static Map<String, String> spellbook = new HashMap<>();

    public SpellResolver(){

    }


    public static Effect buildEffect(ArrayList<Card> cards){
        Effect[] effectsArr = new Effect[cards.size()];
        for(int i = 0; i < effectsArr.length; i++){
            effectsArr[i] = cards.get(i).getEffect();
        }
        Effect product = new Effect();
        for (Effect effect : effectsArr) {
            mergeActions(product.getActions(), effect.getActions());
        }
        return product;
    }

    public static Card checkForSpell(ArrayList<Card> cards){
        StringBuilder spell = new StringBuilder();
        for(int i = 0; i < cards.size(); i++){
            spell.append(cards.get(i).getName());
            if(i != cards.size()-1){
                spell.append(" ");
            }
        }

        if(spellbook.containsKey(spell.toString())){
            return new Card(spellbook.get(spell.toString()), Main.stage);
        }
        return null;
    }

    public static void mergeActions(ArrayList<Action> currentArr, ArrayList<Action> newArr){
        //loops through every action in current array for each new action, if there's an action with the same class, combines them
        //otherwise add the new action to the current array
        boolean combined;
        for(Action newA : newArr){
            combined = false;
            for(Action action : currentArr){
                if(action.getClass().equals(newA.getClass())){
                    combined = action.combine(newA);
                }
            }
            if(!combined){
                currentArr.add(newA);
            }
        }
    }

    public static void populateSpellbook(String filepath){
        try(InputStream inputStream = SpellResolver.class.getResourceAsStream(filepath);){
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            String line;

            while ((line = reader.readLine()) != null) {
                String[] attributes = line.split(":");

                // Make sure the line contains exactly one key-value pair
                if (attributes.length == 3) {
                    String cardClass = attributes[0].trim();
                    if (cardClass.equals(characterClass)) {
                        String key = attributes[1].trim();
                        String value = attributes[2].trim();
                        spellbook.put(key, value);
                    }

                } else {
                    System.out.println("Skipping invalid entry: " + line);
                }
            }
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }

    public static Map<String, String> getSpellbook(){
        return spellbook;
    }
}
