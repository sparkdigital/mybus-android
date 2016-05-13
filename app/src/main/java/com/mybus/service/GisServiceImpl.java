package com.mybus.service;

import com.mybus.builder.GisServiceUrlBuilder;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GisServiceImpl extends GenericService implements GisService{

    /**
     * @param constraint
     * @return
     * @throws IOException
     * @throws JSONException
     */
    @Override
    public List<String> findStreets(String constraint){

        List<String> results = new ArrayList<>();
        String url = GisServiceUrlBuilder.buildFindStreetsUrl(constraint);
        try {
            JSONArray jsonArray = new JSONArray(executeUrl(url));
            for (int i = 0; i < jsonArray.length(); i++) {
                results.add(jsonArray.getJSONObject(i).getString("descripcion"));
            }
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
        return results;
    }
}
