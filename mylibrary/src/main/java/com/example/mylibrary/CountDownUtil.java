package com.example.mylibrary;

import android.os.CountDownTimer;
import android.util.Log;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by zb on 2019/8/14.
 * 页面功能: 定时器倒计时
 */

public class CountDownUtil {
    private CountDownTimer mTimer;

    private int interval = 1000;

    public CountDownUtil() {
    }


    /**
     * 开始倒计时
     *
     * @param startTime      开始时间（时间戳）
     * @param minuteInterval 时间间隔（单位：分）
     * @param callBack
     */
    public void start(long startTime, int minuteInterval, OnCountDownCallBack callBack) {
        long lengthTime = minuteInterval * 60 * interval;
        //查看是否为毫秒的时间戳
        boolean isMillSecond = (String.valueOf(startTime).length() == 13);
        startTime = startTime * (isMillSecond ? 1 : interval);
        long endTime = startTime + lengthTime;
        long curTime = System.currentTimeMillis();
        mTimer = getTimer(endTime - curTime, interval, callBack);
        if (Math.abs(curTime - startTime) > lengthTime) {
            if (callBack != null) {
                callBack.onFinish();
            }
        } else {
            mTimer.start();
        }
    }

    private CountDownTimer getTimer(long millisInFuture, final long interval, final OnCountDownCallBack callBack) {
        return new CountDownTimer(millisInFuture, interval) {
            @Override
            public void onTick(long millisUntilFinished) {
                int day = 0;
                int hour = 0;
                int minute = (int) (millisUntilFinished / interval / 60);
                int second = (int) (millisUntilFinished / interval % 60);
                if (minute > 60) {
                    hour = minute / 60;
                    minute = minute % 60;
                }
                if (hour > 24) {
                    day = hour / 24;
                    hour = hour % 24;
                }
                if (callBack != null) {
                    callBack.onProcess(day, hour, minute, second);
                }
            }

            @Override
            public void onFinish() {
                if (callBack != null) {
                    callBack.onFinish();
                }
            }
        };
    }

    /**
     * 开始倒计时
     *
     * @param endTime  结束时间（时间戳）
     * @param callBack
     */
    public void start(long endTime, OnCountDownCallBack callBack) {
//        long curTime = System.currentTimeMillis();
        long curTime = Long.parseLong(getTimeStamp());
        long startTime = endTime - curTime;
        Log.e("aa", "//////" + endTime + "-" + curTime + "=" + startTime);
        if (startTime < 0) {
            return;
        }
        mTimer = getTimer(startTime, interval, callBack);
        if (endTime < curTime) {
            if (callBack != null) {
                callBack.onFinish();
            }
        } else {
            mTimer.start();
        }
    }

    /**
     * 开始倒计时
     *
     * @param curTime  当前时间（时间戳）
     * @param endTime  结束时间（时间戳）
     * @param callBack
     */
    public void start(long curTime, long endTime, OnCountDownCallBack callBack) {
        mTimer = getTimer(endTime - curTime, interval, callBack);
        if (endTime < curTime) {
            if (callBack != null) {
                callBack.onFinish();
            }
        } else {
            mTimer.start();
        }
    }

    /**
     * 必用
     */
    public void onDestroy() {
        if (mTimer != null) {
            mTimer.cancel();
            mTimer = null;
        }
    }

    /**
     * 自定义倒计时
     * seconds 秒
     */
    public void onStartTime(int seconds, OnCountDownCallBack callBack) {
        long endTime = System.currentTimeMillis() + (seconds * 1000);
        long curTime = System.currentTimeMillis();
        if ((endTime - curTime) < 0) {
            return;
        }
        mTimer = getTimer(endTime - curTime, interval, callBack);
        if (endTime < curTime) {
            if (callBack != null) {
                callBack.onFinish();
            }
        } else {
            mTimer.start();
        }
    }

    /**
     * 自定义倒计时
     * seconds 毫秒
     */
    public void onStartTimeSeconds(long seconds, OnCountDownCallBack callBack) {
        long endTime = System.currentTimeMillis() + (seconds);
        long curTime = System.currentTimeMillis();
        if ((endTime - curTime) < 0) {
            return;
        }
        mTimer = getTimer(endTime - curTime, interval, callBack);
        if (endTime < curTime) {
            if (callBack != null) {
                callBack.onFinish();
            }
        } else {
            mTimer.start();
        }
    }

    public interface OnCountDownCallBack {

        void onProcess(int day, int hour, int minute, int second);

        void onFinish();
    }

    public void startStEd(String startTime, String endTime, OnCountDownCallBack callBack) {
        startSt(startTime, endTime, callBack);
    }
    public void startSt(String endTime, OnCountDownCallBack callBack) {
        startSt("", endTime, callBack);
    }

    /**
     * 开始倒计时
     * @param endTime  结束时间（时间戳）
     * @param startTime  开始时间（时间戳）
     * @param callBack
     */
    public void startSt(String startTime, String endTime, OnCountDownCallBack callBack) {
        long end = TimeUtils.backTimeLong(endTime);
        long cur = isNullOrEmpty(startTime) ? System.currentTimeMillis() : TimeUtils.backTimeLong(startTime);
        mTimer = getTimer(end - cur, interval, callBack);
        if (end < cur) {
            if (callBack != null) {
                callBack.onFinish();
            }
        } else {
            mTimer.start();
        }
    }

    /**
     * 直接获取时间戳
     */
    public static String getTimeStamp() {
        String currentDate = getCurrentDate();
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date();
        try {
            date = sf.parse(currentDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return String.valueOf(date.getTime());
    }

    /**
     * 获取系统时间
     */
    public static String getCurrentDate() {
        Date d = new Date();
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sf.format(d);
    }
    public static boolean isNotNullOrEmpty(String s) {
        return !isNullOrEmpty(s);
    }

    public static boolean isNullOrEmpty(String s) {
        return s == null || s.isEmpty();
    }

    public static String judgeNull(String s) {
        return judgeNull(s, "");
    }

    public static String judgeNull(String s, String defaultStr) {
        return isNullOrEmpty(s) ? defaultStr : s;
    }
}
