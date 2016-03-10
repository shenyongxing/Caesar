package com.study.shenxing.caesar.chargelock;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;

import com.study.shenxing.caesar.chargelock.activity.PowerSavingActivity;

/**
 * Created by shenxing on 16-3-9.
 */
public class ChargeLockService extends Service {
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
    private RequestReceiver mRequestReceiver;
    private Handler mHandler = new Handler() ;
    /**
     * 上一次灭屏时间
     */
    private long mLastScreenOffTime = 0l;
    /**
     * 上一次加速时间
     */
    private long mLastAccelTime = 0l;
    @Override
    public void onCreate() {
        super.onCreate();
        mRequestReceiver = new RequestReceiver();
        IntentFilter filter = new IntentFilter() ;
        filter.addAction(Intent.ACTION_SCREEN_OFF);
        filter.addAction(Intent.ACTION_SCREEN_ON);
        registerReceiver(mRequestReceiver, filter) ;
    }

    private class RequestReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction() ;
            if (action.equals(Intent.ACTION_SCREEN_ON)) {
                handleScreenEvent(true);
            }
        }
    }

    /**
     * 处理屏幕事件
     * @param isScreenOn
     */
    private void handleScreenEvent(boolean isScreenOn) {
        if (isScreenOn) {
            long now = System.currentTimeMillis();
            if (/*isShowChargingLock() && isPhoneIdle() && now - mLastAccelTime < TWENTY_MINUTE*/false) {
                mHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        startActivity(PowerSavingActivity.getStartIntent(ChargeLockService.this, PowerSavingActivity.MODE_NO_ANIM, true));
                        Log.i("GoPowerMaster", "距离上一次加速小于20分钟, 无动画 如果没有广告缓存，会请求广告并展示 如果存在缓存,则展示缓存") ;
                    }
                }, 500) ;
            } else if (/*isShowChargingLock() && isPhoneIdle() && now - mLastScreenOffTime >= FIVE_MINUTE*/false) {
                mHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        startActivity(PowerSavingActivity.getStartIntent(ChargeLockService.this, PowerSavingActivity.MODE_RESULT_ONLY, true));
                        Log.i("GoPowerMaster", "距离上一次灭屏大于5分钟, 没有动画，会杀进程,弹出加速结果， 会展示广告") ;
                        mLastAccelTime = System.currentTimeMillis();
                    }
                }, 500);
            } else if (/*isShowChargingLock() && isPhoneIdle() && now - mLastScreenOffTime < FIVE_MINUTE*/true) {
                mHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Log.i("GoPowerMaster", "距离上一次灭屏小于5分钟, 有动画，会杀进程,弹出加速结果， 会展示广告") ;
                        startActivity(PowerSavingActivity.getStartIntent(ChargeLockService.this, PowerSavingActivity.MODE_ANIM_WITH_RESULT, true));
                        mLastAccelTime = System.currentTimeMillis();
                    }
                }, 500);
            } else if (/*isShowChargingLock() && isPhoneIdle()*/false) {
                mHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Log.i("GoPowerMaster", "其他情况， 无动画，如果没有广告缓存则会请求广告并展示 若存在则不展示广告");
                        startActivity(PowerSavingActivity.getStartIntent(ChargeLockService.this, PowerSavingActivity.MODE_NO_ANIM, true));
                    }
                }, 500);
            }
        }
    }
}
