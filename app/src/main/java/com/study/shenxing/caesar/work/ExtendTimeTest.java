package com.study.shenxing.caesar.work;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Environment;
import android.util.Log;


import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by shenxing on 16/7/4.
 * 深度清理延长时间测试类, 测试完成后删掉
 */
public class ExtendTimeTest {
    private TestReceiver mTestReceiver = new TestReceiver();
    private static final String PATH = Environment.getExternalStorageDirectory().getPath() + File.separator + "Extend_time";
    private static final String FILE_NAME = PATH + File.separator + "info.txt";
    private static final String TEST_ACTION = "com.gau.go.launcherex.gowidget.gopowermaster.TEST_ACTION";
    private static ExtendTimeTest sInstance;
    private Context mContext;

    private ExtendTimeTest(Context context) {
        mContext = context;
        initReceiver(context);


    }

    public static ExtendTimeTest getsInstance(Context context) {
        if (sInstance == null) {
            sInstance = new ExtendTimeTest(context) ;
        }
        return sInstance;
    }

    private void initReceiver(Context context) {
        IntentFilter filter = new IntentFilter();
        context.registerReceiver(mTestReceiver, filter);
    }

    private void writeDataToFile(String msg) {
        File dir = new File(PATH);
        if (!dir.exists()) {
            dir.mkdir();
        }
        File file = new File(FILE_NAME);
        FileOutputStream fileOutputStream = null;
        BufferedOutputStream bufferedOutputStream = null;
        try {
            if (!file.exists()) {
                file.createNewFile();
            }
            fileOutputStream = new FileOutputStream(file, true);
            bufferedOutputStream = new BufferedOutputStream(fileOutputStream);
            bufferedOutputStream.write(msg.getBytes());
            bufferedOutputStream.flush();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (fileOutputStream != null) {
                    fileOutputStream.close();
                }

                if (bufferedOutputStream != null) {
                    bufferedOutputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void recordInfo(int level, int generalCleanTime, int generalAddTime, int deepCleanTime, int deepAddtime) {
        StringBuffer stringBuffer = new StringBuffer();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Date date = new Date();
        String date_str = simpleDateFormat.format(date);
        stringBuffer.append(date_str);
        stringBuffer.append(File.separator);
        stringBuffer.append(level);
        stringBuffer.append(File.separator);
        stringBuffer.append(generalCleanTime);
        stringBuffer.append(File.separator);
        stringBuffer.append(generalAddTime);
        stringBuffer.append(File.separator);
        stringBuffer.append(deepCleanTime);
        stringBuffer.append(File.separator);
        stringBuffer.append(deepAddtime);
        String line = System.getProperty("line.separator");
        stringBuffer.append(line);
        Log.i("shenxing", "info: " + stringBuffer.toString() + "line:" + line);
        writeDataToFile(stringBuffer.toString());
    }

    public void release() {
        // // TODO: 16/7/4
    }

    /**
     * 测试类广播
     */
    private class TestReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent != null) {
                String action = intent.getAction();
            }
        }
    }

    private void sendKillProgramBroadcast(boolean flag) {
    }
}
