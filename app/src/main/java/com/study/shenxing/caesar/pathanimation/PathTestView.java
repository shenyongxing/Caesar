package com.study.shenxing.caesar.pathanimation;

import android.animation.AnimatorSet;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.PointF;
import android.graphics.drawable.Drawable;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.LinearInterpolator;

import com.study.shenxing.caesar.R;

import java.security.cert.PolicyNode;
import java.util.ArrayList;
import java.util.List;

/**
 * path 贝塞尔曲线实践
 */
public class PathTestView extends View {
    private String mExampleString; // TODO: use a default from R.string...
    private int mExampleColor = Color.RED; // TODO: use a default from R.color...
    private float mExampleDimension = 0; // TODO: use a default from R.dimen...
    private Drawable mExampleDrawable;

    private TextPaint mTextPaint;
    private float mTextWidth;
    private float mTextHeight;

    public static final float C = 0.551915024494f;
    public static final int MULTIFACTOR = 100;

    public PathTestView(Context context) {
        super(context);
        init(null, 0);
    }

    public PathTestView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs, 0);
    }

    public PathTestView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(attrs, defStyle);
    }

    private void initPaint() {
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setColor(Color.BLUE);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setStrokeWidth(1.0f);
    }

    private Paint mPaint;
    private Path mPath = new Path();
    private PointF p0 = new PointF(0, 1);
    private PointF p1 = new PointF(C, 1);
    private PointF p2 = new PointF(1, C);
    private PointF p3 = new PointF(1, 0);
    private PointF p4 = new PointF(1, -C);
    private PointF p5 = new PointF(C, -1);
    private PointF p6 = new PointF(0, -1);
    private PointF p7 = new PointF(-C, -1);
    private PointF p8 = new PointF(-1, -C);
    private PointF p9 = new PointF(-1, 0);
    private PointF p10 = new PointF(-1, C);
    private PointF p11 = new PointF(-C, 1);
    private List<PointF> pointList = new ArrayList<>();

    private void init(AttributeSet attrs, int defStyle) {
        buildPath();
        initPaint();
        pointList.add(p0);
        pointList.add(p1);
        pointList.add(p2);
        pointList.add(p3);
        pointList.add(p4);
        pointList.add(p5);
        pointList.add(p6);
        pointList.add(p7);
        pointList.add(p8);
        pointList.add(p9);
        pointList.add(p10);
        pointList.add(p11);
    }

    private void buildPath() {
        mPath.reset();
        mPath.moveTo(p0.x * MULTIFACTOR, p0.y * MULTIFACTOR);
        mPath.cubicTo(p1.x * MULTIFACTOR, p1.y * MULTIFACTOR, p2.x * MULTIFACTOR, p2.y * MULTIFACTOR, p3.x * MULTIFACTOR, p3.y * MULTIFACTOR);
        mPath.cubicTo(p4.x * MULTIFACTOR, p4.y * MULTIFACTOR, p5.x * MULTIFACTOR, p5.y * MULTIFACTOR, p6.x * MULTIFACTOR, p6.y * MULTIFACTOR);
        mPath.cubicTo(p7.x * MULTIFACTOR, p7.y * MULTIFACTOR, p8.x * MULTIFACTOR, p8.y * MULTIFACTOR, p9.x * MULTIFACTOR, p9.y * MULTIFACTOR);
        mPath.cubicTo(p10.x * MULTIFACTOR, p10.y * MULTIFACTOR, p11.x * MULTIFACTOR, p11.y * MULTIFACTOR, p0.x * MULTIFACTOR, p0.y * MULTIFACTOR);
        mPath.close();
    }

    private float dx;

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.save();
        canvas.translate(getWidth() >> 1, getHeight() >> 1);
        canvas.translate(dx, 0);
        canvas.drawPath(mPath, mPaint);
        canvas.restore();
    }

    /**
     * Gets the example string attribute value.
     *
     * @return The example string attribute value.
     */
    public String getExampleString() {
        return mExampleString;
    }

    /**
     * Sets the view's example string attribute value. In the example view, this string
     * is the text to draw.
     *
     * @param exampleString The example string attribute value to use.
     */
    public void setExampleString(String exampleString) {
        mExampleString = exampleString;
    }

    /**
     * Gets the example color attribute value.
     *
     * @return The example color attribute value.
     */
    public int getExampleColor() {
        return mExampleColor;
    }

    /**
     * Sets the view's example color attribute value. In the example view, this color
     * is the font color.
     *
     * @param exampleColor The example color attribute value to use.
     */
    public void setExampleColor(int exampleColor) {
        mExampleColor = exampleColor;
    }

    /**
     * Gets the example dimension attribute value.
     *
     * @return The example dimension attribute value.
     */
    public float getExampleDimension() {
        return mExampleDimension;
    }

    /**
     * Sets the view's example dimension attribute value. In the example view, this dimension
     * is the font size.
     *
     * @param exampleDimension The example dimension attribute value to use.
     */
    public void setExampleDimension(float exampleDimension) {
        mExampleDimension = exampleDimension;
    }

    /**
     * Gets the example drawable attribute value.
     *
     * @return The example drawable attribute value.
     */
    public Drawable getExampleDrawable() {
        return mExampleDrawable;
    }

    /**
     * Sets the view's example drawable attribute value. In the example view, this drawable is
     * drawn above the text.
     *
     * @param exampleDrawable The example drawable attribute value to use.
     */
    public void setExampleDrawable(Drawable exampleDrawable) {
        mExampleDrawable = exampleDrawable;
    }


    public static final int DURATION = 50;
    public void start() {
        // 向右拉伸状态
        ValueAnimator stepOneVa = ValueAnimator.ofFloat(0.0f, 1.0f);
        stepOneVa.setDuration(DURATION);
        stepOneVa.setInterpolator(new AccelerateInterpolator());
        stepOneVa.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float fractionValue = (float) animation.getAnimatedValue();
                float m = fractionValue / 2;
                p2 = new PointF(m + 1, C);
                p3 = new PointF(m + 1, 0);
                p4 = new PointF(m + 1, -C);
                buildPath();
                invalidate();
            }
        });

        // 中间部分向右移动状态
        ValueAnimator stepTwoVa = ValueAnimator.ofFloat(0.0f, 1.0f);
        stepTwoVa.setDuration(DURATION);
        stepTwoVa.setInterpolator(new LinearInterpolator());
        stepTwoVa.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float fractionValue = (float) animation.getAnimatedValue();
                float m = fractionValue / 2;

                p0 = new PointF(m, p0.y);
                p1 = new PointF(C + m, p1.y);
                p11 = new PointF(-C + m, p11.y);
                p6 = new PointF(m, p6.y);
                p5 = new PointF(C + m, p5.y);
                p7 = new PointF(-C + m, p7.y);
                buildPath();
                invalidate();
            }
        });

        // 拉长的尾部还原状态
        ValueAnimator stepThreeVa = ValueAnimator.ofFloat(0.0f, 1.0f);
        stepThreeVa.setDuration(DURATION);
        stepThreeVa.setInterpolator(new DecelerateInterpolator());
        stepThreeVa.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float fractionValue = (float) animation.getAnimatedValue();
                float m = fractionValue / 2;

                p8 = new PointF(-1 + m, p8.y);
                p9 = new PointF(-1 + m, p9.y);
                p10 = new PointF(-1 + m, p10.y);
                buildPath();
                invalidate();
            }
        });

        ValueAnimator va = ValueAnimator.ofFloat(0.0f, 1.0f);
        va.setDuration(DURATION);
        va.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float fractionValue = (float) animation.getAnimatedValue();
                dx = 100 * fractionValue;
                invalidate();
            }
        });

        AnimatorSet set = new AnimatorSet();
        set.playTogether(va, stepOneVa);
        set.playSequentially(stepOneVa, stepTwoVa, stepThreeVa);
        set.start();
    }
}
