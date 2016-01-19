package com.example.probook.stock.view;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.app.DatePickerDialog.OnDateSetListener;
import com.example.probook.stock.R;
import com.example.probook.stock.handler.dataSource.DataSource;
import com.example.probook.stock.model.Stock;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by probook on 1/13/2016.
 */
public class ObjectAddFragment extends Fragment implements View.OnClickListener {

    private DataSource dataSource;

    private EditText etItemName;
    private EditText etItemQuantity;
    private EditText etItemPrice;
    private TextView txtCreatedOn;

    private static final String CREATED_ON = "Created On: ";

    private Calendar calender = Calendar.getInstance();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_add_object, container, false);
        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        // Set initial Creation Date
        String sDate = new SimpleDateFormat("yyyy-MM-dd").format(calender.getTime());
        txtCreatedOn = (TextView) getView().findViewById(R.id.txt_created_on);
        txtCreatedOn.setText(sDate);

        // Set button to select date from datepicker fragment
        Button btnDatePick = (Button) getView().findViewById(R.id.btn_date_pick);
        btnDatePick.setText(CREATED_ON);
        btnDatePick.setOnClickListener(this);
        // get data from edit text
        // save data on btn click
        Button btnSave = (Button) getActivity().findViewById(R.id.btn_save);
        btnSave.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.btn_date_pick:
                setDate(); break;
            case R.id.btn_save:
                saveStock(); break;
        }
    }

    private void setDate() {
        DatePickerFragment date = new DatePickerFragment();
        /**
         * Set Up Current Date Into dialog
         */
        Bundle args = new Bundle();
        args.putInt("year", calender.get(Calendar.YEAR));
        args.putInt("month", calender.get(Calendar.MONTH));
        args.putInt("day", calender.get(Calendar.DAY_OF_MONTH));
        date.setArguments(args);
        /**
         * Set Call back to capture selected date
         */
        date.setCallBack(ondate);
        date.show(getFragmentManager(), "Date Picker");
    }

    OnDateSetListener ondate = new OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            String sDate = String.valueOf(year) + "-" + String.valueOf(monthOfYear)
                    + "-" + String.valueOf(dayOfMonth);
            Toast.makeText(
                    getActivity()
                    ,sDate
                    ,Toast.LENGTH_LONG).show();
            // Set Selected time in  calender object
            calender.set(year, monthOfYear, dayOfMonth);

            // Set txtViw to calecalender text
            txtCreatedOn.setText(sDate);
        }
    };



    private void saveStock() {
        // Find edittext
        etItemName = (EditText) getView().findViewById(R.id.et_item_name);
        etItemQuantity = (EditText) getView().findViewById(R.id.et_item_quantity);
        etItemPrice = (EditText) getView().findViewById(R.id.et_item_price);

        // Fetch data from edittext
        String itemName = etItemName.getText().toString();
        String itemQuantity = etItemQuantity.getText().toString();
        String itemPrice = etItemPrice.getText().toString();

        // Save data
        Stock stock = new Stock();
        stock.setItem_name(itemName);
        stock.setItem_quantity(itemQuantity);
        stock.setItem_price(itemPrice);
        stock.setCreated_on(new SimpleDateFormat("yyyy-MM-dd").format(calender.getTime()));
        stock.setModified_on(new SimpleDateFormat("yyyy-MM-dd").format(calender.getTime()));

        saveData(stock);
    }

    private void saveData(Stock stock) {
        dataSource = new DataSource(getActivity());
        try {
            dataSource.open();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        dataSource.addStock(stock);

        // Show saved message
        Toast.makeText(getActivity(),"Saved",Toast.LENGTH_SHORT).show();

        // Clear edittext
        etItemName.setText("");
        etItemQuantity.setText("");
        etItemPrice.setText("");

        calender = Calendar.getInstance();
        txtCreatedOn.setText(new SimpleDateFormat("yyyy-MM-dd").format(calender.getTime()));
    }
}
