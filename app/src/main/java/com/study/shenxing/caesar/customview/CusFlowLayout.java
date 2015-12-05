package com.study.shenxing.caesar.customview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

/**
 * 流式布局
 * sx
 */
public class CusFlowLayout extends ViewGroup {

    public CusFlowLayout(Context context) {
        super(context);
    }

    public CusFlowLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CusFlowLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int width = r - l ;

        int tempWidth = 0 ;
        int tempHeight = 0 ;
        int maxHeightPerLine = 0;

        int count = getChildCount() ;
        for (int i = 0; i < count; i++) {
            View childView = getChildAt(i) ;
            MarginLayoutParams mlp = (MarginLayoutParams) childView.getLayoutParams();
            int childWidth = childView.getMeasuredWidth()+ mlp.leftMargin + mlp.rightMargin;
            int childHeight = childView.getMeasuredHeight() + mlp.topMargin + mlp.bottomMargin;

            if (tempWidth + childWidth > width) {
                tempWidth = 0;
                tempHeight += maxHeightPerLine;
                childView.layout(tempWidth + mlp.leftMargin, tempHeight + mlp.topMargin, tempWidth + + mlp.leftMargin + childView.getMeasuredWidth(), tempHeight + mlp.topMargin + childView.getMeasuredHeight()) ;
                tempWidth = childWidth ;
            } else {
                childView.layout(tempWidth + mlp.leftMargin, tempHeight + mlp.topMargin, tempWidth + + mlp.leftMargin + childView.getMeasuredWidth(), tempHeight + mlp.topMargin + childView.getMeasuredHeight()) ;
                tempWidth += childWidth ;
            }
            // 记录当前行高度
            if (childHeight > maxHeightPerLine) {
                maxHeightPerLine = childHeight;
            }
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        // 先对所有的子View进行测量一次, 确定其宽高
        measureChildren(widthMeasureSpec, heightMeasureSpec);

        int wrapContentWidth = 0;  // 记录该流式布局的宽度
        int wrapContentHeight = 0;  // 记录流式布局的高度
        int maxHeightPerLine = 0;

        // 如何得到最大的宽度
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);

        // 遍历子view
        int count = getChildCount();
        for (int i = 0; i < count; i++) {
            // 如何测量子view的宽高
            View childView = getChildAt(i);
            MarginLayoutParams mlp = (MarginLayoutParams) childView.getLayoutParams();
            int childWidth = childView.getMeasuredWidth() + mlp.leftMargin + mlp.rightMargin;
            int childHeight = childView.getMeasuredHeight() + mlp.topMargin + mlp.bottomMargin;

            if (wrapContentWidth + childWidth > widthSize) {
                // 超出宽度, 则换行
                wrapContentWidth = childWidth;
                wrapContentHeight += maxHeightPerLine;
            } else {
                wrapContentWidth += childWidth ;
            }
            // 记录当前行高度
            if (childHeight > maxHeightPerLine) {
                maxHeightPerLine = childHeight;
            }

            // 不要漏掉了最后一行的高度
            if (i == count - 1) {
                wrapContentHeight += maxHeightPerLine;
            }
        }

        setMeasuredDimension(widthMode == MeasureSpec.EXACTLY ? widthSize : wrapContentWidth,
                heightMode == MeasureSpec.EXACTLY ? heightSize : wrapContentHeight);
    }

    @Override
    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new MarginLayoutParams(getContext(), attrs);
    }
}
