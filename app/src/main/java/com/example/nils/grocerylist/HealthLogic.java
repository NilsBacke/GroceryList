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
        fatPercentage(a);
        fatPercentage(b);
        fatContent(a);
        fatContent(b);
        proteinContent(a);
        proteinContent(b);
        numIngredients(a);
        numIngredients(b);
        cholesterol(a);
        cholesterol(b);
        fiber(a);
        fiber(b);
        sodium(a);
        sodium(b);
        carbs(a);
        carbs(b);
        apoints = a.points;
        bpoints = b.points;
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

    private void sugarContent(Item g) {
        g.points -= g.getSugar();
        for (int i = 0; i < 5 || i < g.getIngredients().length; i++) {
            if (g.getIngredients()[i].toLowerCase().contains("sugar")) {
                g.points--;
            }
        }
    }

    private void calorieCount(Item g) {

    }

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

    }

}

