package sportsfight.com.s.challenge;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import sportsfight.com.s.R;
import sportsfight.com.s.common.AppController;
import sportsfight.com.s.mainmodule.Challenge;

/**
 * Created by ashish.kumar on 10-10-2018.
 */

public class Challenge2  extends Activity implements View.OnClickListener {
    AppController controller;
    @BindView(R.id.back)
    Button back;
    @BindView(R.id.next)
    Button next;
    @BindView(R.id.cancel)
    Button cancel;
    @BindView(R.id.radio_gp)
    RadioGroup r_gp;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.challenge_2);
        controller = (AppController) getApplicationContext();
        ButterKnife.bind(this);
        back.setOnClickListener(this);
        cancel.setOnClickListener(this);
        next.setOnClickListener(this);
        r_gp.check(R.id.skip_know);
        r_gp.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if(i==R.id.choose_below)
                {
                    r_gp.check(R.id.skip_know);
                    Toast.makeText(Challenge2.this,"Ground Booking option is disabled will be available in future.",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back:
                onBackPressed();
                break;
            case R.id.next:
                startActivityForResult(new Intent(Challenge2.this, Challenge3.class).addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION), 1);
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