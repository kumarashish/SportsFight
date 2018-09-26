package sportsfight.com.s.model;

import org.json.JSONObject;

/**
 * Created by Ashish.Kumar on 19-04-2018.
 */

public class IPLResultModel {
    int ChallengeId;
    String ResultText;
    int WinnerId;
    String WinnerImage;
    String WinnerName;
    String MatchDate;
    int LooserId;
    String LooserImage;
    String LooserName;
    String SlotTime;
    int RewardPoints;
    int Id;

    public IPLResultModel(JSONObject jsonObject) {
        try {
            ChallengeId = jsonObject.isNull("ChallengeId") ? -1 : jsonObject.getInt("ChallengeId");
            ResultText = jsonObject.isNull("ResultText") ? "" : jsonObject.getString("ResultText");
            WinnerId = jsonObject.isNull("WinnerId") ? -1 : jsonObject.getInt("WinnerId");
            WinnerImage = jsonObject.isNull("WinnerImage") ? "" : jsonObject.getString("WinnerImage");
            WinnerName = jsonObject.isNull("WinnerName") ? "" : jsonObject.getString("WinnerName");
            MatchDate = jsonObject.isNull("MatchDate") ? "" : jsonObject.getString("MatchDate");
            LooserId = jsonObject.isNull("LooserId") ? -1 : jsonObject.getInt("LooserId");
            LooserImage = jsonObject.isNull("LooserImage") ? "" : jsonObject.getString("LooserImage");
            LooserName = jsonObject.isNull("LooserName") ? "" : jsonObject.getString("LooserName");
            SlotTime = jsonObject.isNull("SlotTime") ? "" : jsonObject.getString("SlotTime");
            RewardPoints = jsonObject.isNull("RewardPoints") ? -1 : jsonObject.getInt("RewardPoints");
            Id = jsonObject.isNull("Id") ? -1 : jsonObject.getInt("Id");
        } catch (Exception ex) {
            ex.fillInStackTrace();
        }
    }

    public String getWinnerName() {
        return WinnerName;
    }

    public String getResultText() {
        return ResultText;
    }

    public String getLooserName() {
        return LooserName;
    }

    public int getChallengeId() {
        return ChallengeId;
    }

    public int getId() {
        return Id;
    }

    public int getWinnerId() {
        return WinnerId;
    }

    public int getLooserId() {
        return LooserId;
    }

    public String getMatchDate() {
        return MatchDate;
    }

    public String getSlotTime() {
        return SlotTime;
    }

    public int getRewardPoints() {
        return RewardPoints;
    }

    public String getLooserImage() {
        return LooserImage;
    }

    public String getWinnerImage() {
        return WinnerImage;
    }
}
