package com.study.shenxing.caesar.shader;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Shader;
import android.os.Handler;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * 带有渐变动画的文字TextView，类似锁屏的Slide to unlock
 */
public class SlideTextView2 extends TextView {

	private LinearGradient mLinearGradient;
	private Matrix mGradientMatrix;
	private Paint mPaint;
	private int mViewWidth = 0;
	private int mTranslate = 0;

	private boolean mAnimating = true;

	public SlideTextView2(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		super.onSizeChanged(w, h, oldw, oldh);
		if (mViewWidth == 0) {
			mViewWidth = getMeasuredWidth();
			if (mViewWidth > 0) {
				mPaint = getPaint();
				mLinearGradient = new LinearGradient(-mViewWidth, 0, 0, 0,
						new int[] { 0x33ffffff, 0xffffffff, 0x33ffffff },
						null, Shader.TileMode.CLAMP);
				mPaint.setShader(mLinearGradient);
				mGradientMatrix = new Matrix();
			}
		}
	}

	@Override
	protected void onDraw(Canvas canvas) {
		if (mAnimating && mGradientMatrix != null) {
			mGradientMatrix.setTranslate(mTranslate, 0);
			mLinearGradient.setLocalMatrix(mGradientMatrix);
		}
		super.onDraw(canvas);
	}

	@Override
	protected void onFinishInflate() {
		super.onFinishInflate();
		mHandler.post(mRefresh) ;
	}

	private Handler mHandler = new Handler() ;
	private Runnable mRefresh = new Runnable() {
		@Override
		public void run() {
			mTranslate += mViewWidth / 20;
			if (mTranslate > 2 * mViewWidth) {
				mTranslate = -mViewWidth;
			}
			invalidate();
			mHandler.postDelayed(mRefresh, 50) ;
		}
	} ;
}