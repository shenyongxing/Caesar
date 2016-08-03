package com.study.shenxing.caesar.customview;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.CornerPathEffect;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;

/**
 * Created by shenxing on 16/7/31.
 * Path绘制波浪实践
 */
public class PaintApiTestView extends View {
    private Paint mPaint = new Paint();
    private float mDashOffset;
    public PaintApiTestView(Context context) {
        super(context);
        initPaint();
    }

    public PaintApiTestView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initPaint();
    }

    public PaintApiTestView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initPaint();
    }

    private void initPaint() {
        mPaint.setStrokeWidth(80);  // 设置空心画笔的宽度
        mPaint.setAntiAlias(true);
        mPaint.setColor(Color.RED); // 画笔不设置颜色,则默认是黑色
        mPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        setLayerType(LAYER_TYPE_SOFTWARE, null);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        // strokeCap
        mPaint.setStrokeCap(Paint.Cap.BUTT);
        canvas.drawLine(100, 200, 400, 200, mPaint);

        mPaint.setStrokeCap(Paint.Cap.SQUARE);
        canvas.drawLine(100, 400, 400, 400, mPaint);

        mPaint.setStrokeCap(Paint.Cap.ROUND);
        canvas.drawLine(100, 600, 400, 600, mPaint);

        mPaint.reset();
        mPaint.setStrokeWidth(2);
        mPaint.setColor(Color.RED);
        canvas.drawLine(100, 100, 100, 700, mPaint);
        canvas.drawLine(400, 100, 400, 700, mPaint);

        // strokeJoin
        mPaint.setStrokeWidth(40);
        mPaint.setStyle(Paint.Style.STROKE);
        Path path  = new Path();
        path.moveTo(500, 100);
        path.lineTo(850, 100);
        path.lineTo(500, 300);
        mPaint.setStrokeJoin(Paint.Join.MITER);
        canvas.drawPath(path, mPaint);

        path.moveTo(500, 400);
        path.lineTo(850, 400);
        path.lineTo(500, 600);
        mPaint.setStrokeJoin(Paint.Join.ROUND);
        canvas.drawPath(path, mPaint);

        path.moveTo(500, 700);
        path.lineTo(850, 700);
        path.lineTo(500, 900);
        mPaint.setStrokeJoin(Paint.Join.BEVEL);
        canvas.drawPath(path, mPaint);

        // path effect
        // corner path effect
        mPaint.setStrokeWidth(2);
        Path path2 = new Path();
        path2.moveTo(100, 1000);
        path2.lineTo(400, 900);
        path2.lineTo(700, 1300);

        canvas.drawPath(path2, mPaint);

        mPaint.setColor(Color.RED);
        mPaint.setPathEffect(new CornerPathEffect(100));
        canvas.drawPath(path2, mPaint);

        mPaint.setColor(Color.YELLOW);
        mPaint.setPathEffect(new CornerPathEffect(200));
        canvas.drawPath(path2, mPaint);

        // dash path effect
        mPaint.setPathEffect(new DashPathEffect(new float[]{20, 10, 100, 100}, 0));
        mPaint.setColor(Color.RED);
        canvas.translate(0, 100);
        canvas.drawPath(path2, mPaint);

        mPaint.setPathEffect(new DashPathEffect(new float[]{20, 10, 50, 100}, mDashOffset));
        mPaint.setColor(Color.YELLOW);
        canvas.translate(0, 100);
        canvas.drawPath(path2, mPaint);
    }

    public void startAnim() {
        ValueAnimator va = ValueAnimator.ofInt(0, 180);
        va.setDuration(1000);
        va.setRepeatCount(Animation.INFINITE);
        // 需要主动设置线性插值器,否则会有稍微停顿的问题, 默认是加速减速插值器
        va.setInterpolator(new LinearInterpolator());
        va.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mDashOffset = (int) animation.getAnimatedValue();
                invalidate();
            }
        });
        va.start();
    }

}
