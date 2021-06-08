package org.example.robot.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TimeFormatTransformer {
    public static Long formatTimeStringToTimeStamp(String formatTimeString){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date;
        try {
            date = simpleDateFormat.parse(formatTimeString);
            return date.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
            return Long.parseLong("-1");
        }
    }
}
