package com.gsered.gse;

import android.animation.ArgbEvaluator;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.Toast;

import com.gsered.gse.rewards.RewardClickListener;

import java.util.ArrayList;
import java.util.List;

public class CauseActivity extends AppCompatActivity {

    ViewPager viewPager;
    causeAdapter adapter;
    List<causemodal> causemodals;
    Integer[] colors = null;
    ArgbEvaluator argbEvaluator = new ArgbEvaluator();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cause);

        causemodals = new ArrayList<>();
        causemodals.add(new causemodal("1", "https://www.gsered.com/seasonal_posts/POST%201%20-%20ARMY%20DAY.jpg","Get your tickets on BookMyShow ","Earn a scratch card upto INR300 for spending INR300","MAPRO",""));
        causemodals.add(new causemodal("2", "https://gsered.com/ngo-social-media-post/0/3.png","Get your tickets on BookMyShow ","Earn a scratch card upto INR300 for spending INR300","MAPRO",""));
        causemodals.add(new causemodal("3", "https://gsered.com/ngo-social-media-post/0/5.png","Get your tickets on BookMyShow ","Earn a scratch card upto INR300 for spending INR300","MAPRO",""));

        adapter = new causeAdapter(causemodals, this);
        viewPager = findViewById(R.id.viewpager);
        viewPager.setAdapter(adapter);
        viewPager.setPadding(30,0,30,0);

        Integer[] colors_temp = {
                getResources().getColor(R.color.colorPrimaryDark),
                getResources().getColor(R.color.colorPrimary),
                getResources().getColor(R.color.colorAccent),
                getResources().getColor(R.color.colorAccentLight),
                getResources().getColor(R.color.colorPrimaryDark),
        };

        colors = colors_temp;

        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
//                if(position < (adapter.getCount() - 1) && position < (colors.length - 1) ){
//                    viewPager.setBackgroundColor(
//                            (Integer) argbEvaluator.evaluate(
//                                    positionOffset,
//                                    colors[position],
//                                    colors[position + 1]
//                            )
//                    );
//
//                }else{
//                    viewPager.setBackgroundColor(colors[colors.length - 1]);
//                }
//                viewPager.setBackgroundColor(R.color.colorWhite);
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }
}
