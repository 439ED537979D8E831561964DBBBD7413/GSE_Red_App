package com.gsered.gse;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class SignupActivity extends AppCompatActivity {

    private TextInputLayout textInputName;
    private TextInputLayout textInputEmail;
    private TextInputLayout textInputContact;
    private TextInputLayout textInputPassword;
    private TextInputLayout textInputReferralcode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        textInputName = findViewById(R.id.text_input_name);
        textInputEmail = findViewById(R.id.text_input_email);
        textInputContact = findViewById(R.id.text_input_contact);
        textInputPassword = findViewById(R.id.text_input_password);
        textInputReferralcode = findViewById(R.id.text_input_referralcode);

        String ema="",con="";
        if (getIntent() != null) {
            ema = getIntent().getStringExtra("email");
            con = getIntent().getStringExtra("contact");
        }

        textInputEmail.getEditText().setText(ema);
        textInputContact.getEditText().setText(con);

        TextView textView1 = findViewById(R.id.termscondition);
        String text1 = "By signing up, you agree to Terms and Conditions";
        // clicking on terms & condition
        SpannableString ss1 = new SpannableString(text1);
        ClickableSpan clickableSpan1 = new ClickableSpan() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent("android.intent.action.VIEW", Uri.parse("https://www.gsered.com/terms&condition.php"));
                startActivity(i);
            }
        };
        ss1.setSpan(clickableSpan1,28,48, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        textView1.setText(ss1);
        textView1.setMovementMethod(LinkMovementMethod.getInstance());

        final TextView textView2 = findViewById(R.id.referralcode);
        String text2 = "Have a Referral Code?";
        // clicking on terms & condition
        SpannableString ss2 = new SpannableString(text2);
        ClickableSpan clickableSpan2 = new ClickableSpan() {
            @Override
            public void onClick(View view) {
                textView2.setVisibility(View.GONE);
                textInputReferralcode.setVisibility(View.VISIBLE);
            }
        };
        ss2.setSpan(clickableSpan2,0,21, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        textView2.setText(ss2);
        textView2.setMovementMethod(LinkMovementMethod.getInstance());

    }

    // start log in monitor at right side corner
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.signupmenu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        if(id == R.id.alreadysignin){
            Intent i = new Intent(SignupActivity.this, SigninActivity.class);
            startActivity(i);
        }
        return super.onOptionsItemSelected(item);
    }

    private boolean validateEmail(){
        String email = textInputEmail.getEditText().getText().toString().trim();

        if(email.isEmpty()){
            textInputEmail.setError("* Required");
            return false;
        }else if(!email.matches("[a-zA-Z0-9._-]+@[a-z]+.[a-z]+")){
            textInputEmail.setError("Invalid Email Address");
            return false;
        } else {
            textInputEmail.setError(null);
            return true;
        }
    }

    private boolean validateName(){
        String name = textInputName.getEditText().getText().toString().trim();

        if(name.isEmpty()){
            textInputName.setError("* Required");
            return false;
        }else {
            textInputName.setError(null);
            return true;
        }
    }

    private boolean validatePassword(){
        String password = textInputPassword.getEditText().getText().toString().trim();

        if(password.isEmpty()){
            textInputPassword.setError("* Required");
            return false;
        }else {
            textInputPassword.setError(null);
            return true;
        }
    }

    private boolean validateContact(){
        String contact = textInputContact.getEditText().getText().toString().trim();

        if(contact.isEmpty()){
            textInputContact.setError("* Required");
            return false;
        }else {
            textInputContact.setError(null);
            return true;
        }
    }


    public void registeruser(View v){
        if(!validateEmail() | !validateContact() | !validateName() | !validatePassword()){
            return;
        }

        String email = textInputEmail.getEditText().getText().toString();
        String name = textInputName.getEditText().getText().toString();
        String contact = textInputContact.getEditText().getText().toString();
        String password = textInputPassword.getEditText().getText().toString();
        String referralcode = textInputReferralcode.getEditText().getText().toString();

        String purpose = "signup";
        donationbackground add_signup = new donationbackground(this);
        add_signup.execute(purpose,name,contact,email,password,referralcode);

    }
}
