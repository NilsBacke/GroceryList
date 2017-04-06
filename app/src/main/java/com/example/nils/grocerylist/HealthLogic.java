package com.example.nils.grocerylist;

/**
 * Created by Daya on 4/3/2017.
 */

public class HealthLogic {

    Item a;
    Item b;
    int apoints;
    int bpoints;

    public HealthLogic(Item y, Item z) {
        a = y;
        b = z;
        apoints = y.points;
        bpoints = z.points;
    }

    public Item chooseHealthier() {
        Item h = new Item();
        ingredients(a);
        ingredients(b);
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

    private void ingredients(Item g) {
        String[] healthy = {"Egg whites", "Potatoes", "Asparagus", "Chicken", "Lima beans", "Bamboo shoots", "Ground Turkey", "Black-eyed peas", "Green beans", "Turkey", "Corn", "Broccoli", "Lean beef", "Lentils", "Brussels sprouts", "Tuna", "Oatmeal", "Cabbage", "Salmon", "Peas", "Carrots", "Scallops", "Popcorn", "Cauliflower", "Shrimp", "Brown rice", "Cucumbers", "Halibut", "Acorn squash", "Eggplant", "Other Fish", "Sweet potato", "Lettuce", "Yogurt", "Tomato", "Red peppers", "Skim milk", "Shredded wheat", "Green peppers", "Cottage cheese", "Yams", "Spinach", "Venison", "White rice", "Summer squash", "Buffalo", "Black beans", "Zucchini squash", "Pork loin", "Kidney beans", "Onions", "Canadian bacon", "Pinto beans", "Rabbit", "Garbanzo beans", "Split peas", "Fruits", "Cereals", "Grains", "Bagels", "Breads", "Crackers", "Pasta"};
        for(int i = 0; i < g.getIngredients().length; i++){
            for(int j = 0; j < healthy.length; j++){
                if(g.getIngredients()[i].equals(healthy[j])){

                }
            }
        }
        //for each ingredient
        //if ingredient.contains(healthy) points++
        //if ingredient.contains(NOT healthy) points--

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
    //vitamin a
    //vitamin C
    //iron
    //calcium
}

