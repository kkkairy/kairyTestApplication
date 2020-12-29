package com.example.mylibrary;

import android.content.Context;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStreamWriter;
import java.text.DecimalFormat;
import java.util.Locale;

import static android.os.Build.VERSION_CODES.P;

/**
 * 描述：文件操作工具类
 */
public class FileUtils {

    //日志TAG
    private static final String TAG = "FileUtils";
    private static final String[][] MIME_TABLE = {    //{后缀名，    MIME类型}
            {".3gp", "video/3gpp"},
            {".apk", "application/vnd.android.package-archive"},
            {".avi", "video/x-msvideo"},
            {".bmp", "image/bmp"},
            {".conf", "text/plain"},
            {".doc", "application/msword"},
            {".docx", "application/msword"},
            {".gif", "image/gif"},
            {".gtar", "application/x-gtar"},
            {".gz", "application/x-gzip"},
            {".htm", "text/html"},
            {".html", "text/html"},
            {".jar", "application/java-archive"},
            {".java", "text/plain"},
            {".jpeg", "image/jpeg"},
            {".jpg", "image/jpeg"},
            {".js", "application/x-javascript"},
            {".log", "text/plain"},
            {".mp3", "audio/x-mpeg"},
            {".mp4", "video/mp4"},
            {".pdf", "application/pdf"},
            {".png", "image/png"},
            {".pps", "application/vnd.ms-powerpoint"},
            {".ppt", "application/vnd.ms-powerpoint"},
            {".pptx", "application/vnd.ms-powerpoint"},
            {".rar", "application/x-rar-compressed"},
            {".rmvb", "audio/x-pn-realaudio"},
            {".tar", "application/x-tar"},
            {".tgz", "application/x-compressed"},
            {".txt", "text/plain"},
            {".wav", "audio/x-wav"},
            {".wma", "audio/x-ms-wma"},
            {".wmv", "audio/x-ms-wmv"},
            {".wps", "application/vnd.ms-works"},
            {".xml", "text/plain"},
            {".xls", "application/vnd.ms-excel"},
            {".xlsx", "application/vnd.ms-excel"},
            {".zip", "application/zip"},
            {"", "*/*"}
    };



    /**
     * 获取文件名称（不含后缀名）
     *
     * @param fileName
     * @return
     */
    public static String getFilePrefix(String fileName) {
        int splitIndex = fileName.lastIndexOf(".");
        return fileName.substring(0, splitIndex);
    }

//    /**
//     * 获得文件类型图标ResourceId
//     *
//     * @return
//     */
//    public static int getFileIconResourceId(String extend) {
////		String extend=getExtend(fileName);
//        if (extend.equals("doc") || extend.equals("docx")) {
//            return R.drawable.file_word;
//        } else if (extend.equals("xls") || extend.equals("xlsx")) {
//            return R.drawable.file_excel;
//        } else if (extend.equals("ppt") || extend.equals("pptx")) {
//            return R.drawable.file_ppt;
//        } else if (extend.equals("png") || extend.equals("gif") || extend.equals("jpg")) {
//            return R.drawable.file_image;
//        } else if (extend.equals("rar") || extend.equals("zip")) {
//            return R.drawable.file_compress;
//        }
//        return R.drawable.file_unknown;
//    }

    /**
     * 文件复制
     *
     * @param inputFile
     * @param outputFile
     */
    public static String copyFile(String inputFile, String outputFile) {
        File sFile = new File(inputFile);
        File tFile = new File(outputFile);
        InputStreamReader in = null;
        OutputStreamWriter out = null;
        try {
            FileInputStream fis = new FileInputStream(sFile);
            FileOutputStream fos = new FileOutputStream(tFile);
            int temp = 0;
            char[] buf = new char[2048];
            in = new InputStreamReader(fis);
            out = new OutputStreamWriter(fos);
            while ((temp = in.read(buf)) != -1) {
                out.write(buf, 0, temp);
            }
        } catch (Exception ex) {
            Log.e(TAG, "copyFile fail", ex);
            return "";
        } finally {
            try {
                in.close();
                out.close();
            } catch (IOException ex) {
                ex.printStackTrace();
                return "";
            }
        }

        return tFile.getAbsolutePath();

    }

    /**
     * 返回文件或文件夹的大小
     *
     * @param f
     * @return
     * @throws Exception
     */
    public static long getFileSizes(File f) throws Exception {
        long s = 0;
        if (f.exists()) {
            FileInputStream fis = new FileInputStream(f);
            s = fis.available();
            fis.close();
        }
        return s;
    }

    /**
     * 返回文件或文件夹的大小（递归）
     *
     * @param f
     * @return
     * @throws Exception
     */
    public static long getFileSize(File f) throws Exception {
        long size = 0;
        File flist[] = f.listFiles();
        for (int i = 0; i < flist.length; i++) {
            if (flist[i].isDirectory()) {
                size = size + getFileSize(flist[i]);
            } else {
                size = size + flist[i].length();
            }
        }
        return size;
    }

    /**
     * 返回友好的文件或者文件夹大小
     *
     * @param fileS
     * @return
     */
    public static String FormetFileSize(long fileS) {
        DecimalFormat df = new DecimalFormat("#0.00");
        String fileSizeString = "";
        if (fileS < 1024) {
            fileSizeString = df.format((double) fileS) + "B";
        } else if (fileS < 1048576) {
            fileSizeString = df.format((double) fileS / 1024) + "K";
        } else if (fileS < 1073741824) {
            fileSizeString = df.format((double) fileS / 1048576) + "M";
        } else {
            fileSizeString = df.format((double) fileS / 1073741824) + "G";
        }
        return fileSizeString;
    }

    /**
     * 递归求取目录文件个数
     *
     * @param f
     * @return
     */
    public static long getlist(File f) {
        long size = 0;
        File flist[] = f.listFiles();
        size = flist.length;
        for (int i = 0; i < flist.length; i++) {
            if (flist[i].isDirectory()) {
                size = size + getlist(flist[i]);
                size--;
            }
        }
        return size;

    }

    /**
     * 获取SDCARD根目录
     *
     * @return
     */
    public static File getSDCardPath() {
        File path = null;
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            path = Environment.getExternalStorageDirectory();
        }
        return path;
    }

    /**
     * 将对象序列化保存到程序目录
     *
     * @param fileName
     * @param o
     * @param context
     */
    public static void saveObject(String fileName, Object o, Context context) {
        try {
            String path = context.getFilesDir() + "/";
            File dir = new File(path);
            if (!dir.exists()) {
                dir.mkdirs();
            }
            File file = new File(dir, fileName);
            if (file.exists()) {
                file.delete();
            }
            FileOutputStream fos = new FileOutputStream(file);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(o);
            oos.close();
            fos.close();
        } catch (Exception e) {
            Log.e(TAG, "saveObject fail", e);
        }
    }

    /**
     * 从程序目录中读取序列化对象
     *
     * @param fileName
     * @param context
     * @return
     */
    public static Object readObject(String fileName, Context context) {
        Object o = null;
        try {
            String path = context.getFilesDir() + "/";
            File dir = new File(path);
            File file = new File(dir, fileName);
            if (file.exists()) {
                FileInputStream fis = new FileInputStream(file);
                ObjectInputStream ois = new ObjectInputStream(fis);
                o = ois.readObject();
                ois.close();
                fis.close();
            }
        } catch (Exception e) {
            Log.e(TAG, "readObject fail", e);
        }
        return o;
    }

    public static String getMIMEType(File f) {
        String type = "*/*";
        String extend = getExtend(f.getName());
        for (int i = 0; i < MIME_TABLE.length; i++) {
            if (extend.equals(MIME_TABLE[i][0])) {
                type = MIME_TABLE[i][1];
                break;
            }
        }
        return type;
    }


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
     * 本地新创建的视频名称名
     *
     * @return
     */
    public static String creatFileName(String fileName) {

        String extend = getExtend(fileName);
        extend = extend.replaceFirst(".", "");

        String path = "";
        if (AppUtil.CURRENTAPIVERSION > P)
            path = Environment.getDataDirectory().getPath() + "/css";
        else
            path = Environment.getExternalStorageDirectory().getPath() + "/css";

            File file = new File(path);

        if (!file.exists())
            file.mkdir();

        return path + "VIDEO_" + System.currentTimeMillis() + "." + extend;
    }

}
