package com.gsered.gse.donationslider;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.gsered.gse.R;
import com.gsered.gse.ScratchGift;
import com.gsered.gse.causemodal;

import java.util.List;

public class donationadapter extends PagerAdapter {

    private List<donationmodal> donationmodals;
    private LayoutInflater layoutInflater;
    private Context context;

    public donationadapter(List<donationmodal> donationmodals, Context context) {
        this.donationmodals = donationmodals;
        this.context = context;
    }

    @Override
    public int getCount() {
        return donationmodals.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view.equals(object);
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, final int position) {
        layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.donationslideritem, container, false);

        ImageView imageView;
        TextView title,description;
        LinearLayout offer;

        offer = view.findViewById(R.id.donationstrip);
        imageView = view.findViewById(R.id.donationimage);
        title = view.findViewById(R.id.donationtitle);
        description = view.findViewById(R.id.donationdescription);

        title.setText(donationmodals.get(position).getTitle());
        description.setText(donationmodals.get(position).getDescription());
        Glide.with(context).load(donationmodals.get(position).getUrl()).placeholder(R.mipmap.ic_launcher).into(imageView);
        container.addView(view, 0);

        offer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ScratchGift.class);
                intent.putExtra("index", String.valueOf(position));
                context.startActivity(intent);
            }
        });

        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View)object);
    }
}
