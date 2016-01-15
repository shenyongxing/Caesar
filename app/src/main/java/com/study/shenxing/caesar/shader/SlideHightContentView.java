package com.study.shenxing.caesar.shader;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.View;

import com.study.shenxing.caesar.R;

/**
 * Created by shenxing on 16-1-15.
 * 高亮内容移动显示。 类似锁屏slide to unlock
 * 此处是用图片绘制方式。对于文字的实现方式参见 {@link SlideImageView}
 */
public class SlideHightContentView extends View {
    private Bitmap mContent ;       // 源图
    private Bitmap mHightContent ;  // 高亮显示的内容
    private Bitmap mContentMask ;   // 遮罩

    private int mViewWidth ;
    private int mViewHeight;

    private float mXOffset ;    // 遮罩的x方向偏移量
    private Paint mPaint ;
    private PorterDuffXfermode mDuffMode = new PorterDuffXfermode(PorterDuff.Mode.DST_IN) ;

    private Handler mHandler = new Handler() ;

    public SlideHightContentView(Context context) {
        super(context);
        init();
    }

    public SlideHightContentView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(resolveSize(mViewWidth, widthMeasureSpec), resolveSize(mViewHeight, heightMeasureSpec));
    }

    private void init() {
        mContent = BitmapFactory.decodeResource(getResources(), R.drawable.slide_to_unlock_black) ;
        mHightContent = BitmapFactory.decodeResource(getResources(), R.drawable.slide_to_unlock_white) ;
        mContentMask = BitmapFactory.decodeResource(getResources(), R.drawable.slide_unlock_mask) ;
        mViewWidth = mContent.getWidth();
        mViewHeight = mContent.getHeight() ;
        mPaint = new Paint() ;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawBitmap(mContent, 0, 0, null);
        canvas.saveLayer(0, 0, getWidth(), getHeight(), null,
                Canvas.MATRIX_SAVE_FLAG |
                        Canvas.CLIP_SAVE_FLAG |
                        Canvas.HAS_ALPHA_LAYER_SAVE_FLAG |
                        Canvas.FULL_COLOR_LAYER_SAVE_FLAG |
                        Canvas.CLIP_TO_LAYER_SAVE_FLAG);

        canvas.drawBitmap(mHightContent, 0, 0, null);

        canvas.save();
        canvas.translate(mXOffset, 0);
        mPaint.setXfermode(mDuffMode) ;
        canvas.drawBitmap(mContentMask, 0, 0, mPaint);
        canvas.drawBitmap(mContentMask, -mViewWidth, 0, mPaint);
        mPaint.setXfermode(null) ;
        canvas.restore();

        canvas.restore();

    }

    private Runnable mRefreshRunnable = new Runnable() {
        @Override
        public void run() {
            mXOffset += 2 ;
            if (mXOffset > mViewWidth) {
                mXOffset = 0 ;
            }
            invalidate();
            // 无限递归调用
            mHandler.postDelayed(this, 10) ;
        }
    } ;

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        mHandler.postDelayed(mRefreshRunnable, 10) ;
    }
}
