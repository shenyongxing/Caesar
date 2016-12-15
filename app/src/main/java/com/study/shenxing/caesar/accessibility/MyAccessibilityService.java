package com.study.shenxing.caesar.accessibility;

import android.accessibilityservice.AccessibilityService;
import android.util.Log;
import android.view.accessibility.AccessibilityEvent;

public class MyAccessibilityService extends AccessibilityService {
    public static final String TAG = "sh_acce";

    public MyAccessibilityService() {
    }

    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {

        int type = event.getEventType();

        Log.i(TAG, "onAccessibilityEvent type: " + Integer.toHexString(type));


    }

    @Override
    public void onInterrupt() {

    }

    @Override
    protected void onServiceConnected() {
        super.onServiceConnected();

        Log.i(TAG, "Caesar onServiceConnected: ");

        Log.i(TAG, "Caesar onServiceConnected: " + performGlobalAction(GLOBAL_ACTION_BACK));
    }
}
