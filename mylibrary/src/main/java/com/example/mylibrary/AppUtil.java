package com.example.mylibrary;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.provider.Settings.Secure;
import android.view.WindowManager;

import java.util.List;
import java.util.Locale;

/**
 * 获取手机信息
 */

public class AppUtil {

    /* 获取当前系统的android版本号 */
    public static int CURRENTAPIVERSION = android.os.Build.VERSION.SDK_INT;
    // 获取手机名称
    public static String MOBIL_NAME = android.os.Build.MANUFACTURER;
    // 手机型号
    public static String MOBIL_TYPE = android.os.Build.MODEL;
    // 手机品牌
    public static String MOBIL_BRAND = android.os.Build.BRAND;

    /**
     * 获取屏幕分辨率
     * isZh
     *
     * @param context
     * @return
     */
    @SuppressWarnings("deprecation")
    public static int[] getScreenDispaly(Context context) {
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        int width = windowManager.getDefaultDisplay().getWidth();// 手机屏幕的宽度
        int height = windowManager.getDefaultDisplay().getHeight();// 手机屏幕的高度
        int result[] =
                {width, height};
        return result;
    }


    /**
     * 获取手机mac地址
     *
     * @param context
     * @return
     */
    public static String getPhoneMac(Context context) {

        WifiManager wifi = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);

        WifiInfo info = wifi.getConnectionInfo();

        return info.getMacAddress();

    }

    /**
     * 获取手机ID
     *
     * @return
     */
    public static String getAndroidId(Context context) {
        String androidId = Secure.getString(context.getContentResolver(), Secure.ANDROID_ID);
        return androidId;
    }

    public static boolean isWifiActive(Context icontext) {
        Context context = icontext.getApplicationContext();
        ConnectivityManager connectivity = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo[] info;
        if (connectivity != null) {
            info = connectivity.getAllNetworkInfo();
            if (info != null) {
                for (int i = 0; i < info.length; i++) {
                    if (info[i].getTypeName().equals("WIFI") && info[i].isConnected()) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /**
     * 获取语言版本
     *
     * @return
     */
    public static boolean isZh(Context context) {
        Locale locale = context.getResources().getConfiguration().locale;
        String language = locale.getLanguage();
        if (language.endsWith("zh"))
            return true;
        else
            return false;

    }

    /**
     * 检查是否有中文
     *
     * @param str
     * @return
     */
    public static boolean checkString(String str) {
        boolean res = false;
        if (str != null) {
            for (int i = 0; i < str.length(); i++) {
                // 只要字符串中有中文则为中文
                if (!checkChar(str.charAt(i))) {
                    res = true;
                    break;
                } else {
                    res = false;
                }
            }
        }
        return res;
    }

    // 英文占1byte，非英文（可认为是中文）占2byte，根据这个特性来判断字符
    public static boolean checkChar(char ch) {
        if ((ch + "").getBytes().length == 1) {
            return true;// 英文
        } else {
            return false;// 中文
        }
    }

    /**
     * app版本号
     *
     * @param context
     * @return versionName
     */
    public static String getAppVersionName(Context context) {
        try {
            PackageInfo packageInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            if (packageInfo != null) {
                return packageInfo.versionName;
            }
        } catch (Exception e) {

        }
        return "1.0.0";
    }

    /**
     * app版本号
     *
     * @param context
     * @return versionCode
     */
    public static int getAppVersionCode(Context context) {
        try {
            PackageInfo packageInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            if (packageInfo != null) {
                return packageInfo.versionCode;
            }
        } catch (Exception e) {

        }
        return 1;
    }


    /**
     * 判断当前应用是否是debug状态
     *
     * @param context
     * @return
     */
    public static boolean isApkInDebug(Context context) {
        try {
            ApplicationInfo info = context.getApplicationInfo();
            return (info.flags & ApplicationInfo.FLAG_DEBUGGABLE) != 0;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 获取状态栏高度
     *
     * @param context
     * @return
     */
    public static int getStatusBarHeight(Context context) {
        Resources resources = context.getResources();
        int resourceId = resources.getIdentifier("status_bar_height", "dimen", "android");
        int height = resources.getDimensionPixelSize(resourceId);
        return height;
    }

    /**
     * 检测程序是否安装
     *
     * @param packageName
     * @return
     */
    public static boolean isInstalled(Context mContext, String packageName) {
        PackageManager manager = mContext.getPackageManager();
        //获取所有已安装程序的包信息
        List<PackageInfo> installedPackages = manager.getInstalledPackages(0);
        if (installedPackages != null) {
            for (PackageInfo info : installedPackages) {
                if (info.packageName.equals(packageName))
                    return true;
            }
        }
        return false;
    }

}
