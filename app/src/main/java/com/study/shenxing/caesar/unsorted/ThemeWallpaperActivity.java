package com.study.shenxing.caesar.unsorted;

import android.app.Activity;
import android.app.WallpaperManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Environment;
import android.util.DisplayMetrics;
import android.util.Log;
import android.widget.LinearLayout;

import com.study.shenxing.caesar.R;
import com.study.shenxing.caesar.enrique.stackblur.StackBlurManager;

import java.io.File;
import java.io.FileOutputStream;

public class ThemeWallpaperActivity extends Activity {
    private static final String TAG = "shenxing";
    public final static String SDCARD = Environment.getExternalStorageDirectory().getPath();
    private static final String IMAGE_CACHE = "/Caesar/wallpaper.png";
    private static final String path = SDCARD + IMAGE_CACHE ;
    private LinearLayout mBackground ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // 将桌面壁纸设置成应用的背景
        // 之外还可以通过设置壁纸类型的主题来实现桌面壁纸背景
        // 跟进FLAG_SHOW_WALLPAPER变量查看说明
        // 经过实践验证, FLAG_SHOW_WALLPAPER 是没有起作用的.必须设置Theme.Wallpaper的主题才会有透明的壁纸背景
        // ??? 初步猜想和版本有关系吧.
//        getWindow().addFlags(WindowManager.LayoutParams.FLAG_SHOW_WALLPAPER);
        // 如果使用的AppcompatActivity则无法使用Wallpaper类型的主题， 必须要改成Activity才行
        setContentView(R.layout.activity_theme_wallpaper);
        mBackground = (LinearLayout) findViewById(R.id.wallpaper_background) ;

        // so库文件的加载还存在一些问题
        Bitmap dst = blurBitmap(getWallpaperBitmap(), 40) ;
        saveBitmap(dst, path);
        BitmapDrawable dstDrawable = new BitmapDrawable(getResources(), dst) ;
        mBackground.setBackgroundDrawable(dstDrawable);
    }

    /**
     * 模糊图片的方法
     * @param originalBitmap 需要模糊处理的图片
     * @param dep 模糊程度
     * @return 模糊后的图片
     */
    public static Bitmap blurBitmap(Bitmap originalBitmap, int dep) {
        Bitmap result = null;

        StackBlurManager manager = new StackBlurManager(originalBitmap);
        result = manager.processNatively(dep);

        return result;
    }

    private Bitmap getWallpaperBitmap() {
        DisplayMetrics metric = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metric);
        int mScreenWidth = metric.widthPixels;     // 屏幕宽度（像素）
        int mScreenHeight = metric.heightPixels;   // 屏幕高度（像素）
        WallpaperManager wallpaperManager = WallpaperManager
                .getInstance(this);
        // 获取当前壁纸
        Drawable wallpaperDrawable = wallpaperManager.getDrawable();
        // 将Drawable,转成Bitmap
        Bitmap bm = ((BitmapDrawable) wallpaperDrawable).getBitmap();
        int bitmapWidth = bm.getWidth() ;
        if (bitmapWidth > mScreenWidth) {
            // 截取相应屏幕的Bitmap
            bm = Bitmap.createBitmap(bm, 0, 0, mScreenWidth, mScreenHeight) ;
        }
        return bm ;
    }

    public static final boolean saveBitmap(Bitmap bmp, String bmpName) {
        if (null == bmp) {
            Log.i(TAG, "save bitmap to file bmp is null");
            return false;
        }

        FileOutputStream stream = null;
        try {
            File file = new File(bmpName);
            if (file.exists()) {
                boolean bDel = file.delete();
                if (!bDel) {
                    Log.i(TAG, "delete src file fail");
                    return false;
                }
            } else {
                File parent = file.getParentFile();
                if (null == parent) {
                    Log.i(TAG, "get bmpName parent file fail");
                    return false;
                }
                if (!parent.exists()) {
                    boolean bDir = parent.mkdirs();
                    if (!bDir) {
                        Log.i(TAG, "make dir fail");
                        return false;
                    }
                }
            }
            boolean bCreate = file.createNewFile();
            if (!bCreate) {
                Log.i(TAG, "create file fail");
                return false;
            }

            stream = new FileOutputStream(file);
            boolean bOk = bmp.compress(Bitmap.CompressFormat.PNG, 100, stream);
            if (!bOk) {
                Log.i(TAG, "bitmap compress file fail");
                return false;
            }
        } catch (Exception e) {
            Log.i(TAG, e.toString());
            return false;
        } finally {
            if (null != stream) {
                try {
                    stream.close();
                } catch (Exception e2) {
                    Log.i(TAG, "close stream " + e2.toString());
                }
            }
        }
        return true;
    }
}
