package com.mybus.view.indowindow;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.mybus.R;
import com.mybus.model.ChargePoint;

/**
 * View for Charge Point Marker Info Window
 * <p/>
 * Created by ldimitroff on 07/07/16.
 */
public class ChargePointMarkerInfoWindow extends FrameLayout {

    private TextView mTvTitle;
    private TextView mTvAddress;
    private TextView mTvDistance;
    private TextView mTvOpenTime;

    public ChargePointMarkerInfoWindow(Context context) {
        super(context);
        initView();
    }

    public ChargePointMarkerInfoWindow(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public ChargePointMarkerInfoWindow(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    private void initView() {
        inflate(getContext(), R.layout.charge_point_marker_info_window, this);
        mTvTitle = ((TextView) findViewById(R.id.marker_title));
        mTvAddress = ((TextView) findViewById(R.id.marker_address));
        mTvDistance = ((TextView) findViewById(R.id.marker_distance));
        mTvOpenTime = ((TextView) findViewById(R.id.marker_open_time));
    }

    /**
     * @param chargePointPresent
     * @return
     */
    public View setChargePoint(ChargePoint chargePointPresent) {
        if (chargePointPresent != null) {
            mTvTitle.setText(chargePointPresent.getName());
            mTvAddress.setText(getContext().getString(R.string.charge_point_address,
                    chargePointPresent.getAddress()));
            mTvDistance.setText(getContext().getString(R.string.charge_point_distance,
                    chargePointPresent.getDistance()));
            mTvOpenTime.setText(getContext().getString(R.string.charge_point_open_time,
                    chargePointPresent.getOpenTime()));
        }
        return this;
    }
}
