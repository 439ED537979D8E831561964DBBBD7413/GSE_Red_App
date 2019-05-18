package com.gsered.gse;

import android.animation.ValueAnimator;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.net.wifi.hotspot2.pps.Credential;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.credentials.CredentialPickerConfig;
import com.google.android.gms.auth.api.credentials.HintRequest;
import com.google.android.gms.auth.api.phone.SmsRetriever;
import com.google.android.gms.auth.api.phone.SmsRetrieverClient;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.common.util.NumberUtils;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.i18n.phonenumbers.NumberParseException;
import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.Phonenumber;
import com.google.i18n.phonenumbers.Phonenumber.PhoneNumber;
import com.hbb20.CountryCodePicker;

import org.jsoup.select.Evaluator;

public class SigninActivity extends AppCompatActivity implements View.OnClickListener{

    private static String NAME = "NAME";
    private static String PHONE = "PHONE";
    private static String EMAIL = "EMAIL";
    private static String PASSWORD = "PASSWORD";
    private static String NGO_ID = "NGOID";
    private static String usersngo = "usersngo";
    private static String USER_ID = "USERID";
    private static String ISLOGIN = "ISLOGIN";
    private static String APPCRED = "SIGNCRED";

    private RelativeLayout bkgView;
    private TextInputLayout textinputusername;
    private TextInputLayout textinputpassword;
    private EditText username,password;
    private Button signin;
    private FrameLayout progressbarhandler;
    GoogleApiClient apiClient;
    int RESOLVE_HINT = 1;
    boolean showhintpicker = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);

        bkgView = findViewById(R.id.bkg_view);
        textinputusername = findViewById(R.id.text_input_username);
        textinputpassword = findViewById(R.id.text_input_password);
        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        signin = findViewById(R.id.signin);
        progressbarhandler = findViewById(R.id.progressBarHolder);

        username.setOnClickListener(this);
        signin.setOnClickListener(this);

        startanimation();
        setclick((TextView) findViewById(R.id.newsignup),"New to GSE Red? SIGN UP",16,23, SigninActivity.this, SignupActivity.class);
        setclick((TextView) findViewById(R.id.forgot),"Forgot password? click",17,22, SigninActivity.this, ForgotPassword.class);

        apiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, new GoogleApiClient.OnConnectionFailedListener() {
                    @Override
                    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
                        Toast.makeText(SigninActivity.this, "Client Connection Failed : "+connectionResult.getErrorMessage(), Toast.LENGTH_SHORT).show();
                    }
                })
                .addApi(Auth.CREDENTIALS_API)
                .build();
    }

    //function for animation
    private ValueAnimator getToggleAnimation(final android.view.View view , int startHeight , int endHeight) {
        ValueAnimator animator = ValueAnimator.ofInt(startHeight,endHeight);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int val = (Integer)animation.getAnimatedValue();
                RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) view.getLayoutParams();
                params.height = val;
                view.setLayoutParams(params);
            }
        });
        animator.setDuration(1300);
        return animator;
    }


    public void startanimation(){
        // animation start
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int initHeight = displayMetrics.heightPixels;
        int height2 = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 200, getResources().getDisplayMetrics());;
        getToggleAnimation(bkgView, initHeight, height2).start();
        // animation end
    }

    public void createdialog(){
    }

    public void setclick(TextView textView, String text, int start, int end, final Context source, final Class destination){
        // clicking on signup
        SpannableString ss = new SpannableString(text);
        ClickableSpan clickableSpan = new ClickableSpan() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(source, destination);
                startActivity(i);
            }
        };
        ss.setSpan(clickableSpan,start,end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        textView.setText(ss);
        textView.setMovementMethod(LinkMovementMethod.getInstance());
        textView.setVisibility(View.VISIBLE);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.signin:
                String uname = String.valueOf(username.getText().toString().trim());
                String pass = String.valueOf(password.getText());
                if(validate(uname,pass))
                    verifyuser(uname,pass);
                break;
            case R.id.username:
                if(showhintpicker){
                    try {
                        requestHint();
                    } catch (IntentSender.SendIntentException e) {
                        e.printStackTrace();
                    }
                }
                break;
        }
    }

    // Construct a request for phone numbers and show the picker
    private void requestHint() throws IntentSender.SendIntentException {
        HintRequest hintRequest = new HintRequest.Builder()
                .setHintPickerConfig(new CredentialPickerConfig.Builder()
                        .setPrompt(CredentialPickerConfig.Prompt.SIGN_IN)
                        .build()
                    )
                .setPhoneNumberIdentifierSupported(true)
                .setEmailAddressIdentifierSupported(true)
                .build();

        PendingIntent intent = Auth.CredentialsApi.getHintPickerIntent(
                apiClient, hintRequest);
        startIntentSenderForResult(intent.getIntentSender(),
                RESOLVE_HINT, null, 0, 0, 0);
    }

    // Obtain the phone number from the result
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RESOLVE_HINT) {
            if (resultCode == RESULT_OK) {
                showhintpicker = false;
                com.google.android.gms.auth.api.credentials.Credential credential = data.getParcelableExtra(com.google.android.gms.auth.api.credentials.Credential.EXTRA_KEY);
//                Credential credential = data.getParcelableExtra(Credential.EXTRA_KEY);
                String user = credential.getId();
                PhoneNumberUtil phoneUtil = PhoneNumberUtil.getInstance();
                try {
                    if(NumberUtils.isNumeric(user)){
                        // phone must begin with '+'
                        PhoneNumber number = phoneUtil.parse(user, "");
                        // int countryCode = number.getCountryCode();
                        long nationalnumber = number.getNationalNumber();
                        username.setText(String.valueOf(nationalnumber));
                    } else {
                        username.setText(user);
                    }
                } catch (NumberParseException e) {
                    System.err.println("NumberParseException was thrown: " + e.toString());
                }
            } else {
                // if none of the above is selected
                showhintpicker = false;
            }

        }
    }

    public boolean validate(String username, String password){
        if(username.isEmpty()){
            textinputusername.setError("* Required");
            return false;
        }
        else if(password.isEmpty()){
            textinputpassword.setError("* Required");
            return false;
        }
        return true;
    }

    public void verifyuser(String username, String password){
        String purpose = "signin";
        donationbackground check_signin = new donationbackground(this);
        check_signin.execute(purpose,username,password);
    }
}
