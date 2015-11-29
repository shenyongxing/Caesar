package com.study.shenxing.caesar.graphic.animation;

import android.graphics.Camera;
import android.graphics.Matrix;
import android.util.Log;
import android.view.animation.Animation;
import android.view.animation.Transformation;

/**
 * Created by shenxing on 15/11/29.
 */
public class CusAnimation extends Animation {
    public static final String TAG = "shenxing";
    private float mCenterX ;
    private float mCenterY ;
    private Camera mCamera;
    @Override
    public void initialize(int width, int height, int parentWidth, int parentHeight) {
        super.initialize(width, height, parentWidth, parentHeight);
        // 在此方法类可以利用动画对象view及父view的宽高进行一些初始化工作
        mCenterX = width / 2;
        mCenterY = height / 2;
        setDuration(2000);
        setFillAfter(true);

        mCamera = new Camera() ;
    }

    @Override
    protected void applyTransformation(float interpolatedTime, Transformation t) {
        super.applyTransformation(interpolatedTime, t);

        Matrix matrix = t.getMatrix();
//        matrix.preTranslate(100 * interpolatedTime, 100 * interpolatedTime);

        mCamera.save();
        mCamera.translate(0.0f, 0.0f, (1300 - 1300.0f * interpolatedTime));
        mCamera.rotateY(360 * interpolatedTime);
        mCamera.getMatrix(matrix);
        matrix.preTranslate(-mCenterX, -mCenterY);
        matrix.postTranslate(mCenterX, mCenterY);
        mCamera.restore();
    }
}
