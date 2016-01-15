package com.example.probook.stock.view;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;
import com.example.probook.stock.R;
import com.example.probook.stock.handler.customAdapter.customListObjectAdapter;
import com.example.probook.stock.handler.dataSource.DataSource;
import com.example.probook.stock.model.Stock;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by probook on 1/13/2016.
 */
public class ObjectListFragment extends Fragment {

    public customListObjectAdapter adapter;

    private DataSource dataSource;
    private ListView lv;

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

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        // Fetch data
        List<Stock> values = dataSource.getAllStocks();

        // Set ListView
        // List data
        lv = (ListView) getView().findViewById(R.id.list);
        adapter = new customListObjectAdapter(getActivity(),values);
        lv.setAdapter(adapter);
        lv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

                ObjectEditDialogFragment editDialog = new ObjectEditDialogFragment();
                editDialog.show(getFragmentManager(),"dialog");

                return true;
            }
        });

    }
}
