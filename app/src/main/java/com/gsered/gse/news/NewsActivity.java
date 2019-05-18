package com.gsered.gse.news;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;

import com.gsered.gse.R;
import com.huxq17.swipecardsview.SwipeCardsView;

import java.util.ArrayList;
import java.util.List;

public class NewsActivity extends AppCompatActivity {

    private SwipeCardsView swipeCardsView;
    private List<modal> newslist = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);

        swipeCardsView = (SwipeCardsView) findViewById(R.id.swipenews);
        swipeCardsView.retainLastCard(false);
        swipeCardsView.enableSwipe(true);
        getnews();
    }

    private void getnews() {
        newslist.add(new modal("https://www.thehindu.com/news/international/34wbws/article26722037.ece/ALTERNATES/FREE_960/ALGERIABOUTEFLIKA","The rise and fall of Algerian President Abdelaziz Bouteflika who controlled the country with tight grip.","After weeks of resisting popular demands, Algerian President Abdelaziz Bouteflika, who controlled the country with a tight grip and with support from the military and the intelligence establishment for 20 years",""));
        newslist.add(new modal("https://www.thehindu.com/todays-paper/tp-national/article16712818.ece/ALTERNATES/FREE_960/th-28-rolling-s+GK2TTSUH.3.jpg.jpg","As a result of the treatment and recovery period, the Rolling Stones postponed the North American le","According to the Rolling Stone magazine, the 75-year-old musician will have the surgery to replace a valve in his heart.As a result of the treatment and recovery period, the Rolling Stones postponed the North American leg of their ‘No Filter’ tour, ",""));
        newslist.add(new modal("https://www.thehindu.com/news/international/34wbws/article26722037.ece/ALTERNATES/FREE_960/ALGERIABOUTEFLIKA","The rise and fall of Algerian President Abdelaziz Bouteflika who controlled the country with tight grip.","After weeks of resisting popular demands, Algerian President Abdelaziz Bouteflika, who controlled the country with a tight grip and with support from the military and the intelligence establishment for 20 years",""));
        newslist.add(new modal("https://www.thehindu.com/todays-paper/tp-national/article16712818.ece/ALTERNATES/FREE_960/th-28-rolling-s+GK2TTSUH.3.jpg.jpg","As a result of the treatment and recovery period, the Rolling Stones postponed the North American le","According to the Rolling Stone magazine, the 75-year-old musician will have the surgery to replace a valve in his heart.As a result of the treatment and recovery period, the Rolling Stones postponed the North American leg of their ‘No Filter’ tour, ",""));

        CardAdapter newsadapter = new CardAdapter(newslist, this);
        swipeCardsView.setAdapter(newsadapter);
    }
}
