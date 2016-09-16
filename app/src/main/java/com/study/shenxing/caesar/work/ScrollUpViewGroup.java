package com.study.shenxing.caesar.work;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.Scroller;

import com.study.shenxing.caesar.R;

/**
 * @description
 * @author shenxing
 * @date 16/9/7
 */

public class ScrollUpViewGroup extends ViewGroup {
    public static final String TAG = "sh";
    private Scroller mScroller;
    private int mVisibleHeight;
    private int mSlideSlop; // 判断滑动的距离阈值
    private int mTopMargin;
    private int mBottom;
    private GestureDetector mGestureDetector;
    public ScrollUpViewGroup(Context context) {
        super(context);
        init(context, null, 0);
    }

    public ScrollUpViewGroup(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs, 0);
    }

    public ScrollUpViewGroup(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context, attrs, defStyle);
    }

    private void init(Context context, AttributeSet attrs, int defStyle) {
        // Load attributes
        final TypedArray a = getContext().obtainStyledAttributes(
                attrs, R.styleable.ScrollUpViewGroup, defStyle, 0);

        mVisibleHeight = a.getDimensionPixelOffset(R.styleable.ScrollUpViewGroup_visibleHeight, 600);
        a.recycle();

        mScroller = new Scroller(context);
        mSlideSlop = 2 * ViewConfiguration.get(context).getScaledTouchSlop();
        mGestureDetector = new GestureDetector(context, mGuestureDetectorListener);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            View childView = getChildAt(i);
            measureChild(childView, widthMeasureSpec, heightMeasureSpec);
        }
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int childCount = getChildCount();
        int height = b - t;
        mTopMargin = height - mVisibleHeight;
        int tempHeight = 0;
        for (int i = 0; i < childCount; i++) {
            View childView = getChildAt(i);
            int childHeight = childView.getMeasuredHeight();
            childView.layout(l, t + mTopMargin + tempHeight, r, t + mTopMargin + tempHeight + childHeight);
            tempHeight += childHeight;

            if (i == childCount - 1) {
                mBottom = childView.getBottom();
            }
        }
    }

    private GestureDetector.SimpleOnGestureListener mGuestureDetectorListener = new GestureDetector.SimpleOnGestureListener() {
        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
            Log.i(TAG, "onScroll: " + distanceY);
            scrollBy(0, (int) distanceY);
            invalidate();
            return true;
        }

        @Override
        public boolean onDown(MotionEvent e) {
            mScroller.forceFinished(true);
            return true;
        }

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            mScroller.fling(0, getScrollY(), 0, (int) velocityY, 0, 0, getWidth() >> 1, getHeight() >> 1);
            invalidate();
            return true;
        }
    };

    @Override
    public void computeScroll() {
        super.computeScroll();
        if (mScroller.computeScrollOffset()) {
            scrollTo(mScroller.getCurrX(), mScroller.getCurrY());
            invalidate();
        }
    }


    private int mLastDownX;
    private int mLastDownY;

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        super.onInterceptTouchEvent(event);
        boolean intercept = false;
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mLastDownX = (int) event.getX();
                mLastDownY = (int) event.getY();
                intercept = false;
                break;
            case MotionEvent.ACTION_MOVE:
                int x = (int) event.getX();
                int y = (int) event.getY();
                int deltaX = x - mLastDownX;
                int deltaY = y - mLastDownY;
                if (Math.abs(deltaY) > mSlideSlop || Math.abs(deltaX) > mSlideSlop) {
                    intercept = true;
                } else {
                    intercept = false;
                }
                Log.i(TAG, "onInterceptTouchEvent: " + intercept);
                break;
            case MotionEvent.ACTION_UP:
                intercept = false;
                break;
            default:
                break;
        }

        return intercept;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return mGestureDetector.onTouchEvent(event) || super.onTouchEvent(event);
    }
}
