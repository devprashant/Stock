package com.example.probook.stock.handler.customAdapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;
import com.example.probook.stock.R;
import com.example.probook.stock.model.Stock;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by probook on 1/14/2016.
 */
public class customListObjectAdapter extends BaseAdapter implements Filterable{

    private List<Stock> stocks;
    private Activity activity;
    private LayoutInflater inflater;


    public customListObjectAdapter( Activity activity, List<Stock> stocks) {
        this.stocks = stocks;
        this.activity = activity;
    }

    @Override
    public int getCount() {
        return stocks.size();
    }

    @Override
    public Object getItem(int position) {
        return stocks.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        // Get layout Inflater
        if ( inflater == null ){
            inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        // Inflate layout
        if ( convertView == null ){
            convertView = inflater.inflate(R.layout.list_row, null);
        }

        // Fill layout
        TextView txtItemName = (TextView) convertView.findViewById(R.id.txt_item_name);
        TextView txtItemQuantity = (TextView) convertView.findViewById(R.id.txt_item_quantity);
        TextView txtItemPrice = (TextView) convertView.findViewById(R.id.txt_item_price);
        TextView txtModifiedOn = (TextView) convertView.findViewById(R.id.txt_modified_on);

        txtItemName.setText(stocks.get(position).getItem_name());
        txtItemQuantity.setText(stocks.get(position).getItem_quantity());
        txtItemPrice.setText(stocks.get(position).getItem_price());
        txtModifiedOn.setText(stocks.get(position).getModified_on());

        System.out.println("Date: " + stocks.get(position).getModified_on());

        return convertView;
    }


    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults results = new FilterResults();

                if ( constraint == null || constraint.length() == 0){
                    results.values = stocks;
                    results.count = stocks.size();
                } else {
                    List<Stock> filteredStock = new ArrayList<>();

                    for(Stock s: stocks){
                        if(s.getItem_name().toUpperCase().contains(constraint.toString().toUpperCase())){
                            filteredStock.add(s);
                        }
                    }

                    results.values = filteredStock;
                    results.count = filteredStock.size();
                }
                return results;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                stocks = (List<Stock>) results.values;
                notifyDataSetChanged();
            }
        };
    }
}
