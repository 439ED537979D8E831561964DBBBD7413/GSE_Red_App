package com.gsered.gse.search;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.customtabs.CustomTabsIntent;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.Gson;
import com.gsered.gse.CompItemClickListener;
import com.gsered.gse.CompanyAdapter;
import com.gsered.gse.CouponsActivity;
import com.gsered.gse.R;
import com.gsered.gse.modals.CompaniesList;
import com.gsered.gse.modals.Company;
import com.gsered.gse.search.adapters.SearchAdapter;

import java.util.ArrayList;
import java.util.List;

public class SearchActivity extends AppCompatActivity implements SearchAdapter.SearchAdapterListener {

    private RecyclerView recyclerView;
    private List<Company> contactList;
    private SearchAdapter mAdapter;
    private EditText searchEt;
    private TextView results;
    private SearchView searchView;

    //    search variables
    private CompanyAdapter companyAdapter;
    private List<Company> companyList;
    ProgressDialog progress;
    private String responseStr;

    private static String SHARED_PREFS_FILE = "COMPANY_SHAREDPREF";
    private static String COMPANIES = "COMPANIES";
//    search variables ends here

    private static String APPCRED = "SIGNCRED";
    private static String NGO_ID = "NGOID";
    private static String USER_ID = "USERID";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        searchEt = (EditText) findViewById(R.id.search_et);
        results = (TextView) findViewById(R.id.search_bar_results);
        contactList = new ArrayList<>();
        mAdapter = new SearchAdapter(this, contactList, this);

        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(this, 3);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);

        CompItemClickListener itemClickListener = new CompItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
//                Toast.makeText(DynamicActivity.this, "", Toast.LENGTH_SHORT).show();
            }
        };

        SharedPreferences prefs = getSharedPreferences(SHARED_PREFS_FILE, Context.MODE_PRIVATE);

        Gson gson = new Gson();
        String json = prefs.getString("companiesList", "");
        CompaniesList obj = gson.fromJson(json, CompaniesList.class);

        companyList = obj.getCompanyList();

        Intent i = getIntent();
        String category = getIntent().getStringExtra("category");
        if(category != null) {

            List<Company> filteredList = new ArrayList<>();
            for (Company row : companyList) {

                // name match condition. this might differ depending on your requirement
                // here we are looking for name or phone number match
                if (row.getCompName().toLowerCase().contains(category.toLowerCase())) {
                    filteredList.add(row);
                } else if(row.getCategory().toLowerCase().contains(category.toLowerCase())) {
                    filteredList.add(row);
                } else if(row.getUrl().toLowerCase().contains(category.toLowerCase())) {
                    filteredList.add(row);
                }
            }
            mAdapter.setList(filteredList);

        } else {
            mAdapter.setList(companyList);
        }

        Window window = SearchActivity.this.getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(ContextCompat.getColor(SearchActivity.this, R.color.search_bar_bkg_gray));

        searchEt.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence cs, int arg1, int arg2, int arg3) {
                // When user changed the Text
                SearchActivity.this.mAdapter.getFilter().filter(cs);
                results.setText("Results (" + Integer.toString(mAdapter.getFilterSize()) + ")");
            }

            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
                // TODO Auto-generated method stub

            }

            @Override
            public void afterTextChanged(Editable arg0) {
                // TODO Auto-generated method stub
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_search) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    @Override
    public void onCompanySelected(Company contact) {
        Intent intent = new Intent(SearchActivity.this, CouponsActivity.class);
        intent.putExtra("admitad_id", contact.getAdmitad_id());
        intent.putExtra("name", contact.getCompName());
        intent.putExtra("raise", contact.getRaise());
        intent.putExtra("url", contact.getUrl());
        intent.putExtra("imgurl", contact.getImgUrl());
        intent.putExtra("probability", contact.getProbability());
        startActivity(intent);
    }
}
