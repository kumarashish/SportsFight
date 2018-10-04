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
   String CompanyName;
    String LocationName;
    String Latitude;
    String Longitude;
    int TotalGames;
    int TotalPoints;
   int WinPercentage;
    String ClubName;
   String gender;
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
            gender = jsonObject.isNull("Gender") ? "": jsonObject.getString("Gender");
            winPercentage=jsonObject.isNull("WinPercentage") ? 0 : jsonObject.getInt("WinPercentage");
            totalPoints=jsonObject.isNull("TotalPoints") ? 0 : jsonObject.getInt("TotalPoints");
            isEmailVerified=jsonObject.isNull("IsEmailVerified") ? false : jsonObject.getBoolean("IsEmailVerified");
             CompanyName =jsonObject.isNull("CompanyName") ? "" : jsonObject.getString("CompanyName");
            LocationName= jsonObject.isNull("LocationName") ? "" : jsonObject.getString("LocationName");
             Latitude =jsonObject.isNull("Latitude") ? "" : jsonObject.getString("Latitude");
             Longitude= jsonObject.isNull("Longitude") ? "" : jsonObject.getString("Longitude");
             TotalGames=jsonObject.isNull("TotalGames") ? 0 : jsonObject.getInt("TotalGames");
           TotalPoints=jsonObject.isNull("TotalPoints") ? 0 : jsonObject.getInt("TotalPoints");
             WinPercentage=jsonObject.isNull("WinPercentage") ? 0 : jsonObject.getInt("WinPercentage");
            ClubName=jsonObject.isNull("ClubName") ? "" : jsonObject.getString("ClubName");
            if (!jsonObject.isNull("UserInterestedGamesDTO")) {
                JSONArray prefferedGame = jsonObject.getJSONArray("UserInterestedGamesDTO");
                for (int i = 0; i < prefferedGame.length(); i++) {
                    JSONObject game = prefferedGame.getJSONObject(i);
                    InterestedGameModel model = new InterestedGameModel( game);
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

    public String getGender() {
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

    public String getClubName() {
        return ClubName;
    }

    public String getCompanyName() {
        return CompanyName;
    }

    public int getTotalGames() {
        return TotalGames;
    }

    public String getLatitude() {
        return Latitude;
    }

    public String getLocationName() {
        return LocationName;
    }

    public String getLongitude() {
        return Longitude;
    }

    public void setGender(String gender) {
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
