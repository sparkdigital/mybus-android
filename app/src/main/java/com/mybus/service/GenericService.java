package com.mybus.service;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class GenericService {

    private OkHttpClient client;

    public GenericService() {
        client = new OkHttpClient();
    }

    /**
     * @param url
     * @return
     * @throws IOException
     */
    protected String executeUrl(String url) throws IOException {
        Request request = new Request.Builder()
                .url(url)
                .build();
        Call call = client.newCall(request);
        Response response = call.execute();
        return response.body().string();
    }
}
