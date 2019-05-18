package com.gsered.gse.dashboard;

import android.content.Context;
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

public class TransactionAdapter extends RecyclerView.Adapter<TransactionAdapter.ViewHolder> {

    private Context context;
    private List<TransactionModal> transactionlist;

    public TransactionAdapter(Context context, List<TransactionModal> transactionlist) {
        this.context = context;
        this.transactionlist = transactionlist;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.transaction_item, parent, false);
        return new TransactionAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.title.setText(transactionlist.get(position).getTitle());
        holder.amount.setText(transactionlist.get(position).getAmount());
        holder.date.setText(transactionlist.get(position).getDate());
        holder.type.setText(transactionlist.get(position).getType());

        if(transactionlist.get(position).getStatus().contentEquals("accepted"))
            holder.image.setImageResource(R.drawable.approved);
        else if(transactionlist.get(position).getStatus().contentEquals("onhold"))
            holder.image.setImageResource(R.drawable.onhold);
    }

    @Override
    public int getItemCount() {
        return transactionlist.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView title,amount,date,type;
        ImageView image;

        public ViewHolder(View itemView) {
            super(itemView);

            title = (TextView) itemView.findViewById(R.id.transactiontitle);
            amount = (TextView) itemView.findViewById(R.id.transactionamount);
            date = (TextView) itemView.findViewById(R.id.transactiondate);
            type = (TextView) itemView.findViewById(R.id.transactiontype);
            image = (ImageView) itemView.findViewById(R.id.transactionimage);
        }
    }

}
