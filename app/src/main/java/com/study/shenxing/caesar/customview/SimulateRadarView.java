package com.study.shenxing.caesar.customview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Transformation;

import com.study.shenxing.caesar.R;

/**
 * 扫描垃圾动画view
 */
public class SimulateRadarView extends View {
    private Bitmap mBackground;   // 背景图
    private int mViewWidths ;
    private int mViewHeight ;
    private float mCurAngle = 30 ;
    private Paint mPaint;
    private PorterDuffXfermode mDrawMode = new PorterDuffXfermode(PorterDuff.Mode.SRC_IN) ;
    private CusAnimation mCusAnim ;

    public SimulateRadarView(Context context) {
        super(context);
        init();
    }

    public SimulateRadarView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public SimulateRadarView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    private void init() {
        mPaint = new Paint();
        mPaint.setFlags(Paint.ANTI_ALIAS_FLAG);
        mPaint.setStyle(Paint.Style.FILL);

        mBackground = BitmapFactory.decodeResource(getResources(), R.drawable.optimization_greentake) ;
        mViewWidths = mBackground.getWidth() ;
        mViewHeight = mBackground.getHeight() ;

        mCusAnim = new CusAnimation() ;
        mCusAnim.setDuration(2000);
        mCusAnim.setRepeatMode(Animation.RESTART);
        mCusAnim.setRepeatCount(Animation.INFINITE);
        startAnimation(mCusAnim);
    }

    /**
     * 开始清理动画
     */
    public void startClearAnimation() {
        startAnimation(mCusAnim);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        RectF arcRect = new RectF(0, 0, getWidth(), getHeight()) ;
        canvas.saveLayer(0, 0, getWidth(), getHeight(), null,
                Canvas.MATRIX_SAVE_FLAG |
                        Canvas.CLIP_SAVE_FLAG |
                        Canvas.HAS_ALPHA_LAYER_SAVE_FLAG |
                        Canvas.FULL_COLOR_LAYER_SAVE_FLAG |
                        Canvas.CLIP_TO_LAYER_SAVE_FLAG);
        canvas.drawArc(arcRect, 0, mCurAngle, true, mPaint);
        mPaint.setXfermode(mDrawMode) ;
        // 绘制扇形遮罩
        canvas.drawBitmap(mBackground, 0, 0, mPaint);
        mPaint.setXfermode(null) ;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(resolveSize(mViewWidths, widthMeasureSpec), resolveSize(mViewHeight, heightMeasureSpec));
    }

    /**
     * 自定义分段动画
     */
    private class CusAnimation extends Animation {

        @Override
        protected void applyTransformation(float interpolatedTime, Transformation t) {
            super.applyTransformation(interpolatedTime, t);

            mCurAngle = 360 * interpolatedTime ;
            invalidate();
        }
    }
}
