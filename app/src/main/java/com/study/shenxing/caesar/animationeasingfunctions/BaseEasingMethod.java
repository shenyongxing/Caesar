package com.study.shenxing.caesar.animationeasingfunctions;

import android.animation.TypeEvaluator;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author shenxing
 * @Date 05/09/2017
 * @Desc 通过TypeEvaluator将Tween缓动算法转化为相应的插值
 */

public abstract class BaseEasingMethod implements TypeEvaluator<Number> {

    protected float mDuration;

    public BaseEasingMethod(float duration) {
        mDuration = duration;
    }

    public void setDuration(float duration) {
        mDuration = duration;
    }

    private List<EasingListener> mEasingListeners = new ArrayList<>();


    public void addEasingListener(EasingListener easingListener) {
        if (!mEasingListeners.contains(easingListener)) {
            mEasingListeners.add(easingListener);
        }
    }

    @Override
    public Float evaluate(float fraction, Number startValue, Number endValue) {
        float t = fraction * mDuration;
        float b = startValue.floatValue();
        float c = endValue.floatValue() - b;
        float d = mDuration;
        float result = calculate(t, b, c, mDuration);
        for (EasingListener easingListener : mEasingListeners) {
            easingListener.on(t, result, b, c, d);
        }
        return result;
    }

    /**
     * 缓动算法计算方法
     * @param t
     * @param b
     * @param c
     * @param d
     * @return
     */
    public abstract Float calculate(float t, float b, float c, float d);

    /**
     * 插值监听
     */
    public interface EasingListener {
        void on(float time, float value, float start, float delta, float duration);
    }

}
