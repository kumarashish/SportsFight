package sportsfight.com.s.common;

import android.content.Context;
import android.content.SharedPreferences;

import org.json.JSONObject;

/**
 * Created by Ashish.Kumar on 16-01-2018.
 */

public class PrefManager {
    SharedPreferences pref;
    SharedPreferences.Editor editor;
    Context _context;

    // shared pref mode
    int PRIVATE_MODE = 0;
    private static final String PREF_NAME = "SportsFight";
    private static final String LoggedIn = "SportsFightUserLoggedIn";
    private static final String FcmToken = "FcmToken";
    private static final String UserToken = "USerToken";
    private static final String IS_FIRST_TIME_LAUNCH = "IsFirstTimeLaunch";

    public PrefManager(Context context) {
        this._context = context;
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();

    }

    public SharedPreferences getPref() {
        return pref;
    }

    public void clearPreferences() {
        String tokenFcm = getFcmToken();
        boolean isFirstTimeLaunch = isFirstTimeLaunch();
        editor.clear().commit();
        setFcmToken(tokenFcm);
        setFirstTimeLaunch(isFirstTimeLaunch);

    }

    public void setUserToken(String value) {
        try {
            JSONObject jsonObject = new JSONObject(value);
            JSONObject jsonObject1 = jsonObject.getJSONObject("Result");
            editor.putString(UserToken, jsonObject1 .getString("Token"));
            editor.commit();
        } catch (Exception ex) {
            ex.fillInStackTrace();
        }
    }

    public String getUserToken() {
        return pref.getString(UserToken, "");
    }

    public void setUserLoggedIn(boolean isloggedIn) {
        editor.putBoolean(LoggedIn , isloggedIn);
        editor.commit();
    }
    public void setFcmToken(String token) {
        editor.putString(FcmToken , token);
        editor.commit();
    }

    public  String getFcmToken() {
        return  pref.getString(FcmToken,"");
    }

    public boolean isUserLoggedIn()
 {
     return pref.getBoolean(LoggedIn , false);
 }
    public void setFirstTimeLaunch(boolean isFirstTime) {
        editor.putBoolean(IS_FIRST_TIME_LAUNCH, isFirstTime);
        editor.commit();
    }

    public boolean isFirstTimeLaunch() {
        return pref.getBoolean(IS_FIRST_TIME_LAUNCH, true);
    }

    public void setUserProfile(String profile) {
        editor.putString("userProfile",profile);
        editor.commit();
    }
  public String  getUserProfile()
  {
      return  pref.getString("userProfile","");
  }
}
