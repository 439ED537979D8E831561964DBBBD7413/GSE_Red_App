package com.gsered.gse.rewards;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;

import com.gsered.gse.CouponsActivity;
import com.gsered.gse.MainActivity;
import com.gsered.gse.R;
import com.gsered.gse.SigninActivity;
import com.gsered.gse.donationbackground;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class BankAccountDetailsActivity extends AppCompatActivity {

    private TextInputLayout HolderName;
    private TextInputLayout BankName;
    private TextInputLayout BranchName;
    private TextInputLayout IFSCCode;
    private TextInputLayout AccountNumber;
    private TextInputLayout Amount;
    private FrameLayout progressbar;
    private String holdername,bankname,branchname,ifsc,accountnumber,amount,uid,paidamount,totalamount;
    private int maxamount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bank_account_details);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Bank Details");

        HolderName = findViewById(R.id.text_holder_name);
        BankName = findViewById(R.id.text_bank_name);
        BranchName = findViewById(R.id.text_branch_name);
        IFSCCode = findViewById(R.id.text_ifsc_code);
        AccountNumber = findViewById(R.id.text_account_number);
        Amount = findViewById(R.id.text_amount);
        progressbar = (FrameLayout) findViewById(R.id.progressbar);

        progressbar.setVisibility(View.GONE);

        SharedPreferences sharedPreferences = getSharedPreferences("SIGNCRED", MODE_PRIVATE);
        uid = sharedPreferences.getString("USERID","");

        SharedPreferences rewardPreferences = getSharedPreferences("REWARDS", MODE_PRIVATE);
        totalamount = rewardPreferences.getString("totalreward", "");
        paidamount = rewardPreferences.getString("paidreward", "");
        maxamount = Integer.valueOf(totalamount) - Integer.valueOf(paidamount);
        HolderName.getEditText().setText(rewardPreferences.getString("holdername", ""));
        BankName.getEditText().setText(rewardPreferences.getString("bankname", ""));
        BranchName.getEditText().setText(rewardPreferences.getString("branchname", ""));
        IFSCCode.getEditText().setText(rewardPreferences.getString("ifsccode", ""));
        AccountNumber.getEditText().setText(rewardPreferences.getString("accountnumber", ""));

        Amount.setHint("Redeem Amount (max : " + String.valueOf(maxamount) + " )");
    }

    private boolean validateHolder(){
        String name = HolderName.getEditText().getText().toString().trim();

        if(name.isEmpty()){
            HolderName.setError("* Required");
            return false;
        }else {
            HolderName.setError(null);
            HolderName.setErrorEnabled(false);
            return true;
        }
    }

    private boolean validateBank(){
        String name = BankName.getEditText().getText().toString().trim();

        if(name.isEmpty()){
            BankName.setError("* Required");
            return false;
        }else {
            BankName.setError(null);
            BankName.setErrorEnabled(false);
            return true;
        }
    }

    private boolean validateBranch(){
        String name = BranchName.getEditText().getText().toString().trim();

        if(name.isEmpty()){
            BranchName.setError("* Required");
            return false;
        }else {
            BranchName.setError(null);
            BranchName.setErrorEnabled(false);
            return true;
        }
    }

    private boolean validateaccountnumber() {
        String contact = AccountNumber.getEditText().getText().toString().trim();

        if(contact.isEmpty()){
            AccountNumber.setError("* Required");
            return false;
        }else {
            AccountNumber.setError(null);
            AccountNumber.setErrorEnabled(false);
            return true;
        }
    }

    private boolean validateifsc() {
        String contact = IFSCCode.getEditText().getText().toString().trim();

        if(contact.isEmpty()){
            IFSCCode.setError("* Required");
            return false;
        }else {
            IFSCCode.setError(null);
            IFSCCode.setErrorEnabled(false);
            return true;
        }
    }

    private boolean validateAmount() {
        String amount = Amount.getEditText().getText().toString().trim();

        if(amount.isEmpty()){
            Amount.setError("* Required");
            return false;
        }else if( Integer.valueOf(amount) > maxamount ) {
            Amount.setError("Max amount : " + String.valueOf(maxamount) );
            return false;
        } else {
            Amount.setError(null);
            Amount.setErrorEnabled(false);
            return true;
        }
    }

    public void registeruser(View v) {

        holdername = HolderName.getEditText().getText().toString().trim();
        bankname = BankName.getEditText().getText().toString().trim();
        branchname = BranchName.getEditText().getText().toString().trim();
        ifsc = IFSCCode.getEditText().getText().toString().trim();
        accountnumber = AccountNumber.getEditText().getText().toString().trim();
        amount = Amount.getEditText().getText().toString().trim();

        if(!validateHolder() | !validateBank() | !validateBranch() | !validateifsc() | !validateaccountnumber() | !validateAmount()){
            return;
        }

        storedetails();
    }

    private void storedetails() {
        progressbar.setVisibility(View.VISIBLE);

        OkHttpClient client = new OkHttpClient();
        String url = "https://www.gsered.com/intern/grp10/GSE_App/earned/bankdetails.php?uid="+uid
                +"&holdername="+holdername+"&bankname="+bankname+"&branchname="+branchname+"&ifsccode="+ifsc+"&accountnumber="+accountnumber
                +"&amount="+amount;
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
                    final String posts = response.body().string();
                    if(BankAccountDetailsActivity.this == null)
                        return;

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            JSONObject socialpost = null;
                            try {
                                socialpost = new JSONObject(posts);
                                JSONObject p = socialpost.getJSONObject("bankdetails");
                                String msg ="";
                                if(p.getString("updated").equalsIgnoreCase("1")) {
                                    msg="Amount will be transferred after five working days";
                                }

                                SharedPreferences sharedPreferences = getSharedPreferences("REWARDS", MODE_PRIVATE);
                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                editor.putString("holdername", holdername);
                                editor.putString("bankname", bankname);
                                editor.putString("branchname", branchname);
                                editor.putString("ifsccode", ifsc);
                                editor.putString("accountnumber", accountnumber);
                                editor.apply();

                                progressbar.setVisibility(View.GONE);
                                AlertDialog.Builder alertDialog = new AlertDialog.Builder(BankAccountDetailsActivity.this)
                                        .setCancelable(false)
                                        .setMessage(msg)
                                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialogInterface, int i) {
                                                Intent intent = new Intent(BankAccountDetailsActivity.this, RewardActivity.class);
                                                startActivity(intent);
                                            }
                                        });
                                alertDialog.create().show();

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    });
                }
            }
        });
    }
}
