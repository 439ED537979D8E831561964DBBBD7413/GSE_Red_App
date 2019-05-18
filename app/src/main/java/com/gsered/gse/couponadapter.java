package com.gsered.gse;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.List;

public class couponadapter extends RecyclerView.Adapter<couponadapter.MyViewHolder> {

    public static Context ctx;
    List<coupon_modal> coupons;
    public String imageurl,cmpyname,probability;

    public couponadapter(Context ctx, List<coupon_modal> coupons, String imageurl, String cmpyname, String probability) {
        this.ctx = ctx;
        this.coupons = coupons;
        this.imageurl = imageurl;
        this.cmpyname = cmpyname;
        this.probability = probability;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v;
        v = LayoutInflater.from(ctx).inflate(R.layout.coupon_item, parent,false);
        MyViewHolder viewHolder = new MyViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        holder.cmpyname = cmpyname;
        holder.prob = probability;
        holder.title.setText(coupons.get(position).getTitle());
        holder.description.setText(coupons.get(position).getDescription());
        holder.expires.setText(coupons.get(position).getExpires());
        holder.used.setText(coupons.get(position).getUsed());
        holder.gotourl.setText(coupons.get(position).getUrl());
        holder.verified.setText(coupons.get(position).getVerified());
        holder.imgurl.setText(imageurl);

        if(coupons.get(position).getCouponcode().equalsIgnoreCase(""))
            holder.couponcode.setVisibility(View.GONE);
        else
            holder.couponcode.setText(coupons.get(position).getCouponcode());
    }

    @Override
    public int getItemCount() {
        return coupons.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView title,gotourl,imgurl;
        private TextView description,couponcode;
        private TextView expires,verified;
        private TextView used;
        private Button firecouponlink;
        String cmpyname,prob;

        public MyViewHolder(View itemView) {
            super(itemView);

            couponcode = (TextView) itemView.findViewById(R.id.coupon_code);
            title = (TextView) itemView.findViewById(R.id.coupon_title);
            description = (TextView) itemView.findViewById(R.id.coupon_description);
            expires = (TextView) itemView.findViewById(R.id.coupon_expires);
            verified = (TextView) itemView.findViewById(R.id.coupon_verified);
            used = (TextView) itemView.findViewById(R.id.coupon_used);
            gotourl = (TextView) itemView.findViewById(R.id.gotourl);
            imgurl = (TextView) itemView.findViewById(R.id.imgurl);
            firecouponlink = (Button) itemView.findViewById(R.id.get_coupon);

            firecouponlink.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String u = gotourl.getText().toString();
                    String i = imgurl.getText().toString();
                    Intent intent = new Intent(ctx, VisitShop.class);
                    intent.putExtra("source","coupon");
                    intent.putExtra("url",u);
                    intent.putExtra("imgurl",i);
                    intent.putExtra("companyname",cmpyname);
                    intent.putExtra("probability",prob);
                    ctx.startActivity(intent);
                }
            });
        }
    }
}
