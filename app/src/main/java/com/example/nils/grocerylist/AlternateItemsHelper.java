package com.example.nils.grocerylist;

import android.content.Context;

import java.util.ArrayList;

/**
 * Created by Nils on 4/18/17.
 */

public class AlternateItemsHelper {

    ArrayList<Item> alternate;
    DatabaseHelper db;

    public AlternateItemsHelper (Context context){
        db = new DatabaseHelper(context);
        alternate = new ArrayList<Item>();
    }

    public void findAlternateItems(Item item) {
        ArrayList<Item> fulllist = db.getAllItems();
        String[] itemingredients = item.getIngredients();
        String[] ingredients;
        double percent = 0;
        alternate.add(item);

        for (Item a: fulllist) {
            ingredients = a.getIngredients();
            for (int i = 0; i < ingredients.length; i++) {
                for (int j = 0; j < itemingredients.length; j++) {
                    if (ingredients[i].contains(itemingredients[j])) {
                        percent++;
                    }
                }
            }
            percent = percent / ingredients.length;
            if (percent >= 60) {
                alternate.add(a);
            }
        }
    }

    public ArrayList<Item> getAlternateItemsList() {
        return alternate;
    }
}
