package com.medjahdi.erkebbus.helpers;

import android.content.Context;

import com.medjahdi.erkebbus.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
