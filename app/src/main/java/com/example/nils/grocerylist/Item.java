package com.example.nils.grocerylist;

import java.util.ArrayList;

/**
 * Created by Nils on 3/22/17.
 */

public class Item {

    private String name;
    private Double price, ppu;
    private int calories, sugar, protein, totalfat;
    private int id;
    public ArrayList<String> ingredients;

    public Item(int id, String name, Double price, Double ppu, int calories, int sugar, int protein, int totalfat, ArrayList<String> ingredients) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.ppu = ppu;
        this.calories = calories;
        this.sugar = sugar;
        this.protein = protein;
        this.totalfat = totalfat;
        this.ingredients = ingredients;
    }

    public Item() {
        id = 0;
        name = "";
        price = 0.;
        ppu = 0.;
        calories = 0;
        sugar = 0;
        protein = 0;
        totalfat = 0;
    }

    public Item(String name) {
        id = 0;
        this.name = name;
        price = 0.;
        ppu = 0.;
        calories = 0;
        sugar = 0;
        protein = 0;
        totalfat = 0;
    }

    public Item(String name, Double price, Double ppu) {
        this.id = 0;
        this.name = name;
        this.price = price;
        this.ppu = ppu;
        this.calories = 0;
        this.sugar = 0;
        this.protein = 0;
        this.totalfat = 0;
    }

    public Item(int id, String name, Double price, Double ppu) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.ppu = ppu;
        this.calories = 0;
        this.sugar = 0;
        this.protein = 0;
        this.totalfat = 0;
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

    public int getSugar() {
        return sugar;
    }

    public int getProtein() {
        return protein;
    }

    public int getTotalfat() {
        return totalfat;
    }

    public ArrayList<String> getIngredients() {
        return ingredients;
    }

    ;


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

    public void setSugar(int sugar) {
        this.sugar = sugar;
    }

    public void setProtein(int protein) {
        this.protein = protein;
    }

    public void setTotalfat(int totalfat) {
        this.totalfat = totalfat;
    }

    public void setIngredients(ArrayList<String> ingredients) {
        this.ingredients = ingredients;
    }

    ;


    @Override
    public String toString() {
        return name;
    }


}