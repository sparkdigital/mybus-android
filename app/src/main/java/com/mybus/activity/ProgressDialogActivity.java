package com.mybus.activity;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by Lucas De Lio on 02-Aug-16.
 */
public class ProgressDialogActivity extends AppCompatActivity{

    protected ProgressDialog mDialog;

    /**
     * Shows a progress dialog with specified text
     *
     * @param text
     */
    protected void showProgressDialog(String text) {
        cancelProgressDialog();
        mDialog = ProgressDialog.show(ProgressDialogActivity.this, "", text, true, false);
    }

    /**
     * Cancels the current progress dialog if any
     */
    protected void cancelProgressDialog() {
        if (mDialog != null) {
            mDialog.cancel();
            mDialog = null;
        }
    }
}
