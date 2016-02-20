package com.study.shenxing.caesar.listviewdemo;

import android.graphics.Bitmap;

/**
 * Created by shenxing on 16/2/20.
 */
public class ChatItemListViewBean {
    private int mType ;
    private String mMessage ;
    private Bitmap mIcon ;

    public int getmType() {
        return mType;
    }

    public void setmType(int mType) {
        this.mType = mType;
    }

    public Bitmap getmIcon() {
        return mIcon;
    }

    public void setmIcon(Bitmap mIcon) {
        this.mIcon = mIcon;
    }

    public String getmMessage() {
        return mMessage;
    }

    public void setmMessage(String mMessage) {
        this.mMessage = mMessage;
    }
}
