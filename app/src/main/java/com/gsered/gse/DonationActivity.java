package com.gsered.gse;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.razorpay.Checkout;
import com.razorpay.PaymentResultListener;
import org.json.JSONObject;

import java.util.Random;

public class DonationActivity extends Activity implements PaymentResultListener {
    private static final String TAG = DonationActivity.class.getSimpleName();

    private EditText amount;
    private TextView usersupportingngo;

    private static String APPCRED = "SIGNCRED";
    private static String NGO_ID = "NGOID";
    private static String USER_ID = "USERID";
    private static String PHONE = "PHONE";
    private static String usersngo = "usersngo";
    private static String NAME = "NAME";
    private static String EMAIL = "EMAIL";
    private static String ISLOGIN = "ISLOGIN";
    private String pay;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_donation);

        /*
         To ensure faster loading of the Checkout form,
          call this method as early as possible in your checkout flow.
         */
        Checkout.preload(getApplicationContext());

        SharedPreferences sharedPrefs = getSharedPreferences(APPCRED, MODE_PRIVATE);
        Boolean checklogin = sharedPrefs.getBoolean(ISLOGIN, false);
        String user_support_ngo = sharedPrefs.getString(usersngo,"");

        usersupportingngo = (TextView) findViewById(R.id.supportingngo);
        if(checklogin == true){
            usersupportingngo.setText("Donate to \n"+user_support_ngo);
        }else{
            usersupportingngo.setText("Donate");
        }

        // Payment button created by you in XML layout
        Button button = (Button) findViewById(R.id.btn_pay);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startPayment();
            }
        });
    }

    public void startPayment() {
        /**
         * You need to pass current activity in order to let Razorpay create CheckoutActivity
         */
        final Activity activity = this;

        final Checkout co = new Checkout();

        SharedPreferences sharedPreferences = getSharedPreferences(APPCRED, MODE_PRIVATE);
        Boolean checklogin = sharedPreferences.getBoolean(ISLOGIN, false);
        String ngoid =sharedPreferences.getString(NGO_ID, "");
        String emailid =sharedPreferences.getString(EMAIL, "");
        String user =sharedPreferences.getString(NAME, "");
        String contact =sharedPreferences.getString(PHONE, "");
        String user_id = sharedPreferences.getString(USER_ID,"");
        String user_ngo = sharedPreferences.getString(usersngo,"");

        amount = (EditText) findViewById(R.id.amount);
        pay = amount.getText().toString();
        double ipt1 = Double.valueOf(pay);
        double ipt2 = Double.valueOf(pay) * 0.06;
        double ipt3 = (ipt1 + ipt2) * 100;
        String amt = Double.toString(ipt3);

        String purpose = "beforedonation";
        String pinfo = "app-"+ngoid;

        Random rdn = new Random();
        int n = 10000000 + rdn.nextInt(90000000);
        String txnid = "Txn"+n;

        donationbackground donationbg = new donationbackground(this);
        donationbg.execute(purpose,user,contact,emailid,pinfo,user_id,pay,txnid);

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("txnid",txnid);
        editor.commit();

        try {
            JSONObject options = new JSONObject();
            options.put("name", "GSE Red");
            options.put("description", "");
            //You can omit the image option to fetch the image from dashboard
            options.put("image", "https://www.gsered.com/images/logo.png");
            options.put("currency", "INR");
            options.put("amount", amt);

            JSONObject preFill = new JSONObject();
            preFill.put("email", emailid);
            preFill.put("contact", contact);

            options.put("prefill", preFill);

            co.open(activity, options);
        } catch (Exception e) {
            Toast.makeText(activity, "payment status: " + e.getMessage(), Toast.LENGTH_SHORT)
                    .show();
            e.printStackTrace();
        }
    }

    /**
     * The name of the function has to be
     * onPaymentSuccess
     * Wrap your code in try catch, as shown, to ensure that this method runs correctly
     */
    @SuppressWarnings("unused")
    @Override
    public void onPaymentSuccess(String razorpayPaymentID) {
        try {
            Toast.makeText(this, "Donation Successful", Toast.LENGTH_SHORT).show();

            SharedPreferences sharedPreferences = getSharedPreferences(APPCRED, MODE_PRIVATE);
            Boolean checklogin = sharedPreferences.getBoolean(ISLOGIN, false);
            String ngoid =sharedPreferences.getString(NGO_ID, "");
            String emailid =sharedPreferences.getString(EMAIL, "");
            String user =sharedPreferences.getString(NAME, "");
            String contact =sharedPreferences.getString(PHONE, "");
            String user_id = sharedPreferences.getString(USER_ID,"");
            String user_ngo = sharedPreferences.getString(usersngo,"");
            String txnid = sharedPreferences.getString("txnid","");
            String purpose = "afterdonation";
            String pinfo = "app-"+ngoid;

            donationbackground donationbg = new donationbackground(this);
            donationbg.execute(purpose,user,contact,emailid,pinfo,txnid,pay);

        } catch (Exception e) {
            Log.e(TAG, "Exception in onPaymentSuccess", e);
        }
    }

    /**
     * The name of the function has to be
     * onPaymentError
     * Wrap your code in try catch, as shown, to ensure that this method runs correctly
     */
    @SuppressWarnings("unused")
    @Override
    public void onPaymentError(int code, String response) {
        try {
            Toast.makeText(this, "Payment failed: " + code + " " + response, Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Log.e(TAG, "Exception in onPaymentError", e);
        }
    }
}
