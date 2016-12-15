package com.study.shenxing.caesar.test;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * @author shenxing
 * @description
 * @date 2016/11/3
 */

public class ForTest implements Parcelable {
    private String mForTest;    // 测试测试框架
    private String mDescription;


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.mForTest);
        dest.writeString(this.mDescription);
    }

    public ForTest() {
    }

    protected ForTest(Parcel in) {
        this.mForTest = in.readString();
        this.mDescription = in.readString();
    }

    public static final Parcelable.Creator<ForTest> CREATOR = new Parcelable.Creator<ForTest>() {
        @Override
        public ForTest createFromParcel(Parcel source) {
            return new ForTest(source);
        }

        @Override
        public ForTest[] newArray(int size) {
            return new ForTest[size];
        }
    };

    public String getForTest() {
        return mForTest;
    }

    public void setForTest(String forTest) {
        mForTest = forTest;
    }

    public String getDescription() {
        return mDescription;
    }

    public void setDescription(String description) {
        mDescription = description;
    }
}
