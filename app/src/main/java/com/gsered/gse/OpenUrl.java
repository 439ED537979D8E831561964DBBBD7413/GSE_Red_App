package com.gsered.gse;

import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;

public class OpenUrl extends AppCompatActivity {

    String link;
    WebView webView;
    ProgressBar progressBar;
    Runnable runnable;
    android.os.Handler handler = null;
    int count;
    String dat="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_open_url);

        webView = (WebView) findViewById(R.id.webView1);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        progressBar.setVisibility(View.GONE);

        //start updating game reward 4times a day
        Date c = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
        String todayDate = df.format(c);

        String u = getIntent().getStringExtra("url");
        if(u.contains("gamezop")) {
            SharedPreferences sharedPreferences = getSharedPreferences("rewardcount", MODE_PRIVATE);
            count = sharedPreferences.getInt( "rewardcount",0);
            dat = sharedPreferences.getString( "rewardcountdate","");

            if(dat.isEmpty() || !dat.equalsIgnoreCase(todayDate)){
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("rewardcountdate", todayDate);
                editor.commit();
                count = 0;
            }

            if(count != 4){
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putInt("rewardcount", count+1);
                editor.commit();
                countplaytime();
            }
//            Log.e("rewardcountdate : ", dat);
//            Log.e("rewardcount : ", String.valueOf(count));
        }
        // end reward updateing
        home();

        if(savedInstanceState==null){
            webView.post(new Runnable() {
                @Override
                public void run() {
                    home();
                }
            });
        }

    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState )
    {
        super.onSaveInstanceState(outState);
        webView.saveState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState)
    {
        super.onRestoreInstanceState(savedInstanceState);
        webView.restoreState(savedInstanceState);
    }

    private void countplaytime() {

        handler = new android.os.Handler();
        runnable = new Runnable() {
            @Override
            public void run() {
                SharedPreferences sharedPreferences = getSharedPreferences("SIGNCRED", MODE_PRIVATE);
                String uid = sharedPreferences.getString("USERID","");

                OkHttpClient client = new OkHttpClient();
                String url = "https://www.gsered.com/intern/grp10/GSE_App/dashboard/add_game_reward.php?uid="+uid;
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
                            String responseStr = response.body().string();
                            if(OpenUrl.this == null)
                                return;

                            Log.e("game db : ", "updated");
                        }
                    }
                });

            }
        };
        handler.postDelayed(runnable, 2000);

    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    public void click(View view) {
        link = "https://gsered.com";
        loadLink();
    }

    public void loadLink() {
        WebSettings webSetting = webView.getSettings();
        webSetting.setBuiltInZoomControls(true);
        webView.setWebViewClient(new WebViewClient());
        webSetting.setJavaScriptEnabled(true);
    }

    public void loadLocalPage() {
        WebSettings webSetting = webView.getSettings();
        webSetting.setBuiltInZoomControls(true);
        webView.setWebViewClient(new WebViewClient());
        webView.loadUrl("file:///android_asset/webpage.html");
    }

    public void refresh() {
        loadLink();
    }

    public void home() {
        WebSettings webSetting = webView.getSettings();
        webSetting.setBuiltInZoomControls(true);
        webSetting.setJavaScriptEnabled(true);
        webSetting.setLoadWithOverviewMode(true);
        webSetting.setUseWideViewPort(true);
        webSetting.setDomStorageEnabled(true);
        webSetting.setAppCacheEnabled(true);
        webView.setWebViewClient(new WebViewClient());
        webView.loadUrl(getIntent().getStringExtra("url"));
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        handler.removeCallbacks(runnable);
        if ((keyCode == KeyEvent.KEYCODE_BACK) && webView.canGoBack()) {
            webView.goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    public class WebViewClient extends android.webkit.WebViewClient
    {
        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {

            // TODO Auto-generated method stub
            super.onPageStarted(view, url, favicon);
            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {

            // TODO Auto-generated method stub
            view.loadUrl(url);
            return true;
        }
        @Override
        public void onPageFinished(WebView view, String url) {

            // TODO Auto-generated method stub

            super.onPageFinished(view, url);
            progressBar.setVisibility(View.GONE);
        }
    }
}
