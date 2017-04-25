package com.example.nils.grocerylist;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import java.util.ArrayList;

public class AlternateItemsActivity extends AppCompatActivity {

    ArrayList<Item> orig;
    DatabaseHelper db;
    AlternateItemsHelper helper;
    ListView alternatelistview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.alternate_items_activity);
        orig = new ArrayList<Item>();
        db = new DatabaseHelper(this);
        helper = new AlternateItemsHelper(this);
        alternatelistview = (ListView) findViewById(R.id.list);
        Log.d("On create: ", "new intent");
        onNewIntent(getIntent());

    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        ArrayList<Item> items = (ArrayList<Item>) intent.getSerializableExtra("Grocery List");
        orig = items;
        for (int i = 0; i < items.size(); i++) {
            helper.findAlternateItems(items.get(i));
        }
        updateLists();
        Log.d("On next intent: ", "new intent");

    }

    /**
     * This method updates the CustomAdapter with the selecteditems array list.
     */
    private void updateLists() {
        CustomAdapter adapter = new CustomAdapter(this, helper.getAlternateItemsList());
        alternatelistview.setAdapter(adapter);
    }




}
