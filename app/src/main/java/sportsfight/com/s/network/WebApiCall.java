package sportsfight.com.s.network;

import android.app.ProgressDialog;
import android.content.Context;
import sportsfight.com.s.util.Contants;
import org.json.JSONObject;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Authenticator;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Credentials;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.Route;
import sportsfight.com.s.common.Common;
import sportsfight.com.s.interfaces.WebApiResponseCallback;

/**
 * Created by Ashish.Kumar on 23-01-2018.
 */

public class WebApiCall {
    OkHttpClient client;
    private  OkHttpClient.Builder client1;
    public static final MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded");
    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    Context context;
    static OkHttpClient clientForMP;

    public WebApiCall(Context context) {
        client = new OkHttpClient();
        this.context = context;
    }

//public void getAUthentication(String userId,String Password)
//    {
//        client1 = new OkHttpClient.Builder();
//        client1.authenticator(new Authenticator() {
//            @Override
//            public Request authenticate(Route route, Response response) throws IOException {
//                if (responseCount(response) >= 3) {
//                    return null; // If we've failed 3 times, give up. - in real life, never give up!!
//                }
//
//                return response.request().newBuilder().header("Authorization", credential).build();
//            }
//        });
//        client1.connectTimeout(10, TimeUnit.SECONDS);
//        client1.writeTimeout(10, TimeUnit.SECONDS);
//        client1.readTimeout(30, TimeUnit.SECONDS);
//    }

    private int responseCount(Response response) {
        int result = 1;
        while ((response = response.priorResponse()) != null) {
            result++;
        }
        return result;
    }
    public void getData(String url,String token, final WebApiResponseCallback callback) {
        OkHttpClient client = new OkHttpClient.Builder().connectTimeout(60, TimeUnit.SECONDS)
                .writeTimeout(60, TimeUnit.SECONDS)
                .readTimeout(60, TimeUnit.SECONDS).build();
        final Request request = new Request.Builder().header("Token", token).url(url).build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                callback.onError(e.fillInStackTrace().toString());
            }
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.code() == 200) {
                    if (response != null) {
                        callback.onSucess(response.body().string());
                    } else {
                        if (response.message() != null) {
                            callback.onError(response.message());
                        } else {
                            callback.onError("No data found!");
                        }

                    }
                }else{
                    callback.onError(response.body().string());
                }
            }
        });
    }

    public String getData(String url) {
        OkHttpClient client = new OkHttpClient();
        client.newBuilder().connectTimeout(60000, TimeUnit.MILLISECONDS).readTimeout(60000, TimeUnit.MILLISECONDS).build();
        final Request request = new Request.Builder().url(url).build();
        try {
            Response response = client.newCall(request).execute();
            if (response.code() == 200) {
                return response.body().string();

            } else {
                return getErrorData();
            }

        } catch (Exception ex) {
            ex.fillInStackTrace();
            return getErrorData();
        }

    }

    public String getErrorData() {
        JSONObject object = new JSONObject();
        try {
            object.put("Status", false);
            object.put("Message", "Error occured");
        } catch (Exception ex) {
            ex.fillInStackTrace();
        }
        return object.toString();
    }
    public void getCheckSum(String url,String orderId,String customerId,String amount, final WebApiResponseCallback callback) {
        client = new OkHttpClient.Builder()
                .connectTimeout(60, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .build();
        RequestBody formBody = null;

        formBody = new FormBody.Builder()
                .add("ORDER_ID", orderId)
                .add("MID",sportsfight.com.s.util.Contants.MID)
                .add("CUST_ID", customerId)
                .add("INDUSTRY_TYPE_ID", Contants.INDUSTRY_TYPE_ID)
                .add("CHANNEL_ID", Contants.CHANNEL_ID)
                .add("TXN_AMOUNT", amount)
                .add("WEBSITE", Contants.WEBSITE)
                .add("CALLBACK_URL", Contants.CALLBACKURL+orderId)
                .build();

        okhttp3.Request request = new okhttp3.Request.Builder()
                .url(url)
                .post(formBody)
                .build();


        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                callback.onError(e.fillInStackTrace().toString());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.code() == 200) {
                    if (response != null) {
                        callback.onSucess(response.body().string());
                    } else {
                        if (response.message() != null) {
                            callback.onError(response.message());
                        } else {
                            callback.onError("No data found!");
                        }

                    }
                }
            }
        });
    }
    public void getAcessToken(String url, final WebApiResponseCallback callback) {
        client = new OkHttpClient.Builder()
                .connectTimeout(60, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .build();
        RequestBody formBody = null;
        formBody = new FormBody.Builder()
                .add("client_id", Common.liveClientId)
                .add("client_secret", Common.liveClientSecret)
                .add("grant_type", "client_credentials").build();

        okhttp3.Request request = new okhttp3.Request.Builder()
                .url(url)
                .post(formBody)
                .build();


        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                callback.onError(e.fillInStackTrace().toString());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.code() == 200) {
                    if (response != null) {
                        callback.onSucess(response.body().string());
                    } else {
                        if (response.message() != null) {
                            callback.onError(response.message());
                        } else {
                            callback.onError("No data found!");
                        }

                    }
                }
            }
        });
    }
    public String isPaymentValid(String url,String authorization) {
        OkHttpClient client = new OkHttpClient();
        client.newBuilder().connectTimeout(60000, TimeUnit.MILLISECONDS).readTimeout(60000, TimeUnit.MILLISECONDS).build();
        final Request request = new Request.Builder().addHeader("authorization","Bearer "+authorization).url(url).build();
        try {
            Response response = client.newCall(request).execute();
            if (response.code() == 200) {
                return

                        response.body().string();
            } else {
                return getErrorData();
            }

        } catch (Exception ex) {
            ex.fillInStackTrace();
            return getErrorData();
        }
        }
public void login(String url,String json,String userName,String password,final WebApiResponseCallback callback) {

        String credential = Credentials.basic( userName, password);
    OkHttpClient client = new OkHttpClient.Builder().connectTimeout(60, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS).build();
        RequestBody reqBody = RequestBody.create(JSON, json);
        Request request = new Request.Builder().header("Authorization", credential).url(url).post(reqBody).build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

                callback.onError(e.fillInStackTrace().toString());
            }
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.code() == 200 || response.code() == 201) {
                    if (response != null) {
                        callback.onSucess(response.body().string());
                    } else {
                        callback.onError(response.message());
                    }
                } else {
                    callback.onError(response.message());
                }
            }
        });
    }
    public void postData(String url, String json, String token,final WebApiResponseCallback callback) {
        client.newBuilder().connectTimeout(60000, TimeUnit.MILLISECONDS).readTimeout(60000, TimeUnit.MILLISECONDS).build();
        RequestBody reqBody = RequestBody.create(JSON, json);
        Request request = new Request.Builder().header("Token", token).url(url).post(reqBody).build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

                callback.onError(e.fillInStackTrace().toString());
            }
            @Override
            public void onResponse(Call call, Response response) throws IOException {
               // cancelProgressDialog(pd);
                if (response.code() == 200 || response.code() == 201) {
                    if (response != null) {
                        callback.onSucess(response.body().string());
                    } else {
                        callback.onError(response.message());
                    }
                } else {
                    callback.onError(response.message());
                }
            }
        });
    }
    }

