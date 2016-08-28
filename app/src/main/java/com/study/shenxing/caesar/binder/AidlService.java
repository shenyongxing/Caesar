package com.study.shenxing.caesar.binder;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.util.Log;


/**
 * Created by shenxing on 16/8/22.
 *
 * 实践手动调用binder, but it's not works
 */
public class AidlService extends Service {
    public static final String TAG = "sh";
    private Binder mBinder = new ITestInterface.Stub() {

        @Override
        public String getName() throws RemoteException {
            return "can you feel me";
        }

        @Override
        public void setName(String name) throws RemoteException {
            Log.i(TAG, "setName: " + name);
        }
    };
    
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.i(TAG, "onCreate: ");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i(TAG, "onStartCommand: ");
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i(TAG, "Service destroyed");
    }
}
