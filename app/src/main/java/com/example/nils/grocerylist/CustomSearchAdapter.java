package com.example.nils.grocerylist;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ListAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.jar.Pack200;

/**
 * Created by Nils on 3/25/17.
 */

public class CustomSearchAdapter extends BaseAdapter implements ListAdapter {

    private ArrayList<Item> items;
    private Context context;

    /**
     * Constructs a new CustomSearchAdapter object from context and an array list of items.
     * @param context The context of the class that constructs the object.
     * @param list The array list of items.
     */
    public CustomSearchAdapter(Context context, ArrayList<Item> list) {
        this.items = list;
        this.context = context;
    }

    /**
     * This method returns the number of items in the list.
     * @return The size of the array list.
     */
    @Override
    public int getCount() {
        return items.size();
    }

    /**
     * This method returns an object at a given position.
     * @param pos The position of the item in the arraylist.
     * @return The item at the given position.
     */
    @Override
    public Object getItem(int pos) {
        return items.get(pos);
    }

    /**
     * This method returns the id of the item at a given position.
     * @param pos The position of the item in the arraylist.
     * @return The item's id at the given position.
     */
    @Override
    public long getItemId(int pos) {
        return items.get(pos).getId();
        //just return 0 if your list items do not have an Id variable.
    }

    /**
     * This method controls the view of an item in the arraylist.
     * @param position The position of the item.
     * @param convertView The view of the item.
     * @param parent The viewgroup of the item.
     * @return The altered view.
     */
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.search, null);
        }

        //Handle TextView and display string from your list
        TextView listItemText = (TextView)view.findViewById(R.id.search_item_name);
        // Sets each element of the list to the name of the corresponding item.
        listItemText.setText(items.get(position).getName());

        return view;
    }


}
