package com.study.shenxing.caesar.chargelock.view.anim;

import android.content.Context;


/**
 * 省电底层动画的AnimScene
 * @author zhanghuijun
 *
 */
public class PowerSavingAnimScene extends AnimScene {

	private PowerSavingLayer mPowerSavingLayer;
	
	public PowerSavingAnimScene(Context base) {
		super(base);
		mPowerSavingLayer = new PowerSavingLayer(this);
		setContentLayer(mPowerSavingLayer);
	}

	@Override
	protected void onStart() {
		super.onStart();
	}

	@Override
	protected void onStop() {
		super.onStop();
	}
	
	public void onDestory() {
		mPowerSavingLayer.onDestory();
	}

	/**
	 * 更新锁屏界面波浪高度
	 */
	public void updateWaveHeight() {
		if (mPowerSavingLayer != null) {
			mPowerSavingLayer.updateWaveHeight();
		}
	}

	/**
	 * 注册回调接口
	 */
	public void registerAccelAnim(PowerSavingLayer.IAccelAnimation iAccelAnimation) {
		if (mPowerSavingLayer != null) {
			mPowerSavingLayer.setmIsInAccelAnim(iAccelAnimation);
		}
	}
}
