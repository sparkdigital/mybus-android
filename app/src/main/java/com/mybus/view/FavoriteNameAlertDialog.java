package com.mybus.view;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import com.mybus.R;
import com.mybus.model.FavoriteLocation;

import butterknife.ButterKnife;

/**
 * Created by Lucas De Lio on 6/13/2016.
 * Alert Dialog used to enter or edit a Favorite Name
 */
public class FavoriteNameAlertDialog extends BaseDialogFragment<FavoriteNameAlertDialog.FavoriteAddOrEditNameListener> {

    public static final int TYPE_EDIT = 1;
    public static final int TYPE_ADD = 2;
    private static final String ARGUMENT_TYPE = "TYPE";
    private static final String ARGUMENT_NAME = "NAME";

    private FavoriteLocation mFavoriteLocation;

    /**
     * @param type
     * @param previousName
     * @param favLocation  to modify
     * @return
     */
    public static FavoriteNameAlertDialog newInstance(int type, String previousName, FavoriteLocation favLocation) {
        FavoriteNameAlertDialog frag = new FavoriteNameAlertDialog();
        frag.mFavoriteLocation = favLocation;
        Bundle args = new Bundle();
        args.putInt(ARGUMENT_TYPE, type);
        args.putString(ARGUMENT_NAME, previousName);
        frag.setArguments(args);
        return frag;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final int dialogType = getArguments().getInt(ARGUMENT_TYPE, -1);
        String previousName = getArguments().getString(ARGUMENT_NAME, null);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        // Get the layout inflater
        LayoutInflater inflater = getActivity().getLayoutInflater();
        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        View view = inflater.inflate(R.layout.favorite_name_alert_dialog, null);
        final EditText favofiteNameEditText = ButterKnife.findById(view, R.id.favorite_name_edit_text);
        if (previousName != null) {
            favofiteNameEditText.setText(previousName);
        }
        builder.setView(view)
                .setPositiveButton(getString(R.string.ok),
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                switch (dialogType) {
                                    case TYPE_ADD:
                                        mFavoriteLocation.setName(favofiteNameEditText.getText().toString());
                                        getActivityInstance().onNewFavoriteName(mFavoriteLocation);
                                        break;
                                    case TYPE_EDIT:
                                        mFavoriteLocation.setName(favofiteNameEditText.getText().toString());
                                        getActivityInstance().onEditFavoriteName(mFavoriteLocation);
                                        break;
                                    default:
                                        break;
                                }
                            }
                        })
                .setNegativeButton(getString(R.string.cancel),
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });
        return builder.create();
    }

    /**
     * Listener for FavoriteNameAlertDialog callbacks
     */
    public interface FavoriteAddOrEditNameListener {

        /**
         * @param favoriteLocation with the name updated
         */
        void onNewFavoriteName(FavoriteLocation favoriteLocation);

        /**
         * @param favoriteLocation with the new name updated
         */
        void onEditFavoriteName(FavoriteLocation favoriteLocation);
    }
}
