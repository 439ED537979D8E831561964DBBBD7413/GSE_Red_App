package com.gsered.gse.smartapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.gsered.gse.CouponsActivity;
import com.gsered.gse.ObservableWebView;
import com.gsered.gse.R;

import static android.content.Context.MODE_PRIVATE;

public class company_webview extends Fragment {
    View view;
    ObservableWebView webView;
    ImageView coupon;
    ProgressBar progressBar;
    String url;
    private static String APPCRED = "SIGNCRED";
    private static String NGO_ID = "NGOID";
    private static String USER_ID = "USERID";

    public company_webview() {
    }

    public static company_webview newInstance() {
        company_webview fragment = new company_webview();
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.swiggy, container, false);
        webView = (ObservableWebView) view.findViewById(R.id.webView1);
        coupon = (ImageView) view.findViewById(R.id.coupons);
        progressBar = (ProgressBar) view.findViewById(R.id.progressBar);
//        progressBar.setVisibility(View.GONE);

        if (getArguments() != null) {
            url = getArguments().getString("url");
        }
        if(getArguments().getString("admitadid") == null)
            coupon.setVisibility(View.GONE);

        coupon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), CouponsActivity.class);
                intent.putExtra("admitad_id", getArguments().getString("admitadid"));
                intent.putExtra("name", getArguments().getString("name"));
                intent.putExtra("raise", getArguments().getString("raise"));
                intent.putExtra("url", getArguments().getString("url"));
                intent.putExtra("imgurl", getArguments().getString("imgurl"));
                getActivity().startActivity(intent);
            }
        });

        SharedPreferences sharedPreferences = getActivity().getSharedPreferences(APPCRED, MODE_PRIVATE);
        String nid = sharedPreferences.getString(NGO_ID,"");
        String uid = sharedPreferences.getString(USER_ID,"");
        String subid = "app-"+nid+"-"+uid;
        url = url.replace("hers",subid);

        WebSettings webSetting = webView.getSettings();
//        webSetting.setBuiltInZoomControls(true);
        webSetting.setJavaScriptEnabled(true);
        webSetting.setLoadWithOverviewMode(true);
        webSetting.setUseWideViewPort(true);
        webSetting.setDomStorageEnabled(true);
        webSetting.setAllowFileAccess(true);
        webSetting.setAppCacheEnabled(true);
        webView.setWebViewClient(new company_webview.WebViewClient());
        webView.loadUrl(url);

        return view;
    }

    public Boolean cangoback() {
        return this.webView != null && this.webView.canGoBack();
    }

    public void goBack() {
        if(this.webView != null) {
            this.webView.goBack();
        }
    }

    public class WebViewClient extends android.webkit.WebViewClient {
        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            progressBar.setVisibility(View.GONE);
        }
    }
}
