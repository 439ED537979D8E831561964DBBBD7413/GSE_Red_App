package com.gsered.gse.games.recommended;

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
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.gsered.gse.R;
import com.gsered.gse.VisitShop;

import org.w3c.dom.Text;

import java.util.List;
import java.util.Random;

public class RecommendedAdapter extends RecyclerView.Adapter<RecommendedAdapter.ViewHolder>  {

    List<RecommendedModals> data;
    Context context;

    public RecommendedAdapter(List<RecommendedModals> data, Context context) {
        this.data = data;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.recommended_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {

        holder.title.setText(data.get(position).getGamename());
        Glide.with(context)
                .load(data.get(position).getLogo())
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
                intent.putExtra("url", data.get(position).getUrl());
                intent.putExtra("imgurl", data.get(position).getLogo());
                intent.putExtra("companyname", data.get(position).getGamename());
                intent.putExtra("probability", String.valueOf(new Random().nextInt(37)+60));
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView image;
        TextView title;
        RelativeLayout game;

        public ViewHolder(View itemView) {
            super(itemView);

            game = (RelativeLayout) itemView.findViewById(R.id.game);
            title = (TextView) itemView.findViewById(R.id.slider_image_name);
            image = itemView.findViewById(R.id.slider_image);
        }
    }

}
