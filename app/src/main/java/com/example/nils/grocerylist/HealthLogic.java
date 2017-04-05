package com.example.nils.grocerylist;

/**
 * Created by Daya on 4/3/2017.
 */

public class HealthLogic {

    Item a;
    Item b;
    int apoints = 0;
    int bpoints = 0;

    public HealthLogic(Item y, Item z) {
        a = y;
        b = z;
    }

    public Item chooseHealthier() {
        Item h = new Item();
        oseIngerdients(a);
        oseIngerdients(b);
        sugarContent(a);
        sugarContent(b);
        calorieCount(a);
        calorieCount(b);
        fatContent(a);
        fatContent(b);
        proteinContent(a);
        proteinContent(b);
        numIngredients(a);
        numIngredients(b);
        if (apoints > bpoints) {
            h = a;
        } else if (apoints == bpoints) {
            return null;
        } else if (bpoints > apoints) {
            h = b;
        }
        return h;
    }

    private void oseIngerdients(Item q) {


    }

    private void sugarContent(Item g) {

    }

    private void calorieCount(Item g) {

    }

    private void fatContent(Item g) {

    }

    private void proteinContent(Item g) {

    }

    private void numIngredients(Item g) {

    }

}

