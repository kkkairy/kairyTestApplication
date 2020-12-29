package com.example.mylibrary;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.graphics.drawable.Drawable;
import android.media.MediaMetadataRetriever;
import android.os.Environment;
import android.widget.ImageView;
import android.widget.ScrollView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class BitmapUtils {

    /**
     * 多张图片拼接9宫格
     *
     * @param margin
     * @param bitmaps
     * @return
     */
    public static Bitmap addBitmaps(int margin, Bitmap... bitmaps) {
        int row = 1;
        int col = 0;
        int width = 0;
        int height = 0;
        int totalHeight = 0;
        int length = bitmaps.length;
        if (length > 3) {
            row = length % 3 == 0 ? length / 3 : length / 3 + 1;
            col = 3;
        } else {
            row = 1;
            col = length;
        }
        for (int i = 0; i < length; i++) {
            height = Math.max(height, bitmaps[i].getHeight());
        }
        totalHeight = height * row;
        totalHeight += (row - 1) * margin;

        for (int i = 0; i < col; i++) {
            width += bitmaps[i].getWidth();
            width += margin;
        }
        width -= margin;
        Bitmap result = Bitmap.createBitmap(width, totalHeight, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(result);
        for (int i = 0; i < row; i++) {
            int left = 0;
            for (int i1 = 0; i1 < col; i1++) {
                if (i * col + i1 >= length) {
                    break;
                }
                if (i > 0) {
                    if (i1 > 0) {
                        left += bitmaps[i * col + i1 - 1].getWidth();
                        left += margin;
                        int top = (height + margin) * i;
                        canvas.drawBitmap(bitmaps[i * col + i1], left, top, null);
                    } else {
                        left = 0;
                        left += margin;
                        int top = (height + margin) * i;
                        canvas.drawBitmap(bitmaps[i * col + i1], left, top, null);
                    }
                } else {
                    //第1行
                    if (i1 > 0) {
                        left += bitmaps[i1 - 1].getWidth();
                        left += margin;
                        canvas.drawBitmap(bitmaps[i1], left, 0, null);
                    } else {
                        left = 0;
                        left += margin;
                        canvas.drawBitmap(bitmaps[i1], left, 0, null);
                    }
                }
            }
        }
        return result;
    }

    private static float LAYOUT_ONE_HEIGHT = 170;


    /**
     * 获取scrollview的截屏
     */
    public static Bitmap scrollViewScreenShot(ScrollView scrollView) {
        int h = 0;
        Bitmap bitmap = null;
        for (int i = 0; i < scrollView.getChildCount(); i++) {
            h += scrollView.getChildAt(i).getHeight();
            scrollView.getChildAt(i).setBackgroundColor(Color.parseColor("#ffffff"));
        }
        bitmap = Bitmap.createBitmap(scrollView.getWidth(), h, Bitmap.Config.RGB_565);
        final Canvas canvas = new Canvas(bitmap);
        scrollView.draw(canvas);
        return bitmap;
    }

    /**
     * 获取scrollview的截屏
     */
    public static Bitmap imageViewScreenShot(ImageView imageView) {
        Bitmap bitmap = Bitmap.createBitmap(imageView.getWidth(), imageView.getHeight(), Bitmap.Config.RGB_565);
        Canvas canvas = new Canvas(bitmap);
        imageView.draw(canvas);
        return bitmap;
    }

    /**
     * 保存bitmap到本地
     *
     * @param bitmap
     * @return
     */
    public static String saveImage(Bitmap bitmap, String fileName) {


        File file = new File(Environment.getExternalStorageDirectory().getPath() + "/css/" + fileName);

        if (file.exists()) {
            file.delete();
        }

        FileOutputStream fos = null;

        try {
            fos = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
            fos.flush();
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return file.getAbsolutePath();
    }

    /**
     * 保存bitmap到本地
     *
     * @param bitmap
     * @return
     */
    public static String saveImage2(Bitmap bitmap) {
        long timeS = System.currentTimeMillis();
        File file = new File(Environment.getExternalStorageDirectory(), timeS + ".jpg");
        if (file.exists()) {
            file.delete();
        }
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.flush();
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return file.getAbsolutePath();
    }

    /**
     * 获取视频文件第一帧截图
     *
     * @param path 视频文件的路径
     * @return Bitmap 返回获取的Bitmap
     */

    public static Bitmap getVideoThumb(String path) {
        MediaMetadataRetriever media = null;
        try {
            media = new MediaMetadataRetriever();
            media.setDataSource(path);
        } catch (IllegalArgumentException e) {
            media = null;
        }
        return media != null ? media.getFrameAtTime() : null;
    }

    /**
     * Drawable转换成一个Bitmap
     *
     * @param drawable drawable对象
     * @return
     */
    public static final Bitmap drawableToBitmap(Drawable drawable) {
        Bitmap bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(),
                drawable.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888 : Bitmap.Config.RGB_565);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
        drawable.draw(canvas);
        return bitmap;
    }
}
