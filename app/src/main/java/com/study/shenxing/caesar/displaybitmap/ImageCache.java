package com.study.shenxing.caesar.displaybitmap;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Environment;
import android.os.StatFs;
import android.support.v4.util.LruCache;
import android.util.Log;

import com.study.shenxing.caesar.BuildConfig;

import java.io.File;
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.ref.SoftReference;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 * Created by shenxing on 16/6/19.
 */
public class ImageCache {
    private static final String TAG = "ImageCache";

    // Default memory cache size in kilobytes
    private static final int DEFAULT_MEM_CACHE_SIZE = 1024 * 5; // 5MB

    // Default disk cache size in bytes
    private static final int DEFAULT_DISK_CACHE_SIZE = 1024 * 1024 * 10; // 10MB

    // Compression settings when writing images to disk cache
    private static final Bitmap.CompressFormat DEFAULT_COMPRESS_FORMAT = Bitmap.CompressFormat.JPEG;
    private static final int DEFAULT_COMPRESS_QUALITY = 70;
    private DiskLruCache mDiskLruCache;
    private LruCache<String, BitmapDrawable> mMemoryCache;

    // Constants to easily toggle various caches
    private static final boolean DEFAULT_MEM_CACHE_ENABLED = true;
    private static final boolean DEFAULT_DISK_CACHE_ENABLED = true;
    private static final boolean DEFAULT_INIT_DISK_CACHE_ON_CREATE = false;

    private ImageCacheParams mCacheParams;

    private Set<SoftReference<Bitmap>> mReusableBitmaps;
    private boolean mDiskCacheStarting = true;  // diskcache 正在初始化中...
    private static final int DISK_CACHE_INDEX = 0;

    private void init(ImageCacheParams params) {
        mCacheParams = params;
        if (mCacheParams.memoryCacheEnabled) {
            if (BuildConfig.DEBUG) {
                Log.d(TAG, "Memory cache created (size = " + mCacheParams.memCacheSize + ")");
            }

            if (Utils.hasHoneycomb()) {
                mReusableBitmaps =
                        Collections.synchronizedSet(new HashSet<SoftReference<Bitmap>>());
            }

            mMemoryCache = new LruCache<String, BitmapDrawable>(mCacheParams.memCacheSize) {
                @Override
                protected void entryRemoved(boolean evicted, String key, BitmapDrawable oldValue, BitmapDrawable newValue) {
                    super.entryRemoved(evicted, key, oldValue, newValue);

                    if (oldValue instanceof RecyclingBitmapDrawable) {
                        ((RecyclingBitmapDrawable) oldValue).setIsCached(false);
                    } else {
                        // 当bitmapdrawable从memroy cache中删除时加入的set集合中保存, 因为该bitmap还有被重用的可能
                        if (Utils.hasHoneycomb()) {
                            mReusableBitmaps.add(new SoftReference<Bitmap>(oldValue.getBitmap()));
                        }
                    }
                }

                @Override
                protected int sizeOf(String key, BitmapDrawable value) {
                    final int bitmapSize = getBitmapSize(value) / 1024;
                    return bitmapSize == 0 ? 1 : bitmapSize;
                }
            };

        }

        if (mCacheParams.initDiskCacheOnCreate) {
            initDiskCache();
        }
    }

    private final Object mDiskCacheLock = new Object();
    /**
     * init disk cache
     */
    private void initDiskCache() {
        synchronized (mDiskCacheLock) {
            if (mDiskLruCache == null || mDiskLruCache.isClosed()) {
                File diskCacheDir = mCacheParams.diskCacheDir;
                if (mCacheParams.diskCacheEnabled && diskCacheDir != null) {
                    if (!diskCacheDir.exists()) {
                        diskCacheDir.mkdirs();
                    }

                    if (getUsableSpace(diskCacheDir) > mCacheParams.diskCacheSize) {
                        try {
                            mDiskLruCache = DiskLruCache.open(diskCacheDir, 1, 1, mCacheParams.diskCacheSize);
                            if (BuildConfig.DEBUG) {
                                Log.d(TAG, "Disk cache initialized");
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                            mCacheParams.diskCacheDir = null;
                            Log.e(TAG, "initDiskCache - " + e);
                        }
                    }

                }
            }
            mDiskCacheStarting = false;
            mDiskCacheLock.notifyAll();

        }
    }

    /**
     * Check how much usable space is available at a given path.
     *
     * @param path The path to check
     * @return The space available in bytes
     */
    @TargetApi(Build.VERSION_CODES.GINGERBREAD)
    public static long getUsableSpace(File path) {
        if (Utils.hasGingerbread()) {
            return path.getUsableSpace();
        }
        final StatFs stats = new StatFs(path.getPath());
        return (long) stats.getBlockSize() * (long) stats.getAvailableBlocks();
    }


    /**
     * Get the size in bytes of a bitmap in a BitmapDrawable. Note that from Android 4.4 (KitKat)
     * onward this returns the allocated memory size of the bitmap which can be larger than the
     * actual bitmap data byte count (in the case it was re-used).
     *
     * @param value
     * @return size in bytes
     */
    @TargetApi(Build.VERSION_CODES.KITKAT)
    public static int getBitmapSize(BitmapDrawable value) {
        Bitmap bitmap = value.getBitmap();

        // From KitKat onward use getAllocationByteCount() as allocated bytes can potentially be
        // larger than bitmap byte count.
        if (Utils.hasKitKat()) {
            return bitmap.getAllocationByteCount();
        }

        if (Utils.hasHoneycombMR1()) {
            return bitmap.getByteCount();
        }

        // Pre HC-MR1
        return bitmap.getRowBytes() * bitmap.getHeight();
    }

    /**
     * Image缓存参数
     */
    private class ImageCacheParams {
        public int memCacheSize = DEFAULT_MEM_CACHE_SIZE;
        public int diskCacheSize = DEFAULT_DISK_CACHE_SIZE;
        public File diskCacheDir;
        public Bitmap.CompressFormat compressFormat = DEFAULT_COMPRESS_FORMAT;
        public int compressQuality = DEFAULT_COMPRESS_QUALITY;
        public boolean memoryCacheEnabled = DEFAULT_MEM_CACHE_ENABLED;
        public boolean diskCacheEnabled = DEFAULT_DISK_CACHE_ENABLED;
        public boolean initDiskCacheOnCreate = DEFAULT_INIT_DISK_CACHE_ON_CREATE;

        public ImageCacheParams(Context context, String cacheDir) {
            diskCacheDir = getDiskCacheDir(context, cacheDir);
        }

        public void setMemCacheSizePercent(float percent) {
            if (percent < 0.01f || percent > 0.8) {
                throw new IllegalArgumentException("setMemCacheSizePercent - percent must be "
                        + "between 0.01 and 0.8 (inclusive)");
            }

            memCacheSize = Math.round(Runtime.getRuntime().maxMemory() * percent) / 1024;
        }
    }

    /**
     * 获取sd缓存路径
     * @param context
     * @param uniqueName
     * @return
     */
    private File getDiskCacheDir(Context context, String uniqueName) {
        String cacheFile = Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState()) || !isExternalStorageRemovable() ?
                getExternalCacheFile(context).getPath() : context.getCacheDir().getPath();
        return new File(cacheFile + File.separator + uniqueName);
    }

    private File getExternalCacheFile(Context context) {
        // 对于android api 8 以前的版本需要自己指定
        return context.getExternalCacheDir();
    }

    /**
     * Check if external storage is built-in or removable.
     *
     * @return True if external storage is removable (like an SD card), false
     *         otherwise.
     */
    @TargetApi(Build.VERSION_CODES.GINGERBREAD)
    public static boolean isExternalStorageRemovable() {
        if (Utils.hasGingerbread()) {
            return Environment.isExternalStorageRemovable();
        }
        return true;
    }

    /**
     * adds a bitmap to both memory and disk cache
     * @param data
     * @param bitmapDrawable
     */
    private void addBitmapToCache(String data, BitmapDrawable bitmapDrawable) {
        if (data == null || bitmapDrawable == null) {
            return;
        }

        // add to memory cache
        if (mMemoryCache != null) {
            if (bitmapDrawable instanceof RecyclingBitmapDrawable) {
                ((RecyclingBitmapDrawable) bitmapDrawable).setIsCached(true);
            }
            mMemoryCache.put(data, bitmapDrawable);
        }

        synchronized (mDiskCacheLock) {
            if (mDiskLruCache != null) {
                String key = hashKeyForDisk(data);
                OutputStream out = null;
                try {
                    DiskLruCache.Snapshot snapshot = mDiskLruCache.get(key);
                    if (snapshot == null) {
                        final DiskLruCache.Editor editor = mDiskLruCache.edit(key);
                        if (editor != null) {
                            out = editor.newOutputStream(DISK_CACHE_INDEX);
                            bitmapDrawable.getBitmap().compress(mCacheParams.compressFormat,
                                    mCacheParams.compressQuality, out);
                            editor.commit();
                            out.close();
                        }
                    } else {
                        snapshot.getInputStream(DISK_CACHE_INDEX).close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    try {
                        if (out != null) {
                            out.close();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }

        }
    }

    /**
     * A hashing method that changes a string (like a URL) into a hash suitable for using as a
     * disk filename.
     */
    public static String hashKeyForDisk(String key) {
        String cacheKey;
        try {
            final MessageDigest mDigest = MessageDigest.getInstance("MD5");
            mDigest.update(key.getBytes());
            cacheKey = bytesToHexString(mDigest.digest());
        } catch (NoSuchAlgorithmException e) {
            cacheKey = String.valueOf(key.hashCode());
        }
        return cacheKey;
    }

    private static String bytesToHexString(byte[] bytes) {
        // http://stackoverflow.com/questions/332079
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

    /**
     *
     * @param data
     */
    public BitmapDrawable getBitmapFromMemoryCache(String data) {
        BitmapDrawable mMemBitmapDrawable = null;
        if (mMemoryCache != null) {
            mMemBitmapDrawable = mMemoryCache.get(data);
        }
        return mMemBitmapDrawable;
    }

    public Bitmap getBitmapFromDiskCache(String data) {
        Bitmap bitmap = null;
        String key = hashKeyForDisk(data);
        synchronized (mDiskCacheLock) {
            while (mDiskCacheStarting) {
                try {
                    mDiskCacheLock.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            if (mDiskLruCache != null) {
                InputStream inputStream = null;
                try {
                    DiskLruCache.Snapshot snapshot = mDiskLruCache.get(key);
                    if (snapshot != null) {
                        inputStream = snapshot.getInputStream(DISK_CACHE_INDEX);
                        if (inputStream != null) {
                            FileDescriptor fd = ((FileInputStream) inputStream).getFD();
                            // 根据fd解析出bitmap
//                            bitmap = ; 官网有优化操作,参看官网相关部分代码
                            bitmap = BitmapFactory.decodeStream(inputStream);
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    try {
                        if (inputStream != null) {
                            inputStream.close();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }

        }
        return bitmap;
    }

    private Bitmap getBitmapFromReuseableSet(BitmapFactory.Options options) {
        Bitmap bitmap = null;
        if (mReusableBitmaps != null && !mReusableBitmaps.isEmpty()) {
            synchronized (mReusableBitmaps) {
                Iterator<SoftReference<Bitmap>> iterator = mReusableBitmaps.iterator();
                Bitmap item;
                while (iterator.hasNext()) {
                    item = iterator.next().get();
                    if (null != item && item.isMutable()) {

                        iterator.remove();
                    } else {
                        iterator.remove();
                    }


                }
            }
        }
        return bitmap;
    }

}
