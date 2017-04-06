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
            String jsonLocation = AssetJSONFile("test.json", this);
            JSONObject obj = new JSONObject(jsonLocation);
            String name = obj.getString("name");
            JSONArray itemsArray = obj.getJSONArray("Nutrition");
            ArrayList<HashMap<String, String>> itemsList = new ArrayList<HashMap<String, String>>();
            HashMap<String, String> m_li;

            for (int i = 0; i < itemsArray.length(); i++) {
                JSONObject jo_inside = itemsArray.getJSONObject(i);
//                Log.d("Details-->", jo_inside.getString("name"));

                String price = jo_inside.getString("price");
                String each = jo_inside.getString("PerUnit");
                String calories = jo_inside.getString("calories");
                String fatCalories = jo_inside.getString("fatCalories");
                String fat = jo_inside.getString("fat");
                String cholesterol = jo_inside.getString("cholesterol");
                String sodium = jo_inside.getString("Sodium");
                String carbs = jo_inside.getString("carbs");
                String fiber = jo_inside.getString("fiber");
                String sugar = jo_inside.getString("sugar");
                String protein = jo_inside.getString("protein");
                String ingredients = jo_inside.getString("ingredients");

                //Add your values in your `ArrayList` as below:
                m_li = new HashMap<String, String>();
                m_li.put("name", name);
                m_li.put("price", price);
                m_li.put("priceper", each);
                m_li.put("calories", calories);
                m_li.put("fatCalories", fatCalories);
                m_li.put("fat", fat);
                m_li.put("cholesterol", cholesterol);
                m_li.put("sodium", sodium);
                m_li.put("carbs", carbs);
                m_li.put("fiber", fiber);
                m_li.put("sugar", sugar);
                m_li.put("protein", protein);
                m_li.put("ingredients", ingredients);

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

                int intcalories = Integer.parseInt(calories);
                int intFatCalories = Integer.parseInt(fatCalories);

                fat = fat.substring(0, fat.length());
                int intfat = Integer.parseInt(fat);

                cholesterol = cholesterol.substring(0, cholesterol.length()-1);
                int intcholesterol = Integer.parseInt(cholesterol);

                sodium = sodium.substring(0, sodium.length()-1);
                int intsodium = Integer.parseInt(sodium);

                carbs = carbs.substring(0, carbs.length());
                int intcarbs = Integer.parseInt(carbs);

                fiber = fiber.substring(0, fiber.length());
                int intfiber = Integer.parseInt(fiber);

                sugar = sugar.substring(0, sugar.length());
                int intsugar = Integer.parseInt(sugar);

                protein = protein.substring(0, protein.length());
                int intprotein = Integer.parseInt(protein);

                Item newItem = new Item(name, doubleprice, doubleeach, intcalories, intFatCalories, intfat,
                        intcholesterol, intsodium, intcarbs, intfiber, intsugar, intprotein, ingredients);

                DatabaseTable.DatabaseOpenHelper db = new DatabaseTable.DatabaseOpenHelper(this);

                db.addItem(newItem);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


}