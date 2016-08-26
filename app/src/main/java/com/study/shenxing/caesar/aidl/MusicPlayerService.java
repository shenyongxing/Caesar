package com.study.shenxing.caesar.aidl;

import android.os.Binder;
import android.os.Parcel;
import android.os.RemoteException;
import android.util.Log;

/**
 * Created by shenxing on 16/8/22.
 */
public class MusicPlayerService extends Binder {

    @Override
    protected boolean onTransact(int code, Parcel data, Parcel reply, int flags) throws RemoteException {
        return super.onTransact(code, data, reply, flags);
    }

    public void start(String filePath) {
        Log.i("sh", "Remote MusicPlayerService start");
    }

    public void stop() {
        Log.i("sh", "Remote MusicPlayerService stop");
    }
}
