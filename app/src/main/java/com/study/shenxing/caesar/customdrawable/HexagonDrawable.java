package com.study.shenxing.caesar.customdrawable;

import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.drawable.Drawable;
import android.util.Log;

/**
 * @author shenxing
 * @description 自定义drawable
 * @date 2016/10/29
 */

// 此处构建一个竖形的正六边形，若正六边形的宽为L 则高为4/sqrt(3)*L,根据这个比值来确定最大程度的占据整个Drawable

public class HexagonDrawable extends Drawable {

    private Bitmap mBitmap;
    private Paint mPaint;
    private RectF mRectF;
    private Path mPath = new Path();

    public HexagonDrawable(Bitmap bitmap) {
        mBitmap = bitmap;
        // 使用clamp时有锯齿出现
        BitmapShader shader = new BitmapShader(mBitmap, Shader.TileMode.MIRROR, Shader.TileMode.MIRROR);
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setShader(shader);
    }

    private void initPath() {
        float width; // 正六边形宽度（注意不是变长）
        float ratio = mRectF.width() / mRectF.height();
        if (ratio > Math.sqrt(3) / 2) {
            // 高度不够，计算以高度为准

            width = (float) (mRectF.height() * Math.sqrt(3) / 2);  // 以drawable高度为正六边形的宽度
        } else {
            // 宽度不够，计算以宽度为准
            width = mRectF.width();
        }
        mPath.moveTo(width / 2, 0);
        mPath.lineTo(width, (float) (width * Math.sqrt(3) / 6));
        mPath.lineTo(width, (float) (width * Math.sqrt(3) / 2));
        mPath.lineTo(width / 2, (float) (width * 2 / Math.sqrt(3)));
        mPath.lineTo(0, (float) (width * Math.sqrt(3) / 2));
        mPath.lineTo(0, (float) (width * Math.sqrt(3) / 6));
        mPath.close();
    }

    @Override
    public void draw(Canvas canvas) {
        Log.i("sh", "draw: " + mRectF + ", " + mPaint + ", " + canvas);
        canvas.drawPath(mPath, mPaint);
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
        initPath();
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
