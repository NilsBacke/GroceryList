package com.example.nils.grocerylist;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ListAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Locale;
import java.util.jar.Pack200;

/**
 * Created by Nils on 3/25/17.
 */

public class CustomSearchAdapter extends BaseAdapter implements Filterable{

    private ArrayList<Item> origitems;
    private ArrayList<Item> filtereditems;
    private Context context;

    /**
     * Constructs a new CustomSearchAdapter object from context and an array list of items.
     *
     * @param context The context of the class that constructs the object.
     * @param list    The array list of items.
     */
    public CustomSearchAdapter(Context context, ArrayList<Item> list) {
        this.context = context;
        filtereditems = list;
        origitems = new ArrayList<Item>();
        origitems.addAll(filtereditems);
    }

    /**
     * This method returns the number of items in the list.
     *
     * @return The size of the array list.
     */
    @Override
    public int getCount() {
        return filtereditems.size();
    }

    /**
     * This method returns an object at a given position.
     *
     * @param pos The position of the item in the arraylist.
     * @return The item at the given position.
     */
    @Override
    public Object getItem(int pos) {
        Log.d("Get item (Array Length)", Integer.toString(filtereditems.size()));
        return filtereditems.get(pos);
    }

    /**
     * This method returns the id of the item at a given position.
     *
     * @param pos The position of the item in the arraylist.
     * @return The item's id at the given position.
     */
    @Override
    public long getItemId(int pos) {
        return filtereditems.get(pos).getId();
        //just return 0 if your list items do not have an Id variable.
    }

    /**
     * This method controls the view of an item in the arraylist.
     *
     * @param position    The position of the item.
     * @param convertView The view of the item.
     * @param parent      The viewgroup of the item.
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
        TextView listItemText = (TextView) view.findViewById(R.id.search_item_name);
        // Sets each element of the list to the name of the corresponding item.
        listItemText.setText(filtereditems.get(position).getName());
        Log.d("Displayed filtered list", Integer.toString(filtereditems.size()));

        return view;
    }

    public Filter getFilter() {
        return new Filter() {

            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                final FilterResults oReturn = new FilterResults();
                final ArrayList<Item> results = new ArrayList<Item>();
                if (origitems == null)
                    origitems = filtereditems;
                if (constraint != null) {
                    if (origitems != null && origitems.size() > 0) {
                        for (final Item g : origitems) {
                            if (g.getName().toLowerCase()
                                    .contains(constraint.toString()))
                                results.add(g);
                        }
                    }
                    oReturn.values = results;
                }
                return oReturn;
            }

            @SuppressWarnings("unchecked")
            @Override
            protected void publishResults(CharSequence constraint,
                                          FilterResults results) {
                filtereditems = (ArrayList<Item>) results.values;
                notifyDataSetChanged();
            }
        };
    }

    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
    }

    public void filter(String charText) {
        charText = charText.toLowerCase(Locale.getDefault());
        filtereditems.clear();
        if (charText.length() == 0) {
            filtereditems.addAll(origitems);
        } else {
            for (Item item : origitems) {
                if (item.getName().toLowerCase(Locale.getDefault()).contains(charText)) {
                    filtereditems.add(item);
                }
            }
        }
        notifyDataSetChanged();
    }
}

