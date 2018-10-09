package sportsfight.com.s.wallet;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONObject;
import org.jsoup.select.Evaluator;

import java.util.ArrayList;

import javax.microedition.khronos.egl.EGLDisplay;

import butterknife.BindView;
import butterknife.ButterKnife;
import sportsfight.com.s.R;
import sportsfight.com.s.adapter.TransferViewPager;
import sportsfight.com.s.common.AppController;
import sportsfight.com.s.common.Common;
import sportsfight.com.s.interfaces.AddBeneficieryCallBack;
import sportsfight.com.s.interfaces.WebApiResponseCallback;
import sportsfight.com.s.mainmodule.Alerts;
import sportsfight.com.s.mainmodule.Dashboard;
import sportsfight.com.s.mainmodule.Profile;
import sportsfight.com.s.model.BankDetails;
import sportsfight.com.s.model.BeneficieryModel;
import sportsfight.com.s.model.MatchesModel;
import sportsfight.com.s.model.TransferPointsCallback;
import sportsfight.com.s.util.Util;

/**
 * Created by Ashish.Kumar on 04-05-2018.
 */

public class Transfer extends Activity implements View.OnClickListener,AddBeneficieryCallBack,WebApiResponseCallback,TransferPointsCallback {
    AppController controller;
    @BindView(R.id.back)
    Button back;
    @BindView(R.id.availablePoints)
    TextView points_Tv;
    @BindView(R.id.user)
    TextView transfer_user;
    @BindView(R.id.bank)
    TextView transfer_bank;
    @BindView(R.id.view_pager)
    ViewPager viewPager;
    Dialog dialogg;
    Dialog dialog;
    boolean isIFSCVerified = false;
    public static int apiCall=-1;
    final   public static int verifyIFSC=1,addBeneficiery=2,transferPoints=3,validateOtp=4,getBeneficieryList=5,transferMoney=6;
    BankDetails bankDetailsModel=null;
    TextView bankDetails ;
    TextView bankName;
    TextView  verify;
    int transfferedPoint=0;

    ArrayList<BeneficieryModel> beneficeryList=new ArrayList<>();
    TransferViewPager adapter=null;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.transfer);
        controller = (AppController) getApplicationContext();
        ButterKnife.bind(this);
        back.setOnClickListener(this);
        points_Tv.setText(controller.getProfile().getTotalPoints() + " pts.");
        transfer_user.setOnClickListener(this);
        transfer_bank.setOnClickListener(this);
        if (Util.isNetworkAvailable(Transfer.this)) {
            getBeneficieryList();
        }
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                handlePages(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    public void getBeneficieryList() {
        apiCall = getBeneficieryList;
        dialog = Util.showPogress(Transfer.this);
        controller.getApiCall().getData(Common.getBeneficieryList(controller.getProfile().getUserId()),controller.getPrefManager().getUserToken(), this);
    }
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bank:
                viewPager.setCurrentItem(1);
                break;
            case R.id.user:
                viewPager.setCurrentItem(0);
                break;
            case R.id.back:
                finish();
                break;
        }
    }

    public void handlePages(int pos) {
        switch (pos) {
            case 0:
                transfer_user.setBackgroundColor(getResources().getColor(R.color.black_font));
                transfer_bank.setBackgroundColor(getResources().getColor(R.color.white));
                transfer_user.setTextColor(getResources().getColor(R.color.white));
                transfer_bank.setTextColor(getResources().getColor(R.color.black_font));
                break;
            case 1:
                transfer_bank.setBackgroundColor(getResources().getColor(R.color.black_font));
                transfer_user.setBackgroundColor(getResources().getColor(R.color.white));
                transfer_bank.setTextColor(getResources().getColor(R.color.white));
                transfer_user.setTextColor(getResources().getColor(R.color.black_font));
                break;
        }

    }

    @Override
    public void onAddBeneficieryClick() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                showAddBeneficiaryAlert();
            }
        });
    }

    @Override
    public void selectBeneficiay() {

    }

    public void showOtpDialog(final int type) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                dialogg = new Dialog(Transfer.this);
                dialogg.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialogg.setContentView(R.layout.otp_validation_alert);
                final EditText otp = (EditText) dialogg.findViewById(R.id.otp);
                Button submit = (Button) dialogg.findViewById(R.id.submit);
                TextView resendotp = (TextView) dialogg.findViewById(R.id.resendotp);
                otp.setTypeface(controller.getDetailsFont());
                resendotp.setVisibility(View.GONE);
                submit.setTypeface(controller.getDetailsFont());
                submit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (otp.getText().length() == 0) {
                            Util.showToast(Transfer.this, "Please enter otp");
                        } else if (otp.getText().length() != 6) {
                            Util.showToast(Transfer.this, "Please enter valid otp");
                        } else {
                            apiCall = validateOtp;
                            dialog = Util.showPogress(Transfer.this);
                            controller.getApiCall().getData(Common.getValidateOtpUUrl(controller.getProfile().getUserId(), otp.getText().toString(), type),controller.getPrefManager().getUserToken(), Transfer.this);
                        }
                    }
                });
                resendotp.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                    }
                });
                dialogg.show();
            }
        });
    }

    public void showTransferMoneyPopUp(final BeneficieryModel model) {
        dialogg = new Dialog(this);
        dialogg.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogg.setContentView(R.layout.transfer_money_popup);
        final Window window = dialogg.getWindow();
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
        window.clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        window.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.transaparent)));
        sportsfight.com.s.common.Heading beneficeryName = (sportsfight.com.s.common.Heading) dialogg.findViewById(R.id.beneficeryName);
        sportsfight.com.s.common.DetailsCustomTextView beneficeryNumber = (sportsfight.com.s.common.DetailsCustomTextView) dialogg.findViewById(R.id.beneficeryNumber);
        sportsfight.com.s.common.DetailsCustomTextView ifsccode = (sportsfight.com.s.common.DetailsCustomTextView) dialogg.findViewById(R.id.ifsccode);
        sportsfight.com.s.common.DetailsCustomTextView bankDetails = (sportsfight.com.s.common.DetailsCustomTextView) dialogg.findViewById(R.id.bankDetails);
        final EditText points = (EditText) dialogg.findViewById(R.id.points);
        final sportsfight.com.s.common.Heading feeValue = (sportsfight.com.s.common.Heading) dialogg.findViewById(R.id.feeValue);
        final sportsfight.com.s.common.Heading amountValue = (sportsfight.com.s.common.Heading) dialogg.findViewById(R.id.amountValue);
        Button submit = (Button) dialogg.findViewById(R.id.transfer);
        submit.setTypeface(controller.getDetailsFont());
        points.setTypeface(controller.getDetailsFont());

        beneficeryName.setText(model.getBeneficieryName());
        beneficeryNumber.setText(model.getBeneficieryAccountNumber());
        ifsccode.setText(model.getIFSC_Code());
        bankDetails.setText(model.getBankDetails());
        points.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (editable.length() == 0) {
                    feeValue.setText("0");
                    amountValue.setText("0");

                } else {
                    if (Integer.parseInt(editable.toString()) < 500) {

                    } else {
                        int value = Integer.parseInt(editable.toString()) / 10;
                        long fee = Math.round(value * 0.08);
                        int amount = Math.round(value - fee);
                        feeValue.setText("" + fee);
                        amountValue.setText(Integer.toString(amount));
                    }
                }

            }
        });
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Integer.parseInt(points.getText().toString()) < 500) {
                    Util.showToast(Transfer.this, "Points should be greater than 500");
                } else if (Integer.parseInt(points.getText().toString()) % 10 != 0) {
                    Util.showToast(Transfer.this, "Points should be multiple of 10 ");
                } else {
                    if(controller.getProfile().getTotalPoints()<Integer.parseInt(points.getText().toString()))
                    {int point=Integer.parseInt(points.getText().toString());
                     int diff=point-controller.getProfile().getTotalPoints();
                        Util.showToast(Transfer.this, "You do not have "+point+" pts in your wallet.Please add "+diff+" more points to continue this transaction.");
                    }else {
                        apiCall = transferMoney;
                        dialog = Util.showPogress(Transfer.this);
                        int amount = Integer.parseInt(points.getText().toString()) / 10;
                        transfferedPoint=Integer.parseInt(points.getText().toString());
                        controller.getApiCall().postData(Common.getTransferMoneyUrl, getMoneyTransferJSON(model, amount).toString(),controller.getPrefManager().getUserToken(), Transfer.this);
                    }
                }
            }
        });
        dialogg.show();
    }
    public void showAddBeneficiaryAlert() {
        dialogg = new Dialog(this);
        dialogg.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogg.setContentView(R.layout.add_beneficiery_popup);
        final Window window = dialogg.getWindow();
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
        window.clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        window.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.transaparent)));
        Button submit = (Button) dialogg.findViewById(R.id.submit);
        final EditText beneficiaryName = (EditText) dialogg.findViewById(R.id.beneficiaryName);
        final EditText beneficiaryAccountNumber = (EditText) dialogg.findViewById(R.id.beneficiaryAccountNumber);
        final EditText ifsccode = (EditText) dialogg.findViewById(R.id.ifsccode);
        bankDetails = (TextView) dialogg.findViewById(R.id.bankDetails);
        bankName = (TextView) dialogg.findViewById(R.id.bankName);
        verify = (TextView) dialogg.findViewById(R.id.verify);

        ifsccode.addTextChangedListener(new TextWatcher() {
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (editable.length() != 11) {
                    isIFSCVerified = false;
                    verify.setTextColor(getResources().getColor(R.color.red));
                    verify.setText(getResources().getString(R.string.verify));
                    bankDetailsModel = null;
                    bankDetails.setText("");
                    bankName.setText("");
                }
            }
        });
        verify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ifsccode.getText().length() == 11) {
                    getData(ifsccode.getText().toString());
                } else {
                    if (ifsccode.getText().length() == 0) {
                        Util.showToast(Transfer.this, "Please enter IFSC Code");
                    } else {
                        Util.showToast(Transfer.this, "Please enter Valid IFSC Code");
                    }
                }
            }
        });
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (beneficiaryAccountNumber.getText().length() > 10) {
                    if (beneficiaryName.getText().length() > 3) {
                        if (ifsccode.getText().length() == 11) {
                            if (isIFSCVerified == true) {
                                dialog = Util.showPogress(Transfer.this);
                                apiCall = addBeneficiery;
                                controller.getApiCall().postData(Common.addBeneficiery,getAddBeneficieryJSON(beneficiaryName.getText().toString(),beneficiaryAccountNumber.getText().toString(),ifsccode.getText().toString(),bankDetailsModel.getBANK()+","+bankDetailsModel.getBRANCH()).toString(),controller.getPrefManager().getUserToken() , Transfer.this);

                            } else {
                                Util.showToast(Transfer.this, "Please verify IFSC Code");
                            }
                        } else {
                            Util.showToast(Transfer.this, "Please enter Valid IFSC Code");
                        }
                    } else {
                        Util.showToast(Transfer.this, "Please enter benificiery Name");
                    }
                } else {
                    Util.showToast(Transfer.this, "Please enter valid account number");
                }
            }
        });
        beneficiaryName.setTypeface(controller.getDetailsFont());
        beneficiaryAccountNumber.setTypeface(controller.getDetailsFont());
        ifsccode.setTypeface(controller.getDetailsFont());
        bankDetails.setTypeface(controller.getDetailsFont());
        verify.setTypeface(controller.getDetailsFont());
        submit.setTypeface(controller.getDetailsFont());
        dialogg.show();
    }


    public void getData(String ifscCode) {
        if (Util.isNetworkAvailable(Transfer.this)) {
            apiCall=verifyIFSC;
            dialog = Util.showPogress(Transfer.this);
            controller.getApiCall().getData(Common.getGetBankDetailFromIFSC(ifscCode),controller.getPrefManager().getUserToken(), this);
        }
    }

    @Override
    public void onSucess(final String value) {
      switch (apiCall)
      {
          case verifyIFSC:
            if ((value.length() > 0) && (!value.equalsIgnoreCase("Not Found"))) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        jsonParsing(value);
                    }
                });
            } else {
                Util.showToast(Transfer.this, "IFSC Code Not Valid");
                if (dialog != null) {
                    dialog.cancel();
                }
            }
            break;
          case transferPoints:
              if (Util.getStatus(value)) {
                  showOtpDialog(Common.TransferPoint);
              }
              Util.showToast(Transfer.this, Util.getMessage(value));
              if (dialog != null) {
                  dialog.cancel();
              }
              break;
          case addBeneficiery:
              if (Util.getStatus(value)) {
                  if (dialogg != null) {
                      dialogg.cancel();
                  }
                  showOtpDialog(Common.AddBeneficiery);
              }
              Util.showToast(Transfer.this, Util.getMessage(value));
              if (dialog != null) {
                  dialog.cancel();
              }
              break;
          case validateOtp:
              if (Util.getStatus(value)) {
                  controller.setPoints(controller.getProfile().getTotalPoints() - transfferedPoint);
                  runOnUiThread(new Runnable() {
                      @Override
                      public void run() {
                          points_Tv.setText(controller.getProfile().getTotalPoints() + " pts.");
                      }
                  });
                  if (dialogg != null) {
                      dialogg.cancel();
                  }
                  JSONArray jsonArray=Util.getResultJsonArray(value);
                  if(jsonArray!=null)
                  {
                      addBeneFicieryJSonParsing(jsonArray);
                  }
              }
              Util.showToast(Transfer.this, Util.getMessage(value));
              if (dialog != null) {
                  dialog.cancel();
              }

              break;
          case getBeneficieryList:
              beneficeryList.clear();
              if (Util.getStatus(value) == true) {
                  JSONArray jsonArray = Util.getResultJsonArray(value);
                  addBeneFicieryJSonParsing(jsonArray);

              }else{
                  Util.showToast(Transfer.this,Util.getMessage(value));
              }
              if (dialog != null) {
                  dialog.cancel();
              }
              break;
          case transferMoney:
              if (Util.getStatus(value)) {
                  if (dialogg != null) {
                      dialogg.cancel();
                  }
                  showOtpDialog(Common.TransferMoney);
              }
              Util.showToast(Transfer.this, Util.getMessage(value));
              if (dialog != null) {
                  dialog.cancel();
              }
              break;
        }

    }

    public void addBeneFicieryJSonParsing(JSONArray jsonArray) {
        beneficeryList.clear();
        if(jsonArray!=null) {
            for (int i = 0; i < jsonArray.length(); i++) {
                try {
                    BeneficieryModel model = new BeneficieryModel(jsonArray.getJSONObject(i));
                    beneficeryList.add(model);
                } catch (Exception ex) {
                    ex.fillInStackTrace();
                }
            }
        }
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (adapter != null) {
                    adapter.notifyDataSetChanged();

                } else {
                    adapter = new TransferViewPager(Transfer.this, beneficeryList);
                    viewPager.setAdapter(adapter);
                }
            }
        });
    }

    public void jsonParsing(String value) {
        try {
            JSONObject jsonObject = new JSONObject(value);
            bankDetailsModel = new BankDetails(jsonObject);
            isIFSCVerified = true;
            bankName.setText(bankDetailsModel.getBANK());
            bankDetails.setText(bankDetailsModel.getADDRESS());
            verify.setTextColor(getResources().getColor(R.color.greencolor));
            verify.setText("Verified");
        } catch (Exception ex) {
            ex.fillInStackTrace();
        }
        dialog.cancel();
    }
    @Override
    public void onError(String value) {
        if(dialog!=null)
        {
            dialog.cancel();
        }
        if (Util.isSessionExpired(value)) {
            controller.logout();
            Util.Logout(Transfer.this);
        }
        Util.showToast(Transfer.this,value);
    }

    @Override
    public void onTransferPointsClick(final int senderId,final  int receiverId , final String points, final int otpType) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                transfferedPoint= Integer.parseInt(points);
                apiCall = transferPoints;
                dialog = Util.showPogress(Transfer.this);
                controller.getApiCall().postData(Common.gettransferPointsUrl, getJSON(senderId, receiverId, points, otpType).toString(),controller.getPrefManager().getUserToken(), Transfer.this);
            }
        });
    }

    @Override
    public void onTransferAmountClick(final BeneficieryModel model) {
runOnUiThread(new Runnable() {
    @Override
    public void run() {
        showTransferMoneyPopUp( model);
    }
});
    }

    public JSONObject getJSON(int senderId, int receiverId, String points, int otpType) {
                JSONObject jsonObject = new JSONObject();
                try {
                    jsonObject.put("senderId", controller.getProfile().getUserId());
                    jsonObject.put("receiverId", receiverId);
                    jsonObject.put("points", points);
                    jsonObject.put("otpTypeId", otpType);
                } catch (Exception ex) {
                    ex.fillInStackTrace();
                }
                return jsonObject;
            }

    public JSONObject getAddBeneficieryJSON(String name, String accountNumber, String IFSC_code, String bankdetails) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("BeneficieryName", name);
            jsonObject.put("BeneficieryAccountNumber", accountNumber);
            jsonObject.put("IFSC_code", IFSC_code);
            jsonObject.put("BankDetails", bankdetails);
            jsonObject.put("UserId", controller.getProfile().getUserId());
        } catch (Exception ex) {
            ex.fillInStackTrace();
        }
        return jsonObject;
    }

    public JSONObject getMoneyTransferJSON(BeneficieryModel model, int Amount) {

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("BeneficieryName", model.getBeneficieryName());
            jsonObject.put("BeneficieryAccountNumber", model.getBeneficieryAccountNumber());
            jsonObject.put("Amount", Amount);
            jsonObject.put("UserId", controller.getProfile().getUserId());
        } catch (Exception ex) {
            ex.fillInStackTrace();
        }
        return jsonObject;

    }
        }