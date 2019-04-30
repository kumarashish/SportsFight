package sportsfight.com.s.model;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by ashish.kumar on 30-08-2018.
 */

public class GameModelNew {
    int GameId;
    String GameName;
    String GameEnabledIcon;
    String GameDisabledIcon;
    int MinPlayers;
    Double DistanceKM;


    ArrayList<OpenTournaments>tournamentlist=new ArrayList<>();
    ArrayList<AvailableGrounds>groundlist=new ArrayList<>();
    ArrayList<AvailablePlayersModel>playerlist=new ArrayList<>();
    ArrayList<AvailableTeamModel>teamList=new ArrayList<>();
    public GameModelNew(JSONObject jsonObject)
    {
        try{
             GameId=jsonObject.isNull("GameId")?0:jsonObject.getInt("GameId");
             GameName=jsonObject.isNull("GameName")?"":jsonObject.getString("GameName");
             GameEnabledIcon=jsonObject.isNull("GameEnabledIcon")?"":jsonObject.getString("GameEnabledIcon");
             GameDisabledIcon=jsonObject.isNull("GameDisabledIcon")?"":jsonObject.getString("GameDisabledIcon");
             MinPlayers=jsonObject.isNull("MinPlayers")?1:jsonObject.getInt("MinPlayers");
             DistanceKM=jsonObject.isNull("DistanceKM")?0.0:jsonObject.getDouble("DistanceKM");
            if (MinPlayers > 1) {
                JSONArray teams = jsonObject.getJSONArray("AvailableTeams");
                for (int i = 0; i < teams.length(); i++) {
                    JSONObject team = teams.getJSONObject(i);
                    teamList.add(new AvailableTeamModel(team));
                }
            } else {
                JSONArray players = jsonObject.getJSONArray("AvailablePlayers");
                for (int i = 0; i < players.length(); i++) {
                    JSONObject player = players.getJSONObject(i);
                    playerlist.add(new AvailablePlayersModel(player));
                }
            }
             JSONArray availableGround=jsonObject.getJSONArray("SuggestedPlaces");
             for(int i=0;i<availableGround.length();i++){
                 JSONObject availableGrounds = availableGround.getJSONObject(i);
                 groundlist.add(new AvailableGrounds(availableGrounds));
             }
            JSONArray openTournament=jsonObject.getJSONArray("OpenTournaments");
            for(int i=0;i<openTournament.length();i++){
                JSONObject openTournaments = openTournament.getJSONObject(i);
                tournamentlist.add(new OpenTournaments(openTournaments));
            }
        }catch (Exception ex)
        {
            ex.fillInStackTrace();
        }
    }

    public String getGameName() {
        return GameName;
    }

    public int getGameId() {
        return GameId;
    }

    public ArrayList<AvailableTeamModel> getTeamList() {
        return teamList;
    }

    public void updateList(GameModelNew modelNew) {
        playerlist.clear();
        teamList.clear();
        groundlist.clear();
        playerlist.addAll(modelNew.getPlayerlist());
        groundlist.addAll(modelNew.getGroundlist());
        teamList.addAll(modelNew.getTeamList());
    }
    public ArrayList<AvailablePlayersModel> getPlayerlist() {
        return playerlist;
    }

    public ArrayList<AvailableGrounds> getGroundlist() {
        return groundlist;
    }


    public ArrayList<OpenTournaments> getTornamentlist() {
        return tournamentlist;
    }

    public int getMinPlayers() {
        return MinPlayers;
    }

    public String getGameDisabledIcon() {
        return GameDisabledIcon;
    }

    public String getGameEnabledIcon() {
        return GameEnabledIcon;
    }
}

