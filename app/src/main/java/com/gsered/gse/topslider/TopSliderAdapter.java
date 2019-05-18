package com.gsered.gse.topslider;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.gsered.gse.R;

import java.util.List;

public class TopSliderAdapter extends RecyclerView.Adapter<TopSliderAdapter.ViewHolder>{

    private List<TopSliderModal> data;
    private Context ctx;

    public TopSliderAdapter(List<TopSliderModal> data, Context ctx) {
        this.data = data;
        this.ctx = ctx;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.topslideritem, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {

        holder.company_logo.setImageResource(data.get(position).getImage());
        holder.company_name.setText(data.get(position).getCategory());
        holder.category.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = data.get(position).getUrl();
                ctx.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView company_logo;
        TextView company_name;
        RelativeLayout category;

        public ViewHolder(View itemView) {
            super(itemView);

            company_logo = itemView.findViewById(R.id.image);
            company_name = itemView.findViewById(R.id.text);
            category = itemView.findViewById(R.id.item);
        }
    }

}
