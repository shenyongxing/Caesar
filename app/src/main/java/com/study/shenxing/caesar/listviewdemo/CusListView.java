package com.study.shenxing.caesar.listviewdemo;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ViewConfiguration;
import android.widget.ListView;

/**
 * Created by shenxing on 16/2/20.
 */
public class CusListView extends ListView {
    private float mTouchSlop = ViewConfiguration.get(getContext()).getScaledTouchSlop() ;   // 判断滑动的阈值.
    private float mFirstY ;
    private int mDirection ;
    public CusListView(Context context) {
        super(context);
    }

    public CusListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CusListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected boolean overScrollBy(int deltaX, int deltaY, int scrollX, int scrollY, int scrollRangeX, int scrollRangeY, int maxOverScrollX, int maxOverScrollY, boolean isTouchEvent) {
        return super.overScrollBy(deltaX, deltaY, scrollX, scrollY, scrollRangeX, scrollRangeY, maxOverScrollX, maxOverScrollY, isTouchEvent);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        int action = ev.getAction() ;
        switch (action) {
            case MotionEvent.ACTION_DOWN :
                mFirstY = ev.getY() ;
                break;
            case MotionEvent.ACTION_MOVE :
                float currY = ev.getY();
                if (mFirstY - currY > mTouchSlop) {
                    mDirection = 0 ; // 向上滑动
                } else if (mFirstY - currY < mTouchSlop) {
                    mDirection = 1 ; // 向下滑动
                }
                break;
            case MotionEvent.ACTION_UP :
                break;
        }

        return false;
    }


}
