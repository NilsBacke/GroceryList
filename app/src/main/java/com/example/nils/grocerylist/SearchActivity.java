package com.example.nils.grocerylist;

import android.app.SearchManager;
import android.content.Intent;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

public class SearchActivity extends AppCompatActivity implements SearchView.OnQueryTextListener {

    DatabaseHelper db;
    ListView searchlistView;
    CustomSearchAdapter searchadapter;
    SearchView sv;

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
        handleIntent(getIntent());
        db = new DatabaseHelper(this);
        generateSearchList();
        searchadapter = new CustomSearchAdapter(this, db.getAllItems());
        searchlistView.setTextFilterEnabled(true);
        searchlistView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                Item item = (Item) searchadapter.getItem(position);
                String itemname = item.getName();
                Toast.makeText(getApplicationContext(),
                        itemname + " was added to the list.", Toast.LENGTH_LONG)
                        .show();
                Intent intent = new Intent(SearchActivity.this, MainActivity.class);

                // The new item that needs to be added to the list in the MainActivity is passed through the intent in an extra.
                intent.putExtra("newitem", item);
                startActivity(intent);

            }
        });

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

    /**
     * This method is called when an intent is passed to this activity.
     * The handleIntent method is called.
     * @param intent
     */
    @Override
    protected void onNewIntent(Intent intent) {
        handleIntent(intent);
    }

    private void handleIntent(Intent intent) {

        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
            DatabaseHelper db = new DatabaseHelper(this);
//            Cursor c = db.getItemMatches(query, null);

        }
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.menu_search) {
            return true;
        }
        return super.onOptionsItemSelected(item);
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
            searchlistView.setFilterText(newText.toString());
        }
        return true;
    }

    public boolean onQueryTextSubmit(String query) {
        return false;
    }
}
