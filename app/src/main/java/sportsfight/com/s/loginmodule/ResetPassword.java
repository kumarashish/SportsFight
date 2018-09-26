package sportsfight.com.s.loginmodule;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import sportsfight.com.s.R;
import sportsfight.com.s.common.AppController;
import sportsfight.com.s.common.Common;
import sportsfight.com.s.interfaces.WebApiResponseCallback;

import sportsfight.com.s.util.Util;
import sportsfight.com.s.util.Validation;

/**
 * Created by Ashish.Kumar on 17-01-2018.
 */

public class ResetPassword extends Activity implements View.OnClickListener,WebApiResponseCallback {

    @BindView(R.id.confirmpassword)
    EditText confirmpassword;
    @BindView(R.id.password)
    EditText password;
    @BindView(R.id.otp)
    EditText otp;
    @BindView(R.id.submit)
    Button submit;
    @BindView(R.id.resendOTP)
    TextView resendOtp;
    AppController controller;
    Dialog dialog;
    int apiCall=0;
    int resendOtpCall=1;
    int submitCall=2;
    String userId="";
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.resetpassword);
        initializeAll();
    }

    public void initializeAll() {

        controller = (AppController) getApplicationContext();
        ButterKnife.bind(this);
        submit.setOnClickListener(this);
        resendOtp.setOnClickListener(this);
        submit.setTypeface(controller.getDetailsFont());
        otp.setTypeface(controller.getDetailsFont());
        confirmpassword.setTypeface(controller.getDetailsFont());
        password.setTypeface(controller.getDetailsFont());
        userId=getIntent().getStringExtra("UserId");
    }
    public void onResume() {
        super.onResume();
       // registerReceiver(myBroadcastReceiver, new IntentFilter("android.net.conn.CONNECTIVITY_CHANGE"));
    }

    public void onPause() {
        super.onPause();
       // unregisterReceiver(myBroadcastReceiver);
    }

    @Override
    public void onClick(View view) {
        if(view.getId()==submit.getId())
        {
            if(isFieldsValidated()==true) {
                if (Util.isNetworkAvailable(ResetPassword.this)) {
                    apiCall=submitCall;
                    dialog=Util.showPogress(ResetPassword.this);
                    controller.getApiCall().getData(Common.resetPassword(otp.getText().toString(), password.getText().toString(), controller.getTempId()),"", this);
                }
            }
        }else if(view.getId()==resendOtp.getId())
        {
            if (Util.isNetworkAvailable(ResetPassword.this)) {
                apiCall=resendOtpCall;
                dialog=Util.showPogress(ResetPassword.this);
                controller.getApiCall().getData(Common.getForgetPasswordUrl( userId),"", this);
            }
        }

    }

    public boolean isFieldsValidated() {
        boolean status = false;
        if (controller.getValidation().isNotNull(otp)) {
            if (controller.getValidation().isPasswordValid(password)) {
                if (controller.getValidation().isPassword_ConfirmPasswordSame(password, confirmpassword)) {
                    status = true;
                } else {
                    Toast.makeText(ResetPassword.this, "Password and confirm password need to be same.", Toast.LENGTH_SHORT).show();
                }
            }

        } else {
            Toast.makeText(ResetPassword.this, "Please enter otp", Toast.LENGTH_SHORT).show();
        }
        return status;
    }
    @Override
    public void onSucess(String value) {
        if(dialog!=null) {
            Util.cancelDialog(ResetPassword.this, dialog);
        }
        if (value.length() > 0) {
            if(apiCall==submitCall)
            {
                if (Util.isNextScreenNeedTobeCalled(value, ResetPassword.this)) {
                    Intent in = new Intent(ResetPassword.this, Login.class);
                    in.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    Util.startActivityCommon(ResetPassword.this, in);

                }
            }
            Util.showToast(ResetPassword.this, Util.getMessage(value));
        }
    }

    @Override
    public void onError(String value) {
        if(dialog!=null) {
            Util.cancelDialog(ResetPassword.this, dialog);
        }
        Util.showToast(ResetPassword.this,Util.getMessage(value));

    }
}
