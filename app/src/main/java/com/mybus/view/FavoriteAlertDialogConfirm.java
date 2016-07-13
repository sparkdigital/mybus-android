package com.mybus.view;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import com.mybus.R;
import com.mybus.marker.MyBusMarker;
import com.mybus.model.FavoriteLocation;

/**
 * Created by Julian Gonzalez <jgonzalez@devspark.com>
 * Alert dialog used to confirm a favorite addition or remove
 */
public class FavoriteAlertDialogConfirm extends BaseDialogFragment<FavoriteAlertDialogConfirm.OnFavoriteDialogConfirmClickListener> {
    //FavoriteLocation associated to this alert
    private FavoriteLocation mFavLocation;
    //MyBusMarker will be used to updated the marker after a favorite remove
    private MyBusMarker mMarker;
    //Alert Type ADD or Remove
    private Integer mType;
    public static final int ADD = 0;
    public static final int REMOVE = 1;

    // interface to handle the dialog click back to the Activity
    public interface OnFavoriteDialogConfirmClickListener {
        void onOkFavoriteAlertConfirmClicked(FavoriteAlertDialogConfirm dialog);
    }

    // Create an instance of the Dialog
    public static FavoriteAlertDialogConfirm newInstance(int type, String title, String message, FavoriteLocation favLocation, MyBusMarker marker) {
        FavoriteAlertDialogConfirm frag = new FavoriteAlertDialogConfirm();
        frag.mType = type;
        frag.mFavLocation = favLocation;
        frag.mMarker = marker;
        Bundle args = new Bundle();
        args.putString("title", title);
        args.putString("msg", message);
        frag.setArguments(args);
        return frag;
    }

    // Create a Dialog using default AlertDialog builder
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        return new AlertDialog.Builder(getActivity())
                .setTitle(getArguments().getString("title"))
                .setMessage(getArguments().getString("msg"))
                .setCancelable(false)
                .setPositiveButton(getString(R.string.yes),
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                // Positive button clicked
                                getActivityInstance().onOkFavoriteAlertConfirmClicked(FavoriteAlertDialogConfirm.this);
                            }
                        }
                )
                .setNegativeButton(getString(R.string.no),
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                // negative button clicked
                                dialog.cancel();
                            }
                        }
                )
                .create();
    }

    public FavoriteLocation getFavoriteLocation() {
        return mFavLocation;
    }

    public MyBusMarker getMarker() {
        return mMarker;
    }

    public Integer getDialogType(){
        return mType;
    }
}
