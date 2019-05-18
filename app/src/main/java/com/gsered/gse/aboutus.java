package com.gsered.gse;

import android.content.Context;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.customtabs.CustomTabsIntent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.Calendar;

public class aboutus extends AppCompatActivity {

    private FloatingActionButton fb, inst, twt, yt;
    private RelativeLayout visit;
    private TextView version, year;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aboutus);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Vision");

        fb = (FloatingActionButton) findViewById(R.id.fbfab);
        twt = (FloatingActionButton) findViewById(R.id.twtfab);
        inst = (FloatingActionButton) findViewById(R.id.instfab);
        yt = (FloatingActionButton) findViewById(R.id.ytfab);
        visit = (RelativeLayout) findViewById(R.id.visitsite);
        version = (TextView) findViewById(R.id.textView5);
        year = (TextView) findViewById(R.id.textView6);

        String currentversion = getAppVersion(this);
        version.setText("Version "+currentversion);

        int currentyear = Calendar.getInstance().get(Calendar.YEAR);
        int nextyear = currentyear+1;
        year.setText("\u00A9 "+String.valueOf(currentyear)+"-"+String.valueOf(nextyear)+", gsered.com");

        visit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url = "https://gsered.com/";
                CustomTabsIntent.Builder intentBuilder = new CustomTabsIntent.Builder();
                CustomTabsIntent customTabsIntent = intentBuilder.build();
                customTabsIntent.launchUrl(view.getContext(), Uri.parse(url));
            }
        });

        fb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url = "https://www.facebook.com/GSERed/";
                CustomTabsIntent.Builder intentBuilder = new CustomTabsIntent.Builder();
                CustomTabsIntent customTabsIntent = intentBuilder.build();
                customTabsIntent.launchUrl(view.getContext(), Uri.parse(url));
            }
        });

        twt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url = "https://twitter.com/gse_red";
                CustomTabsIntent.Builder intentBuilder = new CustomTabsIntent.Builder();
                CustomTabsIntent customTabsIntent = intentBuilder.build();
                customTabsIntent.launchUrl(view.getContext(), Uri.parse(url));
            }
        });

        inst.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url = "https://www.instagram.com/gse_red/";
                CustomTabsIntent.Builder intentBuilder = new CustomTabsIntent.Builder();
                CustomTabsIntent customTabsIntent = intentBuilder.build();
                customTabsIntent.launchUrl(view.getContext(), Uri.parse(url));
            }
        });

        yt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url = "https://www.youtube.com/channel/UCSIGa4cY7fSJuDaRRJCy2mw";
                CustomTabsIntent.Builder intentBuilder = new CustomTabsIntent.Builder();
                CustomTabsIntent customTabsIntent = intentBuilder.build();
                customTabsIntent.launchUrl(view.getContext(), Uri.parse(url));
            }
        });
    }

    private String getAppVersion(Context context){
        String result = "";
        try{
            result = context.getPackageManager().getPackageInfo(context.getPackageName(),0)
                    .versionName;
            result = result.replaceAll("[a-zA-Z]|-","");

        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        return result;
    }

}
