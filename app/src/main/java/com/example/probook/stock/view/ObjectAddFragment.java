package com.example.probook.stock.view;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import com.example.probook.stock.R;
import com.example.probook.stock.handler.dataSource.DataSource;
import java.sql.SQLException;

/**
 * Created by probook on 1/13/2016.
 */
public class ObjectAddFragment extends Fragment implements View.OnClickListener {

    private DataSource dataSource;

    public static final String ARG_OBJECT = "object";
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_add_object, container, false);
        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        // get data from edit text
        // save data on btn click

        Button btnSave = (Button) getActivity().findViewById(R.id.btn_save);
        btnSave.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        // Find edittext
        EditText etItemName = (EditText) getView().findViewById(R.id.et_item_name);
        EditText etItemQuantity = (EditText) getView().findViewById(R.id.et_item_quantity);
        EditText etItemPrice = (EditText) getView().findViewById(R.id.et_item_price);

        // Fetch data from edittext
        String itemName = etItemName.getText().toString();
        String itemQuantity = etItemQuantity.getText().toString();
        String itemPrice = etItemPrice.getText().toString();
        // Save data
        saveData(itemName, itemQuantity, itemPrice);

    }

    private void saveData(String itemName, String itemQuantity, String itemPrice) {
        dataSource = new DataSource(getActivity());
        try {
            dataSource.open();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        dataSource.addStock(itemName, itemQuantity, itemPrice);

    }
}
