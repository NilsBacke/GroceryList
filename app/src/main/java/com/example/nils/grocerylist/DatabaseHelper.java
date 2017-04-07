package com.example.nils.grocerylist;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;


public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "items_table.db";
    public static final String TABLE_NAME = "items_tables";
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

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_NAME + " (" + COL_1 + " INTEGER PRIMARY KEY AUTOINCREMENT," + COL_2 + " TEXT," + COL_3 +
        " TEXT," + COL_4 +  " TEXT," + COL_5 + " TEXT," + COL_6 + " TEXT," + COL_7 + " TEXT," + COL_8 + " TEXT," + COL_9 + " TEXT," +
        COL_10 + " TEXT," + COL_11 + " TEXT," + COL_12 + " TEXT," + COL_13 + " TEXT," + COL_14 + " TEXT" + ");");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public void addItem(Item item) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
//        values.put(COL_1, item.getId());
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
        values.put(COL_14, item.getStringIngredients());
        db.insert(TABLE_NAME, null, values);
        db.close(); // Closing database connection
    }

    public ArrayList<Item> getAllItems() {
        ArrayList<Item> itemlist = new ArrayList<Item>();
        // Select All Query
        String selectQuery = "SELECT * FROM " + TABLE_NAME;
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
                item.setFatCalories(Integer.parseInt(cursor.getString(5)));
                item.setFat(Integer.parseInt(cursor.getString(6)));
                item.setCholesterol(Integer.parseInt(cursor.getString(7)));
                item.setSodium(Integer.parseInt(cursor.getString(8)));
                item.setCarbs(Integer.parseInt(cursor.getString(9)));
                item.setFiber(Integer.parseInt(cursor.getString(10)));
                item.setSugar(Integer.parseInt(cursor.getString(11)));
                item.setProtein(Integer.parseInt(cursor.getString(12)));
                item.setIngredients(cursor.getString(13));
                // Adding item to list
                itemlist.add(item);
            } while (cursor.moveToNext());
        }
        // return item list
        return itemlist;
    }

//    public void clearDatabase(String TABLE_NAME) {
//        SQLiteDatabase db = this.getWritableDatabase();
////        db.delete("SQLITE_SEQUENCE","NAME = ?",new String[]{TABLE_NAME});
//        String clearDBQuery = "DELETE FROM "+TABLE_NAME+ "WHERE NAME = " + TABLE_NAME;
//        db.execSQL(clearDBQuery);
////        db.execSQL("DELETE FROM SQLITE_SEQUENCE WHERE NAME = '" + TABLE_NAME + "'");
//    }

}
