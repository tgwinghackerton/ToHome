package b05studio.com.seeyouagain.model;

/**
 * Created by mansu on 2017-10-22.
 */

public class SearchInfo {
    private String key;
    private int type;
    private MissingPersonInfo missingPersonInfo;
    private GreenUmInfo greenUmInfo;

    public SearchInfo(String key, int type, MissingPersonInfo missingPersonInfo, GreenUmInfo greenUmInfo) {
        this.key = key;
        this.type = type;
        this.missingPersonInfo = missingPersonInfo;
        this.greenUmInfo = greenUmInfo;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public MissingPersonInfo getMissingPersonInfo() {
        return missingPersonInfo;
    }

    public void setMissingPersonInfo(MissingPersonInfo missingPersonInfo) {
        this.missingPersonInfo = missingPersonInfo;
    }

    public GreenUmInfo getGreenUmInfo() {
        return greenUmInfo;
    }

    public void setGreenUmInfo(GreenUmInfo greenUmInfo) {
        this.greenUmInfo = greenUmInfo;
    }
}
