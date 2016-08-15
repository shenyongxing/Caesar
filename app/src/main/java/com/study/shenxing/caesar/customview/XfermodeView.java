package com.study.shenxing.caesar.customview;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by shenxing on 16/8/13.
 * Xfermode 实践
 */
public class XfermodeView extends View {
    private Bitmap mSrc;
    private Bitmap mDst;
    private Paint mPaint = new Paint();
    private int mWidth = 400;
    private int mHeight = 400;
    public XfermodeView(Context context) {
        super(context);
        init(mWidth, mHeight);
    }

    public XfermodeView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(mWidth, mHeight);
    }

    public XfermodeView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(mWidth, mHeight);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
    }

    private void init(int w, int h) {
        /**
         * PorterDuffXfermode(PorterDuff.Mode.LIGHTEN)
         * PorterDuffXfermode(PorterDuff.Mode.DARKEN)
         * PorterDuffXfermode(PorterDuff.Mode.OVERLAY)
         * 这三种模式不支持硬件加速 ,具体查看
         * https://developer.android.com/guide/topics/graphics/hardware-accel.html
         */
        setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        mSrc = makeSrc(w, h);
        mDst = makeDst(w, h);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.saveLayer(0, 0, getWidth(), getHeight(), mPaint, Canvas.ALL_SAVE_FLAG);
        canvas.drawBitmap(mDst, 0, 0, mPaint);
        // ***************颜色叠加相关模式***************
//        mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.ADD));
//        mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.LIGHTEN));
//        mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DARKEN));
//        mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.OVERLAY));
//        mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.MULTIPLY));
//        mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SCREEN));

        // ****************src模式*********************
//        mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.ADD));
//        mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.ADD));
//        mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.ADD));
//        mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.ADD));
//        mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.ADD));

        // ****************dst模式**********************
//        mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.ADD));
//        mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.ADD));
//        mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.ADD));
//        mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.ADD));
        canvas.drawBitmap(mSrc, mWidth / 2, mHeight / 2, mPaint);
        mPaint.setXfermode(null);
    }

    private Bitmap makeSrc(int w, int h) {
        Bitmap src = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(src);
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(0xFF66AAFF);
        canvas.drawRect(0, 0, w, h, paint);
        return src;
    }

    private Bitmap makeDst(int w, int h) {
        Bitmap dst = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(dst);
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(0xFFFFCC44);
        canvas.drawOval(new RectF(0, 0, w, h), paint);
        return dst;
    }
}
