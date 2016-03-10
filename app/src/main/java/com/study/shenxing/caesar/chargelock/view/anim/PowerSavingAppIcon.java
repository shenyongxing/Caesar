package com.study.shenxing.caesar.chargelock.view.anim;

import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.Transformation;


import com.study.shenxing.caesar.chargelock.ProgramDetail;
import com.study.shenxing.caesar.utils.DrawUtils;

import java.util.Random;

/**
 * 加速动画的应用图标
 * @author zhanghuijun
 *
 */
public class PowerSavingAppIcon extends AnimObject {
	
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
	 * 起始，终止位置
	 */
	private int mStartX = 0;
	private int mStartY = 0;
	private int mEndX = 0;
	private int mEndY = 0;
	/**
	 * 图标大小
	 */
	private int mWidth = 0;
	private int mStartWidth = 0;
	private int mEndWidth = 0;
	/**
	 * 图标
	 */
	private Bitmap mIcon = null;
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
	 * Random
	 */
	private Random mRandom = null;
	/**
	 * ProgramDetail
	 */
	private ProgramDetail mApp = null;
	/**
	 * 动画
	 */
	private AppIconAnimation mAppIconAnimation = null;
	/**
	 * 旋转
	 */
	private int mIconRotateDegree = 0;

	public PowerSavingAppIcon(AnimScene context, ProgramDetail app, Random random, int sceneWidth, int sceneHeight) {
		super(context);
		mApp = app;
		mRandom = random;
		// Icon
//		mIcon = BitmapUtils.roundBitmap(AppUtils.loadAppIcon(mContext , mApp.mPackageName));
		try {
			PackageManager packageManager = context.getPackageManager();
			ApplicationInfo appInfo = packageManager.getApplicationInfo(app.getmPackageName(), PackageManager.GET_META_DATA);
//			mIcon = BitmapUtils.createBitmapFromDrawable(appInfo.loadIcon(packageManager)) ;
//			mIcon = appInfo.loadIcon(packageManager) ;
			Drawable drawable = appInfo.loadIcon(packageManager) ;
			if (drawable instanceof BitmapDrawable) {
				BitmapDrawable bitmapDrawable = (BitmapDrawable) drawable;
				mIcon = bitmapDrawable.getBitmap() ;
			}
		} catch (PackageManager.NameNotFoundException e) {
			e.printStackTrace();
		}
		init(sceneWidth, sceneHeight);
	}

	@Override
	protected void drawFrame(Canvas canvas, int sceneWidth, int sceneHeight,
			long currentTime, long deltaTime) {
		if (logic(currentTime)) {
			canvas.save();
			canvas.rotate(mIconRotateDegree, mDstRect.centerX(), mDstRect.centerY());
			canvas.drawBitmap(mIcon, mSrcRect, mDstRect, mPaint);
			canvas.restore();
		}
	}
	
	@Override
	protected void onDrawRectChanged(int sceneWidth, int sceneHeight) {
		init(sceneWidth, sceneHeight);
	}
	
	/**
	 * 初始化
	 */
	private void init(int sceneWidth, int sceneHeight) {
		if (!mHasInit) {
			mSceneWidth = sceneWidth;
			mSceneHeight = sceneHeight;
			// 随机初始位置
			mX = mRandom.nextInt(mSceneWidth);
			mWidth = (int) (DrawUtils.sDensity * 48);
			mY = -mWidth;
			mStartWidth = mWidth;
			mEndWidth = (int) (DrawUtils.sDensity * 20);
			// 起始，终止位置
			mStartX = mX;
			mStartY = mY;
			mEndX = (int) (mSceneWidth * 0.45f + mRandom.nextInt((int) (mSceneWidth * 0.1f)));
			mEndY = mSceneHeight;
			// 动画
			mAppIconAnimation = new AppIconAnimation();
			mAppIconAnimation.setDuration(1000);
			mAppIconAnimation.setInterpolator(new AccelerateInterpolator());
			mAppIconAnimation.setStartOffset(mRandom.nextInt(800));
			mAppIconAnimation.setStartTime(Animation.START_ON_FIRST_FRAME);
			// 绘制Rect
			mSrcRect = new Rect(0, 0, mIcon.getWidth(), mIcon.getHeight());
			mDstRect = new Rect(mX, mY, mX + mWidth, mY + mWidth);
			// Paint 
			mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
			mHasInit = true;
		}
	}

	/**
	 * 逻辑
	 */
	private boolean logic(long currentTime) {
		if (mAppIconAnimation.hasEnded()) {
			return false;
		}
		mAppIconAnimation.getTransformation(currentTime, null);
		mDstRect.set(mX, mY, mX + mWidth, mY + mWidth);
		return true;
	}
	
	/**
	 * 动画
	 */
	private class AppIconAnimation extends Animation {
		
		@Override
		protected void applyTransformation(float interpolatedTime,
				Transformation t) {
			mX = (int) (mStartX + (mEndX - mStartX) * interpolatedTime);
			mY = (int) (mStartY + (mEndY - mStartY) * interpolatedTime);
			mWidth = (int) (mStartWidth + (mEndWidth - mStartWidth) * interpolatedTime);
			mIconRotateDegree = (int) (360 * interpolatedTime);
		}
	};
	
	
	/**
	 * 是否滑动结束
	 */
	public boolean isAnimOver() {
		if (mAppIconAnimation != null && mAppIconAnimation.hasEnded()) {
			return true;
		}
		return false;
	}
}
