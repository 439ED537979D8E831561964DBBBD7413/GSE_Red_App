package com.gsered.gse.search.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.gsered.gse.R;
import com.gsered.gse.modals.Company;

import java.util.ArrayList;
import java.util.List;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.MyViewHolder>
        implements Filterable {
    private Context context;
    private List<Company> companyList;
    private List<Company> companyListFiltered;
    private SearchAdapterListener listener;

    private int filterSize;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView compName, phone;
        //        public ImageView thumbnail;
        public ImageView compLogo,companydonate,companycoupon;

        public MyViewHolder(View view) {
            super(view);
            compName = view.findViewById(R.id.company_name);
            compLogo = view.findViewById(R.id.company_thumbnail);
            companydonate = (ImageView) view.findViewById(R.id.companydonates);
            companycoupon = (ImageView) view.findViewById(R.id.companyhascoupon);

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // send selected company in callback
                    listener.onCompanySelected(companyListFiltered.get(getAdapterPosition()));
                }
            });
        }
    }


    public SearchAdapter(Context context, List<Company> companyList, SearchAdapterListener listener) {
        this.context = context;
        this.listener = listener;
        this.companyList = companyList;
        this.companyListFiltered = companyList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.company_card, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        final Company company = companyListFiltered.get(position);

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

    public void setList(List<Company> companyList){
        this.companyList = companyList;
        this.companyListFiltered = companyList;
        this.filterSize = companyList.size();
//        System.out.println(companyList.toArray().toString());
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return companyListFiltered.size();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                if (charString.isEmpty()) {
                    companyListFiltered = companyList;
                } else {
                    List<Company> filteredList = new ArrayList<>();
                    for (Company row : companyList) {

                        // name match condition. this might differ depending on your requirement
                        // here we are looking for name or phone number match
                        if (row.getCompName().toLowerCase().contains(charString.toLowerCase())) {
                            filteredList.add(row);
                        } else if(row.getCategory().toLowerCase().contains(charString.toLowerCase())) {
                            filteredList.add(row);
                        }
                    }

                    companyListFiltered = filteredList;
                    filterSize = companyListFiltered.size();
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = companyListFiltered;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                companyListFiltered = (ArrayList<Company>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    public int getFilterSize(){
        return filterSize;
    }

    public interface SearchAdapterListener {
        void onCompanySelected(Company company);
    }
}