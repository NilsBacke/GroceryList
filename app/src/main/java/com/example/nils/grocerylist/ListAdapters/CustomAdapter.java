package com.example.nils.grocerylist.ListAdapters;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.nils.grocerylist.Item;
import com.example.nils.grocerylist.MainActivity;
import com.example.nils.grocerylist.R;

import java.io.InputStream;
import java.util.ArrayList;

/**
 * Created by Nils on 3/25/17.
 */

public class CustomAdapter extends BaseAdapter implements ListAdapter {

    private ArrayList<Item> items;
    private Context context;

    /**
     * Constructs a new CustomAdapter object from context and an array list of items.
     * @param context The context of the class that constructs the object.
     * @param list The array list of items.
     */
    public CustomAdapter(Context context, ArrayList<Item> list) {
        this.items = list;
        this.context = context;
    }

    /**
     * Constructs a new CustomAdapter class from context.
     * @param context The context of the class that constructs the object.
     */
    public CustomAdapter(Context context) {
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
            view = inflater.inflate(R.layout.item_info, null);
        }

        new DownloadImageTask((ImageView) view.findViewById(R.id.imageView))
                .execute(items.get(position).getpictureurl());

        //Handle TextViews and display strings from your list
        TextView listItemText = (TextView)view.findViewById(R.id.item_name);
        listItemText.setText(items.get(position).getName());

        TextView PPUtext = (TextView)view.findViewById(R.id.item_price);
        PPUtext.setText("Per Unit: " + items.get(position).PPUtoString());

        TextView pricetext = (TextView)view.findViewById(R.id.orig_item_price);
        pricetext.setText("Price: " + items.get(position).pricetoString());

        //Handle buttons and add onClickListeners
        ImageButton deleteButton = (ImageButton) view.findViewById(R.id.deleteButton);
        CheckBox cb = (CheckBox) view.findViewById(R.id.alternate_items_check);

        // The on click listener for the delete button.
        deleteButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                // A new alert dialog is created.
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);

                // set dialog message
                alertDialogBuilder.setMessage("Are you sure you want to delete?")
                        .setPositiveButton("YES", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // Make toast saying which item was removed.
                                Toast.makeText(context, items.get(position).getName() + " has been removed.",
                                        Toast.LENGTH_SHORT).show();
                                // Remove the item.
                                items.remove(position);
                                notifyDataSetChanged();
                                MainActivity main = (MainActivity)context;
                                // Call the getTotalPrice() method to update the price in the MainActivity.
                                main.getTotalPrice();
                            }
                        })
                .setNegativeButton("Cancel", null);

                // create alert dialog
                AlertDialog alertDialog = alertDialogBuilder.create();
                // show the alert dialog
                alertDialog.show();

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



    /**
     * This class downloads the image to be displayed for each item.
     */
    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        ImageView bmImage;

        /**
         * Constructs a new DownloadImageTask2 object.
         * @param bmImage The imageview to display the image with.
         */
        public DownloadImageTask(ImageView bmImage) {
            this.bmImage = bmImage;
        }

        /**
         * This method runs in the background and downloads the pictures from the url from the internet.
         * @param urls The url for the image.
         * @return The bitmap of the downloaded image.
         */
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

        /**
         * This method is called after the class is executed.
         * @param result The bitmap of the image.
         */
        protected void onPostExecute(Bitmap result) {
            bmImage.setScaleType(ImageView.ScaleType.FIT_XY);
            bmImage.setImageBitmap(result);
        }
    }


}


