package sportsfight.com.s.mainmodule;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import sportsfight.com.s.R;
import sportsfight.com.s.adapter.ReminderAdapter;
import sportsfight.com.s.common.AppController;
import sportsfight.com.s.common.Common;
import sportsfight.com.s.interfaces.WebApiResponseCallback;
import sportsfight.com.s.model.ReminderModel;
import sportsfight.com.s.util.Util;
import sportsfight.com.s.wallet.AddPoints;
import sportsfight.com.s.wallet.Wallet;

/**
 * Created by Ashish.Kumar on 30-01-2018.
 */

public class Reminders extends Activity implements View.OnClickListener,WebApiResponseCallback {
    AppController controller;
    @BindView(R.id.back)
    Button back;
    @BindView(R.id.reminder)
    ListView reminder;
    @BindView(R.id.noItem)
    TextView noItems;
    Dialog dialog;
    ArrayList<ReminderModel> list=new ArrayList<>();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reminder);
        controller=(AppController)getApplicationContext();
        ButterKnife.bind(this);
        back.setOnClickListener(this);
        if(Util.isNetworkAvailable(Reminders.this))
        {
            dialog=Util.showPogress(Reminders.this);
            controller.getApiCall().getData(Common.getReminderUrl(controller.getProfile().getUserId()),controller.getPrefManager().getUserToken(),this);
        }
    }

    @Override
    public void onClick(View view) {
        Intent in = null;
        switch (view.getId()) {

            case R.id.back:
                onBackPressed();
                break;
        }
    }

    @Override
    public void onSucess(String value) {
        if(dialog!=null)
        {
            dialog.cancel();
        }
        if(Util.getStatus(value)==true)
        {
            jsonParsing(value);
        }

    }

    public void jsonParsing(String value) {
        list.clear();
        try {
            JSONObject jsonObject = new JSONObject(value);
            JSONArray jsonArray = jsonObject.getJSONArray("Result");
            for (int i = 0; i < jsonArray.length(); i++) {
                list.add(new ReminderModel(jsonArray.getJSONObject(i)));
            }
        } catch (Exception ex) {
            ex.fillInStackTrace();
        }
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (list.size() > 0) {
                    reminder.setVisibility(View.VISIBLE);
                    noItems.setVisibility(View.GONE);
                    reminder.setAdapter(new ReminderAdapter(Reminders.this, list));

                } else {
                    reminder.setVisibility(View.GONE);
                    noItems.setVisibility(View.VISIBLE);
                }
            }
        });

    }
    @Override
    public void onError(String value) {
        if(dialog!=null)
        {
            dialog.cancel();
        }
        if (Util.isSessionExpired(value)) {
            controller.logout();
            Util.Logout(Reminders.this);
        }
        Util.showToast(Reminders.this,Util.getMessage(value));
    }
}
