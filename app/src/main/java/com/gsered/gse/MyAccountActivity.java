package com.gsered.gse;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.gsered.gse.account.SetupAccount;

public class MyAccountActivity extends AppCompatActivity {

    private ImageButton edit;
    private ImageView img;
    private TextView name, phno, email, dob;

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
        setContentView(R.layout.activity_my_account);

        edit = findViewById(R.id.acc_view_edit);

        img = findViewById(R.id.myacc_user_img);
        name = findViewById(R.id.myacc_usrnm);
        phno = findViewById(R.id.myacc_card1_phno);
        email = findViewById(R.id.myacc_card1_email);
//        dob = findViewById(R.id.myacc_card1_dob);

        SharedPreferences sharedPreferences = getSharedPreferences(APPCRED, MODE_PRIVATE);
        Boolean checklogin = sharedPreferences.getBoolean(ISLOGIN, false);

        if(checklogin) {
            phno.setText(sharedPreferences.getString(PHONE, ""));
            email.setText(sharedPreferences.getString(EMAIL, ""));
//            dob.setText(sharedPreferences.getString(DOB, ""));
            name.setText(sharedPreferences.getString(NAME, ""));
            System.out.println(sharedPreferences.getString(IMGURI, null));
        }
        else
        {
            phno.setText(sharedPreferences.getString(PHONE, "98756543210"));
            email.setText(sharedPreferences.getString(EMAIL, "john@gmail.com"));
//            dob.setText(sharedPreferences.getString(DOB, "01/01/1990"));
            name.setText(sharedPreferences.getString(NAME, "John Doe"));
            System.out.println(sharedPreferences.getString(IMGURI, null));

        }

//        if(!sharedPreferences.getString(IMGURI, null).equals(null)){
//            Glide.with(MyAccountActivity.this).load(sharedPreferences.getString(IMGURI, null)).asBitmap().centerCrop().into(new BitmapImageViewTarget(img) {
//                @Override
//                protected void setResource(Bitmap resource) {
//                    RoundedBitmapDrawable circularBitmapDrawable =
//                            RoundedBitmapDrawableFactory.create(MyAccountActivity.this.getResources(), resource);
//                    circularBitmapDrawable.setCircular(true);
//                    img.setImageDrawable(circularBitmapDrawable);
//                }
//            });
//        } else {
//            Glide.with(MyAccountActivity.this).load(sharedPreferences.getString(IMGURI,null)).into(img);
//        }

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MyAccountActivity.this, SetupAccount.class);
                startActivity(i);
                finish();
            }
        });


    }
}
