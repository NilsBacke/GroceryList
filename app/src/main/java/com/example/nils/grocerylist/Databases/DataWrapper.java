package com.example.nils.grocerylist.Databases;

import android.content.Context;
import android.util.Log;

import com.example.nils.grocerylist.Item;
import com.example.nils.grocerylist.ItemTest;
import com.google.firebase.FirebaseException;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * Created by Nils on 5/8/17.
 */

public class DataWrapper {

    final FirebaseDatabase database;
    DatabaseHelper db;

    public DataWrapper(Context context) {
        database = FirebaseDatabase.getInstance();
        Log.d("DATA", database.toString());
        db = new DatabaseHelper(context);
    }

    public void getItems(){
        DatabaseReference itemRef = database.getReference().child("Items"); //directory
        itemRef.addValueEventListener (new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String name, price, each, fatCalories, fat, cholesterol, sodium, carbs, fiber, sugar, protein, ingredients, calories;
                String picture;
                if (dataSnapshot.hasChildren()) {
                    int i = 0;
                    for (DataSnapshot child : dataSnapshot.getChildren()) {
                        Log.d("DATA", child.child("name").getValue().toString() + i);
                        try {
                            calories = child.child("calories").getValue().toString();
                        } catch (NullPointerException e) {
                            calories = "";
                        }
                        try {
                            carbs = child.child("carbs").getValue().toString();
                        } catch (NullPointerException e) {
                            carbs = "";
                        }
                        try {
                            cholesterol = child.child("cholesterol").getValue().toString();
                        } catch (NullPointerException e) {
                            cholesterol = "";
                        }
                        try {
                            fat = child.child("fat").getValue().toString();
                        } catch (NullPointerException e) {
                            fat = "";
                        }
                        try {
                            fatCalories = child.child("fatCalories").getValue().toString();
                        } catch (NullPointerException e) {
                            fatCalories = "";
                        }
                        try {
                            fiber = child.child("fiber").getValue().toString();
                        } catch (NullPointerException e) {
                            fiber = "";
                        }
                        try {
                            ingredients = child.child("ingredients").getValue().toString();
                        } catch (NullPointerException e) {
                            ingredients = "";
                        }
                        try {
                            name = child.child("name").getValue().toString();
                        } catch (NullPointerException e) {
                            name = "";
                        }
                        try {
                            each = child.child("PerUnit").getValue().toString();
                        } catch (NullPointerException e) {
                            each = "";
                        }
                        try {
                            picture = child.child("picture").getValue().toString();
                        } catch (NullPointerException e) {
                            picture = "";
                        }
                        try {
                            price = child.child("Price").getValue().toString();
                        } catch (NullPointerException e) {
                            price = "";
                        }
                        try {
                            protein = child.child("protein").getValue().toString();
                        } catch (NullPointerException e) {
                            protein = "";
                        }
                        try {
                            sodium = child.child("sodium").getValue().toString();
                        } catch (NullPointerException e) {
                            sodium = "";
                        }
                        try {
                            sugar = child.child("sugar").getValue().toString();
                        } catch (NullPointerException e) {
                            sugar = "";
                        }

                        // A double is formed from the price data by removing the '$'
                        price = price.substring(1);
                        Double doubleprice = Double.parseDouble(price);

                        // A double "each" (PPU) is formed by removing all of the characters except for the relevant numbers.
                        int start = 0;
                        int end = 0;
                        for (int j = 0; j < each.length(); j++) {
                            if (each.charAt(j) == '$') {
                                start = j + 1;
                            }
                            if (each.charAt(j) == '/') {
                                end = j - 1;
                            }
                        }
                        each = each.substring(start, end);
                        Double doubleeach = Double.parseDouble(each);

                        // A double is formed from the retrieved calories from fat
                        Double doubleFatCalories;
                        if (fatCalories.length() == 0) {
                            doubleFatCalories = 0.;
                        } else {
                            doubleFatCalories = Double.parseDouble(fatCalories);
                        }

                        int intcalories;
                        if (calories.length() == 0) {
                            intcalories = 0;
                        } else {
                            intcalories = Integer.parseInt(calories);
                        }

                        // A double is formed from reformatting the fat.
                        Double doublefat;
                        if (fat.length() == 0) {
                            doublefat = 0.;
                        } else {
                            fat = fat.substring(0, fat.length() - 1);
                            doublefat = Double.parseDouble(fat);
                        }

                        // A double is formed from reformatting the cholesterol.
                        if (cholesterol.length() >= 2) {
                            cholesterol = cholesterol.substring(0, cholesterol.length()-2);
                        }
                        Double doublecholesterol;
                        if (cholesterol.length() == 0) {
                            doublecholesterol = 0.;
                        } else {
                            doublecholesterol = Double.parseDouble(cholesterol);
                        }

                        // A double is formed from reformatting the sodium.
                        Double doublesodium;
                        if (sodium.isEmpty()) {
                            doublesodium = 0.;
                        } else {
                            sodium = sodium.substring(0, sodium.length()-2);
                            if (sodium.isEmpty()) {
                                doublesodium = 0.;
                            } else {
                                doublesodium = Double.parseDouble(sodium.replaceAll(",", ""));
                            }
                        }

                        // A double is formed from reformatting the carbs.
                        Double doublecarbs;
                        if (carbs.length() == 0) {
                            doublecarbs = 0.;
                        } else {
                            carbs = carbs.substring(0, carbs.length() - 1);
                            doublecarbs = Double.parseDouble(carbs);
                        }

                        // A double is formed from reformatting the fiber
                        Double doublefiber;
                        if (fiber.length() == 0) {
                            doublefiber = 0.;
                        } else {
                            fiber = fiber.substring(0, fiber.length() - 1);
                            doublefiber = Double.parseDouble(fiber);
                        }

                        // A double is formed from reformatting the sugar.
                        Double doublesugar;
                        if (sugar.length() == 0) {
                            doublesugar = 0.;
                        } else {
                            sugar = sugar.substring(0, sugar.length() - 1);
                            doublesugar = Double.parseDouble(sugar);
                        }

                        // A double is formed from reformatting the protein.
                        Double doubleprotein;
                        protein = protein.substring(0, protein.length()-1);
                        if (protein.length() == 0) {
                            doubleprotein = 0.;
                        } else {
                            doubleprotein = Double.parseDouble(protein);
                        }

                        // A new item object is formed from all of the retreived data.
                        Item newItem = new Item(i, name, doubleprice, doubleeach, picture, intcalories, doubleFatCalories, doublefat,
                                doublecholesterol, doublesodium, doublecarbs, doublefiber, doublesugar, doubleprotein, ingredients);

                        // The new item is added to the virtual data table.
                        db.addItem(newItem);
                        i++;
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getMessage());
            }
        });

    }


}
