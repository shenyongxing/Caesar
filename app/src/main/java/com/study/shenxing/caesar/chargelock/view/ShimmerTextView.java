package com.study.shenxing.caesar.chargelock.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.widget.TextView;

import com.study.shenxing.caesar.utils.DrawUtils;


/**
 * 简易的仿ios解锁文字TextView
 * @author zhanghuijun
 *
 */
public class ShimmerTextView extends TextView {
	
	private Paint mPaint;
	/**
	 * 渐变
	 */
	private LinearGradient mLinearGradient;
	private Matrix mLinearGradientMatrix;
    private float mGradientX = 0;
    private float mGradientXSpeed = 0;
	/**
	 * 是否初始化完成
	 */
	private boolean mHasInit = false;
	/**
	 * 颜色
	 */
	private int mPrimaryColor = 0x7FFFFFFF;
    private int mReflectionColor = 0xFFFFFFFF;
    /**
     * 是否绘制
     */
    private boolean mIsInAnim = false;

	public ShimmerTextView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public ShimmerTextView(Context context) {
		super(context);
	}
	
	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		super.onSizeChanged(w, h, oldw, oldh);
		init();
	}
	/**
	 * 初始化
	 */
	private void init() {
		if (mHasInit) {
			return;
		}
		mPaint = getPaint();
		mLinearGradient = new LinearGradient(-getMeasuredWidth(), 0, 0, 0,
				new int[] { mPrimaryColor, mReflectionColor, mPrimaryColor, },
				new float[] { 0, 0.5f, 1 }, Shader.TileMode.CLAMP);
		mPaint.setShader(mLinearGradient);
		mLinearGradientMatrix = new Matrix();
		mGradientXSpeed = DrawUtils.sDensity * 2;
		mHasInit = true;
	}
	
	@Override
	protected void onDraw(Canvas canvas) {
		if (mHasInit) {
	        if (mPaint.getShader() == null) {
	        	mPaint.setShader(mLinearGradient);
	        }
	        mGradientX += mGradientXSpeed;
	        if (mGradientX >= 2 * getMeasuredWidth()) {
	        	mGradientX = 0;
	        }
	        mLinearGradientMatrix.setTranslate(2 * mGradientX, 0);
	        mLinearGradient.setLocalMatrix(mLinearGradientMatrix);
		}
		super.onDraw(canvas);
		if (mIsInAnim) {
			// 在onDraw方法里面调用invalidate()方法不是很好。
			invalidate();
		}
	}
	
	/**
	 * 请在主线程调用
	 */
	public void start() {
		if (mIsInAnim) {
			return;
		}
		mIsInAnim = true;
		invalidate();
	}
	
	public void stop() {
		mIsInAnim = false;
	}
}
