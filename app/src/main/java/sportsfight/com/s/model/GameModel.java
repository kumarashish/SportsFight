package sportsfight.com.s.model;

import org.json.JSONObject;

/**
 * Created by Ashish.Kumar on 31-01-2018.
 */

  public class GameModel {
    int gameId;
    String gameName;
    String gameImage;
    String gameDescription;
int minPoints;
int maxPoints;
    public GameModel(JSONObject jsonObject) {
        try {
            this.gameId = jsonObject.isNull("Id") ? -1 : jsonObject.getInt("Id");
            this.gameName = jsonObject.isNull("Name") ? "" : jsonObject.getString("Name");
            this.gameImage = jsonObject.isNull("image") ? "" : jsonObject.getString("image");
            this.gameDescription = jsonObject.isNull("Description") ? "" : jsonObject.getString("Description");
            this.minPoints=jsonObject.isNull("MinimumPoints") ? 0 : jsonObject.getInt("MinimumPoints");
            this.maxPoints=jsonObject.isNull("MaximumPoints")? 0 : jsonObject.getInt("MaximumPoints");
        } catch (Exception ex) {
            ex.fillInStackTrace();
        }
    }

    public int getMaxPoints() {
        return maxPoints;
    }

    public int getMinPoints() {
        return minPoints;
    }

    public String getGameDescription() {
        return gameDescription;
    }

    public int getGameId() {
        return gameId;
    }

    public String getGameImage() {
        return gameImage;
    }

    public String getGameName() {
        return gameName;
    }
}
