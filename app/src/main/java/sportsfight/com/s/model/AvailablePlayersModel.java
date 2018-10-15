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
    String GameLevel;
    String CompanyName;


    public AvailablePlayersModel(JSONObject player) {
        try{
            PlayerName=player.isNull("Name")?"":player.getString("Name");
            PlayerImage=player.isNull("PlayerImage")?"":player.getString("PlayerImage");
            PlayerId=player.isNull("PlayerId")?player.getString("RegistraionId"):player.getString("PlayerId");
            PlayerLocation=player.isNull("Location")?"":player.getString("Location");
            Distance=player.isNull("Distance")?"":player.getString("Distance");
            Rank=player.isNull("Rank")?"":player.getString("Rank");
            WinningPercentage=player.isNull("WinPercentage")?"":player.getString("WinPercentage");
            GameLevel=player.isNull("GameLevel")?"":player.getString("GameLevel");
            CompanyName=player.isNull("CompanyName")?"":player.getString("CompanyName");
        }catch (Exception ex)
        {
            ex.fillInStackTrace();
        }

    }

    public String getCompanyName() {
        return CompanyName;
    }

    public String getGameLevel() {
        return GameLevel;
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
