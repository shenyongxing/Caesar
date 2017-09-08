package com.study.shenxing.caesar.animationeasingfunctions.quad;

import com.study.shenxing.caesar.animationeasingfunctions.BaseEasingMethod;

/**
 * @Author shenxing
 * @Date 08/09/2017
 * @Email shen.xing@zyxr.com
 * @Description Copyright © 2017年 深圳中业兴融互联网金融服务有限公司. All rights reserved.
 */

public class EaseInOut extends BaseEasingMethod {

    public EaseInOut(float duration) {
        super(duration);
    }

    @Override
    public Float calculate(float t, float b, float c, float d) {

        if ((t /= d / 2) < 1) {
            return c / 2 * t * t + b;
        }
        return -c / 2 * ((--t) * (t-2) - 1) + b;
    }
}
