package b05studio.com.seeyouagain.model;

import android.os.Parcelable;

import java.io.Serializable;

/**
 * Created by mansu on 2017-10-21.
 */

public class GreenUmInfo implements Serializable {
    private String beforeUrl; // 이미지 유알엘
    private String name; // 이름
    private String gender; // 성별
    private String timeOfMissing; // 실종시간
    private String age;
    private String address;  // 주소
    private String physicalCharacteristics; // 신체특징

    public GreenUmInfo() {}

    public GreenUmInfo(String beforeUrl, String name, String gender, String timeOfMissing, String age, String address, String physicalCharacteristics) {
        this.beforeUrl = beforeUrl;
        this.name = name;
        this.gender = gender;
        this.timeOfMissing = timeOfMissing;
        this.age = age;
        this.address = address;
        this.physicalCharacteristics = physicalCharacteristics;
    }

    public String getBeforeUrl() {
        return beforeUrl;
    }

    public void setBeforeUrl(String beforeUrl) {
        this.beforeUrl = beforeUrl;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getTimeOfMissing() {
        return timeOfMissing;
    }

    public void setTimeOfMissing(String timeOfMissing) {
        this.timeOfMissing = timeOfMissing;
    }

    public String getPhysicalCharacteristics() {
        return physicalCharacteristics;
    }

    public void setPhysicalCharacteristics(String physicalCharacteristics) {
        this.physicalCharacteristics = physicalCharacteristics;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

}
