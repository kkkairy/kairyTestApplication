package com.example.mylibrary;

import android.util.Log;

import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 描述：字符串工具类（主要使用APACHE提供的字符串工具类）
 */
public class StringUtils {

    //日志TAG
    private static final String TAG = "StringUtils";

    /**
     * 去空 如果是NULL则返回空字符
     *
     * @param str
     * @return
     */
    public static String delNull(String str) {
        if (str == null) {
            str = "";
        }
        return str;
    }

    /*** 半角转换为全角
     *
     * @param input
     * @return
     */
    public static String toDBC(String input) {
        char[] c = input.toCharArray();
        for (int i = 0; i < c.length; i++) {
            if (c[i] == 12288) {
                c[i] = (char) 32;
                continue;
            }
            if (c[i] > 65280 && c[i] < 65375)
                c[i] = (char) (c[i] - 65248);
        }
        return new String(c);
    }

    /**
     * 把多行文本框里输入并保存到数据库的内容转换成HTML显示
     *
     * @param s
     * @return
     */
    public static String toHtml(String s) {
        s = delNull(s);
        s = Replace(s, "<", "&lt;");
        s = Replace(s, ">", "&gt;");
        s = Replace(s, "\t", "    ");
        s = Replace(s, "\r\n", "<br>");
        s = Replace(s, "\n", "<br>");
        s = Replace(s, " ", "&nbsp;");
        return s;
    }

    /**
     * 把多行文本框里输入并保存到数据库的内容转换成HTML显示
     *
     * @param source
     * @param oldString
     * @param newString
     * @return
     */
    public static String Replace(String source, String oldString, String newString) {
        if (source == null)
            return "";
        StringBuffer output = new StringBuffer();
        Integer lengOfSource = source.length();
        Integer lengOfOld = oldString.length();
        Integer posStart = 0;
        Integer pos;
        while ((pos = source.indexOf(oldString, posStart)) >= 0) {
            output.append(source.substring(posStart, pos));
            output.append(newString);
            posStart = pos + lengOfOld;
        }

        if (posStart < lengOfSource)
            output.append(source.substring(posStart));
        return output.toString();
    }

    /**
     * 字符串截取，超出部分添加省略号
     *
     * @param str
     * @param num
     * @return
     * @throws Exception
     */
    public static String cutString(String str, int num) {
        if (str != null && str.length() > num) {
            str = str.substring(0, num) + "..";
        }
        return str;
    }

    /**
     * 字符串str不足num位数的，在前面补齐"0"
     *
     * @param str 要处理的字符串
     * @param num 处理后的长度
     * @return
     */
    public static String addZero(String str, long num) {
        StringBuffer ss = new StringBuffer();
        while (ss.length() != num - str.length()) {
            ss.append("0");
        }
        ss.append(str);
        return ss.toString();
    }

    /**
     * 生成一个随机的字符串（手机验证码）
     *
     * @param numberFlag 是否只生成数字
     * @param length     生成字符串长度
     * @return
     */
    public static String createRandom(boolean numberFlag, int length) {
        String retStr = "";
        String strTable = numberFlag ? "1234567890" : "1234567890abcdefghijkmnpqrstuvwxyz";
        int len = strTable.length();
        boolean bDone = true;
        while (bDone) {
            retStr = "";
            int count = 0;
            for (int i = 0; i < length; i++) {
                double dblR = Math.random() * len;
                int intR = (int) Math.floor(dblR);
                char c = strTable.charAt(intR);
                if (('0' <= c) && (c <= '9')) {
                    count++;
                }
                retStr += strTable.charAt(intR);
            }
            if (count >= 2) {
                bDone = false;
            }
        }
        return retStr;
    }

    /**
     * 隐藏部分字符串（用于在网站上显示用户信息时隐藏中间部分）
     *
     * @param info   原字符串
     * @param before 开始显示位数
     * @param after  结尾显示位数
     * @return
     */
    public static String hideString(String info, int before, int after) {
        String str1 = info.substring(0, before);
        String str2 = info.substring(before, info.length() - after);
        String str3 = info.substring(info.length() - after, info.length());

        info = str1;
        for (int i = 0; i < str2.length(); i++) {
            info = info + "*";
        }
        info = info + str3;

        return info;
    }


    /**
     * 产生一个随机的中文字符串
     *
     * @param length  字符串长度
     * @param num     是否包含数字
     * @param english 是否包含英文
     * @param chara   是否包含中文
     * @return
     */
    public static String getChineseCharStr(int length, boolean num, boolean english, boolean chara) {
        //如果需要的字符串不包含数字、英文、汉字
        if (!num && !english && !chara) {
            return "Can you tell me what do you want?";
        }
        StringBuffer sb = new StringBuffer();
        String numDic = "1234567890";
        String engDic = "abcdefghijkmnpqrstuvwxyz";
        int hightPos, lowPos; // 定义高低位
        Random random = new Random();
        for (int i = 0; i < length; i++) {
            List<String> args = new ArrayList<String>();
            String number = numDic.charAt(new Random().nextInt(numDic.length())) + "";//生成一个数字
            String enCha = engDic.charAt(new Random().nextInt(engDic.length())) + "";//生成一个英文字符
            //生成一个中文
            hightPos = (176 + Math.abs(random.nextInt(39)));//获取高位值
            lowPos = (161 + Math.abs(random.nextInt(93)));//获取低位值
            byte[] b = new byte[2];
            b[0] = (new Integer(hightPos).byteValue());
            b[1] = (new Integer(lowPos).byteValue());
            String zhCha = "";
            try {
                zhCha = new String(b, "GBk");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            //放到List里面，然后产生随机数去取
            if (num) {
                args.add(number);
            }
            if (english) {
                args.add(enCha);
            }
            if (chara) {
                args.add(zhCha);
            }
            sb.append(args.get((int) Math.floor(Math.random() * args.size())));
        }
        return sb.toString();
    }


    /**
     * 判断字符串是否为空白（包括null和""）
     *
     * @param str
     * @return
     */
    public static boolean isBlank(String str) {
        return str == null || str.trim().length() == 0 || str.equals("");
    }

    /**
     * 判断是否为数字
     *
     * @param str
     * @return
     */
    public static boolean isNumeric(String str) {
        Pattern pattern = Pattern.compile("[0-9]*");
        Matcher isNum = pattern.matcher(str);
        if (!isNum.matches()) {
            return false;
        }
        return true;
    }

    /**
     * 判断是否为整数
     *
     * @param str
     * @return
     */
    public static boolean isInteger(String str) {
        Pattern pattern = Pattern.compile("^[-\\+]?[\\d]*$");
        return pattern.matcher(str).matches();
    }

    /**
     * 判断是否为浮点数，包括double和float
     *
     * @param str
     * @return
     */
    public static boolean isDouble(String str) {
        Pattern pattern = Pattern.compile("^[-\\+]?[.\\d]*$");
        return pattern.matcher(str).matches();
    }

    /**
     * 判断是不是一个合法的电子邮件地址
     *
     * @param email
     * @return
     */
    public static boolean isEmail(String email) {
        Pattern emailer = Pattern
                .compile("\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*");
        if (email == null || email.trim().length() == 0)
            return false;
        return emailer.matcher(email).matches();
    }

    /**
     * 判断是不是合法手机号码
     *
     * @param handset
     * @return
     */
    public static boolean isHandset(String handset) {
        if (!handset.substring(0, 1).equals("1")) {
            return false;
        }
        if (handset == null || handset.length() != 11) {
            return false;
        }
        String check = "^[0123456789]+$";
        Pattern regex = Pattern.compile(check);
        Matcher matcher = regex.matcher(handset);
        boolean isMatched = matcher.matches();
        if (isMatched) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 返回指定字节长度的字符串
     *
     * @param str    字符串
     * @param length 指定长度
     * @return
     */
    public static String toLength(String str, int length) {
        if (str == null) {
            return null;
        }
        if (length <= 0) {
            return "";
        }
        try {
            if (str.getBytes("GBK").length <= length) {
                return str;
            }
        } catch (Exception ex) {
        }
        StringBuffer buff = new StringBuffer();

        int index = 0;
        char c;
        length -= 3;
        while (length > 0) {
            c = str.charAt(index);
            if (c < 128) {
                length--;
            } else {
                length--;
                length--;
            }
            buff.append(c);
            index++;
        }
        buff.append("...");
        return buff.toString();
    }

    public final static SimpleDateFormat dateFormater = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINESE);

    public final static SimpleDateFormat dateFormater2 = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINESE);

    public final static SimpleDateFormat dateFormater3 = new SimpleDateFormat("HH:mm", Locale.CHINESE);

    /**
     * 将字符串转位日期类型
     *
     * @param sdate
     * @return
     */
    public static Date toDate(String sdate) {
        try {
            return dateFormater.parse(sdate);
        } catch (ParseException e) {
            return null;
        }
    }

    public static Date toDate2(String sdate) {
        try {
            return dateFormater2.parse(sdate);
        } catch (ParseException e) {
            return null;
        }
    }



    /**
     * 获取当前时间
     *
     * @return
     */
    public static String getNowDateStr() {
        return dateFormater.format(new Date());
    }

    /**
     * 获取当前时间
     *
     * @return
     */
    public static String getNowDateStr(SimpleDateFormat dateFormat) {
        return dateFormat.format(new Date());
    }

    /**
     * 根据日期时间格式获得日期时间字符串
     *
     * @param pattern
     * @param date
     * @return
     */
    public static String getDateStr(String pattern, Date date) {
        SimpleDateFormat dateFormater = new SimpleDateFormat(pattern, Locale.CHINESE);
        return dateFormater.format(date);
    }

    public static Date getDate(String pattern, String dateStr) {
        SimpleDateFormat dateFormater = new SimpleDateFormat(pattern, Locale.CHINESE);
        try {
            return dateFormater.parse(dateStr);
        } catch (ParseException e) {
            Log.e(TAG, "parse date  fail", e);
        }
        return new Date();
    }

    /**
     * 以友好的方式显示时间
     *
     * @param sdate
     * @return
     */
    public static String friendlyTime(String sdate) {
        try {
            Date time = toDate(sdate);                //按yyyy-MM-dd HH:mm:ss格式解析时间
            Date time1 = toDate2(sdate);
            if (time == null) {
                return "Unknown";
            }
            String ftime = "";
            Calendar cal = Calendar.getInstance();

            // 判断是否是同一天
            String curDate = dateFormater2.format(cal.getTime());
            String paramDate = dateFormater2.format(time1);
            if (curDate.equals(paramDate)) {
                int hour = (int) ((cal.getTimeInMillis() - time.getTime()) / 3600000);
                if (hour == 0)
                    ftime = Math.max((cal.getTimeInMillis() - time.getTime()) / 60000, 1) + "分钟前";
                else
                    ftime = hour + "小时前";
                return ftime;
            }

            long lt = time.getTime() / 86400000;
            long ct = cal.getTimeInMillis() / 86400000;
            int days = (int) (ct - lt);
            if (days == 0) {
                int hour = (int) ((cal.getTimeInMillis() - time.getTime()) / 3600000);
                if (hour == 0)
                    ftime = Math.max((cal.getTimeInMillis() - time.getTime()) / 60000, 1) + "分钟前";
                else
                    ftime = hour + "小时前";
            } else if (days == 1) {
                ftime = "昨天";
            } else if (days == 2) {
                ftime = "前天";
            } else if (days > 2 && days <= 10) {
                ftime = days + "天前";
            } else if (days > 10) {
                ftime = dateFormater2.format(time);
            } else if (days == -1) {
                ftime = "明天";
            } else if (days == -2) {
                ftime = "后天";
            } else if (days < -2) {
                ftime = -days + "天后";
            }
            return ftime;
        } catch (Exception e) {
            return sdate;
        }
    }

    /**
     * 以友好的方式显示时间
     *
     * @param sdate
     * @return
     */
    public static String dynamicTime(String sdate) {
        try {
            Date time = toDate(sdate);                //按yyyy-MM-dd HH:mm:ss格式解析时间

            String ftime = "";
            Calendar cal = Calendar.getInstance();

            long lt = time.getTime() / 86400000;
            long ct = cal.getTimeInMillis() / 86400000;
            int days = (int) (ct - lt);
            if (days == 0) {
                if ((int) ((cal.getTimeInMillis() - time.getTime()) - 60000 * 5) < 0) {
                    ftime =  "刚刚";
                } else {
                    ftime =  dateFormater3.format(time);
                }
            } else if (days == 1) {
                ftime = "昨天";
            } else {
                ftime = dateFormater2.format(time);
            }
            return ftime;
        } catch (Exception e) {
            return sdate;
        }
    }

    /**
     * @param sdate
     * @return
     */
    public static String friendlyDate(String sdate) {
        String showDetail = sdate;
        Date time = toDate2(sdate);
        if (time == null) {
            return "Unknown";
        }
        String ftime = "";
        Calendar cal = Calendar.getInstance();

        // 判断是否是同一天
        String curDate = dateFormater2.format(cal.getTime());
        String paramDate = dateFormater2.format(time);
        Log.i("curDate1", curDate);
        Log.i("curDate1", paramDate);

        if (curDate.equals(paramDate)) {
            ftime = "今天";
            return ftime;
        }

        long lt = time.getTime() / 86400000;
        long ct = cal.getTimeInMillis() / 86400000;
        int days = (int) (ct - lt - 1);
        if (days == 0) {
            ftime = "今天";
        }
        /*else if (days == 1) {
            ftime = "昨天";
		} else if (days == 2) {
			ftime = "前天";
		} */
        else if (days > 0 && days <= 10) {
            ftime = days + "天前";
        } else if (days > 10) {
            ftime = showDetail;
        }
        /*else if(days == -1){
			ftime = "明天";
		} else if(days == -2){
			ftime = "后天";
		} */
        else if (days < 0 && days >= -10) {
            ftime = -days + "天后";
        } else if (days < -10) {
            ftime = showDetail;
        }
        return ftime;
    }

    //自定义格式化输出函数  小数点后最后一位为0则移除
    public static String fmt_prt_double(String num) {
        int i;
        if (num.length() > 1 && num.contains(".")) {
            for (i = num.length() - 1; i >= 0; i--) //从串尾向前检查，遇到非0数据结束循环
            {
                if (num.charAt(i) == '.') //遇到小数点结束，说明是个整数
                    break;
                if (num.charAt(i) != '0') //遇到小数中有非0值，结束
                {
                    i++;
                    break;
                }
            }
            return num.substring(0, i); //返回处理后的子串
        } else {
            return num;
        }
    }
}
