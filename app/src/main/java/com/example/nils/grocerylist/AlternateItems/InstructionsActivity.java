package com.example.nils.grocerylist.AlternateItems;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.nils.grocerylist.Item;
import com.example.nils.grocerylist.R;

import java.util.ArrayList;

public class InstructionsActivity extends AppCompatActivity {

    ArrayList<Item> itemlist;
    int mode;

    /**
     * This method changes the main activity to the instructions activity and gets the intent of the state.
     * @param savedInstanceState The current state of the app.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instructions);
        onNewIntent(getIntent());
        Log.d("onCreate:", "started");
    }

    /**
     * This method generates the text for the instructions activity and changes the view of the app.
     * @param intent The intent to the instructions activity.
     */
    @Override
    protected void onNewIntent(Intent intent) {
        Log.d("onNewIntent:", "started");
        super.onNewIntent(intent);
        overridePendingTransition(R.anim.slide_in, R.anim.slide_out);

        itemlist = (ArrayList<Item>) intent.getSerializableExtra("Grocery List");
        mode = (int) intent.getSerializableExtra("Mode");
        TextView text = (TextView) findViewById(R.id.instructions);
        String str = "";
        if (mode == 1) {
            str = "cheaper (based on price per unit)";
        } else {
            str = "healthier";
        }
        text.setText("getFood(); will now generate a new list containing similar and " + str +
                " items. Check all of the alternative items that you accept.\n\n" +
                "Unchecked items will not replace any items in your list.\n\n" +
                "Please wait for the new list to be generated.");
    }

    /**
     * This method provides functionality to the OK button by passing the intent to the alternate items activity.
     * @param view The current view of the app.
     */
    public void OKButton(View view) {
        Intent intent = new Intent(InstructionsActivity.this, AlternateItemsActivity.class);
        intent.putExtra("Grocery List", itemlist);
        intent.putExtra("Mode", mode);
        startActivity(intent);
    }

    /**
     * This method sets a slide transition to the activity.
     */
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_in2, R.anim.slide_out2);
    }
}
