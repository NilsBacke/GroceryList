package com.example.nils.grocerylist;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import java.text.NumberFormat;
import java.util.ArrayList;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.example.nils.grocerylist.AlternateItems.InstructionsActivity;
import com.example.nils.grocerylist.Databases.AutoSaveDatabaseHelper;
import com.example.nils.grocerylist.Databases.DataWrapper;
import com.example.nils.grocerylist.Databases.DatabaseHelper;
import com.example.nils.grocerylist.Databases.SavedDatabaseHelper;
import com.example.nils.grocerylist.ListAdapters.CustomAdapter;

public class MainActivity extends AppCompatActivity {
    TextView textView;
    ListView listView;
    ArrayList<Item> selecteditems;
    DatabaseHelper db;
    SavedDatabaseHelper dbsaved;
    AutoSaveDatabaseHelper dbautosave;
    int mode; //Price mode = 1, Health mode = 2


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

        selecteditems.addAll(dbautosave.getAllItems());


        if (db.getAllItems().isEmpty()) {
            refreshfirebasedata();
        }

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
            case R.id.refresh:
                refreshfirebasedata();
                Toast.makeText(MainActivity.this,  "Please wait while the item data refreshes.",
                        Toast.LENGTH_LONG).show();
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

    public void refreshfirebasedata() {
        db.clearDatabase("TABLE_ITEMS");
        DataWrapper data = new DataWrapper(this);
        Log.d("Oncreate: ", "getItem");
        data.getItems();
    }

}