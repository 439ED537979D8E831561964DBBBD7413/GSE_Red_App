package com.gsered.gse;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.customtabs.CustomTabsIntent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.gsered.gse.search.SearchActivity;

import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;

public class horizontalsliderAdapter extends RecyclerView.Adapter<horizontalsliderAdapter.ViewHolder> {

    private static final String TAG = "horizontalsliderAdapter";

    private ArrayList<String> mNames = new ArrayList<>();
    private ArrayList<String> mAdmitadid = new ArrayList<>();
    private ArrayList<String> mUrl = new ArrayList<>();
    private ArrayList<String> mRaise = new ArrayList<>();
    private ArrayList<String> mImageUrls = new ArrayList<>();
    private ArrayList<String> mProbability = new ArrayList<>();
    private Context mContext;
    private static String APPCRED = "SIGNCRED";
    private static String NGO_ID = "NGOID";
    private static String USER_ID = "USERID";

    public horizontalsliderAdapter(ArrayList<String> mNames, ArrayList<String> mAdmitadid, ArrayList<String> mUrl, ArrayList<String> mRaise, ArrayList<String> mImageUrls, ArrayList<String> mProbability, Context mContext) {
        this.mNames = mNames;
        this.mAdmitadid = mAdmitadid;
        this.mUrl = mUrl;
        this.mRaise = mRaise;
        this.mImageUrls = mImageUrls;
        this.mProbability = mProbability;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.horizontal_slider, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final int pos = position;
        Glide.with(mContext).load(mImageUrls.get(pos)).placeholder(R.mipmap.ic_launcher).into(holder.company_logo);

        holder.company_name.setText(mNames.get(pos));
        holder.company_logo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, CouponsActivity.class);
                intent.putExtra("admitad_id", mAdmitadid.get(pos));
                intent.putExtra("name", mNames.get(pos));
                intent.putExtra("raise", mRaise.get(pos));
                intent.putExtra("url", mUrl.get(pos));
                intent.putExtra("imgurl", mImageUrls.get(pos));
                intent.putExtra("probability", mProbability.get(pos));
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mImageUrls.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView company_logo;
        TextView company_name;

        public ViewHolder(View itemView) {
            super(itemView);

            company_logo = itemView.findViewById(R.id.slider_image);
            company_name = itemView.findViewById(R.id.slider_image_name);
        }
    }
}
