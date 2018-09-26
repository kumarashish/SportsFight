package sportsfight.com.s.mainmodule;

import android.app.Activity;
import android.app.Dialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.ContactsContract;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.prof.rssparser.Article;
import com.prof.rssparser.Parser;
import com.squareup.picasso.Picasso;
import org.json.JSONArray;
import org.json.JSONObject;
import java.util.ArrayList;
import butterknife.BindView;
import butterknife.ButterKnife;
import nl.dionsegijn.konfetti.models.Shape;
import nl.dionsegijn.konfetti.models.Size;
import sportsfight.com.s.R;
import sportsfight.com.s.adapter.DashboardItemAdapter;
import sportsfight.com.s.common.AppController;
import sportsfight.com.s.common.Common;
import sportsfight.com.s.common.MyCustomLayoutManager;
import sportsfight.com.s.interfaces.PlaceBidCallBack;
import sportsfight.com.s.interfaces.ViewAllCallBack;
import sportsfight.com.s.interfaces.WebApiResponseCallback;
import sportsfight.com.s.ipl.IplMatches;
import sportsfight.com.s.ipl.MyBid;
import sportsfight.com.s.launchingmodule.WelcomeActivity;
import sportsfight.com.s.loginmodule.Login;
import sportsfight.com.s.model.MatchesModel;
import sportsfight.com.s.results.DeclareResult;
import sportsfight.com.s.util.Util;
import sportsfight.com.s.wallet.Wallet;
/**
 * Created by Ashish.Kumar on 17-01-2018.
 */

public class Dashboard extends Activity implements View.OnClickListener ,WebApiResponseCallback,PlaceBidCallBack,ViewAllCallBack{
    @BindView(R.id.logout)
    Button logout;
    AppController controller;
    nl.dionsegijn.konfetti.KonfettiView viewKonfetti;
    @BindView(R.id.wallet_layout)
    LinearLayout walletLayout;
    @BindView(R.id.wallet_icon)
    ImageView walletIcon;
    @BindView(R.id.wallet_tv)
    TextView walletTv;

    @BindView(R.id.reminder_layout)
    LinearLayout reminderLayout;
    @BindView(R.id.reminder_icon)
    ImageView reminderIcon;
    @BindView(R.id.reminder_tv)
    TextView reminderTv;

    @BindView(R.id.alert_layout)
    LinearLayout alertsLayout;
    @BindView(R.id.alert_icon)
    ImageView alertsIcon;
    @BindView(R.id.alert_tv)
    TextView alertsTv;


    @BindView(R.id.myprofile_layout)
    LinearLayout myProfileLayout;
    @BindView(R.id.myprofile_icon)
    ImageView myProfileIcon;
    @BindView(R.id.myprofile_tv)
    TextView myProfileTv;

    @BindView(R.id.challenge_layout)
    RelativeLayout challengeLayout;
    @BindView(R.id.challenge_icon)
    ImageView challengeIcon;
    @BindView(R.id.challenge_tv)
    TextView challengeTv;
    @BindView(R.id.noItem)
    TextView noItem;
    @BindView(R.id.dashboardItemList)
    RecyclerView list;

    @BindView(R.id.menu)
            Button declareResult;
    @BindView(R.id.ipl)
    ImageButton ipl;
    Dialog dialog;
    ArrayList<MatchesModel> myMatches=new ArrayList<>();
    ArrayList<MatchesModel> upComingMatches=new ArrayList<>();
  public static  ArrayList<MatchesModel> upComingEventMatches=new ArrayList<>();
    ArrayList<MatchesModel> bids=new ArrayList<>();
    ArrayList<MatchesModel> tournaments=new ArrayList<>();
    ArrayList<MatchesModel> news=new ArrayList<>();
    int selectedId=-1;
    int player1BidPoints=0;
    int player2BidPoints=0;
    boolean isPlayer1Selected=false;
    boolean isPlayer2Selected=false;
    int apiCall=0;
    int getDashBoardApiCall=1,addBidAPiCall=2,getWallet=3;
     Dialog dialogg;
    MatchesModel matchmodel=null;
    public static boolean isCongratulationShown=false;
    String urlString = "https://www.news18.com/rss/cricketnext.xml";
    Parser parser;
    ArrayList<Article> newslist=new ArrayList<>();


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dashboard);
      parser= new Parser();
        initializeAll();
    }

    public void initializeAll() {
        upComingEventMatches.clear();
        ButterKnife.bind(this);
        controller = (AppController) getApplicationContext();
        ipl.setOnClickListener(this);
        logout.setOnClickListener(this);
        walletLayout.setOnClickListener(this);
        walletIcon.setOnClickListener(this);
        walletTv.setOnClickListener(this);
        reminderLayout.setOnClickListener(this);
        reminderIcon.setOnClickListener(this);
        reminderTv.setOnClickListener(this);
        alertsLayout.setOnClickListener(this);
        alertsIcon.setOnClickListener(this);
        alertsTv.setOnClickListener(this);
        myProfileLayout.setOnClickListener(this);
        myProfileIcon.setOnClickListener(this);
        myProfileTv.setOnClickListener(this);
        challengeLayout.setOnClickListener(this);
        challengeIcon.setOnClickListener(this);
        challengeTv.setOnClickListener(this);
        challengeTv.setTypeface(controller.getDetailsFont());
        ipl.setVisibility(View.GONE);
        if (controller.getProfile().getUserId() == 1) {
            declareResult.setVisibility(View.VISIBLE);
        } else {
            declareResult.setVisibility(View.GONE);
        }
        declareResult.setOnClickListener(this);
        getDashBoardData();
    }
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.logout:
                showAlert();
                break;
            case R.id.wallet_layout:
            case R.id.wallet_icon:
            case R.id.wallet_tv:
                startActivity(new Intent(this, Wallet.class));
                break;
            case R.id.reminder_icon:
            case R.id.reminder_layout:
            case R.id.reminder_tv:
                startActivity(new Intent(this,Reminders.class));
                break;
            case R.id.myprofile_icon:
            case R.id.myprofile_layout:
            case R.id.myprofile_tv:
                startActivityForResult(new Intent(this,Profile.class),1);
                break;
            case R.id.alert_icon:
            case R.id.alert_layout:
            case R.id.alert_tv:
                startActivity(new Intent(this,Alerts.class));
                break;
            case R.id.menu:
                startActivity(new Intent(this, DeclareResult.class));
                break;
            case R.id.challenge_icon:
            case R.id.challenge_layout:
            case R.id.challenge_tv:
                //startActivity(new Intent(this, Challenge.class));
                if((view.getId()==challengeIcon.getId())||(view.getId()==challengeTv.getId()))
                {
                    handleClick();
                }else{
                    challengeLayout.setBackground(getResources().getDrawable(R.drawable.challenge_selector));
                }
                Intent in = new Intent(Dashboard.this, Challenge.class);
                startActivity(in);
                break;
            case R.id.ipl:

                startActivity(new Intent(this, IplMatches.class));
                break;
        }
    }

    public void handleClick() {
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                challengeLayout.setBackground(getResources().getDrawable(R.drawable.purple_circle));
            }
        }, 10);
        challengeLayout.setBackground(getResources().getDrawable(R.drawable.grey_circle));
    }

    public void showAlert() {
        final Dialog dialog = new Dialog(this);
        LottieAnimationView animationView;
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.logout);
        final Window window = dialog.getWindow();
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
        window.clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        Button no = (Button) dialog.findViewById(R.id.no);
        Button yes = (Button) dialog.findViewById(R.id.yes);
        no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.cancel();
            }
        });
        yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                controller.logout();
                dialog.cancel();
                Util.startActivityCommon(Dashboard.this,new Intent(Dashboard.this, Login.class));
                finish();
            }
        });
        dialog.show();
    }

    public void getDashBoardData() {
        if (Util.isNetworkAvailable(Dashboard.this)) {
            getWallet();
            dialog = Util.showPogress(Dashboard.this);
//            apiCall = getDashBoardApiCall;
//            dialog = Util.showPogress(Dashboard.this);
//            controller.getApiCall().getData(Common.getGetDashBoardrl(controller.getProfile().getUserId()),controller.getPrefManager().getUserToken(), Dashboard.this);
    if(newslist.size()==0) {
    parser.execute(urlString);
    parser.onFinish(new Parser.OnTaskCompleted() {

        @Override
        public void onTaskCompleted(ArrayList<Article> list) {
            //what to do when the parsing is done
            //the Array List contains all article's data. For example you can use it for your adapter.
         //   Util.showToast(Dashboard.this, "Success");
            newslist = list;
            apiCall = getDashBoardApiCall;
            controller.getApiCall().getData(Common.getGetDashBoardrl(controller.getProfile().getUserId()),controller.getPrefManager().getUserToken(), Dashboard.this);

        }

        @Override
        public void onError() {
            dialog .cancel();
            //what to do in case of error
            Util.showToast(Dashboard.this, "error");

        }
    });
}else{
    apiCall = getDashBoardApiCall;
    controller.getApiCall().getData(Common.getGetDashBoardrl(controller.getProfile().getUserId()),controller.getPrefManager().getUserToken(), Dashboard.this);
}
 }
        }


    public void getWallet() {
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                String result = controller.getApiCall().getData(Common.getWalletPointsUrl(controller.getProfile().getUserId()));
                pointsParsing(result);
            }
        });
        t.start();
    }

    public void pointsParsing(String result) {
        if (Util.getStatus(result) == true) {
            controller.setPoints(Util.getPoints(result));
        }
    }
    @Override
    public void onSucess(String value) {
        if (Util.getStatus(value) == true) {
            if (apiCall == getDashBoardApiCall) {
                dashBoardJsonParsing(value);
            } else {
                if (dialog != null) {
                    dialog.cancel();
                }
                if (dialogg != null) {
                    dialogg.cancel();
                }

                Util.showToast(Dashboard.this, Util.getMessage(value));
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        getDashBoardData();
                    }
                });
            }
        } else {
            if (dialog != null) {
                dialog.cancel();
            }
            Util.showToast(Dashboard.this, Util.getMessage(value));
            if(newslist.size()>0)
            {
                updateData();
            }
        }

    }

    public void dashBoardJsonParsing(String value) {
        myMatches.clear();
        upComingMatches.clear();
        bids.clear();
        tournaments.clear();
        upComingEventMatches.clear();
        news.clear();
        try {
            JSONObject jsonObject = new JSONObject(value);
            JSONObject result = jsonObject.getJSONObject("Result");
            JSONArray mybidsResult = result.isNull("Bids") ? null : result.getJSONArray("Bids");
            JSONArray mymatchesResult = result.isNull("MyMatches") ? null : result.getJSONArray("MyMatches");
            JSONArray upComingEventMatchess = result.isNull("UpcomingEventMatches") ? null : result.getJSONArray("UpcomingEventMatches");
            JSONArray upcomingmatchesResult = result.isNull("UpcomingMatches") ? null : result.getJSONArray("UpcomingMatches");
            JSONArray tournamentResults = result.isNull("Tournaments") ? null : result.getJSONArray("Tournaments");
            JSONArray newsResult = result.isNull("News") ? null : result.getJSONArray("News");
//            if (mybidsResult != null) {
//                for (int i = 0; i < mybidsResult.length(); i++) {
//                    bids.add(new MatchesModel(mybidsResult.getJSONObject(i)));
//                }
//            }
            if (mymatchesResult != null) {
                for (int i = 0; i < mymatchesResult.length(); i++) {
                    myMatches.add(new MatchesModel(mymatchesResult.getJSONObject(i)));
                }
            }
            if (upcomingmatchesResult != null) {
                for (int i = 0; i < upcomingmatchesResult.length(); i++) {
                    upComingMatches.add(new MatchesModel(upcomingmatchesResult.getJSONObject(i)));
                }
            }
            if (upComingEventMatchess != null) {
                for (int i = 0; i < upComingEventMatchess.length(); i++) {
                    upComingEventMatches.add(new MatchesModel(upComingEventMatchess.getJSONObject(i)));
                }
            }
            if (tournamentResults != null) {
            }
            if (newslist.size() > 0) {

            }
            updateData();
        } catch (Exception ex) {
            ex.fillInStackTrace();
        }

    }
 public void updateData()
 {        runOnUiThread(new Runnable() {
     @Override
     public void run() {
         if ((myMatches.size() > 0) || (upComingMatches.size() > 0) || (bids.size() > 0) || (tournaments.size() > 0) || (newslist.size() > 0)) {
             MyCustomLayoutManager mLayoutManager = new MyCustomLayoutManager(Dashboard.this);
             list.setLayoutManager(mLayoutManager);
             list.smoothScrollToPosition(2);
             list.setAdapter(new DashboardItemAdapter(Dashboard.this, bids, myMatches, upComingMatches, tournaments, newslist));
             list.setVisibility(View.VISIBLE);
             noItem.setVisibility(View.GONE);
         } else {
             list.setVisibility(View.GONE);
             noItem.setVisibility(View.VISIBLE);
         }
         if (dialog != null) {
             dialog.cancel();
         }
     }
 });}
    @Override
    public void onError(String value) {
        if (dialog != null) {
            dialog.cancel();
        }
        if(newslist.size()>0)
        {
            updateData();
        }
        if (Util.isSessionExpired(value)) {
            controller.logout();
            Util.Logout(Dashboard.this);
        }
        if(Util.getMessage(value).length()>0) {
            Util.showToast(Dashboard.this, Util.getMessage(value));
        }
    }
//    public void notifyUser() {
//        Intent notificationIntent = new Intent(Dashboard.this,
//                WelcomeActivity.class);
//
//
//
//        notificationIntent.putExtra("clicked", "Notification Clicked");
//        notificationIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
//                | Intent.FLAG_ACTIVITY_SINGLE_TOP); // To open only one activity
//
//
//        // Invoking the default notification service
//
//        NotificationManager mNotificationManager;
//        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(
//                Dashboard.this);
//        Uri uri = RingtoneManager
//                .getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
//        mBuilder.setContentTitle("Reminder");
//        mBuilder.setContentText("You have new Reminders.");
//        mBuilder.setTicker("New Reminder Alert!");
//        mBuilder.setSmallIcon(R.drawable.logo_s);
//        mBuilder.setSound(uri);
//        mBuilder.setAutoCancel(true);
//
//        // Add Big View Specific Configuration
//        NotificationCompat.InboxStyle inboxStyle = new NotificationCompat.InboxStyle();
//        String[] events =new String[2];
//
//        events[0] = new String("Your first line text ");
//        events[1] = new String(" Your second line text");
//
//
//
//        // Sets a title for the Inbox style big view
//        inboxStyle.setBigContentTitle("You have Reminders:");
//
//        // Moves events into the big view
//        for (int i = 0; i < events.length; i++) {
//            inboxStyle.addLine(events[i]);
//        }
//
//        mBuilder.setStyle(inboxStyle);
//
//        // Creates an explicit intent for an Activity in your app
//        Intent resultIntent = new Intent(Dashboard.this,
//               WelcomeActivity.class);
//
//        TaskStackBuilder stackBuilder = TaskStackBuilder
//                .create(Dashboard.this);
//        stackBuilder.addParentStack(Dashboard.this);
//
//
//        // Adds the Intent that starts the Activity to the top of the stack
//
//
//        stackBuilder.addNextIntent(resultIntent);
//        PendingIntent resultPendingIntent = stackBuilder
//                .getPendingIntent(0, PendingIntent.FLAG_CANCEL_CURRENT);
//
//        mBuilder.setContentIntent(resultPendingIntent);
//        mNotificationManager = (NotificationManager) getApplicationContext()
//                .getSystemService(Context.NOTIFICATION_SERVICE);
//

//
//        // notificationID allows you to update the notification later  on.
//
//
//        mNotificationManager.notify(999, mBuilder.build());
//    }
    public void placeBidAlert(final MatchesModel model) {
        selectedId = -1;
        player1BidPoints = 0;
        player2BidPoints = 0;
        isPlayer1Selected = false;
        isPlayer2Selected = false;
        dialogg = new Dialog(this);
        LottieAnimationView animationView;
        dialogg.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogg.setContentView(R.layout.bidalert);
        final Window window = dialogg.getWindow();
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
        window.clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        window.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.transaparent)));
        Button placeBid = (Button) dialogg.findViewById(R.id.placeBid);
        ImageView player1 = (ImageView) dialogg.findViewById(R.id.player1);
        TextView player1Name = (TextView) dialogg.findViewById(R.id.player1Name);
        final TextView player1BidValue = (TextView) dialogg.findViewById(R.id.player1BidValue);
        final ImageView player1_selected_icon = (ImageView) dialogg.findViewById(R.id.player1_selected_icon);
        final LinearLayout player1BidOptions = (LinearLayout) dialogg.findViewById(R.id.player1BidOptions);
        ImageView increasePlayer1Bid = (ImageView) dialogg.findViewById(R.id.increasePlayer1Bid);
        ImageView decreasePlayer1Bid = (ImageView) dialogg.findViewById(R.id.decreasePlayer1Bid);
        ImageView player2 = (ImageView) dialogg.findViewById(R.id.player2);
        TextView player2Name = (TextView) dialogg.findViewById(R.id.player2Name);
        final ImageView player2_selected_icon = (ImageView) dialogg.findViewById(R.id.player2_selected_icon);
        final LinearLayout player2BidOptions = (LinearLayout) dialogg.findViewById(R.id.player2BidOptions);
        ImageView increasePlayer2Bid = (ImageView) dialogg.findViewById(R.id.increasePlayer2Bid);
        ImageView decreasePlayer2Bid = (ImageView) dialogg.findViewById(R.id.decreasePlayer2Bid);
        final TextView player2BidValue = (TextView) dialogg.findViewById(R.id.player2BidValue);
        View player1View = (View) dialogg.findViewById(R.id.view1);
        View player2View = (View) dialogg.findViewById(R.id.view2);
        placeBid.setTypeface(controller.getDetailsFont());
        player1Name.setText(model.getPlayer1Name());
        player2Name.setText(model.getPlayer2Name());
        if (model.getPlayer2ImageUrl().length() > 0) {
            Picasso.with(Dashboard.this).load(model.getPlayer2ImageUrl()).placeholder(R.drawable.user_icon).into(player2);
        } else {
            player2.setImageResource(R.drawable.user_icon);
        }
        if (model.getPlayer1ImageUrl().length() > 0) {
            Picasso.with(Dashboard.this).load(model.getPlayer1ImageUrl()).placeholder(R.drawable.user_icon).into(player1);
        } else {
            player1.setImageResource(R.drawable.user_icon);
        }
        if(model.getMyBidToId()!=0)
        {
            placeBid.setText("Update Bid");
        }
        if(model.getMyBidToId()==model.getPlayer1Id())
        {
            player2_selected_icon.setVisibility(View.GONE);
            player1_selected_icon.setVisibility(View.VISIBLE);
            selectedId = model.getPlayer1Id();
            player1BidPoints=model.getMyBid();
            player1BidValue.setText(Integer.toString(player1BidPoints));
            player1BidOptions.setVisibility(View.VISIBLE);
            player2BidOptions.setVisibility(View.INVISIBLE);
        }else if(model.getMyBidToId()==model.getPlayer2Id())
        {
            player2_selected_icon.setVisibility(View.VISIBLE);
            player1_selected_icon.setVisibility(View.GONE);
            selectedId = model.getPlayer2Id();
            player2BidPoints=model.getMyBid();
            player2BidValue.setText(Integer.toString(player2BidPoints));
            player2BidOptions.setVisibility(View.VISIBLE);
            player1BidOptions.setVisibility(View.GONE);
        }
        increasePlayer1Bid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (controller.getProfile().getTotalPoints() >= ((player1BidPoints + 100)- model.getMyBid())) {
                    player1BidPoints = player1BidPoints + 100;
                } else {
                    int value= (player1BidPoints+ 100)- model.getMyBid();
                    Util.showToast(Dashboard.this, "You do not have " +value + " points in your wallet.Please add more points or decrease bid");
                }
                player2BidPoints = 0;
                player1BidValue.setText(Integer.toString(player1BidPoints));
                player2BidValue.setText(Integer.toString(player2BidPoints));
            }
        });
        decreasePlayer1Bid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (model.getMyBidToId() == model.getPlayer1Id()) {
                    if (model.getMyBid() < player1BidPoints) {
                        player1BidPoints = player1BidPoints - 100;
                    }else{
                        Util.showToast(Dashboard.this, "Your Bid should be greater then previously made bid.");

                    }
                }else {
                    if (player1BidPoints != 0) {
                        player1BidPoints = player1BidPoints - 100;
                    } else {
                        Util.showToast(Dashboard.this, "Your bid is already 0");
                    }

                }
                player2BidPoints = 0;
                player1BidValue.setText(Integer.toString(player1BidPoints));
                player2BidValue.setText(Integer.toString(player2BidPoints));
            }
        });
        increasePlayer2Bid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (controller.getProfile().getTotalPoints() >=  ((player2BidPoints + 100)- model.getMyBid())) {
                    player2BidPoints = player2BidPoints + 100;
                } else {
                    int neededPoints = (player2BidPoints + 100)- model.getMyBid();
                    Util.showToast(Dashboard.this, "You do not have " + neededPoints + " points in your wallet.Please add more points or decrease bid");
                }
                player1BidPoints = 0;
                player1BidValue.setText(Integer.toString(player1BidPoints));
                player2BidValue.setText(Integer.toString(player2BidPoints));
            }
        });
        decreasePlayer2Bid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (model.getMyBidToId() == model.getPlayer2Id()) {
                    if (model.getMyBid() < player2BidPoints) {
                        player2BidPoints = player2BidPoints - 100;
                    }else{
                        Util.showToast(Dashboard.this, "Your Bid should be greater then previously made bid.");

                    }
                } else {
                    if (player2BidPoints != 0) {
                        player2BidPoints = player2BidPoints - 100;
                    } else {
                        Util.showToast(Dashboard.this, "Your bid is already 0");
                    }

                }
                player1BidPoints = 0;
                player1BidValue.setText(Integer.toString(player1BidPoints));
                player2BidValue.setText(Integer.toString(player2BidPoints));
            }
        });
        player1View.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(model.getMyBidToId()==model.getPlayer2Id())
                {
                    Util.showToast(Dashboard.this,"You can not place bid on this player,as You have already placed bid on player2.");
                }else {
                    player2_selected_icon.setVisibility(View.GONE);
                    player1_selected_icon.setVisibility(View.VISIBLE);
                    selectedId = model.getPlayer1Id();
                    player1BidPoints=model.getMyBid();
                    player1BidValue.setText(Integer.toString(player1BidPoints));
                    player1BidOptions.setVisibility(View.VISIBLE);
                    player2BidOptions.setVisibility(View.INVISIBLE);
                }

            }
        });
        player2View.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(model.getMyBidToId()==model.getPlayer1Id())
                {
                    Util.showToast(Dashboard.this,"You can not place bid on this player,as You have already placed bid on player1.");
                }else {
                    player1_selected_icon.setVisibility(View.GONE);
                    player2_selected_icon.setVisibility(View.VISIBLE);
                    selectedId = model.getPlayer2Id();
                    player1BidOptions.setVisibility(View.INVISIBLE);
                    player2BidOptions.setVisibility(View.VISIBLE);
                    player2BidPoints=model.getMyBid();
                    player2BidValue.setText(Integer.toString(player2BidPoints));
                }

            }
        });
        placeBid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (selectedId == -1) {
                    Util.showToast(Dashboard.this, "Please select player.");
                } else {
                    if ((player1BidPoints > 0) || (player2BidPoints > 0)) {
                        int points = 0;
                        if (player1BidPoints > 0) {
                            points = player1BidPoints;
                        } else {
                            points = player2BidPoints;
                        }
                        placeBid(model, points);
                    } else {
                        Util.showToast(Dashboard.this, "Please enter your bid.");
                    }
                }
            }
        });
        dialogg.show();
    }

    public void placeBid(MatchesModel model, int points) {
        if (Util.isNetworkAvailable(Dashboard.this)) {
            apiCall = addBidAPiCall;
            dialog = Util.showPogress(Dashboard.this);
            if(model.getMyBidToId()!=0)
            {
                controller.getApiCall().postData(Common.getUpdateBidUrl, getUpdateBidJSON(model, points).toString(),controller.getPrefManager().getUserToken(), this);
            }else {
                controller.getApiCall().postData(Common.getPlaceBidUrl, getAddBidJSON(model, points).toString(),controller.getPrefManager().getUserToken(), this);
            }
        }
    }
    @Override
    public void onPlaceBidClick(final MatchesModel model) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                matchmodel=model;
                placeBidAlert(model);
            }
        });
    }
    @Override
    public void CongratulationViewALL() {
        Intent in = new Intent(Dashboard.this,WonViewAll.class);
        WonViewAll.list = bids;
        Util.startActivityCommon(Dashboard.this, in);
    }

    @Override
    public void MyMatchesViewALL() {
        Intent in = new Intent(Dashboard.this,MyMatchesViewAll.class);
       MyMatchesViewAll.list = myMatches;
        Util.startActivityCommon(Dashboard.this, in);
    }

    @Override
    public void UpComingViewALL() {
        Intent in = new Intent(Dashboard.this, UpcomingMatchesViewAll.class);
        UpcomingMatchesViewAll.list = upComingMatches;
        Util.startActivityForResultCommon(Dashboard.this, in);
    }

    @Override
    public void TournamentsViewALL() {

    }

    @Override
    public void NewsViewALL() {
        Intent in = new Intent(Dashboard.this, NewsViewAll.class);
        NewsViewAll.list = newslist;
        Util.startActivityCommon(Dashboard.this, in);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==1)
        {
            getDashBoardData();
        }

    }

    public JSONObject getAddBidJSON(MatchesModel model, int points) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("ChallengeId", model.getChallengeId());
            jsonObject.put("BidBy", controller.getProfile().getUserId());
            jsonObject.put("BidOn", selectedId);
            jsonObject.put("BidPoints", points);
            jsonObject.put("DeviceId", Util.getDeviceID(Dashboard.this));
        } catch (Exception ex) {
            ex.fillInStackTrace();
        }
        return jsonObject;
    }

    public JSONObject getUpdateBidJSON(MatchesModel model, int points) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("ChallengeId", model.getChallengeId());
            jsonObject.put("BidBy", controller.getProfile().getUserId());
            jsonObject.put("BidPoints", points);
            jsonObject.put("DeviceId", Util.getDeviceID(Dashboard.this));
        } catch (Exception ex) {
            ex.fillInStackTrace();
        }
        return jsonObject;
    }

    @Override
    protected void onDestroy() {
        //isCongratulationShown = false;
        super.onDestroy();
    }
}
