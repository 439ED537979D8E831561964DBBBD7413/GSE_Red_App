package com.gsered.gse;

import android.app.FragmentManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.gsered.gse.share.share_modal;
import com.gsered.gse.smartapp.company_webview;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class CouponsActivity extends AppCompatActivity {

    private FrameLayout progressbar;
    private TabLayout tabLayout;
    private AppBarLayout appBarLayout;
    private ViewPager viewPager;
    private TextView uptoraise;
    private ImageView logo;
    private Button button;
    private static String APPCRED = "SIGNCRED";
    private static String NGO_ID = "NGOID";
    private static String USER_ID = "USERID";
    private static String usersngo = "usersngo";
    private String admitad_ad,name,url,raise,imgurl,probability;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coupons);

        Intent i = getIntent();
        admitad_ad = i.getStringExtra("admitad_id");
        name = i.getStringExtra("name");
        url = i.getStringExtra("url");
        raise = i.getStringExtra("raise");
        imgurl = i.getStringExtra("imgurl");
        probability = i.getStringExtra("probability");

        SharedPreferences sharedPreferences = getSharedPreferences(APPCRED, MODE_PRIVATE);
        String ngoname = sharedPreferences.getString(usersngo, "");
        if(ngoname.isEmpty())
            ngoname="Hers Foundation";

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(name);

        progressbar = (FrameLayout) findViewById(R.id.progressbar);
        tabLayout = (TabLayout) findViewById(R.id.tabs);
        appBarLayout = (AppBarLayout) findViewById(R.id.companyname);
        viewPager = (ViewPager) findViewById(R.id.viewpager_id);
        uptoraise = (TextView) findViewById(R.id.companyraise);
        logo = (ImageView) findViewById(R.id.companylogo);
        button = (Button) findViewById(R.id.companyactivate);

        checkoffers_availability();

        uptoraise.setText("Upto "+raise+" donation to "+ngoname);
        Glide.with(this).load(imgurl).placeholder(R.mipmap.ic_launcher).into(logo);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                redirecttovisitshop();
            }
        });

        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());

        Bundle bundle = new Bundle();
        bundle.putString("admitad_id", admitad_ad);
        bundle.putString("imgurl", imgurl);
        bundle.putString("companyname", name);
        bundle.putString("probability", probability);
        allcoupon_fragment allcoupons = new allcoupon_fragment();
        allcoupons.setArguments(bundle);

        // Add fragment
        adapter.AddFragment(allcoupons, "All offers");
//        adapter.AddFragment(new discountedcoupons_fragment(admitad_ad,imgurl),"Discounts");
//        adapter.AddFragment(new coupononly_fragment(admitad_ad,imgurl,url), "Coupons");

        // adapter setup
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
    }

    public void checkoffers_availability(){

        OkHttpClient client = new OkHttpClient();
        String url = "https://www.gsered.com/intern/grp10/GSE_App/check_coupons_availability.php?id="+admitad_ad;
        Request request = new Request.Builder()
                .url(url)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                if(response.isSuccessful()){
                    final String posts = response.body().string();
                    if(CouponsActivity.this == null)
                        return;

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            JSONObject socialpost = null;
                            try {
                                socialpost = new JSONObject(posts);
                                JSONObject p = socialpost.getJSONObject("coupon");
                                if(p.getString("status").equalsIgnoreCase("no")) {
                                    redirecttovisitshop();
                                } else {
                                    progressbar.setVisibility(View.GONE);
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

    public void redirecttovisitshop() {
        Intent intent = new Intent(CouponsActivity.this, VisitShop.class);
        intent.putExtra("source","activate");
        intent.putExtra("url",url);
        intent.putExtra("imgurl",imgurl);
        intent.putExtra("companyname",name);
        intent.putExtra("probability",probability);
        startActivity(intent);
    }
}
