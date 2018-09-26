package sportsfight.com.s.model;

/**
 * Created by Ashish.Kumar on 31-01-2018.
 */

public class RegistrationModel {
    String userName;
    String userMobile;
    String password;
    String userEmail;
    Integer[] intGame=null;

    public RegistrationModel(String userName, String userEmail, String userMobile, String password) {
        this.userEmail = userEmail;
        this.password = password;
        this.userName = userName;
        this.userEmail = userEmail;
        this.userMobile = userMobile;
    }

    public Integer[] getIntGame() {
        return intGame;
    }

    public void setIntGame(Integer[] intGame) {
        this.intGame = intGame;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public String getPassword() {
        return password;
    }

    public String getUserMobile() {
        return userMobile;
    }

    public String getUserName() {
        return userName;
    }
}
