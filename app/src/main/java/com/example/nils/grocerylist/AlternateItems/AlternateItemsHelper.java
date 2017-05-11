package com.example.nils.grocerylist.AlternateItems;

import android.content.Context;
import android.util.Log;

import com.example.nils.grocerylist.Databases.DatabaseHelper;
import com.example.nils.grocerylist.Item;

import java.util.ArrayList;

/**
 * Created by Nils on 4/18/17.
 */

public class AlternateItemsHelper {

    ArrayList<Item> alternate;
    DatabaseHelper db;
    int mode;

    /**
     * Constructs an AlternateItemsHelper object.
     * @param context The context of the activity using this object.
     * @param mode The mode of the app (price or health).
     */
    public AlternateItemsHelper(Context context, int mode) {
        db = new DatabaseHelper(context);
        alternate = new ArrayList<>();
        this.mode = mode;
    }

    /**
     * Returns the alternate item list.
     * @return The alternate item list.
     */
    public ArrayList<Item> getAlternateItemsList() {
        return alternate;
    }

    /**
     * Finds alternate items for a given item.
     * If the item shared at least 50% of the same ingredients as an item, that item is added to the alternate list.
     * @param item The item that alternate items will be found for.
     */
    public void findAlternateItems(Item item) {

        String[] itemIngredients = item.getIngredients();

        if (!itemIngredients[0].equals("")) {

            ArrayList<Item> allItems = db.getAllItems();
            ArrayList<Item> tempAlternate = new ArrayList<>();

            for (Item itemElement : allItems) {
                String[] ingredients = itemElement.getIngredients();
                double percent = 0;

                if (ingredients[0].equals(""))
                    continue;

                for (String ingredient : ingredients) {
                    for (String itemIngredient : itemIngredients) {
                        if (itemIngredient.contains(ingredient)) {
                            percent = percent + 1;
                            Log.d("Percent: ", " ++ (" + percent + ")");
                            Log.d("Item added to alternate", itemElement.toString());
                        }
                    }
                }

                percent /= ingredients.length;
                if (percent >= 0.50) {
                    tempAlternate.add(itemElement);
                    Log.d("Item added in percent", itemElement.toString());
                }
            }

            // When the list was empty the alternate list added the item itself
            // Why is that not happening here?
            // consistency?
            if (!tempAlternate.isEmpty()) {
                alternate.add(findBestItem(tempAlternate));
            }

        } else {
            alternate.add(item);
        }
    }

    /**
     * Finds the best item (either by price or health depending on the mode) from the list of alternate items.
     * @param list The list of alternate items.
     * @return The best item (either by price or health depending on the mode).
     */
    private Item findBestItem(ArrayList<Item> list) {
        HealthLogic healthLogic = new HealthLogic(list);

        switch (mode) {
            case 1:
                return healthLogic.getCheapestItem();
            case 2:
                return healthLogic.getHealthiestItem();
            default:
                return null;
        }
    }
}
