package com.gsered.gse.dashboard;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ProgressBar;

import com.gsered.gse.MainActivity;
import com.gsered.gse.OpenUrl;
import com.gsered.gse.R;
import com.gsered.gse.newslist.NewsListAdapter;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;

public class TransactionActivity extends AppCompatActivity {
    private ProgressBar progressBar;
    private FrameLayout notransaction;
    private Button contactus;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Transactions");

        contactus = (Button) findViewById(R.id.contactbutton);
        notransaction = (FrameLayout) findViewById(R.id.textView_notransaction);
        progressBar = (ProgressBar) findViewById(R.id.progress);
        progressBar.setVisibility(View.VISIBLE);

        contactus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(TransactionActivity.this, OpenUrl.class);
                i.putExtra("url", "https://www.gsered.com/contact.php");
                startActivity(i);
            }
        });

        SharedPreferences sharedPreferences = getSharedPreferences("SIGNCRED", MODE_PRIVATE);
        String uid = sharedPreferences.getString("USERID","");

        final List<TransactionModal> transactionlist = new ArrayList<>();

        OkHttpClient client = new OkHttpClient();
        String url = "https://www.gsered.com/intern/grp10/GSE_App/dashboard/alltransaction.php?uid="+uid;
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

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                JSONObject socialpost = null;
                                socialpost = new JSONObject(posts);
                                JSONObject p = socialpost.getJSONObject("transaction");
                                progressBar.setVisibility(View.GONE);
                                if(p.getString("status").equalsIgnoreCase("yes")) {
                                    for(int i=0; i<p.length(); i++){
                                        String id = Integer.toString(i+1);
                                        JSONObject r = p.getJSONObject(id);
                                        transactionlist.add(new TransactionModal(r.getString("name"),"Rs."+r.getString("donationamount"),r.getString("date"),r.getString("type"),r.getString("status")));
                                    }
                                } else {
                                    notransaction.setVisibility(View.VISIBLE);
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    });
                }
            }
        });

//        transactionlist.add(new TransactionModal("S Agrawal","Rs.230","20 April 2019",""));
//        transactionlist.add(new TransactionModal("G Agrawal","Rs.2350","20 April 2019",""));
//        transactionlist.add(new TransactionModal("R Agrawal","Rs.276240","20 April 2019",""));

        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        RecyclerView recyclerView = findViewById(R.id.transactionlist);
        recyclerView.setLayoutManager(layoutManager);
        TransactionAdapter adapter = new TransactionAdapter(this, transactionlist);
        recyclerView.setAdapter(adapter);
    }
}
