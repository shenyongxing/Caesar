package com.study.shenxing.caesar.shader;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
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
    private Bitmap mArrowMaskBitmap ;
    private Bitmap mHighlightArrowBitmap ;
    private float mMaskOffset ;
    private int mViewWidth ;
    private int mViewHeight;

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
        mArrowDrawable = getResources().getDrawable(R.drawable.lock_ani) ;
        mViewWidth = mArrowDrawable.getIntrinsicWidth();
        mViewHeight = mArrowDrawable.getIntrinsicHeight();
        mArrowDrawable.setBounds(0, 0, mViewWidth, mViewHeight);

        mArrowMaskBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.lock_ani_mask) ;
        mHighlightArrowBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.lock_ani_light) ;
        mPaint = new Paint();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if (mArrowDrawable == null || mArrowMaskBitmap == null || mHighlightArrowBitmap == null) {
            return ;
        }
        mArrowDrawable.draw(canvas);

        canvas.saveLayer(0, 0, getWidth(), getHeight(), null,
                Canvas.MATRIX_SAVE_FLAG |
                        Canvas.CLIP_SAVE_FLAG |
                        Canvas.HAS_ALPHA_LAYER_SAVE_FLAG |
                        Canvas.FULL_COLOR_LAYER_SAVE_FLAG |
                        Canvas.CLIP_TO_LAYER_SAVE_FLAG);
        canvas.drawBitmap(mHighlightArrowBitmap, 0, 0, null);
        canvas.save();
        mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_IN));
        canvas.translate(mMaskOffset, 0);
        canvas.drawBitmap(mArrowMaskBitmap, 0, 0, mPaint);
        canvas.drawBitmap(mArrowMaskBitmap, -mViewWidth, 0, mPaint);
        mPaint.setXfermode(null) ;
        canvas.restore();
        canvas.restore();
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        uiHandler.postDelayed(mRefreshDrawable, 10) ;
    }

    private Handler uiHandler = new Handler() ;

    private Runnable mRefreshDrawable = new Runnable() {

        public void run() {
            mMaskOffset++;
            if (mMaskOffset > getWidth()) {
                mMaskOffset = 0;
            }

           invalidate();   //重绘
           uiHandler.postDelayed(this, 10) ;
        }
    };


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(resolveSize(mViewWidth, widthMeasureSpec), resolveSize(mViewHeight, heightMeasureSpec));
    }
}
