package com.study.shenxing.caesar.windowmanager;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;

public class WindowManagerService extends Service implements View.OnTouchListener {
    private WindowManager mWindowManager ;
    private WindowManager.LayoutParams mLayoutParams ;

    public WindowManagerService() {

    }

    @Override
    public IBinder onBind(Intent intent) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {

        return false;
    }
}
