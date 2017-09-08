package com.study.shenxing.caesar.animationeasingfunctions;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;

import com.study.shenxing.caesar.R;
import com.study.shenxing.caesar.animationeasingfunctions.linear.Linear;
import com.study.shenxing.caesar.animationeasingfunctions.quad.EaseIn;
import com.study.shenxing.caesar.animationeasingfunctions.quad.EaseInOut;
import com.study.shenxing.caesar.animationeasingfunctions.quad.EaseOut;

/**
 * @Author shenxing
 * @Date 05/09/2017
 */

public class AnimationEasingFuncActivity extends Activity {

    private DrawView mHistory;
    private View mTarget;
    private Button mBtnRun;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.animation_easing_function_layout);
        assignViews();

        mTarget.setTranslationX(0);
        mTarget.setTranslationY(0);
        mBtnRun.setOnClickListener(mRun);
    }

    private View.OnClickListener mRun = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            mHistory.clear();
            ValueAnimator valueAnimator = ObjectAnimator.ofFloat(mTarget, "translationY", 0, -mHistory.getHeight());
            valueAnimator.setDuration(2000);
            BaseEasingMethod baseEasingMethod = new EaseInOut(2000);
            baseEasingMethod.addEasingListener(new BaseEasingMethod.EasingListener() {
                @Override
                public void on(float time, float value, float start, float delta, float duration) {
                    mHistory.drawPoint(time, duration, value);
                }
            });
            valueAnimator.setEvaluator(baseEasingMethod);
            valueAnimator.start();
        }
    };

    private void assignViews() {
        mHistory = (DrawView) findViewById(R.id.history);
        mTarget = findViewById(R.id.target);
        mBtnRun = (Button) findViewById(R.id.btn_run);
    }

}
