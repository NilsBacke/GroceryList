package com.example.nils.grocerylist;

import android.app.SearchManager;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

public class SearchActivity extends AppCompatActivity {

    DatabaseHelper db;
    ListView searchlistView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        handleIntent(getIntent());
        db = new DatabaseHelper(this);
        generateSearchList();
        final CustomSearchAdapter searchadapter = new CustomSearchAdapter(this, db.getAllItems());
        searchlistView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                Toast.makeText(getApplicationContext(),
                        searchadapter.getItem(position).getName() + " was added to the list.", Toast.LENGTH_LONG)
                        .show();
                Intent intent = new Intent(SearchActivity.this, MainActivity.class);
                Item item = searchadapter.getItem(position);
                Log.d("Retrieved item: ", item.getName());
                intent.putExtra("newitem", item);
                startActivity(intent);

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




}
