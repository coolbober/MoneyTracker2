package com.loftschool.moneytracker2;


import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.ActionMode;

public class ConfirmationDialog extends DialogFragment {


    private ItemsAdapter adapter;
    private ActionMode actionMode;
    private ConfirmationDialogListener listener = null;

    public  void setListener(ConfirmationDialogListener listener) {
          this.listener = listener;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
            AlertDialog.Builder builder = new AlertDialog.Builder(getContext())
                    .setCancelable(false)
                    .setTitle(R.string.app_name)
                    .setMessage(R.string.confirm_remove)
                    .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int id) {
                            listener.onPositiveClick(dialog, id);
                        }
                    })
                    .setNegativeButton(android.R.string.cancel, null)
                    .setOnDismissListener(new DialogInterface.OnDismissListener() {
                        @Override
                        public void onDismiss(DialogInterface dialog) {
                            actionMode.finish();
                        }
                    });
            return builder.create();
   }
}

