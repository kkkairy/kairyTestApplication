package com.example.mylibrary;

import android.content.Context;
import android.content.SharedPreferences;


/**
 * 描述：SharedPreferences工具类
 */
public class SharePreferencesUtils {
    private SharedPreferences sp;
    private SharedPreferences.Editor editor;
    private static SharePreferencesUtils sharePreferencesUtils;
    //表名
    private static String SP_NAME = "cssSp";


    //单例sp操作对象
    public static SharePreferencesUtils getInstance(Context context) {
        if (sharePreferencesUtils == null)
            sharePreferencesUtils = new SharePreferencesUtils(context, SP_NAME);
        return sharePreferencesUtils;
    }

    /**
     * 构造方法，需要一个CONTEXT对象
     *
     * @param context
     * @param fileName
     */
    private SharePreferencesUtils(Context context, String fileName) {
        sp = context.getSharedPreferences(fileName, Context.MODE_PRIVATE);
        editor = sp.edit();
    }


    public boolean getBoolean(String key, boolean defValue) {
        return sp.getBoolean(key, defValue);
    }


    public float getFloat(String key, float defValue) {
        return sp.getFloat(key, defValue);
    }


    public int getInt(String key, int defValue) {
        return sp.getInt(key, defValue);
    }

    public long getLong(String key, long defValue) {
        return sp.getLong(key, defValue);
    }


    public String getString(String key, String defValue) {
        return sp.getString(key, defValue);
    }

    public void setBoolean(String key, boolean value) {
        editor.putBoolean(key, value);
        editor.commit();
    }

    public void setFloat(String key, float value) {
        editor.putFloat(key, value);
        editor.commit();
    }

    public void setInt(String key, int value) {
        editor.putInt(key, value);
        editor.commit();
    }

    public void setLong(String key, long value) {
        editor.putLong(key, value);
        editor.commit();
    }

    public void setString(String key, String value) {
        editor.putString(key, value);
        editor.commit();
    }

    /**
     * 根据key删除SharedPreferences信息
     *
     * @param key
     */
    public void remove(String key) {
        editor.remove(key);
        editor.commit();
    }

    /**
     * 根据keys批量删除SharedPreferences信息
     *
     * @param keys
     */
    public void remove(String... keys) {
        for (String key : keys) {
            editor.remove(key);
            editor.commit();
        }
    }

    /**
     * 清空所有的SharedPreferences信息
     */
    public void clear() {
        editor.clear();
        editor.commit();
    }

}
