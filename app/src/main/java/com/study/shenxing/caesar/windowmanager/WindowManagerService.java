package com.study.shenxing.caesar.windowmanager;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.os.IBinder;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;

import com.study.shenxing.caesar.R;

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

    public WindowManagerService() {

    }

    @Override
    public void onCreate() {
        super.onCreate();
        mWindowManager = (WindowManager) getSystemService(Context.WINDOW_SERVICE) ;
        mLayoutParams = new WindowManager.LayoutParams(WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.TYPE_PHONE,
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE | WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL, PixelFormat.TRANSLUCENT);
        mLayoutParams.x = 300;
        mLayoutParams.y = 600 ;
        mFloatView = LayoutInflater.from(this).inflate(R.layout.float_window_view, null) ;
        mFloatView.setOnTouchListener(this);
    }

    @Override
    public IBinder onBind(Intent intent) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        addFloatView();
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        int action = event.getAction();
        switch (action) {
            case MotionEvent.ACTION_DOWN : 	// 按下事件，记录按下时手指在悬浮窗的XY坐标值
                mDownX = event.getX();
                mDownY = event.getY();
                mRawX = event.getRawX();
                mRawY = event.getRawY();
                break;
            case MotionEvent.ACTION_MOVE :
                int x = (int) (event.getRawX() - mDownX);
                int y = (int) (event.getRawY() - mDownY);
                refreshView(x, y);
                break;
            case MotionEvent.ACTION_UP :
                break;
        }

        return false;
    }

    /**
     * 添加悬浮窗
     */
    private void addFloatView() {
        if (!mIsViewAdded) {
            mWindowManager.addView(mFloatView, mLayoutParams);
            mIsViewAdded = true ;
        }
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
}
