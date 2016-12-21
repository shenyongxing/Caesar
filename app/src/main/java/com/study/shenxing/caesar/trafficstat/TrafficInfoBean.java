package com.study.shenxing.caesar.trafficstat;

/**
 * @author shenxing
 * @description 流量信息bean
 * @date 2016/12/15
 */

public class TrafficInfoBean {

    private String mPkgName;

    private long mRxBytes;  // 从开机到此刻所有的接收流量

    private long mTxBytes;  // 从开机到此刻所有的发送流量

    public String getPkgName() {
        return mPkgName;
    }

    public void setPkgName(String pkgName) {
        mPkgName = pkgName;
    }

    public long getRxBytes() {
        return mRxBytes;
    }

    public void setRxBytes(long rxBytes) {
        mRxBytes = rxBytes;
    }

    public long getTxBytes() {
        return mTxBytes;
    }

    public void setTxBytes(long txBytes) {
        mTxBytes = txBytes;
    }
}
