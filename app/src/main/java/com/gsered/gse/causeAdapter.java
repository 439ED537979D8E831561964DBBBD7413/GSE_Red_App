package com.gsered.gse;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

public class causeAdapter extends PagerAdapter {

    private List<causemodal> causemodals;
    private LayoutInflater layoutInflater;
    private Context context;
    URL url = null;
    Bitmap bmp = null;

    public causeAdapter(List<causemodal> causemodals, Context context) {
        this.causemodals = causemodals;
        this.context = context;
    }

    @Override
    public int getCount() {
        return causemodals.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view.equals(object);
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, final int position) {
        layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.causeitem, container, false);

        ImageView imageView;
        TextView title,desc,cmpy,seedetails;

        imageView = view.findViewById(R.id.causeimage);
//        title = view.findViewById(R.id.causetitle);
//        desc = view.findViewById(R.id.causedescription);
//        cmpy = view.findViewById(R.id.companyname);
//        seedetails = view.findViewById(R.id.seemoredetail);

        Glide.with(context).load(causemodals.get(position).getImage()).placeholder(R.mipmap.ic_launcher).into(imageView);

//        title.setText(causemodals.get(position).getTitle());
//        desc.setText(causemodals.get(position).getDescription());
//        cmpy.setText(causemodals.get(position).getCompanyname());

//        String text1 = "See Details";
//        // clicking on terms & condition
//        SpannableString ss1 = new SpannableString(text1);
//        ClickableSpan clickableSpan1 = new ClickableSpan() {
//            @Override
//            public void onClick(View view) {
//                Intent i = new Intent( context , ScratchGiftDetail.class);
//                i.putExtra("id",causemodals.get(position).getId());
//                context.startActivity(i);
//            }
//        };
//        ss1.setSpan(clickableSpan1,0,11, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
//        seedetails.setText(ss1);
//        seedetails.setMovementMethod(LinkMovementMethod.getInstance());

        container.addView(view, 0);
        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View)object);
    }
}
