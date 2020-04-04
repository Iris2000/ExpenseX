package com.example.listview;


import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

class MyAdapter extends FragmentPagerAdapter {

    Context context;
    int totalTabs;
    String username;

    public MyAdapter(Context c, FragmentManager fm, int totalTabs, String username) {
        super(fm);
        context = c;
        this.totalTabs = totalTabs;
        this.username = username;
    }

    @Override
    public Fragment getItem(int position) {
        Bundle bundle = new Bundle();
        bundle.putString("username", username);

        switch (position) {
            case 0:
                ExpenseFragment expenseFragment = new ExpenseFragment();
                expenseFragment.setArguments(bundle);
                return expenseFragment;
            case 1:
                IncomeFragment incomeFragment = new IncomeFragment();
                incomeFragment.setArguments(bundle);
                return incomeFragment;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return totalTabs;
    }
}