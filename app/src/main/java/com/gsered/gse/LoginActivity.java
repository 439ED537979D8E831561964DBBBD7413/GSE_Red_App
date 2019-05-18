package com.gsered.gse;

import android.animation.ValueAnimator;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.telephony.PhoneNumberFormattingTextWatcher;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.FirebaseTooManyRequestsException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.gson.JsonParser;
import com.gsered.gse.account.SetupAccount;
import com.hbb20.CountryCodePicker;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.lang.reflect.Array;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "LoginActivity";

    private static final String KEY_VERIFY_IN_PROGRESS = "key_verify_in_progress";

    private static final int STATE_INITIALIZED = 1;
    private static final int STATE_CODE_SENT = 2;
    private static final int STATE_VERIFY_FAILED = 3;
    private static final int STATE_VERIFY_SUCCESS = 4;
    private static final int STATE_SIGNIN_FAILED = 5;
    private static final int STATE_SIGNIN_SUCCESS = 6;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private static final String URL_FOR_LOGIN = "https://www.gsered.com/intern/grp10/GSE_App/login_data.php";
    private static String NAME = "NAME";
    private static String PHONE = "PHONE";
    private static String EMAIL = "EMAIL";
    private static String PASSWORD = "PASSWORD";
    private static String NGO_ID = "NGOID";
    private static String usersngo = "usersngo";
    private static String USER_ID = "USERID";
    private static String ISLOGIN = "ISLOGIN";
    private static String APPCRED = "SIGNCRED";
    // [START declare_auth]
    private FirebaseAuth mAuth;
    // [END declare_auth]

    private boolean mVerificationInProgress = false;
    private String mVerificationId;
    private PhoneAuthProvider.ForceResendingToken mResendToken;
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks;


    private RelativeLayout bkgView, formView,fullotp_view;
    //    private TextView StatusText;
    private TextView DetailText;

    private EditText phno_et;
    private EditText code_et;

    private Button confButton;
    private Button mVerifyButton;
    //    private Button mResendButton;
    String number;
    CountryCodePicker ccp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Restore instance state
        if (savedInstanceState != null) {
            onRestoreInstanceState(savedInstanceState);
        }

        fullotp_view = findViewById(R.id.Auth_view);
        bkgView = findViewById(R.id.bkg_view);
        formView = findViewById(R.id.form_view);

        fullotp_view = findViewById(R.id.Auth_view);
        DetailText = findViewById(R.id.otp_form_status);

//        phno_et = findViewById(R.id.otp_form_ph);
//        dynamic phone no. spacing
        phno_et = (EditText) findViewById(R.id.otp_form_ph);
        ccp = (CountryCodePicker) findViewById(R.id.ccp);
        ccp.registerCarrierNumberEditText(phno_et);

        phno_et.setSelection(phno_et.getText().length());
        phno_et.addTextChangedListener(new PhoneNumberFormattingTextWatcher());
//        spacing ends here

        code_et = findViewById(R.id.otp_form_ph_ver);

        confButton = findViewById(R.id.otp_form_bt);
//        mVerifyButton = findViewById(R.id.button_verify_phone);
//        mResendButton = findViewById(R.id.button_resend);


        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int initHeight = displayMetrics.heightPixels;
        int height2 = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 200, getResources().getDisplayMetrics());;
        getToggleAnimation(bkgView, initHeight, height2).start();

        TextView textView = findViewById(R.id.newsignup);
        String text = "New to GSE Red? SIGN UP";

//        clicking on signup
        SpannableString ss = new SpannableString(text);
        ClickableSpan clickableSpan = new ClickableSpan() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(LoginActivity.this, SignupActivity.class);
                startActivity(i);
            }
        };

        ss.setSpan(clickableSpan,16,23, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        textView.setText(ss);
        textView.setMovementMethod(LinkMovementMethod.getInstance());
        textView.setVisibility(View.VISIBLE);

        // Assign click listeners
        confButton.setOnClickListener(this);
//        mVerifyButton.setOnClickListener(this);
//        mResendButton.setOnClickListener(this);
//        mSignOutButton.setOnClickListener(this);

        // [START initialize_auth]
        mAuth = FirebaseAuth.getInstance();
        // [END initialize_auth]

        SharedPreferences sharedPreferences = getSharedPreferences(APPCRED, MODE_PRIVATE);
        Boolean checklogin = sharedPreferences.getBoolean(ISLOGIN, false);
        if(checklogin ==false) {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString(NAME, "");
            editor.putString(EMAIL, "");
            editor.putString(PHONE, "");
            editor.putString(usersngo, "");
            editor.apply();
            mAuth.signOut();
        }

        // Initialize phone auth callbacks
        // [START phone_auth_callbacks]
        mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

            @Override
            public void onVerificationCompleted(PhoneAuthCredential credential) {
                // This callback will be invoked in two situations:
                // 1 - Instant verification. In some cases the phone number can be instantly
                //     verified without needing to send or enter a verification code.
                // 2 - Auto-retrieval. On some devices Google Play services can automatically
                //     detect the incoming verification SMS and perform verification without
                //     user action.
                Log.d(TAG, "onVerificationCompleted:" + credential);
                // [START_EXCLUDE silent]
                mVerificationInProgress = false;
                // [END_EXCLUDE]

                // [START_EXCLUDE silent]
                // Update the UI and attempt sign in with the phone credential
                updateUI(STATE_VERIFY_SUCCESS, credential);
                // [END_EXCLUDE]
                signInWithPhoneAuthCredential(credential);
            }

            @Override
            public void onVerificationFailed(FirebaseException e) {
                // This callback is invoked in an invalid request for verification is made,
                // for instance if the the phone number format is not valid.
                Log.w(TAG, "onVerificationFailed", e);
                // [START_EXCLUDE silent]
                mVerificationInProgress = false;
                // [END_EXCLUDE]

                if (e instanceof FirebaseAuthInvalidCredentialsException) {
                    // Invalid request
                    // [START_EXCLUDE]
                    phno_et.setError("Invalid phone number.");
                    // [END_EXCLUDE]
                } else if (e instanceof FirebaseTooManyRequestsException) {
                    // The SMS quota for the project has been exceeded
                    // [START_EXCLUDE]
                    Snackbar.make(findViewById(android.R.id.content), "Quota exceeded.",
                            Snackbar.LENGTH_SHORT).show();
                    // [END_EXCLUDE]
                }

                // Show a message and update the UI
                // [START_EXCLUDE]
                updateUI(STATE_VERIFY_FAILED);
                // [END_EXCLUDE]
            }

            @Override
            public void onCodeSent(String verificationId,
                                   PhoneAuthProvider.ForceResendingToken token) {
                // The SMS verification code has been sent to the provided phone number, we
                // now need to ask the user to enter the code and then construct a credential
                // by combining the code with a verification ID.
                Log.d(TAG, "onCodeSent:" + verificationId);
                code_et.setVisibility(View.VISIBLE);
//                Toast.makeText(LoginActivity.this, "Code has been sent to the number", Toast.LENGTH_SHORT).show();
                // Save verification ID and resending token so we can use them later
                mVerificationId = verificationId;
                mResendToken = token;
                // [START_EXCLUDE]
                // Update UI
                updateUI(STATE_CODE_SENT);
                // [END_EXCLUDE]
            }
        };
        // [END phone_auth_callbacks]
    }

    // [START on_start_check_user]
    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        updateUI(currentUser);

        number = ccp.getFullNumberWithPlus();
        // [START_EXCLUDE]
        if (mVerificationInProgress && validatePhoneNumber()) {
            startPhoneNumberVerification(number);
        }
        // [END_EXCLUDE]
    }
    // [END on_start_check_user]

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(KEY_VERIFY_IN_PROGRESS, mVerificationInProgress);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        mVerificationInProgress = savedInstanceState.getBoolean(KEY_VERIFY_IN_PROGRESS);
    }


    private void startPhoneNumberVerification(String phoneNumber) {
        // [START start_phone_auth]
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                phoneNumber,        // Phone number to verify
                60,                 // Timeout duration
                TimeUnit.SECONDS,   // Unit of timeout
                this,               // Activity (for callback binding)
                mCallbacks);        // OnVerificationStateChangedCallbacks
        // [END start_phone_auth]

        mVerificationInProgress = true;
    }

    private void verifyPhoneNumberWithCode(String verificationId, String code) {
        // [START verify_with_code]
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationId, code);
        // [END verify_with_code]
        signInWithPhoneAuthCredential(credential);
    }

    // [START resend_verification]
    private void resendVerificationCode(String phoneNumber,
                                        PhoneAuthProvider.ForceResendingToken token) {
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                phoneNumber,        // Phone number to verify
                60,                 // Timeout duration
                TimeUnit.SECONDS,   // Unit of timeout
                this,               // Activity (for callback binding)
                mCallbacks,         // OnVerificationStateChangedCallbacks
                token);             // ForceResendingToken from callbacks
    }
    // [END resend_verification]

    // [START sign_in_with_phone]
    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithCredential:success");

                            Toast.makeText(LoginActivity.this, "Successfully Logged In", Toast.LENGTH_SHORT).show();

                            FirebaseUser user = task.getResult().getUser();
                            // [START_EXCLUDE]
                            updateUI(STATE_SIGNIN_SUCCESS, user);
                            // [END_EXCLUDE]
                        } else {
                            // Sign in failed, display a message and update the UI
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                // The verification code entered was invalid
                                // [START_EXCLUDE silent]
                                code_et.setError("Invalid code.");
                                // [END_EXCLUDE]
                            }
                            // [START_EXCLUDE silent]
                            // Update UI
                            updateUI(STATE_SIGNIN_FAILED);
                            // [END_EXCLUDE]
                        }
                    }
                });
    }
    // [END sign_in_with_phone]

    private void signOut() {
        mAuth.signOut();
        updateUI(STATE_INITIALIZED);
    }

    private void updateUI(int uiState) {
        updateUI(uiState, mAuth.getCurrentUser(), null);
    }

    private void updateUI(FirebaseUser user) {
        if (user != null) {
            updateUI(STATE_SIGNIN_SUCCESS, user);
        } else {
            updateUI(STATE_INITIALIZED);
        }
    }

    private void updateUI(int uiState, FirebaseUser user) {
        updateUI(uiState, user, null);
    }

    private void updateUI(int uiState, PhoneAuthCredential cred) {
        updateUI(uiState, null, cred);
    }

    private void updateUI(int uiState, FirebaseUser user, PhoneAuthCredential cred) {
        switch (uiState) {
            case STATE_INITIALIZED:
                // Initialized state, show only the phone number field and start button
                enableViews(confButton, phno_et);
                disableViews(code_et);
                DetailText.setText(null);
                break;
            case STATE_CODE_SENT:
                // Code sent state, show the verification field, the
                enableViews(phno_et, code_et);
                disableViews(confButton);
                DetailText.setText("Code has been Sent");
                break;
            case STATE_VERIFY_FAILED:
                // Verification has failed, show all options
                enableViews(confButton, phno_et,code_et);
                DetailText.setText("Verification Failed");
                break;
            case STATE_VERIFY_SUCCESS:
                // Verification has succeeded, proceed to firebase sign in
                disableViews(confButton, phno_et,code_et);
                DetailText.setText("Verification Successful");

                // Set the verification text based on the credential
                if (cred != null) {
                    if (cred.getSmsCode() != null) {
                        code_et.setText(cred.getSmsCode());
                    } else {
                        code_et.setText("Hey there");
                    }
                }
                SharedPreferences sharedPreferences = getSharedPreferences(APPCRED, MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                String set_ph=phno_et.getText().toString();
                set_ph=set_ph.replace("+91","");
                set_ph = set_ph.replaceAll("[()\\s-]", "");
                editor.putString(PHONE, set_ph);
                editor.apply();

               break;
            case STATE_SIGNIN_FAILED:
                // No-op, handled by sign-in check
                DetailText.setText("Sign-In Failed");
                break;
            case STATE_SIGNIN_SUCCESS:
                // Np-op, handled by sign-in check

                break;
        }

        if (user == null) {
            // Signed out
            fullotp_view.setVisibility(View.VISIBLE);
            // mSignedInViews.setVisibility(View.GONE);

            //mStatusText.setText(R.string.signed_out);
        } else {
            // Signed in
            fullotp_view.setVisibility(View.GONE);
//            mSignedInViews.setVisibility(View.VISIBLE);


            String ph=phno_et.getText().toString();
            ph=ph.replace("+91","");
            ph = ph.replaceAll("[()\\s-]", "");
            loginUser(ph);                                                  //check user
            enableViews(phno_et, code_et);
            // phno_et.setText(null);
            // code_et.setText(null);

            DetailText.setText("Sign-In Success");
            DetailText.setText(getString(R.string.firebase_database_url, user.getUid()));

        }
    }

    private boolean validatePhoneNumber() {
        String phoneNumber = phno_et.getText().toString();
        if (TextUtils.isEmpty(phoneNumber)) {
            phno_et.setError("Invalid phone number.");
            return false;
        }

        return true;
    }

    private void enableViews(View... views) {
        for (View v : views) {
            v.setEnabled(true);
        }
    }

    private void disableViews(View... views) {
        for (View v : views) {
            v.setEnabled(false);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.otp_form_bt:
                confButton.setBackgroundResource(R.drawable.loginbutton_color);
                // SLEEP 1 SECONDS HERE ...
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    public void run() {
                        confButton.setBackgroundResource(R.drawable.loginbutton);
                    }
                }, 250);
                if (!validatePhoneNumber()) {
                    return;
                }

                number = ccp.getFullNumberWithPlus();
                startPhoneNumberVerification(number);
                break;
//            case R.id.button_verify_phone:
//                String code = code_et.getText().toString();
//                if (TextUtils.isEmpty(code)) {
//                    code_et.setError("Cannot be empty.");
//                    return;
//                }
//
//                verifyPhoneNumberWithCode(mVerificationId, code);
//                break;
//            case R.id.button_resend:
//                resendVerificationCode(phno_et.getText().toString(), mResendToken);
//                break;

        }
    }

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
        animator.setDuration(2000);
        return animator;
    }

    public void loginUser( final String phoneno) {
        // Tag used to cancel the request
        String cancel_req_tag = "login";
//        progressDialog.setMessage("Logging you in...");

        StringRequest strReq = new StringRequest(Request.Method.POST,
                URL_FOR_LOGIN, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                System.out.println("Register Response: " + response.toString());        //print php response
                try {

                    JSONObject jObj = new JSONObject(response);
                    boolean error = jObj.getBoolean("error");

                    if (error) {               //existing user found

//                        String errorMsg = jObj.getString("flag");
//                        Toast.makeText(getApplicationContext(),
//                                errorMsg, Toast.LENGTH_LONG).show();
                        startActivity(new Intent(LoginActivity.this, SetupAccount.class));
                        finish();



                    } else{                        //new app user

                        String fullname = jObj.getJSONObject("user").getString("name");
                        String phone = jObj.getJSONObject("user").getString("phone");
                        String password = jObj.getJSONObject("user").getString("password");
                        String email = jObj.getJSONObject("user").getString("email");
                        String ngoid = jObj.getJSONObject("user").getString("ngo_id");
                        String userid = jObj.getJSONObject("user").getString("user_id");
                        String nname = jObj.getJSONObject("user").getString("ngo_name");

                        System.out.println(fullname);
                        System.out.println(password);
                        SharedPreferences sharedPreferences = getSharedPreferences(APPCRED, MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString(NAME, fullname);
                        editor.putString(EMAIL, email);
                        editor.putString(PASSWORD, password);
                        editor.putString(NGO_ID, ngoid);
                        editor.putString(usersngo, nname);
                        editor.putString(USER_ID, userid);

                        editor.putBoolean(ISLOGIN, true);
                        editor.apply();
                        // Launch Main activity
                        Intent intent = new Intent(
                                getApplicationContext(),
                                MainActivity.class);

                        startActivity(intent);
                        finish();

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println("Login Error: " + error.getMessage());
                Toast.makeText(getApplicationContext(),
                        error.getMessage(), Toast.LENGTH_LONG).show();

            }
        })


        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                // Posting params to login url
                Map<String,String> params = new HashMap<String,String>();
                params.put("phone", phoneno);
                params.put("app", "app");
                return params;
            }
        };
        // Adding request to request queue
        AppSingleton.getInstance(getApplicationContext()).addToRequestQueue(strReq,cancel_req_tag);
    }
}
