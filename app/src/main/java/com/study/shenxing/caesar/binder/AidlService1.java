package com.study.shenxing.caesar.binder;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.os.Parcel;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.util.Log;


/**
 * Created by shenxing on 16/8/22.
 *
 * 实践手动调用binder, but it's not works
 */
public class AidlService1 extends Binder {
    public static final String TAG = "sh";

    @Override
    protected boolean onTransact(int code, Parcel data, Parcel reply, int flags) throws RemoteException {
        switch (code) {
            case 1000:
                data.enforceInterface("AidlService1");
                String filePath = data.readString();
                start(filePath);
                break;
            default:
                break;
        }
        return super.onTransact(code, data, reply, flags);
    }

    public void start(String path) {
        Log.i(TAG, "start: " + path);
    }

    public void stop() {

    }
}
