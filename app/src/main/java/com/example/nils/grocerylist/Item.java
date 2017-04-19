package com.example.nils.grocerylist;

import java.io.Serializable;
import java.text.NumberFormat;
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

    /**
     * Constructor that initializes the item's id, name, price information, and nutritionial information.
     * @param id ID number for each item.
     * @param name Name of the item.
     * @param price The price of the item in US dollars.
     * @param ppu The price per unit of the item in US dollars.
     * @param calories The total number of calories per serving for the item.
     * @param fatCalories The number of calories from fat per serving for the item.
     * @param fat The amount of fat per serving for the item in grams.
     * @param cholesterol The amount of cholesterol per serving for the item in milligrams.
     * @param sodium The amount of sodium per serving for the item in milligrams.
     * @param carbs The amount of carbohydrates per serving for the item in grams.
     * @param fiber The amount of fiber per serving for the item in grams.
     * @param sugar The amount of sugar per serving for the item in grams.
     * @param protein The amount of protein per serving for the item in grams.
     * @param ingredients The list of ingredients for the item.
     */
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

    /**
     * Default constructor.
     */
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

    /**
     * Gets the id of the item.
     * @return The id of the item.
     */
    public int getId() {
        return id;
    }

    /**
     * Gets the name of the item.
     * @return The name of the item.
     */
    public String getName() {
        return name;
    }

    /**
     * Gets the price of the item.
     * @return The price of the item.
     */
    public Double getPrice() {
        return price;
    }

    /**
     * Gets the price per unit of the item.
     * @return The price per unit of the item.
     */
    public Double getPPU() {
        return ppu;
    }

    /**
     * Gets the number of calories for the item.
     * @return The number of calories for the item.
     */
    public int getCalories() {
        return calories;
    }

    /**
     * Gets the number of calories from fat for the item.
     * @return The number of calories from fat for the item.
     */
    public Double getFatCalories() {
        return fatCalories;
    }

    /**
     * Gets the amount of fat for the item.
     * @return The amount of fat for the item.
     */
    public Double getFat() {
        return fat;
    }

    /**
     * Gets the amount of cholesterol for the item.
     * @return The amount of cholesterol for the item.
     */
    public Double getCholesterol() {
        return cholesterol;
    }

    /**
     * Gets the amount of sodium for the item.
     * @return The amount of sodium for the item.
     */
    public Double getSodium() {
        return sodium;
    }

    /**
     * Gets the amount of carbohydrates for the item.
     * @return The amount of carbohydrates for the item.
     */
    public Double getCarbs() {
        return carbs;
    }

    /**
     * Gets the amount of fiber for the item.
     * @return The amount of fiber for the item.
     */
    public Double getFiber() {
        return fiber;
    }

    /**
     * Gets the amount of sugar for the item.
     * @return The amount of sugar for the item.
     */
    public Double getSugar() {
        return sugar;
    }

    /**
     * Gets the amount of protein for the item.
     * @return The amount of protein for the item.
     */
    public Double getProtein() {
        return protein;
    }

    /**
     * Gets the list of ingredients for the item.
     * @return The list of ingredients for the item.
     */
    public String[] getIngredients() {
        return ingredients.split(",");
    }

    /**
     * Gets the number of point for the item.
     * @return The number of points for the item.
     */
    public int getPoints() {return points;}

    /**
     * Sets the ID of the item to id.
     * @param id The new id of the item.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Sets the name of the item to name.
     * @param name The new name of the item.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Sets the price of the item to price.
     * @param price The new price of the item.
     */
    public void setPrice(Double price) {
        this.price = price;
    }

    /**
     * Sets the price per unit of the item to ppu.
     * @param ppu The new price per unit of the item.
     */
    public void setPPU(Double ppu) {
        this.ppu = ppu;
    }

    /**
     * Sets the number of calories for the item to calories.
     * @param calories The new number of calories for the item.
     */
    public void setCalories(int calories) {
        this.calories = calories;
    }

    /**
     * Sets the number of calories from fat for the item to fatCalories.
     * @param fatCalories The new number of caloires from fat for the item
     */
    public void setFatCalories(Double fatCalories) {
        this.fatCalories = fatCalories;
    }

    /**
     * Sets the amount of fat for the item to fat.
     * @param fat The new amount of fat for the item
     */
    public void setFat(Double fat) {
        this.fat = fat;
    }

    /**
     * Sets the amount of cholesterol for the item to cholesterol.
     * @param cholesterol The new amount of cholesterol of the item
     */
    public void setCholesterol(Double cholesterol) {
        this.cholesterol = cholesterol;
    }

    /**
     * Sets the amount of sodium for the item to sodium.
     * @param sodium The new amount of sodium for the item
     */
    public void setSodium(Double sodium) {
        this.sodium = sodium;
    }

    /**
     * Sets the amount of carbohydrates for the item to carbs.
     * @param carbs The new amount of carbohydrates for the item
     */
    public void setCarbs (Double carbs) {
        this.carbs = carbs;
    }

    /**
     * Sets the amount of fiber for the item to fiber.
     * @param fiber The new amount of fiber for the item
     */
    public void setFiber (Double fiber) {
        this.fiber = fiber;
    }

    /**
     * Sets the amount of sugar for the item to sugar.
     * @param sugar The new amount of sugar for the item
     */
    public void setSugar (Double sugar) {
        this.sugar = sugar;
    }

    /**
     * Sets the amount of protein for the item to protein.
     * @param protein The new amount of protein of the item
     */
    public void setProtein (Double protein) {
        this.protein = protein;
    }

    /**
     * Sets the list of ingredients for the item to ingredients.
     * @param ingredients The new list of ingredients for the item
     */
    public void setIngredients(String ingredients) {
        this.ingredients = ingredients;
    }


    @Override

    /**
     * Gets the name of the item as a string.
     * @return The name of the item as a string.
     */
    public String toString() {
        return name;
    }

    /**
     * Gets the price of the item as a string.
     * @return The price of the item as a string.
     */
    public String pricetoString() {
        NumberFormat fmt = NumberFormat.getCurrencyInstance();
        return fmt.format(price);
    }

    /**
     * Gets the price per unit of the item as a string.
     * @return The price per unit of the item as a string.
     */
    public String PPUtoString() {
        NumberFormat fmt = NumberFormat.getCurrencyInstance();
        return fmt.format(ppu);
    }

    /**
     * Gets the number of calories for the item as a string.
     * @return The number of calories for the item as a string.
     */
    public String caloriestoString() {
        return Integer.toString(calories);
    }

    /**
     * Gets the number of calories from fat for the item as a string.
     * @return The number of calories from fat for the item as a string.
     */
    public String fatCaloriestoString() {
        return Double.toString(fatCalories);
    }

    /**
     * Gets the amount of fat for the item as a string.
     * @return The amount of fat for the item as a string.
     */
    public String fattoString() {
        return (fat + "g");
    }

    /**
     * Gets the amount of cholesterol for the item as a string.
     * @return The amount of cholesterol for the item as a string.
     */
    public String cholesteroltoString() {
        return (cholesterol + "mg");
    }

    /**
     * Gets the amount of sodium for the item as a string.
     * @return The amount of sodium for the item as a string.
     */
    public String sodiumtoString() {
        return (sodium + "mg");
    }

    /**
     * Gets the amount of carbohydrates for the item as a string.
     * @return The amount of carbohydrates for the item as a string.
     */
    public String carbstoString() {
        return (carbs + "g");
    }

    /**
     * Gets the amount of fiber for the itemas a string.
     * @return The amount of fiber for the item as a string.
     */
    public String fibertoString() {
        return (fiber + "g");
    }

    /**
     * Gets the amount of sugar for the item as a string.
     * @return The amount of sugar for the item as a string.
     */
    public String sugartoString() {
        return (sugar + "g");
    }

    /**
     * Gets the amount of protein for the item as a string.
     * @return The amount of protein for the item as a string.
     */
    public String proteintoString() {
        return (protein + "g");
    }

    /**
     * Gets the list of ingredients for the item as a string.
     * @return The list of ingredients for the item as a string.
     */
    public String ingredientstoString() {
        return ingredients;
    }


}