package b05studio.com.seeyouagain.model;

/**
 * Created by mansu on 2017-08-13.
 */

import java.io.Serializable;

        import com.google.firebase.database.Exclude;

        import java.io.Serializable;
        import java.util.Calendar;

/**
 * Created by mansu on 2017-07-05.
 */

public class MissingPersonInfo implements Serializable {

    private String beforeUrl; // 이미지 유알엘
    private String afterUrl; // 이미지 처리 후 유알엘
    private String aword;   // 한마디
    private String name; // 이름
    private String gender; // 성별
    private long timeOfMissing; // 실종시간
    private int age;
    private String address;  // 주소
    private String circumstanceOfOccurance; // 발생경위
    private String physicalCharacteristics; // 신체특징
    private String dressMarks; // 옷
    private String etc; // 기타
    private String writerKey;

    public MissingPersonInfo() {}

    public MissingPersonInfo(String beforeUrl, String afterUrl, String name, String gender, long timeOfMissing, int age, String address, String aword, String circumstanceOfOccurance, String physicalCharacteristics, String dressMarks, String etc, String writerKey) {
        this.beforeUrl = beforeUrl;
        this.afterUrl = afterUrl;
        this.name = name;
        this.gender = gender;
        this.timeOfMissing = timeOfMissing;
        this.age = age;
        this.address = address;
        this.aword = aword;
        this.circumstanceOfOccurance = circumstanceOfOccurance;
        this.physicalCharacteristics = physicalCharacteristics;
        this.dressMarks = dressMarks;
        this.etc = etc;
        this.writerKey = writerKey;
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

    public long getTimeOfMissing() {
        return timeOfMissing;
    }

    public void setTimeOfMissing(long timeOfMissing) {
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

    public String getWriterKey() {
        return writerKey;
    }

    public void setWriterKey(String writerKey) {
        this.writerKey = writerKey;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
