package com.mybus.view;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.mybus.R;

import butterknife.ButterKnife;

/**
 * Created by Julian Gonzalez <jgonzalez@devspark.com>
 */

public class NotificationDialog extends DialogFragment {

    public static final String NOTIFICATION_MSG = "Notification_MSG";

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {


        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.notification_dialog, null);

        // Notification message:
        TextView notificationTextView = ButterKnife.findById(view, R.id.notificationMsg);
        Bundle bundle = getArguments();
        notificationTextView.setText(bundle.getString(NOTIFICATION_MSG));

        builder.setView(view)
                .setPositiveButton(getString(R.string.ok),
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });
        return builder.create();
    }
}
