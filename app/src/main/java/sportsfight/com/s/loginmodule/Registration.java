package sportsfight.com.s.loginmodule;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.IntentFilter;
import android.icu.text.UnicodeSetSpanner;
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
import sportsfight.com.s.model.RegistrationModel;
import sportsfight.com.s.network.WebApiCall;

import sportsfight.com.s.util.Util;
import sportsfight.com.s.util.Validation;

/**
 * Created by Ashish.Kumar on 17-01-2018.
 */

public class Registration extends Activity implements View.OnClickListener,WebApiResponseCallback {
    public static boolean isCalledFromSplash;

    AppController controller;
    @BindView(R.id.name)
    EditText name;
    @BindView(R.id.email)
    EditText email;
    @BindView(R.id.mobile)
    EditText mobile;
    @BindView(R.id.password)
    EditText password;
    @BindView(R.id.conf_password)
    EditText confirmpassword;
    @BindView(R.id.btn_next)
    Button next;
    @BindView(R.id.signIn)
    TextView signIn;
    Dialog progressDialog = null;
    public static RegistrationModel model=null;
    public int apiCall=-1;
    int getGames=2,validateUser=1;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registration);
        initializeAll();
    }

    public void initializeAll() {

        ButterKnife.bind(this);
        controller = (AppController) getApplicationContext();
        controller.getPrefManager().setFirstTimeLaunch(false);
        name.setTypeface(controller.getDetailsFont());
        email.setTypeface(controller.getDetailsFont());
        mobile.setTypeface(controller.getDetailsFont());
        password.setTypeface(controller.getDetailsFont());
        confirmpassword.setTypeface(controller.getDetailsFont());
        next.setTypeface(controller.getDetailsFont());
        next.setOnClickListener(this);
        signIn.setOnClickListener(this);

    }
public void callGetGames()
{   if (Util.isNetworkAvailable(this)) {

    apiCall=getGames;
    model = new RegistrationModel(name.getText().toString(), email.getText().toString(), mobile.getText().toString(), password.getText().toString());
    progressDialog = Util.showPogress(this);
    controller.getApiCall().getData(Common.GetListOfGames, "", this);
}
}

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_next:
                if (isFieldsValidated()) {
                    if (Util.isNetworkAvailable(this)) {
                        apiCall=validateUser;
                        progressDialog = Util.showPogress(this);
                        controller.getApiCall().getData(Common.isUserAlreadyExist( mobile.getText().toString(),email.getText().toString()), "", this);
                    }


                }
                break;
            case R.id.signIn:
                if(isCalledFromSplash)
                {
                    Intent intent=new Intent(Registration.this,Login.class);
                    startActivity(intent);
                    finish();
                }else {
                    onBackPressed();
                }
                break;
        }
    }

    @Override
    public void onSucess(final String value) {
        if (progressDialog != null) {
            Util.cancelDialog(Registration.this, progressDialog);
        }
        if (apiCall == validateUser) {
            if (Util.getStatus(value) == false) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        callGetGames();
                    }
                });
            } else {
                Util.showToast(Registration.this, Util.getMessage(value));
            }
        }else {
            if (Util.isNextScreenNeedTobeCalled(value, Registration.this)) {
                Intent in = new Intent(Registration.this, PrefsScreen.class);
                in.putExtra("Value", value);
                Util.startActivityCommon(Registration.this, in);
            }
        }
    }

    @Override
    public void onError(final String value) {
        if (progressDialog != null) {
            Util.cancelDialog(Registration.this, progressDialog);
        }
        Util.showToast(Registration.this, Util.getMessage(value));
    }



    public boolean isFieldsValidated() {
        if (controller.getValidation().isNameValidated(name)) {
            if (controller.getValidation().isEmailIdValid(email)) {
                if (controller.getValidation().isPhoneNumberValid(mobile)) {
                    if (controller.getValidation().isPasswordValid(password)) {
                        if (controller.getValidation().isPassword_ConfirmPasswordSame(password, confirmpassword)) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }
}


