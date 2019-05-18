package com.gsered.gse;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.gsered.gse.modals.refercode;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.Inflater;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;

public class allcoupon_fragment extends Fragment {

    View view;
    private RecyclerView recyclerView;
    private List<coupon_modal> coupons;
    private FrameLayout progressbar;
    private FrameLayout textView;
    private String admitadid,imgurl,cmpnyname,probability;

    public allcoupon_fragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.all, container, false);
        recyclerView =(RecyclerView) view.findViewById(R.id.all_coupons);
        progressbar = (FrameLayout) view.findViewById(R.id.progressbar);
        textView = (FrameLayout) view.findViewById(R.id.textView_noresult);

        couponadapter couponadapter = new couponadapter(getContext(),coupons, imgurl, cmpnyname, probability);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(couponadapter);
        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            this.admitadid = getArguments().getString("admitad_id");
            this.imgurl = getArguments().getString("imgurl");
            this.cmpnyname = getArguments().getString("companyname");
            this.probability = getArguments().getString("probability");
        }

        coupons = new ArrayList<>();
        OkHttpClient client = new OkHttpClient();
        String url = "https://www.gsered.com/intern/grp10/GSE_App/fetch_coupon.php?cat=all&id="+admitadid;
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

                    if(getActivity() == null)
                        return;

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
                                    coupons.add(new coupon_modal(r.getString("title"),r.getString("couponcode"),r.getString("description"),r.getString("expiry"),r.getString("verify"),r.getString("used"),r.getString("url")));
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
    }
}
