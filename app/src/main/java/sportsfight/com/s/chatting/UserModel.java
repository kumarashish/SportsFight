package sportsfight.com.s.chatting;

public class UserModel {
    String userId = "";
    String username = "";
    String email = "";
    String chatWith = "";
    String lastSeen="";
    public  UserModel( String userId,String username,String email,String lastSeen)


    {
        this.userId=userId;
        this.username=username;
        this.email=email;
        this.lastSeen=lastSeen;
    }


    public String getChatWith() {
        return chatWith;
    }

    public String getEmail() {
        return email;
    }

    public String getUserId() {
        return userId;
    }

    public String getUsername() {
        return username;
    }

    public String getLastSeen() {
        return lastSeen;
    }
}

