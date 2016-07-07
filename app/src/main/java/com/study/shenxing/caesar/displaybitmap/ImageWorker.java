package com.study.shenxing.caesar.displaybitmap;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;
import android.widget.ImageView;

/**
 * Created by shenxing on 16/6/22.
 */
public class ImageWorker {
    private ImageCache mImageCache;
    /**
     * load bitmap for imageview
     * @param data
     * @param imageView
     * @param listener
     */
    public void loadImage(Object data, ImageView imageView, OnImageLoadListener listener) {
        if (data == null) {
            return;
        }

        BitmapDrawable value = null;
        if (mImageCache != null) {
            value = mImageCache.getBitmapFromMemoryCache(String.valueOf(data));
        }

        if (value != null) {
            imageView.setImageDrawable(value);
            if (listener != null) {
                listener.onImageLoaded(true);
            }
        } /*else if () {

        }*/
    }

    public void loadImage(Object data, ImageView imageView) {
        loadImage(data, imageView, null);
    }
    /**
     * interface definition for callback on image load successfully.
     */
    public interface OnImageLoadListener {
        public void onImageLoaded(boolean success);
    }

    private class BitmapWorkerTask extends AsyncTask<Void, Void, BitmapDrawable> {

        @Override
        protected BitmapDrawable doInBackground(Void... params) {
            return null;
        }
    }
}
