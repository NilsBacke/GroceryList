package com.example.nils.grocerylist;

import java.util.ArrayList;

/**
 * Created by Nils on 3/22/17.
 */

public class Item {

    private String name;
    private Double price, ppu;
    private int calories, sugar, protein, fatCalories, fat, cholesterol, sodium, carbs, fiber;
    private int id;
    private String ingredients;
    public int points;

    public Item(int id, String name, Double price, Double ppu, int calories, int fatCalories, int fat, int cholesterol,
                int sodium, int carbs, int fiber, int sugar, int protein, String ingredients) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.ppu = ppu;
        this.calories = calories;
        this.fatCalories = fatCalories;
        this.fat = fat;
        this.cholesterol = cholesterol;
        this.sodium = sodium;
        this.carbs = carbs;
        this.fiber = fiber;
        this.sugar = sugar;
        this.protein = protein;
        this.ingredients = ingredients;
        points = 0;
    }


    public Item() {
        this.name = "";
        this.price = 0.;
        this.ppu = 0.;
        this.calories = 0;
        this.fatCalories = 0;
        this.fat = 0;
        this.cholesterol = 0;
        this.sodium = 0;
        this.carbs = 0;
        this.fiber = 0;
        this.sugar = 0;
        this.protein = 0;
        this.ingredients = "";
        points = 0;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Double getPrice() {
        return price;
    }

    public Double getPPU() {
        return ppu;
    }

    public int getCalories() {
        return calories;
    }

    public int getFatCalories() {
        return fatCalories;
    }

    public int getFat() {
        return fat;
    }

    public int getCholesterol() {
        return cholesterol;
    }

    public int getSodium() {
        return sodium;
    }

    public int getCarbs() {
        return carbs;
    }

    public int getFiber() {
        return fiber;
    }

    public int getSugar() {
        return sugar;
    }

    public int getProtein() {
        return protein;
    }

    public String getStringIngredients() {
        return ingredients;
    }

    public String[] getIngredients() {
        return ingredients.split(",");
    }





    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public void setPPU(Double ppu) {
        this.ppu = ppu;
    }

    public void setCalories(int calories) {
        this.calories = calories;
    }

    public void setFatCalories(int fatCalories) {
        this.fatCalories = fatCalories;
    }

    public void setFat(int fat) {
        this.fat = fat;
    }

    public void setCholesterol(int cholesterol) {
        this.cholesterol = cholesterol;
    }

    public void setSodium(int sodium) {
        this.sodium = sodium;
    }

    public void setCarbs (int carbs) {
        this.carbs = carbs;
    }

    public void setFiber (int fiber) {
        this.fiber = fiber;
    }

    public void setSugar (int sugar) {
        this.sugar = sugar;
    }

    public void setProtein (int protein) {
        this.protein = protein;
    }

    public void setIngredients(String ingredients) {
        this.ingredients = ingredients;
    }


    @Override
    public String toString() {
        return name;
    }


}