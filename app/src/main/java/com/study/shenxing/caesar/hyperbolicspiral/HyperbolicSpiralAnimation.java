package com.study.shenxing.caesar.hyperbolicspiral;

import android.graphics.Camera;
import android.graphics.Matrix;
import android.view.animation.Animation;
import android.view.animation.Transformation;

/**
 * Created by shenxing on 15/12/15.
 * 双曲螺线动画,也称倒数曲线
 */
public class HyperbolicSpiralAnimation extends Animation {
    private Camera mCamera ;
    private float mCenterX ;
    private float mCenterY ;
    private float mDegree ;

    public HyperbolicSpiralAnimation(float rotateDegree) {
       mDegree = rotateDegree ;
    }

    @Override
    public void initialize(int width, int height, int parentWidth, int parentHeight) {
        super.initialize(width, height, parentWidth, parentHeight);
        mCamera = new Camera() ;
        mCenterX = width / 2 ;
        mCenterY = height / 2;

    }

    @Override
    protected void applyTransformation(float interpolatedTime, Transformation t) {
        super.applyTransformation(interpolatedTime, t);
        float depthZ = 1000 ;
        float constC = 500 ;

        float mCurrDegree = 1 + mDegree * interpolatedTime ;

        float x = (float) (constC * Math.cos(mCurrDegree) / mCurrDegree) ;
        float y = (float) (constC * Math.sin(mCurrDegree) / mCurrDegree) ;

        Matrix matrix = t.getMatrix() ;
        mCamera.save();
        mCamera.translate(x, y, depthZ * interpolatedTime);
        mCamera.getMatrix(matrix);
        mCamera.restore();

        matrix.preTranslate(-mCenterX, -mCenterY) ;
        matrix.postTranslate(mCenterX, mCenterY);
    }
}
