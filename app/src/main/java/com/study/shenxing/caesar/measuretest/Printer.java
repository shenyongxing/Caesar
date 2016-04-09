package com.study.shenxing.caesar.measuretest;

import android.util.Log;
import android.view.View;

import com.study.shenxing.caesar.others.Consts;

/**
 * Created by shenxing on 16/4/8.
 * print measure logs util
 */
public class Printer {
    public static void printMeasureInfo(String className, int widthMeasureSpec, int heightMeasureSpec) {
        int widthMode = View.MeasureSpec.getMode(widthMeasureSpec) ;
        int width = View.MeasureSpec.getSize(widthMeasureSpec) ;
        int heightMode = View.MeasureSpec.getMode(heightMeasureSpec) ;
        int height = View.MeasureSpec.getSize(heightMeasureSpec) ;

        Log.d(Consts.APP_TAG, className + " [ w -> " + width + ", " + getModeString(widthMode) + ",   " + " h -> " + height + ", " + getModeString(heightMode) + "]") ;
    }

    private static String getModeString(int mode) {
        if (mode == View.MeasureSpec.AT_MOST) {
            return "at_most" ;
        } else if (mode == View.MeasureSpec.EXACTLY) {
            return "exactly" ;
        } else if (mode == View.MeasureSpec.UNSPECIFIED) {
            return "unspecified" ;
        }
        return "" ;
    }
}
