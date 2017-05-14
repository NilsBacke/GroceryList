package com.example.nils.grocerylist.AlternateItems;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.example.nils.grocerylist.Databases.DatabaseHelper;
import com.example.nils.grocerylist.Item;
import com.example.nils.grocerylist.ListAdapters.CustomAlternateAdapter;
import com.example.nils.grocerylist.MainActivity;
import com.example.nils.grocerylist.R;

import java.text.NumberFormat;
import java.util.ArrayList;

public class AlternateItemsActivity extends AppCompatActivity {

    DatabaseHelper db;
    AlternateItemsHelper helper;
    ListView alternatelistview;
    TextView totalpricetext;
    ArrayList<Item> newlist;
    ArrayList<Item> origlist;

    /**
     * This method is called when this activity is created.
     * The database, ArrayList, ListView, and TextView are all initialized.
     * The onNewIntent method is called.
     * @param savedInstanceState The current state of the app.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.alternate_items_activity);
        overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
        db = new DatabaseHelper(this);
        newlist = new ArrayList<Item>();
        alternatelistview = (ListView) findViewById(R.id.list);
        totalpricetext = (TextView) findViewById(R.id.totalPriceNewList);
        Log.d("On create: ", "new intent");
        onNewIntent(getIntent());

    }

    /**
     * This method is called when the activity is started.
     */
    @Override
    protected void onStart() {
        super.onStart();
        onNewIntent(getIntent());
    }

    /**
     * This method is automatically called when a new intent is passed to this activity.
     * @param intent The passed intent.
     */
    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        origlist = (ArrayList<Item>) intent.getSerializableExtra("Grocery List");
        int mode = (int) intent.getSerializableExtra("Mode");
        helper = new AlternateItemsHelper(this, mode);
        for (int i = 0; i < origlist.size(); i++) {
            helper.findAlternateItems(origlist.get(i));
        }
        newlist = helper.getAlternateItemsList();
        updateLists();
        Log.d("On next intent: ", "new intent");

    }

    /**
     * This method is called when the back button is pressed.
     * The transition is set for transitioning between activities.
     */
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_in2, R.anim.slide_out2);
    }


    /**
     * This method updates the CustomAdapter with the selecteditems array list.
     */
    private void updateLists() {
        CustomAlternateAdapter adapter = new CustomAlternateAdapter(this, newlist, origlist);
        alternatelistview.setAdapter(adapter);
        getTotalPrice();
    }

    /**
     * This method sets the textView to the accumulative price of all of the items in the list.
     */
    public void getTotalPrice() {
        Double totalprice = 0.;

        for (int i = 0; i < newlist.size(); i++) {
            totalprice += newlist.get(i).getPrice();
        }
        totalprice = (double) Math.round(totalprice*100.00)/100.00;
        NumberFormat fmt = NumberFormat.getCurrencyInstance();
        totalpricetext.setText("Total Price: " + fmt.format(totalprice));
    }

    /**
     * This method replaces the original list with all of the checked items.
     * If an item is unchecked, the corresponding item in the original list is unchanged.
     * The intent is passed to the main activity.
     * @param view The current view of the activity.
     */
    public void replaceOriginalList(View view) {
        Intent intent = new Intent(AlternateItemsActivity.this, MainActivity.class);
        for (int i = 0; i < newlist.size(); i++) {
            if (!newlist.get(i).isSelected()) {
                newlist.set(i, origlist.get(i));
            }
        }
        intent.putExtra("Alternate List", newlist);
        startActivity(intent);

    }

}
