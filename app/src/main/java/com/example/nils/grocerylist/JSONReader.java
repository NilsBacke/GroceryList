package com.example.nils.grocerylist;

import android.app.Activity;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by Nils on 4/1/17.
 */

public class JSONReader extends Activity {


    public String loadJSONFromAsset() {
        String json = null;
        try {
            InputStream is = getAssets().open("yourfilename.json");
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
}
