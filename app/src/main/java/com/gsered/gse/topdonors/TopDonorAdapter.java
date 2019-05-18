package com.gsered.gse.topdonors;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.gsered.gse.R;

import java.util.List;

public class TopDonorAdapter extends RecyclerView.Adapter<TopDonorAdapter.ViewHolder>{
    private Context context;
    private List<TopDonorModal> topdonorlist;

    public TopDonorAdapter(Context context, List<TopDonorModal> topdonorlist) {
        this.context = context;
        this.topdonorlist = topdonorlist;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.topdonoritem, parent, false);
        return new TopDonorAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.rank.setText(topdonorlist.get(position).getSrno());
        holder.name.setText(topdonorlist.get(position).getName());
        holder.amount.setText(topdonorlist.get(position).getAmount());
    }

    @Override
    public int getItemCount() {
        return topdonorlist.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView rank,name,amount;

        public ViewHolder(View itemView) {
            super(itemView);

            rank = (TextView) itemView.findViewById(R.id.srno);
            name = (TextView) itemView.findViewById(R.id.name);
            amount = (TextView) itemView.findViewById(R.id.amount);
        }
    }

}
