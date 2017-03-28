package com.example.nils.grocerylist;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.ListAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by Nils on 3/25/17.
 */

public class CustomAdapter extends BaseAdapter implements ListAdapter {

    private ArrayList<Item> items;
    private Context context;

    public CustomAdapter(Context context, ArrayList<Item> list) {
        this.items = list;
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
        return 0;
        //just return 0 if your list items do not have an Id variable.
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
                //do something
                Toast.makeText(context, items.get(position).getName() + " has been removed.",
                        Toast.LENGTH_SHORT).show();
                items.remove(position); //or some other task
                notifyDataSetChanged();
            }
        });

//        cb.setOnClickListener(new View.OnClickListener(){
//                    @Override
//                    public void onClick(View view) {
//                        //do something
//                        Toast.makeText(context, items.get(position).getName() + " has been checked off the list.",
//                                Toast.LENGTH_SHORT).show();
//                        Item tempitem = items.get(position);
//                        items.remove(position);
//                        items.add(tempitem);
//                        notifyDataSetChanged();
//                    }
//
//        });

        return view;
    }


}
