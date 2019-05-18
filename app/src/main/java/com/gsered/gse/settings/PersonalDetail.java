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
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
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

public class PersonalDetail extends Fragment {
    View view;
    private Button submit;
    String uid,name,mail,cont;
    EditText username,useremail,usercontact;
    TextView uname,uemail,ucontact,emailedit,contactedit;
    RelativeLayout eline,cline;
    ProgressBar progressBar;

    public PersonalDetail() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.personal_detail, container, false);

        eline = (RelativeLayout) view.findViewById(R.id.emailline);
        cline = (RelativeLayout) view.findViewById(R.id.contactline);
        emailedit = (TextView) view.findViewById(R.id.editemail);
        contactedit = (TextView) view.findViewById(R.id.editcontact);
        uname = (TextView) view.findViewById(R.id.username);
        uemail = (TextView) view.findViewById(R.id.useremail);
        ucontact = (TextView) view.findViewById(R.id.usercontact);
        useremail = (EditText) view.findViewById(R.id.email);
        usercontact = (EditText) view.findViewById(R.id.contact);
        submit = view.findViewById(R.id.submit);
        progressBar = (ProgressBar) view.findViewById(R.id.progress);

        emailedit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                eline.setVisibility(View.GONE);
                useremail.setVisibility(View.VISIBLE);
            }
        });

        contactedit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cline.setVisibility(View.GONE);
                usercontact.setVisibility(View.VISIBLE);
            }
        });

        SharedPreferences sharedPreferences = getContext().getSharedPreferences("SIGNCRED", MODE_PRIVATE);
        uid = sharedPreferences.getString("USERID","");
        name = sharedPreferences.getString("NAME","");
        mail = sharedPreferences.getString("EMAIL","");
        cont = sharedPreferences.getString("PHONE","");

        uname.setText(name);
        uemail.setText(mail);
        ucontact.setText(cont);
        useremail.setText(mail);
        usercontact.setText(cont);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registeruser();
            }
        });
        return view;
    }

    public void registeruser(){
        progressBar.setVisibility(View.VISIBLE);
        final String email = useremail.getText().toString().trim();
        final String contact = usercontact.getText().toString().trim();

        if(email.isEmpty() | contact.isEmpty()) {
            progressBar.setVisibility(View.GONE);
            AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity())
                    .setCancelable(true)
                    .setMessage("Fields cannot be empty.")
                    .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                        }
                    });
            alertDialog.create().show();
            return;
        }

        OkHttpClient client = new OkHttpClient();
        String url = "https://www.gsered.com/intern/grp10/GSE_App/dashboard/personaldetails.php?uid="+uid
                +"&email="+email+"&contact="+contact;
        Request request = new Request.Builder()
                .url(url)
                .build();
        Log.e("email : ",email);
        Log.e("contact : ",contact);
        Log.e("url : ",url);

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
                Log.e("error : ", e.toString());
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

                                SharedPreferences sharedPreferences = getActivity().getSharedPreferences("SIGNCRED", MODE_PRIVATE);
                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                editor.putString("PHONE", contact);
                                editor.putString("EMAIL", email);
                                editor.commit();

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
