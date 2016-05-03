package com.mybus.builder;

import android.net.Uri;

/**
 * @author Enrique Pennimpede <epennimpede@devspark.com>
 */
public class UrlBuilder {

    private static final String SCHEME = "http";
    private static final String AUTHORITY = "www.mybus.com.ar";
    private static final String API = "api";
    private static final String VERSION = "v1";
    private static final String TOKEN = "94a08da1fecbb6e8b46990538c7b50b2";

    public static String buildSingleRoadUrl(String mIdLine, String mDirection,
                                            String mStop1, String mStop2) {
        Uri.Builder builder = buildBaseUri()
                .appendPath("SingleRoadApi.php")
                .appendQueryParameter("idline", mIdLine)
                .appendQueryParameter("direction", mDirection)
                .appendQueryParameter("stop1", mStop1)
                .appendQueryParameter("stop2", mStop2)
                .appendQueryParameter("tk", TOKEN);
        return builder.build().toString();
    }

    public static String buildCombinedRoadUrl(String mIdLine, String mIdLine2,
                                              String mDirection, String mDirection2,
                                              String mStop1, String mStop2, String mStop1L2,
                                              String mStop2L2) {
        Uri.Builder builder = buildBaseUri()
                .appendPath("CombinedRoadApi.php")
                .appendQueryParameter("idline1", mIdLine)
                .appendQueryParameter("idline2", mIdLine2)
                .appendQueryParameter("direction1", mDirection)
                .appendQueryParameter("direction2", mDirection2)
                .appendQueryParameter("L1stop1", mStop1)
                .appendQueryParameter("L1stop2", mStop2)
                .appendQueryParameter("L2stop1", mStop1L2)
                .appendQueryParameter("L2stop2", mStop2L2)
                .appendQueryParameter("tk", TOKEN);
        return builder.build().toString();
    }

    private static Uri.Builder buildBaseUri() {
        Uri.Builder builder = new Uri.Builder();
        return builder.scheme(SCHEME)
                .authority(AUTHORITY)
                .appendPath(API)
                .appendPath(VERSION);
    }

}
