package com.study.shenxing.caesar.binder;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.study.shenxing.caesar.R;

public class AidlTestActivity extends AppCompatActivity {
    public static final String TAG = "sh";
    private Button mBtn;
    private Button mBtnGet;
    private Button mBtnSet;
    private ITestInterface mTestInterface;
    private ServiceConnection mServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Log.i(TAG, "onServiceConnected");
            mTestInterface = ITestInterface.Stub.asInterface(service);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            Log.i(TAG, "onServiceDisconnected: ");
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aidl_test);

        mBtn = (Button) findViewById(R.id.btn);
        mBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG, "onClick");
                bindRemoteService();
            }
        });

        mBtnGet = (Button) findViewById(R.id.get_name_btn);
        mBtnGet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mTestInterface != null) {
                    try {
                        Log.i(TAG, "getName " + mTestInterface.getName());
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        mBtnSet = (Button) findViewById(R.id.set_name_btn);
        mBtnSet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mTestInterface != null) {
                    try {
                        mTestInterface.setName("hello");
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    private void bindRemoteService() {
        Intent it = new Intent(this, AidlService.class);
        bindService(it, mServiceConnection, Context.BIND_AUTO_CREATE) ;
    }

    @Override
    protected void onDestroy() {
        Log.i(TAG, "onDestroy.....be care");
        super.onDestroy();
    }
}
