package com.study.shenxing.caesar.chargelock.view.anim;

import android.graphics.Canvas;
import android.graphics.Paint;

import com.study.shenxing.caesar.utils.DrawUtils;

import java.util.Random;

/**
 * 气泡
 * @author zhanghuijun
 *
 */
public class PowerSavingBubble extends AnimObject {

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
	 * 半径
	 */
	private int mRaduis = 0;
	/**
	 * 移动速度
	 */
	private int mFloatSpeed = 0;
	/**
	 * Paint
	 */
	private Paint mPaint = null;
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
	 * PowerSavingWaterWave
	 */
	private PowerSavingWaterWave mPowerSavingWaterWave = null;
	/**
	 * 是否超过水位线
	 */
	private boolean mIsOverWaveLine = false;

	public PowerSavingBubble(AnimScene context, PowerSavingWaterWave wave, Random random) {
		super(context);
		mPowerSavingWaterWave = wave;
		mRandom = random;
	}

	@Override
	protected void drawFrame(Canvas canvas, int sceneWidth, int sceneHeight,
			long currentTime, long deltaTime) {
		if (mHasInit && !mIsOverWaveLine) {
			logic();
			// 进行绘制
			if (/*BatteryInfo.getLevelPercent(mContext) < 10*/false) {
				mPaint.setColor(0x89E31100);
			} else if (/*BatteryInfo.getLevelPercent(mContext) < 20*/false) {
				mPaint.setColor(0x89D5C113);
			} else {
				mPaint.setColor(0x8918A717);
			}
			canvas.drawCircle(mX, mY, mRaduis, mPaint);
		}
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
			mSceneHeight = getSceneHeight();
			mSceneWidth = getSceneWidth();
			// 随机初始位置
			mX = (int) (mSceneWidth * 0.45f + mRandom.nextInt((int) (mSceneWidth * 0.1f)));
			mY = mSceneHeight;
			// 随机大小
			mRaduis = (int) (DrawUtils.sDensity * 4 + mRandom.nextInt((int) (DrawUtils.sDensity * 4)));
			// 随机速度
			mFloatSpeed = (int) (DrawUtils.sDensity * 4 + mRandom.nextInt((int) (DrawUtils.sDensity * 4)));
			// Paint 
			mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
			mPaint.setColor(0xFF3BAFDA);
			mIsOverWaveLine = false;
			mHasInit = true;
		}
	}
	
	/**
	 * 逻辑
	 */
	private void logic() {
		mY -= mFloatSpeed;
		if (mY <= mPowerSavingWaterWave.getLowWaveLine()) {
			mIsOverWaveLine = true;
		}
	}

	/**
	 * 是否超过水位线
	 * @return
	 */
	public boolean isOverWaveLine() {
		return mIsOverWaveLine;
	}

}
