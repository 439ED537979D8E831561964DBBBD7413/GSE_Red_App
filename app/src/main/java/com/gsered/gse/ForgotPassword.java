package com.gsered.gse;

import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class ForgotPassword extends AppCompatActivity {

    private TextInputLayout textInputEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Forgot Password");

        textInputEmail = findViewById(R.id.text_input_email);
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

    public void senduserpassword(View v){
        if(!validateEmail()){
            return;
        }

        String email = textInputEmail.getEditText().getText().toString().trim();
        String purpose = "forgot";
        donationbackground send_password = new donationbackground(this);
        send_password.execute(purpose,email);
    }

}
