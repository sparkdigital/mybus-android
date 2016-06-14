package com.mybus.view;


import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import com.mybus.R;
import com.mybus.listener.OnAddNewFavoriteListener;

import butterknife.ButterKnife;

/**
 * Created by Lucas De Lio on 6/13/2016.
 */
public class FavoriteNameAlertDialog extends DialogFragment {

    private OnAddNewFavoriteListener mOnAddNewFavoriteListener;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        // Get the layout inflater
        LayoutInflater inflater = getActivity().getLayoutInflater();

        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        View view = inflater.inflate(R.layout.favorite_name_alert_dialog, null);
        final EditText mFavofiteNameEditText = ButterKnife.findById(view, R.id.favorite_name_edit_text);
        builder.setView(view)
                // Add action button
                .setPositiveButton(getString(R.string.ok), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        mOnAddNewFavoriteListener.onAddNewFavorite(mFavofiteNameEditText.getText().toString());
                    }
                })
                .setNegativeButton(getString(R.string.cancel), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                    }
                });
        return builder.create();
    }

    public void setmOnAddNewFavoriteListener(OnAddNewFavoriteListener mOnAddNewFavoriteListener) {
        this.mOnAddNewFavoriteListener = mOnAddNewFavoriteListener;
    }

}
