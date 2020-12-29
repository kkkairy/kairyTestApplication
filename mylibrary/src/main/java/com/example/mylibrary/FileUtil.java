package com.example.mylibrary;

import android.content.Context;
import android.os.Environment;


import java.io.File;
import java.util.Locale;

import static android.os.Build.VERSION_CODES.P;
import static android.os.Environment.DIRECTORY_MOVIES;

/**
 * Created by zb on 2020/1/13.
 * 描述：
 */
public class FileUtil {
    /**
     * 获取文件扩展名
     *
     * @param fileName
     * @return
     */
    public static String getExtend(String fileName) {
        String extend = "";
        if (!StringUtils.isBlank(fileName)) {
            int index = fileName.lastIndexOf('.');
            if (index > 0 && index < fileName.length() - 1) {
                extend = fileName.substring(index).toLowerCase(Locale.CHINESE);
            }
        }
        return extend;
    }

    /**
     * 获取文件扩展名
     *
     * @param filePath
     * @return
     */
    public static String getExtendS(String filePath) {
        String endexFix = ".jpg";
        String[] strSS = filePath.split("\\.");
        if (strSS.length > 1) {
            endexFix = "." + strSS[strSS.length - 1];
        } else {
            endexFix = ".jpg";
        }
        return endexFix;
    }

    /**
     * 本地新创建的视频名称名
     *
     * @return
     */
    public static String creatFileName(Context context, String fileName) {

        String extend = getExtend(fileName);
        extend = extend.replaceFirst(".", "");

        String path = "";
        if (AppUtil.CURRENTAPIVERSION > P)
            path = context.getExternalFilesDir(DIRECTORY_MOVIES).getPath();
        else
            path = Environment.getExternalStorageDirectory().getPath() + "/css";

        File file = new File(path);

        if (!file.exists())
            file.mkdir();

        return path + "VIDEO_" + System.currentTimeMillis() + "." + extend;
    }


}
