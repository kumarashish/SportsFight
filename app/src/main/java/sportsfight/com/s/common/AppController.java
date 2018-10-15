package sportsfight.com.s.common;

import android.app.Application;
import android.graphics.Typeface;
import android.location.Location;
import android.os.Environment;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;

import com.google.android.gms.maps.model.LatLng;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;

import sportsfight.com.s.interfaces.WebApiResponseCallback;
import sportsfight.com.s.model.InterestedGameModel;
import sportsfight.com.s.model.SendChallengeModel;
import sportsfight.com.s.model.UserProfile;
import sportsfight.com.s.network.WebApiCall;
import sportsfight.com.s.util.Util;
import sportsfight.com.s.util.Validation;

/**
 * Created by Ashish.Kumar on 16-01-2018.
 */

public class AppController extends Application {
    PrefManager prefManager;
    Typeface headingFont,detailsFont,congratulation_font,match_font;
    Validation validation;
    WebApiCall apiCall;
    String tempId="";
    UserProfile profile;
   int points=0;
    String paymentGatewayToken=null;
    String address="";
    LatLng currentLocation=null;
    SendChallengeModel challengeModel;
    @Override
    public void onCreate() {
        super.onCreate();
        prefManager = new PrefManager(this);
        challengeModel=new SendChallengeModel();
        detailsFont = Typeface.createFromAsset(getApplicationContext().getAssets(), "detailsfont.otf");
        congratulation_font = Typeface.createFromAsset(getApplicationContext().getAssets(), "congratulation_font.ttf");
        match_font = Typeface.createFromAsset(getApplicationContext().getAssets(), "vs_font.ttf");
        validation = new Validation(this);
        apiCall = new WebApiCall(this);
        Common.FCMToken=prefManager.getFcmToken();
        String userProfile = prefManager.getUserProfile();
        if (userProfile.length() > 0) {
            Gson gson = new Gson();
            profile = gson.fromJson(userProfile, UserProfile.class);
        }
        Util.makeFolder(String.valueOf(Environment.getExternalStorageDirectory()), "/SportsFight");
        Common.sdCardPath = Environment.getExternalStorageDirectory() + "/SportsFight";
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
    }
    public void setAddress(String address, LatLng loc) {
        this.address = address;
        this.currentLocation=loc;
    }
    public void setCurrentAddress(String address) {
        this.address = address;

    }

    public void setLocation(LatLng loc) {
        double latitude = loc.latitude;
        double longitude = loc.longitude;
        currentLocation = loc;
    }


    public SendChallengeModel getChallengeModel() {
        return challengeModel;
    }

    public String getAddress() {
        return address;
    }

    public LatLng getCurrentLocation() {
        return currentLocation;
    }
    public void setPoints(int points) {
        profile.setTotalPoints(points);
        Gson gson = new Gson();
        String userProfileString = gson.toJson(profile);
        prefManager.setUserProfile(userProfileString);
    }

    public void setTempId(String id)
    {
        tempId=id;
    }

    public String getTempId() {
        return tempId;
    }

    public Validation getValidation(){return validation;}
    public PrefManager getPrefManager() {
        return prefManager;
    }

    public Typeface getDetailsFont() {
        return detailsFont;
    }

    public Typeface getCongratulation_font() {
        return congratulation_font;
    }

    public Typeface getMatch_font() {
        return match_font;
    }

    public WebApiCall getApiCall() {
        return apiCall;
    }

    public void setUserProfile(JSONObject userProfile) {
        UserProfile profile=new UserProfile(userProfile);
        this.profile=profile;
        Gson gson = new Gson();
        String userProfileString = gson.toJson(profile);
        prefManager.setUserProfile(userProfileString);
    }
    public void setUserProfile( UserProfile userProfile) {

        this.profile=userProfile;
        Gson gson = new Gson();
        String userProfileString = gson.toJson(profile);
        prefManager.setUserProfile(userProfileString);
    }
    public void updateGender(String gender) {
        profile.setGender(gender);
        Gson gson = new Gson();
        String userProfileString = gson.toJson(profile);
        prefManager.setUserProfile(userProfileString);
    }

    public void updateProfilePic(String profilePic) {
        profile.setProfilePic(profilePic);
        Gson gson = new Gson();
        String userProfileString = gson.toJson(profile);
        prefManager.setUserProfile(userProfileString);
    }

    public void updateGamePreferences(ArrayList<InterestedGameModel> list) {
        profile.updateGamePreferences(list);
        Gson gson = new Gson();
        String userProfileString = gson.toJson(profile);
        prefManager.setUserProfile(userProfileString);
    }
    public void updateMobileNumber(String mobileNumber) {
        profile.setMobile(mobileNumber);
        Gson gson = new Gson();
        String userProfileString = gson.toJson(profile);
        prefManager.setUserProfile(userProfileString);
    }

    public UserProfile getProfile() {
        return profile;
    }

    public void logout() {
        prefManager.clearPreferences();
        profile=null;

    }

    public String getPaymentGatewayToken() {
        return paymentGatewayToken;
    }

    public void setPaymentGatewayToken(String paymentGatewayToken) {
        this.paymentGatewayToken = paymentGatewayToken;
    }
}
