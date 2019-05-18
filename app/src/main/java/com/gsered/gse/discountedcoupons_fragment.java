package com.gsered.gse;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;

public class discountedcoupons_fragment extends Fragment {
    View view;
    private RecyclerView recyclerView;
    private List<coupon_modal> coupons;
    private FrameLayout progressbar;
    private FrameLayout textView;
    private String admitadid,imgurl;

    public discountedcoupons_fragment(String admitadid, String imgurl) {
        this.admitadid = admitadid;
        this.imgurl = imgurl;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.discountedcoupon, container, false);
        recyclerView =(RecyclerView) view.findViewById(R.id.all_coupons);
        progressbar = (FrameLayout) view.findViewById(R.id.progressbar);
        textView = (FrameLayout) view.findViewById(R.id.textView_noresult);

        couponadapter couponadapter = new couponadapter(getContext(),coupons, imgurl, "", "");
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(couponadapter);
        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        coupons = new ArrayList<>();

        OkHttpClient client = new OkHttpClient();
        String url = "https://www.gsered.com/intern/grp10/GSE_App/fetch_coupon.php?cat=discounts&id="+admitadid;
        okhttp3.Request request = new okhttp3.Request.Builder()
                .url(url)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, final okhttp3.Response response) throws IOException {
                if(response.isSuccessful()){
                    final String posts = response.body().string();

                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            progressbar.setVisibility(View.GONE);
                            try {
                                JSONObject socialpost = null;
                                socialpost = new JSONObject(posts);
                                JSONObject p = socialpost.getJSONObject("offer");
                                if(p.getString("error").equalsIgnoreCase("coupon")) {
                                    for(int i=0; i<p.length(); i++){
                                        String id = Integer.toString(i+1);
                                        JSONObject r = p.getJSONObject(id);
                                        coupons.add(new coupon_modal(r.getString("title"),"",r.getString("description"),r.getString("expiry"),r.getString("verify"),r.getString("used"),r.getString("url")));
                                    }
                                } else {
                                    textView.setVisibility(View.VISIBLE);
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    });
                }
            }
        });
//        coupons.add(new coupon_modal("Get 4000 below mobile hurry up!","Deal of the day","23-02-2019","231"));
    }

}
