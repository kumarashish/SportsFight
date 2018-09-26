package sportsfight.com.s.model;

import org.json.JSONObject;

/**
 * Created by Ashish.Kumar on 20-02-2018.
 */

public class ReminderModel {
    int ChallengeId;
    String GameName;
    int OpponentId;
    String OpponentName;
    String OpponentImageUrl;
    String MatchDatetime;
    String SlotTime;
    String Status;
int ChallengedPoints;
int GameId;
int GameSlotId;
String Message;
    public ReminderModel(JSONObject json) {
        try {
            ChallengeId = json.isNull("ChallengeId") ? -1 : json.getInt("ChallengeId");
            GameName = json.isNull("GameName") ? "" : json.getString("GameName");
            OpponentId = json.isNull("OpponentId") ? -1 : json.getInt("OpponentId");
            GameId = json.isNull("GameId") ? -1 : json.getInt("GameId");
            GameSlotId = json.isNull("GameSlotId") ? -1 : json.getInt("GameSlotId");
            ChallengedPoints = json.isNull("ChallengedPoints") ? 0 : json.getInt("ChallengedPoints");
            OpponentName = json.isNull("OpponentName") ? "" : json.getString("OpponentName");
            OpponentImageUrl = json.isNull("OpponentImageUrl") ? "" : json.getString("OpponentImageUrl");
            MatchDatetime = json.isNull("MatchDate") ? "" : json.getString("MatchDate");
            SlotTime = json.isNull("SlotTime") ? "" : json.getString("SlotTime");
            Status= json.isNull("Status") ? "" : json.getString("Status");
            Message= json.isNull("Message") ? "" : json.getString("Message");
        } catch (Exception ex) {
            ex.fillInStackTrace();
        }

    }

    public int getGameSlotId() {
        return GameSlotId;
    }

    public int getGameId() {
        return GameId;
    }

    public int getChallengedPoints() {
        return ChallengedPoints;
    }

    public String getStatus() {
        return Status;
    }

    public String getOpponentName() {
        return OpponentName;
    }

    public String getGameName() {
        return GameName;
    }

    public String getSlotTime() {
        return SlotTime;
    }

    public int getChallengeId() {
        return ChallengeId;
    }

    public String getMessage() {
        return Message;
    }

    public int getOpponentId() {
        return OpponentId;
    }

    public String getMatchDatetime() {
        return MatchDatetime;
    }

    public String getOpponentImageUrl() {
        return OpponentImageUrl;
    }
}
