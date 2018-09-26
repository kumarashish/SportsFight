package sportsfight.com.s.ipl;

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
import sportsfight.com.s.adapter.BidListAdapter;
import sportsfight.com.s.common.AppController;
import sportsfight.com.s.common.Common;
import sportsfight.com.s.interfaces.WebApiResponseCallback;
import sportsfight.com.s.model.BidModel;
import sportsfight.com.s.util.Util;
import sportsfight.com.s.wallet.Wallet;

/**
 * Created by Ashish.Kumar on 23-04-2018.
 */

public class MyBid extends Activity implements WebApiResponseCallback,View.OnClickListener{
    AppController controller;
    @BindView(R.id.myBid)
    ListView list;
    @BindView(R.id.back)
    Button back;
    @BindView(R.id.noItem)
    TextView noBid;
    Dialog dialog;
    ArrayList<BidModel> bidList=new ArrayList<>();
    BidListAdapter adapter;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_bid);
        ButterKnife.bind(this);
        back.setOnClickListener(this);
        controller = (AppController) getApplicationContext();
        if(Util.isNetworkAvailable(MyBid.this))
        {
            dialog=Util.showPogress(MyBid.this);
            controller.getApiCall().getData(Common.getGetMyBidList(controller.getProfile().getUserId()),controller.getPrefManager().getUserToken(),this);
        }
    }

    @Override
    public void onSucess(String value) {

        if (Util.getStatus(value) == true) {
            bidJSONParsing(value);
        }else {
            if (dialog != null) {
                dialog.cancel();
            }
            Util.showToast(MyBid.this, Util.getMessage(value));
        }
    }

    public void bidJSONParsing(String value) {
        bidList.clear();
        try {
            JSONObject jsonObject = new JSONObject(value);
            JSONArray jsonArray = jsonObject.getJSONArray("Result");
            for (int i = 0; i < jsonArray.length(); i++) {
                BidModel model = new BidModel(jsonArray.getJSONObject(i));
                bidList.add(model);
            }
            adapter = new BidListAdapter(MyBid.this, bidList);
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (bidList.size() > 0) {

                        list.setAdapter(adapter);
                        list.setVisibility(View.VISIBLE);
                        noBid.setVisibility(View.GONE);

                    } else {
                        list.setVisibility(View.GONE);
                        noBid.setVisibility(View.VISIBLE);

                    }
                }
            });
        } catch (Exception ex) {
            ex.fillInStackTrace();
        }
        if (dialog != null) {
            dialog.cancel();
        }
    }
    @Override
    public void onError(String value) {
        if (dialog != null) {
            dialog.cancel();
        }
        if (Util.isSessionExpired(value)) {
            controller.logout();
            Util.Logout(MyBid.this);
        }
        Util.showToast(MyBid.this, Util.getMessage(value));
    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.back:
                onBackPressed();
                break;
        }

    }
}
