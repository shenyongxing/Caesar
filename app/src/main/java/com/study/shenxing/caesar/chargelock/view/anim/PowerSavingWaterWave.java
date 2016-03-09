package com.study.shenxing.caesar.chargelock.view.anim;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;

import com.study.shenxing.caesar.utils.DrawUtils;

import java.util.ArrayList;
import java.util.List;


/**
 * 水波纹
 * @author zhanghuijun
 *
 */
public class PowerSavingWaterWave extends AnimObject {
	
	/**
	 * 是否初始化完成
	 */
	private boolean mHasInit = false;
	/**
	 * 场景大小
	 */
	private int mSceneWidth = -1;
	private int mSceneHeight = -1;
	/**
	 * 水位线
	 */
	private float mWaveLine;
	/**
	 * 波浪起伏幅度
	 */
	private float mWaveHeight = 0;
	/**
	 * 波长
	 */
	private float mWaveWidth = 0;
	/**
	 * 被隐藏的最左边的波形
	 */
	private float mLeftSide;
	/**
	 * 平移过的距离
	 */
	private float mMoveLen;
	/**
	 * 水波平移速度
	 */
	private float mWaveSpeed = 0f;
	/**
	 * 路径的点
	 */
	private List<Point> mPointsList;
	/**
	 * Paint
	 */
	private Paint mPaint;
	/**
	 * 绘制路径
	 */
	private Path mWavePath;
	/**
	 * 波纹是否在前面
	 */
	private boolean mIsForword = false;
	private float mStartX = 0f;
	/**
	 * 上一次加速时间
	 */
	private long mLastWaveTime = 0l;

	/**
	 * @param context
	 * @param isForword		是否在前面
	 */
	public PowerSavingWaterWave(AnimScene context, boolean isForword) {
		super(context);
		mIsForword = isForword;
	}
	
	@Override
	protected void onDrawRectChanged(int sceneWidth, int sceneHeight) {
		// 有大小了，初始化
		init();
	}
	
	/**
	 * 初始化
	 */
	private void init() {
		if (!mHasInit) {
			mSceneHeight = getSceneHeight();
			mSceneWidth = getSceneWidth();
			// 水位线
			int powerRate = /*BatteryInfo.getLevelPercent(mContext)*/80;
			if (powerRate > 97) {
				powerRate = 97;
			}
			mWaveLine = (100 - powerRate) * mSceneHeight / 100;
			// 波形峰值
			mWaveHeight = DrawUtils.sDensity * 20;
			// 波长
			mWaveWidth = mSceneWidth;
			mPointsList = new ArrayList<Point>();
			int n = 1;
			int mLeftPointCount = 0;
			if (mIsForword) {
				mStartX =  - mWaveWidth ;
				mLeftPointCount = 5;
			} else {
				mStartX = - mWaveWidth * 1.5f;
				mLeftPointCount = 7;
			}
			// 左边隐藏的距离预留一个波形
			mLeftSide = mStartX;
			// n个波形需要4n+1个点，但是我们要预留一个波形在左边隐藏区域，所以需要4n+5个点
			for (int i = 0; i < (4 * n + mLeftPointCount); i++)
			{
				// 从P0开始初始化到P4n+4，总共4n+5个点
				float x = i * mWaveWidth / 4 + mStartX;
				float y = 0;
				switch (i % 4)
				{
				case 0:
				case 2:
					// 零点位于水位线上
					y = mWaveLine;
					break;
				case 1:
					// 往下波动的控制点
					y = mWaveLine + mWaveHeight;
					break;
				case 3:
					// 往上波动的控制点
					y = mWaveLine - mWaveHeight;
					break;
				}
				mPointsList.add(new Point((int) x, (int) y));
			}
			// 速度
			mWaveSpeed = mWaveWidth / 60;
			mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
			mPaint.setColor(0x89D5C113);
			mWavePath = new Path();
			mHasInit = true;
		}
	}

	@Override
	protected void drawFrame(Canvas canvas, int sceneWidth, int sceneHeight,
			long currentTime, long deltaTime) {
		if (mHasInit) {
			logic();
			mWavePath.reset();
			int i = 0;
			boolean hasMoveTo = false;
			int startI = 0;
			for (; i < mPointsList.size() - 2; i = i + 2)
			{
				if (!hasMoveTo && mPointsList.get(i).x <= 0 && mPointsList.get(i + 2).x >= 0) {
					mWavePath.moveTo(mPointsList.get(i).x, mPointsList.get(i).y);
					startI = i;
					hasMoveTo = true;
				} else if (mPointsList.get(i).x >= mSceneWidth) {
					break;
				}
				if (hasMoveTo) {
					mWavePath.quadTo(mPointsList.get(i + 1).x,
							mPointsList.get(i + 1).y, mPointsList.get(i + 2).x,
							mPointsList.get(i + 2).y);
				}
			}
			mWavePath.lineTo(mPointsList.get(i).x, (int) (mWaveLine + mWaveHeight));
			mWavePath.lineTo(mPointsList.get(startI).x, (int) (mWaveLine + mWaveHeight));
			mWavePath.close();
			if (/*BatteryInfo.getLevelPercent(mContext) < 10*/false) {
				mPaint.setColor(0x89E31100);
			} else if (/*BatteryInfo.getLevelPercent(mContext) < 20*/false) {
				mPaint.setColor(0x89D5C113);
			} else {
				mPaint.setColor(0x8918A717);
			}
			canvas.drawPath(mWavePath, mPaint);
			canvas.drawRect(0, (int) (mWaveLine + mWaveHeight), mSceneWidth, mSceneHeight, mPaint);
		}
	}
	
	/**
	 * 逻辑
	 */
	private void logic() {
		long now = System.currentTimeMillis();
		if (mLastWaveTime != 0 && now - mLastWaveTime < 25) {
			return;
		}
		// 记录平移总位移
		mMoveLen += mWaveSpeed;
		mLeftSide += mWaveSpeed;
		// 波形平移
		for (int i = 0; i < mPointsList.size(); i++)
		{
			float x = mPointsList.get(i).x + mWaveSpeed;
			float y = mPointsList.get(i).y;
			switch (i % 4)
			{
			case 0:
			case 2:
				y = (int) mWaveLine;
				break;
			case 1:
				y = mWaveLine + mWaveHeight;
				break;
			case 3:
				y = mWaveLine - mWaveHeight;
				break;
			}
			mPointsList.get(i).set((int) x, (int) y);
		}
		if (mMoveLen >= mWaveWidth) {
			// 波形平移超过一个完整波形后复位
			mMoveLen = 0;
			mLeftSide = mStartX;
			for (int i = 0; i < mPointsList.size(); i++) {
				mPointsList.get(i).x = (int) (i * mWaveWidth / 4 + mStartX);
			}
		}
		mLastWaveTime = System.currentTimeMillis();
	}

	/**
	 * 获取最低水位线
	 */
	public float getLowWaveLine() {
		return mWaveLine + mWaveHeight;
	}

	/**
	 * 设置电量值
	 */
	public void setPowerRate(int powerRate) {
		if (powerRate > 97) {
			powerRate = 97;
		}
		mWaveLine = (100 - powerRate) * mSceneHeight / 100f;
	}
	
}
