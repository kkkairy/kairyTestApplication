package com.example.mylibrary;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class DateUtil {

    public static final TimeZone PHONE_SYS_ZONE = TimeZone.getDefault();
    private static final long ONE_MINUTE = 60;
    private static final long ONE_HOUR = 3600;
    private static final long ONE_DAY = 86400;
    private static final long ONE_MONTH = 2592000;
    private static final long ONE_YEAR = 31104000;
    public static final String YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";
    public static final String YYYY_MM_DD_HH_MM = "yyyy-MM-dd HH:mm";
    public static final String YYYY_MM_DD = "yyyy-MM-dd";
    public static final String YYYY_MM = "yyyy-MM";
    public static final String CHN_YYYY_MM_DD = "yyyy年MM月dd日";
    public static final String CHN_YYYY_MM = "yyyy年MM月";
    public static final String HH_MM_SS = "HH:mm:ss";
    public static final String HH_MM = "HH:mm";
    public static final String MM_DD = "MM-dd";
    public static final String CN_MM_DD = "MM月dd日";
    public static final String CN_MM_DD_HH_MM = "MM月dd日 HH:mm";
    public static final String POINT_MM_DD_HH_MM = "MM.dd HH:mm";


    public static String getSimpleTime(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);

        long time = date.getTime() / 1000;
        long now = new Date().getTime() / 1000;
        long ago = now - time;
        if (ago < 0) {
            return "刚刚";
        }
        if (ago <= ONE_HOUR) {
            long l = ago / ONE_MINUTE;
            if (l == 0) {
                return "刚刚";
            } else {
                return l + "分钟前";
            }

        } else if (ago <= ONE_DAY)
            return ago / ONE_HOUR + "小时前";
        else if (ago <= ONE_DAY * 2)
            return "昨天";
        else if (ago <= ONE_DAY * 3)
            return "前天";
        else if (ago <= ONE_MONTH) {
            long day = ago / ONE_DAY;
            return day + "天前";
        } else if (ago <= ONE_YEAR) {
            long month = ago / ONE_MONTH;
            long day = ago % ONE_MONTH / ONE_DAY;
            return month + "个月前";
        } else {
            long year = ago / ONE_YEAR;
            int month = calendar.get(Calendar.MONTH) + 1;// JANUARY which is 0 so month+1
            return year + "年前";
        }

    }


    /**
     * 日期格式化
     *
     * @param date 需要格式化的日期
     * @return 返回格式化后的时间字符串
     */
    public static String format(Date date) {
        return format(date, "yyyy-MM-dd");
    }

    public static String format(long time) {
        return format(new Date(time), "yyyy-MM-dd HH:mm:ss");
    }

    /**
     * 日期格式化
     *
     * @param date    需要格式化的日期
     * @param pattern 时间格式，如：yyyy-MM-dd HH:mm:ss
     * @return 返回格式化后的时间字符串
     */
    public static String format(Date date, String pattern) {
        if (date == null) {
            return "";
        }
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        return sdf.format(date);
    }

    /**
     * 日期格式化
     *
     * @param calendar 需要格式化的日期
     * @param pattern  时间格式，如：yyyy-MM-dd HH:mm:ss
     * @return 返回格式化后的时间字符串
     */
    public static String format(Calendar calendar, String pattern) {
        if (calendar == null) {
            return "";
        }
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        return sdf.format(calendar.getTime());
    }

    /**
     * 日期格式化
     *
     * @param date    需要格式化的日期
     * @param pattern 时间格式，如：yyyy-MM-dd HH:mm:ss
     * @return 返回格式化后的时间对象
     */
    public static Date format(String date, String pattern) {
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        try {
            return sdf.parse(date);
        } catch (Exception e) {
            e.printStackTrace();
            return new Date();
        }
    }

    /**
     * 日期格式化
     *
     * @param date    需要格式化的日期时间戳
     * @param pattern 时间格式，如：yyyy-MM-dd HH:mm:ss
     * @return 返回格式化后的时间字符串
     */
    public static String format(long date, String pattern) {
        return format(new Date(date), pattern);
    }

    /**
     * 日期格式化
     *
     * @param time 需要格式化的日期时间戳
     * @return 返回格式化后的时间字符串
     */
    public static String formatNew(long time) {
        SimpleDateFormat format = new SimpleDateFormat(CN_MM_DD);
        return format.format(time);
    }

    /*
     * 将时间戳转换为时间
     */
    public static String stampToDate(long l) {
        String res;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        long lt = l;
        Date date = new Date(lt);
        res = simpleDateFormat.format(date);
        return res;
    }

    /*
     * 将时间转换为时间戳
     */
    public static long dateToStamp(String s) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = null;
        try {
            date = simpleDateFormat.parse(s);
            return date.getTime();
        } catch (java.text.ParseException e) {
            e.printStackTrace();
            return -1;
        }
    }

    /*
     * 将时间转换为时间戳
     */
    public static long dateToStamp(String s, String pattern) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        Date date = null;
        try {
            if (s != null) {
                date = simpleDateFormat.parse(s);
                return date.getTime();
            }
            return System.currentTimeMillis();
        } catch (java.text.ParseException e) {
            e.printStackTrace();
            return -1;
        }
    }

    /*
     * 将时间转换为Date
     */
    public static Date datesToStamp(String s) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINESE);
        Date date = null;
        try {
            date = simpleDateFormat.parse(s);
            return date;
        } catch (java.text.ParseException e) {
            e.printStackTrace();
            return new Date();
        }

    }


    /*
     * 将时间转换为时间戳
     */
    public static long dateToStamp(Date date, String pattern) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        try {
            String str = simpleDateFormat.format(date);
            return simpleDateFormat.parse(str).getTime();
        } catch (java.text.ParseException e) {
            e.printStackTrace();
            return -1;
        }
    }

    public static String getCnWeek(int i) {
        String weekStr[] = {"", "星期天", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"};
        return weekStr[i];
    }

    /**
     * 文字只有昨天，今天
     * 其余用 12-06  12-11显示
     * 跨年用2017-11-06显示
     *
     * @param date
     * @return
     */
    public static String getDateDesc(Date date) {
        Calendar calendar = Calendar.getInstance(PHONE_SYS_ZONE);
        calendar.setTime(date);
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        Calendar calendarNow = Calendar.getInstance(PHONE_SYS_ZONE);
        int yearNow = calendarNow.get(Calendar.YEAR);
        int monthNow = calendarNow.get(Calendar.MONTH);
        int dayNow = calendarNow.get(Calendar.DAY_OF_MONTH);

        if (year == yearNow && month == monthNow && day == dayNow) {//今天
            return formatBySysZone(date, "今天 HH:mm");
        } else if (year == yearNow && month == monthNow && day == (dayNow - 1)) {//昨天
            return formatBySysZone(date, "昨天 HH:mm");
        } else if (year == yearNow) {//今年
            return formatBySysZone(date, "MM-dd HH:mm");
        } else {
            return formatBySysZone(date, "yyyy-MM-dd HH:mm");
        }
    }

    public static String longToTime(long time) {
        String timeStr = null;
        int hour = 0;
        int minute = 0;
        int second = 0;
        int newTime = (int) (time / 1000);
        if (newTime <= 0)
            return "00:00";
        else {
            minute = newTime / 60;
            if (minute < 60) {
                second = newTime % 60;
                timeStr = unitFormat(minute) + ":" + unitFormat(second);
            } else {
                hour = minute / 60;
                if (hour > 99)
                    return "99:59:59";
                minute = minute % 60;
                second = newTime - hour * 3600 - minute * 60;
                timeStr = unitFormat(hour) + ":" + unitFormat(minute) + ":" + unitFormat(second);
            }
        }
        return timeStr;
    }

    public static String unitFormat(int i) {
        String retStr = null;
        if (i >= 0 && i < 10)
            retStr = "0" + Integer.toString(i);
        else
            retStr = "" + i;
        return retStr;
    }

    /**
     * 跟随系统时区
     */
    public static String formatBySysZone(Date date, String pattern) {
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        sdf.setTimeZone(PHONE_SYS_ZONE);
        return sdf.format(date);
    }
    /**
     * 输入"2013-3-6 14:41:33";
     */
    public static Date formatDate(String time) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = null;
        try {
            date = format.parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }
}