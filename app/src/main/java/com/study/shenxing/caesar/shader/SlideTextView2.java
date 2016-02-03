package com.study.shenxing.caesar.shader;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Shader.TileMode;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.View;

import com.study.shenxing.caesar.R;

public class SlideTextView2 extends View {
	private String mShowText = "滑动来解锁";
	private int mTextWidth ;
	private Paint mPaint;
	private LinearGradient mLinearShader ;
	private Matrix mMatrix = new Matrix() ;
	private int mXoffSet ;
	private int mDarkColor = Color.parseColor("#dd000000") ;
	private int mLightColor = android.R.color.white;

	public SlideTextView2(Context context) {
		this(context, null);
		init();
	}

	public SlideTextView2(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
		init();
	}

	public SlideTextView2(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		init();
	}

	private void init() {
		mPaint = new Paint() ;
		mPaint.setAntiAlias(true);
		mPaint.setColor(0xffffffff);
		mPaint.setTextSize(getResources().getDimensionPixelSize(R.dimen.slide_to_unlock_textsize));
		Paint.FontMetrics fm = new Paint.FontMetrics() ;
		mTextWidth = mPaint.getTextWidths(mShowText, 0, mShowText.length(), new float[mShowText.length()]) ;
		mLinearShader = new LinearGradient(0, 0, 1000, 1000, new int[]{mDarkColor, mLightColor, mDarkColor}, null, TileMode.CLAMP) ;
	}

	@Override
	protected void onDraw(Canvas canvas) {
		mMatrix.setTranslate(mXoffSet, 0); ;
		mLinearShader.setLocalMatrix(mMatrix);
		mPaint.setShader(mLinearShader) ;
		canvas.drawText(mShowText, 100, 100, mPaint);
	}

	@Override
	protected void onFinishInflate() {
		super.onFinishInflate();
		mHandler.sendEmptyMessage(0x123);
	}

	private Handler mHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			if (msg.what == 0x123) {
				if (mXoffSet > mTextWidth) {
					mXoffSet = -10 ;
				}
				mXoffSet++ ;
				// 刷新View
				invalidate();
				mHandler.sendEmptyMessageDelayed(0x123, 10) ;
			}
		};
	};

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
	}
}
