package com.gsered.gse.smartapp;

import android.content.Intent;
import android.os.Handler;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.gsered.gse.MainActivity;
import com.gsered.gse.R;
import com.gsered.gse.ViewPagerAdapter;

public class TravelActivity extends AppCompatActivity {

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private ImageView imageView1,imageView2,imageView3,imageView4;
    int count=0;

    company_webview mmt,agoda,akbartravels,goibibo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_travel);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        viewPager = (ViewPager) findViewById(R.id.viewpager_id);

        viewPager.setOffscreenPageLimit(4);
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());

        View view1 = getLayoutInflater().inflate(R.layout.custom_tabicon, null);
        imageView1 = (ImageView) view1.findViewById(R.id.icon);
        Glide.with(this).load("https://gsered.com/charity/logo/Makemytrip.png").placeholder(R.mipmap.ic_launcher).into(imageView1);

        View view2 = getLayoutInflater().inflate(R.layout.custom_tabicon, null);
        imageView2 = (ImageView) view2.findViewById(R.id.icon);
        Glide.with(this).load("https://gsered.com/charity/logo/Agoda.png").placeholder(R.mipmap.ic_launcher).into(imageView2);

        View view3 = getLayoutInflater().inflate(R.layout.custom_tabicon, null);
        imageView3 = (ImageView) view3.findViewById(R.id.icon);
        Glide.with(this).load("https://gsered.com/charity/logo/Akbartravels.png").placeholder(R.mipmap.ic_launcher).into(imageView3);

        View view4 = getLayoutInflater().inflate(R.layout.custom_tabicon, null);
        imageView4 = (ImageView) view4.findViewById(R.id.icon);
        Glide.with(this).load("https://gsered.com/charity/logo/Goibibo.png").placeholder(R.mipmap.ic_launcher).into(imageView4);

        Bundle bundle1 = new Bundle();
        bundle1.putString("url", "https://ad.admitad.com/g/uu693psu23d76c048b769814d2cd5d/?subid=hers&subid1=hers1");
        mmt = company_webview.newInstance();
        mmt.setArguments(bundle1);

        Bundle bundle2 = new Bundle();
        bundle2.putString("url", "https://ad.admitad.com/g/k5cxk4xsu7d76c048b76614935c8f5/?subid=hers&subid1=hers1");
        agoda = company_webview.newInstance();
        agoda.setArguments(bundle2);

        Bundle bundle3 = new Bundle();
        bundle3.putString("url", "https://ad.admitad.com/g/tl0vpdbpttd76c048b7631d09ebb63/?subid=hers&subid1=hers1");
        akbartravels = company_webview.newInstance();
        akbartravels.setArguments(bundle3);

        Bundle bundle4 = new Bundle();
        bundle4.putString("url", "https://ad.admitad.com/g/3y33fwma8xd76c048b763a2218e38f/?subid=hers&subid1=hers1");
        goibibo = company_webview.newInstance();
        goibibo.setArguments(bundle4);

        // Add fragment
        adapter.AddFragment(mmt,"");
        adapter.AddFragment(agoda, "");
        adapter.AddFragment(akbartravels,"");
        adapter.AddFragment(goibibo, "");

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
            Intent i = new Intent(TravelActivity.this, MainActivity.class);
            startActivity(i);
        }

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                count=0;
            }
        }, 3000);
        //ends press back button twice to return

        if ((keyCode == KeyEvent.KEYCODE_BACK) && this.mmt != null && this.mmt.cangoback()) {
            this.mmt.goBack();
            return true;
        }
        else if ((keyCode == KeyEvent.KEYCODE_BACK) && this.agoda != null && this.agoda.cangoback()) {
            this.agoda.goBack();
            return true;
        }
        else if ((keyCode == KeyEvent.KEYCODE_BACK) && this.akbartravels != null && this.akbartravels.cangoback()) {
            this.akbartravels.goBack();
            return true;
        }
        else if ((keyCode == KeyEvent.KEYCODE_BACK) && this.goibibo != null && this.goibibo.cangoback()) {
            this.goibibo.goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

}
