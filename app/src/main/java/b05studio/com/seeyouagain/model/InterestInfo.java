package b05studio.com.seeyouagain.model;

/**
 * Created by mansu on 2017-07-05.
 */

public class InterestInfo {
    private GreenUmInfo greenUmInfo;
    private MissingPersonInfo missingPersonInfo;

    public InterestInfo(GreenUmInfo greenUmInfo, MissingPersonInfo missingPersonInfo) {
        this.greenUmInfo = greenUmInfo;
        this.missingPersonInfo = missingPersonInfo;
    }

    public GreenUmInfo getGreenUmInfo() {
        return greenUmInfo;
    }

    public void setGreenUmInfo(GreenUmInfo greenUmInfo) {
        this.greenUmInfo = greenUmInfo;
    }

    public MissingPersonInfo getMissingPersonInfo() {
        return missingPersonInfo;
    }

    public void setMissingPersonInfo(MissingPersonInfo missingPersonInfo) {
        this.missingPersonInfo = missingPersonInfo;
    }
}
