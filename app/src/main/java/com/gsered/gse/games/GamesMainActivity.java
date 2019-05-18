package com.gsered.gse.games;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.DefaultSliderView;
import com.daimajia.slider.library.Tricks.ViewPagerEx;
import com.gsered.gse.DynamicActivity;
import com.gsered.gse.InviteActivity;
import com.gsered.gse.OpenUrl;
import com.gsered.gse.R;
import com.gsered.gse.ScratchGift;
import com.gsered.gse.VisitShop;
import com.gsered.gse.games.category_without_title.CategoryAdapter;
import com.gsered.gse.games.category_without_title.CategoryModals;
import com.gsered.gse.games.recommended.RecommendedAdapter;
import com.gsered.gse.games.recommended.RecommendedModals;
import com.gsered.gse.games.sports.SportsAdapter;
import com.gsered.gse.games.sports.SportsModal;
import com.gsered.gse.horizontalsliderAdapter;
import com.gsered.gse.newslist.NewsListAdapter;
import com.gsered.gse.newslist.newslistmodal;
import com.gsered.gse.search.SearchActivity;
import com.gsered.gse.topslider.TopSliderAdapter;
import com.gsered.gse.topslider.TopSliderModal;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class GamesMainActivity extends AppCompatActivity implements BaseSliderView.OnSliderClickListener, ViewPagerEx.OnPageChangeListener {

    // top slider
    TopSliderAdapter topSliderAdapter;
    List<TopSliderModal> topSliderModals = new ArrayList<>();

    // image slider
    SliderLayout sliderLayout;
    ArrayList<String> image;
    ArrayList<String> title;
    ArrayList<String> weblink;
    HashMap<String,String> Hash_file_maps ;

    //recommended slider
    private ArrayList<String> madmitadid;
    private ArrayList<String> mNames;
    private ArrayList<String> murl;
    private ArrayList<String> mraise;
    private ArrayList<String> mImageUrls;
    private ArrayList<String> mProbability;

    // mind games
    List<newslistmodal> newslist;

    // sports
    private ViewPager viewPager,viewPager1,viewPager2,viewPager3,viewPager4;
    private int mCurrentPosition,mCurrentPosition4,mCurrentPosition3,mCurrentPosition2,mCurrentPosition1;
    List<SportsModal> sportsModals;

    List<CategoryModals> categoryModals;
    private RecyclerView recyclerView1, recyclerView2, recyclerView3;
    TextView seeall_action, seeall_arcade, seeall_strategy, seeall_sports, seeall_mind;

    List<RecommendedModals> recommendedModals;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_games_main);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("GSE Red Games");
        getSupportActionBar().setElevation(0);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#7851a9")));

        topslider();
        imageslider();
        recommendslider();
        highestearningslider();
        mindgames();
        strategy();
        sports();
        simpleslider();
        simple2();
        simple3();
        simple4();

        seeall_action = (TextView) findViewById(R.id.actionseealltext);
        seeall_sports = (TextView) findViewById(R.id.sportseealltext);
        seeall_arcade = (TextView) findViewById(R.id.arcadeseealltext);
        seeall_mind = (TextView) findViewById(R.id.mindseealltext);
        seeall_strategy = (TextView) findViewById(R.id.strategyseealltext);

        seeall_action.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(GamesMainActivity.this, DynamicActivity.class);
                intent.putExtra("domain", "Action");
                intent.putExtra("module_nm", "Action Games");
                startActivity(intent);
            }
        });

        seeall_sports.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(GamesMainActivity.this, DynamicActivity.class);
                intent.putExtra("domain", "Sports");
                intent.putExtra("module_nm", "Sports Games");
                startActivity(intent);
            }
        });
        seeall_arcade.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(GamesMainActivity.this, DynamicActivity.class);
                intent.putExtra("domain", "Arcade");
                intent.putExtra("module_nm", "Arcade Games");
                startActivity(intent);
            }
        });
        seeall_mind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(GamesMainActivity.this, DynamicActivity.class);
                intent.putExtra("domain", "Logic");
                intent.putExtra("module_nm", "Mind Games");
                startActivity(intent);
            }
        });
        seeall_strategy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(GamesMainActivity.this, DynamicActivity.class);
                intent.putExtra("domain", "Strategy");
                intent.putExtra("module_nm", "Strategy Games");
                startActivity(intent);
            }
        });
    }

    // start right corner icons
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.gamesmenu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        if(id == R.id.search){
            Intent i = new Intent(GamesMainActivity.this, SearchActivity.class);
            i.putExtra("category","game");
            startActivity(i);
        } else if(id == R.id.shoppingcart){
            Intent intent = new Intent(GamesMainActivity.this, VisitShop.class);
            intent.putExtra("source","activate");
            intent.putExtra("url", "https://ad.admitad.com/g/nl4tydjde8d76c048b76e302f057c0/?subid=hers&subid1=hers1");
            intent.putExtra("imgurl", "https://www.gsered.com/charity/logo/Gamesdeal.jpg");
            intent.putExtra("companyname", "GamesDeal");
            intent.putExtra("probability", "78");
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }
    // end right cornor icons

    private void simple4() {
        sportsModals = new ArrayList<>();

        sportsModals.add(new SportsModal("https://static.gamezop.com/4y6efOyf-5g/brick.png","https://www.gamezop.com/g/4y6efOyf-5g?id=pULbmJBC7","Bouncing Beasts", "https://static.gamezop.com/4y6efOyf-5g/thumb.png", "Bouncing Beasts", true));
        sportsModals.add(new SportsModal("https://static.gamezop.com/HJ-72IFXyg/brick.png","https://www.gamezop.com/g/HJ-72IFXyg?id=pULbmJBC7","Stay On The Road", "https://static.gamezop.com/HJ-72IFXyg/thumb.png", "Stay On The Road", true));
        sportsModals.add(new SportsModal("https://static.gamezop.com/NJ3xGOyfb5l/brick.png","https://www.gamezop.com/g/NJ3xGOyfb5l?id=pULbmJBC7","Tricky Trip", "https://static.gamezop.com/NJ3xGOyfb5l/thumb.png", "Tricky Trip", true));
        sportsModals.add(new SportsModal("https://static.gamezop.com/HkE7nLFQkx/brick.png","https://www.gamezop.com/g/HkE7nLFQkx?id=pULbmJBC7","Focus Locus", "https://static.gamezop.com/HkE7nLFQkx/thumb.png", "Focus Locus", true));

        recyclerView3 = (RecyclerView) findViewById(R.id.category3);
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(this, 2);
        recyclerView3.setLayoutManager(mLayoutManager);
        recyclerView3.setItemAnimator(new DefaultItemAnimator());
        SportsAdapter rewardAdapter = new SportsAdapter(sportsModals, this);
        recyclerView3.setAdapter(rewardAdapter);
    }

    private void simple3() {
        categoryModals = new ArrayList<>();

        categoryModals.add(new CategoryModals("https://static.gamezop.com/HkBWwMUFOye/brick.png","https://www.gamezop.com/g/HkBWwMUFOye?id=pULbmJBC7","Terra Infirma", "https://static.gamezop.com/HkBWwMUFOye/thumb.png"));
        categoryModals.add(new CategoryModals("https://static.gamezop.com/S1kGWUim8Ux/brick.png","https://www.gamezop.com/g/r10-NLT86bx?id=pULbmJBC7","Rope Ninja", "https://static.gamezop.com/r10-NLT86bx/thumb.png"));
        categoryModals.add(new CategoryModals("https://static.gamezop.com/H1zbZUsmULl/brick.png","https://www.gamezop.com/g/H1zbZUsmULl?id=pULbmJBC7","Shark Dash", "https://static.gamezop.com/H1zbZUsmULl/thumb.png"));
        categoryModals.add(new CategoryModals("https://static.gamezop.com/HkwbZUjm88x/brick.png","https://www.gamezop.com/g/HkwbZUjm88x?id=pULbmJBC7","Barbarian Venture", "https://static.gamezop.com/HkwbZUjm88x/thumb.png"));

        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        RecyclerView recyclerView = findViewById(R.id.categorywithouttitle2);
        recyclerView.setLayoutManager(layoutManager);
        CategoryAdapter adapter = new CategoryAdapter( this, categoryModals);
        recyclerView.setAdapter(adapter);
    }

    private void sports() {
        sportsModals = new ArrayList<>();

        sportsModals.add(new SportsModal("https://static.gamezop.com/HJKWbUj788l/brick.png","https://www.gamezop.com/g/HJKWbUj788l?id=pULbmJBC7","Clay Pigeon", "https://static.gamezop.com/HJKWbUj788l/thumb.png", "Clay Pigeon", true));
        sportsModals.add(new SportsModal("https://static.gamezop.com/BJ8Wb8j7ILx/brick.png","https://www.gamezop.com/g/BJ8Wb8j7ILx?id=pULbmJBC7","Soccer Jerks", "https://static.gamezop.com/BJ8Wb8j7ILx/thumb.png", "Soccer Jerks", true));
        sportsModals.add(new SportsModal("https://static.gamezop.com/HkS-bUoXUIx/brick.png","https://www.gamezop.com/g/HkS-bUoXUIx?id=pULbmJBC7","Boxing Club", "https://static.gamezop.com/HkS-bUoXUIx/thumb.png", "Boxing Club", true));
        sportsModals.add(new SportsModal("https://static.gamezop.com/B1H5NfCXa/brick.png","https://www.gamezop.com/g/B1H5NfCXa?id=pULbmJBC7","Homerun Hit", "https://static.gamezop.com/B1H5NfCXa/thumb.png", "Homerun Hit", true));

        recyclerView2 = (RecyclerView) findViewById(R.id.category2);
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(this, 2);
        recyclerView2.setLayoutManager(mLayoutManager);
        recyclerView2.setItemAnimator(new DefaultItemAnimator());
        SportsAdapter rewardAdapter = new SportsAdapter(sportsModals, this);
        recyclerView2.setAdapter(rewardAdapter);
    }

    private void simpleslider() {
        categoryModals = new ArrayList<>();

        categoryModals.add(new CategoryModals("https://static.gamezop.com/SyO94GA7p/brick.png","https://www.gamezop.com/g/SyO94GA7p?id=pULbmJBC7","Super Goalie Auditions", "https://static.gamezop.com/SyO94GA7p/thumb.png"));
        categoryModals.add(new CategoryModals("https://static.gamezop.com/ry6bwfUt_Jg/brick.png","https://www.gamezop.com/g/ry6bwfUt_Jg?id=pULbmJBC7","Savage Revenge", "https://static.gamezop.com/ry6bwfUt_Jg/thumb.png"));
        categoryModals.add(new CategoryModals("https://static.gamezop.com/rJxVaMkP91Q/brick.png","https://www.gamezop.com/g/rJxVaMkP91Q?id=pULbmJBC7","Cricket Mastermind 2019", "https://static.gamezop.com/rJxVaMkP91Q/thumb.png"));
        categoryModals.add(new CategoryModals("https://static.gamezop.com/rkwCYBZuV/brick.png","https://www.gamezop.com/g/rkwCYBZuV?id=pULbmJBC7","Furious Speed", "https://static.gamezop.com/rkwCYBZuV/thumb.png"));
        categoryModals.add(new CategoryModals("https://static.gamezop.com/B1hCYSbdN/brick.png","https://www.gamezop.com/g/B1hCYSbdN?id=pULbmJBC7","Let's Go Fishing", "https://static.gamezop.com/B1hCYSbdN/thumb.png"));
        categoryModals.add(new CategoryModals("https://static.gamezop.com/HJXei0j/brick.png","https://www.gamezop.com/g/HJXei0j?id=pULbmJBC7","Cyberfusion", "https://static.gamezop.com/HJXei0j/thumb.png"));

        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        RecyclerView recyclerView = findViewById(R.id.categorywithouttitle1);
        recyclerView.setLayoutManager(layoutManager);
        CategoryAdapter adapter = new CategoryAdapter( this, categoryModals);
        recyclerView.setAdapter(adapter);

    }

    private void simple2() {
        sportsModals = new ArrayList<>();

        sportsModals.add(new SportsModal("https://static.gamezop.com/B1jZWUoXUIe/brick.png","https://www.gamezop.com/g/B1jZWUoXUIe?id=pULbmJBC7","Valley of Terror", "https://static.gamezop.com/B1jZWUoXUIe/thumb.png", "Valley of Terror", true));
        sportsModals.add(new SportsModal("https://static.gamezop.com/S1kGWUim8Ux/brick.png","https://www.gamezop.com/g/S1kGWUim8Ux?id=pULbmJBC7","Shadow Run", "https://static.gamezop.com/S1kGWUim8Ux/thumb.png", "Shadow Run", true));
        sportsModals.add(new SportsModal("https://static.gamezop.com/rkxMV8TI6Wg/brick.png","https://www.gamezop.com/g/rkxMV8TI6Wg?id=pULbmJBC7","Zombies Can't Jump 2", "https://static.gamezop.com/rkxMV8TI6Wg/thumb.png", "Zombies Can't Jump 2", true));
        sportsModals.add(new SportsModal("https://static.gamezop.com/rJXlRtBWd4/brick.png","https://www.gamezop.com/g/rJXlRtBWd4?id=pULbmJBC7","Pumpkin Smasher", "https://static.gamezop.com/rJXlRtBWd4/thumb.png", "Pumpkin Smasher", true));

        recyclerView1 = (RecyclerView) findViewById(R.id.category1);
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(this, 2);
        recyclerView1.setLayoutManager(mLayoutManager);
        recyclerView1.setItemAnimator(new DefaultItemAnimator());
        SportsAdapter rewardAdapter = new SportsAdapter(sportsModals, this);
        recyclerView1.setAdapter(rewardAdapter);
    }

    private void strategy() {
        newslist = new ArrayList<>();

        newslist.add(new newslistmodal("https://static.gamezop.com/HJXei0j/thumb.png","Cyberfusion","Numbers merge to make higher numbers, stars blast when merged. The best match-3 HTML5 game!","https://www.gamezop.com/g/HJXei0j?id=pULbmJBC7", true));
        newslist.add(new newslistmodal("https://static.gamezop.com/HJE-oa2z_l-/thumb.png","Mirror Me","Mirror Me is a relaxing puzzle game that exercises your brain. You have to reflect symmetric shapes for the patterns shown.","https://www.gamezop.com/g/HJE-oa2z_l-?id=pULbmJBC7", true));
        newslist.add(new newslistmodal("https://static.gamezop.com/SJX7TGkDq1X/thumb.png","Pop Soap","Classic Match-3 game in Endless Mode and an Achievements mode! Remember, if there's 2 or more of the same kind, you can burst them!","https://www.gamezop.com/g/SJX7TGkDq1X?id=pULbmJBC7", true));
        newslist.add(new newslistmodal("https://static.gamezop.com/HyBw2v2-F/thumb.png","Attention Span","The goal of the game is to tell apart your colors from your letters... very fast. Don't get confused.","https://www.gamezop.com/g/HyBw2v2-F?id=pULbmJBC7", true));

        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        RecyclerView recyclerView = findViewById(R.id.strategylist);
        recyclerView.setLayoutManager(layoutManager);
        NewsListAdapter adapter = new NewsListAdapter(this, newslist);
        recyclerView.setAdapter(adapter);
    }

    private void mindgames() {
        newslist = new ArrayList<>();

        newslist.add(new newslistmodal("https://static.gamezop.com/HkxcskEs5/thumb.png","Loco Motive","Collect as many treasures as you can hold while avoiding barriers and dead-end rail tracks.","https://www.gamezop.com/g/HkxcskEs5?id=pULbmJBC7", true));
        newslist.add(new newslistmodal("https://static.gamezop.com/HyZMUTVQRe/thumb.png","Where's the Ace?","Don't let the lady out of sight, or you'll lose the game. How attentive are you?","https://www.gamezop.com/g/HyZMUTVQRe?id=pULbmJBC7", true));
        newslist.add(new newslistmodal("https://static.gamezop.com/BJlg94zA76/thumb.png","Zigzag Clash","Colourful and modern snake game with snazzier enemies and much cooler movements!","https://www.gamezop.com/g/BJlg94zA76?id=pULbmJBC7", true));
        newslist.add(new newslistmodal("https://static.gamezop.com/rJsWV8aIa-l/thumb.png","Jelly Doods","Combine jelly shapes of the same color by sliding the jellies together. Use fewest possible moves.","https://www.gamezop.com/g/rJsWV8aIa-l?id=pULbmJBC7", true));

        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        RecyclerView recyclerView = findViewById(R.id.mindgamelist);
        recyclerView.setLayoutManager(layoutManager);
        NewsListAdapter adapter = new NewsListAdapter(this, newslist);
        recyclerView.setAdapter(adapter);

    }

    private void highestearningslider() {
        recommendedModals = new ArrayList<>();

        recommendedModals.add(new RecommendedModals("https://www.gamezop.com/g/SykGDfUKOkg?id=pULbmJBC7","Traffic Command","https://static.gamezop.com/SykGDfUKOkg/thumb.png"));
        recommendedModals.add(new RecommendedModals("https://www.gamezop.com/g/H1pbZUoXIUl?id=pULbmJBC7","Save Your Pinky","https://static.gamezop.com/H1pbZUoXIUl/thumb.png"));
        recommendedModals.add(new RecommendedModals("https://www.gamezop.com/g/BJ9bvzIKdJl?id=pULbmJBC7","Brick Plunge","https://static.gamezop.com/BJ9bvzIKdJl/thumb.png"));
        recommendedModals.add(new RecommendedModals("https://www.gamezop.com/g/SyN0KSWuV?id=pULbmJBC7","Crazy Pizza","https://static.gamezop.com/SyN0KSWuV/thumb.png"));
        recommendedModals.add(new RecommendedModals("https://www.gamezop.com/g/B1H5NfCXa?id=pULbmJBC7","Homerun Hit","https://static.gamezop.com/B1H5NfCXa/thumb.png"));
        recommendedModals.add(new RecommendedModals("https://www.gamezop.com/g/SJXbW8smUUx?id=pULbmJBC7","Quack Hunt","https://static.gamezop.com/SJXbW8smUUx/thumb.png"));
        recommendedModals.add(new RecommendedModals("https://www.gamezop.com/g/SJVxAtrW_N?id=pULbmJBC7","Rapunzel Tower","https://static.gamezop.com/SJVxAtrW_N/thumb.png"));

        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        RecyclerView recyclerView = findViewById(R.id.highestearningslider);
        recyclerView.setLayoutManager(layoutManager);
        RecommendedAdapter adapter = new RecommendedAdapter(recommendedModals, this);
        recyclerView.setAdapter(adapter);
    }

    private void recommendslider() {
        recommendedModals = new ArrayList<>();

        recommendedModals.add(new RecommendedModals("https://www.gamezop.com/g/BJAqNMC7T?id=pULbmJBC7","Alfy","https://static.gamezop.com/BJAqNMC7T/thumb.png"));
        recommendedModals.add(new RecommendedModals("https://www.gamezop.com/g/Bk2-DzLK_yg?id=pULbmJBC7","Zombocalypse","https://static.gamezop.com/Bk2-DzLK_yg/thumb.png"));
        recommendedModals.add(new RecommendedModals("https://www.gamezop.com/g/41FZfdyG-5x?id=pULbmJBC7","1212!","https://static.gamezop.com/41FZfdyG-5x/thumb.png"));
        recommendedModals.add(new RecommendedModals("https://www.gamezop.com/g/SyN0KSWuV?id=pULbmJBC7","Crazy Pizza","https://static.gamezop.com/SyN0KSWuV/thumb.png"));
        recommendedModals.add(new RecommendedModals("https://www.gamezop.com/g/BkqTS_1b?id=pULbmJBC7","Oh No","https://static.gamezop.com/BkqTS_1b/thumb.png"));
        recommendedModals.add(new RecommendedModals("https://www.gamezop.com/g/BJ8Wb8j7ILx?id=pULbmJBC7","Soccer Jerks","https://static.gamezop.com/BJ8Wb8j7ILx/thumb.png"));
        recommendedModals.add(new RecommendedModals("https://www.gamezop.com/g/BJ5-b8jmLIx?id=pULbmJBC7","Game of Tiles","https://static.gamezop.com/BJ5-b8jmLIx/thumb.png"));
        recommendedModals.add(new RecommendedModals("https://www.gamezop.com/g/HJP4afkvqJQ?id=pULbmJBC7","City Cricket Battles","https://static.gamezop.com/HJP4afkvqJQ/thumb.png"));

        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        RecyclerView recyclerView = findViewById(R.id.companyslider);
        recyclerView.setLayoutManager(layoutManager);
        RecommendedAdapter adapter = new RecommendedAdapter(recommendedModals,this);
        recyclerView.setAdapter(adapter);
    }

    private void imageslider() {
        //Carousel view starts
        title = new ArrayList<String>();
        image = new ArrayList<String>();
        weblink = new ArrayList<String>();

        Hash_file_maps = new HashMap<String, String>();

        sliderLayout = (SliderLayout)findViewById(R.id.carouselView);
        if (Hash_file_maps != null) {
            Hash_file_maps.clear();
        }

        Hash_file_maps.put("1", "https://www.gsered.com/intern/grp10/GSE_App/images/games/imageslider/1.png");
        Hash_file_maps.put("2", "https://static.gamezop.com/HJP4afkvqJQ/game-1.png");
        Hash_file_maps.put("3", "https://www.gsered.com/intern/grp10/GSE_App/images/games/imageslider/2.png");
        Hash_file_maps.put("4", "https://static.gamezop.com/SkhljT2fdgb/wall.png");
        Hash_file_maps.put("5", "https://www.gsered.com/intern/grp10/GSE_App/images/games/imageslider/3.png");
        Hash_file_maps.put("6", "https://static.gamezop.com/BJuxCtrWdN/wall.png");

        for(int i=1; i<=6 ; i++){
            DefaultSliderView defaultSliderView = new DefaultSliderView(GamesMainActivity.this);
            defaultSliderView .image(Hash_file_maps.get(String.valueOf(i)))
                    .setScaleType(BaseSliderView.ScaleType.Fit)
                    .setOnSliderClickListener(this);
            sliderLayout.addSlider(defaultSliderView);

        }
        sliderLayout.setPresetTransformer(SliderLayout.Transformer.Accordion);
        sliderLayout.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
        sliderLayout.setCustomAnimation(new DescriptionAnimation());
        sliderLayout.setDuration(3000);
        sliderLayout.addOnPageChangeListener(this);

        //Carousel view ends

    }

    @Override
    public void onSliderClick(BaseSliderView slider) {
        int position=sliderLayout.getCurrentPosition();

        String url = null,imgurl = null,companyname = null, prob = null;
        switch(position)
        {
            case 0:
                url = "https://www.youtube.com/watch?v=NhR2aXGkS7Y";
                Intent i = new Intent(GamesMainActivity.this, OpenUrl.class);
                i.putExtra("url", url);
                startActivity(i);
                break;
            case 1:
                url = "https://www.gamezop.com/g/HJP4afkvqJQ?id=pULbmJBC7";
                imgurl = "https://static.gamezop.com/HJP4afkvqJQ/thumb.png";
                companyname = "City Cricket Battles";
                prob = "84";
                break;
            case 2:
                Intent in = new Intent(GamesMainActivity.this, InviteActivity.class);
                startActivity(in);
                break;
            case 3:
                url = "https://www.gamezop.com/g/SkhljT2fdgb?id=pULbmJBC7";
                imgurl = "https://static.gamezop.com/SkhljT2fdgb/thumb.png";
                companyname = "Ludo";
                prob = "78";
                break;
            case 4:
                Intent intent = new Intent(GamesMainActivity.this, ScratchGift.class);
                intent.putExtra("index", "1");
                startActivity(intent);
                break;
            case 5:
                url = "https://www.gamezop.com/g/BJuxCtrWdN?id=pULbmJBC7";
                imgurl = "https://static.gamezop.com/BJuxCtrWdN/thumb.png";
                companyname = "Skater Dude";
                prob = "90";
                break;
        }

        if(imgurl != null){
            Intent intent = new Intent(GamesMainActivity.this, VisitShop.class);
            intent.putExtra("source","activate");
            intent.putExtra("url",url);
            intent.putExtra("imgurl",imgurl);
            intent.putExtra("companyname",companyname);
            intent.putExtra("probability",prob);
            startActivity(intent);
        }
    }

    private void topslider() {

        topSliderModals.add(new TopSliderModal("Strategy" , R.drawable.strategy , settopsliderintent("Strategy", "Strategy Games") ));
        topSliderModals.add(new TopSliderModal("Action" , R.drawable.action , settopsliderintent("Action", "Action Games") ));
        topSliderModals.add(new TopSliderModal("Mindgames" , R.drawable.mindgames , settopsliderintent("Puzzle", "Mind Games") ));
        topSliderModals.add(new TopSliderModal("Racing" , R.drawable.racing , settopsliderintent("Racing", "Racing Games") ));
        topSliderModals.add(new TopSliderModal("Adventure" , R.drawable.adventure , settopsliderintent("Adventure", "Adventure Games") ));
        topSliderModals.add(new TopSliderModal("Arcade" , R.drawable.arcade , settopsliderintent("Arcade", "Arcade Games") ));
        topSliderModals.add(new TopSliderModal("PS4" , R.drawable.ps4 , settopsliderintent("Games", "PS4 Games") ));

        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        RecyclerView recyclerView = findViewById(R.id.topslider);
        recyclerView.setLayoutManager(layoutManager);
        topSliderAdapter = new TopSliderAdapter(topSliderModals,this);
        recyclerView.setAdapter(topSliderAdapter);
    }

    private Intent settopsliderintent(String category, String title) {
        Intent soc = new Intent(GamesMainActivity.this, DynamicActivity.class);
        soc.putExtra("domain", category);
        soc.putExtra("module_nm", title);
        return soc;
    }

    // start image slider related
    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
    }

    @Override
    public void onPageSelected(int position) {
    }

    @Override
    public void onPageScrollStateChanged(int state) {
    }
    // end image slider related
}
