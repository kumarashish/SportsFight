package sportsfight.com.s.model;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Ashish.Kumar on 08-02-2018.
 */

public class UserProfile  {
    String userName="";
    String email="";
    String mobile="";
    String profilePic=null;
    Integer gender=1;
     ArrayList<InterestedGameModel> interestedGame=new ArrayList<>();
      int userId;
      int winPercentage;
      boolean isEmailVerified;
      boolean isSelected=false;
      int totalPoints=0;
    public UserProfile(JSONObject jsonObject) {
        try {
            userId = jsonObject.getInt("RegistrationId");
            userName = jsonObject.getString("Name");
            email = jsonObject.getString("Email");
            mobile = jsonObject.getString("Mobile");
            profilePic = jsonObject.isNull("ImageUrl") ? "" : jsonObject.getString("ImageUrl");
            gender = jsonObject.isNull("Gender") ? 1 : jsonObject.getInt("Gender");
            winPercentage=jsonObject.isNull("WinPercentage") ? 0 : jsonObject.getInt("WinPercentage");
            totalPoints=jsonObject.isNull("TotalPoints") ? 0 : jsonObject.getInt("TotalPoints");
            isEmailVerified=jsonObject.isNull("IsEmailVerified") ? false : jsonObject.getBoolean("IsEmailVerified");
            if (!jsonObject.isNull("UserInterestedGamesDTO")) {
                JSONArray prefferedGame = jsonObject.getJSONArray("UserInterestedGamesDTO");
                for (int i = 0; i < prefferedGame.length(); i++) {
                    JSONObject game = prefferedGame.getJSONObject(i);
                    InterestedGameModel model = new InterestedGameModel(game.getString("Name"), game.getString("GameId"));
                    interestedGame.add(model);
                }
            }
        } catch (Exception ex) {
            ex.fillInStackTrace();
        }
    }

    public void setTotalPoints(int totalPoints) {
        this.totalPoints = totalPoints;
    }

    public void setSelected(boolean isSelected)
    {
        this.isSelected=isSelected;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public boolean isEmailVerified() {
        return isEmailVerified;
    }

    public int getWinPercentage() {
        return winPercentage;
    }

    public String getUserName() {
        return userName;
    }

    public int getGender() {
        return gender;
    }

    public ArrayList<InterestedGameModel> getInterestedGame() {
        return interestedGame;
    }

    public String getEmail() {
        return email;
    }

    public String getMobile() {
        return mobile;
    }

    public String getProfilePic() {
        return profilePic;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }
    public void setProfilePic(String ProfilePic) {
        this.profilePic = ProfilePic;
    }
    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public void updateGamePreferences(ArrayList<InterestedGameModel> list) {
        interestedGame.clear();
        interestedGame.addAll(list);
    }

    public int getTotalPoints() {
        return totalPoints;
    }
}
