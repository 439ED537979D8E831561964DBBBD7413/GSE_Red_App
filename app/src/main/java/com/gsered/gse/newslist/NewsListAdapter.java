package com.gsered.gse.newslist;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.customtabs.CustomTabsIntent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.gsered.gse.MainActivity;
import com.gsered.gse.OpenUrl;
import com.gsered.gse.R;
import com.gsered.gse.VisitShop;
import com.gsered.gse.horizontalsliderAdapter;

import org.w3c.dom.Text;

import java.util.List;
import java.util.Random;

public class NewsListAdapter extends RecyclerView.Adapter<NewsListAdapter.ViewHolder> {

    private Context context;
    private List<newslistmodal> newslist;

    public NewsListAdapter(Context context, List<newslistmodal> newslist) {
        this.context = context;
        this.newslist = newslist;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.mainpage_newsitem, parent, false);
        return new NewsListAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        holder.title.setText(newslist.get(position).getTitle());
        holder.byline.setText(newslist.get(position).getByline());
        Glide.with(context).load(newslist.get(position).getImageurl()).placeholder(R.mipmap.ic_launcher).into(holder.imageView);
        final String url = newslist.get(position).getLink();

        if(newslist.get(position).getShowplay()) {
            holder.play.setVisibility(View.VISIBLE);
            holder.play.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, VisitShop.class);
                    intent.putExtra("source","activate");
                    intent.putExtra("url", newslist.get(position).getLink());
                    intent.putExtra("imgurl", newslist.get(position).getImageurl());
                    intent.putExtra("companyname", newslist.get(position).getTitle());
                    intent.putExtra("probability", String.valueOf(new Random().nextInt(37)+60));
                    context.startActivity(intent);

                }
            });
        }
        else {
            holder.newsstrip.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i = new Intent(context, OpenUrl.class);
                    i.putExtra("url", url);
                    context.startActivity(i);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return newslist.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        RelativeLayout newsstrip;
        ImageView imageView;
        TextView title,byline;
        TextView play;

        public ViewHolder(View itemView) {
            super(itemView);

            newsstrip = (RelativeLayout) itemView.findViewById(R.id.newsstrip);
            imageView = (ImageView) itemView.findViewById(R.id.newsimage);
            title = (TextView) itemView.findViewById(R.id.newstitle);
            byline = (TextView) itemView.findViewById(R.id.newsbyline);
            play = (TextView) itemView.findViewById(R.id.playbutton);
        }
    }
}