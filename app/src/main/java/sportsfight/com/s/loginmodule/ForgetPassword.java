package sportsfight.com.s.loginmodule;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;
import org.w3c.dom.Text;

import butterknife.BindView;
import butterknife.ButterKnife;
import sportsfight.com.s.R;
import sportsfight.com.s.common.AppController;
import sportsfight.com.s.common.Common;
import sportsfight.com.s.interfaces.WebApiResponseCallback;

import sportsfight.com.s.util.Util;

/**
 * Created by Ashish.Kumar on 17-01-2018.
 */

public class ForgetPassword extends Activity implements View.OnClickListener,WebApiResponseCallback {

    @BindView(R.id.submit)
    Button submit;
    @BindView(R.id.email_mobile_editText)
    EditText email_mobile;
    @BindView(R.id.icon)
    ImageView icon;
    @BindView(R.id.cancel)
    TextView cancel;
    AppController controller;
    Dialog dialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.forgetpassword);
        initializeAll();
    }

    public void initializeAll() {

        ButterKnife.bind(this);
        controller=(AppController)getApplicationContext();
        submit.setTypeface(controller.getDetailsFont());
        email_mobile.setTypeface(controller.getDetailsFont());
        submit.setOnClickListener(this);
        cancel.setOnClickListener(this);
        email_mobile.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                  if(( email_mobile.getText().length()>0)&&(Character.isDigit(charSequence.charAt(0)))&&(!email_mobile.getText().toString().contains("@")))
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


    public boolean isFieldsValidated() {
        boolean status = false;
        if (email_mobile.getText().length() > 0) {
            if (email_mobile.getText().toString().contains("@")) {
                status = true;
            } else {
                if (email_mobile.getText().length() == 10) {
                    status = true;
                } else {
                    Toast.makeText(ForgetPassword.this, "Please enter valid mobile number", Toast.LENGTH_SHORT).show();
                }
            }
        } else {
            Toast.makeText(ForgetPassword.this, "Please enter email id or mobile", Toast.LENGTH_SHORT).show();
        }
        return status;
    }
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.submit:
                if(isFieldsValidated()==true)
                {
                    if(Util.isNetworkAvailable(ForgetPassword.this))
                    {
                        dialog=Util.showPogress(ForgetPassword.this);
                         controller.getApiCall().getData(Common.getForgetPasswordUrl(email_mobile.getText().toString().trim()),"",this);
                    }
                }
                break;
            case R.id.cancel:
                finish();
                break;
        }
    }

    @Override
    public void onSucess(String value) {
        if (dialog != null) {
            Util.cancelDialog(ForgetPassword.this, dialog);
        }
        if (value.length() > 0) {
            if (Util.isNextScreenNeedTobeCalled(value, ForgetPassword.this)) {
                Intent in = new Intent(ForgetPassword.this, ResetPassword.class);
                Util.showToast(ForgetPassword.this,Util.getMessage( value));
                in.putExtra("UserId",email_mobile.getText().toString());
                Util.startActivityCommon(ForgetPassword.this,in);
            }
        }
    }

    @Override
    public void onError(String value) {
        if (dialog != null) {
            Util.cancelDialog(ForgetPassword.this, dialog);
        }
        Util.showToast(ForgetPassword.this, Util.getMessage(value));
    }
}
