package com.example.nils.grocerylist;

import android.content.ContentValues;
import android.content.Context;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;
import android.text.TextUtils;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class DatabaseTable {

    private static final String TAG = "DictionaryDatabase";

    //The columns we'll include in the dictionary table
    public static final String COL_WORD = "WORD";
    public static final String COL_DEFINITION = "DEFINITION";

    private static final String DATABASE_NAME = "DICTIONARY";
    private static final String FTS_VIRTUAL_TABLE = "FTS";
    private static final int DATABASE_VERSION = 1;

    private final DatabaseOpenHelper mDatabaseOpenHelper;

    public DatabaseTable(Context context) {
        mDatabaseOpenHelper = new DatabaseOpenHelper(context);
    }

    public Cursor getWordMatches(String query, String[] columns) {
        String selection = COL_WORD + " MATCH ?";
        String[] selectionArgs = new String[] {query+"*"};

        return query(selection, selectionArgs, columns);
    }

    private Cursor query(String selection, String[] selectionArgs, String[] columns) {
        SQLiteQueryBuilder builder = new SQLiteQueryBuilder();
        builder.setTables(FTS_VIRTUAL_TABLE);

        Cursor cursor = builder.query(mDatabaseOpenHelper.getReadableDatabase(),
                columns, selection, selectionArgs, null, null, null);

        if (cursor == null) {
            return null;
        } else if (!cursor.moveToFirst()) {
            cursor.close();
            return null;
        }
        return cursor;
    }



    public static class DatabaseOpenHelper extends SQLiteOpenHelper {

        private static final String KEY_ID = "id";
        private static final String KEY_NAME = "name";
        private static final String KEY_PRICE = "price";
        private static final String KEY_PPU = "ppu";
        private static final String KEY_CALORIES = "calories";
        private static final String KEY_FATCALORIES = "fatcalories";
        private static final String KEY_SODIUM = "sodium";
        private static final String KEY_CARBS = "carbs";
        private static final String KEY_FIBER = "fiber";
        private static final String KEY_SUGAR = "sugar";
        private static final String KEY_PROTEIN = "protein";
        private static final String KEY_INGREDIENTS = "ingredients";



        public DatabaseOpenHelper (Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            String CREATE_TABLE = "CREATE TABLE " + FTS_VIRTUAL_TABLE + "(" + KEY_ID + "ID"
                    + KEY_NAME + " NAME, " + KEY_PRICE + " PRICE," + KEY_PPU + " PRICE PER UNIT, " + KEY_CALORIES + " CALORIES, "
            + KEY_FATCALORIES + " fatcalories, " + KEY_SODIUM + " SODIUM, " + KEY_CARBS + " CARBS, " + KEY_FIBER + " FIBER, " +
                KEY_SUGAR + " SUGAR, " + KEY_PROTEIN + " PROTEIN, " + KEY_INGREDIENTS + " INGREDIENTS, " + ")";
            db.execSQL(CREATE_TABLE);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            // Drop older table if existed
            db.execSQL("DROP TABLE IF EXISTS " + FTS_VIRTUAL_TABLE);
            // Creating tables again
            onCreate(db);
        }

        // Adding new item
        public void addItem(Item item) {
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(KEY_NAME, item.getName());
            values.put(KEY_PRICE, item.getPrice());
            values.put(KEY_PPU, item.getPPU());
            values.put(KEY_CALORIES, item.getCalories());
            values.put(KEY_FATCALORIES, item.getFatCalories());
            values.put(KEY_SODIUM, item.getSodium());
            values.put(KEY_CARBS, item.getCarbs());
            values.put(KEY_FIBER, item.getFiber());
            values.put(KEY_SUGAR, item.getSugar());
            values.put(KEY_PROTEIN, item.getProtein());
            values.put(KEY_INGREDIENTS, item.getStringIngredients());
            db.insert(FTS_VIRTUAL_TABLE, null, values);
            db.close(); // Closing database connection
        }


        // Getting one shop
        public Item getItem(int id) {
            SQLiteDatabase db = this.getReadableDatabase();
            Cursor cursor = db.query(FTS_VIRTUAL_TABLE, new String[] { KEY_ID,
                            KEY_NAME, KEY_PRICE, KEY_PPU, KEY_CALORIES, KEY_FATCALORIES, KEY_SODIUM,
                    KEY_CARBS, KEY_FIBER, KEY_SUGAR, KEY_PROTEIN, KEY_INGREDIENTS}, KEY_ID + "=?",
                    new String[] { String.valueOf(id) }, null, null, null, null);
            if (cursor != null)
                cursor.moveToFirst();
            return new Item(cursor.getString(0),
                    Double.parseDouble(cursor.getString(1)), Double.parseDouble(cursor.getString(2)),
                    Integer.parseInt(cursor.getString(3)), Integer.parseInt(cursor.getString(4)),
                    Integer.parseInt(cursor.getString(5)), Integer.parseInt(cursor.getString(6)),
                    Integer.parseInt(cursor.getString(7)), Integer.parseInt(cursor.getString(8)),
                    Integer.parseInt(cursor.getString(9)), Integer.parseInt(cursor.getString(10)),
                    Integer.parseInt(cursor.getString(11)), cursor.getString(12));
        }

        // Getting All Items
        public ArrayList<Item> getAllItems() {
            ArrayList<Item> itemlist = new ArrayList<Item>();
            // Select All Query
            String selectQuery = "SELECT * FROM " + FTS_VIRTUAL_TABLE;
            SQLiteDatabase db = this.getWritableDatabase();
            Cursor cursor = db.rawQuery(selectQuery, null);
            // looping through all rows and adding to list
            if (cursor.moveToFirst()) {
                do {
                    Item item = new Item();
                    item.setName(cursor.getString(0));
                    item.setPrice(Double.parseDouble(cursor.getString(1)));
                    item.setPPU(Double.parseDouble(cursor.getString(2)));
                    item.setCalories(Integer.parseInt(cursor.getString(3)));
                    item.setFatCalories(Integer.parseInt(cursor.getString(4)));
                    item.setFat(Integer.parseInt(cursor.getString(5)));
                    item.setCholesterol(Integer.parseInt(cursor.getString(6)));
                    item.setSodium(Integer.parseInt(cursor.getString(7)));
                    item.setCarbs(Integer.parseInt(cursor.getString(8)));
                    item.setFiber(Integer.parseInt(cursor.getString(9)));
                    item.setSugar(Integer.parseInt(cursor.getString(10)));
                    item.setProtein(Integer.parseInt(cursor.getString(11)));
                    item.setIngredients(cursor.getString(12));
                    // Adding item to list
                    itemlist.add(item);
                } while (cursor.moveToNext());
            }
            // return item list
            return itemlist;
        }

        //        "calories”:"250",
//                "fatCalories":"10",
//                "Fat":"2.5g",
//                "cholesterol":"0mg",
//                "Sodium":"380mg",
//                "carbs":"52g",
//                "fiber":"2g",
//                "sugar":"8g",
//                "protein":"8g",

        // Getting items count
        public int getItemsCount() {
            String countQuery = "SELECT * FROM " + FTS_VIRTUAL_TABLE;
            SQLiteDatabase db = this.getReadableDatabase();
            Cursor cursor = db.rawQuery(countQuery, null);
            cursor.close();
            // return count
            return cursor.getCount();
        }

        // Updating an item
        public int updateItem(Item item) {
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(KEY_NAME, item.getName());
            values.put(KEY_PRICE, item.getPrice());
            values.put(KEY_PPU, item.getPPU());
            values.put(KEY_CALORIES, item.getCalories());
            values.put(KEY_FATCALORIES, item.getFatCalories());
            values.put(KEY_SODIUM, item.getSodium());
            values.put(KEY_CARBS, item.getCarbs());
            values.put(KEY_FIBER, item.getFiber());
            values.put(KEY_SUGAR, item.getSugar());
            values.put(KEY_PROTEIN, item.getProtein());
            values.put(KEY_INGREDIENTS, item.getStringIngredients());
            // updating row
            return db.update(FTS_VIRTUAL_TABLE, values, KEY_ID + " = ?",
                    new String[]{String.valueOf(item.getId())});
        }

        // Deleting a item
        public void deleteItem(Item item) {
            SQLiteDatabase db = this.getWritableDatabase();
            db.delete(FTS_VIRTUAL_TABLE, KEY_ID + " = ?",
                    new String[] { String.valueOf(item.getId()) });
            db.close();
        }

    }
}
