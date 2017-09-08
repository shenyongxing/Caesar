package com.study.shenxing.caesar.customview;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
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
        int width = r - l;
        int childCount = getChildCount();
        int left = getPaddingLeft();
        int top = getPaddingTop();
        for (int i = 0; i < childCount; i++) {
            View childView = getChildAt(i);
            MarginLayoutParams mlp = (MarginLayoutParams) childView.getLayoutParams();
            int childWidth = childView.getMeasuredWidth();
            int childHeight = childView.getMeasuredHeight();
            if (left + childWidth > width - getPaddingLeft() - getPaddingRight()) {
                break;
            }
            childView.layout(left + mlp.leftMargin, top + mlp.topMargin, left + mlp.leftMargin + childWidth, top + mlp.topMargin + childHeight);
            left += childWidth + mlp.leftMargin + mlp.rightMargin;
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        measureChildren(widthMeasureSpec, heightMeasureSpec);

        int wrapContentWidth = 0;
        int wrapContentHeight = 0;
        int maxHeightPerLine = 0;

        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);

        // 遍历子view计算wrap_content模式下的宽高
        int count = getChildCount();
        for (int i = 0; i < count; i++) {
            View childView = getChildAt(i);
            MarginLayoutParams mlp = (MarginLayoutParams) childView.getLayoutParams();
            int childWidth = childView.getMeasuredWidth() + mlp.leftMargin + mlp.rightMargin;
            int childHeight = childView.getMeasuredHeight() + mlp.topMargin + mlp.bottomMargin;

            if (wrapContentWidth + childWidth > widthSize - getPaddingLeft() - getPaddingRight()) {
                // 超出宽度, 丢弃
                break;
            } else {
                wrapContentWidth += childWidth;
            }
            // 当前行高
            if (childHeight > maxHeightPerLine) {
                maxHeightPerLine = childHeight;
            }
            wrapContentHeight = maxHeightPerLine;
        }
        setMeasuredDimension(widthMode == MeasureSpec.EXACTLY ? widthSize : wrapContentWidth + getPaddingLeft() + getPaddingRight(),
                heightMode == MeasureSpec.EXACTLY ? heightSize : wrapContentHeight + getPaddingTop() + getPaddingBottom());
    }

    @Override
    protected LayoutParams generateLayoutParams(LayoutParams p) {
        return new MarginLayoutParams(p);
    }

    @Override
    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new MarginLayoutParams(getContext(), attrs);
    }

    @Override
    protected LayoutParams generateDefaultLayoutParams() {
        return new MarginLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
    }
}
