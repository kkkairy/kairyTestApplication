package com.example.mylibrary;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * 描述：从assets获取资源
 */
public class AssetsOperateUtils {

    //日志TAG
    private static final String TAG = "AssetsOperateUtils";

    /**
     * 通过文件名从Assets中获得资源,以输入流的形式返回
     *
     * @param context
     * @param fileName 文件名应为assets文件下载绝对路径
     * @return
     */
    public static InputStream getInputStreamForName(Context context,
                                                    String fileName) {
        AssetManager assetManager = context.getAssets();
        InputStream inputStream = null;
        try {
            inputStream = assetManager.open(fileName);
        } catch (IOException e) {
            Log.e(TAG, "open from assets  fail", e);
        }
        return inputStream;
    }

    /**
     * 通过文件名从Assets中获得资源，以字符串的形式返回
     *
     * @param context
     * @param fileName 文件名应为assets文件下载绝对路径
     * @return
     */
    public static String getStringForName(Context context, String fileName) {
        String result = "";
        InputStream inputStream = null;
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        byte buf[] = new byte[2048];
        int len;
        try {
            inputStream = getInputStreamForName(context, fileName);
            while ((len = inputStream.read(buf)) != -1) {
                outputStream.write(buf, 0, len);
            }
            result = outputStream.toString();
            outputStream.close();
            inputStream.close();
        } catch (IOException e) {
            Log.e(TAG, "open from assets  fail", e);
        }
        return result;
    }

    /**
     * 通过文件名从Assets中获得资源，以位图的形式返回
     *
     * @param context
     * @param fileName 文件名应为assets文件下载绝对路径
     * @return
     */
    public static Bitmap getBitmapForName(Context context, String fileName) {
        Bitmap bitmap = null;
        InputStream inputStream = null;
        try {
            inputStream = getInputStreamForName(context, fileName);
            bitmap = BitmapFactory.decodeStream(inputStream);
            inputStream.close();
        } catch (IOException e) {
            Log.e(TAG, "open from assets  fail", e);
        }
        return bitmap;
    }

}
