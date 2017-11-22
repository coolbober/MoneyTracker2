package com.loftschool.moneytracker2.api;


import android.text.TextUtils;

public class Result {
    String status;

    public boolean isSuccess() {
        return TextUtils.equals(status, "success");
    }

}
