package com.study.shenxing.caesar.threadpoolexcutor;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.study.shenxing.caesar.R;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ThreadPoolExecutorActivity extends AppCompatActivity {
    public static final String TAG = "sh_thread";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thread_pool_excutor);

        test();
    }

    private void test() {
//        testFixedThreadPool();
//        testSingleThreadPool();
//        testCacheThreadPool();
//        testSingleThreadScheduledExecutor();
        testCusThreadPoolExecutor();
    }

    /**
     * fixed thead pool
     * 固定大小的线程池， 执行任务fifo
     *
     * 输出日志如下：
     *
     *
     02-11 19:14:15.226  2628  2688 I sh_thread: thread name :pool-1-thread-1, index : 0
     02-11 19:14:15.235  2628  2690 I sh_thread: thread name :pool-1-thread-3, index : 2
     02-11 19:14:15.236  2628  2689 I sh_thread: thread name :pool-1-thread-2, index : 1
     02-11 19:14:17.227  2628  2688 I sh_thread: thread name :pool-1-thread-1, index : 3
     02-11 19:14:17.235  2628  2690 I sh_thread: thread name :pool-1-thread-3, index : 4
     02-11 19:14:17.236  2628  2689 I sh_thread: thread name :pool-1-thread-2, index : 5
     02-11 19:14:19.227  2628  2688 I sh_thread: thread name :pool-1-thread-1, index : 6
     02-11 19:14:19.236  2628  2690 I sh_thread: thread name :pool-1-thread-3, index : 7
     02-11 19:14:19.237  2628  2689 I sh_thread: thread name :pool-1-thread-2, index : 8
     02-11 19:14:21.228  2628  2688 I sh_thread: thread name :pool-1-thread-1, index : 9
     * 从中可以看出thread始终只有三个即pool-1-thread-1,2,3，没有创建新的线程，
     */
    private void testFixedThreadPool() {
        ExecutorService fixedThreadPool = Executors.newFixedThreadPool(3);
        for (int i = 0; i < 10; i++) {
            final int index = i;
            fixedThreadPool.execute(new Runnable() {
                @Override
                public void run() {
                    String threadName = Thread.currentThread().getName();
                    Log.i(TAG, "thread name :" + threadName + ", index : " + index);

                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    }


    /**
     * 创建只有一个线程的线程池,执行结果同上，
     * singleThreadPool实际上是fixedThreadPool线程数量为1的特殊情况，无其他特殊，故输出结果同上，不同的是testFixedThreadPool()方法中每次执行3个任务，此处是one by one
     */
    private void testSingleThreadPool() {
        ExecutorService singleThreadPool = Executors.newSingleThreadExecutor();
        for (int i = 0; i < 10; i++) {
            final int index = i;
            singleThreadPool.execute(new Runnable() {
                @Override
                public void run() {
                    String threadName = Thread.currentThread().getName();
                    Log.i(TAG, "thread name :" + threadName + ", index : " + index);

                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    }

    /**
     * 创建cacheThreadPool, 可理解为有空余线程则复用，否则创建的线程，而不是一味的创建的新线程
     * 输出如下：
     02-11 19:25:29.978  3677  3736 I sh_thread: thread name :pool-1-thread-1, index : 0
     02-11 19:25:30.977  3677  3736 I sh_thread: thread name :pool-1-thread-1, index : 1
     02-11 19:25:31.978  3677  3736 I sh_thread: thread name :pool-1-thread-1, index : 2
     02-11 19:25:32.979  3677  3742 I sh_thread: thread name :pool-1-thread-2, index : 3
     02-11 19:25:33.979  3677  3736 I sh_thread: thread name :pool-1-thread-1, index : 4
     02-11 19:25:34.979  3677  3742 I sh_thread: thread name :pool-1-thread-2, index : 5
     02-11 19:25:35.979  3677  3736 I sh_thread: thread name :pool-1-thread-1, index : 6
     02-11 19:25:36.983  3677  3760 I sh_thread: thread name :pool-1-thread-3, index : 7
     02-11 19:25:37.981  3677  3742 I sh_thread: thread name :pool-1-thread-2, index : 8
     02-11 19:25:38.981  3677  3736 I sh_thread: thread name :pool-1-thread-1, index : 9
     * 由于代码中不同的线程等待的时间不一样，故在没有空余线程提交任务会重新创建新的线程，从上面输出可以看出新建了3个线程
     */
    private void testCacheThreadPool() {
        ExecutorService cacheThreadPool = Executors.newCachedThreadPool();
        for (int i = 0; i < 10; i++) {
            final int index = i;
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            cacheThreadPool.execute(new Runnable() {
                @Override
                public void run() {
                    String threadName = Thread.currentThread().getName();
                    Log.i(TAG, "thread name :" + threadName + ", index : " + index);

                    try {
                        long time = index * 500;
                        Thread.sleep(time);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    }

    /**
     * 周期性执行任务，线程池的线程数为1, 如果不主动停止则会一直周期性的执行，即使应用在后台执行也会执行。
     */
    private void testSingleThreadScheduledExecutor() {
        ScheduledExecutorService singleThreadScheduledPool = Executors.newSingleThreadScheduledExecutor();
        // ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(3);    // 上面的线程池的大小是固定的1，而该行代码可以自定定义个数
        singleThreadScheduledPool.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                String threadName = Thread.currentThread().getName();
                Log.i(TAG, "thread name :" + threadName + "is executing....");
            }
        }, 1, 2, TimeUnit.SECONDS);
    }

    /**
     * 自定义带有优先级的thread pool executor
     *
     * 输出
     *
     02-11 19:51:12.068  5447  5477 I sh_thread: thread name :pool-1-thread-1, 优先级 : 0
     02-11 19:51:12.069  5447  5478 I sh_thread: thread name :pool-1-thread-2, 优先级 : 1
     02-11 19:51:12.074  5447  5479 I sh_thread: thread name :pool-1-thread-3, 优先级 : 2
     02-11 19:51:14.069  5447  5477 I sh_thread: thread name :pool-1-thread-1, 优先级 : 9
     02-11 19:51:14.070  5447  5478 I sh_thread: thread name :pool-1-thread-2, 优先级 : 8
     02-11 19:51:14.074  5447  5479 I sh_thread: thread name :pool-1-thread-3, 优先级 : 7
     02-11 19:51:16.069  5447  5477 I sh_thread: thread name :pool-1-thread-1, 优先级 : 6
     02-11 19:51:16.070  5447  5478 I sh_thread: thread name :pool-1-thread-2, 优先级 : 5
     02-11 19:51:16.074  5447  5479 I sh_thread: thread name :pool-1-thread-3, 优先级 : 4
     02-11 19:51:18.070  5447  5477 I sh_thread: thread name :pool-1-thread-1, 优先级 : 3
     由于有3个线程，故先执行0， 1， 2， 之后进入的线程则会比较优先级的来执行
     */
    private void testCusThreadPoolExecutor() {
//        ExecutorService priortyThreadPool = new ThreadPoolExecutor(3, 3, 0, TimeUnit.SECONDS, new PriorityBlockingQueue<Runnable>());
        ExecutorService priortyThreadPool = new CusThreadPoolExecutor(3, 3, 0, TimeUnit.SECONDS, new PriorityBlockingQueue<Runnable>());
        for (int i = 0; i < 10; i++) {
            final int priority = i;
            priortyThreadPool.execute(new PriorityRunnable(priority) {
                @Override
                public void doSth() {
                    String threadName = Thread.currentThread().getName();
                    Log.i(TAG, "thread name :" + threadName + ", 优先级 : " + priority);

                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    }
}
