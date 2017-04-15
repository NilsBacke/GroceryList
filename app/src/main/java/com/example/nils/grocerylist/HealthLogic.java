package com.example.nils.grocerylist;

import java.util.ArrayList;

/**
 * Created by Daya on 4/3/2017.
 */

/**
 * This class compares items by the nutrition values on their food labels.
 */
public class HealthLogic {

    //data
    private Item a;
    private ArrayList<Item> b;
    private int apoints;

    //constructor

    /**
     * Constructs a HealthLogic object. Takes in an item and an ArrayList of similar items. The Arraylist was constructed in an earlier class.
     *
     * @param y     the original item to be compared to
     * @param items the ArrayList of similar items
     */
    public HealthLogic(Item y, ArrayList<Item> items) {
        a = y;
        b = items;
    }

    //methods

    /**
     * Compares the items from the ArrayList to the original item. Assigns "HealthPoints" based on how healthy the item is. Sorts the array by the number of point seach item has.
     *
     * @return the ArrayList of all items sorted by health points.
     */
    public ArrayList<Item> rankByHealth() {
        b.add(a);
        for (int i = 0; i < b.size(); i++) {
            ingredients(b.get(i));
            sugarContent(b.get(i));
            calorieCount(b.get(i));
            fatPercentage(b.get(i));
            fatContent(b.get(i));
            proteinContent(b.get(i));
            numIngredients(b.get(i));
            cholesterol(b.get(i));
            fiber(b.get(i));
            sodium(b.get(i));
            carbs(b.get(i));
        }

        for (int i = 0; i < b.size() - 1; i++) {
            for (int j = 0; j < b.size() - i - 1; j++) {
                if (b.get(j).getPoints() > b.get(j + 1).getPoints()) {
                    Item e = b.get(j + 1);
                    b.set(j + 1, b.get(j));
                    b.set(j, e);
                }
            }
        }


        return b;
    }

    /**
     * Adds one health point for each healthy ingredient and subtracts one point for every unhealthy ingedient.
     *
     * @param g an Item to examine
     */
    private void ingredients(Item g) {
        String[] healthy = {"Egg whites", "Potatoes", "Asparagus", "Chicken", "Lima beans", "Bamboo shoots", "Ground Turkey", "Black-eyed peas", "Green beans", "Turkey", "Corn", "Broccoli", "Lean beef", "Lentils", "Brussels sprouts", "Tuna", "Oatmeal", "Cabbage", "Salmon", "Peas", "Carrots", "Scallops", "Popcorn", "Cauliflower", "Shrimp", "Brown rice", "Cucumbers", "Halibut", "Acorn squash", "Eggplant", "Other Fish", "Sweet potato", "Lettuce", "Yogurt", "Tomato", "Red peppers", "Skim milk", "Shredded wheat", "Green peppers", "Cottage cheese", "Yams", "Spinach", "Venison", "White rice", "Summer squash", "Buffalo", "Black beans", "Zucchini squash", "Pork loin", "Kidney beans", "Onions", "Canadian bacon", "Pinto beans", "Rabbit", "Garbanzo beans", "Split peas", "Fruits", "Cereals", "Grains", "Bagels", "Breads", "Crackers", "Pasta"};
        String[] UNhealthy = {"Sodium nitrate",
                "Sulfite", "Azodicarbonamide", "Potassium bromate", "Propyl gallate", "Propylene glycol", "Butane", "Monosodium glutamate", "Disodium inosinate", "Disodium guanylate", "Enriched flour", "Recombinant Bovine Growth Hormone", "soybean oil", "corn oil", "safflower oil", "canola oil", "peanut oil", "Sodium benzoate", "Brominated vegetable oil", "Olestra", "Carrageenan", "Polysorbate 60", "Camauba wax", "Magnesium sulphate", "Chlorine dioxide", "Paraben", "Sodium carboxymethyl cellulose", "Aluminum", "Saccharin", "Aspartame", "High fructose corn syrup", "Acesulfame potassium", "Sucralose", "Agave nectar", "Bleached starch", "Tert butylhydroquinone", "Red #40", "Blue #1", "Blue #2", "Citrus red #1", "Citrus red #2", "Green #3", "Yellow #5", "Yellow #6", "Red #2", "Red #3", "Caramel coloring", "Brown HT", "Orange B", "Bixin", "Norbixin", "Annatto"};
        for (int i = 0; i < g.getIngredients().length; i++) {
            for (int j = 0; j < healthy.length; j++) {
                if (g.getIngredients()[i].toLowerCase().contains(healthy[j].toLowerCase())) {
                    g.points++;
                }
            }
            for (int j = 0; j < UNhealthy.length; j++) {
                if (g.getIngredients()[i].toLowerCase().contains(UNhealthy[j].toLowerCase())) {
                    g.points--;
                }
            }
        }

    }

    /**
     * Subtracts one point for every gram of sugar. Subtracts one point for each sugary ingredient within the first five ingredients on the ingredient list.
     *
     * @param g an Item to examine
     */
    private void sugarContent(Item g) {
        g.points -= g.getSugar();
        for (int i = 0; i < 5 || i < g.getIngredients().length; i++) {
            if (g.getIngredients()[i].toLowerCase().contains("sugar") || g.getIngredients()[i].toLowerCase().contains("ose")) {
                g.points--;
            }
        }
    }

    /**
     * Subtracts one point for every calorie more than 50 calories.
     * @param g an Item to examine
     */
    private void calorieCount(Item g) {
        if (g.getCalories() > 50) {
            g.points -= g.getCalories() - 50;
        }
    }

    /**
     * Subtracts one point for each 1% over 35% of calories from fat.
     * @param g an Item to examine
     */
    private void fatPercentage(Item g) {
        double fatP = (g.getFatCalories() / g.getCalories()) * 100;
        if (fatP > 35) {
            g.points -= fatP - 35;
        }


    }

    private void fatContent(Item g) {
        g.points -= g.getFat();
    }

    private void proteinContent(Item g) {
        g.points += g.getProtein();
    }

    private void numIngredients(Item g) {
        for (int i = 0; i < g.getIngredients().length; i++) {
            if (i % 5 == 0) {
                g.points--;
            }
        }
    }

    private void cholesterol(Item g) {
        g.points -= g.getCholesterol();
    }

    private void sodium(Item g) {
        //lower than 5% DV (75mg)
        if (g.getSodium() > 75) {
            g.points -= g.getSodium() - 75;
        }
    }

    private void fiber(Item g) {
        g.points += g.getFiber();
    }

    private void carbs(Item g) {
        if (g.getCarbs() > 15) {
            g.points += g.getCarbs() - 15;
        }
    }

}

