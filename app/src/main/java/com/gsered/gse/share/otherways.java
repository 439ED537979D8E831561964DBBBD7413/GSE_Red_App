package com.gsered.gse.share;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;

import com.gsered.gse.R;

import org.apache.http.util.EncodingUtils;

import static android.content.Context.MODE_PRIVATE;

public class otherways extends Fragment {
    View view;
    Button callme;

    public otherways() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.otherways, container, false);
        callme = (Button) view.findViewById(R.id.callme);

        callme.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse("tel:8329331748"));
                startActivity(callIntent);
            }
        });
        return view;
    }
}
