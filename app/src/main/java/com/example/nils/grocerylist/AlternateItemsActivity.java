package com.example.nils.grocerylist;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;

public class AlternateItemsActivity extends AppCompatActivity {

    ArrayList<Item> alternate;
    DatabaseHelper db;
    AlternateItemsHelper helper;
    ListView listview;
    CustomAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alternate_items);
        db = new DatabaseHelper(this);
        helper = new AlternateItemsHelper(this);
        listview = (ListView) findViewById(R.id.list);

    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        ArrayList<Item> items = (ArrayList<Item>) intent.getSerializableExtra("Grocery List");
        for (int i = 0; i < items.size(); i++) {
            helper.findAlternateItems(items.get(i));
        }

        adapter = new CustomAdapter(this, alternate);

    }




}
