package sportsfight.com.s.staticpages;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import sportsfight.com.s.R;
import sportsfight.com.s.common.AppController;
import sportsfight.com.s.common.Common;
import sportsfight.com.s.interfaces.WebApiResponseCallback;
import sportsfight.com.s.util.Util;

/**
 * Created by ashish.kumar on 24-08-2018.
 */

public class ContactUs extends Activity implements View.OnClickListener,WebApiResponseCallback{
    @BindView(R.id.back)
    Button back;
    AppController controller;
    Dialog dialog;
    @BindView(R.id.emailId)
    TextView emailId;
    @BindView(R.id.address)
    TextView address;
    @BindView(R.id.contact_number)
    TextView contactNumber;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.contact_us);
        controller=(AppController) getApplicationContext();
        ButterKnife.bind(this);
        back.setOnClickListener(this);
        if(Util.isNetworkAvailable(ContactUs.this))
        {dialog=Util.showPogress(ContactUs.this);
            controller.getApiCall().getData(Common.contactUs,controller.getPrefManager().getUserToken(),this);
        }
    }

    @Override
    public void onClick(View view) {
        onBackPressed();
    }

    @Override
    public void onSucess(final String value) {
        if(dialog!=null)
        {
            dialog.cancel();
        }

         runOnUiThread(new Runnable() {
             @Override
             public void run() {
                 setValue(value);
             }
         });
    }
public void setValue(String json)
{
    try{
        JSONObject jsonObject=Util.getResultJson(json);
        address.setText(jsonObject.getString("Address"));
        contactNumber.setText(jsonObject.getString("Mobile"));
        emailId.setText(jsonObject.getString("EmailId"));
    }catch (Exception ex)
    {
        ex.fillInStackTrace();
    }
}
    @Override
    public void onError(String value) {
        if(dialog!=null)
        {
            dialog.cancel();
        }
        Util.showToast(ContactUs.this,Util.getMessage(value));
    }
}