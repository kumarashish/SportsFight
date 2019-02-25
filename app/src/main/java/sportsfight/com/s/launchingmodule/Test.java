package sportsfight.com.s.launchingmodule;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Spannable;
import android.text.SpannableString;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;
import com.prof.rssparser.Article;
import com.prof.rssparser.Parser;

import java.util.ArrayList;

import butterknife.BindView;
import sportsfight.com.s.R;
import sportsfight.com.s.adapter.DashboardItemAdapter;
import sportsfight.com.s.common.AppController;
import sportsfight.com.s.common.Common;
import sportsfight.com.s.common.CustomTypefaceSpan;
import sportsfight.com.s.common.MyCustomLayoutManager;
import sportsfight.com.s.interfaces.ViewAllCallBack;
import sportsfight.com.s.ipl.IplMatches;
import sportsfight.com.s.loginmodule.Login;
import sportsfight.com.s.mainmodule.Alerts;
import sportsfight.com.s.mainmodule.Challenge;
import sportsfight.com.s.mainmodule.Dashboard;
import sportsfight.com.s.mainmodule.NewsViewAll;
import sportsfight.com.s.mainmodule.Profile;
import sportsfight.com.s.mainmodule.Reminders;
import sportsfight.com.s.model.MatchesModel;

import sportsfight.com.s.staticpages.AboutUs;
import sportsfight.com.s.staticpages.Cancellation;
import sportsfight.com.s.staticpages.ContactUs;
import sportsfight.com.s.staticpages.Policies;
import sportsfight.com.s.staticpages.Terms_Conditions;
import sportsfight.com.s.util.Util;
import sportsfight.com.s.wallet.Wallet;

/**
 * Created by ashish.kumar on 08-08-2018.
 */

public class Test  extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener,ViewAllCallBack ,View.OnClickListener{

    Button logout;
    AppController controller;
    nl.dionsegijn.konfetti.KonfettiView viewKonfetti;
    LinearLayout walletLayout;
    ImageView walletIcon;
    TextView walletTv;
    LinearLayout reminderLayout;
    ImageView reminderIcon;
    TextView reminderTv;
    LinearLayout alertsLayout;
    ImageView alertsIcon;
    TextView alertsTv;
    LinearLayout myProfileLayout;
    ImageView myProfileIcon;
    TextView myProfileTv;
    RelativeLayout challengeLayout;
    ImageView challengeIcon;
    TextView challengeTv;
    TextView noItem;
    RecyclerView list;
    Button declareResult;
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
    View dahboardView;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.newdashboard);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        final DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        Menu m = navigationView.getMenu();
        for (int i=0;i<m.size();i++) {
            MenuItem mi = m.getItem(i);
            applyFontToMenuItem(mi);
        }
        dahboardView=(View) findViewById(R.id.dashdoard);
        Button button=(Button)dahboardView.findViewById(R.id.menu) ;
        parser= new Parser();
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawer.openDrawer(GravityCompat.START);
            }
        });

        initializeAll();
        getDashBoardData();
    }
    private void applyFontToMenuItem(MenuItem mi) {
        Typeface font = Typeface.createFromAsset(getAssets(), "detailsfont.otf");
        SpannableString mNewTitle = new SpannableString(mi.getTitle());
        mNewTitle.setSpan(new CustomTypefaceSpan("" , font), 0 , mNewTitle.length(),  Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        mi.setTitle(mNewTitle);
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
    public void getDashBoardData() {
        if (Util.isNetworkAvailable(Test.this)) {
            getWallet();
            dialog = Util.showPogress(Test.this);
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
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                updateData();
                            }
                        });



                    }

                    @Override
                    public void onError() {
                        dialog .cancel();
                        //what to do in case of error
                        Util.showToast(Test.this, "error");

                    }
                });
            }
        }
    }
    public void initializeAll() {
        logout = (Button) dahboardView.findViewById(R.id.logout);
        controller = (AppController) getApplicationContext();
        walletLayout = (LinearLayout) dahboardView.findViewById(R.id.wallet_layout);
        walletIcon = (ImageView) dahboardView.findViewById(R.id.wallet_icon);
        walletTv = (TextView) dahboardView.findViewById(R.id.wallet_tv);
        reminderLayout = (LinearLayout) dahboardView.findViewById(R.id.wallet_layout);
        reminderIcon = (ImageView) dahboardView.findViewById(R.id.reminder_icon);
        reminderTv = (TextView) dahboardView.findViewById(R.id.reminder_tv);
        alertsLayout = (LinearLayout) dahboardView.findViewById(R.id.alert_layout);
        alertsIcon = (ImageView) dahboardView.findViewById(R.id.alert_icon);
        alertsTv = (TextView) dahboardView.findViewById(R.id.alert_tv);
        myProfileLayout = (LinearLayout) dahboardView.findViewById(R.id.myprofile_layout);
        myProfileIcon = (ImageView) dahboardView.findViewById(R.id.myprofile_icon);
        myProfileTv = (TextView) dahboardView.findViewById(R.id.myprofile_tv);
        challengeLayout = (RelativeLayout) dahboardView.findViewById(R.id.challenge_layout);
        challengeIcon = (ImageView) dahboardView.findViewById(R.id.challenge_icon);
        challengeTv = (TextView) dahboardView.findViewById(R.id.challenge_tv);
        noItem = (TextView) dahboardView.findViewById(R.id.noItem);
        list = (RecyclerView) dahboardView.findViewById(R.id.dashboardItemList);
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
    }
    public void updateData()
    {        runOnUiThread(new Runnable() {
        @Override
        public void run() {
            if ((myMatches.size() > 0) || (upComingMatches.size() > 0) || (bids.size() > 0) || (tournaments.size() > 0) || (newslist.size() > 0)) {
                MyCustomLayoutManager mLayoutManager = new MyCustomLayoutManager(Test.this);
                list.setLayoutManager(mLayoutManager);
                list.smoothScrollToPosition(2);
                list.setAdapter(new DashboardItemAdapter(Test.this, bids, myMatches, upComingMatches, tournaments, newslist));
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
    public void CongratulationViewALL() {

    }

    @Override
    public void MyMatchesViewALL() {

    }

    @Override
    public void UpComingViewALL() {

    }

    @Override
    public void TournamentsViewALL() {

    }

    @Override
    public void NewsViewALL() {
        Intent in = new Intent(Test.this, NewsViewAll.class);
        NewsViewAll.list = newslist;
        Util.startActivityCommon(Test.this, in);
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
                Util.startActivityCommon(Test.this,new Intent(Test.this, Login.class));
                finish();
            }
        });
        dialog.show();
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
                    Intent in = new Intent(Test.this, Challenge.class);
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

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.about_us) {
            Intent in=new Intent(Test.this,AboutUs.class);
            startActivity(in);
        }
        else if (id == R.id.contact_us) {
            Intent in=new Intent(Test.this,ContactUs.class);
            startActivity(in);
        }
        else if (id == R.id.privacy_policies) {
            Intent in=new Intent(Test.this,Policies.class);
            startActivity(in);
        }
        else if (id == R.id.terms_conditions) {
            Intent in=new Intent(Test.this,Terms_Conditions.class);
            startActivity(in);
        }
        else if (id == R.id.cancellation) {
            Intent in=new Intent(Test.this,Cancellation.class);
            startActivity(in);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}

