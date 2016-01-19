package com.study.shenxing.caesar.customview;

import android.content.Context;
import android.content.res.TypedArray;
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
    private Drawable mBackground;   // 背景图
    private int mViewWidths ;
    private int mViewHeight ;
    private float mCurAngle = 30 ;
    private Paint mPaint;

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

        mBackground = getResources().getDrawable(R.drawable.optimization_greentake);
        mViewWidths = mBackground.getIntrinsicWidth() ;
        mViewHeight = mBackground.getIntrinsicHeight() ;
        mBackground.setBounds(0, 0, mViewWidths, mViewHeight);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // 绘制扇形遮罩
        mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.S)) ;
        canvas.drawArc(new RectF(0, 0, getWidth(), getHeight()), 0, mCurAngle, true, mPaint);
        mPaint.setXfermode(null) ;
        mBackground.draw(canvas);
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
