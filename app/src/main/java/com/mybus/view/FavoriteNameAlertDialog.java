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
import com.mybus.listener.FavoriteAddListener;
import com.mybus.listener.FavoriteChangeNameListener;

import butterknife.ButterKnife;

/**
 * Created by Lucas De Lio on 6/13/2016.
 */
public class FavoriteNameAlertDialog extends DialogFragment {

    private FavoriteAddListener mOnAddNewFavoriteListener;
    private FavoriteChangeNameListener mFavoriteChangeNameListener;
    private String mDialogType;
    public static final String TYPE_EDIT = "TYPE_EDIT";
    public static final String TYPE_ADD = "TYPE_ADD";

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
                        switch (mDialogType) {
                            case TYPE_ADD: {
                                mOnAddNewFavoriteListener.onAddNewFavorite(mFavofiteNameEditText.getText().toString());
                                break;
                            }
                            case TYPE_EDIT: {
                                mFavoriteChangeNameListener.onChangeFavoriteName(mFavofiteNameEditText.getText().toString());
                                break;
                            }
                            default:
                                break;
                        }
                    }
                })
                .setNegativeButton(getString(R.string.cancel), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                    }
                });
        return builder.create();
    }

    /**
     * @param onAddNewFavoriteListener
     */
    public void setActionAdding(FavoriteAddListener onAddNewFavoriteListener) {
        this.mOnAddNewFavoriteListener = onAddNewFavoriteListener;
    }

    /**
     * @param favoriteEditOldListener
     */
    public void setActionEditing(FavoriteChangeNameListener favoriteEditOldListener) {
        this.mFavoriteChangeNameListener = favoriteEditOldListener;
    }

    /**
     * @param previousName
     */
    public void setPreviousName(String previousName) {
        this.mPreviousName = previousName;
    }

    public void setDialogType(String type) {
        this.mDialogType = type;
    }


}
