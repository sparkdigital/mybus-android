package com.mybus.helper;

import android.content.Context;
import android.util.Log;

import org.apache.commons.io.IOUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;

/**
 * Created by ldimitroff on 12/05/16.
 */
public final class FileLoaderHelper {

    private static final String TAG = FileLoaderHelper.class.getSimpleName();

    private FileLoaderHelper() {
    }

    /**
     * Loads a JSONObject from Resources
     *
     * @param obj
     * @param fileName
     * @return
     */
    public static JSONObject loadJSONObjectFromResource(Object obj, String fileName) {
        JSONObject result;
        try {
            result = new JSONObject(getStringFromResource(obj, fileName));
        } catch (IOException | JSONException ex) {
            Log.e(TAG, ex.toString());
            return null;
        }
        return result;
    }


    /**
     * Loads a JSONObject from Assets
     *
     * @param context
     * @param fileName
     * @return
     */
    public static JSONObject loadJSONObjectFromAssets(Context context, String fileName) {
        JSONObject result;
        try {
            result = new JSONObject(getStringFromAssets(context, fileName));
        } catch (IOException | JSONException ex) {
            Log.e(TAG, ex.toString());
            return null;
        }
        return result;
    }

    /**
     * Loads a JSONArray from Resources
     *
     * @param obj
     * @param fileName
     * @return
     */
    public static JSONArray loadJSONArrayFromResource(Object obj, String fileName) {
        JSONArray result;
        try {
            result = new JSONArray(getStringFromResource(obj, fileName));
        } catch (IOException | JSONException ex) {
            Log.e(TAG, ex.toString());
            return null;
        }
        return result;
    }


    /**
     * Gets a String from specific resource name
     *
     * @param obj
     * @param filename
     * @return
     * @throws IOException
     */
    private static String getStringFromResource(Object obj, String filename) throws IOException {
        InputStream is = obj.getClass().getClassLoader().getResourceAsStream(filename);
        return readInputStream(is);
    }


    /**
     * Gets a String from specific assets name
     *
     * @param context
     * @param filename
     * @return
     * @throws IOException
     */
    private static String getStringFromAssets(Context context, String filename) throws IOException {
        InputStream is = context.getResources().getAssets().open(filename, Context.MODE_WORLD_READABLE);
        return readInputStream(is);
    }

    /**
     *
     * @param is
     * @return
     * @throws IOException
     */
    private static String readInputStream(InputStream is) throws IOException {
        String text;
        try {
            text = IOUtils.toString(is, Charset.forName("UTF-8"));
        } finally {
            IOUtils.closeQuietly(is);
        }
        return text;
    }
}
