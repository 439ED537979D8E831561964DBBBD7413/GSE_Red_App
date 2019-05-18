package com.gsered.gse;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.gsered.gse.modals.NGO;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class DynamicNGOActivity extends AppCompatActivity implements AdapterCallBack{

    private RecyclerView recyclerView;
    private NGOAdapter companyAdapter;
//    private Menu navmenu;
//    private MenuItem donation;
    private List<NGO> companyList;
    private String responseStr;
    ProgressDialog progress;

    private SharedPreferences prefs;
    private SharedPreferences.Editor editor;
    private static String SHARED_PREFS_FILE = "SIGNCRED";
    private static String PHONE = "PHONE";
    private static String usersngo ="usersngo";
    private static String NGO_ID = "NGOID";
    private static String APPCRED = "SIGNCRED";
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        progress = ProgressDialog.show(this, "Loading",
                "Breathe-In   Breathe-Out", true);
        prefs = getSharedPreferences(SHARED_PREFS_FILE, MODE_PRIVATE);
        editor = prefs.edit();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dynamic_ngo);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        if (prefs.contains(usersngo)) {
            String s= prefs.getString(usersngo,"");
            setTitle("Donate to "+s);

        }else
        {
            Intent i = getIntent();
            String module_nm = i.getStringExtra("module_nm");
            setTitle(module_nm);
        }

        recyclerView = (RecyclerView) findViewById(R.id.company_recycler_view);
        companyList = new ArrayList<>();
        CompItemClickListener itemClickListener = new CompItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
            }
        };

        companyAdapter = new NGOAdapter(this, companyList, itemClickListener,this);

        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(this, 3);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(companyAdapter);

        new ServiceHandler().post2(new Callback() {
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
                                progress.show();
                                render(responseStr);

                                if (progress.isShowing())
                                    progress.dismiss();
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

    private void render(String response) throws JSONException {
        JSONObject jsonObject = new JSONObject(response);
        JSONArray companies = jsonObject.getJSONArray("companies");
        for(int i =0; i<companies.length();i++){
            NGO company = new NGO();
            JSONObject object = companies.getJSONObject(i);
            company.setCompName(object.getString("name"));
            company.setUrl(object.getString("changedlink"));
            company.setNid(object.getString("ngo_id"));
            if(object.getString("img_url").equals("")){
                company.setImgUrl("");
            }else {
                company.setImgUrl(object.getString("img_url"));
            }
            companyList.add(company);
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

    @Override
    public void onChangeActionBar(String ngoname, String ngoid) {

        setTitle("Donate to "+ngoname);
        String phno = prefs.getString(PHONE, null);
        InsertNGOData(ngoid,phno);
    }

    public void InsertNGOData(String nid, String phone){
        SendPostReqAsyncTask sendPostReqAsyncTask = new SendPostReqAsyncTask();
        sendPostReqAsyncTask.execute(nid,phone);
    }

    class SendPostReqAsyncTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {

            String ServerURL = "https://www.gsered.com/intern/grp10/GSE_App/set_ngo.php";
            String text="";
            String nid = params[0];
            String phone = params[1];

            phone=phone.replace("+91","");
            phone = phone.replaceAll("[()\\s-]", "");
            try {
                URL url = new URL(ServerURL);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                OutputStream os = httpURLConnection.getOutputStream();
                String data = URLEncoder.encode("nid", "UTF-8") + "=" + URLEncoder.encode(nid, "UTF-8") + "&" +
                        URLEncoder.encode("phone", "UTF-8") + "=" + URLEncoder.encode(phone, "UTF-8") ;
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
                bufferedWriter.write(data);
                bufferedWriter.flush();
                int statusCode = httpURLConnection.getResponseCode();
                if (statusCode == 200) {

                    BufferedReader reader = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
                    StringBuilder sb = new StringBuilder();
                    String line;

                    while ((line = reader.readLine()) != null)
                        sb.append(line).append("\n");

                    text = sb.toString();
                    bufferedWriter.close();

                }
            }catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return text;
        }

        @Override
        protected void onPostExecute(String result) {

            super.onPostExecute(result);

            Toast.makeText(DynamicNGOActivity.this, "Data Submit Successfully", Toast.LENGTH_LONG).show();
            finish();
            Intent i = getBaseContext().getPackageManager().
                    getLaunchIntentForPackage(getBaseContext().getPackageName());
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(i);
        }
    }
}
