package com.gsered.gse.share;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.content.FileProvider;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.GlideBitmapDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.gsered.gse.BuildConfig;
import com.gsered.gse.R;
import com.gsered.gse.share.share;

import org.w3c.dom.Text;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.concurrent.ExecutionException;

import retrofit2.http.Url;

import static android.support.v4.content.ContextCompat.startActivity;

public class shareadapter extends RecyclerView.Adapter<shareadapter.MyViewHolder> {

    public static Context ctx;
    List<share_modal> shares;
    URL url = null;
    Bitmap bmp = null;

    public shareadapter(Context ctx, List<share_modal> shares) {
        this.ctx = ctx;
        this.shares = shares;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v;
        v = LayoutInflater.from(ctx).inflate(R.layout.share_item, parent,false);
        shareadapter.MyViewHolder viewHolder = new shareadapter.MyViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, int position) {
        holder.title.setText(shares.get(position).getTitle());
        holder.date.setText(shares.get(position).getDate());
        holder.sendtext.setText(shares.get(position).getSharetext());
        Glide.with(ctx)
                .load(shares.get(position).getImage())
                .asBitmap()
                .placeholder(R.mipmap.ic_launcher)
                .into(new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                        holder.image.setScaleType(ImageView.ScaleType.FIT_XY);
                        holder.image.setImageBitmap(resource);
                    }
                });
    }

    @Override
    public int getItemCount() {
        return shares.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView title;
        private TextView date;
        private ImageView image;
        private TextView sendtext;
        private TextView share;
        private LinearLayout linearLayout;

        public MyViewHolder(View itemView) {
            super(itemView);

            share = (TextView) itemView.findViewById(R.id.share);
            sendtext = (TextView) itemView.findViewById(R.id.sendtext);
            title = (TextView) itemView.findViewById(R.id.sharetitle);
            date = (TextView) itemView.findViewById(R.id.sharedate);
            image = (ImageView) itemView.findViewById(R.id.shareimage);
            linearLayout = (LinearLayout) itemView.findViewById(R.id.shareimagecontent);

            linearLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Bitmap bitmap = ((BitmapDrawable) image.getDrawable()).getBitmap();
                    try {
                        File file = new File(ctx.getExternalCacheDir(), "share.png");
                        FileOutputStream fout = new FileOutputStream(file);
                        bitmap.compress(Bitmap.CompressFormat.PNG, 100, fout);
                        fout.flush();
                        fout.close();
                        file.setReadable(true,false);
                        Uri uri = FileProvider.getUriForFile(ctx, BuildConfig.APPLICATION_ID + ".provider",file);
                        final Intent intent = new Intent(Intent.ACTION_SEND);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        intent.putExtra(Intent.EXTRA_TEXT, sendtext.getText());
                        intent.putExtra(Intent.EXTRA_STREAM, uri);
                        intent.setType("image/*");
                        ctx.startActivity(Intent.createChooser(intent, "Share Image via"));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    }
}
