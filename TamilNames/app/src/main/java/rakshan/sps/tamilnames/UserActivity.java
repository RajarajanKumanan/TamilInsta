package rakshan.sps.tamilnames;

public class UserActivity {
    public static String UserName;
    public static String UserEmail;

    public static String getUserName() {
        return UserName;
    }

    public static void setUserName(String userName) {
        UserName = userName;
    }

    public static String getUserEmail() {
        return UserEmail;
    }

    public static void setUserEmail(String userEmail) {
        UserEmail = userEmail;
    }

    public UserActivity() {
    }

    public UserActivity(String userName, String userEmail) {
        UserName = userName;
        UserEmail = userEmail;
    }
}

