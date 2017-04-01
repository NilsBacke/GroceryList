package com.example.nils.grocerylist;

import android.app.Activity;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Nils on 4/1/17.
 */

public class JSONReader extends Activity {


    public String loadJSONFromAsset() {
        String json = null;
        try {
            InputStream is = getAssets().open("Fruit.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }

    public void readJSON() {
        try {
            JSONObject obj = new JSONObject(loadJSONFromAsset());
            JSONArray m_jArry = obj.getJSONArray("Item");
            ArrayList<HashMap<String, String>> formList = new ArrayList<HashMap<String, String>>();
            HashMap<String, String> m_li;

            for (int i = 0; i < m_jArry.length(); i++) {
                JSONObject jo_inside = m_jArry.getJSONObject(i);
                Log.d("Details-->", jo_inside.getString("formule"));
                String name = jo_inside.getString("name");
                String price = jo_inside.getString("price");
                String each = jo_inside.getString("each");
                String per = jo_inside.getString("per");

                //Add your values in your `ArrayList` as below:
                m_li = new HashMap<String, String>();
                m_li.put("name", name);
                m_li.put("price", price);
                m_li.put("each", each);
                m_li.put("per", per);

                formList.add(m_li);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
}
