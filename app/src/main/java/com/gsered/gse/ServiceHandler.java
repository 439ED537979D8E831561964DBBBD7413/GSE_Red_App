package com.gsered.gse;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;

public class ServiceHandler {
    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    OkHttpClient client = new OkHttpClient();

    public Call post(String domain, Callback callback) {
//        RequestBody body = RequestBody.create(JSON, "{\ndomain:" + domain + "}");
        Request request = new Request.Builder()
                .url("https://www.gsered.com/intern/grp10/GSE_App/fetch_company.php?domain=" + domain)
                .build();
        Call call = client.newCall(request);
        call.enqueue(callback);
        return call;
    }
    public Call post2(Callback callback) {
//        RequestBody body = RequestBody.create(JSON, "{\ndomain:" + domain + "}");
        Request request = new Request.Builder()
                .url("https://gsered.com/intern/grp10/DisplayNGO.php")
                .build();
        Call call = client.newCall(request);
        call.enqueue(callback);
        return call;
    }
    public Call update(String version, String packagename, Callback callback) {
//        RequestBody body = RequestBody.create(JSON, "{\ndomain:" + domain + "}");
        Request request = new Request.Builder()
                .url("https://gsered.com/intern/grp10/GSE_App/update_popup.php?userversion="+version+"&packagename="+packagename)
                .build();
        Call call = client.newCall(request);
        call.enqueue(callback);
        return call;
    }
    public Call totalreward(String uid, Callback callback) {
//        RequestBody body = RequestBody.create(JSON, "{\ndomain:" + domain + "}");
        Request request = new Request.Builder()
                .url("https://gsered.com/intern/grp10/GSE_App/earned/fetch_totalreward.php?uid="+uid)
                .build();
        Call call = client.newCall(request);
        call.enqueue(callback);
        return call;
    }

}