package sportsfight.com.s.results;
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
import java.util.ArrayList;
import butterknife.BindView;
import butterknife.ButterKnife;
import sportsfight.com.s.R;
import sportsfight.com.s.adapter.ApproveResultAdapter;
import sportsfight.com.s.common.AppController;
import sportsfight.com.s.common.Common;
import sportsfight.com.s.interfaces.ApproveResultCallBack;
import sportsfight.com.s.interfaces.WebApiResponseCallback;
import sportsfight.com.s.mainmodule.Profile;
import sportsfight.com.s.model.ApproveResultModel;
import sportsfight.com.s.util.Util;

/**
 * Created by Ashish.Kumar on 07-03-2018.
 */

public class ApproveResult  extends Activity implements View.OnClickListener,WebApiResponseCallback,ApproveResultCallBack {
    @BindView(R.id.back)
    Button back;
    @BindView(R.id.approvallist)
    ListView approvalList;
    @BindView(R.id.approveMatches)
    Button approveMatches;
    AppController controller;
    int apicall=-1;
   final int getListForApproval=1,approveResult=2;
    Dialog dialog;
   ArrayList<ApproveResultModel> list=new ArrayList<>();
    ApproveResultAdapter adapter;
    ApproveResultModel model;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.approve_result);
        ButterKnife.bind(this);
        controller=(AppController)getApplicationContext();
        back.setOnClickListener(this);
        approveMatches.setOnClickListener(this);
        getList();
    }

    public void getList() {
        if (Util.isNetworkAvailable(ApproveResult.this)) {
            apicall = getListForApproval;
            dialog = Util.showPogress(ApproveResult.this);
            controller.getApiCall().getData(Common.getMatchesListForAdminApprovalUrl,controller.getPrefManager().getUserToken(), this);
        }
    }
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back:
                onBackPressed();
                break;
            case R.id.approveMatches:
                startActivity(new Intent(ApproveResult.this,ApproveMatches.class));
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
                        list.clear();
                        JSONObject jsonObject = new JSONObject(value);
                        JSONArray results = jsonObject.getJSONArray("Result");
                        for (int i = 0; i < results.length(); i++) {
                            JSONObject job = results.getJSONObject(i);
                            ApproveResultModel model = new ApproveResultModel(job);
                            list.add(model);

                        }
                    } catch (Exception ex) {
                        ex.fillInStackTrace();
                    }
                    if (list.size() > 0) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                adapter = new ApproveResultAdapter(ApproveResult.this, list);
                                approvalList.setAdapter(adapter);
                            }
                        });

                    }
                    break;
                case approveResult:
                    Util.showToast(ApproveResult.this, Util.getMessage(value));
                    if (list.size() > 0) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                list.remove(model);
                                adapter.notifyDataSetChanged();
                            }
                        });

                    }else{
                        model=null;
                        finish();
                    }
                    break;
            }
        }else{
                Util.showToast(ApproveResult.this, Util.getMessage(value));
            }
        }
    @Override
    public void onError(String value) {
        if (dialog != null) {
            dialog.cancel();
        }
        if (Util.isSessionExpired(value)) {
            controller.logout();
            Util.Logout(ApproveResult.this);
        }
        Util.showToast(ApproveResult.this,Util.getMessage(value));
    }
    @Override
    public void OnApproveResultClick(ApproveResultModel selectedModel) {
         model=selectedModel;
        ApproveResult();
    }
    public void ApproveResult() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (Util.isNetworkAvailable(ApproveResult.this)) {
                    apicall = approveResult;
                    dialog = Util.showPogress(ApproveResult.this);
                    controller.getApiCall().getData(Common.getApproveResultUrl(model.getId(),controller.getProfile().getUserId()),controller.getPrefManager().getUserToken(), ApproveResult.this);
                }
            }
        });

    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}