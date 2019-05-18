package com.gsered.gse;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class WelcomeActivity extends AppCompatActivity {

    private ViewPager mSlideViewPager;
    private LinearLayout mDotLayout;

    private TextView[] mDots;

    private SliderAdapter sliderAdapter;
    private Button getstarted;

    private int nCurrentPage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(!isFirstTimeStartApp()){
            startMainActivity();
            finish();
        }

        setContentView(R.layout.activity_welcome);

        mSlideViewPager = (ViewPager) findViewById(R.id.slideViewPager);
        mDotLayout = (LinearLayout) findViewById(R.id.dotsLayout);

        getstarted = (Button) findViewById(R.id.getstarted);
//        nBackBtn = (Button) findViewById(R.id.register);

        sliderAdapter = new SliderAdapter(this);

        mSlideViewPager.setAdapter(sliderAdapter);
        addDotsIndicator(0);

        mSlideViewPager.addOnPageChangeListener(viewListener);

        getstarted.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startMainActivity();
            }
        });

//        nBackBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                mSlideViewPager.setCurrentItem(nCurrentPage - 1);
//            }
//        });
    }

    public void addDotsIndicator(int position){
        mDots = new TextView[4];
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

    private void startMainActivity(){
        setFirstTimeStartStatus(false);
        SharedPreferences sharedPreferences = getSharedPreferences("SIGNCRED", MODE_PRIVATE);
        Boolean checklogin = sharedPreferences.getBoolean("ISLOGIN", false);
        if(checklogin == true) {
            Intent intent = new Intent(WelcomeActivity.this, MainActivity.class);
            startActivity(intent);
        } else {
            Intent intent = new Intent(WelcomeActivity.this, SigninActivity.class);
            startActivity(intent);
        }
    }
    private boolean isFirstTimeStartApp(){
        SharedPreferences ref = getApplicationContext().getSharedPreferences("IntroSliderApp", Context.MODE_PRIVATE);
        return ref.getBoolean("FirstTimeStartFlag" , true);
    }

    private void setFirstTimeStartStatus(boolean set){
        SharedPreferences ref = getApplicationContext().getSharedPreferences("IntroSliderApp", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = ref.edit();
        editor.putBoolean("FirstTimeStartFlag", set);
        editor.commit();
    }


    ViewPager.OnPageChangeListener viewListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {

            addDotsIndicator(position);
            nCurrentPage = position;

//            if(position == 0){
//                nNextBtn.setEnabled(true);
//                nBackBtn.setEnabled(false);
//                nBackBtn.setVisibility(View.INVISIBLE);
//
//                nNextBtn.setText("Next");
//                nBackBtn.setText("");
//            } else if(position == mDots.length-1 ){
//                nNextBtn.setEnabled(true);
//                nBackBtn.setEnabled(true);
//                nBackBtn.setVisibility(View.VISIBLE);
//
//                nNextBtn.setText("Finish");
//                nBackBtn.setText("Back");
//
//            } else {
//                nNextBtn.setEnabled(true);
//                nBackBtn.setEnabled(true);
//                nBackBtn.setVisibility(View.VISIBLE);
//
//                nNextBtn.setText("Next");
//                nBackBtn.setText("Back");
//
//            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };
}
