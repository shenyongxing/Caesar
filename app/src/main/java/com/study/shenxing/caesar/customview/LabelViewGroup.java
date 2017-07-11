package com.zyxr.finance.widget;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

/**
 * @author shenxing
 * @description 投资item的标签容器，超过容器宽度的标签被丢弃
 * @date 11/05/2017
 */
public class LabelViewGroup extends ViewGroup {
    public LabelViewGroup(Context context) {
        super(context);
    }

    public LabelViewGroup(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public LabelViewGroup(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
//        super.onLayout(changed, l, t, r, b);
        int childCount = getChildCount();
        Log.i("sh_label", "childCount : " + childCount);
        int left = 0;
        for (int i = 0; i < childCount; i++) {
            View childView = getChildAt(i);
            Log.i("sh_label", "onLayout: " + childView.getMeasuredWidth() + "， " + getMeasuredWidth());
            if (left + childView.getMeasuredWidth() > getMeasuredWidth()) {
                Log.i("sh_label", "&&&&&&&&&&&&&&&&&&&&&&&&&&&&&: ");
                break;
            }
            Log.i("sh_label", "onLayout: " + left + "， " + (left + childView.getMeasuredWidth() + "， " + (t + childView.getMeasuredHeight())));
            childView.layout(left, t, left + childView.getMeasuredWidth(), t + childView.getMeasuredHeight());
            left += childView.getMeasuredWidth();
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        measureChildren(widthMeasureSpec, heightMeasureSpec);
    }
}
