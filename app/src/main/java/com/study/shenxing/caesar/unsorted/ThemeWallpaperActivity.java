package com.study.shenxing.caesar.unsorted;

import android.app.Activity;
import android.os.Bundle;
import android.view.WindowManager;

import com.study.shenxing.caesar.R;

public class ThemeWallpaperActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // 将桌面壁纸设置成应用的背景
        // 之外还可以通过设置壁纸类型的主题来实现桌面壁纸背景
        // 跟进FLAG_SHOW_WALLPAPER变量查看说明
        // 经过实践验证, FLAG_SHOW_WALLPAPER 是没有起作用的.必须设置Theme.Wallpaper的主题才会有透明的壁纸背景
        // ??? 初步猜想和版本有关系吧.
//        getWindow().addFlags(WindowManager.LayoutParams.FLAG_SHOW_WALLPAPER);
        setContentView(R.layout.activity_theme_wallpaper);
    }

}
