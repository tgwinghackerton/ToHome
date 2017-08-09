package b05studio.com.mpf.model;

import com.google.firebase.database.Exclude;

import java.io.Serializable;
import java.util.Calendar;

/**
 * Created by mansu on 2017-07-05.
 */

public class MissingPersonInfo implements Serializable{
    private String beforeUrl;
    private String afterUrl;
    private String name;
    private String gender;
    private Calendar timeOfMissing;
    private Calendar birth;
    private String address;
    private String aword;
    @Exclude
    private MissingPersonInfoDetail detail;

    public String getBeforeUrl() {
        return beforeUrl;
    }

    public void setBeforeUrl(String beforeUrl) {
        this.beforeUrl = beforeUrl;
    }

    public String getAfterUrl() {
        return afterUrl;
    }

    public void setAfterUrl(String afterUrl) {
        this.afterUrl = afterUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Calendar getBirth() {
        return birth;
    }

    public void setBirth(Calendar birth) {
        this.birth = birth;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAword() {
        return aword;
    }

    public void setAword(String aword) {
        this.aword = aword;
    }

    public MissingPersonInfoDetail getDetail() {
        return detail;
    }

    public void setDetail(MissingPersonInfoDetail detail) {
        this.detail = detail;
    }

    public Calendar getTimeOfMissing() {
        return timeOfMissing;
    }

    public void setTimeOfMissing(Calendar timeOfMissing) {
        this.timeOfMissing = timeOfMissing;
    }
}
