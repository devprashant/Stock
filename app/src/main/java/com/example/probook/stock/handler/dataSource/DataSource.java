package com.example.probook.stock.handler.dataSource;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import com.example.probook.stock.helper.database.MySqliteHelper;
import com.example.probook.stock.model.Stock;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by probook on 1/9/2016.
 */
public class DataSource {

    private SQLiteDatabase database;
    private MySqliteHelper dbHelper;
    private String[]  allColumns = {
            dbHelper.COL_ID
            ,dbHelper.COL_ITEM_NAME
            ,dbHelper.COL_ITEM_QUANTITY
            ,dbHelper.COL_PRICE
            ,dbHelper.COL_MODIFIED_ON
            };

    public DataSource(Context context) {
        //Log.w("Inside: ", " DataSource Constructor");
        dbHelper = new MySqliteHelper(context);
    }

    public void open() throws SQLException{
        //Log.w("Inside: "," DS open()");
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        //Log.w("Inside: ", " DS close()");
        dbHelper.close();
    }

    public Stock addStock(Stock stock){

        ContentValues values = new ContentValues();
        values.put(MySqliteHelper.COL_ITEM_NAME, stock.getItem_name());
        values.put(MySqliteHelper.COL_ITEM_QUANTITY, stock.getItem_quantity());
        values.put(MySqliteHelper.COL_PRICE, stock.getItem_price());
        values.put(MySqliteHelper.COL_MODIFIED_ON, String.valueOf(Calendar.DATE));

        //System.out.println("Date: " + String.valueOf(Calendar.DATE));
        //System.out.println("values to store: " + values);
        //Log.w("Inside: ", " Value inserting to db");
        long insertId = database.insert(MySqliteHelper.TABLE_STOCK,null, values);

        //Log.w("Inside: "," Query DB for value entered");
        Cursor cursor = database.query(MySqliteHelper.TABLE_STOCK, allColumns, MySqliteHelper.COL_ID + " = " + insertId, null, null, null, null);

        cursor.moveToFirst();
        Stock newStock = cursorToStock(cursor);
        cursor.close();
        return newStock;

    }

    public int updateStock(Stock stock){
        ContentValues values = new ContentValues();
        values.put(MySqliteHelper.COL_ITEM_NAME, stock.getItem_name());
        values.put(MySqliteHelper.COL_ITEM_QUANTITY, stock.getItem_quantity());
        values.put(MySqliteHelper.COL_PRICE, stock.getItem_price());
        values.put(MySqliteHelper.COL_MODIFIED_ON, String.valueOf(Calendar.DATE));

        long id = stock.getId();
        int updateStatus  = database.update(MySqliteHelper.TABLE_STOCK, values, MySqliteHelper.COL_ID + " = " + id, null);

        return updateStatus;
    }

    public void deleteStock(long id){

        database.delete(MySqliteHelper.TABLE_STOCK, MySqliteHelper.COL_ID + " = " + id, null);
    }

    public List<Stock> getAllStocks(){
        List<Stock> stocks = new ArrayList<Stock>();

        //Log.w("Inside: "," List all stocks");
        Cursor cursor = database.query(MySqliteHelper.TABLE_STOCK,allColumns,null,null,null,null,null);
        //System.out.println("All " + cursor.getCount());
        cursor.moveToLast();
        while(!cursor.isBeforeFirst()){
            Stock stock = cursorToStock(cursor);

            stocks.add(stock);
            cursor.moveToPrevious();

            //System.out.println("Stocks modified on: " + stock.getModified_on());
        }

        cursor.close(); getAllStockNames();
        return stocks;
    }

    public List<String> getAllStockNames(){
        List<String> allStockNames = new ArrayList<>();

        String[] stockName = { MySqliteHelper.COL_ID, MySqliteHelper.COL_ITEM_NAME };

        // Get unique item names
        //Cursor cursor = database.rawQuery(sql,null);
        Cursor cursor = database.query(
                true
                ,MySqliteHelper.TABLE_STOCK
                ,stockName
                ,null,null
                ,MySqliteHelper.COL_ITEM_NAME
                ,null,null,null
        );

        cursor.moveToFirst();

        while ( !cursor.isAfterLast() ){
            allStockNames.add(cursor.getString(1));
            //System.out.println("Stock Name: " + cursor.getString(1));
            cursor.moveToNext();
        }

        //System.out.println("Stock Names: " + allStockNames);
        cursor.close();
        return  allStockNames;
    }
    private Stock cursorToStock(Cursor cursor) {
        Stock stock = new Stock();
        stock.setId(cursor.getLong(0));
        stock.setItem_name(cursor.getString(1));
        stock.setItem_quantity(cursor.getString(2));
        stock.setItem_price(cursor.getString(3));
        stock.setModified_on(cursor.getString(4));

        //Log.w("Inside: ", " cursorToStock()");
        //System.out.println("cts " + stock);
        return stock;
    }


}
