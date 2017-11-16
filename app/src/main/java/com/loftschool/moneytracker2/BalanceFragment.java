package com.loftschool.moneytracker2;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class BalanceFragment extends Fragment {

    public static final int TYPE_BALANCE = 2;
    private static final String KEY_TYPE = "TYPE";
    private  int type = TYPE_BALANCE;

    public static BalanceFragment createBalanceFragment(int type) {
        BalanceFragment fragment = new BalanceFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(BalanceFragment.KEY_TYPE,BalanceFragment.TYPE_BALANCE);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.balance_fragment, container, false);
        return view;
    }

}
