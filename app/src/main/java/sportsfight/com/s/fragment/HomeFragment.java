package sportsfight.com.s.fragment;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.ArrayList;

import sportsfight.com.s.R;
import sportsfight.com.s.common.AppController;
import sportsfight.com.s.common.Common;
import sportsfight.com.s.common.StaticJson;
import sportsfight.com.s.interfaces.WebApiResponseCallback;
import sportsfight.com.s.mainmodule.Dashboard;
import sportsfight.com.s.mainmodule.TeamDetails;
import sportsfight.com.s.model.AvailableGrounds;
import sportsfight.com.s.model.AvailablePlayersModel;
import sportsfight.com.s.model.AvailableTeamModel;
import sportsfight.com.s.model.GameModel;
import sportsfight.com.s.model.GameModelNew;
import sportsfight.com.s.model.ViewModel;
import sportsfight.com.s.util.Util;

public class HomeFragment  extends Fragment implements WebApiResponseCallback{
   ArrayList<GameModelNew> gameList=new ArrayList<>();
   LinearLayout horizontalView,verticalView;
   ArrayList<ViewModel> addedView=new ArrayList<>();
   AppController controller;
   Dialog dialog;
    int pos=0;
    int apiCall=-1;
    final int getCompleteData=1,updateGameList=2;
    WebApiResponseCallback callback;
    public static int selectedRange=5;
    public static int selectedId=-1;
    int selectedGameId=-1;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        addedView.clear();
        callback=this;
        View view = inflater.inflate(R.layout.home_page, container, false);
        controller=(AppController)getActivity().getApplicationContext();
        horizontalView=(LinearLayout)view.findViewById(R.id.horizontalView);
        verticalView=(LinearLayout)view.findViewById(R.id.verticalView);
       // prepareData();
        if (Util.isNetworkAvailable(getActivity())) {
            apiCall = getCompleteData;
            dialog = Util.showPogress(getActivity());
            controller.getApiCall().getData(Common.getNewDashBoard(Integer.toString(controller.getProfile().getUserId()), "0", "17.441660", "78.386940", Integer.toString(selectedRange)), controller.getPrefManager().getUserToken(), this);
        }
        return view;
    }



public void updateList(int position)
 {   View playerView = getActivity().getLayoutInflater().inflate(R.layout.available_players, null, false);
     View availableGroundView = getActivity().getLayoutInflater().inflate(R.layout.available_players, null, false);
   //  View openTournaments = getActivity().getLayoutInflater().inflate(R.layout.tournament_nearyou, null, false);
     LinearLayout suggestedGounds=(LinearLayout)  availableGroundView.findViewById(R.id.playerList);
     TextView playerCount = (TextView) playerView.findViewById(R.id.available_players);
     LinearLayout playerList=(LinearLayout)  playerView.findViewById(R.id.playerList);
     TextView heading = (TextView) playerView.findViewById(R.id.playerListHeading);
     TextView viewAll = (TextView) playerView.findViewById(R.id.viewall);
     TextView viewAllGrounds = (TextView) availableGroundView.findViewById(R.id.viewall);

     int availablePlayer = 0;
     if (gameList.get( position).getMinPlayers() > 1) {
         selectedGameId=(gameList.get( position).getGameId());
         availablePlayer = gameList.get( position).getTeamList().size();

         heading.setText("Available Teams ");
         int loop=3;
         if (gameList.get(position).getTeamList().size() < 3) {
             loop = gameList.get(position).getTeamList().size();
         }
         for (int i = 0; i < loop; i++) {
             AvailableTeamModel model=gameList.get(position).getTeamList().get(i);
             View playerRow = getActivity().getLayoutInflater().inflate(R.layout.available_player_row, null, false);
             TextView winning_percentage=(TextView)playerRow.findViewById(R.id.winning_percentage);
             ImageView icon=(ImageView) playerRow.findViewById(R.id.icon);
             Button rank=(Button)playerRow.findViewById(R.id.rank);
             TextView name = (TextView) playerRow.findViewById(R.id.name);
             TextView company = (TextView) playerRow.findViewById(R.id.company);
             TextView location = (TextView) playerRow.findViewById(R.id.location);
             TextView level = (TextView) playerRow.findViewById(R.id.level);
             Button challenge=(Button) playerRow.findViewById(R.id.challenge);
             View details=(View)playerRow.findViewById(R.id.player_details);
             Picasso.with(getActivity()).load( model.getTeamImage()).placeholder(R.drawable.logo_s).resize(60,60).into(icon);
             name.setText(model.getTeamName());
             company.setVisibility(View.GONE);
             location.setText(model.getPlayerLocation()+"("+model.getDistance()+" Km )");
             level.setText("");
             rank.setText(model.getRank());
             winning_percentage.setText(""+model.getWinningPercentage() +" %");
             challenge.setOnClickListener(new View.OnClickListener() {
                 @Override
                 public void onClick(View view) {

                 }
             });
             details.setOnClickListener(new View.OnClickListener() {
                 @Override
                 public void onClick(View view) {
                      startActivity(new Intent(getActivity(),TeamDetails.class));
                 }
             });
             playerList.addView(playerRow);
         }
     } else {
         availablePlayer = gameList.get( position).getPlayerlist().size();
         heading.setText("Available Players ");
         int loop=3;
         if ( availablePlayer < 3) {
             loop =  availablePlayer;
         }
         for (int i = 0; i < loop; i++) {
             AvailablePlayersModel model=gameList.get(position).getPlayerlist().get(i);
             View playerRow = getActivity().getLayoutInflater().inflate(R.layout.available_player_row, null, false);
             TextView winning_percentage=(TextView)playerRow.findViewById(R.id.winning_percentage);
             ImageView icon=(ImageView) playerRow.findViewById(R.id.icon);
             Button rank=(Button)playerRow.findViewById(R.id.rank);
             TextView name = (TextView) playerRow.findViewById(R.id.name);
             TextView company = (TextView) playerRow.findViewById(R.id.company);
             TextView location = (TextView) playerRow.findViewById(R.id.location);
             TextView level = (TextView) playerRow.findViewById(R.id.level);
             Button challenge=(Button) playerRow.findViewById(R.id.challenge);
             View details=(View)playerRow.findViewById(R.id.player_details);
             if( model.getPlayerImage().length()>0) {
                 Picasso.with(getActivity()).load(model.getPlayerImage()).placeholder(R.drawable.logo_s).resize(60, 60).into(icon);
             }else{
                 Picasso.with(getActivity()).load("https://treefurniturerental.ca/wp-content/uploads/2017/05/sorry-image-not-available.jpg").placeholder(R.drawable.logo_s).resize(60, 60).into(icon);
             }
             name.setText(model.getPlayerName());
             company.setText(model.getCompanyName());
             location.setText(model.getPlayerLocation()+"("+model.getDistance()+" Km )");
             level.setText(model.getGameLevel());
             rank.setText(model.getRank());
             winning_percentage.setText(""+model.getWinningPercentage() +" %");
             playerList.addView(playerRow);
         }
     }
     viewAll.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View view) {
             Toast.makeText(getActivity(),"Under Development",Toast.LENGTH_SHORT).show();
         }
     });
     TextView groundCount = (TextView) availableGroundView.findViewById(R.id.available_players);
     TextView groundheading = (TextView) availableGroundView.findViewById(R.id.playerListHeading);
     TextView groundviewAll = (TextView) availableGroundView.findViewById(R.id.viewall);
     int availableGround = gameList.get( position).getGroundlist().size();


     if (availablePlayer > 2) {
         viewAll.setText("View All");
     } else {
         viewAll.setText("");
     }

     playerCount.setText("(" + availablePlayer + ")");

     if (availableGround > 2) {
         groundviewAll.setText("View All");
     } else {
         groundviewAll.setText("");
     }
     if(availableGround>0)
     { int loop=3;
         if (availableGround < 3) {
             loop = availableGround;
         }
         for (int i = 0; i < loop; i++) {
             AvailableGrounds groundModel= gameList.get( position).getGroundlist().get(i);
             View groundsRow = getActivity().getLayoutInflater().inflate(R.layout.suggested_ground_row, null, false);

             TextView ground_name=(TextView) groundsRow.findViewById(R.id.ground_name);
             TextView price = (TextView) groundsRow.findViewById(R.id.price);
             TextView location = (TextView) groundsRow.findViewById(R.id.location);
             ImageView image1 = (ImageView) groundsRow.findViewById(R.id.image1);
             ImageView image2 = (ImageView) groundsRow.findViewById(R.id.image2);
             ImageView image3 = (ImageView) groundsRow.findViewById(R.id.image3);
             ImageView image4 = (ImageView) groundsRow.findViewById(R.id.image4);
             ImageView image5 = (ImageView) groundsRow.findViewById(R.id.image5);
             ground_name.setText(groundModel.getGroundName());
             location.setText(groundModel.getLocation()+", ("+groundModel.getDistance()+ "km)");
             price.setText("Rs "+groundModel.getPrice());
             if( groundModel.getGallery().size()==0)
             {   Picasso.with(getActivity()).load("https://treefurniturerental.ca/wp-content/uploads/2017/05/sorry-image-not-available.jpg").placeholder(R.drawable.logo).resize(100,100).into(image1);
                 image2.setVisibility(View.INVISIBLE);
                 image3.setVisibility(View.INVISIBLE);
                 image4.setVisibility(View.INVISIBLE);
                 image5.setVisibility(View.INVISIBLE);
             }else {
                 switch (groundModel.getGallery().size())
                 {
                     case 1:
                         Picasso.with(getActivity()).load(groundModel.getGallery().get(0)).placeholder(R.drawable.logo).resize(100,100).into(image1);
                         image2.setVisibility(View.INVISIBLE);
                         image3.setVisibility(View.INVISIBLE);
                         image4.setVisibility(View.INVISIBLE);
                         image5.setVisibility(View.INVISIBLE);
                         break;
                     case 2:
                         Picasso.with(getActivity()).load(groundModel.getGallery().get(0)).placeholder(R.drawable.logo).into(image1);
                         Picasso.with(getActivity()).load(groundModel.getGallery().get(1)).placeholder(R.drawable.logo).into(image2);
                         image3.setVisibility(View.INVISIBLE);
                         image4.setVisibility(View.INVISIBLE);
                         image5.setVisibility(View.INVISIBLE);
                         break;
                     case 3:
                         Picasso.with(getActivity()).load(groundModel.getGallery().get(0)).placeholder(R.drawable.logo).into(image1);
                         Picasso.with(getActivity()).load(groundModel.getGallery().get(1)).placeholder(R.drawable.logo).into(image2);
                         Picasso.with(getActivity()).load(groundModel.getGallery().get(2)).placeholder(R.drawable.logo).into(image3);
                         image4.setVisibility(View.INVISIBLE);
                         image5.setVisibility(View.INVISIBLE);
                         break;
                     case 4:
                         Picasso.with(getActivity()).load(groundModel.getGallery().get(0)).placeholder(R.drawable.logo).into(image1);
                         Picasso.with(getActivity()).load(groundModel.getGallery().get(1)).placeholder(R.drawable.logo).into(image2);
                         Picasso.with(getActivity()).load(groundModel.getGallery().get(2)).placeholder(R.drawable.logo).into(image3);
                         Picasso.with(getActivity()).load(groundModel.getGallery().get(3)).placeholder(R.drawable.logo).into(image4);
                         image5.setVisibility(View.INVISIBLE);
                         break;
                     case 5:
                         Picasso.with(getActivity()).load(groundModel.getGallery().get(0)).placeholder(R.drawable.logo).into(image1);
                         Picasso.with(getActivity()).load(groundModel.getGallery().get(1)).placeholder(R.drawable.logo).into(image2);
                         Picasso.with(getActivity()).load(groundModel.getGallery().get(2)).placeholder(R.drawable.logo).into(image3);
                         Picasso.with(getActivity()).load(groundModel.getGallery().get(3)).placeholder(R.drawable.logo).into(image4);
                         Picasso.with(getActivity()).load(groundModel.getGallery().get(4)).placeholder(R.drawable.logo).into(image5);
                         break;
                 }
                 image1.setOnClickListener(new View.OnClickListener() {
                     @Override
                     public void onClick(View view) {

                     }
                 });
                 image2.setOnClickListener(new View.OnClickListener() {
                     @Override
                     public void onClick(View view) {

                     }
                 });
                 image3.setOnClickListener(new View.OnClickListener() {
                     @Override
                     public void onClick(View view) {

                     }
                 });
                 image4.setOnClickListener(new View.OnClickListener() {
                     @Override
                     public void onClick(View view) {

                     }
                 });
                 image5.setOnClickListener(new View.OnClickListener() {
                     @Override
                     public void onClick(View view) {

                     }
                 });
             }
             suggestedGounds.addView(groundsRow);
         }
     }
     viewAllGrounds.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View view) {
             Toast.makeText(getActivity(),"Under Development",Toast.LENGTH_SHORT).show();
         }
     });
     groundheading.setText("Suggested Grounds ");
     groundCount.setText("(" + availableGround + ")");
     verticalView.removeAllViews();
     verticalView.addView(playerView);
     verticalView.addView(availableGroundView);
     verticalView.setVisibility(View.VISIBLE);
     if (dialog != null) {
         dialog.cancel();
     }
 }

    public void UpdateHeader(int pos) {
        for (int i = 0; i < addedView.size(); i++) {
            if (i != pos) {
                View view = addedView.get(i).getView();
                LinearLayout gameBg = (LinearLayout) view.findViewById(R.id.gamebg);
                gameBg.setBackgroundColor(getActivity().getResources().getColor(R.color.white));
                TextView text = (TextView) view.findViewById(R.id.game_name);
                text.setTextColor(getActivity().getResources().getColor(R.color.disabled_color));
            } else {
                View view = addedView.get(i).getView();
                LinearLayout gameBg = (LinearLayout) view.findViewById(R.id.gamebg);
                gameBg.setBackground(getActivity().getResources().getDrawable(R.drawable.selected_game));
                TextView text = (TextView) view.findViewById(R.id.game_name);
                text.setTextColor(getActivity().getResources().getColor(R.color.white));
            }
        }
        verticalView.removeAllViews();
        verticalView.setVisibility(View.GONE);
    }

    public void addHeader() {
        for (int i = 0; i < gameList.size(); i++) {
            View inflatedLayout = getActivity().getLayoutInflater().inflate(R.layout.gameview, null, false);
            final LinearLayout gameBg = (LinearLayout) inflatedLayout.findViewById(R.id.gamebg);
            ImageView gameIcon = (ImageView) inflatedLayout.findViewById(R.id.game_icon);
            TextView gameName = (TextView) inflatedLayout.findViewById(R.id.game_name);
            final View clickedView = (View) inflatedLayout.findViewById(R.id.view);
            Picasso.with(getActivity()).load(gameList.get(i).getGameEnabledIcon()).placeholder(R.drawable.logo).resize(30,30).into(gameIcon);
            if (i != 0) {
                gameBg.setBackgroundColor(getActivity().getResources().getColor(R.color.white));
                gameName.setText(gameList.get(i).getGameName().toUpperCase());
                gameName.setTextColor(getActivity().getResources().getColor(R.color.disabled_color));
            }
            inflatedLayout.setId(i);
            clickedView.setId(i);
            addedView.add(new ViewModel(inflatedLayout, i, clickedView.getId()));
            horizontalView.addView(inflatedLayout);
            clickedView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog = Util.showPogress(getActivity());
                    int pos = position(clickedView.getId());
                    selectedGameId=gameList.get(pos).getGameId();
                    boolean isApiTobeCalled=false;
                    if (gameList.get(pos).getMinPlayers() > 1) {
                        if (gameList.get(pos).getTeamList().size() == 0) {
                            isApiTobeCalled = true;
                        }
                    } else {
                        if (gameList.get(pos).getPlayerlist().size() == 0) {
                            isApiTobeCalled = true;
                        }
                    }
                    UpdateHeader(pos);
                    if (isApiTobeCalled) {
                        apiCall = updateGameList;

                        controller.getApiCall().getData(Common.getNewDashBoard(Integer.toString(controller.getProfile().getUserId()), Integer.toString(selectedGameId), "17.441660", "78.386940", "5"), controller.getPrefManager().getUserToken(), callback);
                    }else{

                        updateList(pos);
                    }
                }
            });
        }
    }

    public int position(int id) {
        int value = -1;
        for (int i = 0; i < addedView.size(); i++) {
            if (addedView.get(i).getButtonId() == id) {
                value = addedView.get(i).getPos();
                break;
            }
        }
        return value;
    }

    @Override
    public void onSucess(String value) {

        if (Util.getStatus(value) == true) {
            JSONArray jsonArray = Util.getResultJsonArray(value);
            switch (apiCall) {
                case getCompleteData:
                    gameList.clear();
                    for (int i = 0; i < jsonArray.length(); i++) {
                        try {
                            JSONObject gameobject = jsonArray.getJSONObject(i);
                            gameList.add(new GameModelNew(gameobject));
                        } catch (Exception ex) {
                            ex.fillInStackTrace();
                        }
                    }
                    if (gameList.size() > 0) {
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                addHeader();
                                updateList(pos);
                            }
                        });

                    }
                    break;
                case updateGameList:
                    try {
                        JSONObject gameobject = jsonArray.getJSONObject(0);
                        GameModelNew modelNew = new GameModelNew(gameobject);

                        for (int i = 0; i < gameList.size(); i++) {

                            if (gameList.get(i).getGameId() == modelNew.getGameId()) {

                                gameList.get(i).updateList(modelNew);
                                final int pos = i;
                                getActivity().runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        updateList(pos);
                                    }
                                });

                                break;

                            }
                        }

                    } catch (Exception ex) {
                        ex.fillInStackTrace();
                    }
                    break;
            }

        } else {
            Util.showToast(getActivity(), Util.getMessage(value));
        }

    }

    @Override
    public void onError(String value) {

        if (Util.isSessionExpired(value)) {
        controller.logout();
        Util.Logout(getActivity());
    }
        if(dialog!=null)
        {
            dialog.cancel();
        }
       Util.showToast(getActivity(),Util.getMessage(value));
    }
}

