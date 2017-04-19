package com.example.nils.grocerylist;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import java.util.ArrayList;

public class AlternateItemsActivity extends AppCompatActivity {

    ArrayList<Item> alternate;
    DatabaseHelper db;
    AlternateItemsHelper helper;
    ListView listview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alternate_items);
        db = new DatabaseHelper(this);
        helper = new AlternateItemsHelper(this);
        listview = (ListView) findViewById(R.id.list);
        Log.d("On create: ", "new intent");
        onNewIntent(getIntent());

    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        ArrayList<Item> items = (ArrayList<Item>) intent.getSerializableExtra("Grocery List");
        for (int i = 0; i < items.size(); i++) {
            helper.findAlternateItems(items.get(i));
        }
        updateList();
        Log.d("On next intent: ", "new intent");

    }

    /**
     * This method updates the CustomAdapter with the selecteditems array list.
     */
    private void updateList() {
        CustomAdapter adapter = new CustomAdapter(this, helper.getAlternateItemsList());
        listview.setAdapter(adapter);
    }




}
