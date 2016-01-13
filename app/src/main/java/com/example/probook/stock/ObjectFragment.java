package com.example.probook.stock;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by probook on 1/12/2016.
 */
public class ObjectFragment extends Fragment {
    public static final String ARG_OBJECT = "object";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_collection_object, container,false);
        Bundle args = getArguments();

        ((TextView) rootView.findViewById(R.id.text1))
                .setText(Integer.toString(args.getInt(ARG_OBJECT)));

        return rootView;
    }
}
