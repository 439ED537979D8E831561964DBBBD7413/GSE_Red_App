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

public class GiftsCakesActivity extends AppCompatActivity {

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private ImageView imageView1,imageView2,imageView3,imageView4;
    company_webview swiggy,ubereats,faasos,pizzahut;
    int count=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        viewPager = (ViewPager) findViewById(R.id.viewpager_id);

        viewPager.setOffscreenPageLimit(4);
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());

        View view1 = getLayoutInflater().inflate(R.layout.custom_tabicon, null);
        imageView1 = (ImageView) view1.findViewById(R.id.icon);
        Glide.with(this).load("https://gsered.com/charity/logo/Indiangiftsportal.png").placeholder(R.mipmap.ic_launcher).into(imageView1);

        View view2 = getLayoutInflater().inflate(R.layout.custom_tabicon, null);
        imageView2 = (ImageView) view2.findViewById(R.id.icon);
        Glide.with(this).load("https://gsered.com/charity/logo/Fernsnpetals.png").placeholder(R.mipmap.ic_launcher).into(imageView2);

//        View view3 = getLayoutInflater().inflate(R.layout.custom_tabicon, null);
//        imageView3 = (ImageView) view3.findViewById(R.id.icon);
//        Glide.with(this).load("https://gsered.com/charity/logo/Faasos.png").placeholder(R.mipmap.ic_launcher).into(imageView3);
//
//        View view4 = getLayoutInflater().inflate(R.layout.custom_tabicon, null);
//        imageView4 = (ImageView) view4.findViewById(R.id.icon);
//        Glide.with(this).load("https://gsered.com/charity/logo/Pizzahut.png").placeholder(R.mipmap.ic_launcher).into(imageView4);

        Bundle bundle1 = new Bundle();
        bundle1.putString("url", "https://ad.admitad.com/g/bhq7k7hmcgd76c048b760451de8123/?subid=hers&subid1=hers1");
        bundle1.putString("admitadid", "16163");
        bundle1.putString("raise", "8%");
        bundle1.putString("name", "Indian Gifts Portal");
        bundle1.putString("imgurl", "https://gsered.com/charity/logo/Indiangiftsportal.png");
        swiggy = company_webview.newInstance();
        swiggy.setArguments(bundle1);

        Bundle bundle2 = new Bundle();
        bundle2.putString("url", "https://ad.admitad.com/g/ttefnvo3nud76c048b769889a1a15e/?subid=hers&subid1=hers1");
        bundle2.putString("admitadid", "16495");
        bundle2.putString("raise", "4.25%");
        bundle2.putString("name", "Fernsnpetals");
        bundle2.putString("imgurl", "https://gsered.com/charity/logo/Fernsnpetals.png");
        ubereats = company_webview.newInstance();
        ubereats.setArguments(bundle2);

//        Bundle bundle3 = new Bundle();
//        bundle3.putString("url", "https://ad.admitad.com/g/nknpm3sq5cd76c048b76264a89c4b2/?subid=hers&subid1=hers1");
//        faasos = company_webview.newInstance();
//        faasos.setArguments(bundle3);
//
//        Bundle bundle4 = new Bundle();
//        bundle4.putString("url", "https://ad.admitad.com/g/ih45tqwxl5d76c048b76170aa892f2/?subid=hers&subid1=hers1");
//        pizzahut = company_webview.newInstance();
//        pizzahut.setArguments(bundle4);

        // Add fragment
        adapter.AddFragment(swiggy,"");
        adapter.AddFragment(ubereats, "");
//        adapter.AddFragment(faasos, "");
//        adapter.AddFragment(pizzahut,"");

        // adapter setup
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);

        tabLayout.getTabAt(0).setCustomView(view1);
        tabLayout.getTabAt(1).setCustomView(view2);
//        tabLayout.getTabAt(2).setCustomView(view3);
//        tabLayout.getTabAt(3).setCustomView(view4);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        //starts press back button twice to return
        count++;
        if(count==1)
            Toast.makeText(this, "Please click back button again to exit", Toast.LENGTH_SHORT).show();
        else if(count==2){
            Intent i = new Intent(GiftsCakesActivity.this, MainActivity.class);
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
//        else if ((keyCode == KeyEvent.KEYCODE_BACK) && this.faasos != null && this.faasos.cangoback()) {
//            this.faasos.goBack();
//            return true;
//        }
//        else if ((keyCode == KeyEvent.KEYCODE_BACK) && this.pizzahut != null && this.pizzahut.cangoback()) {
//            this.pizzahut.goBack();
//            return true;
//        }
        return super.onKeyDown(keyCode, event);
    }
}
