package com.mybus.service;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class GenericService {

    private static final int CONNECTION_TIMEOUT = 15;

    private OkHttpClient client;

    public GenericService() {
        client = new OkHttpClient.Builder()
                .connectTimeout(CONNECTION_TIMEOUT, TimeUnit.SECONDS)
                .writeTimeout(CONNECTION_TIMEOUT, TimeUnit.SECONDS)
                .readTimeout(CONNECTION_TIMEOUT, TimeUnit.SECONDS)
                .build();
    }

    /**
     * @param url
     * @return
     * @throws IOException
     */
    protected String executeUrl(String url) throws IOException {
        return execute(url, null);
    }

    /**
     * @param url
     * @return
     * @throws IOException
     */
    protected String executePOST(String url, RequestBody body) throws IOException {
        return execute(url, body);
    }


    /**
     * Executes either GET or POST if a body is given
     *
     * @param url
     * @param body
     * @return
     * @throws IOException
     */
    private String execute(String url, RequestBody body) throws IOException {
        Request.Builder builder = new Request.Builder().url(url);
        if (body != null) {
            builder.post(body);
        }
        Call call = client.newCall(builder.build());
        Response response = call.execute();
        return response.body().string();
    }
}
