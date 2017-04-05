package com.example.nils.grocerylist;
import android.app.SearchManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.AssetManager;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
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



//        JSONReader json = new JSONReader();

        try {
            readJSON();
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

    private String AssetJSONFile (String filename, Context context) throws IOException {
        AssetManager manager = context.getAssets();
        InputStream file = manager.open(filename);
        byte[] formArray = new byte[file.available()];
        file.read(formArray);
        file.close();

        return new String(formArray);
    }

    public void readJSON() throws IOException {
        try {
            String jsonLocation = AssetJSONFile("fruit.json", this);
            JSONObject obj = new JSONObject(jsonLocation);
            JSONArray itemsArray = obj.getJSONArray("Item");
            ArrayList<HashMap<String, String>> itemsList = new ArrayList<HashMap<String, String>>();
            HashMap<String, String> m_li;

            for (int i = 0; i < itemsArray.length(); i++) {
                JSONObject jo_inside = itemsArray.getJSONObject(i);
                Log.d("Details-->", jo_inside.getString("name"));
                String name = jo_inside.getString("name");
                String price = jo_inside.getString("price");
                String each = jo_inside.getString("each");

                //Add your values in your `ArrayList` as below:
                m_li = new HashMap<String, String>();
                m_li.put("name", name);
                m_li.put("price", price);
                m_li.put("each", each);

                itemsList.add(m_li);

                price = price.substring(1);
                Double doubleprice = Double.parseDouble(price);

                int start = 0;
                int end = 0;
                for (int j = 0; j < each.length(); j++) {
                    if (each.charAt(j) == '$') {
                        start = j + 1;
                    }
                    if (each.charAt(j) == '/') {
                        end = j - 1;
                    }
                }
                each = each.substring(start, end);
                Double doubleeach = Double.parseDouble(each);

                Item newItem = new Item(name, doubleprice, doubleeach);

                DatabaseTable.DatabaseOpenHelper db = new DatabaseTable.DatabaseOpenHelper(this);

                db.addItem(newItem);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


}