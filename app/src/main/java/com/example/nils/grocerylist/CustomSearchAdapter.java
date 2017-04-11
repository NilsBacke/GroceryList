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
    private ArrayList<Integer> checkedpositions;

    public CustomSearchAdapter(Context context, ArrayList<Item> list) {
        this.items = list;
        this.context = context;
        checkedpositions = null;
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Item getItem(int pos) {
        return items.get(pos);
    }

    @Override
    public long getItemId(int pos) {
        return items.get(pos).getId();
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.search, null);
        }

        //Handle TextView and display string from your list
        TextView listItemText = (TextView)view.findViewById(R.id.search_item_name);
        listItemText.setText(items.get(position).getName());

        return view;
    }

    public ArrayList<Integer> getCheckedPositions() {
        return checkedpositions;
    }


}
