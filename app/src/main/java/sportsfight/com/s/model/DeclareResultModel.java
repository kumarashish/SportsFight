package sportsfight.com.s.model;

import org.json.JSONObject;

/**
 * Created by Ashish.Kumar on 07-03-2018.
 */

public class DeclareResultModel {
    int GameId;
    int ChallengedBy;
    int ChallengedTo;
    String MatchDate;
    int GameSlotId;
    int GameSlot;
    int ChallengedPoints;
    int Id;
    int winnerId = -1;
    int looserId = -1;
    ChallengeResponse challengeResponse;
    Boolean isApproved=false;
    public DeclareResultModel(JSONObject jsonObject) {
        try {
            GameId = jsonObject.getInt("GameId");
            ChallengedBy = jsonObject.getInt("ChallengedBy");
            ChallengedTo = jsonObject.getInt("ChallengedTo");
            MatchDate = jsonObject.getString("MatchDate");
            GameSlotId =jsonObject.isNull("GameSlotId")?-1: jsonObject.getInt("GameSlotId");
            ChallengedPoints = jsonObject.getInt("ChallengedPoints");
            Id = jsonObject.getInt("Id");
            challengeResponse=new ChallengeResponse(jsonObject.getJSONObject("ChallengeResponse"));
        } catch (Exception ex) {
            ex.fillInStackTrace();
        }
    }

    public String getMatchDate() {
        return MatchDate;
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

    public int getChallengedTo() {
        return ChallengedTo;
    }

    public int getChallengedBy() {
        return ChallengedBy;
    }

    public ChallengeResponse getChallengeResponse() {
        return challengeResponse;
    }

    public int getId() {
        return Id;
    }

    public int getGameSlot() {
        return GameSlot;
    }

    public void setWinnerId(int winnerId) {
        this.winnerId = winnerId;
    }

    public void setLooserId(int looserId) {
        this.looserId = looserId;
    }

    public int getWinnerId() {
        return winnerId;
    }

    public int getLooserId() {
        return looserId;
    }

    public void setApproved(Boolean approved) {
        isApproved = approved;
    }

    public Boolean getApproved() {
        return isApproved;
    }

    public class ChallengeResponse {
        String ChallengedByName;
        String ChallengedToName;
        String GameName;

        public ChallengeResponse(JSONObject jsonObject) {
            try {
                ChallengedByName = jsonObject.getString("ChallengedByName");
                ChallengedToName = jsonObject.getString("ChallengedToName");
                GameName = jsonObject.getString("GameName");

            } catch (Exception ex) {
                ex.fillInStackTrace();
            }
        }

        public String getGameName() {
            return GameName;
        }

        public String getChallengedByName() {
            return ChallengedByName;
        }

        public String getChallengedToName() {
            return ChallengedToName;
        }
    }



}
