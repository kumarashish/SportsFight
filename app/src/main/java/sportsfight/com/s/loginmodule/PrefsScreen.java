package sportsfight.com.s.loginmodule;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.internal.Utils;
import sportsfight.com.s.R;
import sportsfight.com.s.common.AppController;
import sportsfight.com.s.common.Common;
import sportsfight.com.s.interfaces.WebApiResponseCallback;
import sportsfight.com.s.model.GameModel;
import sportsfight.com.s.model.InterestedGameModel;
import sportsfight.com.s.model.RegistrationModel;

import sportsfight.com.s.util.Util;
import sportsfight.com.s.util.Validation;

/**
 * Created by Ashish.Kumar on 18-01-2018.
 */

public class PrefsScreen extends Activity implements View.OnClickListener ,WebApiResponseCallback{
 //   Receiver myBroadcastReceiver;
    AppController controller;
    @BindView(R.id.submit)
    Button submit;
    @BindView(R.id.chess)
    ImageButton chess;
    @BindView(R.id.pool)
    ImageButton pool;
    @BindView(R.id.basketball)
    ImageButton basketball;
    @BindView(R.id.airhockey)
    ImageButton airhockey;
    @BindView(R.id.foosball)
    ImageButton foosball;
    @BindView(R.id.tt)
    ImageButton tt;
    @BindView(R.id.carrom)
    ImageButton carrom;
    @BindView(R.id.mini_golf)
    ImageButton mini_golf;


    boolean isChessSelected = false;
    boolean isPoolSelected = false;
    boolean isBasketBallSelected = false;
    boolean isAirHockeySelected = false;
    boolean isFoosBallSelected = false;
    boolean isTTSelected = false;
    boolean isCarromSlected = false;
    boolean isMiniGolfSelected = false;
    ArrayList<GameModel> list=new ArrayList<>();
    ArrayList<Integer> preferedGame=new ArrayList<>();
    Dialog dialog;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.preference);
        initializeAll();


    }

    public void initializeAll() {
      //  myBroadcastReceiver = new Receiver();
        controller = (AppController) getApplicationContext();
        ButterKnife.bind(this);
        submit.setOnClickListener(this);
        submit.setTypeface(controller.getDetailsFont());
        chess.setOnClickListener(this);
        pool.setOnClickListener(this);
        basketball.setOnClickListener(this);
        airhockey.setOnClickListener(this);
        foosball.setOnClickListener(this);
        tt.setOnClickListener(this);
        carrom.setOnClickListener(this);
        mini_golf.setOnClickListener(this);
        submit.setOnClickListener(this);
        String response=getIntent().getStringExtra("Value");
        dialog= Util.showPogress(this);
        jsonParsing(response);

    }

    public void jsonParsing(String response) {
        list.clear();
        try {
            JSONObject jsonObject = new JSONObject(response);
            JSONArray jsonArray = jsonObject.getJSONArray("Result");
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject game = jsonArray.getJSONObject(i);
                GameModel model = new GameModel(game);
                list.add(model);
            }
        } catch (Exception ex) {

        }
        dialog.cancel();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.chess:
                handleButtonClick(chess, isChessSelected, 1, "Chess");
                break;
            case R.id.pool:
                handleButtonClick(pool, isPoolSelected, 2, "pool or snooker");
                break;
            case R.id.basketball:
                handleButtonClick(basketball, isBasketBallSelected, 3, "Mini Basketball");
                break;
            case R.id.airhockey:
                handleButtonClick(airhockey, isAirHockeySelected, 4, "Air Hockey");
                break;
            case R.id.foosball:
                handleButtonClick(foosball, isFoosBallSelected, 5, "Fussball");
                break;
            case R.id.tt:
                handleButtonClick(tt, isTTSelected, 6, "Table Tennis");
                break;
            case R.id.carrom:
                handleButtonClick(carrom, isCarromSlected, 7, "Carrom");
                break;
            case R.id.mini_golf:
                handleButtonClick(mini_golf, isMiniGolfSelected, 8, "Mini Golf");
                break;
            case R.id.submit:
                if(preferedGame.size()>0) {
                    updateIntrestedGame();
                    if (Util.isNetworkAvailable(PrefsScreen.this)) {
                        dialog = Util.showPogress(PrefsScreen.this);
                        controller.getApiCall().postData(Common.SignUpUrl, getRegistrationJson().toString(),"", this);
                    }
                }else{
                    Toast.makeText(PrefsScreen.this,"Please select atleast 1 game",Toast.LENGTH_SHORT).show();
                }
                break;
        }

    }

    public void updateIntrestedGame() {
        Integer[] intGame = new Integer[preferedGame.size()];
        for (int i = 0; i < preferedGame.size(); i++) {
            intGame[i] = preferedGame.get(i);
        }
        Registration.model.setIntGame(intGame);
    }

    public void handleButtonClick(ImageButton btn, boolean isSelected, int pos, String gameName) {
        if (isSelected == true) {

            preferedGame.remove(preferedGame.indexOf(getGameID(gameName)));
            btn.setBackgroundResource(R.drawable.whitecorner);

        } else {
            preferedGame.add(getGameID(gameName));
            btn.setBackgroundResource(R.drawable.selected_button);

        }
        handleSelectionValue(pos);
    }

    public void handleSelectionValue(int pos) {
        switch (pos) {
            case 1:
                if (isChessSelected) {
                    isChessSelected = false;
                } else {
                    isChessSelected = true;
                }
                break;
            case 2:
                if (isPoolSelected) {
                    isPoolSelected = false;
                } else {
                    isPoolSelected = true;
                }
                break;
            case 3:
                if (isBasketBallSelected) {
                    isBasketBallSelected = false;
                } else {
                    isBasketBallSelected = true;
                }
                break;
            case 4:
                if (isAirHockeySelected) {
                    isAirHockeySelected = false;
                } else {
                    isAirHockeySelected = true;
                }
                break;
            case 5:
                if (isFoosBallSelected) {
                    isFoosBallSelected = false;
                } else {
                    isFoosBallSelected = true;
                }
                break;
            case 6:
                if (isTTSelected) {
                    isTTSelected = false;
                } else {
                    isTTSelected = true;
                }

                break;
            case 7:
                if (isCarromSlected) {
                    isCarromSlected = false;
                } else {
                    isCarromSlected = true;
                }
                break;
            case 8:
                if (isMiniGolfSelected) {
                    isMiniGolfSelected = false;
                } else {
                    isMiniGolfSelected = true;
                }
                break;
        }

    }

    public int getGameID(String gameName) {
        int val = -1;
        switch (gameName) {
            case "pool or snooker":
                val = list.get(0).getGameId();
                break;
            case "Carrom":
                val = list.get(1).getGameId();
                break;
            case "Chess":
                val = list.get(2).getGameId();
                break;
            case "Mini Golf":
                val = list.get(3).getGameId();
                break;
            case "Mini Basketball":
                val = list.get(4).getGameId();
                break;
            case "Table Tennis":
                val = list.get(5).getGameId();
                break;
            case "Fussball":
                val = list.get(6).getGameId();
                break;
            case "Air Hockey":
                val = list.get(7).getGameId();
                break;

        }
        return val;
    }
//    public void onResume() {
//        super.onResume();
//        registerReceiver(myBroadcastReceiver, new IntentFilter("android.net.conn.CONNECTIVITY_CHANGE"));
//    }
//
//    public void onPause() {
//        super.onPause();
//        unregisterReceiver(myBroadcastReceiver);
//    }

    public JSONObject getRegistrationJson() {
        RegistrationModel model = Registration.model;
        JSONObject jsonObject = new JSONObject();

        try {

            jsonObject.put("Name", model.getUserName());
            jsonObject.put("Password", model.getPassword());
            jsonObject.put("Email", model.getUserEmail());
            jsonObject.put("Mobile", model.getUserMobile());
            jsonObject.put("DeviceId",Util.getDeviceID(PrefsScreen.this) );
            jsonObject.put("FCMId", controller.getPrefManager().getFcmToken());
            jsonObject.put("UserInterestedGamesDTO", PrefferedGame());
        } catch (Exception ex) {
            ex.fillInStackTrace();
        }
        return jsonObject;
    }


    public JSONArray PrefferedGame() {
        JSONArray jsonArray = new JSONArray();
        if (preferedGame.size() > 0) {
            for (int i = 0; i < preferedGame.size(); i++) {
                JSONObject jsonObject = new JSONObject();
                try {
                    jsonObject.put("GameId", preferedGame.get(i));
                    jsonArray.put(i, jsonObject);
                } catch (Exception ex) {
                    ex.fillInStackTrace();
                }

            }
        }
        return jsonArray;
    }

    @Override
    public void onSucess(String value) {
        if(dialog!=null) {
            Util.cancelDialog(PrefsScreen.this, dialog);
        }
        if (Util.isNextScreenNeedTobeCalled(value, PrefsScreen.this)) {
            Intent in = new Intent(PrefsScreen.this, Verification.class);
            Util.startActivityCommon(PrefsScreen.this, in);
            Util.showToast(PrefsScreen.this,Util.getMessage(value));
        }
    }

    @Override
    public void onError(String value) {
        if(dialog!=null) {
            Util.cancelDialog(PrefsScreen.this, dialog);
        }
        Util.showToast(PrefsScreen.this, Util.getMessage(value));

    }
}
