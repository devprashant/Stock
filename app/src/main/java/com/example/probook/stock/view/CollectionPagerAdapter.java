package com.example.probook.stock.view;

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
        Fragment fragment = new Fragment();
        Bundle args = new Bundle();
        switch (position){
            case 0:
                fragment = new ObjectListFragment();
                args.putString(ObjectFragment.ARG_OBJECT, "List Fragment");
                fragment.setArguments(args);
                break;
            case 1:
                fragment = new ObjectFragment();
                args.putString(ObjectFragment.ARG_OBJECT, "Add Fragment");
                fragment.setArguments(args);
                break;
            case 2:
                fragment = new ObjectAddFragment();
                args.putString(ObjectFragment.ARG_OBJECT, "Just Fragment");
                fragment.setArguments(args);
                break;
        }

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
