package com.example.nils.grocerylist;

import android.widget.CheckBox;

import java.util.ArrayList;

/**
 * Created by Nils on 3/22/17.
 */
//hi
public class Item {

    private String name;
    private ArrayList<Double> price;

    public Item(String name, Double price) {
        this.name = name;
        this.price = new ArrayList<Double>();
        this.price.add(price);
    }

    public Item(String name) {
        this.name = name;
    }
    public Item() {
        name = "";
        this.price = new ArrayList<Double>();
    }

    public Item(String name, CheckBox check) {
        this.name = name;
    }

    public ArrayList<Double> getPrices() {
        return price;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return name;
    }


}