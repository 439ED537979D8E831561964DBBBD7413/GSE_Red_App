package com.gsered.gse.settings;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.gsered.gse.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static android.content.Context.MODE_PRIVATE;

public class BankDetails extends Fragment {
    View view;
    private EditText HolderName,BankName,BranchName,IFSCCode,AccountNumber;
    private ProgressBar progressBar;
    private String holdername,bankname,branchname,ifsc,accountnumber;
    private Button submit;

    public BankDetails() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.bank_details, container, false);
        HolderName = view.findViewById(R.id.accountholdername);
        BankName = view.findViewById(R.id.bankname);
        BranchName = view.findViewById(R.id.branchname);
        IFSCCode = view.findViewById(R.id.ifsccode);
        AccountNumber = view.findViewById(R.id.accountnumber);
        submit = view.findViewById(R.id.submit);
        progressBar = (ProgressBar) view.findViewById(R.id.progress);

        SharedPreferences rewardPreferences = getActivity().getSharedPreferences("REWARDS", MODE_PRIVATE);
        HolderName.setText(rewardPreferences.getString("holdername", ""));
        BankName.setText(rewardPreferences.getString("bankname", ""));
        BranchName.setText(rewardPreferences.getString("branchname", ""));
        IFSCCode.setText(rewardPreferences.getString("ifsccode", ""));
        AccountNumber.setText(rewardPreferences.getString("accountnumber", ""));

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registeruser();
            }
        });

        return view;
    }

    public void registeruser() {
        progressBar.setVisibility(View.VISIBLE);
        holdername = HolderName.getText().toString().trim();
        bankname = BankName.getText().toString().trim();
        branchname = BranchName.getText().toString().trim();
        ifsc = IFSCCode.getText().toString().trim();
        accountnumber = AccountNumber.getText().toString().trim();

        if(holdername.isEmpty() | bankname.isEmpty() | branchname.isEmpty() | ifsc.isEmpty() | accountnumber.isEmpty()){
            Toast.makeText(getActivity(), "All fields are mandatory", Toast.LENGTH_LONG).show();
            return;
        }

        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("SIGNCRED", MODE_PRIVATE);
        String uid = sharedPreferences.getString("USERID","");

        OkHttpClient client = new OkHttpClient();
        String url = "https://www.gsered.com/intern/grp10/GSE_App/dashboard/bankdetail.php?uid="+uid
                +"&holdername="+holdername+"&bankname="+bankname+"&branchname="+branchname
                +"&ifsccode="+ifsc+"&accountnumber="+accountnumber;
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
                    if(getActivity() == null)
                        return;

                    getActivity().runOnUiThread(new Runnable() {
                        public void run() {
                            JSONObject socialpost = null;
                            try {
                                socialpost = new JSONObject(posts);
                                JSONObject p = socialpost.getJSONObject("response");
                                String msg = p.getString("status");

                                SharedPreferences sharedPreferences = getActivity().getSharedPreferences("REWARDS", MODE_PRIVATE);
                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                editor.putString("holdername", holdername);
                                editor.putString("bankname", bankname);
                                editor.putString("branchname", branchname);
                                editor.putString("ifsccode", ifsc);
                                editor.putString("accountnumber", accountnumber);
                                editor.apply();

                                progressBar.setVisibility(View.GONE);
                                AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity())
                                        .setCancelable(true)
                                        .setMessage(msg)
                                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialogInterface, int i) {
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
