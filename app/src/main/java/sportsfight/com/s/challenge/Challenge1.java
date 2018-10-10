package sportsfight.com.s.challenge;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Switch;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;
import sportsfight.com.s.R;
import sportsfight.com.s.common.AppController;
import sportsfight.com.s.mainmodule.Challenge;
import sportsfight.com.s.util.Util;

/**
 * Created by ashish.kumar on 10-10-2018.
 */

public class Challenge1  extends Activity implements View.OnClickListener,CompoundButton.OnCheckedChangeListener {
    AppController controller;
    @BindView(R.id.back)
    Button back;
    @BindView(R.id.next)
    Button next;
    @BindView(R.id.cancel)
    Button cancel;
    int year,month,day;
    @BindView(R.id.others_radio_btn)
    RadioButton other;
    @BindView(R.id.academy_radio_btn)
    RadioButton academy;
    @BindView(R.id.organization_radio_btn)
    RadioButton organization;
    @BindView(R.id.date)
    EditText date;
    @BindView(R.id.time)
    EditText time;
    private TimePicker timePicker1;
     String format;
    private Calendar calendar;
      boolean isDateSelected=false;
      boolean isTimeSelected=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.challenge_1);
        controller = (AppController) getApplicationContext();
        ButterKnife.bind(this);
        back.setOnClickListener(this);
        cancel.setOnClickListener(this);
        next.setOnClickListener(this);
        date.setOnClickListener(this);
        time.setOnClickListener(this);
        other.setOnCheckedChangeListener(this);
        academy.setOnCheckedChangeListener(this);
        organization.setOnCheckedChangeListener(this);
        other.setChecked(true);
        calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.back:
                onBackPressed();
                break;
            case R.id.next:
                if((isDateSelected)&&(isTimeSelected)) {
                    controller.getChallengeModel().setDate(date.getText().toString());
                    controller.getChallengeModel().setTime(time.getText().toString());
                    startActivityForResult(new Intent(Challenge1.this, Challenge2.class).addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION), 1);
                }else {
                    if(isDateSelected==false)
                    {
                        Toast.makeText(Challenge1.this,"Please choose match date",Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(Challenge1.this,"Please choose match time",Toast.LENGTH_SHORT).show();
                    }
                }
                break;
            case R.id.cancel:
                controller.getChallengeModel().clearChallenge();
                onBackPressed();
                break;
            case R.id.date:
                showDialog(1000);
                break;
            case R.id.time:
                showTimePickerDialog();
                break;


        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==RESULT_OK) {
            if (data.getBooleanExtra("isCloseRequested", false)) {
                onBackPressed();
            }
        }
    }
    @Override
    protected Dialog onCreateDialog(int id) {
        // TODO Auto-generated method stub
        if (id == 1000) {
            DatePickerDialog dialog=new DatePickerDialog(this, myDateListener, year, month, day);
            long minDate=86400000+System.currentTimeMillis();
            dialog.getDatePicker().setMinDate(minDate);
            return dialog;
        }
        return null;
    }
    private DatePickerDialog.OnDateSetListener myDateListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker arg0, int arg1, int arg2, int arg3) {
            int month= arg2+1;
            date.setText(month+"/"+arg3+"/"+arg1);
            isDateSelected=true;
        }
    };


    public void setTime(int hour, int min) {

        time.setText(new StringBuilder().append(hour).append(" : ").append(min));
        isTimeSelected=true;
    }

    public void showTimePickerDialog()
    {Calendar cal= Calendar.getInstance();
        int hour = cal.get(Calendar.HOUR_OF_DAY);
        int minute=cal.get(Calendar.MINUTE);
        TimePickerDialog timePickerDialog = new TimePickerDialog(Challenge1.this,
                new TimePickerDialog.OnTimeSetListener() {

                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay,
                                          int minute) {
                        setTime(hourOfDay, minute);

                    }
                },  hour, minute, true);
        timePickerDialog.show();
    }
    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
        if (b == true) {
            switch (compoundButton.getId()) {
                case R.id.others_radio_btn:
                    academy.setChecked(false);
                    organization.setChecked(false);
                    break;
                case R.id.academy_radio_btn:
                    other.setChecked(false);
                    organization.setChecked(false);
                    break;
                case R.id.organization_radio_btn:
                    academy.setChecked(false);
                    other.setChecked(false);
                    break;


            }
        }
    }
}
