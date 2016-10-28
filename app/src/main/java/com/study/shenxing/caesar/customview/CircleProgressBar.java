package com.study.shenxing.caesar.customview;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AccelerateInterpolator;

import com.study.shenxing.caesar.R;
import com.study.shenxing.caesar.utils.DrawUtils;

/**
 * 自定义圆形进度条
 */
public class CircleProgressBar extends View {
    private static final int DEFAULT_CIRCLE_BG_COLOR = Color.BLACK;
    private static final int DEFAULT_CIRCLE_PROGRESS_COLOR = Color.BLUE;
    private static final int DEFAULT_CIRCLE_PROGRESS_WIDTH = DrawUtils.dip2px(5);
    private static final int DEFAULT_BALL_RADIUS = DrawUtils.dip2px(10);
    private int mBgProgressColor; // 进度条背景色
    private int mProgressColor;   // 进度条颜色
    private int mBorderWidth;   // 进度条宽度
    private int mBallRadius;     // 进度条头部的小球半径
    private Paint mBgProgressPaint;
    private Paint mProgressPaint;
    private Paint mBallPaint;
    private int mRadius;
    private int mX;
    private int mY;
    private RectF mRectF;
    private int mStartAngle;
    private int mEndAngle;

    public CircleProgressBar(Context context) {
        super(context);
        init(null, 0);
    }

    public CircleProgressBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs, 0);
    }

    public CircleProgressBar(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(attrs, defStyle);
    }

    private void init(AttributeSet attrs, int defStyle) {
        final TypedArray a = getContext().obtainStyledAttributes(
                attrs, R.styleable.CircleProgressBar2, defStyle, 0);
        mBgProgressColor = a.getColor(R.styleable.CircleProgressBar2_progress_bg_color, DEFAULT_CIRCLE_BG_COLOR);
        mProgressColor = a.getColor(R.styleable.CircleProgressBar2_progress_color, DEFAULT_CIRCLE_PROGRESS_COLOR);
        mBorderWidth = a.getDimensionPixelSize(R.styleable.CircleProgressBar2_progress_width, DEFAULT_CIRCLE_PROGRESS_WIDTH);
        mBallRadius = a.getDimensionPixelSize(R.styleable.CircleProgressBar2_ball_radius, DEFAULT_BALL_RADIUS);
        a.recycle();

        initPaint();
    }

    private void initPaint() {
        mProgressPaint = new Paint();
        mProgressPaint.setStyle(Paint.Style.STROKE);
        mProgressPaint.setStrokeWidth(mBorderWidth);
        mProgressPaint.setColor(mProgressColor);
        mProgressPaint.setAntiAlias(true);
        mBgProgressPaint = new Paint();
        mBgProgressPaint.setStyle(Paint.Style.STROKE);
        mBgProgressPaint.setStrokeWidth(mBorderWidth);
        mBgProgressPaint.setColor(mBgProgressColor);
        mBgProgressPaint.setAntiAlias(true);
        mBallPaint = new Paint();
        mBallPaint.setStyle(Paint.Style.FILL);
        mBallPaint.setStrokeWidth(mBallRadius);
        mBallPaint.setColor(mProgressColor);
        mBallPaint.setAntiAlias(true);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mRadius = Math.min(w, h) / 2 - mBallRadius;
        mX = w / 2;
        mY = h / 2;
        mRectF = new RectF(mX - mRadius, mY - mRadius, mX + mRadius, mY + mRadius);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.rotate(-90, mX, mY);
        drawBackground(canvas);
        drawProgress(canvas);
        drawBall(canvas);
    }

    private void drawBackground(Canvas canvas) {
        canvas.drawCircle(mX, mY, mRadius, mBgProgressPaint);
    }

    private void drawProgress(Canvas canvas) {
        canvas.drawArc(mRectF, mStartAngle, mEndAngle, false, mProgressPaint);
    }

    private void drawBall(Canvas canvas) {
        double radians = mEndAngle * Math.PI / 180;
        canvas.drawCircle((float) (mX + mRadius * Math.cos(radians)), (float) (mY + mRadius * Math.sin(radians)), mBallRadius, mBallPaint);
    }

    /**
     * 正向进度动画
     */
    public void startForwardAnim() {
        ValueAnimator va = ValueAnimator.ofFloat(0.0f, 1.0f);
        va.setDuration(800);
        va.setInterpolator(new AccelerateDecelerateInterpolator());
        va.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float fractionValue = (float) animation.getAnimatedValue();
                mEndAngle = (int) (360 * fractionValue);
                invalidate();
            }
        });

        if (va.isRunning()) {
            va.end();
        }
        va.start();
    }

    /**
     * 反向进度动画
     */
    public void startBackwardAnim() {
        final int currentAngle = mEndAngle;
        ValueAnimator va = ValueAnimator.ofFloat(0.0f, 1.0f);
        va.setDuration(800);
        va.setInterpolator(new AccelerateInterpolator());
        va.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float fractionValue = (float) animation.getAnimatedValue();
                mEndAngle = (int) (currentAngle - 360 * fractionValue);
                invalidate();
            }
        });

        if (va.isRunning()) {
            va.end();
        }
        va.start();
    }
}
