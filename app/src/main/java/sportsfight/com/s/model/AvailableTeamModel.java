package sportsfight.com.s.model;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by ashish.kumar on 30-08-2018.
 */

public class AvailableTeamModel {
    String TeamName;
    String TeamImage;
    String TeamId;
    String PlayerLocation;
    String Distance;
    String Rank;
    String WinningPercentage;
    ArrayList<TeamPlayers> playerList= new ArrayList<>();

    public AvailableTeamModel(JSONObject team) {
        try {
            TeamName = team.isNull("TeamName") ? "" : team.getString("TeamName");
            TeamImage = team.isNull("TeamImage") ? "" : team.getString("TeamImage");
            TeamId = team.isNull("TeamId") ? "" : team.getString("TeamId");
            PlayerLocation = team.isNull("Location") ? "" : team.getString("Location");
            Distance = team.isNull("Distance") ? "" : team.getString("Distance");
            Rank = team.isNull("Rank") ? "N/A" : team.getString("Rank");
            WinningPercentage = team.isNull("WinPercentage") ? "" : team.getString("WinPercentage");
            JSONArray jsonArray = team.isNull("PlayerList") ? null : team.getJSONArray("PlayerList");
            if (jsonArray != null) {
                for (int i = 0; i < jsonArray.length(); i++) {
                    playerList.add(new TeamPlayers(jsonArray.getJSONObject(i)));
                }
            }
        } catch (Exception ex) {
            ex.fillInStackTrace();
        }

    }

    public String getWinningPercentage() {
        return WinningPercentage;
    }

    public String getRank() {
        return Rank;
    }

    public String getPlayerLocation() {
        return PlayerLocation;
    }

    public String getDistance() {
        return Distance;
    }

    public String getTeamId() {
        return TeamId;
    }

    public String getTeamImage() {
        return TeamImage;
    }

    public String getTeamName() {
        return TeamName;
    }

    public ArrayList<TeamPlayers> getPlayerList() {
        return playerList;
    }
}
