package sportsfight.com.s.launchingmodule;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.paytm.pgsdk.PaytmMerchant;
import com.paytm.pgsdk.PaytmOrder;
import com.paytm.pgsdk.PaytmPGService;
import com.paytm.pgsdk.PaytmPaymentTransactionCallback;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import sportsfight.com.s.R;
import sportsfight.com.s.common.AppController;
import sportsfight.com.s.common.Common;
import sportsfight.com.s.interfaces.WebApiResponseCallback;
import sportsfight.com.s.model.TransactionModel;
import sportsfight.com.s.util.Contants;
import sportsfight.com.s.util.Util;

/**
 * Created by ashish.kumar on 04-09-2018.
 */

public class PaymentGateway extends Activity  implements WebApiResponseCallback{
    String orderId="";
    AppController controller;
    Dialog progressDialog;
    int apiCall=0;
    int getCheckSum=1,getTranactionCheckSum=2,validateTransaction=3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.merchantapp);
        // initOrderId();
        controller=(AppController)getApplicationContext();
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
    }

    //This is to refresh the order id: Only for the Sample App's purpose.
    @Override
    protected void onStart() {
        super.onStart();
        //initOrderId();
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
    }


    private void initOrderId() {
        long r = System.currentTimeMillis();
        orderId = "SportsFight" + r;
    }

 public void generateChecksum(View view)
 {   initOrderId();
     apiCall=getCheckSum;
     progressDialog = Util.showPogress(this);
     controller.getApiCall().getCheckSum("http://192.168.100.92:9019/GenerateChecksum.aspx",orderId,"2","1",this);
 }

    public void getTransactionCheckSum() {
        apiCall = getTranactionCheckSum;
        progressDialog = Util.showPogress(this);
        controller.getApiCall().getCheckSum("http://192.168.100.92:9019/GenerateChecksumforTransactions.aspx", orderId, "2", "1", this);
    }

    public void validateTransaction(String checksum) {
        apiCall = validateTransaction;
        progressDialog = Util.showPogress(this);
        controller.getApiCall().postData(Common.validateTransactionUrl, validateTransactionJson(checksum).toString(), "", this);
    }


    public JSONObject validateTransactionJson(String checkSum) {
        JSONObject jsonObject = new JSONObject();
        try {

            jsonObject.put("ORDERID", orderId);
            jsonObject.put("MID", Contants.MID);
            jsonObject.put("CHECKSUMHASH", checkSum);

        } catch (Exception ex) {
            ex.fillInStackTrace();
        }
        return jsonObject;
    }

    public  void getTransactionStatus(String orderId,String checksum)
{
    PaytmPGService Service = PaytmPGService.getStagingService();
    Map<String, String> paramMap = new HashMap<String, String>();
    paramMap.put("MID",  Contants.MID);
    paramMap.put("ORDER_ID",  orderId);


}
    public void StartTransaction(String checksum) {
        PaytmPGService Service = PaytmPGService.getProductionService();
        Map<String, String> paramMap = new HashMap<String, String>();
        // these are mandatory parameters
        paramMap.put("CALLBACK_URL", Contants.CALLBACKURL+orderId);
        paramMap.put("CHANNEL_ID", Contants.CHANNEL_ID);
        paramMap.put("CHECKSUMHASH", checksum);
        paramMap.put("CUST_ID", "2");
        paramMap.put("INDUSTRY_TYPE_ID", Contants.INDUSTRY_TYPE_ID);
        paramMap.put("MID",  Contants.MID);
        paramMap.put("ORDER_ID", orderId);
        paramMap.put("TXN_AMOUNT", "1");
        paramMap.put("WEBSITE",  Contants.WEBSITE);

/*
        paramMap.put("MID" , "WorldP64425807474247");
        paramMap.put("ORDER_ID" , "210lkldfka2a27");
        paramMap.put("CUST_ID" , "mkjNYC1227");
        paramMap.put("INDUSTRY_TYPE_ID" , "Retail");
        paramMap.put("CHANNEL_ID" , "WAP");
        paramMap.put("TXN_AMOUNT" , "1");
        paramMap.put("WEBSITE" , "worldpressplg");
        paramMap.put("CALLBACK_URL" , "https://pguat.paytm.com/paytmchecksum/paytmCheckSumVerify.jsp");*/
        PaytmOrder Order = new PaytmOrder(paramMap);
		/*PaytmMerchant Merchant = new PaytmMerchant(
				"https://pguat.paytm.com/paytmchecksum/paytmCheckSumGenerator.jsp",
				"https://pguat.paytm.com/paytmchecksum/paytmCheckSumVerify.jsp");*/
        Service.initialize(Order, null);
        Service.startPaymentTransaction(this, true, true,
                new PaytmPaymentTransactionCallback() {
                    @Override
                    public void someUIErrorOccurred(String inErrorMessage) {

                        // Some UI Error Occurred in Payment Gateway Activity.
                        // // This may be due to initialization of views in
                        // Payment Gateway Activity or may be due to //
                        // initialization of webview. // Error Message details
                        // the error occurred.
                    }

					/*@Override
					public void onTransactionSuccess(Bundle inResponse) {
						// After successful transaction this method gets called.
						// // Response bundle contains the merchant response
						// parameters.
						Log.d("LOG", "Payment Transaction is successful " + inResponse);
						Toast.makeText(getApplicationContext(), "Payment Transaction is successful ", Toast.LENGTH_LONG).show();
					}

					@Override
					public void onTransactionFailure(String inErrorMessage,
							Bundle inResponse) {
						// This method gets called if transaction failed. //
						// Here in this case transaction is completed, but with
						// a failure. // Error Message describes the reason for
						// failure. // Response bundle contains the merchant
						// response parameters.
						Log.d("LOG", "Payment Transaction Failed " + inErrorMessage);
						Toast.makeText(getBaseContext(), "Payment Transaction Failed ", Toast.LENGTH_LONG).show();
					}*/

                    @Override
                    public void onTransactionResponse(Bundle inResponse) {
                        Log.d("LOG", "Payment Transaction is successful " + inResponse);
                            String message=inResponse.getString("RESPMSG");
                           String code=inResponse.getString("RESPCODE");
                        if (code.equalsIgnoreCase("01")) {
                            getTransactionCheckSum();
                        } else {
                            Toast.makeText(getApplicationContext(), "Payment Transaction response " + inResponse.toString(), Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void networkNotAvailable() { // If network is not
                        // available, then this
                        // method gets called.

                    }

                    @Override
                    public void clientAuthenticationFailed(String inErrorMessage) {
                        // This method gets called if client authentication
                        // failed. // Failure may be due to following reasons //
                        // 1. Server error or downtime. // 2. Server unable to
                        // generate checksum or checksum response is not in
                        // proper format. // 3. Server failed to authenticate
                        // that client. That is value of payt_STATUS is 2. //
                        // Error Message describes the reason for failure.
                    }

                    @Override
                    public void onErrorLoadingWebPage(int iniErrorCode,
                                                      String inErrorMessage, String inFailingUrl) {


                    }

                    // had to be added: NOTE
                    @Override
                    public void onBackPressedCancelTransaction() {
                        Toast.makeText(PaymentGateway.this, "Back pressed. Transaction cancelled", Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onTransactionCancel(String inErrorMessage, Bundle inResponse) {
                        Log.d("LOG", "Payment Transaction Failed " + inErrorMessage);
                        Toast.makeText(getBaseContext(), "Payment Transaction Failed ", Toast.LENGTH_LONG).show();

                    }

                });
    }

    @Override
    public void onSucess(String value) {
        if (progressDialog != null) {
            progressDialog.cancel();
        }

        if (apiCall == getCheckSum) {
            if (value.length() > 0) {
                try {
                    JSONObject jsonObject = new JSONObject(value);
                    final String checksum = jsonObject.getString("CHECKSUMHASH");
                    {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                StartTransaction(checksum);
                            }
                        });
                    }
                } catch (Exception ex) {
                    ex.fillInStackTrace();
                }
            }
        } else if (apiCall == getTranactionCheckSum) {
            if (value.length() > 0) {
                try {
                    JSONObject jsonObject = new JSONObject(value);
                    final String checksum = jsonObject.getString("CHECKSUMHASH");
                    {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                validateTransaction(checksum);
                            }
                        });
                    }
                } catch (Exception ex) {
                    ex.fillInStackTrace();
                }
            }
        }
        else{
            if(value.length()>0)
            {
                TransactionModel model=new TransactionModel(value);
                Util.showToast(PaymentGateway.this,model.getRESPMSG());
            }

        }
    }

    @Override
    public void onError(String value) {
Util.showToast(PaymentGateway.this,value);
    }
}
