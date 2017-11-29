package com.loftschool.moneytracker2;


import android.content.DialogInterface;

public interface ConfirmationDialogListener {
    void onPositiveClick(DialogInterface dialog, int button);
    void onNegativeClick(DialogInterface dialog, int button);
}
