package sportsfight.com.s.loginmodule;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
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
import sportsfight.com.s.mainmodule.StaticPage;
import sportsfight.com.s.model.RegistrationModel;
import sportsfight.com.s.util.Util;

/**
 * Created by Ashish.Kumar on 31-01-2018.
 */

public class Verification extends Activity implements View.OnClickListener,WebApiResponseCallback{
    @BindView(R.id.otp)
    EditText otp;
    @BindView(R.id.submit)
    Button submit;
    @BindView(R.id.resendOTP)
    TextView resendOtp;
    AppController controller;
    int apiCall=0;
    final int validateAPiCall=1,resendOtpCall=2;
    Dialog dialog;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.verification);
        controller=(AppController)getApplicationContext();
        ButterKnife.bind(this);

        submit.setOnClickListener(this);
        resendOtp.setOnClickListener(this);
        submit.setTypeface(controller.getDetailsFont());
        otp.setTypeface(controller.getDetailsFont());
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.submit:
                if (isFieldsValidated() == true) {
                    if (Util.isNetworkAvailable(Verification.this)) {
                        dialog=Util.showPogress(Verification.this);
                        JSONObject jsonObject=new JSONObject();
                        apiCall=validateAPiCall;
                      controller.getApiCall().getData(Common.validateOtpUrl(controller.getTempId(),otp.getText().toString()),"",this);
                    }
                }
                break;
            case R.id.resendOTP:
                if (Util.isNetworkAvailable(Verification.this)) {
                    dialog=Util.showPogress(Verification.this);
                    JSONObject jsonObject=new JSONObject();
                    apiCall=resendOtpCall;
                    controller.getApiCall().getData(Common.resendOtpUrl(controller.getTempId()),"",this);
                }
                break;
        }
    }

    public boolean isFieldsValidated() {
        boolean status = false;
        if (otp.getText().length() > 0) {
            if (otp.getText().length() == 6) {
                status = true;
            } else {
                Toast.makeText(Verification.this, "Please enter valid otp", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(Verification.this, "Please enter otp", Toast.LENGTH_SHORT).show();
        }
        return status;
    }

    @Override
    public void onSucess(String value) {
        if(dialog!=null) {
            Util.cancelDialog(Verification.this, dialog);
        }
        switch (apiCall) {
            case validateAPiCall:
                if (Util.isNextScreenNeedTobeCalled(value, Verification.this)) {
                    Util.cancelDialog(Verification.this, dialog);
                    Intent in = new Intent(this, Login.class);
                    in.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    Util.startActivityCommon(Verification.this, in);
                }
                Util.showToast(Verification.this, Util.getMessage(value));
                break;
            case resendOtpCall:
                Util.showToast(Verification.this, Util.getMessage(value));
                break;
        }
    }

    @Override
    public void onError(String value) {
        if(dialog!=null) {
            Util.cancelDialog(Verification.this, dialog);
        }
        Util.showToast(Verification.this,Util.getMessage(value));

    }
}
