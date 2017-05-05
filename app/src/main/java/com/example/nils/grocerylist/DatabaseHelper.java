package com.example.nils.grocerylist;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;


public class DatabaseHelper extends SQLiteOpenHelper {
    /**
     * A column for each piece of data is created.
     */
    public static final String DATABASE_NAME = "items_fulllist.db";
    public static final String TABLE_ITEMS = "items_table";
    public static final String COL_1 = "ID";
    public static final String COL_2 = "name";
    public static final String COL_3 = "price";
    public static final String COL_4 = "ppu";
    public static final String COL_5 = "calories";
    public static final String COL_6 = "fatcalories";
    public static final String COL_7 = "fat";
    public static final String COL_8 = "cholesterol";
    public static final String COL_9 = "sodium";
    public static final String COL_10 = "carbs";
    public static final String COL_11 = "fiber";
    public static final String COL_12 = "sugar";
    public static final String COL_13 = "protein";
    public static final String COL_14 = "ingredients";

    /**
     * A new DatabaseHelper object is created from a given context.
     * @param context The given context.
     */
    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    /**
     * This method is called when a DatabaseHelper object is constructed.
     * The table is generated from each of the initialized columns.
     * @param db The database.
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_ITEMS + " (" + COL_1 + " INTEGER PRIMARY KEY," + COL_2 + " TEXT," + COL_3 +
        " TEXT," + COL_4 +  " TEXT," + COL_5 + " TEXT," + COL_6 + " TEXT," + COL_7 + " TEXT," + COL_8 + " TEXT," + COL_9 + " TEXT," +
        COL_10 + " TEXT," + COL_11 + " TEXT," + COL_12 + " TEXT," + COL_13 + " TEXT," + COL_14 + " TEXT" + ");");
    }

    /**
     * This method is called when the database table is upgraded.
     * @param db The database.
     * @param oldVersion The old version number of the table.
     * @param newVersion The new version number of the table.
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ITEMS);
        onCreate(db);
    }

    /**
     * This method adds an item to the full item list data table.
     * Each piece of data of an item is put into a separate row.
     * @param item The new item.
     */
    public void addItem(Item item) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_1, item.getId());
        values.put(COL_2, item.getName());
        values.put(COL_3, item.getPrice());
        values.put(COL_4, item.getPPU());
        values.put(COL_5, item.getCalories());
        values.put(COL_6, item.getFatCalories());
        values.put(COL_7, item.getFat());
        values.put(COL_8, item.getCholesterol());
        values.put(COL_9, item.getSodium());
        values.put(COL_10, item.getCarbs());
        values.put(COL_11, item.getFiber());
        values.put(COL_12, item.getSugar());
        values.put(COL_13, item.getProtein());
        values.put(COL_14, item.ingredientstoString());
        db.insert(TABLE_ITEMS, null, values);
        db.close(); // Closing database connection
    }

    /**
     * This method returns an ArrayList of all of the items stored in the full list table.
     * @return The ArrayList of items.
     */
    public ArrayList<Item> getAllItems() {
        ArrayList<Item> itemlist = new ArrayList<Item>();
        // Select All Query
        String selectQuery = "SELECT * FROM " + TABLE_ITEMS;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Item item = new Item();
                item.setId(Integer.parseInt(cursor.getString(0)));
                item.setName(cursor.getString(1));
                item.setPrice(Double.parseDouble(cursor.getString(2)));
                item.setPPU(Double.parseDouble(cursor.getString(3)));
                item.setCalories(Integer.parseInt(cursor.getString(4)));
                item.setFatCalories(Double.parseDouble(cursor.getString(5)));
                item.setFat(Double.parseDouble(cursor.getString(6)));
                item.setCholesterol(Double.parseDouble(cursor.getString(7)));
                item.setSodium(Double.parseDouble(cursor.getString(8)));
                item.setCarbs(Double.parseDouble(cursor.getString(9)));
                item.setFiber(Double.parseDouble(cursor.getString(10)));
                item.setSugar(Double.parseDouble(cursor.getString(11)));
                item.setProtein(Double.parseDouble(cursor.getString(12)));
                item.setIngredients(cursor.getString(13));
                // Adding item to list
                itemlist.add(item);
            } while (cursor.moveToNext());
        }
        // return item list
        return itemlist;
    }

    /**
     * This method clears the full item list database.
     */
    public void clearDatabase(String TABLE_ITEM) {
        SQLiteDatabase db = this.getWritableDatabase();
        String clearDBQuery = "DELETE FROM " + "items_table";
        db.execSQL(clearDBQuery);
    }


}
