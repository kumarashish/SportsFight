package sportsfight.com.s.ipl;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import sportsfight.com.s.R;
import sportsfight.com.s.adapter.IPLMatchHistoryAdapter;
import sportsfight.com.s.adapter.IPLMatchesAdapter;
import sportsfight.com.s.adapter.IPLPagerAdapter;
import sportsfight.com.s.adapter.UpcomingMatchesAdapter;
import sportsfight.com.s.common.AppController;
import sportsfight.com.s.common.Common;
import sportsfight.com.s.interfaces.PlaceBidCallBack;
import sportsfight.com.s.interfaces.WebApiResponseCallback;
import sportsfight.com.s.mainmodule.Dashboard;
import sportsfight.com.s.model.IPLResultModel;
import sportsfight.com.s.model.MatchesModel;
import sportsfight.com.s.util.Util;
import sportsfight.com.s.wallet.Wallet;

/**
 * Created by Ashish.Kumar on 12-04-2018.
 */

public class IplMatches extends Activity implements View.OnClickListener,PlaceBidCallBack ,WebApiResponseCallback{
    AppController controller;
    @BindView(R.id.back)
    Button back;
    @BindView(R.id.upcoming_matches)
    TextView upcomingMatches;
    @BindView(R.id.match_results)
    TextView results;
    @BindView(R.id.view_pager)
    ViewPager pager;
    @BindView(R.id.myBid)
    Button myBid;
     ArrayList<IPLResultModel> matchHistory=new ArrayList<>();
    Dialog dialog;
    int selectedId = -1;
    int player1BidPoints = 0;
    int  player2BidPoints = 0;
    boolean  isPlayer1Selected = false;
   boolean isPlayer2Selected = false;
    Dialog dialogg;
    int apiCall=0;
    int refreshScreen=1,getHistory=2,placeBid=3;
    IPLPagerAdapter adapter;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ipl_screen);
        ButterKnife.bind(this);
        controller = (AppController) getApplicationContext();

        back.setOnClickListener(this);
        myBid.setOnClickListener(this);
        myBid.setTypeface(controller.getDetailsFont());
        upcomingMatches.setOnClickListener(this);
        results.setOnClickListener(this);
        if (Util.isNetworkAvailable(IplMatches.this)) {
            apiCall = getHistory;
            dialog = Util.showPogress(IplMatches.this);
            controller.getApiCall().getData(Common.getIplMatchHistoryUrl(Util.getCurrentDateWithoutHHMMSS()),controller.getPrefManager().getUserToken(), this);
        }
        pager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }
            @Override
            public void onPageSelected(int position) {
                if(position==1)
                {
                    upcomingMatches.setBackgroundColor(getResources().getColor(R.color.white));
                    upcomingMatches.setTextColor(getResources().getColor(R.color.black_font));
                    results.setBackgroundColor(getResources().getColor(R.color.black_font));
                    results.setTextColor(getResources().getColor(R.color.white));
                }else{
                    upcomingMatches.setBackgroundColor(getResources().getColor(R.color.black_font));
                    upcomingMatches.setTextColor(getResources().getColor(R.color.white));
                    results.setBackgroundColor(getResources().getColor(R.color.white));
                    results.setTextColor(getResources().getColor(R.color.black_font));
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.ECLAIR)
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back:
                onBackPressed();
                break;
            case R.id.match_results:
                upcomingMatches.setBackgroundColor(getResources().getColor(R.color.white));
                upcomingMatches.setTextColor(getResources().getColor(R.color.black_font));
                results.setBackgroundColor(getResources().getColor(R.color.black_font));
                results.setTextColor(getResources().getColor(R.color.white));
                pager.setCurrentItem(1);
                break;

            case R.id.upcoming_matches:
                upcomingMatches.setBackgroundColor(getResources().getColor(R.color.black_font));
                upcomingMatches.setTextColor(getResources().getColor(R.color.white));
                results.setBackgroundColor(getResources().getColor(R.color.white));
                results.setTextColor(getResources().getColor(R.color.black_font));
                pager.setCurrentItem(0);
                break;
            case R.id.myBid:
                startActivity(new Intent(this,MyBid.class));
                break;
        }
    }


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
        TextView heading= (TextView) dialogg.findViewById(R.id.player1Name);
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
        heading.setText("Select the team you want to bid");
        placeBid.setTypeface(controller.getDetailsFont());
        player1Name.setText(model.getPlayer1Name());
        player2Name.setText(model.getPlayer2Name());
        if (model.getPlayer2ImageUrl().length() > 0) {
            Picasso.with(IplMatches.this).load(model.getPlayer2ImageUrl().replace("http://www.dmss.co.in/sportsfight/docs/images/profile/","")).placeholder(R.drawable.user_icon).into(player2);
        } else {
            player2.setImageResource(R.drawable.user_icon);
        }
        if (model.getPlayer1ImageUrl().length() > 0) {
            Picasso.with(IplMatches.this).load(model.getPlayer1ImageUrl().replace("http://www.dmss.co.in/sportsfight/docs/images/profile/","")).placeholder(R.drawable.user_icon).into(player1);
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
                    Util.showToast(IplMatches.this, "You do not have " +value + " points in your wallet.Please add more points or decrease bid");
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
                        Util.showToast(IplMatches.this, "Your Bid should be greater then previously made bid.");

                    }
                }else {
                    if (player1BidPoints != 0) {
                        player1BidPoints = player1BidPoints - 100;
                    } else {
                        Util.showToast(IplMatches.this, "Your bid is already 0");
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
                    Util.showToast(IplMatches.this, "You do not have " + neededPoints + " points in your wallet.Please add more points or decrease bid");
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
                        Util.showToast(IplMatches.this, "Your Bid should be greater then previously made bid.");

                    }
                } else {
                    if (player2BidPoints != 0) {
                        player2BidPoints = player2BidPoints - 100;
                    } else {
                        Util.showToast(IplMatches.this, "Your bid is already 0");
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
                    Util.showToast(IplMatches.this,"You can not place bid on this team,as You have already placed bid on"+model.getPlayer2Name()+".");
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
                    Util.showToast(IplMatches.this,"You can not place bid on this team,as You have already placed bid on "+model.getPlayer1Name()+".");
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
                    Util.showToast(IplMatches.this, "Please select team.");
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
                     Util.showToast(IplMatches.this, "Please enter your bid.");
                    }
                }
            }
        });
        dialogg.show();
    }

    @Override
    public void onPlaceBidClick(final MatchesModel model) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {

                placeBidAlert(model);
            }
        });

    }

    public void refreshScreen() {
        if (Util.isNetworkAvailable(IplMatches.this)) {
            apiCall = refreshScreen;
            dialog = Util.showPogress(IplMatches.this);
            controller.getApiCall().getData(Common.getIPLMatchesList(controller.getProfile().getUserId()),controller.getPrefManager().getUserToken(), this);
        }
    }
    public void placeBid(MatchesModel model, int points) {
        if (Util.isNetworkAvailable(IplMatches.this)) {
            apiCall=placeBid;
            dialog = Util.showPogress(IplMatches.this);
            if(model.getMyBidToId()!=0)
            {
                controller.getApiCall().postData(Common.getUpdateBidUrl, getUpdateBidJSON(model, points).toString(),controller.getPrefManager().getUserToken(), this);
            }else {
                controller.getApiCall().postData(Common.getPlaceBidUrl, getAddBidJSON(model, points).toString(),controller.getPrefManager().getUserToken(), this);
            }
        }
    }
    public JSONObject getAddBidJSON(MatchesModel model, int points) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("ChallengeId", model.getChallengeId());
            jsonObject.put("BidBy", controller.getProfile().getUserId());
            jsonObject.put("BidOn", selectedId);
            jsonObject.put("BidPoints", points);
            jsonObject.put("DeviceId", Util.getDeviceID(IplMatches.this));
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
            jsonObject.put("DeviceId", Util.getDeviceID(IplMatches.this));
        } catch (Exception ex) {
            ex.fillInStackTrace();
        }
        return jsonObject;
    }

    @Override
    public void onSucess(final String value) {
        if (Util.getStatus(value) == true) {
            if (apiCall == placeBid) {
                if (dialog != null) {
                    dialog.cancel();
                }
                if (dialogg != null) {
                    dialogg.cancel();
                }

                Util.showToast(IplMatches.this, Util.getMessage(value));
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        refreshScreen();
                    }
                });
            } else if (apiCall == refreshScreen) {
                jsonParsing(value);
            }else if (apiCall == getHistory) {
                matchHistory.clear();
                try {
                    JSONObject jsonObject=new JSONObject(value);
                    JSONArray jsonArray=jsonObject.getJSONArray("Result");
                    for(int i=0;i<jsonArray.length();i++)
                    {
                        IPLResultModel model=new IPLResultModel(jsonArray.getJSONObject(i));
                        matchHistory.add(model);
                    }
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            adapter=new IPLPagerAdapter(IplMatches.this, Dashboard.upComingEventMatches, matchHistory);
                            pager.setAdapter(adapter);
                            if (dialog != null) {
                                dialog.cancel();
                            }

                        }
                    });

                } catch (Exception ex) {
                    ex.fillInStackTrace();
                    if (dialog != null) {
                        dialog.cancel();
                    }

                }

            }
        } else {
            if (dialog != null) {
                dialog.cancel();
            }
            Util.showToast(IplMatches.this, Util.getMessage(value));
        }

    }

    @Override
    public void onError(String value) {
        if (dialog != null) {
            dialog.cancel();
        }
        if (Util.isSessionExpired(value)) {
            controller.logout();
            Util.Logout(IplMatches.this);
        }
        Util.showToast(IplMatches.this, Util.getMessage(value));
    }
    public void jsonParsing(String s)
    {
        try{
         JSONObject jsonObject=new JSONObject(s);
            JSONObject job=jsonObject.getJSONObject("Result");
         JSONArray jsonArray=job.getJSONArray("EventMatches");
         Dashboard.upComingEventMatches.clear();
         for(int i=0;i<jsonArray.length();i++)
         {
             MatchesModel model=new MatchesModel(jsonArray.getJSONObject(i));
             Dashboard.upComingEventMatches.add(model);
         }
         runOnUiThread(new Runnable() {
             @Override
             public void run() {
                 adapter.notifyDataSetChanged();
             }
         });

        }catch (Exception ex)
        {
            ex.fillInStackTrace();
        }
        if (dialog != null) {
            dialog.cancel();
        }
    }

}
