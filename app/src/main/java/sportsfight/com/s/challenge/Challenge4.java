package sportsfight.com.s.challenge;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;

import org.json.JSONObject;
import org.w3c.dom.Text;

import butterknife.BindView;
import butterknife.ButterKnife;
import sportsfight.com.s.R;
import sportsfight.com.s.common.AppController;
import sportsfight.com.s.common.Common;
import sportsfight.com.s.interfaces.WebApiResponseCallback;
import sportsfight.com.s.loginmodule.Login;
import sportsfight.com.s.mainmodule.Challenge;
import sportsfight.com.s.mainmodule.NewDashBoard;
import sportsfight.com.s.model.SendChallengeModel;
import sportsfight.com.s.util.Util;

/**
 * Created by ashish.kumar on 10-10-2018.
 */

public class Challenge4  extends Activity implements View.OnClickListener,WebApiResponseCallback {
    AppController controller;
    @BindView(R.id.back)
    Button back;
    @BindView(R.id.submit)
    Button submit;
    @BindView(R.id.cancel)
    Button cancel;
    @BindView(R.id.challenge_Coins)
    TextView coins;
    @BindView(R.id.date_time)
    TextView date_time;
    @BindView(R.id. game)
    TextView  game;
    @BindView(R.id.teamValue)
    TextView teamValue;
    @BindView(R.id.teamLabel)
    TextView teamLabel;
    Dialog dialog;
    Dialog congratulation=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.challenge_4);
        controller = (AppController) getApplicationContext();
        ButterKnife.bind(this);
        back.setOnClickListener(this);
        cancel.setOnClickListener(this);
        submit.setOnClickListener(this);
        setValue();
    }

    public void setValue() {
        SendChallengeModel model = controller.getChallengeModel();
        coins.setText(model.getChallenge_coins());
        date_time.setText(model.getDate() + "  " + model.getTime());
        teamValue.setText(model.getTeamName());
        game.setText(model.getGameName());
        if (model.isTeam()==false) {
            teamLabel.setText("Player Name");
        }
    }
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back:
                onBackPressed();
                break;
            case R.id.submit:
                if (Util.isNetworkAvailable(Challenge4.this)) {
                    dialog = Util.showPogress(Challenge4.this);
                    controller.getApiCall().postData(Common.getAddChallengeUrl, getChallengeJson().toString(), controller.getPrefManager().getUserToken(), Challenge4.this);
                }
                break;
            case R.id.cancel:
                Intent _result = new Intent();
                _result.putExtra("isCloseRequested",true);
                setResult(Activity.RESULT_OK, _result);
                controller.getChallengeModel().clearChallenge();
                finish();
                break;

        }

    }

    public JSONObject getChallengeJson() {
        SendChallengeModel model = controller.getChallengeModel();
        JSONObject jsonObject = new JSONObject();
        {
            try {
                jsonObject.put("GameId", model.getGameId());
                jsonObject.put("ChallengedBy", controller.getProfile().getUserId());
                jsonObject.put("ChallengedTo", model.getTeamId());
                jsonObject.put("IsAccepted", false);
                jsonObject.put("MatchDate", model.getDate());
                jsonObject.put("SlotTime", model.getTime());
                jsonObject.put("GroundId", null);
                jsonObject.put("ChallengedPoints", model.getChallenge_coins());
                jsonObject.put("DeviceId", Util.getDeviceID(Challenge4.this));
            } catch (Exception ex) {
                ex.fillInStackTrace();
            }


        }
        return jsonObject;
    }
    @Override
    public void onSucess(String value) {
        if (Util.getStatus(value)) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    showAlert();
                }
            });

        }
        Util.showToast(Challenge4.this,Util.getMessage(value));
        if(dialog!=null)
        {
            dialog.cancel();
        }

    }

    @Override
    public void onError(String value) {
        if (Util.isSessionExpired(value)) {
            controller.logout();
            Util.Logout(Challenge4.this);
        }
        if(dialog!=null)
        {
            dialog.cancel();
        }
        Util.showToast(Challenge4.this,Util.getMessage(value));
    }

    public void showAlert() {

        congratulation = new Dialog(this);

        congratulation .requestWindowFeature(Window.FEATURE_NO_TITLE);
        congratulation .setContentView(R.layout.challenge_sucess_alert);
        congratulation.setCancelable(false);
        final Window window = congratulation .getWindow();
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
        window.clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        TextView coins = (TextView) congratulation .findViewById(R.id.challenge_Coins);
        TextView date_time = (TextView) congratulation .findViewById(R.id.date_time);
        TextView game = (TextView) congratulation .findViewById(R.id.game);
        TextView teamValue = (TextView) congratulation .findViewById(R.id.teamValue);
        TextView teamLabel = (TextView) congratulation .findViewById(R.id.teamLabel);
        Button done = (Button) congratulation .findViewById(R.id.done);
        SendChallengeModel model = controller.getChallengeModel();
        coins.setText(model.getChallenge_coins());
        date_time.setText(model.getDate() + "  " + model.getTime());
        teamValue.setText(model.getTeamName());
        game.setText(model.getGameName());
        if (model.isTeam()==false) {
            teamLabel.setText("Player Name");
        }
        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent _result = new Intent();
                _result.putExtra("isCloseRequested", true);
                setResult(Activity.RESULT_OK, _result);
                controller.getChallengeModel().clearChallenge();
                finish();
            }
        });
        congratulation .show();
    }


}