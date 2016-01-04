package com.study.shenxing.caesar.customview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.view.animation.Transformation;

/**
 * Created by shenxing on 15/12/25.
 * meterial design风格的进度条
 */
public class CusLoadingView extends View {
    private static final float DEFAULT_RADIUS = 100 ;
    private float mRadius = DEFAULT_RADIUS ; // 圆形进度条的半径
    private Paint mPaint ;
    private float mStartAngle = 20 ;
    private static final float START_ANGLE = 20 ;
    private static final float END_ANGLE = 320 ;
    private float mDeltaAngle ;
    public CusLoadingView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public CusLoadingView(Context context) {
        super(context);
        init();
    }

    public CusLoadingView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        mPaint = new Paint() ;
        mPaint.setColor(getResources().getColor(android.R.color.holo_blue_dark));
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(3);
        mPaint.setAntiAlias(true);
        startLoadingAnim();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        RectF arcRect = new RectF(10, 10, mRadius - 10, mRadius - 10) ;
        canvas.save();
        canvas.drawArc(arcRect, mStartAngle, mStartAngle + mDeltaAngle, false, mPaint);
        canvas.restore();
        invalidate();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(resolveSize((int) mRadius, widthMeasureSpec), resolveSize((int) mRadius, heightMeasureSpec));
    }

    /**
     * 整个view的旋转
     */
    private Animation getCircleAnim() {
        RotateAnimation rotateAnimation = new RotateAnimation(0, 360f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f) ;
        rotateAnimation.setDuration(1000);
        rotateAnimation.setRepeatCount(Animation.INFINITE);
        rotateAnimation.setRepeatMode(Animation.RESTART);
        rotateAnimation.setInterpolator(new LinearInterpolator());
        return rotateAnimation ;
    }

    /**
     * 进度条的动画
     */
    private Animation getLoadingAnim() {
        CusLoadingAnimation loadingAnimation = new CusLoadingAnimation() ;
        loadingAnimation.setDuration(2000);
        loadingAnimation.setRepeatCount(Animation.INFINITE);
        loadingAnimation.setRepeatMode(Animation.REVERSE);
        loadingAnimation.setInterpolator(new AccelerateInterpolator());
        return loadingAnimation ;
    }

    private void startLoadingAnim() {
        AnimationSet as = new AnimationSet(false) ;
        as.addAnimation(getCircleAnim());
        as.addAnimation(getLoadingAnim());
        startAnimation(as);
    }

    private class CusLoadingAnimation extends Animation {
        @Override
        protected void applyTransformation(float interpolatedTime, Transformation t) {
            super.applyTransformation(interpolatedTime, t);

            float startTime1 = 0.0f ;
            float endtime1 = 1.0f ;

            if (startTime1 < interpolatedTime && interpolatedTime <= endtime1) {
                float newInterpolatedTime = (interpolatedTime - startTime1) / (endtime1 - startTime1) ;

                mDeltaAngle = START_ANGLE + (END_ANGLE - START_ANGLE) * newInterpolatedTime ;
            }

//            float startTime2 = 0.5f ;
//            float endtime2 = 1.0f ;
//
//            if (startTime2 < interpolatedTime && interpolatedTime <= endtime2) {
//                float newInterpolatedTime = (interpolatedTime - startTime2) / (endtime2 - startTime2) ;
//
//                float angle =  START_ANGLE + (END_ANGLE - START_ANGLE) * newInterpolatedTime ;
//                mStartAngle += angle ;
//            }


        }
    }
}
