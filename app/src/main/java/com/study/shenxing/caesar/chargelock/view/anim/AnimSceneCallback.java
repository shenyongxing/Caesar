package com.study.shenxing.caesar.chargelock.view.anim;

/**
 * Callback for AnimScene.<br>
 * 
 * @author laojiale
 * 
 */
public interface AnimSceneCallback {

	/**
	 * AnimScene onStart()<br>
	 * 在主线程中回调.<br>
	 */
	public void onAnimSceneStart();

	/**
	 * AnimScene onStop()<br>
	 * 在主线程中回调.<br>
	 */
	public void onAnimSceneStop();

}
