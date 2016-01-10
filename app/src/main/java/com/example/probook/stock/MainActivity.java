package com.example.probook.stock;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
import com.example.probook.stock.dataSource.DataSource;
import com.example.probook.stock.model.Stock;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends ActionBarActivity implements AdapterView.OnItemClickListener, View.OnClickListener {

    private DataSource dataSource;
    private ListView listView;
    private EditText txtitemName;
    private EditText txtitemQuantity;
    private EditText txtitemPrice;
    private Button btnAdd;
    private String itemPrice;
    private String itemQuantity;
    private String itemName;
    private ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtitemName = (EditText) findViewById(R.id.item_name);
        txtitemQuantity = (EditText) findViewById(R.id.item_quantity);
        txtitemPrice = (EditText) findViewById(R.id.item_price);

        btnAdd = (Button) findViewById(R.id.btnAdd);

        btnAdd.setOnClickListener(this);

        listView = (ListView) findViewById(R.id.list);

        Log.e("Inside:","main");
        dataSource = new DataSource(this);
        try {
            dataSource.open();
        } catch (SQLException e) {
            e.printStackTrace();
        }


        Log.e("Inside: "," main again" );
        List<Stock> values = dataSource.getAllStocks();

        List<String> sValues = new ArrayList<String>();

        for(Stock value :  values){
            sValues.add(value.getId() + " : " + value.getItem_name() + " : " + value.getItem_quantity() + " : " + value.getItem_price());
        }

        System.out.println("values:" + sValues);
        adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, sValues);

        listView.setAdapter(adapter);

        listView.setOnItemClickListener(this);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        final String item = (String) parent.getItemAtPosition(position);
        Toast.makeText(this,item,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(View v) {
        itemName = txtitemName.getText().toString();
        itemQuantity = txtitemQuantity.getText().toString();
        itemPrice = txtitemPrice.getText().toString();


        Log.e("itemName:", itemName);
        Log.e("itemQuantity",itemQuantity);
        Log.e("itemPrice", itemPrice);

        dataSource.addStock(itemName, itemQuantity, itemPrice);
        adapter.notifyDataSetChanged();

    }
}
