package com.gsered.gse;

import android.app.ProgressDialog;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.gsered.gse.modals.Company;
import com.gsered.gse.search.SearchActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class DynamicActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ProgressBar progressBar;
    private CompanyAdapter companyAdapter;
    private List<Company> companyList;
    private String responseStr;
    ProgressDialog progress;
    private static String APPCRED = "SIGNCRED";
    private static String NGO_ID = "NGOID";
    private static String USER_ID = "USERID";

    public DynamicActivity() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

//        AlertDialog.Builder builder = new AlertDialog.Builder(DynamicActivity.this);
//        builder.setTitle("Loading...").setMessage("Breathe-In   Breathe-Out");
//        final AlertDialog progress = builder.create();

//        progress = ProgressDialog.show(this, "Loading",
//                "Breathe-In   Breathe-Out", true);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dynamic);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        Intent i = getIntent();
        String domain = i.getStringExtra("domain");
        String module_nm = i.getStringExtra("module_nm");
        setTitle(module_nm);

        progressBar = (ProgressBar) findViewById(R.id.progress);
        recyclerView = (RecyclerView) findViewById(R.id.company_recycler_view);
        companyList = new ArrayList<>();
        CompItemClickListener itemClickListener = new CompItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
//                Toast.makeText(DynamicActivity.this, "", Toast.LENGTH_SHORT).show();
            }
        };

        companyAdapter = new CompanyAdapter(this, companyList, itemClickListener);

        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(this, 3);
        recyclerView.setLayoutManager(mLayoutManager);
//        recyclerView.addItemDecoration(new GridSpacingItemDecoration(2, dpToPx(10), true));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(companyAdapter);

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
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            try {
//                                progress.show();
                                render(responseStr);
//                                if (progress.isShowing())
//                                    progress.dismiss();
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

        companyAdapter.notifyDataSetChanged();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_search) {
            Intent i = new Intent(DynamicActivity.this, SearchActivity.class);
            startActivity(i);
        }
        return super.onOptionsItemSelected(item);
    }

    private void render(String response) throws JSONException {
        progressBar.setVisibility(View.GONE);

        JSONObject jsonObject = new JSONObject(response);
        JSONArray companies = jsonObject.getJSONArray("companies");
        for(int i =0; i<companies.length();i++){
            JSONObject object = companies.getJSONObject(i);
            companyList.add(new Company(object.getString("name"),"",object.getString("gotolink"),object.getString("img_url"),object.getString("admitad_id"),object.getString("raise"),object.getString("activate"),object.getString("probability"),object.getString("coupon"),object.getString("category")));
        }
        companyAdapter.notifyDataSetChanged();
    }

    @Override
    public void onBackPressed() {
        finish();
        super.onBackPressed();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }

}
