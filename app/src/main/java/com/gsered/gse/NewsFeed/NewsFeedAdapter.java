package com.gsered.gse.NewsFeed;

import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.support.customtabs.CustomTabsIntent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.gsered.gse.R;

import java.util.List;

public class NewsFeedAdapter extends RecyclerView.Adapter<NewsFeedAdapter.CompViewHolder> {

    private Context context;
    private List<NewsList> newsList;
    private NewsItemClickListener listener2;

    @Override
    public int getItemCount() {
        return newsList.size();
    }

    public class CompViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public TextView headline;
        public TextView compName;
        public ImageView thumbnail;
        private NewsItemClickListener listener;

        public CompViewHolder(View view, NewsItemClickListener listener){
            super(view);
            headline = (TextView) view.findViewById(R.id.newsfeed_text);
            compName = (TextView) view.findViewById(R.id.newsfeed_company);
            thumbnail = (ImageView) view.findViewById(R.id.newsfeed_thumbnail);
            this.listener = listener;
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            listener.onItemClick(view, getAdapterPosition());
            String url = newsList.get(getAdapterPosition()).getRedUrl();
            CustomTabsIntent.Builder intentBuilder = new CustomTabsIntent.Builder();
            CustomTabsIntent customTabsIntent = intentBuilder.build();
            intentBuilder.setToolbarColor(Color.parseColor("#f44336"));
            customTabsIntent.launchUrl(view.getContext(), Uri.parse(url));
        }
    }

    public NewsFeedAdapter(Context context, List<NewsList> newsList, NewsItemClickListener listener){
        this.context = context;
        this.newsList = newsList;
        this.listener2 = listener;
    }

    @Override
    public CompViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_newsfeed, parent, false);

        return new CompViewHolder(itemView, listener2);
    }

    @Override
    public void onBindViewHolder(final CompViewHolder holder, int position) {
        NewsList news = newsList.get(position);
        holder.compName.setText(news.getCompName());
        holder.headline.setText(news.getHeadline());
        
        Glide.with(context).load(news.getThumb_url()).centerCrop().placeholder(R.mipmap.ic_launcher).into(holder.thumbnail);
    }


}
