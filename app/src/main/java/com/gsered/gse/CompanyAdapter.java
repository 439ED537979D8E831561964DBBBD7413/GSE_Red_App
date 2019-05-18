package com.gsered.gse;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.support.customtabs.CustomTabsIntent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.gsered.gse.modals.Company;

import java.util.List;

public class CompanyAdapter extends RecyclerView.Adapter<CompanyAdapter.CompViewHolder> {

    private Context context;
    private List<Company> companyList;
    private CompItemClickListener listener2;

    @Override
    public int getItemCount() {
        return companyList.size();
    }

    public class CompViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public TextView compName;
        public ImageView compLogo,companydonate,companycoupon;
        private CompItemClickListener listener;

        public CompViewHolder(View view, CompItemClickListener listener){
            super(view);
            compName = (TextView) view.findViewById(R.id.company_name);
            compLogo = (ImageView) view.findViewById(R.id.company_thumbnail);
            companydonate = (ImageView) view.findViewById(R.id.companydonates);
            companycoupon = (ImageView) view.findViewById(R.id.companyhascoupon);

            this.listener = listener;
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            listener.onItemClick(view, getAdapterPosition());
            Intent intent = new Intent(context, CouponsActivity.class);
            intent.putExtra("admitad_id", companyList.get(getAdapterPosition()).getAdmitad_id());
            intent.putExtra("name",companyList.get(getAdapterPosition()).getCompName());
            intent.putExtra("raise",companyList.get(getAdapterPosition()).getRaise());
            intent.putExtra("url",companyList.get(getAdapterPosition()).getUrl());
            intent.putExtra("imgurl",companyList.get(getAdapterPosition()).getImgUrl());
            intent.putExtra("probability",companyList.get(getAdapterPosition()).getProbability());
            context.startActivity(intent);

        }
    }

    public CompanyAdapter(Context context, List<Company> companyList, CompItemClickListener listener){
        this.context = context;
        this.companyList = companyList;
        this.listener2 = listener;
    }

    @Override
    public CompViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.company_card, parent, false);

        return new CompViewHolder(itemView, listener2);
    }

    @Override
    public void onBindViewHolder(final CompViewHolder holder, int position) {
        Company company = companyList.get(position);
        if( company.getActivate().contentEquals("no"))
            holder.companydonate.setVisibility(View.GONE);
        else
            holder.companydonate.setVisibility(View.VISIBLE);

        if( company.getCoupon().contentEquals("no"))
            holder.companycoupon.setVisibility(View.GONE);
        else
            holder.companycoupon.setVisibility(View.VISIBLE);

        holder.compName.setText(company.getCompName());
        Glide.with(context).load(company.getImgUrl()).placeholder(R.mipmap.ic_launcher).into(holder.compLogo);
    }
}
