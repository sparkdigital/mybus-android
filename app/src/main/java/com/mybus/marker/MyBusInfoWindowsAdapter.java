package com.mybus.marker;

import android.view.View;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;
import com.mybus.model.MyBusMap;
import com.mybus.view.indowindow.ChargePointMarkerInfoWindow;
import com.mybus.view.indowindow.GenericMarkerInfoWindow;

/**
 * Created by Julian Gonzalez <jgonzalez@devspark.com>
 * Custom infoWindows for markers
 */
public class MyBusInfoWindowsAdapter implements GoogleMap.InfoWindowAdapter {

    private final MyBusMap mMyBusMap;


    public MyBusInfoWindowsAdapter(MyBusMap myBusMap) {
        mMyBusMap = myBusMap;
    }

    @Override
    public View getInfoWindow(Marker marker) {
        return null;
    }

    @Override
    public View getInfoContents(Marker marker) {
        View resultView;
        //Detect which type of marker is:
        MyBusMarker myBusMarker = mMyBusMap.isMyBusMarker(marker);
        if (myBusMarker != null) {
            switch (myBusMarker.getType()) {
                case MyBusMarker.ORIGIN:
                case MyBusMarker.DESTINATION:
                    resultView = new GenericMarkerInfoWindow(mMyBusMap.getContext())
                            .setMyBusMarker(myBusMarker);
                    break;
                case MyBusMarker.CHARGING_POINT:
                    resultView = new ChargePointMarkerInfoWindow(mMyBusMap.getContext())
                            .setChargePoint(mMyBusMap.getChargePointPresent(myBusMarker));
                    break;
                default:
                    resultView = new GenericMarkerInfoWindow(mMyBusMap.getContext())
                            .setMapMarker(marker)
                            .setFavIconVisible(false);
                    break;
            }
        } else {
            resultView = new GenericMarkerInfoWindow(mMyBusMap.getContext())
                    .setMapMarker(marker)
                    .setFavIconVisible(false);
        }
        return resultView;
    }
}
