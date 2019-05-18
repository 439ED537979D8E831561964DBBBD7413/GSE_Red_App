package com.gsered.gse.smartapp;

import android.content.Intent;
import android.os.Handler;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.gsered.gse.MainActivity;
import com.gsered.gse.R;
import com.gsered.gse.ViewPagerAdapter;

public class SocialActivity extends AppCompatActivity {

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private ImageView imageView1,imageView2,imageView3,imageView4;
    int count=0;
    company_webview fb,insta,twitter,linkedin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_social);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        viewPager = (ViewPager) findViewById(R.id.viewpager_id);

        viewPager.setOffscreenPageLimit(4);
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());

        View view1 = getLayoutInflater().inflate(R.layout.custom_tabicon, null);
        imageView1 = (ImageView) view1.findViewById(R.id.icon);
        Glide.with(this).load("https://www.gsered.com/charity/images/Facebook.png").placeholder(R.mipmap.ic_launcher).into(imageView1);

        View view2 = getLayoutInflater().inflate(R.layout.custom_tabicon, null);
        imageView2 = (ImageView) view2.findViewById(R.id.icon);
        Glide.with(this).load("https://www.gsered.com/charity/images/instagram.png").placeholder(R.mipmap.ic_launcher).into(imageView2);

        View view3 = getLayoutInflater().inflate(R.layout.custom_tabicon, null);
        imageView3 = (ImageView) view3.findViewById(R.id.icon);
        Glide.with(this).load("https://www.gsered.com/charity/images/Twitter.png").placeholder(R.mipmap.ic_launcher).into(imageView3);

        View view4 = getLayoutInflater().inflate(R.layout.custom_tabicon, null);
        imageView4 = (ImageView) view4.findViewById(R.id.icon);
        Glide.with(this).load("https://www.gsered.com/charity/images/Linkedin.png").placeholder(R.mipmap.ic_launcher).into(imageView4);

        Bundle bundle1 = new Bundle();
        bundle1.putString("url", "https://www.facebook.com/");
        fb = company_webview.newInstance();
        this.fb.setArguments(bundle1);

        Bundle bundle2 = new Bundle();
        bundle2.putString("url", "https://www.instagram.com/");
        insta = company_webview.newInstance();
        this.insta.setArguments(bundle2);

        Bundle bundle3 = new Bundle();
        bundle3.putString("url", "https://twitter.com/");
        twitter = company_webview.newInstance();
        this.twitter.setArguments(bundle3);

        Bundle bundle4 = new Bundle();
        bundle4.putString("url", "https://www.linkedin.com/");
        linkedin = company_webview.newInstance();
        this.linkedin.setArguments(bundle4);

        // Add fragment
        adapter.AddFragment(this.fb,"");
        adapter.AddFragment(this.insta, "");
        adapter.AddFragment(this.twitter,"");
        adapter.AddFragment(this.linkedin, "");

        // adapter setup
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);

        tabLayout.getTabAt(0).setCustomView(view1);
        tabLayout.getTabAt(1).setCustomView(view2);
        tabLayout.getTabAt(2).setCustomView(view3);
        tabLayout.getTabAt(3).setCustomView(view4);

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        //starts press back button twice to return
        count++;
        if(count==1)
            Toast.makeText(this, "Please click back button again to exit", Toast.LENGTH_SHORT).show();
        else if(count==2){
            Intent i = new Intent(SocialActivity.this, MainActivity.class);
            startActivity(i);
        }

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                count=0;
            }
        }, 3000);
        //ends press back button twice to return

        if ((keyCode == KeyEvent.KEYCODE_BACK) && this.fb != null && this.fb.cangoback()) {
            this.fb.goBack();
            return true;
        }
        else if ((keyCode == KeyEvent.KEYCODE_BACK) && this.insta != null && this.insta.cangoback()) {
            this.insta.goBack();
            return true;
        }
        else if ((keyCode == KeyEvent.KEYCODE_BACK) && this.twitter != null && this.twitter.cangoback()) {
            this.twitter.goBack();
            return true;
        }
        else if ((keyCode == KeyEvent.KEYCODE_BACK) && this.linkedin != null && this.linkedin.cangoback()) {
            this.linkedin.goBack();
            return true;
        }

        return super.onKeyDown(keyCode, event);
    }
}
