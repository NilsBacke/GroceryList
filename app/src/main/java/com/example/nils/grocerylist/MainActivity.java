package com.example.nils.grocerylist;
import android.app.SearchManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ListView;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    ListView listView ;
    ArrayList<Item> items;
    DatabaseTable db = new DatabaseTable(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Get ListView object from xml
        listView = (ListView) findViewById(R.id.list);
        items = new ArrayList<Item>();
        updateList();

        DatabaseTable.DatabaseOpenHelper db = new DatabaseTable.DatabaseOpenHelper(this);



        JSONReader json = new JSONReader();
        try {
            json.readJSON();
        } catch (IOException e) {
            e.printStackTrace();
        }




        // Inserting Items/Rows
        Log.d("Insert: ", "Inserting ..");
        db.addItem(new Item());

        // Reading all items
        Log.d("Reading: ", "Reading all items..");
        List<Item> items = db.getAllItems();

        for (Item item : items) {
            String log = "Id: "  + item.getId() + " ,Name: " + item.getName() + " ,Price: " + item.getPrice() + " ,PPU: " + item.getPPU();
            // Writing items to log
            Log.d("Item: : ", log);
        }



    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_add_task:
                final EditText taskEditText = new EditText(this);
                AlertDialog dialog = new AlertDialog.Builder(this)
                        .setTitle("Add a new item")
                        .setMessage("What is the name of the item you want to add?")
                        .setView(taskEditText)
                        .setPositiveButton("Add", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                String task = String.valueOf(taskEditText.getText());
                                items.add(new Item(task));
                                updateList();
                            }
                        })
                        .setNegativeButton("Cancel", null)
                        .create();
                dialog.show();

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void updateList() {
        CustomAdapter adapter = new CustomAdapter(this, items);
        listView.setAdapter(adapter);
    }

    private void handleIntent(Intent intent) {

        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
            Cursor c = db.getWordMatches(query, null);
            //process Cursor and display results
        }
    }


}