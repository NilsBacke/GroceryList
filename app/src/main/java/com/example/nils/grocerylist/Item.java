package com.example.nils.grocerylist;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Nils on 3/22/17.
 */

public class Item implements Serializable {

    private String name;
    private Double price, ppu, fat, fiber, sugar, protein, fatCalories, cholesterol, sodium, carbs;
    private int calories;
    private int id;
    private String ingredients;
    public int points;

    public Item(int id, String name, Double price, Double ppu, int calories, Double fatCalories, Double fat, Double cholesterol,
                Double sodium, Double carbs, Double fiber, Double sugar, Double protein, String ingredients) {
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
        this.fatCalories = 0.;
        this.fat = 0.;
        this.cholesterol = 0.;
        this.sodium = 0.;
        this.carbs = 0.;
        this.fiber = 0.;
        this.sugar = 0.;
        this.protein = 0.;
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

    public Double getFatCalories() {
        return fatCalories;
    }

    public Double getFat() {
        return fat;
    }

    public Double getCholesterol() {
        return cholesterol;
    }

    public Double getSodium() {
        return sodium;
    }

    public Double getCarbs() {
        return carbs;
    }

    public Double getFiber() {
        return fiber;
    }

    public Double getSugar() {
        return sugar;
    }

    public Double getProtein() {
        return protein;
    }

    public String[] getIngredients() {
        return ingredients.split(",");
    }

    public int getPoints() {
        return points;
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

    public void setFatCalories(Double fatCalories) {
        this.fatCalories = fatCalories;
    }

    public void setFat(Double fat) {
        this.fat = fat;
    }

    public void setCholesterol(Double cholesterol) {
        this.cholesterol = cholesterol;
    }

    public void setSodium(Double sodium) {
        this.sodium = sodium;
    }

    public void setCarbs (Double carbs) {
        this.carbs = carbs;
    }

    public void setFiber (Double fiber) {
        this.fiber = fiber;
    }

    public void setSugar (Double sugar) {
        this.sugar = sugar;
    }

    public void setProtein (Double protein) {
        this.protein = protein;
    }

    public void setIngredients(String ingredients) {
        this.ingredients = ingredients;
    }


    @Override
    public String toString() {
        return name;
    }

    public String pricetoString() {
        return ("$" + price);
    }

    public String PPUtoString() {
        return ("$" + ppu);
    }

    public String caloriestoString() {
        return Integer.toString(calories);
    }

    public String fatCaloriestoString() {
        return Double.toString(fatCalories);
    }

    public String fattoString() {
        return (fat + "g");
    }

    public String cholesteroltoString() {
        return (cholesterol + "mg");
    }

    public String sodiumtoString() {
        return (sodium + "mg");
    }

    public String carbstoString() {
        return (carbs + "g");
    }

    public String fibertoString() {
        return (fiber + "g");
    }

    public String sugartoString() {
        return (sugar + "g");
    }

    public String proteintoString() {
        return (protein + "g");
    }

    public String ingredientstoString() {
        return ingredients;
    }


}