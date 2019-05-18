package com.gsered.gse.news;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.gsered.gse.R;
import com.huxq17.swipecardsview.BaseCardAdapter;

import org.w3c.dom.Text;

import java.util.List;

public class CardAdapter extends BaseCardAdapter {

    private List<modal> newslist;
    private Context ctx;

    public CardAdapter(List<modal> newslist, Context ctx) {
        this.newslist = newslist;
        this.ctx = ctx;
    }

    @Override
    public int getCount() {
        return newslist.size();
    }

    @Override
    public int getCardLayoutId() {
        return R.layout.news_item;
    }

    @Override
    public void onBindData(int position, View cardview) {
        if(newslist == null || newslist.size() == 0) {
            return;
        }

        ImageView image = (ImageView) cardview.findViewById(R.id.newsimage);
        TextView title = (TextView) cardview.findViewById(R.id.newstitle);
        TextView description = (TextView) cardview.findViewById(R.id.newsdescription);

        modal modalposition = newslist.get(position);
        title.setText(modalposition.getTitle());
        description.setText(modalposition.getDescription());
        Glide.with(ctx).load(modalposition.getImage()).placeholder(R.mipmap.ic_launcher).into(image);
    }
}
