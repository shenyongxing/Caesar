package com.study.shenxing.caesar.scalepivot;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;

import com.study.shenxing.caesar.R;

/**
 * 验证scale 锚点相关问题
 */
public class ScalePivotActivity extends AppCompatActivity {
    private ImageView mTestImageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_scale_pivot);

        mTestImageView = (ImageView) findViewById(R.id.iv_scale_test);
        mTestImageView.setOnClickListener(testClick);
    }

    private View.OnClickListener testClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            doAnimation();
        }
    };

    private void doAnimation() {
        float startX = 1.0f;
        float endX = 1.5f;
        float startY = 1.0f;
        float endY = 1.5f;
        float pivotX = 0.5f;
        float pivotY = 0.5f;
        ScaleAnimation sa = new ScaleAnimation(startX, endX, startY, endY,
                Animation.RELATIVE_TO_PARENT, pivotX, Animation.RELATIVE_TO_PARENT, pivotY);
        sa.setDuration(5000);
        sa.setFillAfter(true);
        mTestImageView.startAnimation(sa);
    }

}
