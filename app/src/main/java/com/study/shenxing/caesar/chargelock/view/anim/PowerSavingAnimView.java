package com.study.shenxing.caesar.chargelock.view.anim;

import android.content.Context;
import android.util.AttributeSet;

/**
 * 省电底层动画的View
 * @author zhanghuijun
 *
 */
public class PowerSavingAnimView extends AnimView {
	
	/**
	 * PowerSavingAnimScene
	 */
	private PowerSavingAnimScene mPowerSavingAnimScene = null;

	/**
	 * 当前处于生命周期
	 */
	private static final int CYCLE_ONCREATE = 0;
	private static final int CYCLE_ONRESUME = 1;
	private static final int CYCLE_ONPAUSE = 2;
	private int mCurrentCycle = CYCLE_ONCREATE;
	
	public PowerSavingAnimView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init();
	}

	public PowerSavingAnimView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	public PowerSavingAnimView(Context context) {
		super(context);
		init();
	}
	
	/**
	 * 初始化
	 */
	private void init() {
		mPowerSavingAnimScene = new PowerSavingAnimScene(getContext());
		setAnimScene(mPowerSavingAnimScene);
		setFPS(60);
	}
	
	@Override
	public void onDestroy() {
		super.onDestroy();
		if (mPowerSavingAnimScene != null) {
			mPowerSavingAnimScene.onDestory();
		}
	}

	@Override
	public void onResume() {
		mCurrentCycle = CYCLE_ONRESUME;
		super.onResume();
	}

	@Override
	public void onPause() {
		mCurrentCycle = CYCLE_ONPAUSE;
		super.onPause();
	}

	@Override
	protected void start() {
		if (mCurrentCycle == CYCLE_ONPAUSE) {
			return;
		}
		super.start();
	}

	public PowerSavingAnimScene getmPowerSavingAnimScene() {
		return mPowerSavingAnimScene;
	}
}

