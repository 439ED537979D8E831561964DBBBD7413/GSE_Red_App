package com.gsered.gse.games.sports;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.gsered.gse.R;
import com.gsered.gse.VisitShop;

import java.util.List;
import java.util.Random;

public class SportsAdapter extends RecyclerView.Adapter<SportsAdapter.MyHolder> {

    private List<SportsModal> donationmodals;
    private Context context;
    private LayoutInflater layoutInflater;

    public SportsAdapter(List<SportsModal> donationmodals, Context context) {
        this.donationmodals = donationmodals;
        this.context = context;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v;
        v = LayoutInflater.from(context).inflate(R.layout.games_category_item, parent, false);
        SportsAdapter.MyHolder holder = new SportsAdapter.MyHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull final MyHolder holder, final int position) {
        if(donationmodals.get(position).getShowtitle()) {
            holder.title.setVisibility(View.VISIBLE);
            holder.title.setText(donationmodals.get(position).getTitle());
        }

        Glide.with(context)
                .load(donationmodals.get(position).getImageurl())
                .asBitmap()
                .placeholder(R.mipmap.ic_launcher)
                .into(new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                        holder.image.setScaleType(ImageView.ScaleType.FIT_XY);
                        holder.image.setImageBitmap(resource);
                    }
                });

        holder.game.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, VisitShop.class);
                intent.putExtra("source","activate");
                intent.putExtra("url", donationmodals.get(position).getLink());
                intent.putExtra("imgurl", donationmodals.get(position).getLogo());
                intent.putExtra("companyname", donationmodals.get(position).getGamename());
                intent.putExtra("probability", String.valueOf(new Random().nextInt(37)+60));
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return donationmodals.size();
    }

    public class MyHolder extends RecyclerView.ViewHolder {
        private ImageView image;
        private TextView title;
        private RelativeLayout game;

        public MyHolder(View itemView) {
            super(itemView);

            game = (RelativeLayout) itemView.findViewById(R.id.game);
            image = (ImageView) itemView.findViewById(R.id.image);
            title = (TextView) itemView.findViewById(R.id.title);
        }
    }
}
