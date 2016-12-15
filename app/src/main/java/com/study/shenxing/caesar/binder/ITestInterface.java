package com.study.shenxing.caesar.binder;

import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import android.util.Log;

/**
 * Created by shenxing on 16/8/27.
 *
 * try to write a aidl file manually
 *
 * 注意在手动书写的aidl文件时,需要注意的是proxy类中调用data.writeInterfaceToken
 * 而在Stub类的重载方法onTrasaction中调用的是data.enforceInterface,两者是不同的
 * 今天花了很长时间才对比出来,错误出现在这里。
 */

public interface ITestInterface extends IInterface {
    /**
     * for server
     */
    public static abstract class Stub extends android.os.Binder implements ITestInterface {
        public static final String TAG = "sh";
        private static final String DESCRIPTOR = "com.study.shenxing.caesar.binder.ITestInterface";
        public Stub() {
            this.attachInterface(this, DESCRIPTOR);
        }

        @Override
        public IBinder asBinder() {
            return this;
        }

        public static ITestInterface asInterface(android.os.IBinder obj) {
            if (obj == null) {
                return null;
            }
            android.os.IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
            if (((iin != null) && (iin instanceof ITestInterface))) {
                Log.i(TAG, "**************asInterface: inner process");
                return ((ITestInterface)iin);
            }
            Log.i(TAG, "**************asInterface: inter process");
            return new ITestInterface.Stub.Proxy(obj);
        }

        @Override
        protected boolean onTransact(int code, Parcel data, Parcel reply, int flags) throws RemoteException {
            switch (code) {
                case INTERFACE_TRANSACTION:
                    reply.writeString(DESCRIPTOR);
                    return true;
                case TRANSACTION_getName:
                    data.enforceInterface(DESCRIPTOR);
                    String result = this.getName();
                    reply.writeNoException();
                    reply.writeString(result);
                    return true;
                case TRANSACTION_setName:
                    data.enforceInterface(DESCRIPTOR);
                    String _arg0 = data.readString();
                    this.setName(_arg0);
                    reply.writeNoException();
                    return true;
            }
            return super.onTransact(code, data, reply, flags);
        }

        /**
         * for client
         */
        private static class Proxy implements ITestInterface {

            private IBinder mRemote;    // 驱动层的binder对象

            public Proxy(IBinder mRemote) {
                this.mRemote = mRemote;
            }

            @Override
            public String getName() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                String result;
                try {
                    _data.writeInterfaceToken(DESCRIPTOR);
                    mRemote.transact(Stub.TRANSACTION_getName, _data, _reply, 0); // 最后一个参数标识是否是双向, 0表示双向
                    _reply.readException();
                    result = _reply.readString();
                } finally {
                    _data.recycle();
                    _reply.recycle();
                }
                return result;

            }


            @Override
            public void setName(java.lang.String name) throws RemoteException {
                android.os.Parcel _data = android.os.Parcel.obtain();
                android.os.Parcel _reply = android.os.Parcel.obtain();
                try {
                    _data.writeInterfaceToken(DESCRIPTOR);
                    _data.writeString(name);
                    mRemote.transact(ITestInterface.Stub.TRANSACTION_setName, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override
            public IBinder asBinder() {
                return mRemote;
            }
        }
        public static final int TRANSACTION_getName = IBinder.FIRST_CALL_TRANSACTION + 0;
        public static final int TRANSACTION_setName = IBinder.FIRST_CALL_TRANSACTION + 1;

        public String getInterfaceDescriptor() {
            return DESCRIPTOR;
        }
    }

    public String getName() throws RemoteException;

    public void setName(String name) throws RemoteException;
}
