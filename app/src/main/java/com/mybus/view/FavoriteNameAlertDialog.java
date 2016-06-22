package com.mybus.view;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import com.mybus.R;

import butterknife.ButterKnife;

/**
 * Created by Lucas De Lio on 6/13/2016.
 */
public class FavoriteNameAlertDialog extends DialogFragment {

    public static final int TYPE_EDIT = 1;
    public static final int TYPE_ADD = 2;
    private static final String ARGUMENT_TYPE = "TYPE";
    private static final String ARGUMENT_NAME = "NAME";

    private FavoriteAddOrEditNameListener mCallback;

    /**
     * Listener for FavoriteNameAlertDialog callbacks
     */
    public interface FavoriteAddOrEditNameListener {

        /**
         * @param favoriteName
         */
        void onNewFavoriteName(String favoriteName);

        /**
         * @param favoriteName
         */
        void onEditFavoriteName(String favoriteName);
    }

    /**
     * @param type
     * @param previousName
     * @return
     */
    public static FavoriteNameAlertDialog newInstance(int type, String previousName) {
        FavoriteNameAlertDialog frag = new FavoriteNameAlertDialog();
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
        final EditText mFavofiteNameEditText = ButterKnife.findById(view, R.id.favorite_name_edit_text);
        if (previousName != null) {
            mFavofiteNameEditText.setText(previousName);
        }
        builder.setView(view)
                .setPositiveButton(getString(R.string.ok), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        //callback to OnAdd or OnEdit if it's adding or editing the favorite
                        switch (dialogType) {
                            case TYPE_ADD:
                                if (mCallback != null) {
                                    mCallback.onNewFavoriteName(mFavofiteNameEditText.getText().toString());
                                }
                                break;
                            case TYPE_EDIT:
                                if (mCallback != null) {
                                    mCallback.onEditFavoriteName(mFavofiteNameEditText.getText().toString());
                                }
                                break;
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

    @Override
    public void onAttach(Activity activity) throws ClassCastException {
        super.onAttach(activity);

        // This makes sure that the container activity has implemented
        // the callback interface. If not, it throws an exception
        try {
            mCallback = (FavoriteAddOrEditNameListener) activity;
        } catch (ClassCastException e) {
            throw (ClassCastException) new ClassCastException(activity.toString()
                    + " must implement FavoriteAddOrEditNameListener")
                    .initCause(e);
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mCallback = null;
    }
}
