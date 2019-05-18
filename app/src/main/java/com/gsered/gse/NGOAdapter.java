package com.gsered.gse;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.gsered.gse.modals.NGO;

import java.util.List;

import static android.content.Context.MODE_PRIVATE;


public class NGOAdapter extends RecyclerView.Adapter<NGOAdapter.CompViewHolder> {

    private AdapterCallBack adapterCallback;
    private Context context;
    private List<NGO> companyList;
    private CompItemClickListener listener2;


    @Override
    public int getItemCount() {
        return companyList.size();
    }

    public class CompViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public TextView compName;
        public ImageView compLogo;
        public RelativeLayout symbols;
        private CompItemClickListener listener;

        public CompViewHolder(View view, CompItemClickListener listener){
            super(view);
            compName = (TextView) view.findViewById(R.id.company_name);
            compLogo = (ImageView) view.findViewById(R.id.company_thumbnail);
            symbols = (RelativeLayout) view.findViewById(R.id.indicator);

            symbols.setVisibility(View.GONE);
            this.listener = listener;
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            listener.onItemClick(view, getAdapterPosition());
            String name = companyList.get(getAdapterPosition()).getCompName();
            String nid= companyList.get(getAdapterPosition()).getNid();

            Toast.makeText(view.getContext(), "Your Donations will go for CHARITY : "+name, Toast.LENGTH_SHORT).show();

            SharedPreferences prefs = context.getSharedPreferences( "SIGNCRED" , MODE_PRIVATE);
            SharedPreferences.Editor editor = prefs.edit();
            editor.putString( "usersngo", name);
            editor.putString( "NGOID" , nid);
            editor.commit();

            adapterCallback.onChangeActionBar(name,nid);

        }
    }

    public NGOAdapter(Context context, List<NGO> companyList, CompItemClickListener listener, AdapterCallBack adapterCallback){
        this.context = context;
        this.companyList = companyList;
        this.listener2 = listener;
        this.adapterCallback = adapterCallback;
    }

    @Override
    public CompViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.company_card, parent, false);

        return new CompViewHolder(itemView, listener2);
    }

    @Override
    public void onBindViewHolder(final CompViewHolder holder, int position) {
        NGO company = companyList.get(position);
        holder.compName.setText(company.getCompName());

        Glide.with(context).load("https://gsered.com/charity/images/ngologo/" + company.getImgUrl()).placeholder(R.mipmap.ic_launcher).into(holder.compLogo);
    }


}
