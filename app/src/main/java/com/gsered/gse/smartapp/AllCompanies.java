package com.gsered.gse.smartapp;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.gsered.gse.CompItemClickListener;
import com.gsered.gse.CompanyAdapter;
import com.gsered.gse.R;
import com.gsered.gse.ServiceHandler;
import com.gsered.gse.modals.Company;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class AllCompanies extends Fragment {

    private RecyclerView recyclerView;
    private CompanyAdapter companyAdapter;
    private List<Company> companyList;
    private String responseStr,domain;
    private ProgressBar progressBar;
    View view;

    public static AllCompanies newInstance() {
        AllCompanies fragment = new AllCompanies();
        return fragment;
    }

    public AllCompanies() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.activity_dynamic, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.company_recycler_view);
        progressBar = (ProgressBar) view.findViewById(R.id.progress);

        CompItemClickListener itemClickListener = new CompItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
            }
        };

        if (getArguments() != null) {
            this.domain = getArguments().getString("domain");
        }

        companyList = new ArrayList<>();
        new ServiceHandler().post(domain, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                // Something went wrong
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    responseStr = response.body().string();
                    System.out.println(responseStr);
                    if(getActivity() == null)
                        return;

                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                render(responseStr);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    });

                } else {
                    // Request not successful
                    System.out.println("fail");
                }
            }
        });

        companyAdapter = new CompanyAdapter(getActivity(), companyList, itemClickListener);
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getActivity(), 3);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(companyAdapter);

        return view;
    }

    private void render(String response) throws JSONException {
        progressBar.setVisibility(View.GONE);

        JSONObject jsonObject = new JSONObject(response);
        JSONArray companies = jsonObject.getJSONArray("companies");
        for(int i =0; i<companies.length();i++){
            JSONObject object = companies.getJSONObject(i);
            companyList.add(new Company(object.getString("name"),"",object.getString("gotolink"),object.getString("img_url"),object.getString("admitad_id"),object.getString("raise"),object.getString("activate"),object.getString("probability"),object.getString("coupon"),object.getString("category")));
        }
    }

}
