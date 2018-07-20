package com.medjahdi.erkebbus.helpers;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public final class Common {
    public static String getCurrentDateTime()
    {
       return getCurrentDateTime("yyyy-MM-dd HH:mm:ss");
    }

    public static String getCurrentDateTime(String pattern)
    {
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat moment = new SimpleDateFormat(pattern);
        String result = moment.format(cal.getTime());
        return result;
    }
}
