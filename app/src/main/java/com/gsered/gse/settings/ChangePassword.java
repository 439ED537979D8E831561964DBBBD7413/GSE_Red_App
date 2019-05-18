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

public class ChangePassword extends Fragment {
    View view;
    private TextInputLayout textInputName;
    private TextInputLayout textInputEmail;
    private TextInputLayout textInputContact;
    private Button submit;
    ProgressBar progressBar;

    public ChangePassword() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.change_password, container, false);

        textInputName = view.findViewById(R.id.text_current_password);
        textInputEmail = view.findViewById(R.id.text_new_password);
        textInputContact = view.findViewById(R.id.text_confirm_password);
        submit = view.findViewById(R.id.submit);
        progressBar = (ProgressBar) view.findViewById(R.id.progress);

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

        String old = textInputName.getEditText().getText().toString().trim();
        String newpassword = textInputEmail.getEditText().getText().toString().trim();
        String confirm = textInputContact.getEditText().getText().toString().trim();

        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("SIGNCRED", MODE_PRIVATE);
        String uid = sharedPreferences.getString("USERID","");

        if(old.isEmpty() | newpassword.isEmpty() | confirm.isEmpty())
            return;

        if(!newpassword.equals(confirm)){
            progressBar.setVisibility(View.GONE);
            AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity())
                    .setCancelable(true)
                    .setMessage("Kindly check your new password with confirm password.")
                    .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                        }
                    });
            alertDialog.create().show();
            return;
        }

        OkHttpClient client = new OkHttpClient();
        String url = "https://www.gsered.com/intern/grp10/GSE_App/dashboard/changepassword.php?uid="+uid
                +"&old="+old+"&new="+newpassword;
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
