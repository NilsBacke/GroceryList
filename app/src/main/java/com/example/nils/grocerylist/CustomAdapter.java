package com.example.nils.grocerylist;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.ListAdapter;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;

/**
 * Created by Nils on 3/25/17.
 */

public class CustomAdapter extends BaseAdapter implements ListAdapter {

    private ArrayList<Item> items;
    private Context context;
    private TextView textView;

    public CustomAdapter(Context context, ArrayList<Item> list, TextView textView) {
        this.items = list;
        this.context = context;
    }

    public CustomAdapter(Context context) {
        items = new ArrayList<Item>();
        this.context = context;
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(int pos) {
        return items.get(pos);
    }

    @Override
    public long getItemId(int pos) {
        return items.get(pos).getId();
        //just return 0 if your list items do not have an Id variable.
    }

    public void addItem(Item item) {
        items.add(item);
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.item_info, null);
        }

        //Handle TextView and display string from your list
        TextView listItemText = (TextView)view.findViewById(R.id.item_name);
        listItemText.setText(items.get(position).getName());

        //Handle buttons and add onClickListeners
        ImageButton deleteButton = (ImageButton) view.findViewById(R.id.deleteButton);
        CheckBox cb = (CheckBox) view.findViewById(R.id.checkbox);

        deleteButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);

                // set dialog message
                alertDialogBuilder.setMessage("Are you sure you want to delete?")
                        .setPositiveButton("YES", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Toast.makeText(context, items.get(position).getName() + " has been removed.",
                                        Toast.LENGTH_SHORT).show();
                                items.remove(position);
                                notifyDataSetChanged();
                            }
                        })
                .setNegativeButton("Cancel", null);

                // create alert dialog
                AlertDialog alertDialog = alertDialogBuilder.create();
                // show it
                alertDialog.show();


            }
        });

        View.OnClickListener yourClickListener = new View.OnClickListener() {
            public void onClick(View v) {
                android.app.AlertDialog.Builder alertDialogBuilder = new android.app.AlertDialog.Builder(context);

                String string = ("Price: " + items.get(position).pricetoString() + "\n"
                        + "Price Per Unit: " + items.get(position).PPUtoString() + "\n"
                        + "Calories: " + items.get(position).caloriestoString() + "\n"
                        + "Calories from fat: " + items.get(position).fatCaloriestoString() + "\n"
                        + "Fat: " + items.get(position).fattoString() + "\n"
                        + "Cholesterol: " + items.get(position).cholesteroltoString() + "\n"
                        + "Sodium: " + items.get(position).sodiumtoString() + "\n"
                        + "Carbs: " + items.get(position).carbstoString() + "\n"
                        + "Fiber: " + items.get(position).fibertoString() + "\n"
                        + "Sugar: " + items.get(position).sugartoString() + "\n"
                        + "Protein: " + items.get(position).proteintoString() + "\n\n"
                        + "Ingredients: " + items.get(position).ingredientstoString() + "\n");

                // set dialog message
                alertDialogBuilder.setTitle(items.get(position).getName())
                        .setMessage(string)
                        .setNegativeButton("Exit", null);

                // create alert dialog
                android.app.AlertDialog alertDialog = alertDialogBuilder.create();
                // show it
                alertDialog.show();
            }
        };

        view.setOnClickListener(yourClickListener);

        return view;
    }


}
