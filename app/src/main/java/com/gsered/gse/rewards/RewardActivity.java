package com.gsered.gse.rewards;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.GridLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.gsered.gse.CouponsActivity;
import com.gsered.gse.InternetStatusActivity;
import com.gsered.gse.MainActivity;
import com.gsered.gse.R;
import com.gsered.gse.ScratchGift;
import com.gsered.gse.coupon_modal;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class RewardActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private TextView rewardamount,availbal;
    private List<reward_modal> rewardlist;
    private Button redeem,offer;
    private static String APPCRED = "SIGNCRED";
    private static String USER_ID = "USERID";
    String uid,oldamount,paidreward;
    private FrameLayout progressbar,noresult;
    SharedPreferences rewardPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reward);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setElevation(0);

        progressbar = (FrameLayout) findViewById(R.id.progressbar);
        noresult = (FrameLayout) findViewById(R.id.noresult);
        recyclerView = (RecyclerView) findViewById(R.id.allrewards);
        rewardamount = (TextView) findViewById(R.id.totalrewardamount);
        availbal = (TextView) findViewById(R.id.availbal);
        redeem = (Button) findViewById(R.id.redeembutton);
        offer = (Button) findViewById(R.id.offerbutton);

        SharedPreferences sharedPreferences = getSharedPreferences(APPCRED, MODE_PRIVATE);
        uid = sharedPreferences.getString(USER_ID,"");

        rewardPreferences = getSharedPreferences("REWARDS", MODE_PRIVATE);
        oldamount = rewardPreferences.getString("totalreward", "");
        paidreward = rewardPreferences.getString("paidreward","");
        int balance = (Integer.valueOf(oldamount) - Integer.valueOf(paidreward));
        rewardamount.setText(oldamount);
        availbal.setText("Aval. Balance : " + String.valueOf(balance));

        if( balance > 200)
            redeem.setEnabled(true);

        redeem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(redeem.isEnabled()) {
                    Intent i = new Intent(RewardActivity.this, BankAccountDetailsActivity.class);
                    startActivity(i);
                }
            }
        });

        offer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(RewardActivity.this, ScratchGift.class);
                startActivity(i);
            }
        });

        gettotalreward();

        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(this, 3);
        recyclerView.setLayoutManager(mLayoutManager);
//        recyclerView.addItemDecoration(new GridSpacingItemDecoration(2, dpToPx(10), true));
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        RewardClickListener rewardClickListener = new RewardClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                String s = rewardlist.get(position).getRewardamount();
                String[] result = s.split("Rs.");
                int old = Integer.valueOf(oldamount);
                int newamt = Integer.valueOf(result[1]);
                int sum = old + newamt;
                rewardamount.setText(String.valueOf(sum));
                oldamount = String.valueOf(sum);

                SharedPreferences.Editor editor = rewardPreferences.edit();
                editor.putString("totalreward", String.valueOf(sum));
                editor.commit();

                availbal.setText("Aval. Balance : " + String.valueOf(sum - Integer.valueOf(paidreward)));
                if( (sum - Integer.valueOf(paidreward)) > 200)
                    redeem.setEnabled(true);
            }
        };
        RewardAdapter rewardAdapter = new RewardAdapter(this, rewardlist, rewardClickListener);
        recyclerView.setAdapter(rewardAdapter);
    }

    private void gettotalreward() {
        rewardlist = new ArrayList<>();

        OkHttpClient client = new OkHttpClient();
        String url = "https://www.gsered.com/intern/grp10/GSE_App/earned/fetch_touchearn.php?uid="+uid;
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
                    if(RewardActivity.this == null)
                        return;

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            JSONObject socialpost = null;
                            try {
                                progressbar.setVisibility(View.GONE);
                                socialpost = new JSONObject(posts);
                                JSONObject p = socialpost.getJSONObject("earned");
                                if(p.getString("status").equalsIgnoreCase("yes")) {
                                    for(int i=0; i<p.length(); i++){
                                        String id = Integer.toString(i+1);
                                        JSONObject r = p.getJSONObject(id);
                                        rewardlist.add(new reward_modal("Rs."+r.getString("touchearned"),r.getString("seen"),r.getString("id")));
                                    }
                                } else {
                                    rewardlist.add(new reward_modal("","2",""));
//                                    noresult.setVisibility(View.VISIBLE);
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
}
