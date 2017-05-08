package com.example.nils.grocerylist;

/**
 * Created by Nils on 5/8/17.
 */

public class ItemTest {

    public String name;

    public ItemTest() {
        name = "";
    }

    public ItemTest(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
