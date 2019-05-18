package com.gsered.gse;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

import static android.content.Context.MODE_PRIVATE;

public class donationbackground extends AsyncTask<String, String, String> {

    Context ctx;
    private String purpose;
    private String BEFOREDONATION = "beforedonation";
    private String AFTERDONATION = "afterdonation";
    private String SIGNUP = "signup";
    private String FORGOT = "forgot";
    private String SIGNIN = "signin";
    private static String APPCRED = "SIGNCRED";
    private static String NGO_ID = "NGOID";
    private static String USER_ID = "USERID";
    private static String PHONE = "PHONE";
    private static String usersngo = "usersngo";
    private static String NAME = "NAME";
    private static String EMAIL = "EMAIL";
    private static String CODE = "CODE";
    private static String ISLOGIN = "ISLOGIN";
    ProgressDialog progress;

    donationbackground(Context ctx){
        this.ctx = ctx;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        dialog("processing");
        progress.show();
    }

    @Override
    protected String doInBackground(String... strings) {

        String url_beforedonation = "https://gsered.com/intern/grp10/GSE_App/beforetransaction.php";
        String url_afterdonation = "https://gsered.com/intern/grp10/GSE_App/aftertransaction.php";
        String url_signup = "https://gsered.com/intern/grp10/GSE_App/signup.php";
        String url_signin = "https://gsered.com/intern/grp10/GSE_App/signin.php";
        String url_forgot = "https://gsered.com/intern/grp10/GSE_App/forgot_password.php";

        purpose = strings[0];

        if(purpose.equalsIgnoreCase(BEFOREDONATION))
        {
            String name = strings[1];
            String phone = strings[2];
            String email = strings[3];
            String pinfo = strings[4];
            String uid = strings[5];
            String amount = strings[6];
            String txnid = strings[7];

            try {
                URL url = new URL(url_beforedonation);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);

                OutputStream OS = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(OS,"UTF-8"));
                String data = URLEncoder.encode("name","UTF-8") +"="+ URLEncoder.encode(name,"UTF-8") +"&"+
                        URLEncoder.encode("phone","UTF-8") +"="+ URLEncoder.encode(phone,"UTF-8") +"&"+
                        URLEncoder.encode("email","UTF-8") +"="+ URLEncoder.encode(email,"UTF-8") +"&"+
                        URLEncoder.encode("pinfo","UTF-8") +"="+ URLEncoder.encode(pinfo,"UTF-8") +"&"+
                        URLEncoder.encode("uid","UTF-8") +"="+ URLEncoder.encode(uid,"UTF-8") +"&"+
                        URLEncoder.encode("txnid","UTF-8") +"="+ URLEncoder.encode(txnid,"UTF-8") +"&"+
                        URLEncoder.encode("amount","UTF-8") +"="+ URLEncoder.encode(amount,"UTF-8");

                bufferedWriter.write(data);
                bufferedWriter.flush();
                bufferedWriter.close();
                OS.close();

                InputStream IS = httpURLConnection.getInputStream();
                IS.close();
                return "Donation Updated..";

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        else if(purpose.equalsIgnoreCase(AFTERDONATION))
        {
            String name = strings[1];
            String phone = strings[2];
            String email = strings[3];
            String pinfo = strings[4];
            String txnid = strings[5];
            String amount = strings[6];

            try {
                URL url = new URL(url_afterdonation);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);

                OutputStream OS = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(OS,"UTF-8"));
                String data = URLEncoder.encode("name","UTF-8") +"="+ URLEncoder.encode(name,"UTF-8") +"&"+
                        URLEncoder.encode("txnid","UTF-8") +"="+ URLEncoder.encode(txnid,"UTF-8") +"&"+
                        URLEncoder.encode("phone","UTF-8") +"="+ URLEncoder.encode(phone,"UTF-8") +"&"+
                        URLEncoder.encode("email","UTF-8") +"="+ URLEncoder.encode(email,"UTF-8") +"&"+
                        URLEncoder.encode("pinfo","UTF-8") +"="+ URLEncoder.encode(pinfo,"UTF-8") +"&"+
                        URLEncoder.encode("amount","UTF-8") +"="+ URLEncoder.encode(amount,"UTF-8");

                bufferedWriter.write(data);
                bufferedWriter.flush();
                bufferedWriter.close();
                OS.close();

                InputStream IS = httpURLConnection.getInputStream();
                IS.close();
                return "Donation Updated..";

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        else if(purpose.equalsIgnoreCase(SIGNUP))
        {
            String name = strings[1];
            String phone = strings[2];
            String email = strings[3];
            String password = strings[4];
            String referralcode = strings[5];

            try {
                URL url = new URL(url_signup);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);

                OutputStream OS = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(OS,"UTF-8"));
                String data = URLEncoder.encode("name","UTF-8") +"="+ URLEncoder.encode(name,"UTF-8") +"&"+
                        URLEncoder.encode("phone","UTF-8") +"="+ URLEncoder.encode(phone,"UTF-8") +"&"+
                        URLEncoder.encode("email","UTF-8") +"="+ URLEncoder.encode(email,"UTF-8") +"&"+
                        URLEncoder.encode("referralcode","UTF-8") +"="+ URLEncoder.encode(referralcode,"UTF-8") +"&"+
                        URLEncoder.encode("password","UTF-8") +"="+ URLEncoder.encode(password,"UTF-8");

                bufferedWriter.write(data);
                bufferedWriter.flush();
                bufferedWriter.close();
                OS.close();

                InputStream IS = httpURLConnection.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(IS, "UTF-8"));
                StringBuilder str = new StringBuilder();
                String line = "";

                while ((line = reader.readLine()) != null){
                    str.append(line).append("\n");
                }
                IS.close();

                return str.toString();

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        else if(purpose.equalsIgnoreCase(SIGNIN))
        {
            String username = strings[1];
            String password = strings[2];

            try {
                URL url = new URL(url_signin);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);

                OutputStream OS = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(OS,"UTF-8"));
                String data = URLEncoder.encode("username","UTF-8") +"="+ URLEncoder.encode(username,"UTF-8") +"&"+
                        URLEncoder.encode("password","UTF-8") +"="+ URLEncoder.encode(password,"UTF-8");

                bufferedWriter.write(data);
                bufferedWriter.flush();
                bufferedWriter.close();
                OS.close();

                InputStream IS = httpURLConnection.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(IS, "UTF-8"));
                StringBuilder str = new StringBuilder();
                String line = "";

                while ((line = reader.readLine()) != null){
                    str.append(line).append("\n");
                }
                IS.close();

                return str.toString();

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else if(purpose.equalsIgnoreCase("forgot")){
            String email = strings[1];

            try {
                URL url = new URL(url_forgot);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);

                OutputStream OS = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(OS,"UTF-8"));
                String data = URLEncoder.encode("email","UTF-8") +"="+ URLEncoder.encode(email,"UTF-8");

                bufferedWriter.write(data);
                bufferedWriter.flush();
                bufferedWriter.close();
                OS.close();

                InputStream IS = httpURLConnection.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(IS, "UTF-8"));
                StringBuilder str = new StringBuilder();
                String line = "";

                while ((line = reader.readLine()) != null){
                    str.append(line).append("\n");
                }
                IS.close();

                return str.toString();

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    @Override
    protected void onProgressUpdate(String... values) {
        super.onProgressUpdate(values);
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        progress.dismiss();
        if(purpose.equalsIgnoreCase(SIGNUP)){
            String[] result = s.split(",");
            if(result[0].equalsIgnoreCase("successfull"))
            {
                SharedPreferences sharedPreferences = ctx.getSharedPreferences(APPCRED, MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putBoolean(ISLOGIN, true);
                editor.putString(NAME, result[1]);
                editor.putString(PHONE, result[2]);
                editor.putString(EMAIL, result[3]);
                editor.putString(USER_ID, result[4]);
                editor.putString(CODE, result[5]);
                editor.putString(NGO_ID, "0");
                editor.putString(usersngo, "Hers");
                editor.commit();

                Toast.makeText(ctx, "Successfully Registered", Toast.LENGTH_SHORT).show();

                Intent i = new Intent(ctx, MainActivity.class);
                ctx.startActivity(i);

            }
            else if(result[0].equalsIgnoreCase("fail")) {
                Toast.makeText(ctx, result[1], Toast.LENGTH_SHORT).show();
                Intent i = new Intent(ctx, SigninActivity.class);
                ctx.startActivity(i);
            }
        }
        else if(purpose.equalsIgnoreCase(SIGNIN)){
            String[] result = s.split(",");
            if(result[0].equalsIgnoreCase("successfull"))
            {
                SharedPreferences sharedPreferences = ctx.getSharedPreferences(APPCRED, MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putBoolean(ISLOGIN, true);
                editor.putString(NAME, result[1]);
                editor.putString(PHONE, result[2]);
                editor.putString(EMAIL, result[3]);
                editor.putString(USER_ID, result[4]);
                editor.putString(NGO_ID, result[5]);
                editor.putString(usersngo, result[6].trim());
                editor.commit();

                Intent i = new Intent(ctx, MainActivity.class);
                ctx.startActivity(i);
            }
            else if(result[0].equalsIgnoreCase("fail")) {
                Toast.makeText(ctx, result[1], Toast.LENGTH_SHORT).show();
            }
            else if(result[0].equalsIgnoreCase("new")) {
                if(result[1].equalsIgnoreCase("contact")) {
                    Intent i = new Intent(ctx, SignupActivity.class);
                    i.putExtra("contact", result[2]);
                    i.putExtra("email", "");
                    ctx.startActivity(i);
                } else if(result[1].equalsIgnoreCase("email")) {
                    Intent i = new Intent(ctx, SignupActivity.class);
                    i.putExtra("contact", "");
                    i.putExtra("email", result[2]);
                    ctx.startActivity(i);
                }
                Toast.makeText(ctx, "Email or contact no. is not registered, kindly signup", Toast.LENGTH_SHORT).show();
            }
        }
        else if(purpose.equalsIgnoreCase(FORGOT)) {
            String msg="",buttontext="";
            final Intent intent;
            String[] result = s.split(",");
            if(result[0].equalsIgnoreCase("sendmail")) {
                buttontext = "Login";
                msg = "Kindly check your mail inbox or spam for your password.";
                intent = new Intent(ctx, SigninActivity.class);
            } else {
                buttontext = "Re-enter";
                msg = "Email address not registered!";
                intent = new Intent(ctx, ForgotPassword.class);
            }

            AlertDialog.Builder alertDialog = new AlertDialog.Builder(ctx)
                    .setCancelable(false)
                    .setMessage(msg)
                    .setPositiveButton(buttontext, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            ctx.startActivity(intent);
                        }
                    });
            alertDialog.create().show();
        }
    }

    public void dialog(String msg){
        progress = ProgressDialog.show(ctx,"",msg,true);
    }
}
