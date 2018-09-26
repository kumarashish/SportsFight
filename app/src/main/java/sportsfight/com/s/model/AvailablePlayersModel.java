package sportsfight.com.s.model;

import org.json.JSONObject;

/**
 * Created by ashish.kumar on 30-08-2018.
 */

public class AvailablePlayersModel {
    String PlayerName;
    String PlayerImage;
    String PlayerId;
    String PlayerLocation;
    String Distance;
    String Rank;
    String WinningPercentage;

    public AvailablePlayersModel(JSONObject player) {
        try{
            PlayerName=player.isNull("PlayerName")?"":player.getString("PlayerName");
            PlayerImage=player.isNull("PlayerImage")?"":player.getString("PlayerImage");
            PlayerId=player.isNull("PlayerId")?"":player.getString("PlayerId");
            PlayerLocation=player.isNull("PlayerLocation")?"":player.getString("PlayerLocation");
            Distance=player.isNull("Distance")?"":player.getString("Distance");
            Rank=player.isNull("Rank")?"":player.getString("Rank");
            WinningPercentage=player.isNull("WinningPercentage")?"":player.getString("WinningPercentage");
        }catch (Exception ex)
        {
            ex.fillInStackTrace();
        }

    }

    public String getDistance() {
        return Distance;
    }

    public String getPlayerId() {
        return PlayerId;
    }

    public String getPlayerImage() {
        return PlayerImage;
    }

    public String getPlayerLocation() {
        return PlayerLocation;
    }

    public String getPlayerName() {
        return PlayerName;
    }

    public String getRank() {
        return Rank;
    }

    public String getWinningPercentage() {
        return WinningPercentage;
    }
}
