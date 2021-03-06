package com.loftschool.moneytracker2;


import android.content.res.Resources;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import static com.loftschool.moneytracker2.Item.TYPE_EXPENSE;
import static com.loftschool.moneytracker2.Item.TYPE_INCOME;

public class MainPagerAdapter extends FragmentPagerAdapter {


    private final static int PAGE_EXPENSES = 0;
    private final static int PAGE_INCOMES = 1;
    private final static int PAGE_BALANCE = 2;

    private String[] titles;

    public MainPagerAdapter(FragmentManager fm, Resources resources) {
        super(fm);

        titles = resources.getStringArray(R.array.tabs_titles);
    }

    @Override
    public android.support.v4.app.Fragment getItem(int position) {

        switch (position) {
            case PAGE_EXPENSES: {

                return ItemsFragment.createItemFragment(TYPE_EXPENSE);
            }
            case PAGE_INCOMES: {
                return ItemsFragment.createItemFragment(TYPE_INCOME);
            }
            case PAGE_BALANCE:
                return BalanceFragment.createBalanceFragment(BalanceFragment.TYPE_BALANCE);

                default:
                    return null;
        }
    }

    @Override
    public int getCount() {
        return titles.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titles[position];
    }
}
