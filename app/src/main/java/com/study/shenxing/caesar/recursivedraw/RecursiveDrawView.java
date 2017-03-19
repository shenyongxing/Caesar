package com.study.shenxing.caesar.recursivedraw;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

/**
 * TODO: document your custom view class.
 */
public class RecursiveDrawView extends View {
    private Paint mPaint = new Paint();
    public static final int LENGTH = 100;
    public RecursiveDrawView(Context context) {
        super(context);
        init(null, 0);
    }

    public RecursiveDrawView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs, 0);
    }

    public RecursiveDrawView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(attrs, defStyle);
    }

    private void init(AttributeSet attrs, int defStyle) {
        mPaint.setColor(Color.RED);
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setStrokeWidth(10);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawTree(canvas, 5);
    }

    private void left(Canvas canvas) {
        canvas.rotate(-30, getWidth() >> 1, getHeight() >> 1);
    }

    private void right(Canvas canvas) {
        canvas.rotate(30, getWidth() >> 1, getHeight() >> 1);
    }


    private void actualDraw(Canvas canvas) {
        canvas.drawLine(getWidth() >> 1, 0, getWidth() >> 1, LENGTH, mPaint);

        canvas.save();
        canvas.rotate(-30, getWidth() >> 1, LENGTH);
        canvas.drawLine(getWidth() >> 1, LENGTH, getWidth() >> 1, LENGTH * 2, mPaint);
        canvas.restore();

        canvas.save();
        canvas.rotate(30, getWidth() >> 1, LENGTH);
        canvas.drawLine(getWidth() >> 1, LENGTH, getWidth() >> 1, LENGTH * 2, mPaint);
        canvas.restore();

    }


    private void drawTree(Canvas canvas, int n) {
        if (n == 0) {

        } else {
            left(canvas);
            actualDraw(canvas);
            drawTree(canvas, n - 1);
            right(canvas);

            right(canvas);
            actualDraw(canvas);
            drawTree(canvas, n - 1);
            right(canvas);
        }
    }
}
