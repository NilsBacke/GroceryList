package com.example.nils.grocerylist;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import java.text.NumberFormat;
import java.util.ArrayList;

public class AlternateItemsActivity extends AppCompatActivity {

    DatabaseHelper db;
    AlternateItemsHelper helper;
    ListView alternatelistview;
    TextView totalpricetext;
    ArrayList<Item> newlist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.alternate_items_activity);
        overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
        db = new DatabaseHelper(this);
        helper = new AlternateItemsHelper(this);
        newlist = new ArrayList<Item>();
        alternatelistview = (ListView) findViewById(R.id.list);
        totalpricetext = (TextView) findViewById(R.id.totalPriceNewList);
        Log.d("On create: ", "new intent");
        onNewIntent(getIntent());

    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        ArrayList<Item> items = (ArrayList<Item>) intent.getSerializableExtra("Grocery List");
        for (int i = 0; i < items.size(); i++) {
            helper.findAlternateItems(items.get(i));
        }
        newlist = helper.getAlternateItemsList();
        updateLists();
        Log.d("On next intent: ", "new intent");

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_in2, R.anim.slide_out2);
    }


    /**
     * This method updates the CustomAdapter with the selecteditems array list.
     */
    private void updateLists() {
        CustomAdapter adapter = new CustomAdapter(this, newlist);
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

    public void replaceOriginalList(View view) {

        Intent intent = new Intent(AlternateItemsActivity.this, MainActivity.class);
        intent.putExtra("Alternate List", newlist);
        startActivity(intent);

    }




}
