package com.mybus.builder;

import android.net.Uri;

/**
 *
 * GIS Service API url builder.
 *
 * @author Enrique Pennimpede <epennimpede@devspark.com>
 */
public class GisServiceUrlBuilder {

    //TODO: Replace constants by system properties
    private static final String SCHEME = "http";
    private static final String AUTHORITY = "gis.mardelplata.gob.ar";
    private static final String API = "opendata";
    private static final String TOKEN = "rwef3253465htrt546dcasadg4343";

    /**
     *
     * Creates the API url in order to get the streets that include
     * a specific street name
     *
     * @param streetName
     * @return API url
     */
    public static String buildFindStreetsUrl(String streetName) {
        Uri.Builder builder = buildBaseUri()
                .appendPath("ws.php")
                .appendQueryParameter("method", "rest")
                .appendQueryParameter("endpoint", "callejero_mgp")
                .appendQueryParameter("nombre_calle", streetName)
                .appendQueryParameter("token", TOKEN);
        return builder.build().toString();
    }

    /**
     *
     * Creates the base API url.
     *
     * @return base API url
     */
    public static Uri.Builder buildBaseUri() {
        Uri.Builder builder = new Uri.Builder();
        return builder.scheme(SCHEME)
                .authority(AUTHORITY)
                .appendPath(API);
    }

}
