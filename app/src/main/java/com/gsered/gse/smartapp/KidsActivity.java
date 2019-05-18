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

public class KidsActivity extends AppCompatActivity {

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private ImageView imageView1,imageView2,imageView3,imageView4,imageView5;
    company_webview myntra,nnnow,flipkart,amazon,firstcry;
    int count=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        viewPager = (ViewPager) findViewById(R.id.viewpager_id);

        viewPager.setOffscreenPageLimit(6);
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());

        View view1 = getLayoutInflater().inflate(R.layout.custom_tabicon, null);
        imageView1 = (ImageView) view1.findViewById(R.id.icon);
        Glide.with(this).load("https://gsered.com/charity/logo/Firstcry.png").placeholder(R.mipmap.ic_launcher).into(imageView1);

        View view2 = getLayoutInflater().inflate(R.layout.custom_tabicon, null);
        imageView2 = (ImageView) view2.findViewById(R.id.icon);
        Glide.with(this).load("https://gsered.com/charity/logo/Kidzee.png").placeholder(R.mipmap.ic_launcher).into(imageView2);

        View view3 = getLayoutInflater().inflate(R.layout.custom_tabicon, null);
        imageView3 = (ImageView) view3.findViewById(R.id.icon);
        Glide.with(this).load("https://gsered.com/charity/logo/Amazon.png").placeholder(R.mipmap.ic_launcher).into(imageView3);

        View view4 = getLayoutInflater().inflate(R.layout.custom_tabicon, null);
        imageView4 = (ImageView) view4.findViewById(R.id.icon);
        Glide.with(this).load("https://www.gsered.com/charity/logo/Flipkart.png").placeholder(R.mipmap.ic_launcher).into(imageView4);

        Bundle bundle1 = new Bundle();
        bundle1.putString("url", "https://ad.admitad.com/g/s04yc4g9ryd76c048b7662ee748b0a/?subid=hers&subid1=hers1");
        bundle1.putString("admitadid", "14719");
        bundle1.putString("raise", "2%");
        bundle1.putString("name", "Firstcry");
        bundle1.putString("imgurl", "https://gsered.com/charity/logo/Firstcry.png");
        firstcry = company_webview.newInstance();
        firstcry.setArguments(bundle1);

        Bundle bundle2 = new Bundle();
        bundle2.putString("url", "https://ad.admitad.com/g/vsgv0rbk47d76c048b76c2c5456194/?subid=hers&subid1=hers1");
        bundle2.putString("admitadid", "17798");
        bundle2.putString("raise", "10%");
        bundle2.putString("name", "Kidzee");
        bundle2.putString("imgurl", "https://gsered.com/charity/logo/Kidzee.png");
        nnnow = company_webview.newInstance();
        nnnow.setArguments(bundle2);

        Bundle bundle3 = new Bundle();
        bundle3.putString("url", "https://www.amazon.in/?tag=gsered-21&ascsubtag=hers");
        amazon = company_webview.newInstance();
        amazon.setArguments(bundle3);

        Bundle bundle4 = new Bundle();
        bundle4.putString("url", "https://ad.admitad.com/g/rb1qie435bd76c048b76a80d05f527/?subid=hers&subid1=hers1");
        bundle4.putString("admitadid", "14627");
        bundle4.putString("raise", "12%");
        bundle4.putString("name", "Flipkart");
        bundle4.putString("imgurl", "https://gsered.com/charity/logo/Flipkart.png");
        flipkart = company_webview.newInstance();
        flipkart.setArguments(bundle4);

        // Add fragment
        adapter.AddFragment(firstcry,"");
        adapter.AddFragment(nnnow, "");
        adapter.AddFragment(amazon, "");
        adapter.AddFragment(flipkart, "");

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
            Intent i = new Intent(KidsActivity.this, MainActivity.class);
            startActivity(i);
        }

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                count=0;
            }
        }, 3000);
        //ends press back button twice to return

        if ((keyCode == KeyEvent.KEYCODE_BACK) && this.nnnow != null && this.nnnow.cangoback()) {
            this.nnnow.goBack();
            return true;
        }
        else if ((keyCode == KeyEvent.KEYCODE_BACK) && this.firstcry != null && this.firstcry.cangoback()) {
            this.firstcry.goBack();
            return true;
        }
        else if ((keyCode == KeyEvent.KEYCODE_BACK) && this.flipkart != null && this.flipkart.cangoback()) {
            this.flipkart.goBack();
            return true;
        }
        else if ((keyCode == KeyEvent.KEYCODE_BACK) && this.amazon != null && this.amazon.cangoback()) {
            this.amazon.goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
