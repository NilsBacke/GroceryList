package com.example.nils.grocerylist.ListAdapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.example.nils.grocerylist.Item;
import com.example.nils.grocerylist.R;

import java.io.InputStream;
import java.util.ArrayList;

/**
 * Created by Nils on 3/25/17.
 */

public class CustomAlternateAdapter extends BaseAdapter implements ListAdapter {

    private ArrayList<Item> items, orig;
    private Context context;

    /**
     * Constructs a new CustomAdapter object from context and an array list of items.
     * @param context The context of the class that constructs the object.
     * @param list The array list of items.
     * @param orig The original array list of items.
     */
    public CustomAlternateAdapter(Context context, ArrayList<Item> list, ArrayList<Item> orig) {
        this.items = list;
        this.orig = orig;
        this.context = context;
    }

    /**
     * Constructs a new CustomAdapter class from context.
     * @param context The context of the class that constructs the object.
     */
    public CustomAlternateAdapter(Context context) {
        items = new ArrayList<Item>();
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
     * This method adds an item to the arraylist.
     * @param item The item that will be added.
     */
    public void addItem(Item item) {
        items.add(item);
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
            view = inflater.inflate(R.layout.alternate_item_info, null);
        }

        new DownloadImageTask2((ImageView) view.findViewById(R.id.imageView))
                .execute(items.get(position).getpictureurl());

        //Handle TextViews and display strings from your list
        TextView listItemText = (TextView)view.findViewById(R.id.item_name);
        listItemText.setText(items.get(position).getName());

        TextView pricetext = (TextView)view.findViewById(R.id.item_price);
        pricetext.setText("Price: " + items.get(position).pricetoString());

        TextView PPUtext = (TextView)view.findViewById(R.id.item_PPU);
        PPUtext.setText("Per Unit: " + items.get(position).PPUtoString());

        TextView origitemtext = (TextView) view.findViewById(R.id.orig_item_name);
        origitemtext.setText("Original: " + orig.get(position).getName());

        TextView origprice = (TextView) view.findViewById(R.id.orig_item_price);
        origprice.setText("Original: " + orig.get(position).pricetoString());

        TextView origPPU = (TextView) view.findViewById(R.id.orig_item_PPU);
        origPPU.setText("Original: " + orig.get(position).PPUtoString());

        // Setup checkbox
        final CheckBox checkBox = (CheckBox) view.findViewById(R.id.alternate_items_check);
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    items.get(position).setSelected(true);
                } else {
                    items.get(position).setSelected(false);
                }
            }
        });

        // The on click listener for an item in the list.
        View.OnClickListener yourClickListener = new View.OnClickListener() {
            public void onClick(View v) {
                android.app.AlertDialog.Builder alertDialogBuilder = new android.app.AlertDialog.Builder(context);

                // The string that displays the information of the nutrition label.
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

    private class DownloadImageTask2 extends AsyncTask<String, Void, Bitmap> {
        ImageView bmImage;

        public DownloadImageTask2(ImageView bmImage) {
            this.bmImage = bmImage;
        }

        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            Bitmap mIcon11 = null;
            try {
                InputStream in = new java.net.URL(urldisplay).openStream();
                mIcon11 = BitmapFactory.decodeStream(in);
                in.close();
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return mIcon11;
        }

        protected void onPostExecute(Bitmap result) {
            bmImage.setScaleType(ImageView.ScaleType.FIT_XY);
            bmImage.setImageBitmap(result);
        }
    }


}




