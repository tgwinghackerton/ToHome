package b05studio.com.seeyouagain.model;

import com.google.firebase.database.Exclude;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by mansu on 2017-08-10.
 */

public class User {
    private String email;
    private String name;
    private String phoneNumber;
    private String token;
    private List<String> userLikeList = new ArrayList<>();
    private List<MissingPersonInfo> infoList = new ArrayList<>();
    private HashMap<String, AlarmInfo> alarmInfos = new HashMap<>();

    @Exclude
    private String password;

    private static User currentUser = new User();

    private User() {}

    public static synchronized User getUserInstance() {
        return currentUser;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public List<String> getUserLikeList() {
        return userLikeList;
    }

    public void setUserLikeList(List<String> userLikeList) {
        this.userLikeList = userLikeList;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public HashMap<String, AlarmInfo> getAlarmInfos() {
        return alarmInfos;
    }

    public void setAlarmInfos(HashMap<String, AlarmInfo> alarmInfos) {
        this.alarmInfos = alarmInfos;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public List<MissingPersonInfo> getInfoList() {
        return infoList;
    }

    public void setInfoList(List<MissingPersonInfo> infoList) {
        this.infoList = infoList;
    }
}