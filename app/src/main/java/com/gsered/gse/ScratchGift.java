package com.gsered.gse;

import android.animation.ArgbEvaluator;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.gsered.gse.games.GamesMainActivity;
import com.gsered.gse.modals.CompaniesList;
import com.gsered.gse.modals.ReferCodeList;
import com.gsered.gse.modals.refercode;
import com.gsered.gse.rewards.RewardActivity;
import com.gsered.gse.rewards.RewardClickListener;
import com.gsered.gse.share.share_modal;
import com.gsered.gse.smartapp.ElectronicsActivity;
import com.gsered.gse.smartapp.FilmsActivity;
import com.gsered.gse.smartapp.FoodActivity;
import com.gsered.gse.smartapp.GamesActivity;
import com.gsered.gse.smartapp.ShoppingActivity;
import com.gsered.gse.smartapp.SocialActivity;
import com.gsered.gse.smartapp.TravelActivity;

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

public class ScratchGift extends AppCompatActivity {

    ViewPager viewPager;
    causeAdapter adapter;
    TextView title,description,companyname,buttontext;
    Button rewards;
    private List<causemodal> causemodals;
    private List<refercode> giftlist;
    private String GIFTSLIST = "giftlist", GIFTS = "gifts";

    Integer[] colors = null;
    ArgbEvaluator argbEvaluator = new ArgbEvaluator();

    private int mCurrentPosition;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scratch_gift);

        getSupportActionBar().setTitle("Offers");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setElevation(0);

        title = (TextView) findViewById(R.id.title);
        description = (TextView) findViewById(R.id.description);
        companyname = (TextView) findViewById(R.id.companyname);
        buttontext = (TextView) findViewById(R.id.seemoredetail);
        rewards = (Button) findViewById(R.id.rewardbutton);

        rewards.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(ScratchGift.this, RewardActivity.class);
                startActivity(i);
            }
        });

        Intent i = getIntent();
        String index = i.getStringExtra("index");
        if(index == null)
            index="1";
        mCurrentPosition = Integer.valueOf(index);

        causemodals = new ArrayList<>();

        causemodals.add(new causemodal("9", "https://www.gsered.com/intern/grp10/GSE_App/images/offers/8.png","NGOpedia in collaboration with wikipedia has started a new channel for NGO’s to reach charities to millions around the world.","Make your own NGO page and contribute for the greater good. Contribute INR 51 for eye operation for FREE.","", "Edit"));
        causemodals.add(new causemodal("1", "https://www.gsered.com/intern/grp10/GSE_App/images/offers/1.png","Did you ever imagine that you will get paid to play a game?","Simply, play a game continuously for 10 minutes & Earn a Flip Card worth INR 1 to INR 19.","", "Play"));
        causemodals.add(new causemodal("2", "https://www.gsered.com/intern/grp10/GSE_App/images/offers/2.png","Book a meal from Swiggy, Fassoss, Pizza Hut or more.","No minimum order & Earn a Flip Card upto INR 99.","","Order"));
        causemodals.add(new causemodal("3", "https://www.gsered.com/intern/grp10/GSE_App/images/offers/3.png","Recharge","A successful payment of your electricity, mobile, DTH or other bills will win you a free Flip Card of INR __","", "Pay"));
        causemodals.add(new causemodal("4", "https://www.gsered.com/intern/grp10/GSE_App/images/offers/4.png","We celebrate the homecoming of your new electronics with a free Flip Card worth INR 9 to INR 299.","Order now from flipkart, amazon, paytmmall or any of your favourite store in GSE Red.","", "Buy"));
        causemodals.add(new causemodal("5", "https://www.gsered.com/intern/grp10/GSE_App/images/offers/5.png","Share your experience on social media","Post items brought from GSE Red on social media or write a blog on your experience with GSE Red or Make a VLog on GSE Red and tag @gsered. Earn a free flipcard worth of INR 9 to 199.","","Share"));
        causemodals.add(new causemodal("6", "https://www.gsered.com/intern/grp10/GSE_App/images/offers/6.png","Get paid to shop this season from your favourite fashion websites: Flipkart, Myntra, Jabong among others.","For every transaction of INR 500 or above.  Win a Flip Card worth INR 9 to INR 199.","", "Shop"));
        causemodals.add(new causemodal("7", "https://www.gsered.com/intern/grp10/GSE_App/images/offers/7.png","Book a flight or hotel.","Earn a Flip Card worth of INR 9 to INR 199 for every booking on yatra, makemytrip, goibibo or more in GSE Red.","", "Book"));
        causemodals.add(new causemodal("9", "https://www.gsered.com/intern/grp10/GSE_App/images/offers/8.png","NGOpedia in collaboration with wikipedia has started a new channel for NGO’s to reach charities to millions around the world.","Make your own NGO page and contribute for the greater good. Contribute INR 51 for eye operation for FREE.","", "Edit"));
        causemodals.add(new causemodal("1", "https://www.gsered.com/intern/grp10/GSE_App/images/offers/1.png","Did you ever imagine that you will get paid to play a game?","Simply, play a game continuously for 10 minutes & Earn a Flip Card worth INR 1 to INR 19.","", "Play"));
//        causemodals.add(new causemodal("8", "https://www.gsered.com/intern/grp10/GSE_App/images/offers/9.png","Book a movie ticket","Win a free movie ticket if you make more than 5 transactions in a week or Buy a movie ticket from Bookmyshow and get a free Flip Card worth of upto INR 99.","","Book"));

        adapter = new causeAdapter(causemodals, this);
        viewPager = findViewById(R.id.viewpager);

        viewPager.setAdapter(adapter);
        viewPager.setCurrentItem(Integer.valueOf(index));
        viewPager.setPageMargin(10);
        viewPager.setPadding(140,0,140,0);

        title.setText(causemodals.get(Integer.valueOf(index)).getTitle());
        description.setText(causemodals.get(Integer.valueOf(index)).getDescription());
        companyname.setText(causemodals.get(Integer.valueOf(index)).getCompanyname());
        buttontext.setText(causemodals.get(Integer.valueOf(index)).getButtontext());

        buttontext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position = viewPager.getCurrentItem();
                if(position==1){
                    Intent i = new Intent(ScratchGift.this, GamesMainActivity.class);
                    startActivity(i);
                } else if(position==2){
                    Intent i = new Intent(ScratchGift.this, FoodActivity.class);
                    startActivity(i);
                } else if(position==3){
                    Intent intent = new Intent(ScratchGift.this, VisitShop.class);
                    intent.putExtra("source","activate");
                    intent.putExtra("url","https://www.amazon.in/b?ie=UTF8&node=15246428031&tag=gsered-21&ascsubtag=hers");
                    intent.putExtra("imgurl","https://www.gsered.com/charity/logo/Amazon.png");
                    startActivity(intent);
                } else if(position==4){
                    Intent i = new Intent(ScratchGift.this, ElectronicsActivity.class);
                    startActivity(i);
                } else if(position==5){
                    Intent i = new Intent(ScratchGift.this, SocialActivity.class);
                    startActivity(i);
                } else if(position==6){
                    Intent i = new Intent(ScratchGift.this, ShoppingActivity.class);
                    startActivity(i);
                } else if(position==7){
                    Intent i = new Intent(ScratchGift.this, TravelActivity.class);
                    startActivity(i);
                } else if(position==8){
                    Intent intent = new Intent(ScratchGift.this, VisitShop.class);
                    intent.putExtra("source","activate");
                    intent.putExtra("url","https://www.gsered.com/contact.php");
                    intent.putExtra("imgurl","");
                    startActivity(intent);
                } else if(position==9){
                    Intent i = new Intent(ScratchGift.this, FilmsActivity.class);
                    startActivity(i);
                }
            }
        });

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(final int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(final int position) {
                mCurrentPosition = position;

                title.setText(causemodals.get(position).getTitle());
                description.setText(causemodals.get(position).getDescription());
                companyname.setText(causemodals.get(position).getCompanyname());
                buttontext.setText(causemodals.get(position).getButtontext());

                buttontext.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if(position==1){
                            Intent i = new Intent(ScratchGift.this, GamesMainActivity.class);
                            startActivity(i);
                        } else if(position==2){
                            Intent i = new Intent(ScratchGift.this, FoodActivity.class);
                            startActivity(i);
                        } else if(position==3){
                            Intent intent = new Intent(ScratchGift.this, VisitShop.class);
                            intent.putExtra("source","activate");
                            intent.putExtra("url","https://www.amazon.in/b?ie=UTF8&node=15246428031&tag=gsered-21&ascsubtag=hers");
                            intent.putExtra("imgurl","https://www.gsered.com/charity/logo/Amazon.png");
                            startActivity(intent);
                        } else if(position==4){
                            Intent i = new Intent(ScratchGift.this, ElectronicsActivity.class);
                            startActivity(i);
                        } else if(position==5){
                            Intent i = new Intent(ScratchGift.this, SocialActivity.class);
                            startActivity(i);
                        } else if(position==6){
                            Intent i = new Intent(ScratchGift.this, ShoppingActivity.class);
                            startActivity(i);
                        } else if(position==7){
                            Intent i = new Intent(ScratchGift.this, TravelActivity.class);
                            startActivity(i);
                        } else if(position==8){
                            Intent intent = new Intent(ScratchGift.this, VisitShop.class);
                            intent.putExtra("source","activate");
                            intent.putExtra("url","https://www.gsered.com/contact.php");
                            intent.putExtra("imgurl","");
                            startActivity(intent);
                        } else if(position==9){
                            Intent i = new Intent(ScratchGift.this, FilmsActivity.class);
                            startActivity(i);
                        }
                    }
                });

            }

            @Override
            public void onPageScrollStateChanged(int state) {
                if (mCurrentPosition == 0)
                    viewPager.setCurrentItem((causemodals.size()-1) -1, false);

                if (mCurrentPosition == (causemodals.size()-1))
                    viewPager.setCurrentItem(1, false);
            }
        });
    }
}
