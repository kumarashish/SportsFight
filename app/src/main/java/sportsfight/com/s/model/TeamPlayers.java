package sportsfight.com.s.model;

import org.json.JSONObject;

/**
 * Created by ashish.kumar on 05-10-2018.
 */

public  class TeamPlayers {
    int RegistraionId;
    int GameId;
    String Name;
    String CompanyName;
    boolean isCaptain;
    int Rank;
    double WinPercentage;
    String GameLevel;
    String Location;
    String PlayerImage;
    public TeamPlayers(JSONObject jsonObject)
    {
          try{
              RegistraionId = jsonObject.isNull("RegistraionId") ? 0 : jsonObject.getInt("RegistraionId");
              GameId = jsonObject.isNull("GameId") ? 0 : jsonObject.getInt("GameId");
              Name = jsonObject.isNull("Name") ? "" : jsonObject.getString("Name");
              PlayerImage=jsonObject.isNull("PlayerImage") ? "" : jsonObject.getString("PlayerImage");
              CompanyName = jsonObject.isNull("CompanyName") ? "" : jsonObject.getString("CompanyName");
              isCaptain = jsonObject.isNull("IsCaptain") ? false : jsonObject.getBoolean("IsCaptain");
              Rank = jsonObject.isNull("Rank") ? 0 : jsonObject.getInt("Rank");
              WinPercentage = jsonObject.isNull("WinPercentage") ? 0 : jsonObject.getDouble("WinPercentage");
              GameLevel = jsonObject.isNull("GameLevel") ? "" : jsonObject.getString("GameLevel");
              Location = jsonObject.isNull("Location") ? "" : jsonObject.getString("Location");
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

    public int getRank() {
        return Rank;
    }

    public String getName() {
        return Name;
    }

    public String getLocation() {
        return Location;
    }

    public int getGameId() {
        return GameId;
    }

    public double getWinPercentage() {
        return WinPercentage;
    }

    public int getRegistraionId() {
        return RegistraionId;
    }

    public boolean isCaptain() {
        return isCaptain;
    }
    public String getPlayerImage() {
        return PlayerImage;
    }
}
