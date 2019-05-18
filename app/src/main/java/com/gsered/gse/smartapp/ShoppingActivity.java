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

public class ShoppingActivity extends AppCompatActivity {

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private ImageView imageView1,imageView2,imageView3,imageView4,imageView5;
    int count=0;

    company_webview myntra,nnnow,flipkart,amazon,firstcry;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        viewPager = (ViewPager) findViewById(R.id.viewpager_id);

        viewPager.setOffscreenPageLimit(6);
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());

        View view1 = getLayoutInflater().inflate(R.layout.custom_tabicon, null);
        imageView1 = (ImageView) view1.findViewById(R.id.icon);
        Glide.with(this).load("https://gsered.com/charity/logo/Myntra.png").placeholder(R.mipmap.ic_launcher).into(imageView1);

        View view2 = getLayoutInflater().inflate(R.layout.custom_tabicon, null);
        imageView2 = (ImageView) view2.findViewById(R.id.icon);
        Glide.with(this).load("https://gsered.com/charity/logo/Nnnow.png").placeholder(R.mipmap.ic_launcher).into(imageView2);

        View view3 = getLayoutInflater().inflate(R.layout.custom_tabicon, null);
        imageView3 = (ImageView) view3.findViewById(R.id.icon);
        Glide.with(this).load("https://gsered.com/charity/logo/Firstcry.png").placeholder(R.mipmap.ic_launcher).into(imageView3);

        View view4 = getLayoutInflater().inflate(R.layout.custom_tabicon, null);
        imageView4 = (ImageView) view4.findViewById(R.id.icon);
        Glide.with(this).load("https://www.gsered.com/charity/logo/Amazon.png").placeholder(R.mipmap.ic_launcher).into(imageView4);

        View view5 = getLayoutInflater().inflate(R.layout.custom_tabicon, null);
        imageView5 = (ImageView) view5.findViewById(R.id.icon);
        Glide.with(this).load("https://gsered.com/charity/logo/Flipkart.png").placeholder(R.mipmap.ic_launcher).into(imageView5);

        Bundle bundle1 = new Bundle();
        bundle1.putString("url", "https://ad.admitad.com/g/s56leml8ckd76c048b7623d5247706/?subid=hers&subid1=hers1");
        bundle1.putString("admitadid", "15481");
        bundle1.putString("raise", "5.90%");
        bundle1.putString("name", "Myntra");
        bundle1.putString("imgurl", "https://gsered.com/charity/logo/Myntra.png");
        myntra = company_webview.newInstance();
        myntra.setArguments(bundle1);

        Bundle bundle2 = new Bundle();
        bundle2.putString("url", "https://ad.admitad.com/g/f6dgh64z7ud76c048b76f448324775/?subid=hers&subid1=hers1");
        bundle2.putString("admitadid", "18136");
        bundle2.putString("raise", "9%");
        bundle2.putString("name", "Nnnow");
        bundle2.putString("imgurl", "https://gsered.com/charity/logo/Nnnow.png");
        nnnow = company_webview.newInstance();
        nnnow.setArguments(bundle2);

        Bundle bundle3 = new Bundle();
        bundle3.putString("url", "https://ad.admitad.com/g/s04yc4g9ryd76c048b7662ee748b0a/?subid=hers&subid1=hers1");
        bundle3.putString("admitadid", "14719");
        bundle3.putString("raise", "2%");
        bundle3.putString("name", "Firstcry");
        bundle3.putString("imgurl", "https://gsered.com/charity/logo/Firstcry.png");
        firstcry = company_webview.newInstance();
        firstcry.setArguments(bundle3);

        Bundle bundle4 = new Bundle();
        bundle4.putString("url", "https://www.amazon.in/?tag=gsered-21&ascsubtag=hers");
        amazon = company_webview.newInstance();
        amazon.setArguments(bundle4);

        Bundle bundle5 = new Bundle();
        bundle5.putString("url", "https://ad.admitad.com/g/rb1qie435bd76c048b76a80d05f527/?subid=hers&subid1=hers1");
        bundle5.putString("admitadid", "14627");
        bundle5.putString("raise", "12%");
        bundle5.putString("name", "Flipkart");
        bundle5.putString("imgurl", "https://gsered.com/charity/logo/Flipkart.png");
        flipkart = company_webview.newInstance();
        flipkart.setArguments(bundle5);

        Bundle bundle6 = new Bundle();
        bundle6.putString("domain", "fashion");
        AllCompanies allcompany = AllCompanies.newInstance();
        allcompany.setArguments(bundle6);

        // Add fragment
        adapter.AddFragment(myntra,"");
        adapter.AddFragment(nnnow, "");
        adapter.AddFragment(firstcry,"");
        adapter.AddFragment(amazon, "");
        adapter.AddFragment(flipkart, "");
        adapter.AddFragment(allcompany,"All");

        // adapter setup
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);

        tabLayout.getTabAt(0).setCustomView(view1);
        tabLayout.getTabAt(1).setCustomView(view2);
        tabLayout.getTabAt(2).setCustomView(view3);
        tabLayout.getTabAt(3).setCustomView(view4);
        tabLayout.getTabAt(4).setCustomView(view5);

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        //starts press back button twice to return
        count++;
        if(count==1)
            Toast.makeText(this, "Please click back button again to exit", Toast.LENGTH_SHORT).show();
        else if(count==2){
            Intent i = new Intent(ShoppingActivity.this, MainActivity.class);
            startActivity(i);
        }

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                count=0;
            }
        }, 3000);
        //ends press back button twice to return

        if ((keyCode == KeyEvent.KEYCODE_BACK) && this.myntra != null && this.myntra.cangoback()) {
            this.myntra.goBack();
            return true;
        }
        else if ((keyCode == KeyEvent.KEYCODE_BACK) && this.nnnow != null && this.nnnow.cangoback()) {
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
