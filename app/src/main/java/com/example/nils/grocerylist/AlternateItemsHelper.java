package com.example.nils.grocerylist;

import android.content.Context;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by Nils on 4/18/17.
 */

public class AlternateItemsHelper {

    ArrayList<Item> tempalternate;
    ArrayList<Item> alternate;
    DatabaseHelper db;
    int mode;

    public AlternateItemsHelper (Context context, int mode){
        db = new DatabaseHelper(context);
        tempalternate = new ArrayList<Item>();
        alternate = new ArrayList<Item>();
        this.mode = mode;
    }

    public void findAlternateItems(Item item) {
        ArrayList<Item> fulllist = db.getAllItems();
        String[] itemingredients = item.getIngredients();
        String[] ingredients;
        double percent;

        if (!itemingredients[0].equals("")) {
            for (Item listElement : fulllist) {
                ingredients = listElement.getIngredients();
                percent = 0;

                if (ingredients[0].equals("")) {
                    break;
                } else {
                    for (String ingredient : ingredients) {
                        for (String itemingredient : itemingredients) {
                            if (ingredient.contains(itemingredient)) {
                                percent = percent + 1;
                                Log.d("Percent: ", " ++ (" + percent + ")");
                                Log.d("Item added to alternate", listElement.toString());
                            } else if (itemingredient.contains(ingredient)) {
                                percent = percent + 1;
                            }

                        }
                    }

                    percent = percent / ingredients.length;
                    if (percent >= 0.25) {
                        tempalternate.add(listElement);
                        Log.d("Item added in percent", listElement.toString());
                    }
                }

            }
            if (!tempalternate.isEmpty()) {
                alternate.add(findBestItem(tempalternate));
            }
        } else {
            tempalternate.add(item);
            alternate.add(findBestItem(tempalternate));
        }
        tempalternate.clear();
    }

    public ArrayList<Item> getAlternateItemsList() {
        return alternate;
    }

    public Item findBestItem(ArrayList<Item> list) {
        HealthLogic healthLogic = new HealthLogic(list);
        if (mode == 1) {
            return healthLogic.getCheapestItem();
        }
        if (mode == 2) {
            return healthLogic.getHealthiestItem();
        }
        return null;
    }
}
