package com.gsered.gse;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class write_us extends AppCompatActivity {

    private Button submitbtn;
    private EditText descriptiontext;

    String bodytext,subtext;
    private static String APPCRED = "SIGNCRED";
    private static String PHONE = "PHONE";
    private static String usersngo = "usersngo";
    private static String NAME = "NAME";
    private static String EMAIL = "EMAIL";
    private static String ISLOGIN = "ISLOGIN";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write_us);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Write us");

        descriptiontext = findViewById(R.id.describetext1);
        bodytext = descriptiontext.getText().toString();

        submitbtn = (Button)findViewById(R.id.submitbutton);
        submitbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

        SharedPreferences sharedPreferences = getSharedPreferences(APPCRED, MODE_PRIVATE);
        Boolean checklogin = sharedPreferences.getBoolean(ISLOGIN, false);
        String user_name = sharedPreferences.getString(NAME, "");
        String user_email = sharedPreferences.getString(EMAIL, "");
        String user_phone = sharedPreferences.getString(PHONE, "");

        Intent i = new Intent(Intent.ACTION_SEND);
        i.setType("message/html");
        i.putExtra(Intent.EXTRA_EMAIL, new String[]{"elite@globalsuperelite.com"});
        i.putExtra(Intent.EXTRA_SUBJECT, "Query from GSE Red App user");

        if(checklogin)
            i.putExtra(Intent.EXTRA_TEXT, "User Detail - \nName : "+user_name+"\nEmail : "+user_email+"\nContact : "+user_phone+"\n\nMessage : \n"+descriptiontext.getText());
        else
            i.putExtra(Intent.EXTRA_TEXT, "User Detail - \nNot logged in\n\nMessage : \n"+descriptiontext.getText());

        try {
            startActivity(Intent.createChooser(i, "Send feedback..."));
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(getApplicationContext(), "There are no email clients installed.", Toast.LENGTH_SHORT).show();
        }

//                String mailto = "preranaggupta@gmail.com" +
//                        "&subject=" + Uri.encode("GSE RED App Feedback") +
//                        "&body=" + Uri.encode(bodytext);
//
//                Intent emailIntent = new Intent(Intent.ACTION_SEND);
//                emailIntent.setData(Uri.parse(mailto));
//
//                try {
//                    startActivity(Intent.createChooser(emailIntent, "Send mail..."));
//
//                } catch (ActivityNotFoundException e) {
//                    //TODO: Handle case where no email app is available
//                    Toast.makeText(WriteUsActivity.this, "There are no email clients installed.", Toast.LENGTH_SHORT).show();
//                }




//                String[] TO = {"globalsuperelite@gmail.com"};
//                String[] CC = {""};
//                Intent emailIntent = new Intent(Intent.ACTION_SEND);
//
//                emailIntent.setData(Uri.parse("mailto:"));
//                emailIntent.setType("text/plain");
//                emailIntent.putExtra(Intent.EXTRA_EMAIL, TO);
//                emailIntent.putExtra(Intent.EXTRA_CC, CC);
//                emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Application feedback");
//                emailIntent.putExtra(Intent.EXTRA_TEXT, bodytext);
//
//                try {
//                    startActivity(Intent.createChooser(emailIntent, "Send mail..."));
//                    finish();
//                } catch (android.content.ActivityNotFoundException ex) {
//                    Toast.makeText(WriteUsActivity.this, "There is no email client installed.", Toast.LENGTH_SHORT).show();
//                }
            }
        });
    }
}
