package com.gsered.gse;


import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import org.apache.http.util.EncodingUtils;

public class WebsiteView extends AppCompatActivity {
    ProgressBar progress;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_website_view);
        String method = getIntent().getStringExtra("method");
        String url  = getIntent().getStringExtra("url");
        String emailid  = getIntent().getStringExtra("emailid");
        String name  = getIntent().getStringExtra("name");
        String uid  = getIntent().getStringExtra("uid");
        WebView webView = (WebView)findViewById(R.id.web_view);
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webView.setWebViewClient(new WebViewClient());

        webView.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);

        if(method.equalsIgnoreCase("post"))
        {
            byte[] post = EncodingUtils.getBytes("usernm="+emailid+"&name="+name+"&uid="+uid, "BASE64");
            webView.postUrl(url,post);
        }
        else
        {
            byte[] get = EncodingUtils.getBytes("cause=GSE Red", "BASE64");
            webView.postUrl(url,get);
        }
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }


}