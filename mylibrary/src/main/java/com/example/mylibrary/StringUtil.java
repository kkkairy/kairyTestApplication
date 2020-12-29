package com.example.mylibrary;

import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.widget.EditText;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Lz on 2017/10/24.
 */

public class StringUtil {

    /**
     * 检测是否有emoji表情
     *
     * @param source
     * @return
     */
    public static boolean containsEmoji(String source) {
        int len = source.length();
        for (int i = 0; i < len; i++) {
            char codePoint = source.charAt(i);
            if (!isEmojiCharacter(codePoint)) { //如果不能匹配,则该字符是Emoji表情
                return true;
            }
        }
        return false;
    }

    /**
     * 判断是否是Emoji
     *
     * @param codePoint 比较的单个字符
     * @return
     */
    private static boolean isEmojiCharacter(char codePoint) {
        return (codePoint == 0x0) || (codePoint == 0x9) || (codePoint == 0xA) ||
                (codePoint == 0xD) || ((codePoint >= 0x20) && (codePoint <= 0xD7FF)) ||
                ((codePoint >= 0xE000) && (codePoint <= 0xFFFD)) || ((codePoint >= 0x10000)
                && (codePoint <= 0x10FFFF));
    }

    // 判断当前电话号码是否有效，注意默认账号为电话号码，如不是，请从新添加判断
    public static boolean isPhoneNumValid(String phoneNum) {
        Pattern p = Pattern.compile("^((13[0-9])|(14[0-9])|(15[0-9])|(18[0-9])|(17[0-9])|(19[0-9]))\\d{8}$");
        Matcher m = p.matcher(phoneNum);
        return m.matches();
    }

    //判断密码长度是否符合
    public static boolean isPasswordValid(String password) {
        return password.length() < 6 || password.length() > 18;
    }

    //输入是否为空（默认开头不能是空格字符）
    public static boolean isEmpty(String string) {
        if (string == null) return true;
        String str = string.trim();
        return TextUtils.isEmpty(str);
    }

    //输入是否为空（默认开头不能是空格字符）
    public static boolean isEmpty1(String string) {
        if (string == null) return true;
        if (string.equals("")) return true;
        String str = string.trim();
        return TextUtils.isEmpty(str);
    }

    //输入是否为空（默认开头不能是空格字符）
    public static String isEmptys(Object string) {
        if (string == null || string.equals("")) return "";
        return string.toString();
    }

    //检测是否是身份证
    public static boolean isIdCard(String str) {
        Pattern p = Pattern.compile("^[1-9]\\d{16}[\\dxX]$");
        Matcher m = p.matcher(str);
        return m.matches();
    }

    /**
     * 日期转星期
     *
     * @param datetime
     * @return
     */
    public static String dateToWeek(String datetime) {
        SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd");
        String[] weekDays = {"星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"};
        Calendar cal = Calendar.getInstance(); // 获得一个日历
        Date datet = null;
        try {
            datet = f.parse(datetime);
            cal.setTime(datet);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        int w = cal.get(Calendar.DAY_OF_WEEK) - 1; // 指示一个星期中的某天。
        if (w < 0)
            w = 0;
        return weekDays[w];
    }

    /**
     * 判断时间是不是今天
     *
     * @param date
     * @return 是返回true，不是返回false
     */
    public static boolean isNow(String date) {
        //当前时间
        Date now = new Date();
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
        //获取今天的日期
        String nowDay = sf.format(now);


        //对比的时间
        String day = date;

        return day.equals(nowDay);
    }

    /**
     * 提供精确的小数位四舍五入处理
     *
     * @param v     需要四舍五入的数字
     * @param scale 小数点后保留几位
     * @return 四舍五入后的结果
     */
    public static String round(String v, int scale) {
        if (scale < 0) {
            throw new IllegalArgumentException(
                    "The scale must be a positive integer or zero");
        }
        if (isContainChinese(v)) {
            return "0.00";
        }
        BigDecimal b = new BigDecimal(v);
        return b.setScale(scale, BigDecimal.ROUND_HALF_UP).toString();
    }


    /**
     * 判断字符串中是否包含中文
     *
     * @param str 待校验字符串
     * @return 是否为中文
     * @warn 不能校验是否为中文标点符号
     */
    public static boolean isContainChinese(String str) {
        Pattern p = Pattern.compile("[\u4e00-\u9fa5]");
        Matcher m = p.matcher(str);
        if (m.find()) {
            return true;
        }
        return false;
    }

    /**
     * 设置editview只能输入几位小数
     *
     * @param editText       被设置的editview
     * @param DECIMAL_DIGITS 只准输入几位小数
     */
    public static void setPoint(final EditText editText, final int DECIMAL_DIGITS) {
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().contains(".")) {
                    if (s.length() - 1 - s.toString().indexOf(".") > DECIMAL_DIGITS) {
                        s = s.toString().subSequence(0,
                                s.toString().indexOf(".") + DECIMAL_DIGITS + 1);
                        editText.setText(s);
                        editText.setSelection(s.length());
                    }
                }
                if (s.toString().trim().substring(0).equals(".")) {
                    s = "0" + s;
                    editText.setText(s);
                    editText.setSelection(2);
                }
                if (s.toString().startsWith("0")
                        && s.toString().trim().length() > 1) {
                    if (!s.toString().substring(1, 2).equals(".")) {
                        editText.setText(s.subSequence(0, 1));
                        editText.setSelection(1);
                        return;
                    }
                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }

    /**
     * 6      * 时间戳转换成日期格式字符串
     * 7      * @param seconds 精确到秒的字符串
     * 8      * @param formatStr
     * 9      * @return
     * 10
     */
    public static String timeStamp2Date(String seconds, String format) {
        if (seconds == null || seconds.isEmpty() || seconds.equals("null")) {
            return "";
        }
        if (format == null || format.isEmpty()) {
            format = "yyyy-MM-dd HH:mm:ss";
        }
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(new Date(Long.valueOf(seconds + "000")));
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


    public static boolean isNumeric(String str) {
        for (int i = str.length(); --i >= 0; ) {
            if (!Character.isDigit(str.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    public static String formatBigWanNum(String num) {
        if (isEmpty(num)) {
            return "0";
        }
        BigDecimal numBig = new BigDecimal(num);
        BigDecimal afterNum = new BigDecimal(10000);
        if (numBig.compareTo(afterNum) > 0) {
            BigDecimal divide = numBig.divide(afterNum, 1, BigDecimal.ROUND_UP);
            return divide.toPlainString();
        }
        return num;
    }

    public static String makeQanNum(int num) {
        //第一个参数是除数，第二个参数代表保留几位小数，第三个代表的是使用的模式。
        return new BigDecimal(num).divide(new BigDecimal(1000), 1, BigDecimal.ROUND_HALF_UP).toPlainString();//四舍五入
    }

    /**
     * 距离格式化数字
     */
    public static String distanceformatBigNum(String distance) {
        if(TextUtils.isEmpty(distance)){
            return "";
        }
        if (new BigDecimal(distance).compareTo(new BigDecimal("1000")) < 0) {
            return distance + "m";
        } else {
            return new BigDecimal(distance).divide(new BigDecimal("1000")).setScale(2, RoundingMode.DOWN).toPlainString() + "km";
        }
    }

    /**
     * 格式化上万的数字
     *
     * @param num
     * @return
     */
    public static String formatBigNum(String num) {
        if (isEmpty(num)) {
            // 数据为空直接返回0
            return "0";
        }
        try {
            StringBuilder sb = new StringBuilder();
            if (!isNumeric(num)) {
                // 如果数据不是数字则直接返回0
                return "0";
            }


            BigDecimal b0 = new BigDecimal("1000");
            BigDecimal b1 = new BigDecimal("10000");
            BigDecimal b2 = new BigDecimal("100000000");
            BigDecimal b3 = new BigDecimal(num);

            String formatedNum = "";//输出结果
            String unit = "";//单位

            if (b3.compareTo(b0) < 0) {
                sb.append(b3.toString());
            } else if ((b3.compareTo(b0) == 0 || b3.compareTo(b0) > 0)
                    && b3.compareTo(b1) < 0) {
                formatedNum = b3.divide(b0, BigDecimal.ROUND_DOWN,1).toString();
                unit = "k";
//                sb.append(b3.toString());
            } else if ((b3.compareTo(b1) == 0 && b3.compareTo(b1) > 0)
                    || b3.compareTo(b2) < 0) {
                formatedNum = b3.divide(b1, BigDecimal.ROUND_DOWN,1).toString();
                unit = "w";
            } else if (b3.compareTo(b2) == 0 || b3.compareTo(b2) > 0) {
                formatedNum = b3.divide(b2, BigDecimal.ROUND_DOWN,1).toString();
                unit = "亿";
            }
//            if (!"".equals(formatedNum)) {
//                int i = formatedNum.indexOf(".");
//                if (i == -1) {
//                    sb.append(formatedNum).append(unit);
//                } else {
//                    i = i + 1;
//                    String v = formatedNum.substring(i, i + 1);
//                    if (!v.equals("0")) {
//                        sb.append(formatedNum.substring(0, i + 1)).append(unit);
//                    } else {
//                        sb.append(formatedNum.substring(0, i - 1)).append(unit);
//                    }
//                }
//            }

            if (!formatedNum.isEmpty())
                sb.append(new BigDecimal(formatedNum).setScale(1, BigDecimal.ROUND_UP).stripTrailingZeros().toString()).append(unit);

            if (sb.length() == 0)
                return "0";
            return sb.toString();

        } catch (Exception e) {
            e.printStackTrace();
            return num;
        }
    }

    /**
     * 获取整数
     */
    public static String getIntgerData(String num) {

        if (isEmpty(num))
            return "";

        if (!num.contains(".")) {
            return num;
        } else {
            num = num.substring(0, num.indexOf("."));
            return num;
        }
    }

    /**
     * 获取浮点数
     */
    public static String getDouleData(String num) {

        if (isEmpty(num))
            return "";

        if (!num.contains(".")) {
            return "";
        } else {
            num = num.substring(num.indexOf(".") + 1);
            return num;
        }
    }

    /**
     * 分秒
     *
     * @param time ms
     */
    public static String fromMMss(long time) {
        if (time < 0) {
            return "00:00";
        }

        int ss = (int) (time / 1000);
        int mm = ss / 60;
        int s = ss % 60;
        int m = mm % 60;
        String strM = String.valueOf(m);
        String strS = String.valueOf(s);
        if (m < 10) {
            strM = "0" + strM;
        }
        if (s < 10) {
            strS = "0" + strS;
        }
        return strM + ":" + strS;
    }

    /**
     * 拼接构造字符串
     *
     * @param str
     * @return
     */
    public static String buildingMoreStr(Object... str) {
        StringBuffer sb = new StringBuffer();
        for (Object string : str) {
            sb.append(string);
        }
        return sb.toString();
    }

}
