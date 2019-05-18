package com.gsered.gse.smartapp;

import android.content.Intent;
import android.os.Handler;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.gsered.gse.MainActivity;
import com.gsered.gse.R;
import com.gsered.gse.ViewPagerAdapter;

public class CricketActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private EditText search;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private ImageView imageView1,imageView2,imageView3,imageView4;
    company_webview swiggy,ubereats,faasos,pizzahut;
    int count=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food);

//        toolbar = (Toolbar) findViewById(R.id.toolbar);
//        search = (EditText) findViewById(R.id.search);
        tabLayout = (TabLayout) findViewById(R.id.tabs);
        viewPager = (ViewPager) findViewById(R.id.viewpager_id);

//        search.setVisibility(View.GONE);
//        setSupportActionBar(toolbar);

        viewPager.setOffscreenPageLimit(3);
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());

        View view1 = getLayoutInflater().inflate(R.layout.custom_tabicon, null);
        imageView1 = (ImageView) view1.findViewById(R.id.icon);
        Glide.with(this).load("https://www.gsered.com/intern/grp10/GSE_App/images/cricket/CricBuzz").placeholder(R.mipmap.ic_launcher).into(imageView1);

        View view2 = getLayoutInflater().inflate(R.layout.custom_tabicon, null);
        imageView2 = (ImageView) view2.findViewById(R.id.icon);
        Glide.with(this).load("https://www.gsered.com/intern/grp10/GSE_App/images/cricket/Dream11.jpg").placeholder(R.mipmap.ic_launcher).into(imageView2);

        View view3 = getLayoutInflater().inflate(R.layout.custom_tabicon, null);
        imageView3 = (ImageView) view3.findViewById(R.id.icon);
        Glide.with(this).load("https://www.gsered.com/intern/grp10/GSE_App/images/cricket/EspnCric.jpg").placeholder(R.mipmap.ic_launcher).into(imageView3);

        Bundle bundle1 = new Bundle();
        bundle1.putString("url", "https://www.cricbuzz.com/");
        swiggy = company_webview.newInstance();
        swiggy.setArguments(bundle1);

        Bundle bundle2 = new Bundle();
        bundle2.putString("url", "https://www.dream11.com/leagues");
        ubereats = company_webview.newInstance();
        ubereats.setArguments(bundle2);

        Bundle bundle3 = new Bundle();
        bundle3.putString("url", "http://www.espncricinfo.com/");
        faasos = company_webview.newInstance();
        faasos.setArguments(bundle3);

        // Add fragment
        adapter.AddFragment(swiggy,"");
        adapter.AddFragment(ubereats, "");
        adapter.AddFragment(faasos, "");

        // adapter setup
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);

        tabLayout.getTabAt(0).setCustomView(view1);
        tabLayout.getTabAt(1).setCustomView(view2);
        tabLayout.getTabAt(2).setCustomView(view3);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        //starts press back button twice to return
        count++;
        if(count==1)
            Toast.makeText(this, "Please click back button again to exit", Toast.LENGTH_SHORT).show();
        else if(count==2){
            Intent i = new Intent(CricketActivity.this, MainActivity.class);
            startActivity(i);
        }

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                count=0;
            }
        }, 3000);
        //ends press back button twice to return

        if ((keyCode == KeyEvent.KEYCODE_BACK) && this.swiggy != null && this.swiggy.cangoback()) {
            this.swiggy.goBack();
            return true;
        }
        else if ((keyCode == KeyEvent.KEYCODE_BACK) && this.ubereats != null && this.ubereats.cangoback()) {
            this.ubereats.goBack();
            return true;
        }
        else if ((keyCode == KeyEvent.KEYCODE_BACK) && this.faasos != null && this.faasos.cangoback()) {
            this.faasos.goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
