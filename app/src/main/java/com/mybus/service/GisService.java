package com.mybus.service;

import com.mybus.builder.GisServiceUrlBuilder;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;

public class GisService extends GenericService {

    /**
     * @param constraint
     * @return
     * @throws IOException
     * @throws JSONException
     */
    public JSONArray findStreets(String constraint) throws IOException, JSONException {
        String url = GisServiceUrlBuilder.buildFindStreetsUrl(constraint);
        return new JSONArray(executeUrl(url));
    }
}
