package com.example.nils.grocerylist;

import android.content.Context;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by Nils on 4/18/17.
 */

public class AlternateItemsHelper {

    ArrayList<Item> alternate;
    DatabaseHelper db;
    int mode;

    public AlternateItemsHelper (Context context, int mode){
        db = new DatabaseHelper(context);
        alternate = new ArrayList<>();
        this.mode = mode;
    }

    public void findAlternateItems(Item item) {
        ArrayList<Item> allItems = db.getAllItems();
        ArrayList<Item> tempAlternate = new ArrayList<>();
        String[] itemIngredients = item.getIngredients();
        String[] ingredients;
        double percent;

        if (!itemIngredients[0].equals("")) {
            for (Item listElement : allItems) {
                ingredients = listElement.getIngredients();
                percent = 0;

                if (ingredients[0].equals(""))
                    break;
                for (String ingredient : ingredients) {
                        for (String itemIngredient : itemIngredients) {
                            if (ingredient.contains(itemIngredient)) {
                                percent = percent + 1;
                                Log.d("Percent: ", " ++ (" + percent + ")");
                                Log.d("Item added to alternate", listElement.toString());
                            } else if (itemIngredient.contains(ingredient)) {
                                percent = percent + 1;
                            }

                        }

                    percent = percent / ingredients.length;
                    if (percent >= 0.25) {
                        tempAlternate.add(listElement);
                        Log.d("Item added in percent", listElement.toString());
                    }
                }

            }
            if (!tempAlternate.isEmpty()) {
                alternate.add(findBestItem(tempAlternate));
            }
        } else {
            alternate.add(item);
        }
        tempAlternate.clear();
    }

    public ArrayList<Item> getAlternateItemsList() {
        return alternate;
    }

    public Item findBestItem(ArrayList<Item> list) {
        HealthLogic healthLogic = new HealthLogic(list);

        switch (mode){
            case 1:
                return healthLogic.getCheapestItem();
            case 2:
                return healthLogic.getHealthiestItem();
            default:
                return null;
        }
    }
}
