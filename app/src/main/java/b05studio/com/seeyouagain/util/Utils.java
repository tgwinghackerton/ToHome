package b05studio.com.seeyouagain.util;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import b05studio.com.seeyouagain.model.MissingPersonInfo;

/**
 * Created by mansu on 2017-08-11.
 */

public class Utils {
    public static String getDateString(long createDate) {
        long timeDiff = System.currentTimeMillis() - createDate;

        if(timeDiff < 60000)
            return "";
        else if(timeDiff < 3600000)
            return "";
        else if(timeDiff < 86400000)
            return "";
        else if(timeDiff < 2592000000L)
            return (timeDiff/(86400000))+"일 전";
        else if(timeDiff >= 31104000000L)
            return (timeDiff/(2592000000L))+"달 전";
        else
            return (timeDiff/(31104000000L))+"년 전";
    }

    public static int getAge(long timestamp, int age) {
        Calendar today = Calendar.getInstance();
        Calendar before = Calendar.getInstance();
        before.setTimeInMillis(timestamp);

        int diff = today.get(Calendar.YEAR) - before.get(Calendar.YEAR);

        return diff + age;
    }
}
