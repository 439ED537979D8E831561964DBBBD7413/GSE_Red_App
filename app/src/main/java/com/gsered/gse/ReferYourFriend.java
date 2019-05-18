package com.gsered.gse;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class ReferYourFriend extends AppCompatActivity {
    private ViewPager mSlideViewPager;
    private LinearLayout mDotLayout;
    private TextView[] mDots;
    private TextView refercode;
    private refer_adapter referadapter;
    private RelativeLayout refercodeimage;
    private Button getstarted;
    private int nCurrentPage;
    private String refercode1,refercode2,refercode3;
    private static String APPCRED = "SIGNCRED";
    private static String USER_ID = "USERID";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_refer_your_friend);
        getstarted = (Button) findViewById(R.id.getstarted);
        mSlideViewPager = (ViewPager) findViewById(R.id.slideViewPager);
        mDotLayout = (LinearLayout) findViewById(R.id.dotsLayout);
        refercodeimage = (RelativeLayout) findViewById(R.id.referbackground);
        refercode = (TextView) findViewById(R.id.refercode);
        referadapter = new refer_adapter(this);
        mSlideViewPager.setAdapter(referadapter);
        addDotsIndicator(0);

        refercodeimage.setBackgroundResource(R.drawable.refer1);

        SharedPreferences sharedPreferences = getSharedPreferences(APPCRED, MODE_PRIVATE);
        String id =sharedPreferences.getString(USER_ID, "");
        refercode1 = id+"MAPRO4EDU";
        refercode2 = id+"582MAPRO4EAT";
        refercode3 = id+"582MAPRO4FOOD";

        mSlideViewPager.addOnPageChangeListener(viewListener);
        getstarted.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                share();
            }
        });
    }

    private void share(){
        Intent share = new Intent(Intent.ACTION_SEND);
        share.setType("text/plain");
        share.putExtra(Intent.EXTRA_SUBJECT, "just sharing to download");
        share.putExtra(Intent.EXTRA_TEXT,"share it to your friends");
        startActivity(Intent.createChooser(share, getResources().getString(R.string.app_name)));
    }

    public void addDotsIndicator(int position){
        mDots = new TextView[3];
        mDotLayout.removeAllViews();
        for(int i=0; i<mDots.length; i++){
            mDots[i] = new TextView(this);
            mDots[i].setText(Html.fromHtml("&#8226;"));
            mDots[i].setTextSize(40);
            mDots[i].setPadding(5,0,5,0);
            mDots[i].setTextColor(getResources().getColor(R.color.colorWhite));

            mDotLayout.addView(mDots[i]);

        }

        if(mDots.length > 0){
            mDots[position].setTextColor(getResources().getColor(R.color.search_bar_bkg_gray));
        }
    }

    ViewPager.OnPageChangeListener viewListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            addDotsIndicator(position);
            nCurrentPage = position;

            if(position == 0){
                refercodeimage.setBackgroundResource(R.drawable.refer1);
                refercode.setText(refercode1);
            } else if(position == mDots.length-1 ){
                refercodeimage.setBackgroundResource(R.drawable.refer2);
                refercode.setText(refercode2);
            } else {
                refercodeimage.setBackgroundResource(R.drawable.refer3);
                refercode.setText(refercode3);
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };

}
