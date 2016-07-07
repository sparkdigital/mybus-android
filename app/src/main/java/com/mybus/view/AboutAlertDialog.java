package com.mybus.view;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.mybus.R;

import butterknife.ButterKnife;

/**
 * Created by Lucas De Lio on 7/06/2016.
 * Alert Dialog to show about information
 */
public class AboutAlertDialog extends DialogFragment {

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.about_alert_dialog, null);
        //get the rate textView and set the link to GooglePlay
        TextView linkTextView = ButterKnife.findById(view, R.id.link_to_store);
        linkTextView.setText(Html.fromHtml(String.format(getString(R.string.html_link), getString(R.string.app_store_url), getString(R.string.rate_this_app))));
        linkTextView.setMovementMethod(LinkMovementMethod.getInstance());
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
