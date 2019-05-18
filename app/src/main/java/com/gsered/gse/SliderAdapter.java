package com.gsered.gse;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class SliderAdapter extends PagerAdapter {

    Context context;
    LayoutInflater layoutInflater;

    public SliderAdapter(Context context) {

        this.context = context;
    }

    //Arrays
//    public int[] slide_images = {
//
//            R.drawable.eat_icon,
//            R.drawable.sleep_icon,
//            R.drawable.code_icon,
//            R.drawable.code_icon
//    };

    public String[] slide_headings = {

            "Donate",
            "Shop",
            "Support",
            "All-In-One"
    };

    public String[] slide_descs = {

            "Feel The Magic Of Giving without paying any extra penny!",
            "We help you donate upto 12% everytime you shop through us! ",
            "Supporting 50+ charities across India!",
            "Includes paytm, bookmyshow, swiggy & 375+ stores!"
    };

    @Override
    public int getCount() {
        return slide_headings.length;
    }

    @Override
    public boolean isViewFromObject(View view, Object o) {
        return view == (RelativeLayout) o;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {

        layoutInflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.slide_layout, container, false);

//        ImageView slideImageView = (ImageView) view.findViewById(R.id.slide_image);
        TextView slideHeading = (TextView) view.findViewById(R.id.slide_heading);
        TextView slideDescription = (TextView) view.findViewById(R.id.slide_desc);

//        slideImageView.setImageResource(slide_images[position]);
        slideHeading.setText(slide_headings[position]);
        slideDescription.setText(slide_descs[position]);

        container.addView(view);

        return view;

    }


    @Override
    public void destroyItem (ViewGroup container, int position, Object object) {
        container.removeView((RelativeLayout)object);
    }
}