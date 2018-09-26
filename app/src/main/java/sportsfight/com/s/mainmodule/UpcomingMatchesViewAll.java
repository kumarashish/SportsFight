package sportsfight.com.s.mainmodule;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
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

import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import sportsfight.com.s.R;
import sportsfight.com.s.adapter.UpcomingMatchesAdapter;
import sportsfight.com.s.common.AppController;
import sportsfight.com.s.common.Common;
import sportsfight.com.s.interfaces.PlaceBidCallBack;
import sportsfight.com.s.interfaces.WebApiResponseCallback;
import sportsfight.com.s.model.MatchesModel;
import sportsfight.com.s.util.Util;

/**
 * Created by Ashish.Kumar on 26-02-2018.
 */

public class UpcomingMatchesViewAll extends Activity implements View.OnClickListener,PlaceBidCallBack,WebApiResponseCallback{
    @BindView(R.id.back)
    Button back;
    AppController controller;
    @BindView(R.id.heading)
    TextView heading;
    @BindView(R.id.listView)
    ListView listView;
   public static ArrayList<MatchesModel> list;
    int selectedId=-1;
    int player1BidPoints=0;
    int player2BidPoints=0;
    boolean isPlayer1Selected=false;
    boolean isPlayer2Selected=false;
    int apiCall=0;
    int getDashBoardApiCall=1,addBidAPiCall=2,getWallet=3;
    Dialog dialogg,dialog;
    MatchesModel matchmodel=null;
    UpcomingMatchesAdapter adapter=null;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list);
        controller = (AppController) getApplicationContext();
        ButterKnife.bind(this);
        heading.setText("Upcoming Matches");
        back.setOnClickListener(this);
        adapter=new UpcomingMatchesAdapter(list,UpcomingMatchesViewAll.this);
        listView.setAdapter(adapter);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.back:
                onBackPressed();
        }

    }

    @Override
    public void onPlaceBidClick(MatchesModel model) {
        matchmodel=model;
        placeBidAlert(model);
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
            Picasso.with(UpcomingMatchesViewAll.this).load(model.getPlayer2ImageUrl()).placeholder(R.drawable.user_icon).into(player2);
        } else {
            player2.setImageResource(R.drawable.user_icon);
        }
        if (model.getPlayer1ImageUrl().length() > 0) {
            Picasso.with(UpcomingMatchesViewAll.this).load(model.getPlayer1ImageUrl()).placeholder(R.drawable.user_icon).into(player1);
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
                if (controller.getProfile().getTotalPoints() >=  ((player1BidPoints + 100)- model.getMyBid())) {
                    player1BidPoints = player1BidPoints + 100;
                } else {
                    int neededPoints = (player1BidPoints + 100)- model.getMyBid();
                    Util.showToast(UpcomingMatchesViewAll.this, "You do not have " + neededPoints+ " points in your wallet.Please add more points or decrease bid");
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
                        Util.showToast(UpcomingMatchesViewAll.this, "Your Bid should be greater then previously made bid.");

                    }
                }else {
                    if (player1BidPoints != 0) {
                        player1BidPoints = player1BidPoints - 100;
                    } else {
                        Util.showToast(UpcomingMatchesViewAll.this, "Your bid is already 0");
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
                    int neededPoints = player2BidPoints + 100;
                    Util.showToast(UpcomingMatchesViewAll.this, "You do not have " + neededPoints + " points in your wallet.Please add more points or decrease bid");
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

                        Util.showToast(UpcomingMatchesViewAll.this, "Your Bid should be greater then previously made bid.");

                    }
                } else {
                    if (player2BidPoints != 0) {
                        player2BidPoints = player2BidPoints - 100;
                    } else {
                        Util.showToast(UpcomingMatchesViewAll.this, "Your bid is already 0");
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
                    Util.showToast(UpcomingMatchesViewAll.this,"You can not place bid on this player,as You have already placed bid on player2.");
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
                    Util.showToast(UpcomingMatchesViewAll.this,"You can not place bid on this player,as You have already placed bid on player1.");
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
                    Util.showToast(UpcomingMatchesViewAll.this, "Please select player.");
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
                        Util.showToast(UpcomingMatchesViewAll.this, "Please enter your bid.");
                    }
                }
            }
        });
        dialogg.show();
    }

    public void placeBid(MatchesModel model, int points) {
        if (Util.isNetworkAvailable(UpcomingMatchesViewAll.this)) {
            apiCall = addBidAPiCall;
            dialog = Util.showPogress(UpcomingMatchesViewAll.this);
            if(model.getMyBidToId()!=0)
            {
                controller.getApiCall().postData(Common.getUpdateBidUrl, getUpdateBidJSON(model, points).toString(),controller.getPrefManager().getUserToken(), UpcomingMatchesViewAll.this);
            }else {
                controller.getApiCall().postData(Common.getPlaceBidUrl, getAddBidJSON(model, points).toString(),controller.getPrefManager().getUserToken(), UpcomingMatchesViewAll.this);
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
            jsonObject.put("DeviceId", Util.getDeviceID(UpcomingMatchesViewAll.this));
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
            jsonObject.put("DeviceId", Util.getDeviceID(UpcomingMatchesViewAll.this));
        } catch (Exception ex) {
            ex.fillInStackTrace();
        }
        return jsonObject;
    }

    @Override
    public void onSucess(String value) {
        if (Util.getStatus(value) == true) {

                if (dialog != null) {
                    dialog.cancel();
                }
                if (dialogg != null) {
                    dialogg.cancel();
                }

                Util.showToast(UpcomingMatchesViewAll.this, Util.getMessage(value));
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        int points=0;
                        if (player1BidPoints > 0) {
                            points = player1BidPoints;
                            matchmodel.setMyBidToId(matchmodel.getPlayer1Id());
                            matchmodel.setTotalBids(player2BidPoints+points);
                        } else {
                            points = player2BidPoints;
                            matchmodel.setMyBidToId(matchmodel.getPlayer2Id());
                            matchmodel.setTotalBids(player1BidPoints+points);
                        }
                        matchmodel.setMyBid(points);
                        adapter.notifyDataSetChanged();


                    }
                });


        } else {
            if (dialog != null) {
                dialog.cancel();
            }
            Util.showToast(UpcomingMatchesViewAll.this, Util.getMessage(value));
        }


    }

    @Override
    public void onError(String value) {
        if(dialog!=null)
        {
            dialog.cancel();
        }
        if (Util.isSessionExpired(value)) {
            controller.logout();
            Util.Logout(UpcomingMatchesViewAll.this);
        }
        Util.showToast(UpcomingMatchesViewAll.this,Util.getMessage(value));

    }
}
