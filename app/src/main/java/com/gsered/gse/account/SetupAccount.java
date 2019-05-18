package com.gsered.gse.account;

import android.Manifest;
import android.animation.ObjectAnimator;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.bumptech.glide.Glide;
import com.gsered.gse.DynamicNGOActivity;
import com.gsered.gse.LoginActivity;
import com.gsered.gse.MainActivity;
import com.gsered.gse.MyAccountActivity;
import com.gsered.gse.R;

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
import java.util.Calendar;

public class SetupAccount extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

    private RelativeLayout detail, birth, img, done;
    private ImageButton back1, back2, back3;

    private Button onwards;
    private EditText email, name, password, contact;
    private TextView dob;
    private ImageView pic;
    private  DatePickerDialog datePickerDialog;
    // Progress dialog
    ProgressDialog progressDialog;

    private String photoPath = null;
    private Bitmap photo;
    private Uri photoUri;
    private String phno, msg, userid;
    private int NOPAGES = 3;
    private int CURPAGE = 0;
    private static final int REQUEST_CAMERA = 1, REQUEST_CROP_ICON = 2, PICK_IMAGE = 3;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private static String APPCRED = "SIGNCRED";
    private static String NAME = "NAME";
    private static String PHONE = "PHONE";
    private static String EMAIL = "EMAIL";
    private static String DOB = "DOB";
    private static String IMGURI = "IMGURI";
    private static String ISLOGIN = "ISLOGIN";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup_account);

        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        detail = findViewById(R.id.acc_set_detailsview);
        birth = findViewById(R.id.acc_set_dobview);
        img = findViewById(R.id.acc_set_imgview);
        done = findViewById(R.id.acc_set_doneview);

        onwards = findViewById(R.id.acc_set_onward);
        back1 = findViewById(R.id.acc_set_det_back);
        back2 = findViewById(R.id.acc_set_dob_back);
        back3 = findViewById(R.id.acc_set_img_back);

        email = findViewById(R.id.acc_set_email);
        name = findViewById(R.id.acc_set_name);
        contact = findViewById(R.id.acc_set_mobilenumber);
        password = findViewById(R.id.acc_set_password);

        dob = findViewById(R.id.acc_set_dob);
        pic = findViewById(R.id.acc_set_img);

        sharedPreferences = getSharedPreferences(APPCRED, MODE_PRIVATE);
        phno=sharedPreferences.getString(PHONE, "");
        Boolean checklogin = sharedPreferences.getBoolean(ISLOGIN, false);
        if(checklogin)
        {
            email.setText(sharedPreferences.getString(EMAIL, ""));
            contact.setText(sharedPreferences.getString(PHONE, ""));
            name.setText(sharedPreferences.getString(NAME, ""));
        }
        editor = sharedPreferences.edit();

        revAnimate(img, 0);
        revAnimate(birth, 0);
        revAnimate(done, 0);

        datePickerDialog = new DatePickerDialog(SetupAccount.this, this, Calendar.getInstance().get(Calendar.YEAR), Calendar.getInstance().get(Calendar.MONTH), Calendar.getInstance().get(Calendar.DAY_OF_MONTH));

        dob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datePickerDialog.show();
//                dob.getX();
//                datePickerDialog.getDatePicker().getX();
            }
        });

        pic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent getIntent = new Intent(Intent.ACTION_GET_CONTENT);
                getIntent.setType("image/*");

                Intent pickIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                pickIntent.setType("image/*");

                Intent chooserIntent = Intent.createChooser(getIntent, "Select Image");
                chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, new Intent[] {pickIntent});

                startActivityForResult(chooserIntent, PICK_IMAGE);
            }
        });

        back3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("3");
                CURPAGE = 1;
                revAnimate(img, 700);
                onwards.setText("onwards");
            }
        });
        back2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("2");
                CURPAGE = 0;
                revAnimate(birth, 700);
            }
        });
        back1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(SetupAccount.this, MyAccountActivity.class);
                startActivity(i);
            }
        });

        onwards.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (CURPAGE){
                    case 0:
                        if (TextUtils.isEmpty(name.getText().toString())) {
                            name.setError("Name Field cannot be left empty");

                        }else if(TextUtils.isEmpty(email.getText().toString())){
                            email.setError("Email ID Field cannot be left empty");
                        }
                        else {
                            CURPAGE = 1;
                            animate(birth);
                        }
                        break;
                    case 1:
                        if (TextUtils.isEmpty(dob.getText().toString())) {
                            dob.setError("Date Of Birth Field cannot be left empty");
                        }else if(TextUtils.isEmpty(contact.getText().toString())){
                            contact.setError("Mobile Number cannot be left empty");
                        }else if(TextUtils.isEmpty(password.getText().toString())){
                            password.setError("Password cannot be left empty");
                        }
                        else {
                            CURPAGE = 3;
                            animate(done);
                        }
                        break;
                    case 2:
                        CURPAGE = 3;
                        animate(done);

                        break;
                    case 3:

                        onwards.setText("Okay");
                        Boolean check;
//                        sharedPreferences = getSharedPreferences(APPCRED, MODE_PRIVATE);
                        check=sharedPreferences.getBoolean(ISLOGIN, false);
//                        editor = sharedPreferences.edit();
                        if(check==false)
                            InsertData(contact.getText().toString(),name.getText().toString(),email.getText().toString(),password.getText().toString());
                        else
                            UpdateMyaccData(name.getText().toString(),email.getText().toString(), contact.getText().toString());
//                        editor.putString(NAME, name.getText().toString());
//                        editor.putString(EMAIL, email.getText().toString());
//                        editor.putString(DOB, dob.getText().toString());
//                        editor.putBoolean(ISLOGIN, true);
//                        editor.apply();
//
//
//                        Intent i = new Intent(SetupAccount.this, MainActivity.class);
//                        startActivity(i);
//                        finish();
                        break;
                }
            }
        });

    }
    private void animate(View view){
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        float displayWidth = size.x;
        ObjectAnimator animator = ObjectAnimator.ofFloat(view, "translationX", displayWidth, 0f);
        animator.setDuration(700);
        animator.setInterpolator(new AccelerateDecelerateInterpolator());
        animator.start();
    }
    private void revAnimate(View view, int time){
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        float displayWidth = size.x;
        ObjectAnimator animator = ObjectAnimator.ofFloat(view, "translationX", 0f, displayWidth);
        animator.setDuration(time);
        animator.setInterpolator(new AccelerateDecelerateInterpolator());
        animator.start();
//        view.setVisibility(View.VISIBLE);
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        dob.setText(dayOfMonth + "/" + (month+1) + "/" + year);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == PICK_IMAGE){
            System.out.println(data.getDataString());
            editor.putString(IMGURI, data.getDataString());
            editor.apply();
            System.out.println("pict" + sharedPreferences.getString(IMGURI, null));
            Glide.with(SetupAccount.this).load(data.getData()).placeholder(R.drawable.ic_iconmonstr_user_19).into(pic);

        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        switch (CURPAGE){
            case 0:
                Intent i = new Intent(SetupAccount.this, MyAccountActivity.class);
                startActivity(i);
                finish();
                break;
            case 1:
                CURPAGE = 0;
                revAnimate(birth, 700);
                break;
            case 2:
                CURPAGE = 1;
                revAnimate(img, 700);
                onwards.setText("Onwards");
                break;
            case 3:
                finish();
                break;
        }
    }

    public void InsertData(String phone, String name, String email,String pwd){

        SendPostReqAsyncTask sendPostReqAsyncTask = new SendPostReqAsyncTask();
        System.out.println(name);
        System.out.println(phone);
        System.out.println(email);
        sendPostReqAsyncTask.execute(phone,name, email,pwd);

    }

    class SendPostReqAsyncTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {

            String ServerURL = "https://www.gsered.com/intern/grp10/GSE_App/signup_supporter.php" ;
            String text="";
            String phone = params[0] ;
            phone=phone.replace("+91","");
            phone = phone.replaceAll("[()\\s-]", "");

            String name = params[1] ;
            String email = params[2] ;
            String pwd = params[3] ;


            try {

                URL url = new URL(ServerURL);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                OutputStream os = httpURLConnection.getOutputStream();
                System.out.println("USER DETAILS:  ");
                System.out.println(name);
                System.out.println(phone);
                System.out.println(email);
                String data = URLEncoder.encode("name", "UTF-8") + "=" + URLEncoder.encode(name, "UTF-8") + "&" +
                        URLEncoder.encode("phone", "UTF-8") + "=" + URLEncoder.encode(phone, "UTF-8") + "&" +
                        URLEncoder.encode("email", "UTF-8") + "=" + URLEncoder.encode(email, "UTF-8") + "&" +
                        URLEncoder.encode("nid", "UTF-8") + "=" + URLEncoder.encode("7", "UTF-8") + "&" +
                        URLEncoder.encode("pwd", "UTF-8") + "=" + URLEncoder.encode(pwd, "UTF-8");
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
                    System.out.println(" *** Data inserted in db *** ");

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

            String[] response=result.split(",");
            if(result.equalsIgnoreCase("noresult"))
            {
                msg="Email or contact number already exist!";
            }
            else if(response[0].equalsIgnoreCase("1"))
            {
                msg=response[1];
                userid=response[2];
            }

            Toast.makeText(SetupAccount.this, msg, Toast.LENGTH_SHORT).show();
            editor.putString(NAME, name.getText().toString());
            editor.putString(EMAIL, email.getText().toString());
            editor.putString(DOB, dob.getText().toString());
//            editor.putBoolean(ISLOGIN, true);
            editor.apply();

            Intent redirect = new Intent(SetupAccount.this, LoginActivity.class);
            startActivity(redirect);
            finish();

        }
    }


    //    update date code
    public void UpdateMyaccData(String name,String email,String phone){
        SendPostReqAsyncTask2 sendPostReqAsyncTask = new SendPostReqAsyncTask2();
        System.out.println(name);
        System.out.println(email);
        System.out.println(phone);

        sendPostReqAsyncTask.execute(name,email,phone);

    }

    class SendPostReqAsyncTask2 extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {

            String ServerURL = "https://www.gsered.com/intern/grp10/GSE_App/update_myacc.php" ;
            String text="";
            String name = params[0] ;
            String email =params[1] ;
            String phone = params[2] ;

            phone=phone.replace("+91","");
            phone = phone.replaceAll("[()\\s-]", "");
            try {

                URL url = new URL(ServerURL);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                OutputStream os = httpURLConnection.getOutputStream();
                System.out.println("PHONE:  "+phone);
                System.out.println("Name: "+name);
                System.out.println("Email: "+email);
                String data = URLEncoder.encode("name", "UTF-8") + "=" + URLEncoder.encode(name, "UTF-8") + "&" +
                        URLEncoder.encode("phone", "UTF-8") + "=" + URLEncoder.encode(phone, "UTF-8") + "&" +
                        URLEncoder.encode("email", "UTF-8") + "=" + URLEncoder.encode(email, "UTF-8");
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
                    System.out.println(" *** My Account Data updated in db *** ");

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

            Toast.makeText(SetupAccount.this, "MyAccount Data Updated Successfully", Toast.LENGTH_LONG).show();
            editor.putString(NAME, name.getText().toString());
            editor.putString(EMAIL, email.getText().toString());
            editor.putString(DOB, dob.getText().toString());
            editor.putBoolean(ISLOGIN, true);
            editor.apply();


            Intent i = new Intent(SetupAccount.this, MainActivity.class);
            startActivity(i);
            finish();

        }
    }

}

