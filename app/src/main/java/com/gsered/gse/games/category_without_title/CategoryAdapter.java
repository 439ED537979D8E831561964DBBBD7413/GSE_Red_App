package com.gsered.gse.games.category_without_title;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.gsered.gse.R;
import com.gsered.gse.VisitShop;

import java.util.List;
import java.util.Random;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ViewHolder> {

    private Context context;
    List<CategoryModals> category;

    public CategoryAdapter(Context context, List<CategoryModals> category) {
        this.context = context;
        this.category = category;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.games_category_withouttitle, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        holder.image.setClipToOutline(true);
        Glide.with(context)
                .load(category.get(position).getImageurl())
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
                intent.putExtra("url", category.get(position).getUrl());
                intent.putExtra("imgurl", category.get(position).getLogo());
                intent.putExtra("companyname", category.get(position).getGamename());
                intent.putExtra("probability", String.valueOf(new Random().nextInt(37)+60));
                context.startActivity(intent);

            }
        });
    }

    @Override
    public int getItemCount() {
        return category.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView image;
        RelativeLayout game;

        public ViewHolder(View itemView) {
            super(itemView);

            game = (RelativeLayout) itemView.findViewById(R.id.game);
            image = itemView.findViewById(R.id.slider_image);
        }
    }
}
