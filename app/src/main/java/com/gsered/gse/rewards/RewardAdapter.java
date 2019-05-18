package com.gsered.gse.rewards;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.Image;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.gsered.gse.R;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.IOException;
import java.util.List;
import java.util.logging.Handler;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class RewardAdapter extends RecyclerView.Adapter<RewardAdapter.MyHolder> {

    private Context ctx;
    private List<reward_modal> rewards;
    private RewardClickListener itemclicklistener;

    public RewardAdapter(Context ctx, List<reward_modal> rewards, RewardClickListener itemclicklistener) {
        this.ctx = ctx;
        this.rewards = rewards;
        this.itemclicklistener = itemclicklistener;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v;
        v = LayoutInflater.from(ctx).inflate(R.layout.reward_item, parent, false);
        RewardAdapter.MyHolder holder = new RewardAdapter.MyHolder(v, itemclicklistener);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {
        holder.rewardid = rewards.get(position).getId();
        String seen = rewards.get(position).getSeen();
        if(seen.equalsIgnoreCase("2")) {
            holder.flag = "2";
            holder.image.setImageResource(R.drawable.blackwhite);
            holder.title.setText("");
            holder.amount.setText("Shop & Earn");
        } else if(seen.equalsIgnoreCase("0")) {
            holder.flag = "0";
            holder.image.setImageResource(R.drawable.notseen);
            holder.title.setText("GSE Red Gift");
            holder.amount.setText("Touch");
        } else {
            holder.flag = "1";
            holder.image.setImageResource(R.drawable.seen);
            holder.title.setText("You've Won");
            holder.amount.setText(rewards.get(position).getRewardamount());
        }
    }

    @Override
    public int getItemCount() {
        return rewards.size();
    }

    public class MyHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private ImageView image;
        private TextView title;
        private TextView amount;
        private RelativeLayout hidereward;
        RewardClickListener rewardClickListener;
        Dialog Mydialog;
        String rewardid,flag;

        public MyHolder(View itemView, RewardClickListener rewardClickListener) {
            super(itemView);

            hidereward = (RelativeLayout) itemView.findViewById(R.id.hidereward);
            image = (ImageView) itemView.findViewById(R.id.rewardimage);
            title = (TextView) itemView.findViewById(R.id.rewardtitle);
            amount = (TextView) itemView.findViewById(R.id.rewardamount);
            this.rewardClickListener = rewardClickListener;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if( (flag.equalsIgnoreCase("2")) )
                return;

            if( (flag.equalsIgnoreCase("0")) )
                rewardClickListener.onItemClick(view, getAdapterPosition());

            Mydialog = new Dialog(ctx);
            showpopup(view);
        }

        public void showpopup(final View view){
            TextView close;
            final TextView rewardamount,rewardtext;
            final ImageView rewardimage;
            final RelativeLayout hidereward;
            final Button tellyourfriends;

            String seen = rewards.get(getAdapterPosition()).getSeen();
            String amount = rewards.get(getAdapterPosition()).getRewardamount();
            Mydialog.setContentView(R.layout.custompopup);

            updaterewardseen();

            rewardtext = (TextView) Mydialog.findViewById(R.id.popuprewardtext);
            rewardimage = (ImageView) Mydialog.findViewById(R.id.popuprewardimage);
            close = (TextView) Mydialog.findViewById(R.id.close);
            rewardamount = (TextView) Mydialog.findViewById(R.id.popuprewardamount);
            hidereward = (RelativeLayout) Mydialog.findViewById(R.id.hidereward);
            tellyourfriends = (Button) Mydialog.findViewById(R.id.popuptellyourfriends);
            tellyourfriends.setVisibility(View.GONE);

            if(seen.equalsIgnoreCase("0")) {
                rewardimage.setVisibility(view.VISIBLE);
                rewardtext.setVisibility(view.VISIBLE);
                rewardamount.setVisibility(view.VISIBLE);
                hidereward.setBackgroundResource(R.drawable.blacksolidborder);

                // to show at reward page
                this.flag = "1";
                this.image.setImageResource(R.drawable.seen);
                this.title.setText("You've Won");
                this.amount.setText(amount);
            }

            rewardamount.setText(amount);
            close.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Mydialog.dismiss();
                }
            });
            Mydialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            Mydialog.show();
        }

        private void updaterewardseen() {
            OkHttpClient client = new OkHttpClient();
            String url = "https://www.gsered.com/intern/grp10/GSE_App/earned/updateseen.php?id="+rewardid;
            Request request = new Request.Builder()
                    .url(url)
                    .build();

            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    e.printStackTrace();
                }

                @Override
                public void onResponse(Call call, final Response response) throws IOException {
                    if(response.isSuccessful()){
                        if(ctx == null)
                            return;
                    }
                }
            });

        }
    }
}
