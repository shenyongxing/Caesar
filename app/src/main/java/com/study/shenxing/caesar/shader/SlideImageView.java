package com.study.shenxing.caesar.shader;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Shader;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.View;

import com.study.shenxing.caesar.R;

/**
 * 滑动解锁ImageView部分内容高亮显示的动画.
 * @author shenxing
 *
 */
public class SlideImageView extends View {
    private Drawable mArrowDrawable;
    private Bitmap mArrowBitmap ;
    private int mViewWidth ;
    private int mViewHeight ;

    private float mRangeArea ;
    private float mMaxXDistance ;
    private Matrix mMatrix ;
    private int mDarkColor ;
    private int mLightColor ;
    private int mCirculationNum = 30;   // 从左到右一次循环的次数
    private int mCycleNum = 0; // 线程循环，sleep的次数


    private Paint mPaint;

    public SlideImageView(Context context) {
        super(context);
        init(null, 0);
    }

    public SlideImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs, 0);
    }

    public SlideImageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(attrs, defStyle);
    }

    private void init(AttributeSet attrs, int defStyle) {
        // Set up a default TextPaint object
//        mPaint = new Paint();
//        mPaint.setFlags(Paint.ANTI_ALIAS_FLAG);

        mArrowDrawable = getResources().getDrawable(R.drawable.lock_ani) ;
        if (mArrowDrawable instanceof BitmapDrawable) {
            BitmapDrawable bitmapDrawable = (BitmapDrawable) mArrowDrawable;
            mPaint = bitmapDrawable.getPaint() ;

            mArrowDrawable.setBounds(0, 0, mArrowDrawable.getIntrinsicWidth(), mArrowDrawable.getIntrinsicHeight());

            mArrowBitmap = bitmapDrawable.getBitmap() ;
            mViewWidth = mArrowBitmap.getWidth() ;
            mViewHeight = mArrowBitmap.getHeight();

            mRangeArea = (float) mViewWidth / 3;
            mMaxXDistance = 1 + 2 * mRangeArea;

            mDarkColor = Color.parseColor("#11747474");
            mLightColor = Color.parseColor("#33ffffff");
            mMatrix = new Matrix();
            mMatrix.setRotate(-90);
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if (mArrowBitmap != null) {
            int thisNum = 1 + mCycleNum % mCirculationNum;

            // 跑马灯的区域是[-rangeArea, 1 + rangeArea]
            float centerX = -mRangeArea + mMaxXDistance * thisNum / mCirculationNum;
            Shader shader = new LinearGradient(0, 0, 0, mViewWidth, new int[] {
                    mDarkColor, mDarkColor, mLightColor, mDarkColor, mDarkColor },
                    new float[] { -1f, centerX - mRangeArea, centerX,
                            centerX + mRangeArea, 2f }, Shader.TileMode.CLAMP);
            shader.setLocalMatrix(mMatrix);
            mPaint.setShader(shader);
//            mArrowDrawable.draw(canvas);
            canvas.drawBitmap(mArrowBitmap, 0, 0, mPaint);
//            canvas.drawText("hello world", 30, 30, mPaint);
        }
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        new UIThread().start();
    }

    class UIThread extends Thread {
        public UIThread() {
            mCycleNum = 0;
        }

        @Override
        public void run() {
            try {
                while (true) {
                    sleep(40);
                    // handler通知ui更新文字透明度
                    if (mCycleNum > 0 && mCycleNum % mCirculationNum == 0) {
                        sleep(1200);
                    }

                    Message msg = uiHandler.obtainMessage();
                    mCycleNum++;
                    msg.sendToTarget();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        };
    };

    private Handler uiHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            // 刷新View
            invalidate();
        };
    };


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(resolveSize(mViewWidth, widthMeasureSpec), resolveSize(mViewHeight, heightMeasureSpec));
    }
}
