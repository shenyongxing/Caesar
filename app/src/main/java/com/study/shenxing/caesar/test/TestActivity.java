package com.study.shenxing.caesar.test;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.widget.Toast;

import com.study.shenxing.caesar.R;

/**
 * 用于验证各种知识点
 */
public class TestActivity extends Activity {
    private HandlerThread mThread ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        testThreadToast();
        testHandlerThread();
    }

    /**********************************************Start Handler及Toast知识********************************************************/
    private void testThreadToast() {
        new Thread() {
            @Override
            public void run() {
                super.run();
                Looper.prepare();
                Looper.getMainLooper();
                Toast.makeText(TestActivity.this, "it is must be wrong", Toast.LENGTH_SHORT).show();
                Looper.loop();
            }
        }.start();
    }
//    Looper.prepare();
    // 如果注释掉Looper.prepare();及Looper.loop();  会报错,如下：
//                java.lang.RuntimeException: Can't create handler inside thread that has not called Looper.prepare()
//                at android.widget.Toast$TN.<init>(Toast.java:335)
//                at android.widget.Toast.<init>(Toast.java:91)
//                at android.widget.Toast.makeText(Toast.java:242)
//                at com.study.shenxing.caesar.test.TestActivity$1.run(TestActivity.java:31)
    // 通过查看相关源码，发现里面有创建Handler的代码
    // final Handler mHandler = new Handler();
    // 而创建Handler是需要关联一个Looper的，而在主线程系统已经初始化了Looper，故可以直接new Handler。 而对于子线程需要先调用Loop.prepare（）方法。
//                Toast.makeText(TestActivity.this, "hello", Toast.LENGTH_SHORT).show();
//                Looper.loop();

    // 结论: 由于Toast依赖Handler的消息队列，非主线程需要为Toast准备Looper。
    // 虽然加入Looper.prepare（）和Looper.loop（）可以正确弹toast，但是不推荐这么做， 因为由于looper是无限循环的，所以该线程会一直存活。实际中须考虑该问题影响。
    // 最好还是将toast post到主线程中去。


    // 系统提供了HandlerThread类。由于在Thread类创建Handler必须关联一个Looper，所以创建之前必须调用Looper.prepare（）方法创建一个loop。
    // 而HandlerThread类封装了创建Handler的过程。可以省略创建Looper的过程。 但其使用有一定的限制,需要注意。
    private void testHandlerThread() {
        mThread = new HandlerThread("test") ;
        mThread.start();     // 必须调用start，这样thread的run方法才会执行，从而创建looper对象。

        Handler mMyHandler = new Handler(mThread.getLooper()) {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                if (msg.what == 0x111) {
                    // 下面的代码是在looper所在线程执
                    try {
                        Thread.sleep(10000); // 休眠10秒钟，模拟耗时操作
                        Toast.makeText(TestActivity.this, "i am toast from background thread", Toast.LENGTH_SHORT).show();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

            }
        } ;
        mMyHandler.sendEmptyMessage(0x111)  ;

    }
    /**********************************************End Handler及Toast知识********************************************************/



    /**********************************************Start IntentService *********************************************************/

    /**********************************************End IntentService ***********************************************************/


}
