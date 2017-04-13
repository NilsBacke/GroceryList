package com.example.nils.grocerylist;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import android.util.Log;
import android.widget.TextView;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.HashMap;


public class MainActivity extends AppCompatActivity {
    TextView textView;
    ListView listView;
    ArrayList<Item> selecteditems;
    DatabaseHelper db;


    /**
     * When the main activity first loads this method is called.
     * The list view, text view, and array list of items are initialized.
     * The updatelist() method is called.
     * The database is initialized and cleared.
     * The readJSON() method is called.
     * @param savedInstanceState The savedInstanceState of the app.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Get ListView object from xml
        listView = (ListView) findViewById(R.id.list);
        // Get TextView object from xml
        textView = (TextView)findViewById(R.id.totalPrice);
        selecteditems = new ArrayList<Item>();
        db = new DatabaseHelper(this);
        updateList();
        db.clearDatabase("items_tables");
        try {
            readJSON();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * This method is called when the MainActivity is called in an intent of another activity.
     * An item object is retrieved from the activity passing the intent.
     * This item is added to the arraylist of items in the grocery list.
     * The updateList() method is called.
     * The getTotalPrice() method is called.
     * @param intent The passed intent.
     */
    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        Item item = (Item) intent.getSerializableExtra("newitem");
        selecteditems.add(item);
        updateList();
        getTotalPrice();

    }

    /**
     * This method is called upon startup to create the options menu.
     * Gets the menu object from the xml.
     * @param menu The options menu.
     * @return Always true.
     */
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    /**
     * This method is called when a button on the options menu is pressed.
     * It passes the intent to the SearchActivity activity.
     * @param item The menu item.
     * @return Always true.
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        startActivity(new Intent(MainActivity.this, SearchActivity.class));
        return true;
    }

    /**
     * This method is called when the MainActivity is resumed.
     * The updateList() method is called.
     * The getTotalPrice() method is called.
     */
    @Override
    public void onResume(){
        super.onResume();
        updateList();
        getTotalPrice();

    }

    /**
     * This method updates the CustomAdapter with the selecteditems array list.
     */
    private void updateList() {
        CustomAdapter adapter = new CustomAdapter(this, selecteditems);
        listView.setAdapter(adapter);
    }

    /**
     * This method retrieves the JSON file from a given file name.
     * @param filename The filename of the JSON file.
     * @param context The context of the MainActivity.
     * @return A string formed from an array of bytes from the JSON file.
     * @throws IOException Necessary for file retrieving and reading.
     */
    private String AssetJSONFile (String filename, Context context) throws IOException {
        AssetManager manager = context.getAssets();
        InputStream file = manager.open(filename);
        byte[] formArray = new byte[file.available()];
        file.read(formArray);
        file.close();

        return new String(formArray);
    }

    /**
     * This method reads and parses through the given JSON file.
     * The item data is put into the virtual data table.
     * @throws IOException Necessary for file retrieving and reading.
     */
    public void readJSON() throws IOException {
        try {
            String jsonLocation = AssetJSONFile("sample.json", this);
            JSONObject obj = new JSONObject(jsonLocation);

            JSONArray itemsArray = obj.getJSONArray("Fruit");
            ArrayList<HashMap<String, String>> itemsList = new ArrayList<HashMap<String, String>>();
            HashMap<String, String> m_li;
            String name, price, each, fatCalories, fat, cholesterol, sodium, carbs, fiber, sugar, protein, ingredients;
            int calories;

            // Loops through every item and gets all of the nutrition label information.
            for (int i = 0; i < itemsArray.length(); i++) {
                JSONObject jo_inside = itemsArray.getJSONObject(i);
                Log.d("JSON length: ", Integer.toString(itemsArray.length()));
                Log.d("Details-->", jo_inside.getString("name"));

                try {
                    name = jo_inside.getString("name");
                } catch (JSONException e) {
                    name = " ";
                }
                try {
                    price = jo_inside.getString("Price");
                } catch (JSONException e) {
                    price = "0";
                }
                try {
                    each = jo_inside.getString("PerUnit");
                } catch (JSONException e) {
                    each = "0";
                }
                try {
                    calories = jo_inside.getInt("calories");
                } catch (JSONException e) {
                    calories = 0;
                }
                try {
                    fatCalories = jo_inside.getString("fatCalories");
                } catch (JSONException e) {
                    fatCalories = "0";
                }
                try {
                    fat = jo_inside.getString("fat");
                } catch (JSONException e) {
                    fat = "0";
                }
                try {
                    cholesterol = jo_inside.getString("cholesterol");
                } catch (JSONException e) {
                    cholesterol = "0";
                }
                try {
                    sodium = jo_inside.getString("sodium");
                } catch (JSONException e) {
                    sodium = "0";
                }
                try {
                    carbs = jo_inside.getString("carbs");
                } catch (JSONException e) {
                    carbs = "0";
                }
                try {
                    fiber = jo_inside.getString("fiber");
                } catch (JSONException e) {
                    fiber = "0";
                }
                try {
                    sugar = jo_inside.getString("sugar");
                } catch (JSONException e) {
                    sugar = "0";
                }

                try {
                    protein = jo_inside.getString("protein");
                } catch (JSONException e) {
                    protein = "0";
                }
                try {
                    ingredients = jo_inside.getString("ingredients");
                } catch (JSONException e) {
                    ingredients = " ";
                }


                // Adds values into ArrayList
                m_li = new HashMap<String, String>();
                m_li.put("name", name);
                m_li.put("price", price);
                m_li.put("priceper", each);
                m_li.put("calories", Integer.toString(calories));
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

                // A double is formed from the price data by removing the '$'
                price = price.substring(1);
                Double doubleprice = Double.parseDouble(price);

                // A double "each" (PPU) is formed by removing all of the characters except for the relevant numbers.
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

                // A double is formed from the retrieved calories from fat
                Double doubleFatCalories = Double.parseDouble(fatCalories);

                // A double is formed from reformatting the fat.
                fat = fat.substring(0, fat.length()-1);
                Double doublefat = Double.parseDouble(fat);

                // A double is formed from reformatting the cholesterol.
                if (cholesterol.length() >= 2) {
                    cholesterol = cholesterol.substring(0, cholesterol.length()-2);
                }
                Double doublecholesterol = Double.parseDouble(cholesterol);

                // A double is formed from reformatting the sodium.
                sodium = sodium.substring(0, sodium.length()-2);
                Double doublesodium = Double.parseDouble(sodium);

                // A double is formed from reformatting the carbs.
                carbs = carbs.substring(0, carbs.length()-1);
                Double doublecarbs = Double.parseDouble(carbs);

                // A double is formed from reformatting the fiber
                fiber = fiber.substring(0, fiber.length()-1);
                Double doublefiber = Double.parseDouble(fiber);

                // A double is formed from reformatting the sugar.
                sugar = sugar.substring(0, sugar.length()-1);
                Double doublesugar = Double.parseDouble(sugar);

                // A double is formed from reformatting the protein.
                protein = protein.substring(0, protein.length()-1);
                Double doubleprotein = Double.parseDouble(protein);

                // A new item object is formed from all of the retreived data.
                Item newItem = new Item(i, name, doubleprice, doubleeach, calories, doubleFatCalories, doublefat,
                        doublecholesterol, doublesodium, doublecarbs, doublefiber, doublesugar, doubleprotein, ingredients);

                // The new item is added to the virtual data table.
                db.addItem(newItem);
            }


        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /**
     * This method sets the textView to the accumulative price of all of the items in the list.
     */
    public void getTotalPrice() {
        Double totalprice = 0.;

        for (int i = 0; i < selecteditems.size(); i++) {
            totalprice += selecteditems.get(i).getPrice();
        }

        textView.setText("Total Price: $" + Double.toString(totalprice));
    }

}