package sportsfight.com.s.loginmodule;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;

import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import sportsfight.com.s.R;
import sportsfight.com.s.common.AppController;
import sportsfight.com.s.common.Common;
import sportsfight.com.s.interfaces.WebApiResponseCallback;
import sportsfight.com.s.launchingmodule.Test;
import sportsfight.com.s.mainmodule.Dashboard;
import sportsfight.com.s.mainmodule.NewDashBoard;
import sportsfight.com.s.model.RegistrationModel;
import sportsfight.com.s.model.UserProfile;
import sportsfight.com.s.network.WebApiCall;

import sportsfight.com.s.util.Util;
import sportsfight.com.s.util.Validation;

/**
 * Created by Ashish.Kumar on 17-01-2018.
 */

public class Login extends Activity  implements View.OnClickListener,WebApiResponseCallback{
   // Receiver myBroadcastReceiver;
    @BindView(R.id.icon)
    ImageView icon;
    @BindView(R.id.emailId_mobile_edt)
    EditText mobile_email;
    @BindView(R.id.password)
    EditText password;
    @BindView(R.id.login)
    Button login;
    @BindView(R.id.forgetPassword)
    TextView forgetpassword;
    @BindView(R.id.signup)
    TextView signUp;
    AppController controller;
    Dialog dialog;
    int apiCall = 0;
    int loginApiCall = 1, resenOtp = 2, verifyOtp = 3;
    Dialog alertdialog=null;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        initializeAll();
    }

    public void initializeAll() {
        //myBroadcastReceiver = new Receiver();
        Typeface typeface=Typeface.createFromAsset(getApplicationContext().getAssets(), "forget_passwordfont.otf");
        controller=(AppController)getApplicationContext();
        ButterKnife.bind(this);
        password.setTypeface(controller.getDetailsFont());
        mobile_email.setTypeface(controller.getDetailsFont());
        login.setTypeface(controller.getDetailsFont());
        login.setOnClickListener(this);
        signUp.setOnClickListener(this);
        forgetpassword.setOnClickListener(this);
        forgetpassword.setTypeface(typeface);
        mobile_email.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if((  mobile_email.getText().length()>0)&&(Character.isDigit(charSequence.charAt(0)))&&(! mobile_email.getText().toString().contains("@")))
                {
                    icon.setImageResource(R.drawable.mobile);
                }else{
                    icon.setImageResource(R.drawable.username);
                }
            }
            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    public void onResume() {
        super.onResume();
       // registerReceiver(myBroadcastReceiver, new IntentFilter("android.net.conn.CONNECTIVITY_CHANGE"));
    }

    public void onPause() {
        super.onPause();
      //  unregisterReceiver(myBroadcastReceiver);
    }

    @Override
    public void onClick(View view) {
        Intent in=null;
        switch (view.getId())
        {
            case R.id.forgetPassword:

                in=new Intent(Login.this,ForgetPassword.class);
                startActivity(in);
                break;
            case R.id.login:
                if (isFieldsValidated() == true) {
                    if (Util.isNetworkAvailable(Login.this)) {
                        apiCall=loginApiCall;
                        dialog=Util.showPogress(Login.this);
                        JSONObject jsonObject=new JSONObject();
                        controller.getApiCall().login(Common.getLoginUrl(Util.getDeviceID(Login.this), controller.getPrefManager().getFcmToken(),Util.getAppVersion(Login.this)),jsonObject.toString(),mobile_email.getText().toString(), password.getText().toString(), this);
                    }
                }
                break;
            case R.id.signup:
                 in=new Intent(Login.this,Registration.class);
                 Registration.isCalledFromSplash=false;
                startActivity(in);
                break;
        }
    }

    public boolean isFieldsValidated() {
        boolean status = false;
        if (mobile_email.getText().length() > 0) {
            if (mobile_email.getText().toString().contains("@")) {
                if (controller.getValidation().isEmailIdValid(mobile_email)) {

                    if (password.getText().length() > 0) {
                        status = true;
                    } else {
                        Toast.makeText(this, "Please enter  password ", Toast.LENGTH_SHORT).show();
                    }
                }
            } else {
                if (controller.getValidation().isPhoneNumberValid(mobile_email)) {
                    if (password.getText().length() > 0) {
                        status = true;
                    } else {
                        Toast.makeText(this, "Please enter  password ", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        } else {
            Toast.makeText(this, "Please enter email id /mobile number ", Toast.LENGTH_SHORT).show();
        }
        return status;
    }

    @Override
    public void onSucess(String value) {
        if (dialog != null) {
            Util.cancelDialog(Login.this, dialog);
        }
        Util.showToast(Login.this, Util.getMessage(value));
        switch (apiCall) {
            case 1:
                if (Util.isNextScreenReady(value, Login.this)) {
                    Intent in = new Intent(Login.this,NewDashBoard.class);
                    controller.getPrefManager().setUserToken(value);
                    controller.getPrefManager().setUserLoggedIn(true);
                    UserProfile  profile=new UserProfile(Util.getResultJson(value));
                    controller.setUserProfile(profile);
                    Util.startActivityCommon(Login.this, in);
                    finish();
                } else {
                    if (Util.isMobileVerified(value, Login.this) == false) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                resendOtp();
                            }
                        });
                    }
                }
                break;
            case 2:
                if (alertdialog == null) {
                    showAlert();
                }
                break;
            case 3:
                if (Util.getStatus(value)) {
                    if (alertdialog != null) {
                        Util.cancelDialog(Login.this, alertdialog);
                        alertdialog = null;
                    }
                }
                break;
        }
    }

    @Override
    public void onError(String value) {

        Util.showToast(Login.this,Util.getMessage(value));
        if (dialog != null) {
            Util.cancelDialog(Login.this,dialog);
        }


    }

    public void resendOtp() {
        apiCall = resenOtp;
        dialog = Util.showPogress(Login.this);
        JSONObject jsonObject = new JSONObject();
        controller.getApiCall().getData(Common.resendOtpUrl(controller.getTempId()),"", this);

    }

    public void showAlert() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                alertdialog = new Dialog(Login.this);
                alertdialog.setCancelable(false);
                alertdialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                alertdialog.setContentView(R.layout.otp_alert);
                final Window window = alertdialog.getWindow();
                window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
                window.clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
                window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                Button submit = (Button) alertdialog.findViewById(R.id.submit);
                Button cancel = (Button) alertdialog.findViewById(R.id.cancel);
                TextView resendOtp = (TextView) alertdialog.findViewById(R.id.resendOTP);
                final EditText otp = (EditText) alertdialog.findViewById(R.id.otp);
                otp.setTypeface(controller.getDetailsFont());
                cancel.setTypeface(controller.getDetailsFont());
                submit.setTypeface(controller.getDetailsFont());
                submit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (otp.getText().length() == 6) {
                            if (Util.isNetworkAvailable(Login.this)) {
                                apiCall=verifyOtp;
                                dialog=Util.showPogress(Login.this);
                                controller.getApiCall().getData(Common.validateOtpUrl(controller.getTempId(),otp.getText().toString()),"", Login.this);
                            }
                        } else {
                            Toast.makeText(Login.this, "Please enter otp.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                resendOtp.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        resendOtp();
                    }
                });
                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                       alertdialog.cancel();
                        alertdialog=null;
                    }
                });
                alertdialog.show();

            }
        });

    }
}
