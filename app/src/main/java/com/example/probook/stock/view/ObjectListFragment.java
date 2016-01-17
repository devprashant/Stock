package com.example.probook.stock.view;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
import com.example.probook.stock.R;
import com.example.probook.stock.handler.customAdapter.customListObjectAdapter;
import com.example.probook.stock.handler.dataSource.DataSource;
import com.example.probook.stock.helper.database.MySqliteHelper;
import com.example.probook.stock.model.Stock;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by probook on 1/13/2016.
 */
public class ObjectListFragment extends Fragment {

    public customListObjectAdapter adapter;

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
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_list_object, container,false);
        return rootView;
    }

    /**
     * Operations:
     * 1. Fetch data and put inside ListView.
     * 2. Get EditText data and use it to list items inside ListVIew.
     * @param savedInstanceState
     */
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        /**
         * 1. Fetch data and put inside ListView.
         */
        // Fetch data
        final List<Stock> values = dataSource.getAllStocks();

        // Set ListView
        // List data
        ListView lv = (ListView) getView().findViewById(R.id.list);

        /**
         * 2. Get EditText data and use it to list items inside ListVIew.
         */

        EditText etSearch = (EditText) getView().findViewById(R.id.et_search);
        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                    for (int i =0; i < values.size(); i++){
                        if (!values.get(i).getItem_name().contains(s)){
                            values.remove(i);
                        }
                    }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        adapter = new customListObjectAdapter(getActivity(), values);
        lv.setAdapter(adapter);

        // Open dialog fragment for edit and delete
        lv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

                Stock stock = (Stock) adapter.getItem(position);
                //Toast.makeText(getActivity(), stock.getItem_name(), Toast.LENGTH_SHORT).show();
                ObjectEditDialogFragment editDialogFragment = new ObjectEditDialogFragment();
                Bundle args = new Bundle();
                args.putString(MySqliteHelper.COL_ITEM_NAME, stock.getItem_name());
                args.putString(MySqliteHelper.COL_ITEM_QUANTITY, stock.getItem_quantity());
                args.putString(MySqliteHelper.COL_PRICE, stock.getItem_price());
                args.putLong(MySqliteHelper.COL_ID, stock.getId());

                editDialogFragment.setArguments(args);
                editDialogFragment.show(getFragmentManager(), "dialog");

                return true;
            }
        });
    }
}
