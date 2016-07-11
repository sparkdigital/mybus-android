package com.mybus.asynctask;

import com.mybus.model.TouristicPlace;

import java.util.List;

/**
 * Created by Lucas De Lio on 6/23/2016.
 */
public interface TouristicPlacesRequestCallback {

    void onPlacesFound(List<TouristicPlace> places);
}
