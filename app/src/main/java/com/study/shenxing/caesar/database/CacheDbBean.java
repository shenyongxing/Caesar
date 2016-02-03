package com.study.shenxing.caesar.database;

/**
 * Created by shenxing on 16-1-21.
 * 数据库缓存Bean
 */
public class CacheDbBean {
    private String mPackageName ; // 包名
    private String mPath ;      // 文件路径
    private String mTitle ;     // 标识文件类型

    public String getmPackageName() {
        return mPackageName;
    }

    public void setmPackageName(String mPackageName) {
        this.mPackageName = mPackageName;
    }

    public String getmTitle() {
        return mTitle;
    }

    public void setmTitle(String mTitle) {
        this.mTitle = mTitle;
    }

    public String getmPath() {
        return mPath;
    }

    public void setmPath(String mPath) {
        this.mPath = mPath;
    }
}
