package com.study.shenxing.caesar.chargelock.view.anim;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Canvas;
import android.os.Handler;
import android.util.Log;

import com.study.shenxing.caesar.chargelock.ProgramDetail;
import com.study.shenxing.caesar.others.Consts;
import com.study.shenxing.caesar.utils.DrawUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * 省电底层动画的AnimLayerGroup
 * @author zhanghuijun
 *
 */
public class PowerSavingLayer extends AnimLayerGroup {
	
	/**
	 * 水波纹
	 */
	private PowerSavingWaterWave mWaveAbove = null;
	private PowerSavingWaterWave mWaveBelow = null;
	/**
	 * 气泡
	 */
	private List<PowerSavingBubble> mBubbles = null;
	/**
	 * 初始化气泡个数
	 */
	private int mBubbleCount = 3;
	/**
	 * Random
	 */
	private Random mRandom = null;
	/**
	 * 上一次生成气泡的时间
	 */
	private long mLastCreateBubbleTime = System.currentTimeMillis();
	/**
	 * 应用泡泡
	 */
	private List<PowerSavingAppIcon> mIcons = null;
	/**
	 * 加速光
	 */
	private PowerSavingAccelLight mLight = null;
	/**
	 * 加速动画是否进行中
	 * 0 : 动画未开始 1：动画开始了 2：动画进行中
	 * @param context
	 */
	private int mIsInAccelAnim = 0;

	private Handler mHandler = new Handler() ;

	private IAccelAnimation mAccelAnimation ;

	public PowerSavingLayer(AnimScene context) {
		super(context);
		mRandom = new Random();
		mWaveBelow = new PowerSavingWaterWave(context, false);
		mWaveAbove = new PowerSavingWaterWave(context, true);
		addAnimaLayer(mWaveBelow);
		addAnimaLayer(mWaveAbove);
		mLight = new PowerSavingAccelLight(context);
		addAnimaLayer(mLight);
		// 气泡
		mBubbles = new ArrayList<PowerSavingBubble>();
		// 初始化泡泡
		for (int i = 0; i < mBubbleCount; i++) {
			PowerSavingBubble bubble = new PowerSavingBubble(context, mWaveAbove, mRandom);
			mBubbles.add(bubble);
			addAnimaLayer(bubble);
		}
		// 应用泡泡
		mIcons = new ArrayList<PowerSavingAppIcon>();

		IntentFilter intentFilter = new IntentFilter(Consts.KEY_START_ACCEL_ANIM) ;
		mContext.registerReceiver(mAccelReceiver, intentFilter) ;
	}

	@Override
	protected void drawFrame(Canvas canvas, int sceneWidth, int sceneHeight,
			long currentTime, long deltaTime) {
		logic();
		super.drawFrame(canvas, sceneWidth, sceneHeight, currentTime, deltaTime);
	}

	/**
	 * 注册回调
	 * @param iAccelAnimation
	 */
	public void setmIsInAccelAnim(IAccelAnimation iAccelAnimation) {
		mAccelAnimation = iAccelAnimation;
	}

	/**
	 * 逻辑
	 */
	private void logic() {
		// 气泡
		for (int i = mBubbles.size() - 1; i >= 0; i--) {
			if (mBubbles.get(i).isOverWaveLine()) {
				removeAnimaLayer(mBubbles.get(i));
				mBubbles.remove(i);
			}
		}
		long now = System.currentTimeMillis();
		if (now - mLastCreateBubbleTime >= 500 && isCharging() && mBubbles.size() < 8) {
			PowerSavingBubble bubble = new PowerSavingBubble(mContext, mWaveAbove, mRandom);
			mBubbles.add(bubble);
			addAnimaLayer(bubble);
			mLastCreateBubbleTime = now;
		}
		// 应用气泡
		for (int i = mIcons.size() - 1; i >= 0; i--) {
			if (mIcons.get(i).isAnimOver()) {
				removeAnimaLayer(mIcons.get(i));
				mIcons.remove(i);
			}
		}
		Log.d("zhanghuijun", "mIsInAccelAnim : " + mIsInAccelAnim + "   mIcons :  " + mIcons.size());
		if (mIcons.isEmpty() && mIsInAccelAnim == 2) {
			// 加速动画结束
			mIsInAccelAnim = 0;
			mLight.startExitAnim(); 
			if (mAccelAnimation != null) {
				mAccelAnimation.onAccelEnd();
			}
		}
	}
	
	/**
	 * 开启加速动画
	 */
	private void startAccelAnim() {
		if (mIsInAccelAnim > 0) {
			return;
		}
		mIsInAccelAnim = 1;
		mLight.startEnterAnim();
		new Thread(new Runnable() {
			@Override
			public void run() {
				// 异步准备并初始化数据
				int scenenWidth = DrawUtils.sWidthPixels;
				int scenenHeight = DrawUtils.sHeightPixels;
				if (getSceneWidth() > 0 || getSceneHeight() > 0) {
					scenenWidth = getSceneWidth();
					scenenHeight = getSceneHeight();
				}
				int size = 20 ;
				for (int i = 0; i < size; i++) {
//					ProgramDetail app = recommendBoostRunningApps.get(i) ;
					ProgramDetail app = new ProgramDetail();
					app.setmPackageName(Consts.PACKAGE_NAME);
					app.setmName("test");
//					if (app.isKillFlag()) {
//						if (!app.ismBgProgram()) {
							PowerSavingAppIcon icon = new PowerSavingAppIcon(mContext, app, mRandom, scenenWidth, scenenHeight);
							if (!mIcons.contains(icon)) {
								mIcons.add(icon);
							}
//						}
//					}
				}

				mHandler.post(new Runnable() {
					@Override
					public void run() {
						// 再同步添加动画元素
						removeAnimaLayer(mWaveAbove);
						removeAnimaLayer(mLight);
						for (int i = 0; i < mIcons.size(); i++) {
							if (!containLayer(mIcons.get(i))) {
								addAnimaLayer(mIcons.get(i));
							}
						}
						addAnimaLayer(mWaveAbove);
						addAnimaLayer(mLight);
						mIsInAccelAnim = 2;
					}
				});
			}
		}).start();
	}


	/**
	 * 根据电量设置波浪高度
	 */
	public void updateWaveHeight() {
		if (mWaveAbove != null) {
			mWaveAbove.setPowerRate(/*BatteryInfo.getLevelPercent(mContext)*/80);
		}
		if (mWaveBelow != null) {
			mWaveBelow.setPowerRate(/*BatteryInfo.getLevelPercent(mContext)*/80);
		}
	}
	
	/**
	 * 开始加速动画
	 */
	public void startAccleAnimation() {
		Log.d("zhanghuijun", "PowerAccelAnimStartEvent");
		startAccelAnim();
	}
	
	public void onDestory() {
		if (mContext != null && mAccelReceiver != null) {
			mContext.unregisterReceiver(mAccelReceiver);
		}
	}

	private boolean isCharging() {
		return /*BatteryInfo.isUsingAcElectricity(mContext) || BatteryInfo.isUsingUsbElectricity(mContext)*/true;
	}

	/**
	 * 动画结束回调接口
	 */
	public interface IAccelAnimation {
		public void onAccelEnd() ;
	}

	public BroadcastReceiver mAccelReceiver = new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) {
			if (intent != null) {
				String action = intent.getAction() ;
				if (Consts.KEY_START_ACCEL_ANIM.equals(action)) {
					startAccleAnimation();
				}
			}
		}
	};
}
