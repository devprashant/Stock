package com.example.probook.stock.view;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import com.example.probook.stock.R;
import com.example.probook.stock.handler.dataSource.DataSource;
import com.example.probook.stock.helper.database.MySqliteHelper;
import com.example.probook.stock.model.Stock;
import java.sql.SQLException;


/**
 * Created by probook on 1/15/2016.
 */
public class ObjectEditDialogFragment extends DialogFragment {

    private AlertDialog.Builder builder;
    private LayoutInflater inflater;
    private View dialogView;
    private EditText etItemName;
    private EditText etItemQuantity;
    private EditText etItemPrice;

    private long itemId;
    private String itemName;
    private String itemQuantity;
    private String itemPrice;

    private DataSource dataSource;


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        dataSource = new DataSource(getActivity());
        try {
            dataSource.open();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        builder = new AlertDialog.Builder(getActivity());

        inflater = getActivity().getLayoutInflater();
        dialogView = inflater.inflate(R.layout.fragment_edit_object, null);

        etItemName = (EditText) dialogView.findViewById(R.id.et_item_name);
        etItemQuantity = (EditText) dialogView.findViewById(R.id.et_item_quantity);
        etItemPrice = (EditText) dialogView.findViewById(R.id.et_item_price);

        itemId = getArguments().getLong(MySqliteHelper.COL_ID);
        itemName = getArguments().getString(MySqliteHelper.COL_ITEM_NAME);
        itemQuantity = getArguments().getString(MySqliteHelper.COL_ITEM_QUANTITY);
        itemPrice = getArguments().getString(MySqliteHelper.COL_PRICE);

        etItemName.setText(itemName);
        etItemQuantity.setText(itemQuantity);
        etItemPrice.setText(itemPrice);

        builder.setTitle("Edit Stock")
                .setView(dialogView)
                .setNegativeButton("Update", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(getActivity(), "Stock Updated", Toast.LENGTH_SHORT).show();
                        Stock stock = new Stock();

                        itemName = etItemName.getText().toString();
                        itemQuantity = etItemQuantity.getText().toString();
                        itemPrice = etItemPrice.getText().toString();

                        stock.setId(itemId);
                        stock.setItem_name(itemName);
                        stock.setItem_quantity(itemQuantity);
                        stock.setItem_price(itemPrice);

                        UpdateData(stock);
                        dbUpdated();
                    }
                })
                .setNeutralButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(getActivity(), "Edit Canceled", Toast.LENGTH_SHORT).show();
                    }
                })
                .setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(getActivity(), "Item Deleted", Toast.LENGTH_SHORT).show();
                        dataSource.deleteStock(itemId);
                        dbUpdated();
                    }
                });



        return builder.create();
    }

    private void UpdateData(Stock stock) {
        dataSource.updateStock(stock);
    }

    private void dbUpdated(){
        Intent intent = new Intent(getActivity(),MainActivity.class);
        startActivity(intent);
        getActivity().finish();
    }
}
