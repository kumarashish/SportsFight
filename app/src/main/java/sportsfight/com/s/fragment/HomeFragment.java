package sportsfight.com.s.fragment;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


import org.json.JSONArray;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.ArrayList;

import sportsfight.com.s.R;
import sportsfight.com.s.common.Common;
import sportsfight.com.s.common.StaticJson;
import sportsfight.com.s.mainmodule.TeamDetails;
import sportsfight.com.s.model.GameModelNew;
import sportsfight.com.s.model.ViewModel;

public class HomeFragment  extends Fragment {
   ArrayList<GameModelNew> gameList=new ArrayList<>();
   LinearLayout horizontalView,verticalView;

 ArrayList<ViewModel> addedView=new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        addedView.clear();
        View view = inflater.inflate(R.layout.home_page, container, false);
        horizontalView=(LinearLayout)view.findViewById(R.id.horizontalView);
        verticalView=(LinearLayout)view.findViewById(R.id.verticalView);
        prepareData();
        return view;
    }


    public void prepareData()
    {try{
            JSONObject jsonObject=new JSONObject(StaticJson.json);
            JSONArray data=jsonObject.getJSONArray("GameDetails");
            for(int i=0;i<data.length();i++)
            {
               GameModelNew model=new GameModelNew(data.getJSONObject(i)) ;
               gameList.add(model);
            }
        }catch (Exception ex)
        {
            ex.fillInStackTrace();
        }
        addHeader();
        updateList(0);
    }
public void updateList(int position)
 {   View playerView = getActivity().getLayoutInflater().inflate(R.layout.available_players, null, false);
     View availableGroundView = getActivity().getLayoutInflater().inflate(R.layout.available_players, null, false);
     View openTournaments = getActivity().getLayoutInflater().inflate(R.layout.tournament_nearyou, null, false);
     LinearLayout suggestedGounds=(LinearLayout)  availableGroundView.findViewById(R.id.playerList);
     TextView playerCount = (TextView) playerView.findViewById(R.id.available_players);
     LinearLayout playerList=(LinearLayout)  playerView.findViewById(R.id.playerList);
     TextView heading = (TextView) playerView.findViewById(R.id.playerListHeading);
     TextView viewAll = (TextView) playerView.findViewById(R.id.viewall);
     TextView viewAllGrounds = (TextView) availableGroundView.findViewById(R.id.viewall);
     int availablePlayer = 0;
     if (gameList.get( position).getMinPlayers() > 1) {
         availablePlayer = gameList.get( position).getTeamList().size();
         heading.setText("Available Teams ");
         for (int i = 0; i < gameList.get(position).getTeamList().size(); i++) {
             View playerRow = getActivity().getLayoutInflater().inflate(R.layout.available_player_row, null, false);
             Button challenge=(Button) playerRow.findViewById(R.id.challenge);
             View details=(View)playerRow.findViewById(R.id.player_details);
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
         for (int i = 0; i < gameList.get(position).getPlayerlist().size(); i++) {
             View playerRow = getActivity().getLayoutInflater().inflate(R.layout.available_player_row, null, false);
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
     {
         for (int i = 0; i < availableGround; i++) {
             View groundsRow = getActivity().getLayoutInflater().inflate(R.layout.suggested_ground_row, null, false);
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
     verticalView.addView(openTournaments);
     verticalView.removeAllViews();
     verticalView.addView(playerView);
     verticalView.addView(availableGroundView);
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
    }

    public void addHeader() {
        for (int i = 0; i < gameList.size(); i++) {
            View inflatedLayout = getActivity().getLayoutInflater().inflate(R.layout.gameview, null, false);
            LinearLayout gameBg = (LinearLayout) inflatedLayout.findViewById(R.id.gamebg);
            ImageView gameIcon = (ImageView) inflatedLayout.findViewById(R.id.game_icon);
            TextView gameName = (TextView) inflatedLayout.findViewById(R.id.game_name);
            final View clickedView = (View) inflatedLayout.findViewById(R.id.view);
            if (i != 0) {
                gameBg.setBackgroundColor(getActivity().getResources().getColor(R.color.white));
                gameName.setText(gameList.get(i).getGameName());
                gameName.setTextColor(getActivity().getResources().getColor(R.color.disabled_color));
            }
            inflatedLayout.setId(i);
            clickedView.setId(i);
            addedView.add(new ViewModel(inflatedLayout, i, clickedView.getId()));
            horizontalView.addView(inflatedLayout);
            clickedView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int pos = position(clickedView.getId());
                    UpdateHeader(pos);
                    updateList(position(pos));
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
}

