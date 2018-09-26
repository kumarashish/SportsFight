package sportsfight.com.s.results;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.ColorSpace;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import sportsfight.com.s.R;
import sportsfight.com.s.adapter.Pending_Result_List_Adapter;
import sportsfight.com.s.common.AppController;
import sportsfight.com.s.common.Common;
import sportsfight.com.s.interfaces.DeclareResultCallBack;
import sportsfight.com.s.interfaces.WebApiResponseCallback;
import sportsfight.com.s.mainmodule.Profile;
import sportsfight.com.s.model.DeclareResultModel;
import sportsfight.com.s.util.Util;

/**
 * Created by Ashish.Kumar on 07-03-2018.
 */

public class DeclareResult extends Activity implements View.OnClickListener,WebApiResponseCallback,DeclareResultCallBack{
    @BindView(R.id.yesterday)
    TextView yesterday;
    @BindView(R.id.today)
   TextView today;
    @BindView(R.id.approveResult)
    Button approveResult;
    @BindView(R.id.back)
    Button back;
    @BindView(R.id.matcheslist)
    ListView pendingResultmatchesList;
    @BindView(R.id.noItem)
    TextView noItems;
    boolean todaySelected=true;
    AppController controller;
    String date="";
    Dialog dialog;
    ArrayList<DeclareResultModel> matchesList=new ArrayList<DeclareResultModel>();
    DeclareResultModel model=null;
    int apicall=-1;
    int getListApiCall=1,sendResult=2;
    Dialog dialogg;
    Pending_Result_List_Adapter adapter=null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.declare_result);
        ButterKnife.bind(this);
        controller=(AppController)getApplicationContext();
        back.setOnClickListener(this);
        today.setOnClickListener(this);
        yesterday.setOnClickListener(this);
        approveResult.setOnClickListener(this);
         date=Util.getCurrentDate();
        //date="05/31/2018";
        getList(date);

    }

    public void getList(String date) {
        if (Util.isNetworkAvailable(DeclareResult.this)) {
            apicall = getListApiCall;
            dialog = Util.showPogress(DeclareResult.this);
            controller.getApiCall().getData(Common.getGetMatchesForResultDeclarationUrl(date),controller.getPrefManager().getUserToken(), this);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.today:
                date=Util.getCurrentDate();
                getList(date);
                yesterday.setBackgroundColor(getResources().getColor(R.color.white));
                yesterday.setTextColor(getResources().getColor(R.color.black_font));
                today.setBackgroundColor(getResources().getColor(R.color.black_font));
                today.setTextColor(getResources().getColor(R.color.white));

                break;
            case R.id.yesterday:
                date=Util.getYesterdayDate();
                getList(date);
                yesterday.setBackgroundColor(getResources().getColor(R.color.black_font));
                yesterday.setTextColor(getResources().getColor(R.color.white));
                today.setBackgroundColor(getResources().getColor(R.color.white));
                today.setTextColor(getResources().getColor(R.color.black_font));

                break;
            case R.id.back:
                onBackPressed();
                break;
            case R.id.approveResult:
             startActivity(new Intent(DeclareResult.this,ApproveResult.class));
                break;

        }

    }

    @Override
    public void onSucess(String value) {
        matchesList.clear();
        if (dialog != null) {
            dialog.cancel();
        }
        if (Util.getStatus(value) == true) {
            if (apicall == getListApiCall) {
                try {
                    JSONObject jsonObject = new JSONObject(value);
                    JSONArray jsonArray = jsonObject.getJSONArray("Result");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        DeclareResultModel model = new DeclareResultModel(jsonArray.getJSONObject(i));
                        matchesList.add(model);
                    }
                } catch (Exception ex) {
                    ex.fillInStackTrace();
                }

                if (matchesList.size() > 0) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            adapter = new Pending_Result_List_Adapter(matchesList, DeclareResult.this,false);
                            pendingResultmatchesList.setAdapter(adapter);
                            pendingResultmatchesList.setVisibility(View.VISIBLE);
                            noItems.setVisibility(View.GONE);
                        }
                    });
                }else{
                    pendingResultmatchesList.setVisibility(View.GONE);
                    noItems.setVisibility(View.VISIBLE);

                }
            } else if (apicall == sendResult) {
                if (dialogg != null) {
                    dialogg.cancel();
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (matchesList.size() > 0) {
                            matchesList.remove(model);
                            adapter.notifyDataSetChanged();

                        } else {
                            getList(date);
                        }
                    }
                });
            }
        } else {
            Util.showToast(DeclareResult.this, Util.getMessage(value));
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    pendingResultmatchesList.setVisibility(View.GONE);
                    noItems.setVisibility(View.VISIBLE);
                }
            });
        }
    }
    @Override
    public void onError(String value) {
        if (dialog != null) {
            dialog.cancel();
        }
        if (Util.isSessionExpired(value)) {
            controller.logout();
            Util.Logout(DeclareResult.this);
        }
        Util.showToast(DeclareResult.this, Util.getMessage(value));
    }
    @Override
    protected void onDestroy() {
        model=null;
        super.onDestroy();
    }
    @Override
    public void onDeclareResultClick(final DeclareResultModel model1) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                model = model1;
                showPopUp(model1);
            }
        });
    }

    public void showPopUp(final DeclareResultModel model) {
        dialogg = new Dialog(this);
        dialogg.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogg.setContentView(R.layout.declare_result_popup);
        final Window window = dialogg.getWindow();
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
        window.clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        window.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.transaparent)));
        final EditText message = (EditText) dialogg.findViewById(R.id.message);
        Button submit = (Button) dialogg.findViewById(R.id.submit);
        submit.setTypeface(controller.getDetailsFont());
        message.setTypeface(controller.getDetailsFont());
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (message.getText().length() > 0) {
                    apicall = sendResult;
                    dialog = Util.showPogress(DeclareResult.this);
                    controller.getApiCall().postData(Common.getUrlForResultDeclarationUrl, getDeclareResultJSON(message.getText().toString(), model).toString(), controller.getPrefManager().getUserToken(), DeclareResult.this);
                } else {
                    Util.showToast(DeclareResult.this, "Please enter message");
                }
            }
        });
        dialogg.show();
    }

    public JSONObject getDeclareResultJSON(String message, DeclareResultModel Model) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("ChallengeId", model.getId());
            jsonObject.put("ResultText", message);
            jsonObject.put("WinnerId", model.getWinnerId());
            jsonObject.put("LooserId", model.getLooserId());
        } catch (Exception ex) {
            ex.fillInStackTrace();
        }
        return jsonObject;
    }
}
