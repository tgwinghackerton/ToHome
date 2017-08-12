package b05studio.com.seeyouagain.model;

import com.google.firebase.database.Exclude;

import java.io.Serializable;
import java.util.Calendar;

/**
 * Created by mansu on 2017-07-05.
 */

public class MissingPersonInfo implements Serializable{

    private String beforeUrl; // 이미지 유알엘
    private String afterUrl; // 이미지 처리 후 유알엘
    private String aword;   // 한마디
    private String name; // 이름
    private String gender; // 성별
    private Calendar timeOfMissing; // 실종시간
    private Calendar birth;
    private String address;  // 주소
    private String circumstanceOfOccurance; // 발생경위
    private String physicalCharacteristics; // 신체특징
    private String dressMarks; // 옷
    private String etc; // 기타



    public MissingPersonInfo(String beforeUrl, String afterUrl, String name, String gender, Calendar timeOfMissing, Calendar birth, String address, String aword, String circumstanceOfOccurance, String physicalCharacteristics, String dressMarks, String etc) {
        this.beforeUrl = beforeUrl;
        this.afterUrl = afterUrl;
        this.name = name;
        this.gender = gender;
        this.timeOfMissing = timeOfMissing;
        this.birth = birth;
        this.address = address;
        this.aword = aword;
        this.circumstanceOfOccurance = circumstanceOfOccurance;
        this.physicalCharacteristics = physicalCharacteristics;
        this.dressMarks = dressMarks;
        this. etc = etc;
    }

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

    public Calendar getTimeOfMissing() {
        return timeOfMissing;
    }

    public void setTimeOfMissing(Calendar timeOfMissing) {
        this.timeOfMissing = timeOfMissing;
    }

    public String getCircumstanceOfOccurance() {
        return circumstanceOfOccurance;
    }

    public void setCircumstanceOfOccurance(String circumstanceOfOccurance) {
        this.circumstanceOfOccurance = circumstanceOfOccurance;
    }

    public String getPhysicalCharacteristics() {
        return physicalCharacteristics;
    }

    public void setPhysicalCharacteristics(String physicalCharacteristics) {
        this.physicalCharacteristics = physicalCharacteristics;
    }

    public String getDressMarks() {
        return dressMarks;
    }

    public void setDressMarks(String dressMarks) {
        this.dressMarks = dressMarks;
    }

    public String getEtc() {
        return etc;
    }

    public void setEtc(String etc) {
        this.etc = etc;
    }
}
