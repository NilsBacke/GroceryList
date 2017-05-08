package com.example.nils.grocerylist;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import java.io.IOException;
import java.io.InputStream;
import java.text.NumberFormat;
import java.util.ArrayList;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.example.nils.grocerylist.AlternateItems.InstructionsActivity;
import com.example.nils.grocerylist.Databases.AutoSaveDatabaseHelper;
import com.example.nils.grocerylist.Databases.DatabaseHelper;
import com.example.nils.grocerylist.Databases.SavedDatabaseHelper;
import com.example.nils.grocerylist.ListAdapters.CustomAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.HashMap;


public class MainActivity extends AppCompatActivity {
    TextView textView;
    ListView listView;
    ArrayList<Item> selecteditems;
    DatabaseHelper db;
    SavedDatabaseHelper dbsaved;
    AutoSaveDatabaseHelper dbautosave;
    int mode; //Price mode = 1, Health mode = 2
//    private DatabaseReference mDatabase;


    /**
     * When the main activity first loads this method is called.
     * The list view, text view, and array list of items are initialized.
     * The updatelist() method is called.
     * The database is initialized and cleared.
     * The readJSON() method is called.
     *
     * @param savedInstanceState The savedInstanceState of the app.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Get ListView object from xml
        listView = (ListView) findViewById(R.id.list);
        // Get TextView object from xml
        textView = (TextView)findViewById(R.id.totalPriceNewList);
        selecteditems = new ArrayList<Item>();
        mode = 1;                                     //Defaultly choose price mode
        setTitle("Price Mode");
        db = new DatabaseHelper(this);
        dbsaved = new SavedDatabaseHelper(this);
        dbautosave = new AutoSaveDatabaseHelper(this);
        updateList();
        db.clearDatabase("TABLE_ITEM");


        dbautosave.clearDatabase("TABLE_SAVED"); //delete after



        try {
            readJSON();
        } catch (IOException e) {
            e.printStackTrace();
        }

        selecteditems.addAll(dbautosave.getAllItems());





//        final FirebaseDatabase database = FirebaseDatabase.getInstance();
//        DatabaseReference ref = database.getReference("server/saving-data/fireblog/posts");
//
//// Attach a listener to read the data at our posts reference
//        ref.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                Post post = dataSnapshot.getValue(Post.class);
//                System.out.println(post);
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//                System.out.println("The read failed: " + databaseError.getCode());
//            }
//        });

    }

    /**
     * This method is called when the MainActivity is called in an intent of another activity.
     * An item object is retrieved from the activity passing the intent.
     * This item is added to the arraylist of items in the grocery list.
     * The updateList() method is called.
     * @param intent The passed intent.
     */
    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        overridePendingTransition(R.anim.slide_in2, R.anim.slide_out2);

        Item item = (Item) intent.getSerializableExtra("newitem");
        ArrayList<Item> itemslist = (ArrayList<Item>) intent.getSerializableExtra("Alternate List");
        Log.d("Intent: ", "Intent was passed.");
        if (item != null) {
            selecteditems.add(item);
            Log.d("Tried: ", selecteditems.toString());
        } else if (itemslist != null) {
            Log.d("List: ", itemslist.toString());
            selecteditems.clear();
            selecteditems.addAll(itemslist);
        } else {
            Log.d("Intent: ", "no intent found.");
        }

        updateList();

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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        dbautosave.clearDatabase("TABLE_ITEM");
        for (Item item : selecteditems) {
            dbautosave.addItem(item);
        }
    }

    /**
     * This method is called when a button on the options menu is pressed.
     * If the search button is pressed, it passes the intent to the SearchActivity activity.
     * If the deleteAll button is pressed, the list is cleared.
     * @param item The menu item.
     * @return Always true.
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.search_button:
                startActivity(new Intent(MainActivity.this, SearchActivity.class));
                return true;
            case R.id.deleteAll_button:
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);

                // set dialog message
                alertDialogBuilder.setMessage("Are you sure you want to delete all items in your list?")
                        .setPositiveButton("YES", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // Make toast
                                Toast.makeText(MainActivity.this,  "The list has been cleared.",
                                        Toast.LENGTH_SHORT).show();
                                // Remove all items
                                selecteditems.clear();
                                updateList();
                            }
                        })
                        .setNegativeButton("Cancel", null);

                // create alert dialog
                AlertDialog alertDialog = alertDialogBuilder.create();
                // show the alert dialog
                alertDialog.show();

                return true;
            case R.id.price_mode:
                mode = 1;
                Toast.makeText(MainActivity.this,  "Price mode set",
                        Toast.LENGTH_SHORT).show();
                setTitle("Price Mode");
                return true;
            case R.id.health_mode:
                mode = 2;
                Toast.makeText(MainActivity.this,  "Health mode set",
                        Toast.LENGTH_SHORT).show();
                setTitle("Health Mode");
                return true;
            case R.id.save_list:
                AlertDialog.Builder alertDialogBuilder2 = new AlertDialog.Builder(this);

                // set dialog message
                alertDialogBuilder2.setMessage("Are you sure you want to overwrite the current saved list with this list?")
                        .setPositiveButton("YES", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // Make toast
                                Toast.makeText(MainActivity.this,  "This list has been saved.",
                                        Toast.LENGTH_SHORT).show();
                                dbsaved.clearDatabase("items_saved");
                                for (Item listitem : selecteditems) {
                                    dbsaved.addItem(listitem);
                                }
                            }
                        })
                        .setNegativeButton("Cancel", null);

                // create alert dialog
                AlertDialog alertDialog2 = alertDialogBuilder2.create();
                // show the alert dialog
                alertDialog2.show();

                return true;
            case R.id.get_saved_list:
                AlertDialog.Builder alertDialogBuilder3 = new AlertDialog.Builder(this);

                // set dialog message
                alertDialogBuilder3.setMessage("Are you sure you want to replace your current list with the most recently saved list?")
                        .setPositiveButton("YES", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // Make toast
                                Toast.makeText(MainActivity.this,  "The list has been replaced.",
                                        Toast.LENGTH_SHORT).show();
                                selecteditems.clear();
                                selecteditems.addAll(dbsaved.getAllItems());
                                updateList();
                            }
                        })
                        .setNegativeButton("Cancel", null);

                // create alert dialog
                AlertDialog alertDialog3 = alertDialogBuilder3.create();
                // show the alert dialog
                alertDialog3.show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /**
     * This method is called when the MainActivity is resumed.
     * The updateList() method is called.
     */
    @Override
    public void onResume(){
        super.onResume();
        updateList();
    }

    /**
     * This method updates the CustomAdapter with the selecteditems array list.
     */
    private void updateList() {
        Log.d("Size: ", Integer.toString(selecteditems.size()));
        if (selecteditems.size() > 0) {
            Log.d("URL1: ", selecteditems.get(0).getpictureurl());
        }
        CustomAdapter adapter = new CustomAdapter(this, selecteditems);
        listView.setAdapter(adapter);
        getTotalPrice();
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
            String pictureurl;
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
                    pictureurl = jo_inside.getString("picture");
//                    int index = pictureurl.indexOf('s');
//                    pictureurl = pictureurl.substring(0, index) + pictureurl.substring(index + 1);
//                    pictureurl = pictureurl.substring(8);
                } catch (JSONException e) {
                    pictureurl = " ";
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
                    if (ingredients.equals(" ")) {
                        ingredients = "";
                    }
                } catch (JSONException e) {
                    ingredients = "";
                }


                // Adds values into ArrayList
                m_li = new HashMap<String, String>();
                m_li.put("name", name);
                m_li.put("price", price);
                m_li.put("priceper", each);
                m_li.put("pictureurl", pictureurl);
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

                Log.d("pictureurl: " , pictureurl);
                // A new item object is formed from all of the retreived data.
                Item newItem = new Item(i, name, doubleprice, doubleeach, pictureurl, calories, doubleFatCalories, doublefat,
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
        totalprice = (double) Math.round(totalprice*100.00)/100.00;
        NumberFormat fmt = NumberFormat.getCurrencyInstance();
        textView.setText("Total Price: " + fmt.format(totalprice));
    }

    public void AlternateItemsButton(View view) {
        Intent intent = new Intent(MainActivity.this, InstructionsActivity.class);
        intent.putExtra("Grocery List", selecteditems);
        intent.putExtra("Mode", mode);
        startActivity(intent);
        Log.d("Intent ","Intent is switched");
    }

}