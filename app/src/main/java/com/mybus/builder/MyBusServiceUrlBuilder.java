package com.mybus.builder;

import android.net.Uri;

import com.mybus.model.road.RoadSearch;

/**
 * MyBus Service Public API url builder.
 *
 * @author Enrique Pennimpede <epennimpede@devspark.com>
 */
public final class MyBusServiceUrlBuilder {

    //TODO: Replace constants by system properties
    private static final String SCHEME = "http";
    private static final String AUTHORITY = "www.mybus.com.ar";
    private static final String API = "api";
    private static final String VERSION = "v1";
    private static final String TOKEN = "94a08da1fecbb6e8b46990538c7b50b2";

    private MyBusServiceUrlBuilder() {
    }

    /**
     * Creates the API url in order to get a single road.
     *
     * @param roadSearch
     * @return API url for a single road.
     */
    public static String buildSingleRoadUrl(RoadSearch roadSearch) {
        Uri.Builder builder = buildBaseUri()
                .appendPath("SingleRoadApi.php")
                .appendQueryParameter("idline", roadSearch.getmIdLine())
                .appendQueryParameter("direction", roadSearch.getmDirection())
                .appendQueryParameter("stop1", roadSearch.getmStop1())
                .appendQueryParameter("stop2", roadSearch.getmStop2())
                .appendQueryParameter("tk", TOKEN);
        return builder.build().toString();
    }

    /**
     * Creates the API url in order to get a combined road.
     *
     * @param roadSearch
     * @return API url for a combined road.
     */
    public static String buildCombinedRoadUrl(RoadSearch roadSearch) {
        Uri.Builder builder = buildBaseUri()
                .appendPath("CombinedRoadApi.php")
                .appendQueryParameter("idline1", roadSearch.getmIdLine())
                .appendQueryParameter("idline2", roadSearch.getmIdLine2())
                .appendQueryParameter("direction1", roadSearch.getmDirection())
                .appendQueryParameter("direction2", roadSearch.getmDirection2())
                .appendQueryParameter("L1stop1", roadSearch.getmStop1())
                .appendQueryParameter("L1stop2", roadSearch.getmStop2())
                .appendQueryParameter("L2stop1", roadSearch.getmStop1L2())
                .appendQueryParameter("L2stop2", roadSearch.getmStop2L2())
                .appendQueryParameter("tk", TOKEN);
        return builder.build().toString();
    }

    /**
     * Creates the API url in order to access the Nexus Service.
     *
     * @param originLatitude
     * @param originLongitude
     * @param destinationLatitude
     * @param destinationLongitude
     * @return API url for Nexus Service.
     */
    public static String buildNexusUrl(Double originLatitude, Double originLongitude,
                                       Double destinationLatitude, Double destinationLongitude) {
        Uri.Builder builder = buildBaseUri()
                .appendPath("NexusApi.php")
                .appendQueryParameter("lat0", originLatitude.toString())
                .appendQueryParameter("lng0", originLongitude.toString())
                .appendQueryParameter("lat1", destinationLatitude.toString())
                .appendQueryParameter("lng1", destinationLongitude.toString())
                .appendQueryParameter("tk", TOKEN);
        return builder.build().toString();
    }

    /**
     * Creates the base API url
     *
     * @return base API url
     */
    public static Uri.Builder buildBaseUri() {
        Uri.Builder builder = new Uri.Builder();
        return builder.scheme(SCHEME)
                .authority(AUTHORITY)
                .appendPath(API)
                .appendPath(VERSION);
    }

}
