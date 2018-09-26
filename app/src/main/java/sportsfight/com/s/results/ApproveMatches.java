package sportsfight.com.s.results;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import sportsfight.com.s.R;
import sportsfight.com.s.adapter.ApproveMatchAdapter;
import sportsfight.com.s.adapter.ApproveResultAdapter;
import sportsfight.com.s.adapter.Pending_Result_List_Adapter;
import sportsfight.com.s.common.AppController;
import sportsfight.com.s.common.Common;
import sportsfight.com.s.interfaces.ApproveResultCallBack;
import sportsfight.com.s.interfaces.DeclareResultCallBack;
import sportsfight.com.s.interfaces.WebApiResponseCallback;
import sportsfight.com.s.mainmodule.Profile;
import sportsfight.com.s.model.ApproveResultModel;
import sportsfight.com.s.model.DeclareResultModel;
import sportsfight.com.s.util.Util;

/**
 * Created by Ashish.Kumar on 12-03-2018.
 */

public class ApproveMatches extends Activity implements View.OnClickListener,WebApiResponseCallback,DeclareResultCallBack {
    @BindView(R.id.back)
    Button back;
    @BindView(R.id.approvallist)
    ListView approvalList;
    @BindView(R.id.approveMatches)
    Button approveMatchesBtn;
    @BindView(R.id.heading)
    TextView heading;

    AppController controller;
    int apicall = -1;
    final int getListForApproval = 1, approveMatches = 2;
    Dialog dialog;
    ArrayList<DeclareResultModel> list = new ArrayList<>();
    ApproveMatchAdapter adapter;
    DeclareResultModel model;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.approve_result);
        ButterKnife.bind(this);
        controller = (AppController) getApplicationContext();
        back.setOnClickListener(this);
        approveMatchesBtn.setVisibility(View.GONE);
        heading.setText("Approve Matches");

        getList();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back:
                onBackPressed();
                break;
        }
    }

    @Override
    public void onSucess(String value) {

        if (dialog != null) {
            dialog.cancel();
        }
        if(Util.getStatus(value)==true) {
            switch (apicall) {
                case getListForApproval:

                    try {
                        JSONObject jsonObject = new JSONObject(value);
                        JSONArray jsonArray = jsonObject.getJSONArray("Result");
                        for (int i = 0; i < jsonArray.length(); i++) {
                            DeclareResultModel model = new DeclareResultModel(jsonArray.getJSONObject(i));
                            list.add(model);
                        }
                    } catch (Exception ex) {
                        ex.fillInStackTrace();
                    }

                    if (list.size() > 0) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                adapter = new ApproveMatchAdapter(list, ApproveMatches.this,true);
                                approvalList.setAdapter(adapter);
                            }
                        });
                    }

                    break;
                case approveMatches:
                    Util.showToast(ApproveMatches.this,Util.getMessage(value));
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                    list.remove(model);
                    adapter.notifyDataSetChanged();
                        }
                    });
                    break;
            }
            }else{
            Util.showToast(ApproveMatches.this,Util.getMessage(value));
        }

    }


    @Override
    public void onError(String value) {
        if (dialog != null) {
            dialog.cancel();
        }
        if (Util.isSessionExpired(value)) {
            controller.logout();
            Util.Logout(ApproveMatches.this);
        }
        Util.showToast(ApproveMatches.this,Util.getMessage(value));
    }


    public void getList() {
        if (Util.isNetworkAvailable(ApproveMatches.this)) {
            apicall = getListForApproval;
            dialog = Util.showPogress(ApproveMatches.this);
            controller.getApiCall().getData(Common.getChallengesListForAdminApprovalUrl,controller.getPrefManager().getUserToken(), this);
        }
    }

    public void callApproveRejectMatch() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (Util.isNetworkAvailable(ApproveMatches.this)) {
                    apicall = approveMatches;
                    dialog = Util.showPogress(ApproveMatches.this);
                    controller.getApiCall().postData(Common.getApprove_Reject_ChallengesUrl, getApproveJSON().toString(),controller.getPrefManager().getUserToken(), ApproveMatches.this);
                }
            }
        });


    }

    public JSONObject getApproveJSON() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("ChallengeId", model.getId());
            jsonObject.put("RefereeId", "8");
            jsonObject.put("AdminId", controller.getProfile().getUserId());
            if (model.getApproved() == true) {
                jsonObject.put("IsApproved", 1);
            } else {
                jsonObject.put("IsApproved", 0);
            }
        } catch (Exception ex) {
            ex.fillInStackTrace();
        }
        return jsonObject;
    }
    @Override
    public void onDeclareResultClick(DeclareResultModel modell) {
     model=modell;
        callApproveRejectMatch();
    }
}