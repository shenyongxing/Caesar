package com.study.shenxing.caesar.animationeasingfunctions.linear;

import com.study.shenxing.caesar.animationeasingfunctions.BaseEasingMethod;

/**
 * @Author shenxing
 * @Date 05/09/2017
 * @Desc 线性插值
 */

public class Linear extends BaseEasingMethod {

    public Linear(float duration) {
        super(duration);
    }

    @Override
    public Float calculate(float t, float b, float c, float d) {
        return b + c * t / d;
    }
}
