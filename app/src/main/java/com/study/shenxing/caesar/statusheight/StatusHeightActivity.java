package com.study.shenxing.caesar.statusheight;

import android.app.Activity;
import android.graphics.Rect;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Window;

import com.study.shenxing.caesar.R;
import com.study.shenxing.caesar.utils.DrawUtils;

public class StatusHeightActivity extends Activity {
    public static final String TAG = "sh_height";

    private Handler mHandler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_status_height);

        // 从实际的日志来看，通过post的方式方法三和四也不能获取到相应的数据，而是必须在onWindowFocusChanged中获取。
        Log.i(TAG, "method1 : " + method1());
        Log.i(TAG, "method2 : " + method2());
        Log.i(TAG, "method3 : " + method3());
        Log.i(TAG, "method4 : " + method4());
        Log.i(TAG, "drawUtils : " + DrawUtils.sHeightPixels + ", " + DrawUtils.sRealHeightPixels);

        // 此种方式无法获取
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Log.i(TAG, "runOnUiThread method3 : " + method3());
                Log.i(TAG, "runOnUiThread method4 : " + method4());
            }
        });

        // 同上 此种方式也无法获取
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                Log.i(TAG, "mHandler.post method3 : " + method3());
                Log.i(TAG, "mHandler.post method4 : " + method4());
            }
        });
    }

    // 可以正确获取
    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            Log.i(TAG, "onWindowFocusChanged method3 : " + method3());
            Log.i(TAG, "onWindowFocusChanged method4 : " + method4());
            Log.i(TAG, "onWindowFocusChanged: titleBarHeght: " + getTitleBarHeight());
        }
    }

    /**
     * 状态栏高度 方法一
     * @return
     */
    private int method1() {
//        Returns 0 if no such resource was found.
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            return getResources().getDimensionPixelSize(resourceId);
        }

        return 0;
    }

    /**
     * 状态栏高度 方法二
     */
    private int method2() {
        try {
            Class<?> clazz = Class.forName("com.android.internal.R$dimen");
            Object object = clazz.newInstance();
            int resourceId = Integer.parseInt(clazz.getField("status_bar_height").get(object).toString());
            return getResources().getDimensionPixelSize(resourceId);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }

        return 0;
    }

    /**
     * 状态栏高度 方法三
     *
     * 依赖于WMS回调(简单说就是依赖窗口的显示，和获取view高度原理一样)，在activity初始化时得到的高度为0，应该onWindowFocusChanged()中回调
     */
    private int method3() {
        Rect out = new Rect();
        // out是不包含状态栏的外矩形
        getWindow().getDecorView().getWindowVisibleDisplayFrame(out);
        return out.top;
    }

    /**
     * 状态栏高度 方法四
     *
     * 使用方法同三， 此种方法比方法三繁琐了一点
     * @return
     */
    private int method4() {
        // 窗口的高度，包含状态栏， 但不包含虚拟键的高度
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        Rect out = new Rect();
        // out是不包含状态栏的外矩形
        getWindow().getDecorView().getWindowVisibleDisplayFrame(out);

        return dm.heightPixels - out.height();
    }

    /**
     * 获取标题栏高度 注意此种方法也是依赖于wms，故需要在windowFoucsChanged中获取
     * @return
     */
    private int getTitleBarHeight() {
        Rect appRect = new Rect();
        getWindow().findViewById(Window.ID_ANDROID_CONTENT).getDrawingRect(appRect);

        Rect decorRect = new Rect();
        getWindow().getDecorView().getWindowVisibleDisplayFrame(decorRect);

        return appRect.top - decorRect.top;
    }

}