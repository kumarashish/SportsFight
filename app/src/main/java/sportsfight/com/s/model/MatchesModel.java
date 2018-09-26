package sportsfight.com.s.model;

import org.json.JSONObject;

/**
 * Created by Ashish.Kumar on 21-02-2018.
 */

public class MatchesModel {
    int ChallengeId;
    String GameName;
    int TotalBids;
    int Player1Id;
    String Player1Name;
    String Player1ImageUrl;
    int Player1Bids;
    int Player2Id;
    String Player2Name;
    String Player2ImageUrl;
    int Player2Bids;
    String MatchDate;
    String SlotTime;
    int MyBid;
    int MyBidToId;
    boolean IsWon;
    int WinnerId;
String Message;
String MatchPlace;
   public MatchesModel(JSONObject jsonObject)
    {
        try{
            ChallengeId = jsonObject.isNull("ChallengeId") ? -1 : jsonObject.getInt("ChallengeId");
            GameName = jsonObject.isNull("GameName") ? "" : jsonObject.getString("GameName");
            TotalBids = jsonObject.isNull("TotalBids") ? -1 : jsonObject.getInt("TotalBids");
            Player1Id = jsonObject.isNull("Player1Id") ? -1 : jsonObject.getInt("Player1Id");
            Player1Name = jsonObject.isNull("Player1Name") ? "" : jsonObject.getString("Player1Name");
            Player1ImageUrl = jsonObject.isNull("Player1ImageUrl") ? "" : jsonObject.getString("Player1ImageUrl");
            Player1Bids = jsonObject.isNull("Player1Bids") ? -1 : jsonObject.getInt("Player1Bids");
            Player2Id = jsonObject.isNull("Player2Id") ? -1 : jsonObject.getInt("Player2Id");
            Player2Name = jsonObject.isNull("Player2Name") ? "" : jsonObject.getString("Player2Name");
            Player2ImageUrl = jsonObject.isNull("Player2ImageUrl") ? "" : jsonObject.getString("Player2ImageUrl");
            Player2Bids = jsonObject.isNull("Player2Bids") ? -1 : jsonObject.getInt("Player2Bids");
            MatchDate = jsonObject.isNull("MatchDate") ? "" : jsonObject.getString("MatchDate");
            SlotTime = jsonObject.isNull("SlotTime") ? "" : jsonObject.getString("SlotTime");
            MyBid = jsonObject.isNull("MyBid") ? -1: jsonObject.getInt("MyBid");
            MyBidToId = jsonObject.isNull("MyBidToId") ? -1 : jsonObject.getInt("MyBidToId");
            WinnerId= jsonObject.isNull("WinnerId") ? -1: jsonObject.getInt("WinnerId");
            IsWon = jsonObject.isNull("IsWon") ? false : jsonObject.getBoolean("IsWon");
            Message= jsonObject.isNull("Message") ? "" : jsonObject.getString("Message");
            MatchPlace=jsonObject.isNull("MatchPlace") ? "" : jsonObject.getString("MatchPlace");
        }catch (Exception ex)
        {
            ex.fillInStackTrace();
        }
    }

    public String getMatchPlace() {
        return MatchPlace;
    }

    public String getMessage() {
        return Message;
    }

    public int getWinnerId() {
        return WinnerId;
    }

    public boolean isWon() {
        return IsWon;
    }

    public int getMyBid() {
        return MyBid;
    }

    public void setMyBid(int myBid) {
        MyBid = myBid;
    }

    public void setTotalBids(int totalBids) {
        TotalBids = totalBids;
    }

    public void setMyBidToId(int myBidToId) {
        MyBidToId = myBidToId;
    }

    public int getMyBidToId() {
        return MyBidToId;
    }

    public int getChallengeId() {
        return ChallengeId;
    }

    public String getSlotTime() {
        return SlotTime;
    }

    public String getGameName() {
        return GameName;
    }

    public String getMatchDate() {
        return MatchDate;
    }

    public int getPlayer1Bids() {
        return Player1Bids;
    }

    public int getPlayer1Id() {
        return Player1Id;
    }

    public int getPlayer2Bids() {
        return Player2Bids;
    }

    public int getPlayer2Id() {
        return Player2Id;
    }

    public int getTotalBids() {
        return TotalBids;
    }

    public String getPlayer1ImageUrl() {
        return Player1ImageUrl;
    }

    public String getPlayer1Name() {
        return Player1Name;
    }

    public String getPlayer2ImageUrl() {
        return Player2ImageUrl;
    }

    public String getPlayer2Name() {
        return Player2Name;
    }




}
