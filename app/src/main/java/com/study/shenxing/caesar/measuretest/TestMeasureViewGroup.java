package com.study.shenxing.caesar.measuretest;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import com.study.shenxing.caesar.R;

import java.io.LineNumberReader;

/**
 * Created by shenxing on 16/4/8.
 * 测量过程优化实践
 */
public class TestMeasureViewGroup extends LinearLayout {
    private ProfilePhoto mProfilePhoto ;
    private Title mTitle ;
    private SubTitle mSubTitle ;
    private Menu mMenu ;
    public TestMeasureViewGroup(Context context) {
        super(context);
    }

    public TestMeasureViewGroup(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public TestMeasureViewGroup(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        mProfilePhoto = (ProfilePhoto) findViewById(R.id.profile_photo);
        mTitle = (Title) findViewById(R.id.title);
        mSubTitle = (SubTitle) findViewById(R.id.sub_title);
        mMenu = (Menu) findViewById(R.id.menu);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
//        measureOne(widthMeasureSpec, heightMeasureSpec);

        measureTwo(widthMeasureSpec, heightMeasureSpec);

        // 方法一和方法二打印出日志是完全一样的.
        // 方法二是我参照facebook工程师的做法修改而来的,一者实践自己的理解是否是对的. 二者加深对view测量的理解及优化方法
        // 方法二的本质是每次测量完之后就更新一下measureSpec, 方法一的这个过程在measureChildWidthMargins通过参数传递的方式在内部实现了更新
    }

    /**
     * first pattern. This is work of the facebook engineer's
     */
    private void measureOne(int widthMeasureSpec, int heightMeasureSpec) {
        // 1. setup initial constraints
        int widthConstraints = getPaddingLeft() + getPaddingRight() ;
        int heightConstaints = getPaddingTop() + getPaddingBottom() ;
        int width = 0;
        int height = 0;

        // 2. measure profile photo
        measureChildWithMargins(mProfilePhoto, widthMeasureSpec,
                widthConstraints, heightMeasureSpec, heightConstaints);

        // 3. update constraints
        widthConstraints += mProfilePhoto.getMeasuredWidth() ;
        width += mProfilePhoto.getMeasuredWidth() ;
        height = Math.max(height, mProfilePhoto.getMeasuredHeight()) ;

        // 4. measure menu
        measureChildWithMargins(mMenu, widthMeasureSpec,
                widthConstraints, heightMeasureSpec, heightConstaints);

        // 5. update constraints
        widthConstraints += mMenu.getMeasuredWidth() ;
        width += mMenu.getMeasuredWidth() ;
        height = Math.max(height, mMenu.getMeasuredHeight()) ;

        // 6.prepare vertical measurespec
        int verticalWidthMeasureSpec = MeasureSpec.makeMeasureSpec(
                MeasureSpec.getSize(widthMeasureSpec) - widthConstraints,
                MeasureSpec.getMode(widthMeasureSpec)
        ) ;

        int verticalHeightMeasureSpec = MeasureSpec.makeMeasureSpec(
                MeasureSpec.getSize(heightMeasureSpec) - heightConstaints,
                MeasureSpec.getMode(heightMeasureSpec)
        ) ;

        // 7. measure title
        measureChildWithMargins(mTitle, verticalWidthMeasureSpec, 0, verticalHeightMeasureSpec, 0);

        // 8. measure subtitle
        measureChildWithMargins(mSubTitle, verticalWidthMeasureSpec, 0, verticalHeightMeasureSpec, mTitle.getMeasuredHeight());

        // 9. udpate width and height
        width += Math.max(mTitle.getMeasuredWidth(), mSubTitle.getMeasuredWidth()) ;
        height = Math.max(height, mTitle.getMeasuredHeight() + mSubTitle.getMeasuredHeight()) ;

        setMeasuredDimension(resolveSize(width, widthMeasureSpec), resolveSize(height, heightMeasureSpec));
        Printer.printMeasureInfo("LinearLayout", widthMeasureSpec, heightMeasureSpec);
    }

    /**
     * second pattern. This is understanding of my mind .
     */
    private void measureTwo(int widthMeasureSpec, int heightMeasureSpec) {
        // 1. set initial constraints
        int widthConstraints = getPaddingLeft() + getPaddingRight() ;
        int heightConstraints = getPaddingTop() + getPaddingBottom();
        int width = 0 ;
        int height = 0 ;

        int originWidthMeasureSpec = widthMeasureSpec ;
        int originHeightMeasureSpec = heightMeasureSpec ;

        // 2. measure profilephoto
        measureChildWithMargins(mProfilePhoto, widthMeasureSpec,
                widthConstraints, heightMeasureSpec, heightConstraints);

        // 3. udpate measureSpec
        widthMeasureSpec = MeasureSpec.makeMeasureSpec(
                MeasureSpec.getSize(widthMeasureSpec) - mProfilePhoto.getMeasuredWidth() - widthConstraints,
                MeasureSpec.getMode(widthMeasureSpec)
        ) ;

        heightMeasureSpec = MeasureSpec.makeMeasureSpec(
                MeasureSpec.getSize(heightMeasureSpec) - heightConstraints,
                MeasureSpec.getMode(heightMeasureSpec)
        ) ;
        widthConstraints += mProfilePhoto.getMeasuredWidth() ;
        width += mProfilePhoto.getMeasuredWidth() ;
        height = Math.max(height, mProfilePhoto.getMeasuredHeight()) ;

        // 4. measure menu
        measureChildWithMargins(mMenu, widthMeasureSpec, 0, heightMeasureSpec, 0);

        // 5. update measureSpec
        widthMeasureSpec = MeasureSpec.makeMeasureSpec(
                MeasureSpec.getSize(widthMeasureSpec) - widthConstraints,
                MeasureSpec.getMode(widthMeasureSpec)
        ) ;

        heightMeasureSpec = MeasureSpec.makeMeasureSpec(
                MeasureSpec.getSize(heightMeasureSpec) - heightConstraints,
                MeasureSpec.getMode(heightMeasureSpec)
        ) ;
        widthConstraints += mMenu.getMeasuredWidth() ;
        width += mMenu.getMeasuredWidth() ;
        height = Math.max(height, mMenu.getMeasuredHeight()) ;

        // 6. measure title
        measureChildWithMargins(mTitle, widthMeasureSpec, 0, heightMeasureSpec, 0);

        // 7. udpate measureSpec
        heightConstraints += mTitle.getMeasuredHeight() ;
        heightMeasureSpec = MeasureSpec.makeMeasureSpec(
                MeasureSpec.getSize(heightMeasureSpec) - heightConstraints,
                MeasureSpec.getMode(heightMeasureSpec)
        ) ;

        // 8. measure subtitle
        measureChildWithMargins(mSubTitle, widthMeasureSpec, 0, heightMeasureSpec, 0);

        // 9. update width and height
        width += Math.max(mTitle.getMeasuredWidth(), mSubTitle.getMeasuredWidth()) ;
        height = Math.max(height, mTitle.getMeasuredHeight() + mSubTitle.getMeasuredHeight()) ;

        setMeasuredDimension(resolveSize(width, originHeightMeasureSpec), resolveSize(height, originHeightMeasureSpec));
        Printer.printMeasureInfo("LinearLayout", originWidthMeasureSpec, originHeightMeasureSpec);
    }

    // 结论:
    // 经过实现, 通过主动的有策略的测量view确实能减少父view的测量次数.
    // 以后考虑优化时可以利用此种方式, 即对每个子view进行测量.
}
