package sportsfight.com.s.model;

/**
 * Created by Ashish.Kumar on 31-01-2018.
 */

public class InterestedGameModel {
    int gameId;
    String gameName;
    public InterestedGameModel(String gameName,String id)
    {
        this.gameId=Integer.parseInt(id);
        this.gameName=gameName;
    }

    public int getGameId() {
        return gameId;
    }

    public String getGameName() {
        return gameName;
    }
}
