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

    public AlternateItemsHelper (Context context){
        db = new DatabaseHelper(context);
        tempalternate = new ArrayList<Item>();
        alternate = new ArrayList<Item>();
        mode = 1;
    }

    public void findAlternateItems(Item item) {
        ArrayList<Item> fulllist = db.getAllItems();
        String[] itemingredients = item.getIngredients();
        String[] ingredients;
        double percent = 0;

        if (!itemingredients[0].equals("")) {
            for (int k = 0; k < fulllist.size(); k++) {
                ingredients = fulllist.get(k).getIngredients();

                if (ingredients[0].equals("")) {
                    k++;
                } else {
                    for (int i = 0; i < ingredients.length; i++) {
                        for (int j = 0; j < itemingredients.length; j++) {
                            if (ingredients[i].contains(itemingredients[j])) {
                                percent = percent + 1;
                                Log.d("Percent: ", " ++ (" + percent + ")");
                                Log.d("Item added to alternate", fulllist.get(k).toString());
                            } else if (itemingredients[j].contains(ingredients[i])) {
                                percent = percent + 1;
                            }

                        }
                    }

                    percent = percent / ingredients.length;
                    if (percent >= 0.25) {
                        tempalternate.add(fulllist.get(k));
                        Log.d("Item added in percent", fulllist.get(k).toString());
                    }
                }

                percent = 0;

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

    public void setMode(int mode) {
        this.mode = mode;
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
