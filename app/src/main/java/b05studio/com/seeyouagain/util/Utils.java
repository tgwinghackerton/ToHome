package b05studio.com.seeyouagain.util;

import java.util.Calendar;

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

    public static int getAge(Calendar calendar) {
        Calendar today = Calendar.getInstance();

        int age = today.get(Calendar.YEAR) - calendar.get(Calendar.YEAR);
        if (today.get(Calendar.DAY_OF_YEAR) < calendar.get(Calendar.DAY_OF_YEAR))
            age--;
        Integer ageInt = new Integer(age);

        return ageInt;
    }
}
