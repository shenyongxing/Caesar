package com.study.shenxing.caesar.customview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Region;
import android.graphics.RegionIterator;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by shenxing on 16/7/31.
 * Paint与Canvas api接口使用实践
 */
public class GraphicView extends View {
    private Paint mPaint = new Paint();
    private Path mPath = new Path();

    private float mLastX;
    private float mLastY;
    public GraphicView(Context context) {
        super(context);
        initPaint();
    }

    public GraphicView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initPaint();
    }

    public GraphicView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initPaint();
    }

    private void initPaint() {
        mPaint.setStrokeWidth(2);  // 设置空心画笔的宽度
        mPaint.setAntiAlias(true);
        mPaint.setColor(Color.RED); // 画笔不设置颜色,则默认是黑色
        mPaint.setStyle(Paint.Style.STROKE);
        setLayerType(LAYER_TYPE_SOFTWARE, null);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // bezier curve
//        Path path = new Path();
//        path.moveTo(100, 300);
//        path.quadTo(200, 200, 500, 500);
//        path.quadTo(700,700, 900, 700);
        canvas.drawPath(mPath, mPaint);
    }

    public void reset() {
        mPath.reset();
        invalidate();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getAction();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                mPath.moveTo(event.getX(), event.getY());
                mLastX = event.getX();
                mLastY = event.getY();
                return true;
//                break;
            case MotionEvent.ACTION_MOVE:
                float endX = (mLastX + event.getX()) / 2;
                float endY = (mLastY + event.getY()) / 2;

                mPath.quadTo(mLastX, mLastY, endX, endY);

                mLastX = event.getX();
                mLastY = event.getY();
                invalidate();
                break;
            default:
                break;
        }


        return super.onTouchEvent(event);
    }
}
