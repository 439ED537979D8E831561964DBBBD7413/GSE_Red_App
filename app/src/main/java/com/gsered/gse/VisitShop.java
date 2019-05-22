package com.gsered.gse;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.support.customtabs.CustomTabsIntent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;

import org.w3c.dom.Text;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

public class VisitShop extends AppCompatActivity {

    private static String APPCRED = "SIGNCRED";
    private static String NGO_ID = "NGOID";
    private static String USER_ID = "USERID";
    private static String usersngo = "usersngo";
    ImageView setlogo,indicator1,indicator2,indicator3,indicator4;
    TextView supporting,condition2;
    String readylink;
    ProgressBar progressBar;
    AdView ad;
    Runnable runnable;
    android.os.Handler handler = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visit_shop);

        setlogo = (ImageView) findViewById(R.id.companylogo);
        indicator1 = (ImageView) findViewById(R.id.indicator1);
        indicator2 = (ImageView) findViewById(R.id.indicator2);
        indicator3 = (ImageView) findViewById(R.id.indicator3);
        indicator4 = (ImageView) findViewById(R.id.indicator4);
        progressBar = (ProgressBar) findViewById(R.id.progressbar);
        supporting = (TextView) findViewById(R.id.user_supporting);
        condition2 = (TextView) findViewById(R.id.condition2);
        ad = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        ad.loadAd(adRequest);

        SharedPreferences sharedPreferences = getSharedPreferences(APPCRED, MODE_PRIVATE);
        String nid = sharedPreferences.getString(NGO_ID,"");
        String uid = sharedPreferences.getString(USER_ID,"");
        String subid = "app-"+nid+"-"+uid;

        Intent i = getIntent();
        final String source = i.getStringExtra("source");
        final String url = i.getStringExtra("url");
        final String imgurl = i.getStringExtra("imgurl");
        final String companyname = i.getStringExtra("companyname");
        final String probability = i.getStringExtra("probability");

        String cond2 = "2.Until now, "+companyname+" has shown "+probability+"% probability of successful contribution from shopping via GSE Red.";
        supporting.setText(sharedPreferences.getString(usersngo,""));
        condition2.setText(cond2);

        //set comapny logo start
        Glide.with(this).load(imgurl).placeholder(R.mipmap.ic_launcher).into(setlogo);
        //set company logo end

        if(source.equalsIgnoreCase("activate"))
            readylink = url.replace("hers",subid);
        else
            readylink = url+"&subid="+subid;

        // set click here process start
        setlink("If you are not automatically re-directed, please click here",49,59);
        //end

        // start wait for 4 seconds before redirect start
        handler = new android.os.Handler();
        runnable = new Runnable() {
            @Override
            public void run() {

                Intent i = new Intent(VisitShop.this, OpenUrl.class);
                i.putExtra("url", readylink);
                startActivity(i);

            }
        };
        handler.postDelayed(runnable, 4000);
        // end wait for 4 seconds before redirect start
    }

    public void setlink(String text,int start,int end){
        // sign up text for new user
        TextView textView = findViewById(R.id.redirectnow);
        // clicking on signup
        SpannableString ss = new SpannableString(text);
        ClickableSpan clickableSpan = new ClickableSpan() {
            @Override
            public void onClick(View view) {

                handler.removeCallbacks(runnable);
                Intent i = new Intent(VisitShop.this, OpenUrl.class);
                i.putExtra("url", readylink);
                startActivity(i);
            }
        };
        ss.setSpan(clickableSpan,start,end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        textView.setText(ss);
        textView.setMovementMethod(LinkMovementMethod.getInstance());
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        handler.removeCallbacks(runnable);
        return super.onKeyDown(keyCode, event);
    }
}
