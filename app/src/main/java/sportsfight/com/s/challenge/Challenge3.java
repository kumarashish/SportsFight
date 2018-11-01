package sportsfight.com.s.challenge;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.security.PublicKey;

import butterknife.BindView;
import butterknife.ButterKnife;
import sportsfight.com.s.R;
import sportsfight.com.s.common.AppController;
import sportsfight.com.s.mainmodule.Challenge;

/**
 * Created by ashish.kumar on 10-10-2018.
 */

public class Challenge3  extends Activity implements View.OnClickListener {
    AppController controller;
    @BindView(R.id.back)
    Button back;
    @BindView(R.id.next)
    Button next;
    @BindView(R.id.cancel)
    Button cancel;
    @BindView(R.id.btn_50)
    Button btn_50;
    @BindView(R.id.btn_100)
    Button btn_100;
    @BindView(R.id.btn_200)
    Button btn_200;
    @BindView(R.id.btn_400)
    Button btn_400;
    @BindView(R.id.btn_500)
    Button btn_500;
    @BindView(R.id.decrease)
    ImageView decrease;
    @BindView(R.id.increase)
    ImageView increase;
    @BindView(R.id.coins)
    EditText coins;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.challenge_3);
        controller = (AppController) getApplicationContext();
        ButterKnife.bind(this);
        back.setOnClickListener(this);
        cancel.setOnClickListener(this);
        next.setOnClickListener(this);
        btn_50.setOnClickListener(this);
        btn_100.setOnClickListener(this);
        btn_200.setOnClickListener(this);
        btn_400.setOnClickListener(this);
        btn_500.setOnClickListener(this);
        increase.setOnClickListener(this);
        decrease.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back:
                onBackPressed();
                break;
            case R.id.next:
                if ((coins.getText().length() > 0)) {
                    if(Integer.parseInt(coins.getText().toString())>49) {
                        controller.getChallengeModel().setChallenge_coins(coins.getText().toString());
                        startActivityForResult(new Intent(Challenge3.this, Challenge4.class).addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION), 1);
                    }else{
                        Toast.makeText(Challenge3.this, "Minimum coins for challenge is 50", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(Challenge3.this, "Please enter coins for challenge", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.cancel:
                Intent _result = new Intent();
                _result.putExtra("isCloseRequested",true);
                setResult(Activity.RESULT_OK, _result);
                controller.getChallengeModel().clearChallenge();
                finish();
                break;
            case R.id.btn_50:
                coins.setText("50");
                btn_100.setBackgroundDrawable(getDrawable(R.drawable.grey_gradient_button));
                btn_200.setBackgroundDrawable(getDrawable(R.drawable.grey_gradient_button));
                btn_400.setBackgroundDrawable(getDrawable(R.drawable.grey_gradient_button));
                btn_500.setBackgroundDrawable(getDrawable(R.drawable.grey_gradient_button));
                btn_50.setBackgroundDrawable(getDrawable(R.drawable.orange_gradient_button));

                break;
            case R.id.btn_100:

                btn_50.setBackgroundDrawable(getDrawable(R.drawable.grey_gradient_button));
                btn_200.setBackgroundDrawable(getDrawable(R.drawable.grey_gradient_button));
                btn_400.setBackgroundDrawable(getDrawable(R.drawable.grey_gradient_button));
                btn_500.setBackgroundDrawable(getDrawable(R.drawable.grey_gradient_button));
                btn_100.setBackgroundDrawable(getDrawable(R.drawable.orange_gradient_button));

                coins.setText("100");
                break;
            case R.id.btn_200:
                coins.setText("200");
                btn_50.setBackgroundDrawable(getDrawable(R.drawable.grey_gradient_button));
                btn_100.setBackgroundDrawable(getDrawable(R.drawable.grey_gradient_button));
                btn_400.setBackgroundDrawable(getDrawable(R.drawable.grey_gradient_button));
                btn_500.setBackgroundDrawable(getDrawable(R.drawable.grey_gradient_button));
                btn_200.setBackgroundDrawable(getDrawable(R.drawable.orange_gradient_button));
                break;
            case R.id.btn_400:
                coins.setText("400");
                btn_50.setBackgroundDrawable(getDrawable(R.drawable.grey_gradient_button));
                btn_100.setBackgroundDrawable(getDrawable(R.drawable.grey_gradient_button));
                btn_200.setBackgroundDrawable(getDrawable(R.drawable.grey_gradient_button));
                btn_500.setBackgroundDrawable(getDrawable(R.drawable.grey_gradient_button));
                btn_400.setBackgroundDrawable(getDrawable(R.drawable.orange_gradient_button));
                break;
            case R.id.btn_500:
                coins.setText("500");
                btn_50.setBackgroundDrawable(getDrawable(R.drawable.grey_gradient_button));
                btn_100.setBackgroundDrawable(getDrawable(R.drawable.grey_gradient_button));
                btn_200.setBackgroundDrawable(getDrawable(R.drawable.grey_gradient_button));
                btn_400.setBackgroundDrawable(getDrawable(R.drawable.grey_gradient_button));
                btn_500.setBackgroundDrawable(getDrawable(R.drawable.orange_gradient_button));
                break;
            case R.id.increase:
                increaseCoins();
                break;
            case R.id.decrease:
                decreaseCoins();
                break;


        }

    }

    public void increaseCoins() {
        if (coins.getText().length() == 0) {
            coins.setText("50");
        } else {
            int val = Integer.parseInt(coins.getText().toString());
            if (val < val + 10) {
                val = val + 10;
                coins.setText(Integer.toString(val));
            } else {
                Toast.makeText(Challenge3.this, "You have already entered maximum coins", Toast.LENGTH_SHORT).show();
            }
        }
    }
    public void decreaseCoins()
    {
        if (coins.getText().length() == 0) {
            Toast.makeText(Challenge3.this, "Please enter challenge coins", Toast.LENGTH_SHORT).show();
        } else {
            int val = Integer.parseInt(coins.getText().toString());
            if (49 < val - 10) {
                val = val - 10;
                coins.setText(Integer.toString(val));
            } else {
                Toast.makeText(Challenge3.this, "Minimum coins required for challenge is 50", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==RESULT_OK) {
            if (data.getBooleanExtra("isCloseRequested", false)) {
                Intent _result = new Intent();
                _result.putExtra("isCloseRequested",true);
                setResult(Activity.RESULT_OK, _result);
                finish();
            }
        }
    }
}