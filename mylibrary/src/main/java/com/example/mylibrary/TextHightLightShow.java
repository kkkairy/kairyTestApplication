package com.example.mylibrary;

import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by zb on 2018/5/4.
 */

public class TextHightLightShow {
    /**
     * @param color   关键字颜色
     * @param text    文本
     * @param keyword 多个关键字
     * @return
     */
    public static SpannableString getHighLightKeyWord(int color, String text, String[] keyword) {
        SpannableString s = new SpannableString(text);
        for (int i = 0; i < keyword.length; i++) {
            Pattern p = Pattern.compile(keyword[i]);
            Matcher m = p.matcher(s);
            while (m.find()) {
                int start = m.start();
                int end = m.end();
                s.setSpan(new ForegroundColorSpan(color), start, end,
                        Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            }
        }
        return s;
    }

    /**
     * @param color   关键字颜色
     * @param text    文本
     * @param keyword 单个关键字
     * @return
     */
    public static SpannableString getLightSingleKeyWord(int color, String text, String keyword) {
        SpannableString s = new SpannableString(text);
        Pattern p = Pattern.compile(keyword);
        Matcher m = p.matcher(s);
        int start = m.start();
        int end = m.end();
        s.setSpan(new ForegroundColorSpan(color), start, end,
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        return s;

    }

    /**
     * 将textview中的字符全角化。即将所有的数字、字母及标点全部转为全角字符，使它们与汉字同占两个字节，
     * 这样就可以避免由于占位导致的排版混乱问题了。
     *
     * @param input
     * @return
     */
    public static String ToDBC(String input) {
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
     * @param color 关键字颜色
     * @param text  文本
     * @return
     */
    public static SpannableString getHighLightComment(int color, String text, String nickName, String pNickName) {
        SpannableString s = new SpannableString(text);

        int nickLastIndex = nickName.length();
        int pNickFirstIndex = text.indexOf(pNickName);
        int pNickLastIndex = pNickName.length() + pNickFirstIndex;

        s.setSpan(new ForegroundColorSpan(color), 0, nickLastIndex,
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        s.setSpan(new ForegroundColorSpan(color), pNickFirstIndex, pNickLastIndex,
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        return s;
    }


}
