package com.mybus.helper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by ldimitroff on 12/05/16.
 */
public class FileLoaderHelper {

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
            ex.printStackTrace();
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
            ex.printStackTrace();
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
        int size = is.available();
        byte[] buffer = new byte[size];
        is.read(buffer);
        is.close();
        return new String(buffer, "UTF-8");
    }
}
