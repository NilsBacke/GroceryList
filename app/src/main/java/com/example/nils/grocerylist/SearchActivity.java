package com.example.nils.grocerylist;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

public class SearchActivity extends AppCompatActivity {

    DatabaseHelper db = new DatabaseHelper(this);
    ListView searchlistView;
    private MainActivity mainActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        handleIntent(getIntent());
        generateSearchList();
        final CustomSearchAdapter searchadapter = new CustomSearchAdapter(this, db.getAllItems());
        searchlistView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.d("List of Items: ", db.getAllItems().toString());
                Log.d("On Click: ", searchadapter.getItem(position).getName() + " was clicked on");
                addItemfromSearch(searchadapter.getItem(position));
            }
        });
    }

    private void generateSearchList() {
        CustomSearchAdapter searchadapter = new CustomSearchAdapter(this, db.getAllItems());
        searchlistView = (ListView) findViewById(R.id.searchlist);
        searchlistView.setAdapter(searchadapter);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        handleIntent(intent);
    }

    private void handleIntent(Intent intent) {

        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
            DatabaseHelper db = new DatabaseHelper(this);
            Cursor c = db.getItemMatches(query, null);

        }
    }

    private void addItemfromSearch(Item item) {
        mainActivity.addItemToList(item);
        startActivity(new Intent(SearchActivity.this, MainActivity.class));
    }
}
