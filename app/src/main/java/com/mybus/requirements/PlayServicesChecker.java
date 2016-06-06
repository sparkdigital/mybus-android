package com.mybus.requirements;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.mybus.R;

public class PlayServicesChecker {

    /**
     *
     * @param context
     * @return
     */
    public static boolean checkPlayServices(Context context) {
        int status = GooglePlayServicesUtil.isGooglePlayServicesAvailable(context);
        return status != ConnectionResult.SUCCESS;
    }

    /**
     *
     * @param context
     */
    public static void buildAlertMessageUpdatePlayServices(final Context context) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage(context.getResources().getString(R.string.update_playservice))
                .setCancelable(false)
                .setPositiveButton("Si", new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, final int id) {
                        context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + "com.google.android.gms")));
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, final int id) {
                        dialog.cancel();
                    }
                });
        final AlertDialog alert = builder.create();
        alert.show();
    }
}
