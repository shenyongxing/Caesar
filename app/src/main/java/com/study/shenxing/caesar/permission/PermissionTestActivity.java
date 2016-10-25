package com.study.shenxing.caesar.permission;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.widget.TextView;
import android.widget.Toast;

import com.study.shenxing.caesar.R;

public class PermissionTestActivity extends AppCompatActivity {
    public static final int REQUEST_CODE_ASK_PERMISSIONS = 0x111;
    private TextView mTextView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_permission_test);

        mTextView = (TextView) findViewById(R.id.tv_imei);

        getIMEIWrapper();
    }

    /**
     * 该方法在android6.0上需要授权 android.permission.READ_PHONE_STATE 权限,以此来验证权限授权机制
     * @param context
     * @return
     */
    public static String getIMEI(Context context) {
        TelephonyManager telephonyManager = (TelephonyManager)context.getSystemService(Context.TELEPHONY_SERVICE);
        String imei = telephonyManager.getDeviceId();
        return imei;
    }

    @TargetApi(Build.VERSION_CODES.M)
    private void getIMEIWrapper() {
        int hasWriteContactsPermission = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE);
        if (hasWriteContactsPermission != PackageManager.PERMISSION_GRANTED) {

            // 当用户第一次拒绝了权限请求时,则下一次弹窗提示时就会有不在询问的复选框,如果用户勾选了不在询问,那么在下一次则需要自己
            // 手动设置对话框提醒用户,否则应用没有响应影响用户体验
            if (!ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.WRITE_CONTACTS)) {
                showMessageOKCancel("You need to allow access to Read Phone State",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                requestPermissions(new String[] {Manifest.permission.READ_PHONE_STATE},
                                        REQUEST_CODE_ASK_PERMISSIONS);
                            }
                        });
                return;
            }

            ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.READ_PHONE_STATE},
                    REQUEST_CODE_ASK_PERMISSIONS);
            return;
        }
        mTextView.setText(getIMEI(this));
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CODE_ASK_PERMISSIONS:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // Permission Granted
                    getIMEIWrapper();
                } else {
                    // Permission Denied
                    Toast.makeText(PermissionTestActivity.this, "READ_PHONE_STATE Denied", Toast.LENGTH_SHORT)
                            .show();
                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    /**
     * 对话框
     * @param message
     * @param okListener
     */
    private void showMessageOKCancel(String message, DialogInterface.OnClickListener okListener) {
        new AlertDialog.Builder(PermissionTestActivity.this)
                .setMessage(message)
                .setPositiveButton("OK", okListener)
                .setNegativeButton("Cancel", null)
                .create()
                .show();
    }
}
