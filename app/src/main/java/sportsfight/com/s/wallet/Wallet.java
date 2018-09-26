package sportsfight.com.s.wallet;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewParent;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;
import sportsfight.com.s.R;
import sportsfight.com.s.adapter.CustomPagerAdapter;
import sportsfight.com.s.common.AppController;
import sportsfight.com.s.common.Common;
import sportsfight.com.s.interfaces.WebApiResponseCallback;
import sportsfight.com.s.model.TransactionHistoryModel;
import sportsfight.com.s.util.Util;

/**
 * Created by Ashish.Kumar on 30-01-2018.
 */

public class Wallet extends Activity implements View.OnClickListener,WebApiResponseCallback {
    AppController controller;
    @BindView(R.id.addPoints)
    TextView addPoints;
    @BindView(R.id.back)
    Button back;
    @BindView(R.id.view_pager)
    ViewPager viewPager;
    @BindView(R.id.all)
    TextView all;
    @BindView(R.id.spend)
    TextView spend;
    @BindView(R.id.won)
    TextView won;
    @BindView(R.id.added)
    TextView added;
    @BindView(R.id.availablePoints)
    TextView availablePoints;
    @BindView(R.id.startDate)
    TextView startDate;
    @BindView(R.id.endDate)
    TextView endDate;
    @BindView(R.id.show)
    TextView show;
    @BindView(R.id.transfer)
    Button transfer;
    Dialog dialog;
    int apiCall=0;
    int getWalletPoints=1,getTransactionHistory=2;
    ArrayList<TransactionHistoryModel> allItemsList=new ArrayList<>();
    ArrayList<TransactionHistoryModel> spendItemsList=new ArrayList<>();
    ArrayList<TransactionHistoryModel> wonItemsList=new ArrayList<>();
    ArrayList<TransactionHistoryModel> addedItemsList=new ArrayList<>();
    Calendar calendar;
    int year;
    int month;
    int day;
boolean isStartDateClicked;
boolean isEndDateClicked;
String startDateValue="";
String endDateValue="";
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.wallet);
        controller=(AppController)getApplicationContext();
        ButterKnife.bind(this);
        addPoints.setOnClickListener(this);
        back.setOnClickListener(this);
        won.setOnClickListener(this);
        spend.setOnClickListener(this);
        added.setOnClickListener(this);
        all.setOnClickListener(this);
        startDate.setOnClickListener(this);
        endDate.setOnClickListener(this);
        show.setOnClickListener(this);
        transfer.setOnClickListener(this);
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                handleTabs(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        if(Util.isNetworkAvailable(Wallet.this))
        {
            dialog=Util.showPogress(Wallet.this);
            apiCall=getWalletPoints;
            controller.getApiCall().getData(Common.getWalletPointsUrl(controller.getProfile().getUserId()),controller.getPrefManager().getUserToken(),this);
        }
        calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);

        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);
    }

    @Override
    public void onClick(View view) {
        Intent in = null;
        switch (view.getId()) {
            case R.id.addPoints:
                in = new Intent(Wallet.this, AddPoints.class);
                startActivityForResult(in,1);
                break;
            case R.id.back:
                onBackPressed();
                break;
            case R.id.won:
                viewPager.setCurrentItem(1);
                break;
            case R.id.spend:
                viewPager.setCurrentItem(2);
                break;
            case R.id.added:
                viewPager.setCurrentItem(3);
                break;
            case R.id.all:
                viewPager.setCurrentItem(0);
                break;
            case R.id.transfer:
                startActivityForResult(new Intent(this,Transfer.class),1);
                break;
            case R.id.startDate:
                isStartDateClicked=true;
                isEndDateClicked=false;

                showDialog(999);
                break;
            case R.id.endDate:
                isStartDateClicked=false;
                isEndDateClicked=true;
                showDialog(999);
                break;
            case R.id.show:
              if((startDateValue.length()>1)&&(endDateValue.length()>1))
              { getTransactions(startDateValue,endDateValue);
              }else{
                  if(startDateValue.length()==0)
                  {
                      Toast.makeText(Wallet.this,"Please select start date.",Toast.LENGTH_SHORT).show();
                  }else{
                      Toast.makeText(Wallet.this,"Please select end date.",Toast.LENGTH_SHORT).show();
                  }
              }
                break;
        }
    }
    @Override
    protected Dialog onCreateDialog(int id) {
        // TODO Auto-generated method stub
        if (id == 999) {
            return new DatePickerDialog(this,
                    myDateListener, year, month, day);
        }
        return null;
    }

    private DatePickerDialog.OnDateSetListener myDateListener = new
            DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker arg0,
                                      int arg1, int arg2, int arg3) {
                    // TODO Auto-generated method stub
                    // arg1 = year
                    // arg2 = month
                    // arg3 = day
                    showDate(arg1, arg2+1, arg3);
                }
            };
    private void showDate(int year, int month, int day) {
        if(isStartDateClicked) {
            startDateValue=(new StringBuilder().append(month).append("/").append(day).append("/").append(year)).toString();
            startDate.setText(startDateValue);
        }else{
            endDateValue=(new StringBuilder().append(month).append("/").append(day).append("/").append(year)).toString();
            endDate.setText(endDateValue);
        }
    }
public void handleTabs(int position)
{
    switch (position)
    {
        case 0:
            all.setBackgroundColor(getResources().getColor(R.color.black_font));
            all.setTextColor(getResources().getColor(R.color.white));
            spend.setBackgroundColor(getResources().getColor(R.color.silver));
            won.setBackgroundColor(getResources().getColor(R.color.silver));
            added.setBackgroundColor(getResources().getColor(R.color.silver));
            spend.setTextColor(getResources().getColor(R.color.black_font));
            won.setTextColor(getResources().getColor(R.color.black_font));
            added.setTextColor(getResources().getColor(R.color.black_font));
            break;
        case 1:
            all.setBackgroundColor(getResources().getColor(R.color.silver));
            all.setTextColor(getResources().getColor(R.color.black_font));
            spend.setBackgroundColor(getResources().getColor(R.color.silver));
            won.setBackgroundColor(getResources().getColor(R.color.black_font));
            won.setTextColor(getResources().getColor(R.color.white));
            added.setBackgroundColor(getResources().getColor(R.color.silver));
            spend.setTextColor(getResources().getColor(R.color.black_font));

            added.setTextColor(getResources().getColor(R.color.black_font));
            break;
        case 2:
            all.setBackgroundColor(getResources().getColor(R.color.silver));
            all.setTextColor(getResources().getColor(R.color.black_font));
            spend.setBackgroundColor(getResources().getColor(R.color.black_font));
            won.setBackgroundColor(getResources().getColor(R.color.silver));
            added.setBackgroundColor(getResources().getColor(R.color.silver));
            spend.setTextColor(getResources().getColor(R.color.white));
            won.setTextColor(getResources().getColor(R.color.black_font));
            added.setTextColor(getResources().getColor(R.color.black_font));
            break;
        case 3:
            all.setBackgroundColor(getResources().getColor(R.color.silver));
            all.setTextColor(getResources().getColor(R.color.black_font));
            spend.setBackgroundColor(getResources().getColor(R.color.silver));
            won.setBackgroundColor(getResources().getColor(R.color.silver));
            added.setBackgroundColor(getResources().getColor(R.color.black_font));
            spend.setTextColor(getResources().getColor(R.color.black_font));
            won.setTextColor(getResources().getColor(R.color.black_font));
            added.setTextColor(getResources().getColor(R.color.white));
            break;
    }
}
public void getTransactions(String startDate,String endDate)
{
    if(Util.isNetworkAvailable(Wallet.this))
    {
        dialog=Util.showPogress(Wallet.this);
        apiCall=getTransactionHistory;
        controller.getApiCall().getData(Common.getTransactionHistoryUrl(controller.getProfile().getUserId(),startDate,endDate),controller.getPrefManager().getUserToken(),this);
    }
}
    @Override
    public void onSucess(String value) {
        if (dialog != null) {
            Util.cancelDialog(Wallet.this, dialog);
        }
        Util.showToast(Wallet.this, Util.getMessage(value));
        if (Util.getStatus(value) == true) {
            if (apiCall == getWalletPoints) {
                controller.setPoints(Util.getPoints(value));
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        availablePoints.setText(Integer.toString(controller.getProfile().getTotalPoints()));
                        getTransactions(Util.getDefaultStartDate(), Util.getCurrentDateWithoutTime());

                    }
                });
            } else {
                transactionHistoryParsing(value);

            }
        }

    }

    public void transactionHistoryParsing(String value) {
        allItemsList.clear();
        addedItemsList.clear();
        wonItemsList.clear();
        spendItemsList.clear();
        try {
            JSONObject jsonObject = new JSONObject(value);
            JSONObject result = jsonObject.getJSONObject("Result");
            JSONArray allItems = result.getJSONArray("All");
            JSONArray wonItems = result.getJSONArray("Won");
            JSONArray spendItems = result.getJSONArray("Spent");
            JSONArray addedItems = result.getJSONArray("Added");
            if (allItems.length() > 0) {
                for (int i = 0; i < allItems.length(); i++) {
                    allItemsList.add(new TransactionHistoryModel(allItems.getJSONObject(i),Common.all));
                }
            }
            if (wonItems.length() > 0) {
                for (int i = 0; i < wonItems.length(); i++) {
                    wonItemsList.add(new TransactionHistoryModel(wonItems.getJSONObject(i),Common.won));
                }
            }
            if (spendItems.length() > 0) {
                for (int i = 0; i < spendItems.length(); i++) {
                    spendItemsList.add(new TransactionHistoryModel(spendItems.getJSONObject(i),Common.spend));
                }
            }
            if (addedItems.length() > 0) {
                for (int i = 0; i < addedItems.length(); i++) {
                    addedItemsList.add(new TransactionHistoryModel(addedItems.getJSONObject(i),Common.added));
                }
            }
        } catch (Exception ex) {
            ex.fillInStackTrace();
        }
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                //viewPager.setAdapter(new CustomPagerAdapter(Wallet.this, allItemsList, wonItemsList,spendItemsList,addedItemsList));

            }
        });

    }
    @Override
    public void onError(String value) {
        if(dialog!=null)
        {
            Util.cancelDialog(Wallet.this,dialog);
        }
        if (Util.isSessionExpired(value)) {
            controller.logout();
            Util.Logout(Wallet.this);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==1)
        {
            availablePoints.setText(Integer.toString(controller.getProfile().getTotalPoints()));
        }
    }
}
