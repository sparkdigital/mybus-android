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
import com.mybus.listener.OnEditOldFavoriteListener;

import butterknife.ButterKnife;

/**
 * Created by Lucas De Lio on 6/13/2016.
 */
public class FavoriteNameAlertDialog extends DialogFragment {

    private OnAddNewFavoriteListener mOnAddNewFavoriteListener;
    private OnEditOldFavoriteListener mOnEditOldFavoriteListener;

    private String actionType;

    private String mPreviousName;
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        // Get the layout inflater
        LayoutInflater inflater = getActivity().getLayoutInflater();
        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        View view = inflater.inflate(R.layout.favorite_name_alert_dialog, null);
        final EditText mFavofiteNameEditText = ButterKnife.findById(view, R.id.favorite_name_edit_text);
        if (mPreviousName != null) {
            mFavofiteNameEditText.setText(mPreviousName);
        }
        builder.setView(view)
                .setPositiveButton(getString(R.string.ok), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        //callback to OnAdd or OnEdit if it's adding or editing the favorite
                        if (mOnAddNewFavoriteListener != null) {
                            mOnAddNewFavoriteListener.onAddNewFavorite(mFavofiteNameEditText.getText().toString());
                        } else if (mOnEditOldFavoriteListener != null) {
                            mOnEditOldFavoriteListener.onEditOldFavorite(mFavofiteNameEditText.getText().toString());
                        }
                    }
                })
                .setNegativeButton(getString(R.string.cancel), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                    }
                });
        return builder.create();
    }

    public void setActionAdding(OnAddNewFavoriteListener onAddNewFavoriteListener) {
        this.mOnAddNewFavoriteListener = onAddNewFavoriteListener;
        this.mOnEditOldFavoriteListener = null;
    }

    public void setActionEditing(OnEditOldFavoriteListener onEditOldFavoriteListener) {
        this.mOnEditOldFavoriteListener = onEditOldFavoriteListener;
        this.mOnAddNewFavoriteListener = null;
    }

    public void setPreviousName(String previousName) {
        this.mPreviousName = previousName;
    }


}
