package com.gsered.gse;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.DialogInterface;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.customtabs.CustomTabsIntent;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.bumptech.glide.Glide;
import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.DefaultSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.daimajia.slider.library.Tricks.ViewPagerEx;
import com.darwindeveloper.horizontalscrollmenulibrary.custom_views.HorizontalScrollMenuView;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.gson.Gson;
import com.gsered.gse.NewsFeed.NewsFragment;
import com.gsered.gse.NewsFeed.NewsList;
import com.gsered.gse.account.SetupAccount;
import com.gsered.gse.dashboard.dashboard;
import com.gsered.gse.donationslider.donationadapter;
import com.gsered.gse.donationslider.donationmodal;
import com.gsered.gse.games.GamesMainActivity;
import com.gsered.gse.modals.CompaniesList;
import com.gsered.gse.modals.Company;
import com.gsered.gse.modals.ReferCodeList;
import com.gsered.gse.modals.refercode;
import com.gsered.gse.newslist.NewsListAdapter;
import com.gsered.gse.newslist.newslistmodal;
import com.gsered.gse.rewards.RewardActivity;
import com.gsered.gse.search.SearchActivity;
import com.gsered.gse.settings.PersonalSettingActivity;
import com.gsered.gse.smartapp.BooksActivity;
import com.gsered.gse.smartapp.CricketActivity;
import com.gsered.gse.smartapp.EducationActivity;
import com.gsered.gse.smartapp.ElectronicsActivity;
import com.gsered.gse.smartapp.EntertainmentActivity;
import com.gsered.gse.smartapp.FilmsActivity;
import com.gsered.gse.smartapp.FoodActivity;
import com.gsered.gse.smartapp.GamesActivity;
import com.gsered.gse.smartapp.GiftsCakesActivity;
import com.gsered.gse.smartapp.GroceryActivity;
import com.gsered.gse.smartapp.HealthBeautyActivity;
import com.gsered.gse.smartapp.HomeServicesActivity;
import com.gsered.gse.smartapp.HotelActivity;
import com.gsered.gse.smartapp.KidsActivity;
import com.gsered.gse.smartapp.LocalActivity;
import com.gsered.gse.smartapp.MedicineActivity;
import com.gsered.gse.smartapp.NewsActivity;
import com.gsered.gse.smartapp.ProductionActivity;
import com.gsered.gse.smartapp.RentalCarsActivity;
import com.gsered.gse.smartapp.ShoppingActivity;
import com.gsered.gse.smartapp.SocialActivity;
import com.gsered.gse.smartapp.SongsActivity;
import com.gsered.gse.smartapp.TravelActivity;
import com.gsered.gse.smartapp.VideosActivity;
import com.gsered.gse.topdonors.TopDonorsActivity;
import com.gsered.gse.topslider.TopSliderAdapter;
import com.gsered.gse.topslider.TopSliderModal;
import com.mobapphome.mahandroidupdater.tools.MAHUpdaterController;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, BottomNavigationView.OnNavigationItemSelectedListener, NewsFragment.OnFragmentInteractionListener ,
        BaseSliderView.OnSliderClickListener, ViewPagerEx.OnPageChangeListener {

    public Button button;
    public LinearLayout linearLayout;
    private LinearLayout food,local,fashion,cars,films,beautyhealth,grocery,medicine,homeservices,electronics,books,giftscakes,kid,hotel,flight;
    private RelativeLayout social,videos,news,songs,search,game,cricket,production,education,entertainment,invitestrip;

    SliderLayout sliderLayout;
    ArrayList<String> image;
    ArrayList<String> title;
    ArrayList<String> weblink;
    HashMap<String,String> Hash_file_maps ;

    HorizontalScrollMenuView company_menu;
    TextView textView;

    private List<refercode> giftlist;
    private String GIFTSLIST = "giftlist", GIFTS = "gifts";

    //    search variables
    private String responseStr;
    private List<Company> companyList;
//    private List<NGO> ngocompanyList;
    private static String SHARED_PREFS_FILE = "COMPANY_SHAREDPREF";
    private static String COMPANIES = "companiesList";
    private String emailid;
    //    search variables ends here

    BottomNavigationView bottomNavigationView;
    NavigationView navigationView;
    TextView username,selectedngo,donateto;
    private Fragment fragment;
    private static final String URL_FOR_NGO = "https://www.gsered.com/intern/grp10/GSE_App/get_ngo.php";
    private static String APPCRED = "SIGNCRED";
    private static String REWARDS = "REWARDS";
    private static String NGO_ID = "NGOID";
    private static String USER_ID = "USERID";
    private static String PHONE = "PHONE";
    private static String usersngo = "usersngo";
    private static String NAME = "NAME";
    private static String EMAIL = "EMAIL";
    private static String DOB = "DOB";
    private static String IMGURI = "IMGURI";
    private static String ISLOGIN = "ISLOGIN";

    private ArrayList<String> madmitadid = new ArrayList<>();
    private ArrayList<String> mNames = new ArrayList<>();
    private ArrayList<String> murl = new ArrayList<>();
    private ArrayList<String> mraise = new ArrayList<>();
    private ArrayList<String> mImageUrls = new ArrayList<>();
    private ArrayList<String> mProbability = new ArrayList<>();

    private ConnectivityManager connectivityManager;
    private NetworkInfo networkInfo;
    private Snackbar snackbar;

    private InterstitialAd interstitialAd;
    private String uid,name,ngoid;
    private ViewPager viewPager;
    List<donationmodal> donationmodals;
    donationadapter adapter;
    List<newslistmodal> newslist = new ArrayList<>();

    TopSliderAdapter topSliderAdapter;
    List<TopSliderModal> topSliderModals = new ArrayList<>();

    private int mCurrentPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // admob initialize
        MobileAds.initialize(this, "ca-app-pub-1175795558096494~2544295068");

        subscribenotificationtoken("general");
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        viewPager = (ViewPager) findViewById(R.id.viewpager);

        topslider();
        deal_slider();
        donationslider();
        getImages();
        bottomnews();
        company_category();
        invitestripclick();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        //bottom navigation options i.e. explore & news
        bottomNavigationView = (BottomNavigationView) findViewById(R.id.navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        snackbar = Snackbar.make(findViewById(R.id.drawer_layout), "No Internet Connection", Snackbar.LENGTH_INDEFINITE);

        //start setting the visibility of logout and enable feature
        SharedPreferences sharedPreferences = getSharedPreferences(APPCRED, MODE_PRIVATE);
        Boolean checklogin = sharedPreferences.getBoolean(ISLOGIN, false);
        uid = sharedPreferences.getString(USER_ID,"");
        ngoid =sharedPreferences.getString(NGO_ID, "");
        emailid =sharedPreferences.getString(EMAIL, "");

        //TO SET NAME OF THE USER AFTER LOGIN AT THE SIDE ABR HEADER
        View side_bar_header = navigationView.getHeaderView(0);
        username = side_bar_header.findViewById(R.id.username);
        donateto = side_bar_header.findViewById(R.id.donateto);
        //END SETTING NSME AT SIDE BAR HEADER

        if(checklogin == true)
        {
            unsubscribenotificationtoken("notloggedin");
            subscribenotificationtoken("loggedin");

            //set username at the side bar header
            username.setText(getString(R.string.hello)+ " " + sharedPreferences.getString(NAME,""));
            donateto.setVisibility(View.VISIBLE);
            donateto.setText("Supporting " + sharedPreferences.getString(usersngo,""));

            navigationView.getMenu().findItem(R.id.top_donors).setEnabled(true);
            navigationView.getMenu().findItem(R.id.dashboard).setEnabled(true);
            navigationView.getMenu().findItem(R.id.personal_settings).setEnabled(true);
            navigationView.getMenu().findItem(R.id.selectNGO).setEnabled(true);
            navigationView.getMenu().findItem(R.id.nav_marketingkit).setEnabled(true);
            navigationView.getMenu().findItem(R.id.nav_refer).setEnabled(true);
            navigationView.getMenu().findItem(R.id.nav_reward).setEnabled(true);
            navigationView.getMenu().findItem(R.id.nav_logout).setVisible(true);
            navigationView.getMenu().findItem(R.id.nav_otp).setVisible(false);

            fetchreward();
        } else {
            subscribenotificationtoken("notloggedin");
            //set username at the side bar header
            username.setText(getString(R.string.nav_header_title));

            navigationView.getMenu().findItem(R.id.top_donors).setEnabled(false);
            navigationView.getMenu().findItem(R.id.dashboard).setEnabled(false);
            navigationView.getMenu().findItem(R.id.personal_settings).setEnabled(false);
            navigationView.getMenu().findItem(R.id.selectNGO).setEnabled(false);
            navigationView.getMenu().findItem(R.id.nav_marketingkit).setEnabled(false);
            navigationView.getMenu().findItem(R.id.nav_refer).setEnabled(false);
            navigationView.getMenu().findItem(R.id.nav_reward).setEnabled(false);
            navigationView.getMenu().findItem(R.id.nav_logout).setVisible(false);
            navigationView.getMenu().findItem(R.id.nav_otp).setVisible(true);
        }
        //end setting visibility and enable feature

        checkinternet();
        checkupdate();
        fetchdata();
        showad();
    }

    private void topslider() {
        Intent soc = new Intent(MainActivity.this, SocialActivity.class);
        Intent v = new Intent(MainActivity.this, VideosActivity.class);
        Intent n = new Intent(MainActivity.this, NewsActivity.class);
        Intent son = new Intent(MainActivity.this, SongsActivity.class);
        Intent sea = new Intent(MainActivity.this, com.gsered.gse.smartapp.SearchActivity.class);
        Intent g = new Intent(MainActivity.this, GamesMainActivity.class);
        Intent c = new Intent(MainActivity.this, CricketActivity.class);
        Intent p = new Intent(MainActivity.this, ProductionActivity.class);
        Intent ed = new Intent(MainActivity.this, EducationActivity.class);
        Intent en = new Intent(MainActivity.this, EntertainmentActivity.class);

        topSliderModals.add(new TopSliderModal("Social",R.drawable.social,soc));
        topSliderModals.add(new TopSliderModal("Videos",R.drawable.videos,v));
        topSliderModals.add(new TopSliderModal("News",R.drawable.news,n));
        topSliderModals.add(new TopSliderModal("Songs",R.drawable.songs,son));
        topSliderModals.add(new TopSliderModal("Search",R.drawable.search,sea));
        topSliderModals.add(new TopSliderModal("Games",R.drawable.games,g));
        topSliderModals.add(new TopSliderModal("Cricket",R.drawable.cricket,c));
        topSliderModals.add(new TopSliderModal("Productivity",R.drawable.productivity,p));
        topSliderModals.add(new TopSliderModal("Educational",R.drawable.education,ed));
        topSliderModals.add(new TopSliderModal("Entertainment",R.drawable.entertainment,en));

        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        RecyclerView recyclerView = findViewById(R.id.topslider);
        recyclerView.setLayoutManager(layoutManager);
        topSliderAdapter = new TopSliderAdapter(topSliderModals,this);
        recyclerView.setAdapter(topSliderAdapter);
    }

    private void bottomnews() {
        ImageView live1,live2,live3,live4;
        TextView seeallnews;

        live1 = (ImageView) findViewById(R.id.indiatodaylive);
        live2 = (ImageView) findViewById(R.id.indiatvlive);
        live3 = (ImageView) findViewById(R.id.news18live);
        live4 = (ImageView) findViewById(R.id.aajtaklive);
        seeallnews = (TextView) findViewById(R.id.seeallnews);

        seeallnews.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, NewsActivity.class);
                startActivity(i);
            }
        });

        live1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, OpenUrl.class);
                i.putExtra("url", "https://www.indiatoday.in/livetv");
                startActivity(i);
            }
        });

        live2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, OpenUrl.class);
                i.putExtra("url", "https://www.indiatvnews.com/livetv");
                startActivity(i);
            }
        });

        live3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, OpenUrl.class);
                i.putExtra("url", "https://www.news18.com/livetv/");
                startActivity(i);
            }
        });

        live4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, OpenUrl.class);
                i.putExtra("url", "https://aajtak.intoday.in/livetv.html");
                startActivity(i);
            }
        });

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                getnews();
            }
        });

    }

    private void getnews() {

        OkHttpClient client = new OkHttpClient();
        String url = "https://newsapi.org/v2/top-headlines?country=in&apiKey=cb2c98e096a04508b86e1238386deb08";
        okhttp3.Request request = new okhttp3.Request.Builder()
                .url(url)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, final okhttp3.Response response) throws IOException {
                if(response.isSuccessful()){
                    responseStr = response.body().string();
                    if(MainActivity.this == null)
                        return;

                    try {
                        JSONObject jsonObject = new JSONObject(responseStr);
                        JSONArray articles = jsonObject.getJSONArray("articles");
                        int l=7;

                        if(l > articles.length())
                            l = articles.length();

                        for(int i = 0; i < l; i++){
                            JSONObject object = articles.getJSONObject(i);
                            newslist.add(new newslistmodal(object.getString("urlToImage"),object.getString("title"),object.getJSONObject("source").getString("name"),object.getString("url"), false));
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        RecyclerView recyclerView = findViewById(R.id.newslist);
        recyclerView.setLayoutManager(layoutManager);
        NewsListAdapter adapter = new NewsListAdapter(this, newslist);
        recyclerView.setAdapter(adapter);
    }

    private void invitestripclick() {
        invitestrip = (RelativeLayout) findViewById(R.id.invitestrip);
        invitestrip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, InviteActivity.class);
                startActivity(i);
            }
        });
    }

    private void donationslider() {
        donationmodals = new ArrayList<>();

        donationmodals.add(new donationmodal("https://www.gsered.com/intern/grp10/GSE_App/images/offericon/2.png","Edit on NGO pedia","Donate INR 51"));
        donationmodals.add(new donationmodal("https://www.gsered.com/intern/grp10/GSE_App/images/offericon/9.png","Play Games","Earn INR 1 to INR 19"));
        donationmodals.add(new donationmodal("https://www.gsered.com/intern/grp10/GSE_App/images/offericon/5.png","Order food","Earn INR 1 to INR 99"));
        donationmodals.add(new donationmodal("https://www.gsered.com/intern/grp10/GSE_App/images/offericon/6.png","Recharge Phone, DTH","Earn INR 1 to INR 199"));
        donationmodals.add(new donationmodal("https://www.gsered.com/intern/grp10/GSE_App/images/offericon/8.png","Buy electronics","Earn INR 29 to INR 299"));
        donationmodals.add(new donationmodal("https://www.gsered.com/intern/grp10/GSE_App/images/offericon/7.png","Share on Social Media","Earn INR 9 to INR 199"));
        donationmodals.add(new donationmodal("https://www.gsered.com/intern/grp10/GSE_App/images/offericon/4.png","Shopping","Earn INR 9 to INR 199"));
        donationmodals.add(new donationmodal("https://www.gsered.com/intern/grp10/GSE_App/images/offericon/1.png","Book a flight or hotel","Earn INR 9 to INR 99"));
        donationmodals.add(new donationmodal("https://www.gsered.com/intern/grp10/GSE_App/images/offericon/2.png","Edit on NGO pedia","Donate INR 51"));
        donationmodals.add(new donationmodal("https://www.gsered.com/intern/grp10/GSE_App/images/offericon/9.png","Play Games","Earn INR 1 to INR 19"));
//        donationmodals.add(new donationmodal("https://www.gsered.com/intern/grp10/GSE_App/images/offericon/3.png","Book a movie ticket","Earn upto INR 101"));

        adapter = new donationadapter(donationmodals, this);

        mCurrentPosition = 1;

        viewPager.setAdapter(adapter);
        viewPager.setCurrentItem(mCurrentPosition);
        viewPager.setClipToPadding(false);
        viewPager.setPadding(200,0,200,0);
        viewPager.setPageMargin(10);

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                mCurrentPosition = position;
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                if (mCurrentPosition == 0)
                    viewPager.setCurrentItem((donationmodals.size()-1) -1, false);

                if (mCurrentPosition == (donationmodals.size()-1))
                    viewPager.setCurrentItem(1, false);
            }
        });
    }

    private void showad() {
        int count;

        interstitialAd = new InterstitialAd(this);
        interstitialAd.setAdUnitId("ca-app-pub-1175795558096494/8414804503");
        interstitialAd.loadAd(new AdRequest.Builder().build());

        SharedPreferences ref = getApplicationContext().getSharedPreferences("ads", Context.MODE_PRIVATE);
        count = ref.getInt("count",0);
        if(count != 5) {
            count = count+1;
        }
        else {
            count = 0;
            // start wait for 25 seconds before redirect start
            final android.os.Handler handler = new android.os.Handler();
            Runnable runnable = new Runnable() {
                @Override
                public void run() {
                    if (interstitialAd.isLoaded()) {
                        interstitialAd.show();
                    } else {
                    }
                }
            };
            handler.postDelayed(runnable, 25000);
            // end wait for 25 seconds before redirect start
        }

        SharedPreferences.Editor editor = ref.edit();
        editor.putInt("count", count);
        editor.apply();

    }

    private void subscribenotificationtoken(String groupname) {
        FirebaseMessaging.getInstance().subscribeToTopic(groupname)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        String msg = "successfull";
                        if (!task.isSuccessful()) {
                            msg = "failed";
                        }
                    }
                });
    }

    private void unsubscribenotificationtoken(String groupname) {
        FirebaseMessaging.getInstance().unsubscribeFromTopic(groupname)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        String msg = "successfull";
                        if (!task.isSuccessful()) {
                            msg = "failed";
                        }
                    }
                });
    }

    private void checkinternet() {
        connectivityManager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        networkInfo = connectivityManager.getActiveNetworkInfo();
        snackbar = Snackbar.make(findViewById(R.id.drawer_layout), "No Internet Connection", Snackbar.LENGTH_INDEFINITE);

        if(networkInfo!=null && networkInfo.isConnected()){
        } else {
            Intent i = new Intent(MainActivity.this, InternetStatusActivity.class);
            startActivity(i);
        }
    }

    private void getImages(){
        madmitadid.add("14627");
        murl.add("https://ad.admitad.com/g/rb1qie435bd76c048b76a80d05f527/?subid=hers&subid1=hers1");
        mraise.add("12%");
        mImageUrls.add("https://gsered.com/charity/logo/Flipkart.png");
        mProbability.add("70");
        mNames.add("Flipkart");

        madmitadid.add("15469");
        murl.add("https://ad.admitad.com/g/ta366jiz5od76c048b7674c5fc7d3a/?subid=hers&subid1=hers1");
        mraise.add("INR 20");
        mNames.add("Swiggy");
        mProbability.add("83");
        mImageUrls.add("https://gsered.com/charity/logo/Swiggy.png");

        madmitadid.add("16000");
        murl.add("https://ad.admitad.com/g/qek9wydsi1d76c048b763b5519b828/?subid=hers&subid1=hers1");
        mraise.add("INR 250");
        mNames.add("Zoomcar");
        mProbability.add("78");
        mImageUrls.add("https://gsered.com/charity/logo/Zoomcar.png");

        madmitadid.add("14719");
        murl.add("https://ad.admitad.com/g/s04yc4g9ryd76c048b7662ee748b0a/?subid=hers&subid1=hers1");
        mraise.add("2%");
        mNames.add("Firstcry");
        mProbability.add("77");
        mImageUrls.add("https://gsered.com/charity/logo/Firstcry.png");

        madmitadid.add("14745");
        murl.add("https://ad.admitad.com/g/jqvq0rzz8pd76c048b765c7d2eed69/?subid=hers&subid1=hers1");
        mraise.add("6%");
        mNames.add("Netmeds");
        mProbability.add("76");
        mImageUrls.add("https://gsered.com/charity/logo/Netmeds.png");

        madmitadid.add("15587");
        murl.add("https://ad.admitad.com/g/gob3zzel35d76c048b761051f25b5d/?subid=hers&subid1=hers1");
        mraise.add("5%");
        mNames.add("Bigbasket");
        mProbability.add("96");
        mImageUrls.add("https://gsered.com/charity/logo/Bigbasket.png");

        madmitadid.add("15481");
        murl.add("https://ad.admitad.com/g/s56leml8ckd76c048b7623d5247706/?subid=hers&subid1=hers1");
        mraise.add("5.90%");
        mNames.add("Myntra");
        mProbability.add("77");
        mImageUrls.add("https://gsered.com/charity/logo/Myntra.png");

        madmitadid.add("18679");
        murl.add("https://ad.admitad.com/g/skgi6a1tctd76c048b766339fea834/?subid=hers&subid1=hers1");
        mraise.add("4%");
        mNames.add("PaytmMall");
        mProbability.add("76");
        mImageUrls.add("https://gsered.com/charity/logo/Paytm.png");

        initRecyclerView();
    }

    public void checkupdate(){
        String currentversion = getAppVersion(this);
        String packagename = this.getPackageName();

        new ServiceHandler().update(currentversion, packagename, new Callback() {
            @Override
            public void onFailure(okhttp3.Call call, IOException e) {
                // Something went wrong
            }

            @Override
            public void onResponse(okhttp3.Call call, okhttp3.Response response) throws IOException {
                if (response.isSuccessful()) {
                    final String updatereport = response.body().string();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if(updatereport.equalsIgnoreCase("showupdatepopup,")) {
                                AlertDialog.Builder alertDialog = new AlertDialog.Builder(MainActivity.this)
                                        .setIcon(R.mipmap.ic_launcher_round)
                                        .setCancelable(false)
                                        .setTitle("New Version Available")
                                        .setMessage("To better experience the features, update to the latest version.")
                                        .setPositiveButton("Update Now", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialogInterface, int i) {
                                                Uri uri = Uri.parse("market://details?id=" + getPackageName()); // missing 'http://' will cause crashed
                                                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                                                startActivity(intent);
                                            }
                                        });
                                alertDialog.create().show();
                            }
                        }
                    });

                } else {
                    // Request not successful
                    Toast.makeText(MainActivity.this, "fail", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private String getAppVersion(Context context){
        String result = "";
        try{
            result = context.getPackageManager().getPackageInfo(context.getPackageName(),0)
                    .versionName;
            result = result.replaceAll("[a-zA-Z]|-","");

        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        return result;
    }

    private void initRecyclerView(){
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        RecyclerView recyclerView = findViewById(R.id.companyslider);
        recyclerView.setLayoutManager(layoutManager);
        horizontalsliderAdapter adapter = new horizontalsliderAdapter(mNames, madmitadid, murl, mraise, mImageUrls, mProbability,this);
        recyclerView.setAdapter(adapter);
    }

    public void deal_slider(){

        //Carousel view starts
        title = new ArrayList<String>();
        image = new ArrayList<String>();
        weblink = new ArrayList<String>();

        Hash_file_maps = new HashMap<String, String>();

        sliderLayout = (SliderLayout)findViewById(R.id.carouselView);
        if (Hash_file_maps != null) {
            Hash_file_maps.clear();
        }

        Hash_file_maps.put("contribute1", "https://www.gsered.com/intern/grp10/GSE_App/images/slider/shoppingforgoodness.png");
        Hash_file_maps.put("contribute2", "https://www.gsered.com/intern/grp10/GSE_App/images/slider/6.png");
        Hash_file_maps.put("contribute3", "https://www.gsered.com/intern/grp10/GSE_App/images/slider/5.png");
        Hash_file_maps.put("contribute4", "https://www.gsered.com/intern/grp10/GSE_App/images/slider/4.png");
        Hash_file_maps.put("contribute5", "https://www.gsered.com/intern/grp10/GSE_App/images/slider/3.png");

        for(String name : Hash_file_maps.keySet()){

            DefaultSliderView defaultSliderView = new DefaultSliderView(MainActivity.this);
            defaultSliderView .image(Hash_file_maps.get(name))
                    .setScaleType(BaseSliderView.ScaleType.Fit)
                    .setOnSliderClickListener(this);
            sliderLayout.addSlider(defaultSliderView);

//            TextSliderView textSliderView = new TextSliderView(MainActivity.this);
//            textSliderView
//                    .description(name)
//                    .image(Hash_file_maps.get(name))
//                    .setScaleType(BaseSliderView.ScaleType.Fit);
//            textSliderView.bundle(new Bundle());
//            textSliderView.getBundle()
//                    .putString("extra",name);
//            sliderLayout.addSlider(textSliderView);
        }
        sliderLayout.setPresetTransformer(SliderLayout.Transformer.Accordion);
        sliderLayout.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
        sliderLayout.setCustomAnimation(new DescriptionAnimation());
        sliderLayout.setDuration(3000);
        sliderLayout.addOnPageChangeListener(this);

        //Carousel view ends


    }

    public void company_category(){

//        social = (RelativeLayout) findViewById(R.id.item1);
//        social.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(MainActivity.this, SocialActivity.class);
//                intent.putExtra("domain", "Social");
//                startActivity(intent);
//            }
//        });
//
//        videos = (RelativeLayout) findViewById(R.id.item2);
//        videos.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(MainActivity.this, VideosActivity.class);
//                intent.putExtra("domain", "Videos");
//                startActivity(intent);
//            }
//        });
//
//        news = (RelativeLayout) findViewById(R.id.item3);
//        news.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(MainActivity.this, NewsActivity.class);
//                intent.putExtra("domain", "News");
//                startActivity(intent);
//            }
//        });
//
//        songs = (RelativeLayout) findViewById(R.id.item4);
//        songs.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(MainActivity.this, SongsActivity.class);
//                intent.putExtra("domain", "Songs");
//                startActivity(intent);
//            }
//        });
//
//        search = (RelativeLayout) findViewById(R.id.item5);
//        search.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(MainActivity.this, com.gsered.gse.smartapp.SearchActivity.class);
//                intent.putExtra("domain", "Search");
//                startActivity(intent);
//            }
//        });
//
//        game = (RelativeLayout) findViewById(R.id.item6);
//        game.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(MainActivity.this, GamesActivity.class);
//                intent.putExtra("domain", "Games");
//                startActivity(intent);
//            }
//        });
//
//        cricket = (RelativeLayout) findViewById(R.id.item7);
//        cricket.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(MainActivity.this, CricketActivity.class);
//                intent.putExtra("domain", "Cricket");
//                startActivity(intent);
//            }
//        });
//
//        production = (RelativeLayout) findViewById(R.id.item8);
//        production.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(MainActivity.this, ProductionActivity.class);
//                intent.putExtra("domain", "Productivity");
//                startActivity(intent);
//            }
//        });
//
//        education = (RelativeLayout) findViewById(R.id.item9);
//        education.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(MainActivity.this, EducationActivity.class);
//                intent.putExtra("domain", "Education");
//                startActivity(intent);
//            }
//        });
//
//        entertainment = (RelativeLayout) findViewById(R.id.item10);
//        entertainment.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(MainActivity.this, EntertainmentActivity.class);
//                intent.putExtra("domain", "Entertainment");
//                startActivity(intent);
//            }
//        });

        // category starts
        food = (LinearLayout) findViewById(R.id.food);
        food.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, FoodActivity.class);
                i.putExtra("domain", "Food");
                startActivity(i);
            }
        });

        local = (LinearLayout) findViewById(R.id.local);
        local.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, LocalActivity.class);
                i.putExtra("domain", "Near You");
                startActivity(i);
            }
        });

        fashion = (LinearLayout) findViewById(R.id.fashion);
        fashion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, ShoppingActivity.class);
                i.putExtra("domain", "Fashion");
                startActivity(i);
            }
        });

        cars = (LinearLayout) findViewById(R.id.cars);
        cars.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, RentalCarsActivity.class);
                i.putExtra("domain", "Cars");
                startActivity(i);
            }
        });

        films = (LinearLayout) findViewById(R.id.movies);
        films.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, FilmsActivity.class);
                i.putExtra("domain", "Films");
                startActivity(i);
            }
        });

        beautyhealth = (LinearLayout) findViewById(R.id.healthbeauty);
        beautyhealth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, HealthBeautyActivity.class);
                i.putExtra("domain", "Health & Beauty");
                startActivity(i);
            }
        });

        grocery = (LinearLayout) findViewById(R.id.grocery);
        grocery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, GroceryActivity.class);
                i.putExtra("domain", "Grocery");
                startActivity(i);
            }
        });

        medicine = (LinearLayout) findViewById(R.id.medicine);
        medicine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, MedicineActivity.class);
                i.putExtra("domain", "Medicine");
                startActivity(i);
            }
        });

        homeservices = (LinearLayout) findViewById(R.id.homeservices);
        homeservices.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, HomeServicesActivity.class);
                i.putExtra("domain", "Home Services");
                startActivity(i);
            }
        });

        electronics = (LinearLayout) findViewById(R.id.electronics);
        electronics.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, ElectronicsActivity.class);
                i.putExtra("domain", "Electronics");
                startActivity(i);
            }
        });

        books = (LinearLayout) findViewById(R.id.books);
        books.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, DynamicActivity.class);
                i.putExtra("domain", "books");
                i.putExtra("module_nm", "Books");
                startActivity(i);
            }
        });

        giftscakes = (LinearLayout) findViewById(R.id.giftcake);
        giftscakes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, GiftsCakesActivity.class);
                i.putExtra("domain", "Gifts & Cakes");
                startActivity(i);
            }
        });

        kid = (LinearLayout) findViewById(R.id.kids);
        kid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, KidsActivity.class);
                i.putExtra("domain", "kids");
                startActivity(i);
            }
        });

        hotel = (LinearLayout) findViewById(R.id.hotels);
        hotel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, HotelActivity.class);
                i.putExtra("domain", "hotels");
                startActivity(i);
            }
        });

        flight = (LinearLayout) findViewById(R.id.flights);
        flight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, TravelActivity.class);
                i.putExtra("domain", "flights");
                startActivity(i);
            }
        });

    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    //    for update popup- method to finalize module
    @Override
    protected void onDestroy() {
        MAHUpdaterController.end();
        super.onDestroy();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_search) {
            Intent i = new Intent(MainActivity.this, SearchActivity.class);
            startActivity(i);
        }
        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        SharedPreferences sharedPreferences = getSharedPreferences(APPCRED, MODE_PRIVATE);
        Boolean checklogin = sharedPreferences.getBoolean(ISLOGIN, false);

        if (id == R.id.nav_otp) {
            if(checklogin ==true) {
//                Toast.makeText(MainActivity.this, "Already Logged In", Toast.LENGTH_SHORT).show();
            }
            else{
                Intent i = new Intent(MainActivity.this, SigninActivity.class);
                startActivity(i);
            }
        }
        else if (id == R.id.nav_marketingkit) {
            if(checklogin ==true) {
                Intent i = new Intent(MainActivity.this, MarketingKit.class);
                startActivity(i);
            }
        }
        else if (id == R.id.nav_refer) {
            Intent i = new Intent(MainActivity.this, ScratchGift.class);
            startActivity(i);
        }
        else if (id == R.id.nav_reward) {
            Intent i = new Intent(MainActivity.this, RewardActivity.class);
            startActivity(i);
        }
        else if (id == R.id.top_donors) {
            Intent i = new Intent(MainActivity.this, TopDonorsActivity.class);
            startActivity(i);
        }
        else if (id == R.id.nav_invite) {
            Intent i = new Intent(MainActivity.this, InviteActivity.class);
            startActivity(i);
        }
        else if (id == R.id.dashboard) {
            if(checklogin ==true) {
                Intent i = new Intent(MainActivity.this, dashboard.class);
                startActivity(i);
            }
            else{
                Toast.makeText(MainActivity.this, "Please Login and retry", Toast.LENGTH_SHORT).show();
            }
        }
        else if (id == R.id.personal_settings)
        {
            if (checklogin == true) {
                Intent i = new Intent(MainActivity.this, PersonalSettingActivity.class);
                startActivity(i);
            } else {
                Toast.makeText(MainActivity.this, "Please Login and retry", Toast.LENGTH_SHORT).show();
            }
        }
        else if (id == R.id.selectNGO)
        {
            if (checklogin == true) {
                Intent i = new Intent(getApplicationContext(), DynamicNGOActivity.class);
                i.putExtra("module_nm", "NGO's");
                startActivity(i);
            } else {
                Toast.makeText(MainActivity.this, "Please Login and retry", Toast.LENGTH_SHORT).show();
            }
        }
        else if (id == R.id.direct_donation) {

            Intent i = new Intent(MainActivity.this, DonationActivity.class);
            startActivity(i);

//                Intent i = new Intent(MainActivity.this, WebsiteView.class);
//                i.putExtra("method","get");
//                i.putExtra("emailid", emailid);
//                i.putExtra("url", "https://www.gsered.com/socialcause/payment/index.php?payment_type=one_time&t3=D&item_name=Shakti&currency_code=INR&amount=100");
//                startActivity(i);
        }
        else if (id == R.id.nav_about_us) {
            // Handle the about us action
            Intent i = new Intent(MainActivity.this, aboutus.class);
            startActivity(i);

        } else if (id == R.id.nav_contact_us) {
            Intent i = new Intent(MainActivity.this, contact_us.class);
            startActivity(i);

        } else if (id == R.id.nav_share) {
//            Intent sendIntent = new Intent();
//            sendIntent.setAction(Intent.ACTION_SEND);
//            sendIntent.putExtra(Intent.EXTRA_TEXT, "Hey check out this awesome app which helps to donate without paying any extra penny :) https://play.google.com/store/apps/details?id=com.gsered.gse");
//            sendIntent.setType("text/plain");
//            startActivity(sendIntent);

            Intent i = new Intent(MainActivity.this, InviteActivity.class);
            startActivity(i);

        } else if (id == R.id.nav_logout) {

            SharedPreferences preferences = getSharedPreferences(APPCRED, MODE_PRIVATE);
            preferences.edit().putBoolean(ISLOGIN, false);
            preferences.edit().clear().commit();

            //set username at the side bar header
            username.setText(getString(R.string.nav_header_title));
            donateto.setVisibility(View.GONE);
            //disable my account and dashboard options
            navigationView.getMenu().findItem(R.id.top_donors).setEnabled(false);
            navigationView.getMenu().findItem(R.id.dashboard).setEnabled(false);
            navigationView.getMenu().findItem(R.id.selectNGO).setEnabled(false);
            navigationView.getMenu().findItem(R.id.nav_logout).setVisible(false);
            navigationView.getMenu().findItem(R.id.nav_otp).setVisible(true);
//            navigationView.getMenu().findItem(R.id.nav_sign_up).setVisible(true);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_dashboard:
                    Intent explore = new Intent(MainActivity.this, dashboard.class);
                    startActivity(explore);
                    return true;
                case R.id.navigation_game:
                    Intent ne = new Intent(MainActivity.this, GamesMainActivity.class);
                    startActivity(ne);
                    return true;
                case R.id.navigation_news:
                    Intent n = new Intent(MainActivity.this, NewsActivity.class);
                    startActivity(n);
                    return true;

            }
            return false;
        }
    };

    @Override
    public void onSliderClick(BaseSliderView slider) {
        int position=sliderLayout.getCurrentPosition();

        String url = null,imgurl = null,companyname = null, prob = null;
        switch(position)
        {
            case 0:
//                CustomTabsIntent.Builder intentBuilder = new CustomTabsIntent.Builder();
//                intentBuilder.setToolbarColor(Color.parseColor("#f44336"));
//                CustomTabsIntent customTabsIntent = intentBuilder.build();
//                intentBuilder.enableUrlBarHiding();
                url = "https://www.youtube.com/watch?v=NhR2aXGkS7Y";
//                customTabsIntent.launchUrl(MainActivity.this, Uri.parse(url));
                Intent i = new Intent(MainActivity.this, OpenUrl.class);
                i.putExtra("url", url);
                startActivity(i);
                break;
            case 1:
                url = "https://ad.admitad.com/g/ta366jiz5od76c048b7674c5fc7d3a/?subid=hers&subid1=hers1";
                imgurl = "https://gsered.com/charity/logo/Swiggy.png";
                companyname = "Swiggy";
                prob = "83";
                break;
            case 2:
                url = "https://ad.admitad.com/g/gob3zzel35d76c048b761051f25b5d/?subid=hers&subid1=hers1";
                imgurl = "https://gsered.com/charity/logo/Bigbasket.png";
                companyname = "BigBasket";
                prob = "96";
                break;
            case 3:
                url = "https://ad.admitad.com/g/qo2o4kaqx6d76c048b767e035ccb62/?subid=hers&subid1=hers1";
                imgurl = "https://gsered.com/charity/logo/Medlife.png";
                companyname = "MedLife";
                prob = "66";
                break;
            case 4:
                Intent in = new Intent(MainActivity.this, InviteActivity.class);
                startActivity(in);
                break;
        }

        if(imgurl != null){
            Intent intent = new Intent(MainActivity.this, VisitShop.class);
            intent.putExtra("source","activate");
            intent.putExtra("url",url);
            intent.putExtra("imgurl",imgurl);
            intent.putExtra("companyname",companyname);
            intent.putExtra("probability",prob);
            startActivity(intent);
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
    }

    @Override
    public void onPageScrollStateChanged(int state) {
    }

    private void fetchreward() {
        new ServiceHandler().totalreward(uid , new Callback() {
            @Override
            public void onFailure(okhttp3.Call call, IOException e) {
                // Something went wrong
            }

            @Override
            public void onResponse(okhttp3.Call call, okhttp3.Response response) throws IOException {
                if (response.isSuccessful()) {
                    final String posts = response.body().string();

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                JSONObject socialpost = null;
                                socialpost = new JSONObject(posts);
                                JSONObject p = socialpost.getJSONObject("earned");
                                String userreward = p.getString("total");
                                String paidreward = p.getString("paid");
                                String holdername = p.getString("holdername");
                                String bankname = p.getString("bankname");
                                String branchname = p.getString("branchname");
                                String ifsccode = p.getString("ifsccode");
                                String accountnumber = p.getString("accountnumber");
                                SharedPreferences sharedPreferences = getSharedPreferences(REWARDS, MODE_PRIVATE);
                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                editor.putString("totalreward", userreward);
                                editor.putString("paidreward", paidreward);
                                editor.putString("holdername", holdername);
                                editor.putString("bankname", bankname);
                                editor.putString("branchname", branchname);
                                editor.putString("ifsccode", ifsccode);
                                editor.putString("accountnumber", accountnumber);
                                editor.apply();
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    });
                } else {
                    // Request not successful
                    System.out.println("fail");
                }
            }
        });
    }



    //    search bar starts here
    private void fetchdata() {
        new ServiceHandler().post("" , new Callback() {
            @Override
            public void onFailure(okhttp3.Call call, IOException e) {
                // Something went wrong
            }

            @Override
            public void onResponse(okhttp3.Call call, okhttp3.Response response) throws IOException {
                if (response.isSuccessful()) {
                    responseStr = response.body().string();
                    System.out.println(responseStr);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                render(responseStr);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    });

                } else {
                    // Request not successful
                    System.out.println("fail");
                }
            }

        });

    }

    private void render(String response) throws JSONException {
        companyList = new ArrayList<>();

        JSONObject jsonObject = new JSONObject(response);
        JSONArray companies = jsonObject.getJSONArray("companies");
        for(int i =0; i<companies.length();i++){
            JSONObject object = companies.getJSONObject(i);
            companyList.add(new Company(object.getString("name"),"",object.getString("gotolink"),object.getString("img_url"),object.getString("admitad_id"),object.getString("raise"),object.getString("activate"),object.getString("probability"),object.getString("coupon"),object.getString("category")));
        }

        CompaniesList companiesList = new CompaniesList();
        companiesList.setCompanyList(companyList);

        // save the task list to preference
        SharedPreferences prefs = getSharedPreferences(SHARED_PREFS_FILE, Context.MODE_PRIVATE);
        SharedPreferences.Editor prefsEditor = prefs.edit();
        Gson gson = new Gson();
        String json = gson.toJson(companiesList);
        prefsEditor.putString(COMPANIES, json);
        prefsEditor.commit();
    }
    //    search bar endes here

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    private boolean loadFragment(Fragment fragment)
    {
        if(fragment != null)
        {
            // create a frame layout
            FrameLayout fragmentLayout = new FrameLayout(this);
            // set the layout params to fill the activity
            fragmentLayout.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            // set an id to the layout
            fragmentLayout.setId(1000); // some positive integer
            // set the layout as Activity content
            setContentView(fragmentLayout);
            // Finally , add the fragment

            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
//            transaction.replace(R.id.frame_container, fragment);
            transaction.add(1000,fragment);
            transaction.addToBackStack(null);
            transaction.commit();

            return true;
        }
        return false;
    }

}

