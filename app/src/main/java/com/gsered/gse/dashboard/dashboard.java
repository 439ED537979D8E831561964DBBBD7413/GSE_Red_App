package com.gsered.gse.dashboard;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.gsered.gse.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class dashboard extends AppCompatActivity {

    TextView alltranaction,username,totalearning,totaldonation,totalliveimpact,cash,shopping,referral,totalshoppingcount,shoppingliveimpacted,usersupporting;
    ProgressBar progressBar;
    RelativeLayout displaydashboard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Dashboard");

        progressBar = (ProgressBar) findViewById(R.id.progress);
        displaydashboard = (RelativeLayout) findViewById(R.id.showdashboard);
        username = (TextView) findViewById(R.id.user_name);
        usersupporting = (TextView) findViewById(R.id.user_supporting);
        totalearning = (TextView) findViewById(R.id.earning);
        totaldonation = (TextView) findViewById(R.id.donation);
        totalliveimpact = (TextView) findViewById(R.id.livesimpacted);
        referral = (TextView) findViewById(R.id.referralcontribution);
        cash = (TextView) findViewById(R.id.cashcontribution);
        shopping = (TextView) findViewById(R.id.shoppingcontribution);
        totalshoppingcount = (TextView) findViewById(R.id.totalshopping);
        shoppingliveimpacted = (TextView) findViewById(R.id.shoppinglivesimpacted);
        alltranaction = (TextView) findViewById(R.id.transaction);

        progressBar.setVisibility(View.VISIBLE);
        displaydashboard.setVisibility(View.GONE);

        SharedPreferences sharedPreferences = getSharedPreferences("SIGNCRED", MODE_PRIVATE);


        username.setText( capitalize(sharedPreferences.getString("NAME","")) );
        usersupporting.setText("Supporting "+ capitalize(sharedPreferences.getString("usersngo","")) );
        String uid = sharedPreferences.getString("USERID","");

        SharedPreferences rewardPreferences = getSharedPreferences("REWARDS", MODE_PRIVATE);
        totalearning.setText(rewardPreferences.getString("totalreward", ""));

        OkHttpClient client = new OkHttpClient();
        String url = "https://www.gsered.com/intern/grp10/GSE_App/dashboard/dashboarddata.php?uid="+uid;
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
                    if(dashboard.this == null)
                        return;

                    runOnUiThread(new Runnable() {
                        public void run() {
                            JSONObject socialpost = null;
                            try {
                                socialpost = new JSONObject(posts);
                                JSONObject p = socialpost.getJSONObject("money");
                                totaldonation.setText(p.getString("totaldonate"));
                                totalliveimpact.setText(p.getString("totaldonation_livesimpact"));
                                cash.setText(p.getString("directdonate"));
                                shopping.setText(p.getString("shopdonate"));
                                referral.setText(p.getString("referdonate"));
                                totalshoppingcount.setText(p.getString("shoppingcount"));
                                shoppingliveimpacted.setText(p.getString("shoppingreferralliveimpacted"));
                                progressBar.setVisibility(View.GONE);
                                displaydashboard.setVisibility(View.VISIBLE);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    });
                }
            }
        });


        alltranaction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(dashboard.this, TransactionActivity.class);
                startActivity(i);
            }
        });
    }

    public static String capitalize(String input) {

        String[] words = input.toLowerCase().split(" ");
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < words.length; i++) {
            String word = words[i];

            if (i > 0 && word.length() > 0) {
                builder.append(" ");
            }

            String cap = word.substring(0, 1).toUpperCase() + word.substring(1);
            builder.append(cap);
        }
        return builder.toString();
    }
}
