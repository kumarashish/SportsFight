package sportsfight.com.s.model;

import org.json.JSONObject;

/**
 * Created by Ashish.Kumar on 31-01-2018.
 */

public class InterestedGameModel {
    int gameId;
    String gameName;
    String GameLevelId;
    String GameLevel;
    int Rank;
    boolean GameIsActive;
    String EnabledIcon;
   String DisabledIcon;
    public InterestedGameModel(String gameName,String id)
    {
        this.gameId=Integer.parseInt(id);
        this.gameName=gameName;

    }
public InterestedGameModel(JSONObject jsonObject)
{
    try{
        this.gameId=jsonObject.getInt("GameId");
        this.gameName=jsonObject.getString("Name");
        this.GameLevelId=jsonObject.isNull("GameLevelId")?"":jsonObject.getString("GameLevelId");
        this.GameLevel=jsonObject.isNull("GameLevel")?"":jsonObject.getString("GameLevel");
        this.Rank=jsonObject.isNull("Rank")?0:jsonObject.getInt("Rank");
        this.GameIsActive=jsonObject.isNull("GameIsActive")?false:jsonObject.getBoolean("GameIsActive");
        this.EnabledIcon=jsonObject.isNull("EnabledIcon")?"":jsonObject.getString("EnabledIcon");
        this.DisabledIcon=jsonObject.isNull("DisabledIcon")?"":jsonObject.getString("DisabledIcon");
    }catch (Exception ex)
    {
        ex.fillInStackTrace();
    }
}

    public boolean isGameIsActive() {
        return GameIsActive;
    }

    public int getRank() {
        return Rank;
    }

    public String getDisabledIcon() {
        return DisabledIcon;
    }

    public String getEnabledIcon() {
        return EnabledIcon;
    }

    public String getGameLevel() {
        return GameLevel;
    }

    public String getGameLevelId() {
        return GameLevelId;
    }


    public int getGameId() {
        return gameId;
    }

    public String getGameName() {
        return gameName;
    }
}
