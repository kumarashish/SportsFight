package sportsfight.com.s.model;

/**
 * Created by Ashish.Kumar on 16-02-2018.
 */

public class ChallengeModel {
    int GameID=-1;
   int  ChallengedBy=-1;
   int ChallengedTo=-1;
   String MatchDate="";
   int GameSlotId=-1;
   int ChallengedPoints=-1;
   String deviceId="";
   String opponentName="";
   String matchTime="";
   int minPoints=-1;
   int maxPoints=-1;
   int matchPoints=-1;
   String gameName="";
   String day="";

    public void setChallengedBy(int challengedBy) {
        ChallengedBy = challengedBy;
    }

//    public void setChallengedPoints(int challengedPoints) {
//        ChallengedPoints = challengedPoints;
//    }

    public void setChallengedTo(int challengedTo) {
        ChallengedTo = challengedTo;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public void setGameID(int gameID) {
        GameID = gameID;
    }

    public void setGameSlotId(int gameSlotId) {
        GameSlotId = gameSlotId;
    }

    public void setMatchDate(String matchDate) {
        MatchDate = matchDate;
    }

    public int getChallengedBy() {
        return ChallengedBy;
    }

    public int getChallengedPoints() {
        return ChallengedPoints;
    }

    public int getChallengedTo() {
        return ChallengedTo;
    }

    public int getGameID() {
        return GameID;
    }

    public int getGameSlotId() {
        return GameSlotId;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public String getMatchDate() {
        return MatchDate;
    }

    public void setGameName(String gameName) {
        this.gameName = gameName;
    }

    public void setMatchTime(String matchTime) {
        this.matchTime = matchTime;
    }

    public void setMaxPoints(int maxPoints) {
        this.maxPoints = maxPoints;
    }

    public void setMinPoints(int minPoints) {
        this.minPoints = minPoints;
    }

    public void setOpponentName(String opponentName) {
        this.opponentName = opponentName;
    }

    public int getMinPoints() {
        return minPoints;
    }

    public int getMaxPoints() {
        return maxPoints;
    }

    public String getGameName() {
        return gameName;
    }

    public String getMatchTime() {
        return matchTime;
    }

    public String getOpponentName() {
        return opponentName;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getDay() {
        return day;
    }

    public void setMatchPoints(int matchPoints) {
        this.matchPoints = matchPoints;
    }

    public int getMatchPoints() {
        return matchPoints;
    }

    public void setToDefault() {
        GameID = -1;
        ChallengedBy = -1;
        ChallengedTo = -1;
        MatchDate = "";
        GameSlotId = -1;
        ChallengedPoints = -1;
        deviceId = "";
        opponentName = "";
        matchTime = "";
        minPoints = -1;
        maxPoints = -1;
        matchPoints = -1;
        gameName = "";
        day = "";
    }
}
