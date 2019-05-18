package com.gsered.gse.topdonors;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.gsered.gse.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;

public class TopDonorsActivity extends AppCompatActivity {
    private ProgressBar progressBar;
    private TextView rank,name,amount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_top_donors);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Top Donors");

        progressBar = (ProgressBar) findViewById(R.id.progress);
        rank = (TextView) findViewById(R.id.srno);
        name = (TextView) findViewById(R.id.name);
        amount = (TextView) findViewById(R.id.amount);
        progressBar.setVisibility(View.VISIBLE);

        SharedPreferences sharedPreferences = getSharedPreferences("SIGNCRED", MODE_PRIVATE);
        String uid = sharedPreferences.getString("USERID","");

        final List<TopDonorModal> topdonorlist = new ArrayList<>();

        OkHttpClient client = new OkHttpClient();
        String url = "https://www.gsered.com/intern/grp10/GSE_App/dashboard/topdonor.php?uid="+uid;
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
                                JSONObject p = socialpost.getJSONObject("donor");
                                for(int i=0; i<p.length()-1; i++){
                                    String id = Integer.toString(i+1);
                                    JSONObject r = p.getJSONObject(id);
                                    topdonorlist.add(new TopDonorModal(r.getString("rank"),r.getString("name"),r.getString("amount")));
                                }

                                rank.setText(p.getJSONObject("currentuser").getString("rank"));
                                name.setText(p.getJSONObject("currentuser").getString("name"));
                                amount.setText(p.getJSONObject("currentuser").getString("amount"));
                                progressBar.setVisibility(View.GONE);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    });
                }
            }
        });

        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        RecyclerView recyclerView = findViewById(R.id.topdonorlist);
        recyclerView.setLayoutManager(layoutManager);
        TopDonorAdapter adapter = new TopDonorAdapter(this, topdonorlist);
        recyclerView.setAdapter(adapter);

    }
}
