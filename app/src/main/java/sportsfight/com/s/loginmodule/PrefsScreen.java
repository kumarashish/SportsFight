package sportsfight.com.s.loginmodule;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.Image;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

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
import sportsfight.com.s.mainmodule.NewProfile;
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
   @BindView(R.id.gameList)
   LinearLayout gameList;
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
        controller = (AppController) getApplicationContext();
        ButterKnife.bind(this);
        submit.setOnClickListener(this);
        submit.setTypeface(controller.getDetailsFont());
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
        updateList();
        dialog.cancel();
    }

    public void updateList() {
        int count = 1;
        View inflatedLayout = null;
        LinearLayout linearLayout = null;
        gameList.removeAllViews();
        for (int i = 0; i < list.size(); i++) {

            GameModel model = list.get(i);
            if (count == 1) {
                inflatedLayout = getLayoutInflater().inflate(R.layout.game_list, gameList, false);
                linearLayout = (LinearLayout) inflatedLayout.findViewById(R.id.inflated_layout);
            }
            View GameRow = getLayoutInflater().inflate(R.layout.game_row,  linearLayout, false);
            ImageView icon = (ImageView) GameRow.findViewById(R.id.game_icon);
            TextView name = (TextView) GameRow.findViewById(R.id.game_name);
            final View gameSelected = (View) GameRow.findViewById(R.id.selected_icon);
            Picasso.with(PrefsScreen.this).load(model.getGameImage()).resize(40, 40).placeholder(R.drawable.logo).into(icon);
            name.setText(model.getGameName());
            gameSelected.setId(model.getGameId());
            linearLayout.addView(GameRow);
            View blankview = getLayoutInflater().inflate(R.layout.emptyview, null, false);
            linearLayout.addView(blankview);
            count++;
            if((count == 4)||(i==list.size()-1)) {
                count = 1;
                gameList.addView(inflatedLayout);
            }
            gameSelected.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if (!preferedGame.contains(gameSelected.getId())) {
                        preferedGame.add(gameSelected.getId());
                        gameSelected.setBackgroundResource(R.drawable.selected_button);

                    } else {
                        if (preferedGame.contains(gameSelected.getId())) {
                            preferedGame.remove(preferedGame.indexOf(gameSelected.getId()));
                            gameSelected.setBackgroundColor(getResources().getColor(android.R.color.transparent));
                        }
                    }
                }
            });

        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {

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
