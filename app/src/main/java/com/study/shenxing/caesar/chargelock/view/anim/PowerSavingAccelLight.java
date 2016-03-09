package com.study.shenxing.caesar.chargelock.view.anim;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.animation.Animation;
import android.view.animation.Transformation;

import com.study.shenxing.caesar.R;
import com.study.shenxing.caesar.utils.DrawUtils;


/**
 * 省电加速底部的光
 * @author zhanghuijun
 *
 */
public class PowerSavingAccelLight extends AnimObject {

	/**
	 * 是否初始化完成
	 */
	private boolean mHasInit = false;
	/**
	 * 坐标
	 */
	private int mX = 0;
	private int mY = 0;
	/**
	 * 图
	 */
	private Bitmap mBitmap = null;
	/**
	 * 图大小
	 */
	private int mWidth = 0;
	private int mHeight = 0;
	/**
	 * Paint
	 */
	private Paint mPaint = null;
	/**
	 * 绘制Rect
	 */
	private Rect mSrcRect = null;
	private Rect mDstRect = null;
	/**
	 * 场景大小
	 */
	private int mSceneWidth = -1;
	private int mSceneHeight = -1;
	/**
	 * 透明度
	 */
	private int mAlpha = 0;
	/**
	 * 动画
	 */
	private LightAnimation mLightAnimation = null;
	
	public PowerSavingAccelLight(AnimScene context) {
		super(context);
	}

	@Override
	protected void drawFrame(Canvas canvas, int sceneWidth, int sceneHeight,
			long currentTime, long deltaTime) {
		logic(currentTime);
		mPaint.setAlpha(mAlpha);
		canvas.drawBitmap(mBitmap, mSrcRect, mDstRect, mPaint);
	}
	
	@Override
	protected void onDrawRectChanged(int sceneWidth, int sceneHeight) {
		init();
	}
	
	/**
	 * 初始化
	 */
	private void init() {
		if (!mHasInit) {
			mSceneWidth = getSceneWidth();
			mSceneHeight = getSceneHeight();
			// 大小
			mWidth = (int) (DrawUtils.sDensity * 138);
			mHeight = (int) (DrawUtils.sDensity * 30);
			// 位置
			mX = (mSceneWidth - mWidth) / 2;
			mY = mSceneHeight - mHeight;
			// 图
			mBitmap = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.power_saving_anim_light);
			// 绘制Rect
			mSrcRect = new Rect(0, 0, mBitmap.getWidth(), mBitmap.getHeight());
			mDstRect = new Rect(mX, mY, mX + mWidth, mY + mHeight);
			// Paint 
			mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
			mHasInit = true;
		}
	}

	/**
	 * 逻辑
	 */
	private void logic(long currentTime) {
		if (mLightAnimation != null) {
			if (mLightAnimation.hasEnded()) {
				mLightAnimation = null;
			} else {
				mLightAnimation.getTransformation(currentTime, null);
			}
		}
	}
	
	/**
	 * 开始进入动画
	 */
	public void startEnterAnim() {
		mLightAnimation = new LightAnimation(true);
		mLightAnimation.setDuration(1000);
		mLightAnimation.setStartTime(Animation.START_ON_FIRST_FRAME);
	}
	
	/**
	 * 开始退场动画
	 */
	public void startExitAnim() {
		mLightAnimation = new LightAnimation(false);
		mLightAnimation.setDuration(1000);
		mLightAnimation.setStartOffset(2000);
		mLightAnimation.setStartTime(Animation.START_ON_FIRST_FRAME);
	}
	
	/**
	 * 动画
	 */
	private class LightAnimation extends Animation {
		
		private boolean mIsFadeIn = true;
		
		public LightAnimation(boolean mIsFadeIn) {
			this.mIsFadeIn = mIsFadeIn;
		}

		@Override
		protected void applyTransformation(float interpolatedTime,
				Transformation t) {
			if (mIsFadeIn) {
				mAlpha = (int) (255 * interpolatedTime);
			} else {
				mAlpha = (int) (255 * (1 - interpolatedTime));
			}
		}
	};
}
