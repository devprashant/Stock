package com.example.probook.stock.helper.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by probook on 1/9/2016.
 */
public class MySqliteHelper extends SQLiteOpenHelper {


    public static final String TABLE_STOCK = "stock_table";
    public static final String COL_ID = "_id";
    public static final String COL_ITEM_NAME = "item_name";
    public static final String COL_ITEM_QUANTITY = "item_quantity";
    public static final String COL_PRICE = "price";


    private static final String DATABASE_NAME = "stock2.db";
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_CREATE = "create table " + TABLE_STOCK + "("
            + COL_ID + " integer primary key autoincrement, "
            + COL_ITEM_NAME + " text not null, "
            + COL_ITEM_QUANTITY + " text not null, "
            + COL_PRICE + " text not null"
            + ")";

    public MySqliteHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        Log.w("Inside: ", " DB Constructor");
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.w("Inside: ", " Create DB");
        db.execSQL(DATABASE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        Log.w("Inside: ", " Upgrading DB");
        db.execSQL(" DROP TABLE IF EXISTS " + TABLE_STOCK);
        onCreate(db);
    }
}
