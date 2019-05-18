package com.gsered.gse.NewsFeed;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;

public class NewsServiceHandler {
    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    OkHttpClient client = new OkHttpClient();

    Call post(Callback callback) {
        Request request = new Request.Builder()
                .url("https://newsapi.org/v2/top-headlines?country=in&apiKey=cb2c98e096a04508b86e1238386deb08")
                .build();
        Call call = client.newCall(request);
        call.enqueue(callback);
        return call;
    }
}