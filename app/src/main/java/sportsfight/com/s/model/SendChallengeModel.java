package sportsfight.com.s.model;

/**
 * Created by ashish.kumar on 10-10-2018.
 */

public class SendChallengeModel {
    String teamId;
    String teamName;
    String gameName;
    int gameId;
    String playingArea;
    String date;
    String time;
    String playground;
    String challenge_coins;
    boolean isTeam=false;


    public void clearChallenge()
    {
        teamId = null;
        teamName = "";
        playingArea = "";
        date = "";
        time = "";
        playground = "";
        challenge_coins = "";
    }
    public void setGameName(String gameName) {
        this.gameName = gameName;
    }

    public void setTeam(boolean team) {
        isTeam = team;
    }

    public boolean isTeam() {
        return isTeam;
    }

    public void setChallenge_coins(String challenge_coins) {
        this.challenge_coins = challenge_coins;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setGameId(int gameId) {
        this.gameId = gameId;
    }

    public void setPlayground(String playground) {
        this.playground = playground;
    }

    public void setPlayingArea(String playingArea) {
        this.playingArea = playingArea;
    }

    public void setTeamId(String teamId) {
        this.teamId = teamId;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getGameId() {
        return gameId;
    }

    public String getDate() {
        return date;
    }

    public String getTeamName() {
        return teamName;
    }

    public String getGameName() {
        return gameName;
    }

    public String getTeamId() {
        return teamId;
    }

    public String getChallenge_coins() {
        return challenge_coins;
    }

    public String getPlayground() {
        return playground;
    }

    public String getPlayingArea() {
        return playingArea;
    }

    public String getTime() {
        return time;
    }
}


