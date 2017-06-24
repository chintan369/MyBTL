package com.agraeta.user.btl.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Nivida new on 24-Jun-17.
 */

public class TabsAdapter extends FragmentPagerAdapter {

    List<Fragment> fragmentList = new ArrayList<>();
    List<String> titles = new ArrayList<>();

    public TabsAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return fragmentList.get(position);
    }

    @Override
    public int getCount() {
        return fragmentList.size();
    }

    public void addFragment(Fragment fragment, String title) {
        this.fragmentList.add(fragment);
        this.titles.add(title);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titles.get(position);
    }
}
