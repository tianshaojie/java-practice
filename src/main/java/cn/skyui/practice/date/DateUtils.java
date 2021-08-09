package cn.skyui.practice.date;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtils {

    public static void main(String[] args) {
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
//
//        String date1="20180709";//日期字符串
//        String date2="20180708";
//        boolean flag = isSameWeek(date1, date2, sdf);
//        System.out.println(flag?"在同一周内":"不在同一周内");
//
//
//        String date11="20180701";//日期字符串
//        String date22="20180630";
//        boolean flag1 = isSameMonth(date11, date22, sdf);
//        System.out.println(flag1?"在同一月内":"不在同一月内");
//
//
//        System.out.println(getDayOfBefore("20180101", sdf));

        printMonthDay(2021,7);
    }


    //打印月份主体
    public static void printMonthDay(int year,int month){
        int numberOfDaysInMonth=getNumberOfDaysInMonth(year, month);
        for(int i=numberOfDaysInMonth;i>0;i--){
            String day = year + zerofill(month) + zerofill(i);
            String dayTime930 = day + " 09:30:00";
            String dayTime1000 = day + " 10:00:00";
            System.out.println(dayTime930 + " = " + getTimestamp(dayTime930));
            System.out.println(dayTime1000 + " = " + getTimestamp(dayTime1000));

            System.out.println();
        }
    }

    private static String zerofill(int monthOrDay) {
        return monthOrDay < 10 ? "0" + String.valueOf(monthOrDay) : String.valueOf(monthOrDay);
    }

    public static long getTimestamp(String time) {
        try {
            Date date = new SimpleDateFormat("yyyyMMdd HH:mm:ss").parse(time);
            long unixTimestamp = date.getTime();
            return unixTimestamp;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0;
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



    private static void printMonth(int year, int month) {
        printMonthTitle(year, month);
        printMonthBody(year, month);
    }

    public static void printMonthTitle(int year, int month){
        System.out.println("        "+getMonthName(month)+" "+year);
        System.out.println("-------------------------");
        System.out.println(" Sun Mont Tue Wed Thu Fri Sat");

    }

    //打印月份主体
    public static void printMonthBody(int year,int month){
        int startDay=getStartDay(year, month);
        int numberOfDaysInMonth=getNumberOfDaysInMonth(year, month);
        for(int i=0;i<startDay;i++)
            System.out.print("    ");
        for(int i=1;i<=numberOfDaysInMonth;i++){
            System.out.printf("%4d",i);
            if((i+startDay)%7==0)
                System.out.println();
        }
        System.out.println();
    }

    public static String getMonthName(int month){
        String  monthName="";
        switch(month){
            case 1: monthName="January";break;
            case 2: monthName="February";break;
            case 3: monthName="March";break;
            case 4: monthName="April";break;
            case 5: monthName="May";break;
            case 6: monthName="June";break;
            case 7: monthName="July";break;
            case 8: monthName="August";break;
            case 9: monthName="September";break;
            case 10: monthName="October";break;
            case 11: monthName="Nobember";break;
            case 12: monthName="December";break;
        }
        return monthName;
    }
    //获取指定月份的第一天是周几
    public static  int getStartDay(int year, int month){
        final int START_DAY_FOR_JAN_1_1800=8;
        int totalNumberOfDays=getTotalNumberOfDays(year,month);

        return (totalNumberOfDays+START_DAY_FOR_JAN_1_1800)%7;
    }
    //返回总共的天数，从1800年1月1日开始计算,直到指定月份1日的前一天
    public static int getTotalNumberOfDays(int year,int month){
        int total=0;
        for(int i=1800;i<year;i++)
            if(isLeapYear(i))
                total=total+366;
            else
                total=total+365;

        for(int i=1;i<month;i++)
            total=total+getNumberOfDaysInMonth(year,i);

        return total;
    }

    //返回指定月份的天数
    public static int getNumberOfDaysInMonth(int year,int month){
        int result=0;
        switch (month){
            case 1:
            case 3:
            case 5:
            case 7:
            case 8:
            case 10:
            case 12:
                result=31;
                break;
            case 4:
            case 6:
            case 9:
            case 11:
                result=30;
                break;
            case 2:
                result=isLeapYear(year)?29:28;
                break;
        }
        return result;
    }

    //判断是否为闰年
    public static boolean isLeapYear(int year){
        return (year%400==0)||(year%4==0&&year%100!=0);
    }
}
