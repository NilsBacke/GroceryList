package com.example.nils.grocerylist;

import android.widget.CheckBox;

import java.util.ArrayList;

/**
 * Created by Nils on 3/22/17.
 */
//hi
public class Item {

    private String name;
    private Double price;

    public Item(String name, Double price) {
        this.name = name;
        this.price = price;
    }

    public Item(String name) {
        this.name = name;
    }
    public Item() {
        name = "";
        this.price = price;
    }

    public Item(String name, CheckBox check) {
        this.name = name;
    }

    public Double getPrice() {
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