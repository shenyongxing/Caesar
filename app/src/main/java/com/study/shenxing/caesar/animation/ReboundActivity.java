package com.study.shenxing.caesar.animation;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.facebook.rebound.SimpleSpringListener;
import com.facebook.rebound.Spring;
import com.facebook.rebound.SpringChain;
import com.facebook.rebound.SpringConfig;
import com.facebook.rebound.SpringSystem;
import com.study.shenxing.caesar.R;
import com.study.shenxing.caesar.animationeasingfunctions.linear.Linear;

import java.util.List;

/**
 * @Author shenxing
 * @Date 2017/9/18
 * @Email shen.xing@zyxr.com
 * @Description rebound开源库实践
 */

public class ReboundActivity extends AppCompatActivity {

    private ImageView mTarget;
    private Button mBtnRun;
    private LinearLayout mLinearLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rebound);
        assignViews();
    }

    private void assignViews() {
        mTarget = (ImageView) findViewById(R.id.target);
        mBtnRun = (Button) findViewById(R.id.btn_run);
        mBtnRun.setOnClickListener(mRunClickListener);
        mLinearLayout = (LinearLayout) findViewById(R.id.ll_container);
    }

    private View.OnClickListener mRunClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
//            testRebound();
            testSpringChain();
        }
    };

    private void testRebound() {
        SpringSystem springSystem = SpringSystem.create();
        Spring spring = springSystem.createSpring();
        spring.setSpringConfig(new SpringConfig(50, 3));
        spring.addListener(new SimpleSpringListener() {
            @Override
            public void onSpringUpdate(Spring spring) {
                super.onSpringUpdate(spring);
                float curr = (float) spring.getCurrentValue();
                float scale = 1 - (curr * 0.5f);
                mTarget.setScaleX(scale);
                mTarget.setScaleY(scale);
            }
        });
        spring.setEndValue(1.0f);
    }

    private void testSpringChain() {
        SpringChain springChain = SpringChain.create(40, 7, 50, 6);
        for (int i = 0; i < mLinearLayout.getChildCount(); i++) {
            final View view = mLinearLayout.getChildAt(i);
            springChain.addSpring(new SimpleSpringListener() {
                @Override
                public void onSpringUpdate(Spring spring) {
                    super.onSpringUpdate(spring);
                    view.setTranslationY((float) spring.getCurrentValue());
                }
            });
        }
        List<Spring> allSprings = springChain.getAllSprings();
        for (int i = 0; i < allSprings.size(); i++) {
            Spring spring = allSprings.get(i);
            // translateY从400移动到0
            spring.setCurrentValue(400);
        }
        // 此处的结束值应该是0
        springChain.setControlSpringIndex(0).getControlSpring().setEndValue(0f);
    }



}
