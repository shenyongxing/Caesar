package com.study.shenxing.caesar.windowmanager;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Configuration;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.os.BatteryManager;
import android.os.IBinder;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.study.shenxing.caesar.R;


/**
 * 悬浮窗demo,注意部分机型需要开启悬浮窗权限才能显示,例如mx5
 */
public class WindowManagerService extends Service implements View.OnTouchListener {
    private WindowManager mWindowManager ;
    private WindowManager.LayoutParams mLayoutParams ;
    private View mFloatView ;
    private boolean mIsViewAdded ;
    private float mRawX ;
    private float mRawY ;
    private float mDownX ;
    private float mDownY ;
    private int mBarHeight ;
    private int mScreenWidth ;
    private int mScreenHeight ;
    private int mCurrOrientation; // 当前屏幕方向
    private TextView mFloatIcon ;

    private BatteryReceiver mBatteryReceiver ;

    public WindowManagerService() {

    }

    @Override
    public void onCreate() {
        super.onCreate();
        mWindowManager = (WindowManager) getApplication().getSystemService(Context.WINDOW_SERVICE) ;
        mLayoutParams = new WindowManager.LayoutParams(WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.TYPE_SYSTEM_ALERT,
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE | WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL, PixelFormat.TRANSLUCENT);
        mLayoutParams.gravity = Gravity.RIGHT | Gravity.TOP;
        mLayoutParams.y = 200 ;
        mFloatView = LayoutInflater.from(this).inflate(R.layout.float_window_view, null) ;
        mFloatView.setOnTouchListener(this);

        mFloatIcon = (TextView) mFloatView.findViewById(R.id.float_icon);
        mFloatIcon.setText(getBatteryLevelPercent(this) + "%");

        mScreenWidth = getResources().getDisplayMetrics().widthPixels ;
        mScreenHeight = getResources().getDisplayMetrics().heightPixels;

        IntentFilter intentFilter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED) ;
        registerReceiver(mBatteryReceiver, intentFilter) ;

    }

    @Override
    public IBinder onBind(Intent intent) {
        return null ;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        refresh();
        Log.i("shenxing", "onStartCommand") ;
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        int action = event.getAction();
        switch (action) {
            case MotionEvent.ACTION_DOWN : 	// 按下事件，记录按下时手指在悬浮窗的XY坐标值
                mCurrOrientation = getResources().getConfiguration().orientation ;
                mDownX = event.getX();
                mDownY = event.getY();
                mRawX = event.getRawX();
                mRawY = event.getRawY();
                break;
            case MotionEvent.ACTION_MOVE :
                int x ;
                if ((Gravity.RIGHT | Gravity.TOP) == mLayoutParams.gravity) {
                    if (mCurrOrientation == Configuration.ORIENTATION_PORTRAIT) {
                        x = (int) (mScreenWidth - (event.getRawX() - mDownX) - mFloatView.getWidth());
                    } else {
                        x = (int) (mScreenHeight - (event.getRawX() - mDownX) - mFloatView.getWidth());
                    }
                } else {
                    x = (int) (event.getRawX() - mDownX);
                }
                int y = (int) (event.getRawY() - mDownY);
                refreshView(x, y);
                break;
            case MotionEvent.ACTION_UP :
                if (mCurrOrientation == Configuration.ORIENTATION_PORTRAIT) {
                    if (event.getRawX() > mScreenWidth / 2) {
                        mLayoutParams.gravity = Gravity.RIGHT | Gravity.TOP ;
                        mFloatView.setBackgroundResource(R.drawable.float_icon_right);
                    } else {
                        mLayoutParams.gravity = Gravity.LEFT | Gravity.TOP ;
                        mFloatView.setBackgroundResource(R.drawable.float_icon_left);
                    }
                } else {
                    if (event.getRawX() > mScreenHeight / 2) {
                        mLayoutParams.gravity = Gravity.RIGHT | Gravity.TOP ;
                        mFloatView.setBackgroundResource(R.drawable.float_icon_right);
                    } else {
                        mLayoutParams.gravity = Gravity.LEFT | Gravity.TOP ;
                        mFloatView.setBackgroundResource(R.drawable.float_icon_left);
                    }
                }
                refreshView(0, (int) event.getRawY());

                break;
        }

        return false;
    }


    /**
     * 删除悬浮窗
     */
    private void removeFloatView() {
        if (mIsViewAdded) {
            mWindowManager.removeView(mFloatView);
            mIsViewAdded = false ;
        }
    }

    private void refreshView(int x, int y) {
        if (mBarHeight == 0) {
            View rootView = mFloatView.getRootView();
            Rect r = new Rect();
            rootView.getWindowVisibleDisplayFrame(r);
            mBarHeight = r.top;
        }

        mLayoutParams.x = x;
        mLayoutParams.y = y - mBarHeight;
        refresh();
    }


    private void refresh() {
        if (mIsViewAdded) {
            mWindowManager.updateViewLayout(mFloatView, mLayoutParams);
        } else {
            mWindowManager.addView(mFloatView, mLayoutParams);
            mIsViewAdded = true;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mBatteryReceiver != null) {
            unregisterReceiver(mBatteryReceiver);
        }
        Log.i("shenxing", "service destroy") ;
    }

    class BatteryReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            //判断它是否是为电量变化的Broadcast Action
            if(Intent.ACTION_BATTERY_CHANGED.equals(intent.getAction())){
                //获取当前电量
                int level = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, 0);
                //电量的总刻度
                int scale = intent.getIntExtra(BatteryManager.EXTRA_SCALE, 100);
                //把它转成百分比
                int batteryPercent = level * 100 / scale ;
                mFloatIcon.setText(batteryPercent + "%");
            }
        }

    }


    /**
     * 参考省电的做法,但是该方法依然取不到首次的电量
     * @param context
     * @return
     */
    public static int getBatteryLevelPercent(Context context) {
        Intent intent = context.registerReceiver(null, new IntentFilter(Intent.ACTION_BATTERY_CHANGED)) ;

        if (intent == null) {
            return 0 ;
        }

        //获取当前电量
        int level = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, 0);
        //电量的总刻度
        int scale = intent.getIntExtra(BatteryManager.EXTRA_SCALE, 100);

        return level / scale * 100 ;
    }

}
