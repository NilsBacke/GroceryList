package com.example.nils.grocerylist;

import android.app.SearchManager;
import android.content.Intent;
import android.database.Cursor;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Locale;

import android.os.Handler;

public class SearchActivity extends AppCompatActivity implements SearchView.OnQueryTextListener, SearchView.OnCloseListener {

    DatabaseHelper db;
    ListView searchlistView;
    CustomSearchAdapter searchadapter;
    SearchView sv;
    ArrayList<Item> itemlist;

    /**
     * This method is called when the SearchActivity first loads.
     * The handle intent method is called.
     * The database is initialized using the class' context.
     * The generateSearchList() method is called.
     * A searchadapter object is constructed from the context of the class and from all of the items in the virtual database.
     * An onItemClickListener is made, for when an item is clicked on the intent is passed to the MainActivity
     *       and a Toast appears saying that the selected item was added to the list in the MainActivity.
     * @param savedInstanceState The savedInstanceState of the app.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        overridePendingTransition(R.anim.slide_in, R.anim.slide_out);

        db = new DatabaseHelper(this);
        generateSearchList();
        itemlist = db.getAllItems();

        searchadapter = new CustomSearchAdapter(this, db.getAllItems());
        searchlistView.setTextFilterEnabled(true);
        searchlistView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                Log.d("Intent passed: ", "passed1.");
                Item item = (Item) searchadapter.getItem(position);
                Toast.makeText(getApplicationContext(),
                        item.getName() + " was added to the list.", Toast.LENGTH_SHORT)
                        .show();
                Intent intent = new Intent(SearchActivity.this, MainActivity.class);

                // The new item that needs to be added to the list in the MainActivity is passed through the intent in an extra.
                Log.d("Intent passed: ", "passed.");
                intent.putExtra("newitem", item);
                startActivity(intent);

            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_in2, R.anim.slide_out2);
    }

    @Override
    public boolean onClose() {
        searchlistView.setAdapter(searchadapter);
        return false;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.searchmenu, menu);
        MenuItem searchItem = menu.findItem(R.id.menu_search);
        sv = (SearchView) MenuItemCompat.getActionView(searchItem);
        setupSearchView();
        return true;
    }

    /**
     * This method creates a list of items from the CustomSearchAdapter class.
     */
    private void generateSearchList() {
        CustomSearchAdapter searchadapter = new CustomSearchAdapter(this, db.getAllItems());
        searchlistView = (ListView) findViewById(R.id.searchlist);
        searchlistView.setAdapter(searchadapter);
    }

    private void setupSearchView() {
        sv.setIconifiedByDefault(false);
        sv.setOnQueryTextListener(this);
        sv.setSubmitButtonEnabled(true);
        sv.setQueryHint("Search Here");
    }


    public boolean onQueryTextChange(String newText) {
        if (TextUtils.isEmpty(newText)) {
            searchlistView.clearTextFilter();
        } else {
            searchlistView.setFilterText(newText.toString().toLowerCase());
            searchadapter.filter(newText.toLowerCase());

        }
        return true;
    }

    public boolean onQueryTextSubmit(String query) {
        return false;
    }



}
