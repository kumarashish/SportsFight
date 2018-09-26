package sportsfight.com.s.model;

import org.json.JSONObject;

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

    public AvailableTeamModel(JSONObject team) {
        try {
            TeamName = team.isNull("TeamName") ? "" : team.getString("TeamName");
            TeamImage = team.isNull("TeamImage") ? "" : team.getString("TeamImage");
            TeamId = team.isNull("TeamId") ? "" : team.getString("TeamId");
            PlayerLocation = team.isNull("PlayerLocation") ? "" : team.getString("PlayerLocation");
            Distance = team.isNull("Distance") ? "" : team.getString("Distance");
            Rank = team.isNull("Rank") ? "" : team.getString("Rank");
            WinningPercentage = team.isNull("WinningPercentage") ? "" : team.getString("WinningPercentage");
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
}
