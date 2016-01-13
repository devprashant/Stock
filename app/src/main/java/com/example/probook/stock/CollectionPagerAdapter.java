package com.example.probook.stock;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

/**
 * Created by probook on 1/12/2016.
 */
public class CollectionPagerAdapter extends FragmentStatePagerAdapter{

    public CollectionPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = new ObjectFragment();
        Bundle args = new Bundle();
        args.putInt(ObjectFragment.ARG_OBJECT, position+1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return MainActivity.tabsActionBar[position];
    }
}
