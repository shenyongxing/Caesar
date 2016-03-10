package com.study.shenxing.caesar.network;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.study.shenxing.caesar.R;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class NetWorkActivity extends AppCompatActivity {
    private TextView mResult ;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 1) {
                String text = (String) msg.obj;
                mResult.setText(text);
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_net_work);

        mResult = (TextView) findViewById(R.id.net_result);
        new Thread() {
            @Override
            public void run() {
                request();
            }
        }.start();
    }

    // 利用系统的HttpURLConnection请求
    private void request() {
        String dstSite = "http://www.baidu.com" ;
        String resultData = "";

        URL url = null ;
        try {
            url = new URL(dstSite) ;
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        String inputLine = "" ;
        HttpURLConnection connUrl = null ;
        InputStreamReader inputStreamReader = null ;
        BufferedReader bufferedReader = null ;

        if (url != null) {
            try {
                connUrl = (HttpURLConnection) url.openConnection();
                inputStreamReader = new InputStreamReader(connUrl.getInputStream()) ;
                bufferedReader = new BufferedReader(inputStreamReader) ;

                while ((inputLine = bufferedReader.readLine()) != null) {
                    resultData += inputLine + "\n" ;
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    if (inputStreamReader != null) {
                        inputStreamReader.close();
                    }
                    if (bufferedReader != null) {
                        bufferedReader.close();
                    }
                    connUrl.disconnect();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }

        Message message = mHandler.obtainMessage();
        message.what = 1 ;
        message.obj = resultData ;
        mHandler.sendMessage(message) ;
    }

    private String readInputStream(InputStream inputStream) {
        String result = "" ;
        BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream) ;
        try {
            byte[] content = new byte[inputStream.available()];
            int k = 0 ;
            while ((k = bufferedInputStream.read(content, 0 ,content.length)) != -1) {
                result = new String(content, "utf-8") ;
            }
            return result ;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "" ;
    }
}


// 参考
// http://blog.csdn.net/jdsjlzx/article/details/6876958