package rakshan.sps.tamilnames;

import java.util.ArrayList;
import java.util.List;

public class UserActivity {
    public String gname;
    public String gemail;
    public List<String> user_Saved_names;

    public String getGname() {
        return gname;
    }

    public void setGname(String gname) {
        this.gname = gname;
    }

    public String getGemail() {
        return gemail;
    }

    public void setGemail(String gemail) {
        this.gemail = gemail;
    }

    public List<String> getUser_Saved_names() {
        return user_Saved_names;
    }

    public void setUser_Saved_names(List<String> user_Saved_names) {
        this.user_Saved_names = user_Saved_names;
    }

    public UserActivity(String gname, String gemail, List<String> user_Saved_names) {
        this.gname = gname;
        this.gemail = gemail;
        this.user_Saved_names = user_Saved_names;
    }



    public UserActivity() {
    }





}

