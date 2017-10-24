package com.study.shenxing.caesar.customview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author shenxing
 * @Date 2017/9/30
 * @Email shen.xing@zyxr.com
 * @Description 圆盘布局
 */
public class RingView extends ViewGroup {
    public static final String TAG = "sh_ring";
    private Paint mPaint = new Paint();
    private List<Integer> mImageList = new ArrayList<>();
    private float mRadius;
    private int mAngle;     // 圆盘转过的角度，用于控制圆盘转动
    private float mSpeedThresold = 40;    // 惯性滑动的最小速度
    private float mMinSpeed = 4;        // 最小速度，转动速度大于此速度时屏蔽点击事件

    public RingView(Context context) {
        this(context, null, 0);
    }

    public RingView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RingView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        for (int i = 0; i < 10; i++) {
//            mImageList.add((int) (Math.random() * 1000));
            mImageList.add(i);
        }
        initView();
        mPaint.setAntiAlias(true);
        mPaint.setColor(Color.BLUE);
        mPaint.setStyle(Paint.Style.FILL);
    }

    private void initView() {
        for (int i = 0; i < mImageList.size(); i++) {
            final TextView textveiw = new TextView(getContext());
            textveiw.setText(String.valueOf(mImageList.get(i)));
            textveiw.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
            addView(textveiw);
            textveiw.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(getContext(), "the value is " + textveiw.getText(), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(widthMeasureSpec, heightMeasureSpec);
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            View view = getChildAt(i);
            measureChild(view, widthMeasureSpec, heightMeasureSpec);
        }
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int width = r - l;
        int height = b - t;

        width = Math.min(width, height);
        height = Math.min(width, height);
        mRadius = width / 2 - getChildAt(0).getMeasuredWidth(); // 圆圈半径
        int angleGap = 360 / mImageList.size(); // 每张图片之间的角度间隔

        for (int i = 0; i < mImageList.size(); i++) {
            View child = getChildAt(i);
            int angleOffset = angleGap * i;
            int left = (int) (width / 2 + mRadius * Math.cos(angleOffset * Math.PI / 180)) - child.getMeasuredWidth() / 2;
            int top = (int) (height / 2 + mRadius * Math.sin(angleOffset * Math.PI / 180)) - child.getMeasuredWidth() / 2;
            child.layout(left, top, left + child.getMeasuredWidth(), top + child.getMeasuredHeight());
            child.setRotation(90 + angleOffset);   // 转动图片，形成弧线效果
        }
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);
    }

    /**
     * 获取触摸点相对圆中心点的角度
     * @param touchX
     * @param touchY
     * @return
     */
    public float getAngle(float touchX, float touchY) {
        // 将触摸点转化为以圆中心为坐标中心的坐标点
        float x = touchX - (getMeasuredWidth() >> 1);
        float y = touchY - (getMeasuredHeight() >> 1);
        return (float) (Math.asin(y / Math.hypot(x, y)) * 180 / Math.PI);
    }

    private float mLastX;
    private float mLastY;
    private long mDownTime;
    private int mTmpAngle;
    private boolean mIsMoving;  // 是否在转动
    private AngleRunnable mAngleRunnable;



    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        float x = ev.getX();
        float y = ev.getY();

        Log.i(TAG, "dispatchTouchEvent: ");
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:

                mLastX = x;
                mLastY = y;
                mDownTime = System.currentTimeMillis();
                mTmpAngle = 0;

                Log.i(TAG, "dispatchTouchEvent: " + mIsMoving);
                if (mIsMoving) {
                    removeCallbacks(mAngleRunnable);
                    mIsMoving = false;
                    return true;
                }

                break;
            case MotionEvent.ACTION_MOVE:

                // 开始的角度
                float startAngle = getAngle(mLastX, mLastY);
                // 当前的角度
                float endAngle = getAngle(x, y);
                Log.i(TAG, "dispatchTouchEvent: " + (startAngle) + ", " + (endAngle));

                // 一四象限
                if (/*getQuadrant(x, y) == 1 || getQuadrant(x, y) == 4*/true) {
                    Log.i(TAG, "第一四象限: " + endAngle + ", " + startAngle);
                    mAngle += endAngle - startAngle;
                    mTmpAngle += endAngle - startAngle;
                    //二三象限
                } else {
                    Log.i(TAG, "第二三象限: " + endAngle + ", " + startAngle);
                    mAngle += startAngle - endAngle;
                    mTmpAngle += startAngle - endAngle;
                }

                rotatePlate();

                break;
            case MotionEvent.ACTION_UP:
                // 手指离开屏幕时
                float anglePerSecond = mTmpAngle * 1000 / (System.currentTimeMillis() - mDownTime);
                // 速度超过某个值时，需要惯性滚动
                if (Math.abs(anglePerSecond) > mSpeedThresold && !mIsMoving) {
                    mAngleRunnable = new AngleRunnable(anglePerSecond);
                    post(mAngleRunnable);
                    return true;
                }

                if (Math.abs(anglePerSecond) > mMinSpeed) {
                    return true;
                }

                break;
        }

        return super.dispatchTouchEvent(ev);
    }

    /**
     * 角度变化任务
     */
    public class AngleRunnable implements Runnable {

        private float mAnglePerSecond;  // 每秒移动的角度是多少

        public AngleRunnable(float anglePerSecond) {
            mAnglePerSecond = anglePerSecond;
        }

        @Override
        public void run() {
            if (Math.abs(mAnglePerSecond) < 20) {
                mIsMoving = false;
                return;
            }

            mIsMoving = true;
            mAngle += (mAnglePerSecond / 30);
            // 逐步减小mAnglePerSecond， 模拟惯性现象
            mAnglePerSecond /= 1.0666f;
            postDelayed(this, 30);
            rotatePlate();
        }
    }

    /**
     * 转动圆盘
     */
    private void rotatePlate() {
        mAngle %= 360;
        setRotation(mAngle);
    }

    /**
     * 根据当前位置计算象限
     */
    private int getQuadrant(float x, float y) {
        int tmpX = (int) (x - (getMeasuredWidth() >> 1));
        int tmpY = (int) (y - (getMeasuredHeight() >> 1));
        if (tmpX >= 0) {
            return tmpY >= 0 ? 4 : 1;
        } else {
            return tmpY >= 0 ? 3 : 2;
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                Log.i(TAG, "onTouchEvent: ACTION_DOWN");
                break;
            case MotionEvent.ACTION_MOVE:
                Log.i(TAG, "onTouchEvent: ACTION_MOVE");
                break;
            case MotionEvent.ACTION_UP:
                Log.i(TAG, "onTouchEvent: ACTION_UP");
                break;
        }
        return super.onTouchEvent(event);
    }
}
