package com.study.shenxing.caesar.imageloader;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.NonNull;
import android.util.AndroidRuntimeException;
import android.util.Log;
import android.util.LruCache;
import android.widget.ImageView;

import com.study.shenxing.caesar.R;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.concurrent.Executor;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author shenxing
 * @description imageloader of myself
 * @date 18/03/2017
 */

public class ImageLoader {

    public static final String TAG = "sh";

    public static final int MESSAGE_POST_RESULT = 1;

    public static final int CPU_COUNT = Runtime.getRuntime()
            .availableProcessors();
    public static final int CORE_POOL_SIZE = CPU_COUNT + 1;
    public static final int MAX_POOL_SIZE = 2 * CORE_POOL_SIZE + 1;
    private static final long KEEP_ALIVE = 10L;

    private static final int TAG_KEY_URI = R.id.imageloader_uri;
    public static final long DISK_CACHE_SIZE = 1024 * 1024 * 50;    // sd卡缓存阈值
    private static final int IO_BUFFER_SIZE = 8 * 1024;
    private static final int DISK_CACHE_INDEX = 0;

    private boolean mIsDiskLruCacheCreated = false;

    public static final ThreadFactory mThreadFactory = new ThreadFactory() {

        private final AtomicInteger mCount = new AtomicInteger(1);
        @Override
        public Thread newThread(@NonNull Runnable r) {
            return new Thread(r, "ImageLoader#" + mCount.getAndIncrement());
        }
    };

    private static final Executor THREAD_POOL_EXECUTOR = new ThreadPoolExecutor(
            CORE_POOL_SIZE, MAX_POOL_SIZE,
            KEEP_ALIVE, TimeUnit.SECONDS,
            new LinkedBlockingQueue<Runnable>(), mThreadFactory);

    /**
     * interact with the background thread
     */
    private Handler mMainHanlder = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            int what = msg.what;
            if (what == MESSAGE_POST_RESULT) {
                LoaderResult result = (LoaderResult) msg.obj;
                ImageView imageView = result.mImageView;
                String tag = (String) imageView.getTag(TAG_KEY_URI);
                if (result.mUrl.equals(tag)) {
                    imageView.setImageBitmap(result.mBitmap);
                } else {
                    Log.i(TAG, "url has changed so ignored it");
                }
            }
        }
    };

    private Context mContext;
    private ImageResizer mImageResizer = new ImageResizer(); // 图片采样util
    private LruCache<String, Bitmap> mMemoryCache;  // 内存缓存
    private DiskLruCache mDiskLruCache; // sd卡缓存

    private static ImageLoader sInstance;

    public static ImageLoader getInstance(Context context) {
        if (sInstance == null) {
            synchronized (ImageLoader.class) {
                if (sInstance == null) {
                    sInstance = new ImageLoader(context);
                }
            }
        }

        return sInstance;
    }

    private ImageLoader(Context context) {
        mContext = context.getApplicationContext();
        int maxMemory = (int) (Runtime.getRuntime().maxMemory() / 1024);
        int cacheSize = maxMemory / 8;
        mMemoryCache = new LruCache<String, Bitmap>(cacheSize) {
            @Override
            protected int sizeOf(String key, Bitmap value) {
                // 注意这个地方不要返回错误的值
                // 返回value.getByteCount()和value.getByteCount() * value.getHeight() / 1024，会有不同的效果，明显后者显示更快
                return value.getByteCount() * value.getHeight() / 1024;
            }
        };

        File diskCacheDir = getDiskCacheDir(mContext, "bitmaps");
        if (!diskCacheDir.exists()) {
            diskCacheDir.mkdirs();
        }
        try {
            mDiskLruCache = DiskLruCache.open(diskCacheDir, 1, 1, DISK_CACHE_SIZE);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 从内存缓存中获取图片
     * @param key
     * @return
     */
    private Bitmap getBitmapFromMemoryCache(String key) {
        return mMemoryCache.get(key);
    }

    /**
     * 添加图片到内存缓存
     * @param bitmap
     */
    private void addBitmapToMemoryCache(String key, Bitmap bitmap) {
        if (getBitmapFromMemoryCache(key) == null) {
            Log.i(TAG, "addBitmapToMemoryCache: ");
            mMemoryCache.put(key, bitmap);
        }
    }

    public void displayImage(String url, ImageView view) {
        displayImage(url, view, 0, 0);  // reqWidth, reqHeight为0时图片以原图显示
    }

    public void displayImage(final String url, final ImageView view, final int reqWidth, final int reqHeight) {
        String cacheKey = hashKeyFromUrl(url);
        view.setTag(TAG_KEY_URI, url);
        Bitmap bitmap = getBitmapFromMemoryCache(cacheKey);
        if (bitmap != null) {
            view.setImageBitmap(bitmap);
            return;
        }

        Runnable loadBitmapTask = new Runnable() {
            @Override
            public void run() {
                Log.i(TAG, "load bitmap task run: ");
                Bitmap resultBitmap = loadBitmap(url, reqWidth, reqHeight);

                if (resultBitmap != null) {
                    LoaderResult result = new LoaderResult(view, url, resultBitmap);
                    mMainHanlder.obtainMessage(MESSAGE_POST_RESULT, result).sendToTarget();
                }
            }
        };

        THREAD_POOL_EXECUTOR.execute(loadBitmapTask);
    }

    /**
     * 从disk或者网络获取图片
     * @param url
     * @param reqWidth
     * @param reqHeight
     * @return
     */
    private Bitmap loadBitmap(String url, int reqWidth, int reqHeight) {
        Log.i(TAG, "loadBitmap: ");
        String cacheKey = hashKeyFromUrl(url);

        Bitmap bitmap = getBitmapFromMemoryCache(cacheKey);
        if (bitmap != null) {
            Log.d(TAG, "loadBitmapFromMemCache,url:" + cacheKey);
            return bitmap;
        }

        bitmap = getBitmapFromDiskCache(cacheKey, reqWidth, reqHeight);
        if (bitmap != null) {
            Log.i(TAG, "get bitmap from disk");
            return bitmap;
        }

        bitmap = downloadBitmapFromHttp(url, reqWidth, reqHeight);   // 下载到流里 然后存入sd卡，在从sd卡获取图片

        if (bitmap == null) {
            bitmap = downloadBitmapFromUrl(url);
        }
        // 可能由于disk缓存不可用，则直接从url下载为图片返回
        return bitmap;
    }

    private File getDiskCacheDir(Context context, String uniqueName) {
        boolean externalStorageAvailable = Environment
                .getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
        final String cachePath;
        if (externalStorageAvailable) {
            cachePath = context.getExternalCacheDir().getPath();    // sd目录 引用删除该缓存依然存在，其他应用可读
        } else {
            cachePath = context.getCacheDir().getPath();  // sd目录上应用内存缓存目录 其他应用无法读取
        }

        return new File(cachePath + File.separator + uniqueName);
    }

    /**
     * 从磁盘缓存获取缓存图片
     * @param key
     * @param reqWidth
     * @param reqHeight
     */
    private Bitmap getBitmapFromDiskCache(String key, int reqWidth, int reqHeight) {
        checkThread();
        Bitmap bitmap = null;
        try {
            DiskLruCache.Snapshot snapshot = mDiskLruCache.get(key);
            if (snapshot != null) {
                FileInputStream inputStream = (FileInputStream) snapshot.getInputStream(0);
                FileDescriptor fd = inputStream.getFD();
                bitmap = mImageResizer.decodeSampledBitmapFromFileDescriptor(fd, reqWidth, reqHeight);
                if (bitmap != null) {
                    addBitmapToMemoryCache(key, bitmap);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        Log.i(TAG, "getBitmapFromDiskCache Bitmap:" + bitmap);
        return bitmap;
    }


    /**
     * 下载网络图片
     * @param urlString
     * @param reqWidth
     * @param reqHeight
     * @return
     */
    private Bitmap downloadBitmapFromHttp(String urlString, int reqWidth, int reqHeight) {
        Log.i(TAG, "downloadBitmapFromUrl: ");
        String key = hashKeyFromUrl(urlString);
        try {
            DiskLruCache.Editor editor = mDiskLruCache.edit(key);
            if (editor != null) {
                OutputStream outputStream = editor.newOutputStream(DISK_CACHE_INDEX);
                if (downloadUrlToStream(urlString, outputStream)) {
                    editor.commit();
                } else {
                    editor.abort();
                }
                mDiskLruCache.flush();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return getBitmapFromDiskCache(key, reqWidth, reqHeight);
    }

    private void checkThread() {
        if (Looper.getMainLooper() == Looper.myLooper()) {
            throw new AndroidRuntimeException("Please do not handle I/O operation in UI thread");
        }
    }

    /**
     * 返回url的唯一标识码 通过md5算法产生
     * @param url
     * @return
     */
    public String hashKeyFromUrl(String url) {
        String cacheKey = null;
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("MD5");
            messageDigest.update(url.getBytes());
            cacheKey = bytesToHexString(messageDigest.digest());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            cacheKey = String.valueOf(url.hashCode());
        }

        return cacheKey;
    }

    private String bytesToHexString(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < bytes.length; i++) {
            String hex = Integer.toHexString(0xFF & bytes[i]);
            if (hex.length() == 1) {
                sb.append('0');
            }
            sb.append(hex);
        }
        return sb.toString();
    }



    public boolean downloadUrlToStream(String urlString, OutputStream outputStream) {
        HttpURLConnection urlConnection = null;
        BufferedOutputStream out = null;
        BufferedInputStream in = null;

        try {
            final URL url = new URL(urlString);
            urlConnection = (HttpURLConnection) url.openConnection();
            in = new BufferedInputStream(urlConnection.getInputStream(),
                    IO_BUFFER_SIZE);
            out = new BufferedOutputStream(outputStream, IO_BUFFER_SIZE);

            int b;
            while ((b = in.read()) != -1) {
                out.write(b);
            }
            return true;
        } catch (IOException e) {
            Log.e(TAG, "downloadBitmap failed." + e);
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            MyUtils.close(out);
            MyUtils.close(in);
        }
        return false;
    }



    private Bitmap downloadBitmapFromUrl(String urlString) {
        Bitmap bitmap = null;
        HttpURLConnection urlConnection = null;
        BufferedInputStream in = null;

        try {
            final URL url = new URL(urlString);
            urlConnection = (HttpURLConnection) url.openConnection();
            in = new BufferedInputStream(urlConnection.getInputStream(),
                    IO_BUFFER_SIZE);
            bitmap = BitmapFactory.decodeStream(in);
        } catch (final IOException e) {
            Log.e(TAG, "Error in downloadBitmap: " + e);
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            MyUtils.close(in);
        }
        return bitmap;
    }



    /**
     * 用于在handler消息队列里传递多个信息的载体
     */
    private static class LoaderResult {
        public ImageView mImageView;
        public String mUrl;
        public Bitmap mBitmap;

        public LoaderResult(ImageView imageView, String url, Bitmap bitmap) {
            mImageView = imageView;
            mUrl = url;
            mBitmap = bitmap;
        }
    }
}
