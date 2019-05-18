package com.gsered.gse;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.customtabs.CustomTabsIntent;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;

public class coupononly_fragment extends Fragment {
    View view;
    private RecyclerView recyclerView;
    private List<coupon_modal> coupons;
    private FrameLayout progressbar;
    private FrameLayout textView;
    private TextView visitstore;
    private String admitadid,imgurl,url;

    public coupononly_fragment(String admitadid, String imgurl, String url) {
        this.admitadid = admitadid;
        this.imgurl = imgurl;
        this.url = url;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.coupons_only, container, false);
        recyclerView =(RecyclerView) view.findViewById(R.id.all_coupons);
        progressbar = (FrameLayout) view.findViewById(R.id.progressbar);
        textView = (FrameLayout) view.findViewById(R.id.textView_noresult);
        visitstore = (TextView) view.findViewById(R.id.visitstore);

        setlink("Visit Store",0,11);

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
        String url = "https://www.gsered.com/intern/grp10/GSE_App/fetch_coupon.php?cat=coupons&id="+admitadid;
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

    public void setlink(String text,int start,int end){
        // clicking on signup
        SpannableString ss = new SpannableString(text);
        ClickableSpan clickableSpan = new ClickableSpan() {
            @Override
            public void onClick(View view) {
            Intent intent = new Intent(getActivity(), VisitShop.class);
            intent.putExtra("source","activate");
            intent.putExtra("url",url);
            intent.putExtra("imgurl",imgurl);
            startActivity(intent);
            }
        };
        ss.setSpan(clickableSpan,start,end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        visitstore.setText(ss);
        visitstore.setMovementMethod(LinkMovementMethod.getInstance());
    }

}
