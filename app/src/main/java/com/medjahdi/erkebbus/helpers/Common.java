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
    public static String getCurrentDateTime() {
        return getCurrentDateTime("yyyy-MM-dd HH:mm:ss");
    }

    public static String getCurrentDateTime(String pattern) {
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat moment = new SimpleDateFormat(pattern);
        String result = moment.format(cal.getTime());
        return result;
    }

    public static int countOccurences(
            String stringObject, char searchedChar) {

        int counter = 0;
        for (int i = 0; i < stringObject.length(); i++) {
            if (stringObject.charAt(i) == searchedChar) {
                counter++;
            }
        }
        return counter;
    }

    public static String CardIdExtractor(
            String stringObject, char delimiter) {
        int counter=0;
        String output = "";
        for (int i = 0; i < stringObject.length(); i++) {
            if (stringObject.charAt(i) == delimiter) {
                {
                    return stringObject.substring(i+1,i+8);
                }
            }
        }
        return null;
    }


}