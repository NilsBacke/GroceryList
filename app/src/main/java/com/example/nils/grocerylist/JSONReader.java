package com.example.nils.grocerylist;

import android.app.Activity;
import android.content.Context;
import android.content.res.AssetManager;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Nils on 4/1/17.
 */

public class JSONReader extends Activity {

    public JSONReader() {

    }

//    public String loadJSONFromAsset() {
//        String json = null;
//        try {
//            InputStream is = JSONReader.this.getAssets().open("Fruit.json");
//            System.out.println("**** JSON is returning null ****");
//            int size = is.available();
//            byte[] buffer = new byte[size];
//            is.read(buffer);
//            is.close();
//            json = new String(buffer, "UTF-8");
//
//
//        return json;
//        try {
//            String jsonLocation = AssetJSONFile("Fruit.json", JSONReader.this);
//        } catch (IOException ex) {
//            ex.printStackTrace();
//            return null;
//        }
//        return jsonLocation;
//
//    }

    public String AssetJSONFile (String filename, Context context) throws IOException {
        AssetManager manager = context.getAssets();
        InputStream file = manager.open("filename");
        byte[] formArray = new byte[file.available()];
        file.read(formArray);
        file.close();

        return new String(formArray);
    }

    public void readJSON() throws IOException {
        try {
            String jsonLocation = AssetJSONFile("fruit.json", this);
            JSONObject obj = new JSONObject(jsonLocation);
            JSONArray itemsArray = obj.getJSONArray("Item");
            ArrayList<HashMap<String, String>> itemsList = new ArrayList<HashMap<String, String>>();
            HashMap<String, String> m_li;

            for (int i = 0; i < itemsArray.length(); i++) {
                JSONObject jo_inside = itemsArray.getJSONObject(i);
                Log.d("Details-->", jo_inside.getString("name"));
                String name = jo_inside.getString("name");
                String price = jo_inside.getString("price");
                String each = jo_inside.getString("each");

                //Add your values in your `ArrayList` as below:
                m_li = new HashMap<String, String>();
                m_li.put("name", name);
                m_li.put("price", price);
                m_li.put("each", each);

                itemsList.add(m_li);

                Double doubleprice = Double.parseDouble(price);
                Double doubleeach = Double.parseDouble(each);

                Item newItem = new Item(name, doubleprice, doubleeach);

                DatabaseTable.DatabaseOpenHelper db = new DatabaseTable.DatabaseOpenHelper(this);

                db.addItem(newItem);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
