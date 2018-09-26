package sportsfight.com.s.model;

import org.json.JSONObject;

import butterknife.BindView;

/**
 * Created by Ashish.Kumar on 23-04-2018.
 */

public class BidModel {
    int ChallengeId;
    int BidBy;
    int BidOn;
    String Player1Name;
    String Player1Image;
    String Player2Name;
    String Player2Image;
    int BidPoints;
    String MatchDate;
    String GameName;
    String DeviceId;
    int ResultAmount;
    int Id;
    public BidModel(JSONObject jsonObject) {
        try {
            ChallengeId = jsonObject.isNull("ChallengeId") ? -1 : jsonObject.getInt("ChallengeId");
            BidBy = jsonObject.isNull("BidBy") ? -1 : jsonObject.getInt("BidBy");
            BidOn = jsonObject.isNull("BidOn") ? -1 : jsonObject.getInt("BidOn");
            Player1Name = jsonObject.isNull("Player1Name") ? "" : jsonObject.getString("Player1Name");
            Player1Image = jsonObject.isNull("Player1Image") ? "" : jsonObject.getString("Player1Image");
            Player2Name = jsonObject.isNull("Player2Name") ? "" : jsonObject.getString("Player2Name");
            Player2Image = jsonObject.isNull("Player2Image") ? "" : jsonObject.getString("Player2Image");
            BidPoints = jsonObject.isNull("BidPoints") ? -1 : jsonObject.getInt("BidPoints");
            MatchDate = jsonObject.isNull("MatchDate") ? "" : jsonObject.getString("MatchDate");
            GameName = jsonObject.isNull("GameName") ? "" : jsonObject.getString("GameName");
            DeviceId = jsonObject.isNull("DeviceId") ? "" : jsonObject.getString("DeviceId");
            ResultAmount = jsonObject.isNull("ResultAmount") ? -1 : jsonObject.getInt("ResultAmount");
            Id = jsonObject.isNull("Id") ? -1 : jsonObject.getInt("Id");
        } catch (Exception ex) {
            ex.fillInStackTrace();
        }
    }

    public String getMatchDate() {
        return MatchDate;
    }

    public int getId() {
        return Id;
    }

    public int getChallengeId() {
        return ChallengeId;
    }

    public String getGameName() {
        return GameName;
    }

    public String getPlayer2Name() {
        return Player2Name;
    }

    public String getPlayer1Name() {
        return Player1Name;
    }

    public String getDeviceId() {
        return DeviceId;
    }

    public int getBidBy() {
        return BidBy;
    }

    public int getBidOn() {
        return BidOn;
    }

    public int getBidPoints() {
        return BidPoints;
    }

    public int getResultAmount() {
        return ResultAmount;
    }

    public String getPlayer1Image() {
        return Player1Image;
    }

    public String getPlayer2Image() {
        return Player2Image;
    }
}
