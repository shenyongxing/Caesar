package com.study.shenxing.caesar.customdrawable;

import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.drawable.Drawable;
import android.util.Log;

/**
 * @author shenxing
 * @description 自定义圆角drawable
 * @date 2016/10/29
 */

public class RoundDrawable extends Drawable {

    private Bitmap mBitmap;
    private Paint mPaint;
    private RectF mRectF;
    private float mRadius; // 圆角半径

    public RoundDrawable(Bitmap bitmap, int roundRadius) {
        mBitmap = bitmap;
        mRadius = roundRadius;
        BitmapShader shader = new BitmapShader(mBitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setShader(shader);
    }

    @Override
    public void draw(Canvas canvas) {
        Log.i("sh", "draw: " + mRectF + ", " + mPaint + ", " + canvas);
        canvas.drawRoundRect(mRectF, mRadius, mRadius, mPaint);
    }

    @Override
    public void setAlpha(int alpha) {
        mPaint.setAlpha(alpha);
    }

    @Override
    public void setColorFilter(ColorFilter colorFilter) {
        mPaint.setColorFilter(colorFilter);
    }

    @Override
    public int getOpacity() {
        return PixelFormat.TRANSLUCENT;
    }

    // 注意：这个方法没有被回调导致rect为空，所以此处不可以获取宽高
    @Override
    public void setBounds(Rect bounds) {
        Log.i("sh", "setBounds(Rect) called");
        super.setBounds(bounds);
        mRectF = new RectF(bounds);
    }

    @Override
    public void setBounds(int left, int top, int right, int bottom) {
        Log.i("sh", "setBounds(int,int,int,int) called ");
        super.setBounds(left, top, right, bottom);
        mRectF = new RectF(left, top, right, bottom);
    }

    // 该方法默认返回-1，自定义最好提供实现类，以便在各种view中能正确显示
   @Override
    public int getIntrinsicWidth() {
        return mBitmap.getWidth();
    }

    @Override
    public int getIntrinsicHeight() {
        return mBitmap.getHeight();
    }
}
