package sportsfight.com.s.mainmodule;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.UTFDataFormatException;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import sportsfight.com.s.R;
import sportsfight.com.s.adapter.AlertsPagerAdapter;
import sportsfight.com.s.common.AppController;
import sportsfight.com.s.common.Common;
import sportsfight.com.s.interfaces.Accept_reject_callback;
import sportsfight.com.s.interfaces.WebApiResponseCallback;
import sportsfight.com.s.model.ChallengeModel;
import sportsfight.com.s.model.ReminderModel;
import sportsfight.com.s.util.Util;
import sportsfight.com.s.wallet.Wallet;

/**
 * Created by Ashish.Kumar on 30-01-2018.
 */

public class Alerts extends Activity implements View.OnClickListener,WebApiResponseCallback,Accept_reject_callback {
    AppController controller;
    @BindView(R.id.back)
    Button back;
    @BindView(R.id.challenges)
    TextView challenges;
    @BindView(R.id.notifications)
    TextView notifications;
    @BindView(R.id.view_pager)
    ViewPager pager;
    ArrayList<ReminderModel> notificationsList=new ArrayList<>();
    ArrayList<ReminderModel> challengesList=new ArrayList<>();
    Dialog dialog;
    int apiCall=0;
    int getChallengeAPiCall=1,accept_rejectChallenge=2;
    ReminderModel model;
    AlertsPagerAdapter adapter;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.alerts);
        controller=(AppController)getApplicationContext();
        ButterKnife.bind(this);
        back.setOnClickListener(this);
        challenges.setOnClickListener(this);
        notifications.setOnClickListener(this);

        pager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                handleButton(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
        getData();
    }

    public void getData()
    {
        if (Util.isNetworkAvailable(Alerts.this)) {
            apiCall = getChallengeAPiCall;
            dialog = Util.showPogress(Alerts.this);
            controller.getApiCall().getData(Common.getAlertsUrl(controller.getProfile().getUserId()),controller.getPrefManager().getUserToken(), this);
        }
    }
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back:
                onBackPressed();
                break;
            case R.id.notifications:
                pager.setCurrentItem(1);
                break;
            case R.id.challenges:
                pager.setCurrentItem(0);
                break;
        }
    }
    public void handleButton(int position)
    {
        switch (position)
        {
            case 0:
                challenges.setBackgroundColor(getResources().getColor(R.color.black_font));
                challenges.setTextColor(getResources().getColor(R.color.white));
                notifications.setBackgroundColor(getResources().getColor(R.color.white));
                notifications.setTextColor(getResources().getColor(R.color.black_font));
                break;
            case 1:
                challenges.setBackgroundColor(getResources().getColor(R.color.white));
                challenges.setTextColor(getResources().getColor(R.color.black_font));
                notifications.setBackgroundColor(getResources().getColor(R.color.black_font));
                notifications.setTextColor(getResources().getColor(R.color.white));

                break;
        }
    }
    @Override
    public void onSucess(final String value) {
        if (dialog != null) {
            dialog.cancel();
        }
        if (Util.getStatus(value) == true) {

            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (apiCall == getChallengeAPiCall) {
                        jsonParsing(value);
                    } else {
                        getData();
                    }
                }

            });

        } else {
            Util.showToast(Alerts.this, Util.getMessage(value));
        }

    }

    public void jsonParsing(String value) {
        challengesList.clear();
        notificationsList.clear();
        try {
            JSONObject jsonObject = new JSONObject(value);
            JSONObject result = jsonObject.getJSONObject("Result");
            JSONArray challengesArray = result.getJSONArray("Challenges");
            JSONArray notificationArray = result.getJSONArray("Notifications");
            if (challengesArray.length() > 0) {
                for (int i = 0; i < challengesArray.length(); i++) {
                    challengesList.add(new ReminderModel(challengesArray.getJSONObject(i)));
                }
            }
            if (notificationArray.length() > 0) {
                for (int j = 0; j < notificationArray.length(); j++) {
                    notificationsList.add(new ReminderModel(notificationArray.getJSONObject(j)));
                }
            }
            adapter = new AlertsPagerAdapter(Alerts.this, challengesList, notificationsList);
            pager.setAdapter(adapter);
        } catch (Exception ex) {
            ex.fillInStackTrace();
        }
    }

    @Override
    public void onError(String value) {
        if (dialog != null) {
            dialog.cancel();
        }
        if (Util.isSessionExpired(value)) {
            controller.logout();
            Util.Logout(Alerts.this);
        }
        Util.showToast(Alerts.this, Util.getMessage(value));
    }

    @Override
    public void onAcceptClick(ReminderModel model) {
        this.model = model;
        if (controller.getProfile().getTotalPoints() >= model.getChallengedPoints()) {
            if (Util.isNetworkAvailable(Alerts.this)) {
                apiCall = accept_rejectChallenge;
                dialog = Util.showPogress(Alerts.this);
                controller.getApiCall().postData(Common.getAccept_RejectUrl, getJSON(1, model).toString(),controller.getPrefManager().getUserToken(), this);
            }
        } else {
            Util.showToast(Alerts.this, "Do not have sufficient points in wallet.Please add " + (model.getChallengedPoints() - controller.getProfile().getTotalPoints()) + "  more points to accept this challenge");
        }
    }

    @Override
    public void onRejectClick(ReminderModel model) {
        this.model = model;
        if (Util.isNetworkAvailable(Alerts.this)) {
            apiCall = accept_rejectChallenge;
            dialog = Util.showPogress(Alerts.this);
            controller.getApiCall().postData(Common.getAccept_RejectUrl, getJSON(0, model).toString(),controller.getPrefManager().getUserToken(), this);
        }
    }

    public JSONObject getJSON(int status, ReminderModel model) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("Id", model.getChallengeId());
            jsonObject.put("GameId", model.getGameId());
            jsonObject.put("ChallengedBy", model.getOpponentId());
            jsonObject.put("ChallengedTo", controller.getProfile().getUserId());
            jsonObject.put("IsAccepted", status);
            jsonObject.put("MatchDate", model.getMatchDatetime());
            jsonObject.put("GameSlotId", model.getGameSlotId());
            jsonObject.put("ChallengedPoints", model.getChallengedPoints());
            jsonObject.put("DeviceId", Util.getDeviceID(Alerts.this));


        } catch (Exception ex) {
            ex.fillInStackTrace();
        }
        return jsonObject;
    }
}