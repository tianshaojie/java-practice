package com.yuzhi.fine.date;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtils {

    public static void main(String[] args) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");

        String date1="20180709";//日期字符串
        String date2="20180708";
        boolean flag = isSameWeek(date1, date2, sdf);
        System.out.println(flag?"在同一周内":"不在同一周内");


        String date11="20180701";//日期字符串
        String date22="20180630";
        boolean flag1 = isSameMonth(date11, date22, sdf);
        System.out.println(flag1?"在同一月内":"不在同一月内");


        System.out.println(getDayOfBefore("20180101", sdf));
    }


    //判断选择的日期是否是本周
    public static boolean isSameWeek(String time1, String time2, SimpleDateFormat sdf) {
        try {
            Calendar calendar = Calendar.getInstance();
            calendar.setFirstDayOfWeek(Calendar.MONDAY);
            calendar.setTime(sdf.parse(time1));
            int currentWeek = calendar.get(Calendar.WEEK_OF_YEAR);
            System.out.println("-----------------" + currentWeek);
            calendar.setTime(sdf.parse(time2));
            int paramWeek = calendar.get(Calendar.WEEK_OF_YEAR);
            System.out.println("-----------------" + paramWeek);
            if (paramWeek == currentWeek) {
                return true;
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return false;
    }

    //判断选择的日期是否是本周
    public static boolean isSameMonth(String time1, String time2, SimpleDateFormat sdf) {
        try {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(sdf.parse(time1));
            int currentWeek = calendar.get(Calendar.MONTH);
            System.out.println("-----------------" + currentWeek);
            calendar.setTime(sdf.parse(time2));
            int paramWeek = calendar.get(Calendar.MONTH);
            System.out.println("-----------------" + paramWeek);
            if (paramWeek == currentWeek) {
                return true;
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return false;
    }


    private static String getDayOfBefore(String specifiedDay, SimpleDateFormat sdf) {
        try {
            Calendar c = Calendar.getInstance();
            Date date = sdf.parse(specifiedDay);
            c.setTime(date);
            int day = c.get(Calendar.DATE);
            c.set(Calendar.DATE, day - 1);
            return sdf.format(c.getTime());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }
}
