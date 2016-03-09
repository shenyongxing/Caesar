package com.study.shenxing.caesar.chargelock;

import android.content.ComponentName;

/**
 * 
 * <br>类描述: 应用程序详情
 * <br>功能详细描述:
 * 
 * @author  tangyong
 * @date  [2012-9-3]
 */
public class ProgramDetail {

	private String mName;
	private String mPackageName;
	private boolean mBgProgram;
	private ComponentName mComponentName;
	private boolean mIsKillFlag;

	public String getmName() {
		return mName;
	}

	public void setmName(String mName) {
		this.mName = mName;
	}

	public String getmPackageName() {
		return mPackageName;
	}

	public void setmPackageName(String mPackageName) {
		this.mPackageName = mPackageName;
	}

	public boolean ismBgProgram() {
		return mBgProgram;
	}

	public void setmBgProgram(boolean mBgProgram) {
		this.mBgProgram = mBgProgram;
	}

	public ComponentName getmComponentName() {
		return mComponentName;
	}

	public void setmComponentName(ComponentName mComponentName) {
		this.mComponentName = mComponentName;
	}

	public boolean isKillFlag() {
		return mIsKillFlag;
	}

	public void setKillFlag(boolean mIsKillFlag) {
		this.mIsKillFlag = mIsKillFlag;
	}

}
