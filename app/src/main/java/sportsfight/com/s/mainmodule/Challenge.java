package sportsfight.com.s.mainmodule;

import android.app.Activity;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import sportsfight.com.s.R;
import sportsfight.com.s.adapter.GridViewAdapter;
import sportsfight.com.s.adapter.PlayerListAdapter;
import sportsfight.com.s.common.AppController;
import sportsfight.com.s.common.Common;
import sportsfight.com.s.interfaces.GameSlotCallback;
import sportsfight.com.s.interfaces.ListClickListner;
import sportsfight.com.s.interfaces.WebApiResponseCallback;
import sportsfight.com.s.model.Calenderr;
import sportsfight.com.s.model.ChallengeModel;
import sportsfight.com.s.model.GameModel;
import sportsfight.com.s.model.Slots;
import sportsfight.com.s.model.UserProfile;
import sportsfight.com.s.util.Util;
import sportsfight.com.s.wallet.Wallet;

/**
 * Created by Ashish.Kumar on 15-02-2018.
 */
public class Challenge extends Activity implements View.OnClickListener,WebApiResponseCallback,ListClickListner,GameSlotCallback{
    @BindView(R.id.view1)
    LinearLayout gameListView;
    @BindView(R.id.view2)
    LinearLayout playerListView;
    @BindView(R.id.view3)
    LinearLayout schedule;
    @BindView(R.id.view4)
    LinearLayout selectPoint;
    @BindView(R.id.view5)
    LinearLayout confirmation;
    @BindView(R.id.back)
    Button back;
    @BindView(R.id.cancelView1_btn)
    Button cancel;
    @BindView(R.id.nextView1_btn)
    Button next;
    @BindView(R.id.backView2_btn)
    Button back2;
    @BindView(R.id.nextView2_btn)
    Button next2;
    @BindView(R.id.backView3_btn)
    Button back3;
    @BindView(R.id.nextView3_btn)
    Button next3;
    @BindView(R.id.backView4_btn)
    Button back4;
    @BindView(R.id.nextView4_btn)
    Button next4;
    @BindView(R.id.nextView5_btn)
    Button next5;
    @BindView(R.id.chess_selected)
    ImageView chessSelectedIcon;
    @BindView(R.id.chess_view)
    View chessView;
    @BindView(R.id.golf_selected)
    ImageView golfSelectedIcon;
    @BindView(R.id.minigolf_view)
    View miniGolfView;
    @BindView(R.id.snookers_selected)
    ImageView snookerSelectedIcon;
    @BindView(R.id.pool_view)
    View poolView;
    @BindView(R.id.carrom_selected)
    ImageView carromSelectedIcon;
    @BindView(R.id.carrom_view)
    View carromView;

    @BindView(R.id.airhockey_selected)
    ImageView airhockeySelectedIcon;
    @BindView(R.id.airhockey_view)
    View airHockeyView;
    @BindView(R.id.foosball_selected)
    ImageView foosBallSelectedIcon;
    @BindView(R.id.foosBall_view)
    View foosBallView;
    @BindView(R.id.gameIcon)
    ImageView gameIcon;
    @BindView(R.id.gameName)
    TextView gameName;
    @BindView(R.id.month)
    TextView month;
    @BindView(R.id.year)
    TextView year;

    @BindView(R.id.next_btn)
    Button nextDays;
    @BindView(R.id.previous_btn)
    Button previousDays;

    @BindView(R.id.day1_llt)
    LinearLayout day1layout;
    @BindView(R.id.date1)
    TextView date1;
    @BindView(R.id.day1)
    TextView day1;
    @BindView(R.id.day1_view)
    View day1_view;

    @BindView(R.id.day2_llt)
    LinearLayout day2layout;
    @BindView(R.id.date2)
    TextView date2;
    @BindView(R.id.day2)
    TextView day2;
    @BindView(R.id.day2_view)
    View day2_view;

    @BindView(R.id.day3_llt)
    LinearLayout day3layout;
    @BindView(R.id.date3)
    TextView date3;
    @BindView(R.id.day3)
    TextView day3;
    @BindView(R.id.day3_view)
    View day3_view;

    @BindView(R.id.day4_llt)
    LinearLayout day4layout;
    @BindView(R.id.date4)
    TextView date4;
    @BindView(R.id.day4)
    TextView day4;
    @BindView(R.id.day4_view)
    View day4_view;
    @BindView(R.id.date)
    TextView date_View4;
    @BindView(R.id.month_year)
    TextView month_year_View4;
    @BindView(R.id.day_view4)
    TextView day_View4;
    @BindView(R.id.time)
    TextView time_View4;
    @BindView(R.id.am_pm)
    TextView am_pm_View4;
    @BindView(R.id.game_name)
    TextView gameName_View4;
    @BindView(R.id.opponent_name)
    TextView opponent_name_View4;
    @BindView(R.id.min_points)
    TextView min_points_View4;
    @BindView(R.id.max_points)
    TextView max_points_View4;
    @BindView(R.id.points_value)
    TextView points_value_View4;
    @BindView(R.id.increase)
    Button increase_View4;
    @BindView(R.id.decrease)
    Button decrease_View4;


    @BindView(R.id.date_view5)
    TextView date_View5;
    @BindView(R.id.month_year_view5)
    TextView month_year_View5;
    @BindView(R.id.month_year_day_view5)
    TextView month_year_day_View5;
    @BindView(R.id.time_view5)
    TextView time_View5;
    @BindView(R.id.am_pm_view5)
    TextView am_pm_View5;
    @BindView(R.id.game_name_view5)
    TextView gameName_View5;
    @BindView(R.id.opponent_name_view5)
    TextView opponent_name_View5;
    @BindView(R.id.points_value_view5)
    TextView points_value_view5;







    @BindView(R.id.day5_llt)
    LinearLayout day5layout;
    @BindView(R.id.date5)
    TextView date5;
    @BindView(R.id.day5)
    TextView day5;
    @BindView(R.id.day5_view)
    View day5_view;
    @BindView(R.id.grid)
    GridView grid;
    @BindView(R.id.basketball_selected)
    ImageView basketballSelectedIcon;
    @BindView(R.id.minibasketBall_view)
    View basketBallView;
    @BindView(R.id.tt_Selected)
    ImageView ttSelectedIcon;
    @BindView(R.id.tt_view)
    View ttView;
    @BindView(R.id.players_list)
    ListView playerList;
    @BindView(R.id.search)
    EditText search;
    @BindView( R.id.timeIcon)
    ImageView timeIcon;
    @BindView(R.id.timeValue)
            TextView timeValue;
    @BindView(R.id.freeslotValue)
            TextView freeSlot;
    @BindView(R.id.validate)
            Button checkAvailablility;
    AppController controller;
    int apiCall=0;
    final int getGameList=1,getPlayerList=2,getAvailableSlot=3,sendChallenge=4,validateSlot=5;
    Dialog dialog;
    ArrayList<GameModel> list = new ArrayList<>();
    ArrayList<UserProfile> playerProfileList=new ArrayList<>();
    ArrayList<Slots> avialableSlots=new ArrayList<>();
    boolean isNextClicked=false;
    boolean isPreviousClicked=false;
    ArrayList<Calenderr> dayslist = new ArrayList<>();
    int previousday=0;
    ChallengeModel model=new ChallengeModel();
    int gamePoint=0;
    PlayerListAdapter adapter;
    Boolean isSlotValidated=false;
    boolean isTimeSelected=false;
    private int pHour;
    private int pMinute;
    /** This integer will uniquely define the dialog to be used for displaying time picker.*/
    static final int TIME_DIALOG_ID = 0;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.challenge);
        controller = (AppController) getApplicationContext();

        ButterKnife.bind(this);
        if (Util.isNetworkAvailable(Challenge.this)) {
            apiCall = getGameList;
            dialog = Util.showPogress(Challenge.this);
            controller.getApiCall().getData(Common.GetListOfGames,controller.getPrefManager().getUserToken(), this);
        }
        cancel.setTypeface(controller.getDetailsFont());
        checkAvailablility.setTypeface(controller.getDetailsFont());
        next.setTypeface(controller.getDetailsFont());
        back2.setTypeface(controller.getDetailsFont());
        next2.setTypeface(controller.getDetailsFont());
        back3.setTypeface(controller.getDetailsFont());
        next3.setTypeface(controller.getDetailsFont());
        back4.setTypeface(controller.getDetailsFont());
        next4.setTypeface(controller.getDetailsFont());

        next5.setTypeface(controller.getDetailsFont());
        back2.setOnClickListener(this);
        next2.setOnClickListener(this);
        back3.setOnClickListener(this);
        next3.setOnClickListener(this);
        back4.setOnClickListener(this);
        next4.setOnClickListener(this);
        timeIcon.setOnClickListener(this);
        next5.setOnClickListener(this);
        cancel.setOnClickListener(this);
        next.setOnClickListener(this);
        chessView.setOnClickListener(this);
        ttView.setOnClickListener(this);
        timeValue.setOnClickListener(this);
        back4.setText("Back");
        checkAvailablility.setOnClickListener(this);
        poolView.setOnClickListener(this);
        basketBallView.setOnClickListener(this);
        airHockeyView.setOnClickListener(this);
        carromView.setOnClickListener(this);
        miniGolfView.setOnClickListener(this);
        foosBallView.setOnClickListener(this);
       nextDays.setOnClickListener(this);
       previousDays.setOnClickListener(this);
       increase_View4.setOnClickListener(this);
       decrease_View4.setOnClickListener(this);
        back.setOnClickListener(this);
        dayslist.addAll(Util.getNext15Days());
        setDays();
        search.setHint("Search by player name");
        search.setTypeface(controller.getDetailsFont());
        search.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable arg0) {
                // TODO Auto-generated method stub

                String text =search.getText().toString().toLowerCase(Locale.getDefault());
                    adapter.filter(text);
                }


            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1,
                                          int arg2, int arg3) {
                // TODO Auto-generated method stub
            }

            @Override
            public void onTextChanged(CharSequence arg0, int arg1, int arg2,
                                      int arg3) {
                // TODO Auto-generated method stub
            }
        });
        final Calendar cal = Calendar.getInstance();
        pHour = cal.get(Calendar.HOUR_OF_DAY);
        pMinute = cal.get(Calendar.MINUTE);

        /** Display the current time in the TextView */
        updateDisplay();
    }
    protected Dialog onCreateDialog(int id) {
        switch (id) {
            case TIME_DIALOG_ID:
                return new TimePickerDialog(this,
                        mTimeSetListener, pHour, pMinute, false);
        }
        return null;
    }
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.timeIcon:
              case  R.id.timeValue:
                showDialog(TIME_DIALOG_ID);
                break;
            case R.id.pool_view:

                model.setGameID( Util.getGameId(Common.pool, list));
                model.setGameName(Common.pool);
                int []min_max=Util.getMin_maxPoints(Common.pool, list);
                model.setMinPoints(min_max[0]);
                model.setMaxPoints(min_max[1]);
                snookerSelectedIcon.setVisibility(View.VISIBLE);
                carromSelectedIcon.setVisibility(View.GONE);
                airhockeySelectedIcon.setVisibility(View.GONE);
                chessSelectedIcon.setVisibility(View.GONE);
                ttSelectedIcon.setVisibility(View.GONE);
                foosBallSelectedIcon.setVisibility(View.GONE);
                golfSelectedIcon.setVisibility(View.GONE);
                basketballSelectedIcon.setVisibility(View.GONE);
                break;
            case R.id.carrom_view:
                model.setGameID( Util.getGameId(Common.carrom, list));
                model.setGameName(Common.carrom);
                min_max=Util.getMin_maxPoints(Common.carrom, list);
                model.setMinPoints(min_max[0]);
                model.setMaxPoints(min_max[1]);
                snookerSelectedIcon.setVisibility(View.GONE);
                carromSelectedIcon.setVisibility(View.VISIBLE);
                airhockeySelectedIcon.setVisibility(View.GONE);
                chessSelectedIcon.setVisibility(View.GONE);
                ttSelectedIcon.setVisibility(View.GONE);
                foosBallSelectedIcon.setVisibility(View.GONE);
                golfSelectedIcon.setVisibility(View.GONE);
                basketballSelectedIcon.setVisibility(View.GONE);
                break;
            case R.id.airhockey_view:
                model.setGameID( Util.getGameId(Common.airhockey, list));
                model.setGameName(Common.airhockey);
                min_max=Util.getMin_maxPoints(Common.airhockey, list);
                model.setMinPoints(min_max[0]);
                model.setMaxPoints(min_max[1]);
                snookerSelectedIcon.setVisibility(View.GONE);
                carromSelectedIcon.setVisibility(View.GONE);
                airhockeySelectedIcon.setVisibility(View.VISIBLE);
                chessSelectedIcon.setVisibility(View.GONE);
                ttSelectedIcon.setVisibility(View.GONE);
                foosBallSelectedIcon.setVisibility(View.GONE);
                golfSelectedIcon.setVisibility(View.GONE);
                basketballSelectedIcon.setVisibility(View.GONE);
                break;
            case R.id.chess_view:
                model.setGameID( Util.getGameId(Common.chess, list));
                model.setGameName(Common.chess);
                min_max=Util.getMin_maxPoints(Common.chess, list);
                model.setMinPoints(min_max[0]);
                model.setMaxPoints(min_max[1]);
                snookerSelectedIcon.setVisibility(View.GONE);
                carromSelectedIcon.setVisibility(View.GONE);
                airhockeySelectedIcon.setVisibility(View.GONE);
                chessSelectedIcon.setVisibility(View.VISIBLE);
                ttSelectedIcon.setVisibility(View.GONE);
                foosBallSelectedIcon.setVisibility(View.GONE);
                golfSelectedIcon.setVisibility(View.GONE);
                basketballSelectedIcon.setVisibility(View.GONE);
                break;
            case R.id.tt_view:
                model.setGameID(Util.getGameId(Common.tt, list));
                model.setGameName(Common.tt);
                min_max=Util.getMin_maxPoints(Common.tt, list);
                model.setMinPoints(min_max[0]);
                model.setMaxPoints(min_max[1]);
                snookerSelectedIcon.setVisibility(View.GONE);
                carromSelectedIcon.setVisibility(View.GONE);
                airhockeySelectedIcon.setVisibility(View.GONE);
                chessSelectedIcon.setVisibility(View.GONE);
                ttSelectedIcon.setVisibility(View.VISIBLE);
                foosBallSelectedIcon.setVisibility(View.GONE);
                golfSelectedIcon.setVisibility(View.GONE);
                basketballSelectedIcon.setVisibility(View.GONE);
                break;
            case R.id.foosBall_view:
                model.setGameID(Util.getGameId(Common.fussball, list));
                model.setGameName(Common.fussball);
                min_max=Util.getMin_maxPoints(Common.fussball, list);
                model.setMinPoints(min_max[0]);
                model.setMaxPoints(min_max[1]);
                snookerSelectedIcon.setVisibility(View.GONE);
                carromSelectedIcon.setVisibility(View.GONE);
                airhockeySelectedIcon.setVisibility(View.GONE);
                chessSelectedIcon.setVisibility(View.GONE);
                ttSelectedIcon.setVisibility(View.GONE);
                foosBallSelectedIcon.setVisibility(View.VISIBLE);
                golfSelectedIcon.setVisibility(View.GONE);
                basketballSelectedIcon.setVisibility(View.GONE);
                break;
            case R.id.minigolf_view:
                model.setGameID(Util.getGameId(Common.golf, list));
                model.setGameName(Common.golf);
                min_max=Util.getMin_maxPoints(Common.golf, list);
                model.setMinPoints(min_max[0]);
                model.setMaxPoints(min_max[1]);
                snookerSelectedIcon.setVisibility(View.GONE);
                carromSelectedIcon.setVisibility(View.GONE);
                airhockeySelectedIcon.setVisibility(View.GONE);
                chessSelectedIcon.setVisibility(View.GONE);
                ttSelectedIcon.setVisibility(View.GONE);
                foosBallSelectedIcon.setVisibility(View.GONE);
                golfSelectedIcon.setVisibility(View.VISIBLE);
                basketballSelectedIcon.setVisibility(View.GONE);
                break;
            case R.id.minibasketBall_view:
                model.setGameID(Util.getGameId(Common.minibasketball, list));
                model.setGameName(Common.minibasketball);
                min_max=Util.getMin_maxPoints(Common.minibasketball, list);
                model.setMinPoints(min_max[0]);
                model.setMaxPoints(min_max[1]);
                snookerSelectedIcon.setVisibility(View.GONE);
                carromSelectedIcon.setVisibility(View.GONE);
                airhockeySelectedIcon.setVisibility(View.GONE);
                chessSelectedIcon.setVisibility(View.GONE);
                ttSelectedIcon.setVisibility(View.GONE);
                foosBallSelectedIcon.setVisibility(View.GONE);
                golfSelectedIcon.setVisibility(View.GONE);
                basketballSelectedIcon.setVisibility(View.VISIBLE);
                break;
            case R.id.nextView1_btn:
                if (model.getGameID()!= -1) {
                    gameListView.setVisibility(View.GONE);
                    playerListView.setVisibility(View.VISIBLE);
                    apiCall = getPlayerList;
                    dialog = Util.showPogress(Challenge.this);
                    controller.getApiCall().getData(Common.getGamePlayersList(model.getGameID(),controller.getProfile().getUserId()),controller.getPrefManager().getUserToken(), this);
                } else {
                    Toast.makeText(Challenge.this, "Please select game for challenge.", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.backView2_btn:
                playerListView.setVisibility(View.GONE);
                gameListView.setVisibility(View.VISIBLE);
                model.setToDefault();
                break;
            case R.id.backView3_btn:
                playerListView.setVisibility(View.VISIBLE);
                isSlotValidated=false;
                isTimeSelected=false;
                freeSlot.setText("Select slot");
                schedule.setVisibility(View.GONE);
                model.setOpponentName("");
                model.setChallengedTo(-1);
                break;
            case R.id.backView4_btn:
                schedule.setVisibility(View.VISIBLE);
                gamePoint=0;
                model.setMatchPoints(0);
                selectPoint.setVisibility(View.GONE);
                break;

            case R.id.nextView2_btn:
                if (model.getChallengedTo()!= -1) {
                    apiCall=getAvailableSlot;
                    controller.getApiCall().getData(Common.getGetFreeSlotsForGame(model.getGameID(),Util.getTommorowDate(),15),controller.getPrefManager().getUserToken(),this);
                  dialog=Util.showPogress(Challenge.this);
                } else {
                    Util.showToast(Challenge.this, "Please select your opponent.");
                }

                break;
            case R.id.validate:
                apiCall=validateSlot;
                controller.getApiCall().postData(Common.isSlotAvailable,getSlotJSON().toString(),controller.getPrefManager().getUserToken(),this);
                dialog=Util.showPogress(Challenge.this);
                break;
            case R.id.nextView3_btn:
                if ( (model.getMatchDate().length() > 0)&&(isSlotValidated==true)&&(isTimeSelected==true)) {
                    schedule.setVisibility(View.GONE);
                    selectPoint.setVisibility(View.VISIBLE);
                    handleView4();
                } else {
                    if(isTimeSelected==false) {
                        Util.showToast(Challenge.this, "Select select the time for slot");
                    }else if(isSlotValidated==false)
                  {
                      Util.showToast(Challenge.this, "Please check availability of selected slot.");
                  }
                }
                break;
            case R.id.nextView4_btn:
                if((gamePoint>=model.getMinPoints())&&(gamePoint<=model.getMaxPoints())) {
                    if(controller.getProfile().getTotalPoints()>=gamePoint) {
                        apiCall = sendChallenge;
                        dialog = Util.showPogress(Challenge.this);
                        controller.getApiCall().postData(Common.getAddChallengeUrl, getAddChallengeJSON().toString(),controller.getPrefManager().getUserToken(), this);

                    }else {
                        Util.showToast(Challenge.this, "You dont have sufficient points in your wallet,either decrease points or go to profile and add more points.");
                    }
                }else{
                    Util.showToast(Challenge.this, "Please enter game points");
                }

                break;
            case R.id.nextView5_btn:
                model=null;
                finish();
                break;
            case R.id.back:
                finish();
            case R.id.previous_btn:
                previousday=0;
                setDays();
                nextDays.setVisibility(View.VISIBLE);
                previousDays.setVisibility(View.GONE);
                hadleClick(-1,true);
                break;
            case R.id.next_btn:
                previousday=5;
                previousDays.setVisibility(View.VISIBLE);
                nextDays.setVisibility(View.GONE);
                setDays();
                hadleClick(-1,true);

                break;
            case R.id.day1_view:
                hadleClick(1,false);

                break;
            case R.id.day2_view:
                hadleClick(2,false);
                break;
            case R.id.day3_view:
                hadleClick(3,false);
                break;
            case R.id.day4_view:
                hadleClick(4,false);
                break;
            case R.id.day5_view:
                hadleClick(5,false);
                break;
            case R.id.increase:
                if(gamePoint<model.getMaxPoints())
                {
                    gamePoint=gamePoint+100;
                    points_value_View4.setText(Integer.toString(gamePoint));
                    model.setMatchPoints(gamePoint);
                }else{
                    Util.showToast(Challenge.this,"Your match points should be less than maximum game points");
                }
                break;
            case R.id.decrease:
                if(gamePoint==model.getMinPoints())
                {
                    Util.showToast(Challenge.this,"Your match points should not be less than minimum game points");
                }else{

                    gamePoint=gamePoint-100;
                    points_value_View4.setText(Integer.toString(gamePoint));
                    model.setMatchPoints(gamePoint);
                }
                break;


        }
    }

    private void setDays() {
        if (dayslist.size() > 5) {
            date1.setText(dayslist.get(previousday + 0).getDate());
            day1.setText(dayslist.get(previousday + 0).getDay());
            date2.setText(dayslist.get(previousday + 1).getDate());
            day2.setText(dayslist.get(previousday + 1).getDay());
            date3.setText(dayslist.get(previousday + 2).getDate());
            day3.setText(dayslist.get(previousday + 2).getDay());
            date4.setText(dayslist.get(previousday + 3).getDate());
            day4.setText(dayslist.get(previousday + 3).getDay());
            date5.setText(dayslist.get(previousday + 4).getDate());
            day5.setText(dayslist.get(previousday + 4).getDay());
        }
    }

    @Override
    public void onSucess(final String value) {

        if (Util.getStatus(value) == true) {
            switch (apiCall) {
                case getPlayerList:
                    playerProfileListParsing(value);
                    break;
                case getGameList:

                    jsonParsing(value);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            gameListView.setVisibility(View.VISIBLE);
                        }
                    });
                    break;
                case getAvailableSlot:
                    availablSlotsParsing(value);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            setAvailableSlots();
                            playerListView.setVisibility(View.GONE);
                            schedule.setVisibility(View.VISIBLE);
                        }
                    });
                    break;
                case sendChallenge:
                    Util.showToast(Challenge.this,Util.getMessage(value));
                    showView5();
                    break;
            }
        }
        if(apiCall==validateSlot)
        {runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (Util.getStatus(value) == true){

                    freeSlot.setText("Slot is available");
                    isSlotValidated=true;
                }else{
                    JSONArray  availableSlot=Util.getResultJsonArray(value);
                    try {
                        String freeSlots = "Free Slots : "+availableSlot.get(0) + ", " + availableSlot.get(1);

                        freeSlot.setText(freeSlots);
                    }catch (Exception ex)
                    {
                        ex.fillInStackTrace();
                    }
                }
            }
        });

        }

        if (dialog != null) {
            dialog.cancel();
        }
        Util.showToast(Challenge.this, Util.getMessage(value));
    }

    @Override
    public void onError(String value) {
        if (dialog != null) {
            dialog.cancel();
        }
        if (Util.isSessionExpired(value)) {
            controller.logout();
            Util.Logout(Challenge.this);
        }
        Util.showToast(Challenge.this, Util.getMessage(value));
    }
public void showView5() {
    runOnUiThread(new Runnable() {
        @Override
        public void run() {
            if (dialog != null) {
                dialog.cancel();
            }
            updateConfirmationView();

        }
    });

}

    public void updateConfirmationView() {
        date_View5.setText(Util.getMatchDate(model.getMatchDate()));
        month_year_View5.setText(Util.getCurrentMonth() + "-" + Util.getCurrentYear());
        month_year_day_View5.setText(model.getDay());
        time_View5.setText(model.getMatchTime());
        int value = Integer.parseInt(model.getMatchTime().substring(0, 3));
        if (value < 12) {
            am_pm_View5.setText("am");
        } else {
            am_pm_View5.setText("pm");
        }

        gameName_View5.setText(model.getGameName());
        opponent_name_View5.setText("Vs " + model.getOpponentName().toUpperCase());
        points_value_view5.setText(Integer.toString(model.getMatchPoints()));
        confirmation.setVisibility(View.VISIBLE);
        selectPoint.setVisibility(View.GONE);
    }

    public void setAvailableSlots() {
        if (dialog != null) {
            dialog.cancel();
        }
        String gameNamee = Util.getGameName(model.getGameID(), list);
        gameName.setText(gameNamee.toUpperCase());
        gameIcon.setImageResource(getGameIcon(gameNamee));
        if (avialableSlots.size() > 0) {
            calendarView();
            grid.setAdapter(new GridViewAdapter(Challenge.this, avialableSlots.get(0).getList(),avialableSlots.get(0).getDate()));
        }
    }
    public void availablSlotsParsing(String response) {
        avialableSlots.clear();
        try {
            JSONObject jsonObject = new JSONObject(response);
            JSONArray jsonArray = jsonObject.getJSONArray("Result");
            for (int i = 0; i < jsonArray.length(); i++) {
                Slots slots = new Slots(jsonArray.getJSONObject(i));
                avialableSlots.add(slots);
            }
            setAvailableSlots();
        } catch (Exception ex) {

        }
    }
    public void playerProfileListParsing(String response) {
        playerProfileList.clear();
        try {
            JSONObject jsonObject = new JSONObject(response);
            JSONArray jsonArray = jsonObject.getJSONArray("Result");
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject profile = jsonArray.getJSONObject(i);
                UserProfile model = new UserProfile(profile);
                playerProfileList.add(model);
            }
            setPlayerProfile();
        } catch (Exception ex) {

        }
        if (dialog != null) {
            dialog.cancel();
        }

    }

    public void setPlayerProfile() {
        if (playerProfileList.size() > 0) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    adapter=new PlayerListAdapter(Challenge.this, playerProfileList);
                    playerList.setAdapter(adapter);
                }
            });
        } else {
            Util.showToast(Challenge.this, "No player found for selected game");
        }
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
        if (dialog != null) {
            dialog.cancel();
        }
    }
public void handleView4()
{
    min_points_View4.setText(Integer.toString(model.getMinPoints()));
    max_points_View4.setText(Integer.toString(model.getMaxPoints()));
    gameName_View4.setText(model.getGameName());
    opponent_name_View4.setText("Vs "+model.getOpponentName().toUpperCase());
    month_year_View4.setText(Util.getCurrentMonth()+"-"+Util.getCurrentYear());
    date_View4.setText(Util.getMatchDate(model.getMatchDate()));
    time_View4.setText(model.getMatchTime());

    int value=Integer.parseInt(model.getMatchTime().substring(0,2));
    if(value<12) {
        am_pm_View4.setText("am");
    }else{
        am_pm_View4.setText("pm");
    }
    points_value_View4.setText(Integer.toString(gamePoint));
    day_View4.setText(model.getDay());
}
    @Override
    public void onClick(int position) {
        model.setOpponentName(playerProfileList.get(position).getUserName());
        model.setChallengedTo(playerProfileList.get(position).getUserId());
    }

    public void calendarView() {
        previousDays.setVisibility(View.GONE);
        month.setText(Util.getCurrentMonth());
        year.setText(Util.getCurrentYear());
        day1_view.setOnClickListener(this);
        day2_view.setOnClickListener(this);
        day3_view.setOnClickListener(this);
        day4_view.setOnClickListener(this);
        day5_view.setOnClickListener(this);
    }

    public void hadleClick(int pos,boolean isNextClicked) {
        if(isNextClicked==false) {
            model.setMatchTime("");
            isSlotValidated=false;
            model.setMatchDate(avialableSlots.get(previousday + pos-1).getDate());
            grid.setAdapter(new GridViewAdapter(Challenge.this, avialableSlots.get(previousday + pos-1).getList(), avialableSlots.get(previousday + pos-1).getDate()));
            model.setDay(dayslist.get(previousday + pos-1).getDay());
            switch (pos) {
                case 1:

                    day1.setTextColor(getResources().getColor(R.color.white));
                    date1.setTextColor(getResources().getColor(R.color.white));
                    day1layout.setBackgroundColor(getResources().getColor(R.color.black_font));

                    day2.setTextColor(getResources().getColor(R.color.black_font));
                    date2.setTextColor(getResources().getColor(R.color.black_font));
                    day2layout.setBackgroundColor(getResources().getColor(R.color.white));

                    day3.setTextColor(getResources().getColor(R.color.black_font));
                    date3.setTextColor(getResources().getColor(R.color.black_font));
                    day3layout.setBackgroundColor(getResources().getColor(R.color.white));

                    day4.setTextColor(getResources().getColor(R.color.black_font));
                    date4.setTextColor(getResources().getColor(R.color.black_font));
                    day4layout.setBackgroundColor(getResources().getColor(R.color.white));


                    day5.setTextColor(getResources().getColor(R.color.black_font));
                    date5.setTextColor(getResources().getColor(R.color.black_font));
                    day5layout.setBackgroundColor(getResources().getColor(R.color.white));


                    break;
                case 2:
                    day1.setTextColor(getResources().getColor(R.color.black_font));
                    date1.setTextColor(getResources().getColor(R.color.black_font));
                    day1layout.setBackgroundColor(getResources().getColor(R.color.white));

                    day2.setTextColor(getResources().getColor(R.color.white));
                    date2.setTextColor(getResources().getColor(R.color.white));
                    day2layout.setBackgroundColor(getResources().getColor(R.color.black_font));

                    day3.setTextColor(getResources().getColor(R.color.black_font));
                    date3.setTextColor(getResources().getColor(R.color.black_font));
                    day3layout.setBackgroundColor(getResources().getColor(R.color.white));

                    day4.setTextColor(getResources().getColor(R.color.black_font));
                    date4.setTextColor(getResources().getColor(R.color.black_font));
                    day4layout.setBackgroundColor(getResources().getColor(R.color.white));


                    day5.setTextColor(getResources().getColor(R.color.black_font));
                    date5.setTextColor(getResources().getColor(R.color.black_font));
                    day5layout.setBackgroundColor(getResources().getColor(R.color.white));
                    break;
                case 3:
                    day1.setTextColor(getResources().getColor(R.color.black_font));
                    date1.setTextColor(getResources().getColor(R.color.black_font));
                    day1layout.setBackgroundColor(getResources().getColor(R.color.white));

                    day2.setTextColor(getResources().getColor(R.color.black_font));
                    date2.setTextColor(getResources().getColor(R.color.black_font));
                    day2layout.setBackgroundColor(getResources().getColor(R.color.white));

                    day3.setTextColor(getResources().getColor(R.color.white));
                    date3.setTextColor(getResources().getColor(R.color.white));
                    day3layout.setBackgroundColor(getResources().getColor(R.color.black_font));

                    day4.setTextColor(getResources().getColor(R.color.black_font));
                    date4.setTextColor(getResources().getColor(R.color.black_font));
                    day4layout.setBackgroundColor(getResources().getColor(R.color.white));


                    day5.setTextColor(getResources().getColor(R.color.black_font));
                    date5.setTextColor(getResources().getColor(R.color.black_font));
                    day5layout.setBackgroundColor(getResources().getColor(R.color.white));
                    break;
                case 4:
                    day1.setTextColor(getResources().getColor(R.color.black_font));
                    date1.setTextColor(getResources().getColor(R.color.black_font));
                    day1layout.setBackgroundColor(getResources().getColor(R.color.white));

                    day2.setTextColor(getResources().getColor(R.color.black_font));
                    date2.setTextColor(getResources().getColor(R.color.black_font));
                    day2layout.setBackgroundColor(getResources().getColor(R.color.white));

                    day3.setTextColor(getResources().getColor(R.color.black_font));
                    date3.setTextColor(getResources().getColor(R.color.black_font));
                    day3layout.setBackgroundColor(getResources().getColor(R.color.white));

                    day4.setTextColor(getResources().getColor(R.color.white));
                    date4.setTextColor(getResources().getColor(R.color.white));
                    day4layout.setBackgroundColor(getResources().getColor(R.color.black_font));


                    day5.setTextColor(getResources().getColor(R.color.black_font));
                    date5.setTextColor(getResources().getColor(R.color.black_font));
                    day5layout.setBackgroundColor(getResources().getColor(R.color.white));
                    break;
                case 5:
                    day1.setTextColor(getResources().getColor(R.color.black_font));
                    date1.setTextColor(getResources().getColor(R.color.black_font));
                    day1layout.setBackgroundColor(getResources().getColor(R.color.white));

                    day2.setTextColor(getResources().getColor(R.color.black_font));
                    date2.setTextColor(getResources().getColor(R.color.black_font));
                    day2layout.setBackgroundColor(getResources().getColor(R.color.white));

                    day3.setTextColor(getResources().getColor(R.color.black_font));
                    date3.setTextColor(getResources().getColor(R.color.black_font));
                    day3layout.setBackgroundColor(getResources().getColor(R.color.white));

                    day4.setTextColor(getResources().getColor(R.color.black_font));
                    date4.setTextColor(getResources().getColor(R.color.black_font));
                    day4layout.setBackgroundColor(getResources().getColor(R.color.white));

                    day5.setTextColor(getResources().getColor(R.color.white));
                    date5.setTextColor(getResources().getColor(R.color.white));
                    day5layout.setBackgroundColor(getResources().getColor(R.color.black_font));
                    break;

            }
        }else{
            day1.setTextColor(getResources().getColor(R.color.black_font));
            date1.setTextColor(getResources().getColor(R.color.black_font));
            day1layout.setBackgroundColor(getResources().getColor(R.color.white));

            day2.setTextColor(getResources().getColor(R.color.black_font));
            date2.setTextColor(getResources().getColor(R.color.black_font));
            day2layout.setBackgroundColor(getResources().getColor(R.color.white));

            day3.setTextColor(getResources().getColor(R.color.black_font));
            date3.setTextColor(getResources().getColor(R.color.black_font));
            day3layout.setBackgroundColor(getResources().getColor(R.color.white));

            day4.setTextColor(getResources().getColor(R.color.black_font));
            date4.setTextColor(getResources().getColor(R.color.black_font));
            day4layout.setBackgroundColor(getResources().getColor(R.color.white));

            day5.setTextColor(getResources().getColor(R.color.black_font));
            date5.setTextColor(getResources().getColor(R.color.black_font));
            day5layout.setBackgroundColor(getResources().getColor(R.color.white));
        }

    }
    public int getGameIcon(String gameName) {
        int gameIcon = 0;
        switch (gameName) {
            case Common.chess:
                gameIcon = R.drawable.chess_orange;
                break;
            case Common.pool:
                gameIcon = R.drawable.pool_orange;
                break;
            case Common.minibasketball:
                gameIcon = R.drawable.basket_ball;
                break;
            case Common.airhockey:
                gameIcon = R.drawable.air_hockey_orange;
                break;
            case Common.fussball:
                gameIcon = R.drawable.foos_ball_orange;
                break;
            case Common.tt:
                gameIcon = R.drawable.tt_orange;
                break;
            case Common.carrom:
                gameIcon = R.drawable.carrom_orange;
                break;
            case Common.golf:
                gameIcon = R.drawable.golf_orange;
                break;
        }
        return gameIcon;
    }

    @Override
    public void onGameSlotSelected(String gameDate, int gameSlotId,String time) {
        model.setMatchDate(gameDate);
        model.setGameSlotId(gameSlotId);
        model.setMatchTime(time);
    }

    @Override
    public void onBackPressed() {

        Util.showToast(Challenge.this,"If you want to exit from challenge then press back button at top.");
    }
    public JSONObject getAddChallengeJSON()
    {JSONObject jsonObject=new JSONObject();
        try{
            jsonObject.put("GameId",model.getGameID());
            jsonObject.put("ChallengedBy",controller.getProfile().getUserId());
            jsonObject.put("ChallengedTo",model.getChallengedTo());
            jsonObject.put("MatchDate",model.getMatchDate());
            jsonObject.put("SlotTime",model.getMatchTime());
            jsonObject.put("ChallengedPoints",model.getMatchPoints());
            jsonObject.put("DeviceId",Util.getDeviceID(Challenge.this));
        }catch (Exception ex)
        {
            ex.fillInStackTrace();
        }
        return jsonObject;
    }

    private TimePickerDialog.OnTimeSetListener mTimeSetListener =
            new TimePickerDialog.OnTimeSetListener() {
                public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                    pHour = hourOfDay;
                    pMinute = minute;
                    updateDisplay();

                }
            };

    public String getEndTime() {
        if (pMinute + 20 < 60) {
            String value = pad(pHour) + ":" + pad(pMinute + 20);
            return value;
        } else {
            int min = 60 - pMinute + 20;
            int hour = pHour + 1;
            String value = pad(hour) + ":" + pad(min);
            return value;
        }
    }
    /** Updates the time in the TextView */
    private void updateDisplay() {
        isTimeSelected = true;
        model.setMatchTime(new StringBuilder()
                .append(pad(pHour)).append(":")
                .append(pad(pMinute)) + "-" + getEndTime());
        timeValue.setText("Slot timming : " +
                new StringBuilder()
                        .append(pad(pHour)).append(":")
                        .append(pad(pMinute)) + "-" + getEndTime());
    }

    private static String pad(int c) {
        if (c >= 10)
            return String.valueOf(c);
        else
            return "0" + String.valueOf(c);
    }

    public JSONObject getSlotJSON() {
        if((model.getMatchDate()==null)||(model.getMatchDate().length()==0))
        {
            model.setMatchDate(Util.getTommorowDate());
        }
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("gameId", model.getGameID());
            jsonObject.put("matchDate", model.getMatchDate());
            jsonObject.put("SlotTime", model.getMatchTime());
        } catch (Exception ex) {
            ex.fillInStackTrace();
        }
        return jsonObject;
    }
}
