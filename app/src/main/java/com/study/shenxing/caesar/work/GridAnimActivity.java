package com.study.shenxing.caesar.work;

import android.animation.LayoutTransition;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;

import com.study.shenxing.caesar.R;
import com.study.shenxing.caesar.common.CommonTestAdapter;
import com.study.shenxing.caesar.common.DataGenerator;


public class GridAnimActivity extends Activity implements View.OnClickListener {
    private GridView mGridView ;
    private Button mStButtton ; // 开始
    private Handler mHandler = new Handler() ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grid_anim);

        mGridView = (GridView) findViewById(R.id.grid_view);
        CommonTestAdapter<String> adapter = new CommonTestAdapter<>(this, DataGenerator.getInstance().getListDatas()) ;
        mGridView.setAdapter(adapter);

        mStButtton = (Button) findViewById(R.id.start_anim_button);
        mStButtton.setOnClickListener(this);

        setGridAnimation();
    }

    private void setGridAnimation() {
        LayoutTransition transition = new LayoutTransition() ;
        PropertyValuesHolder pvhX = PropertyValuesHolder.ofFloat("scaleX", 1, 0) ;
        PropertyValuesHolder pvhY = PropertyValuesHolder.ofFloat("scaleY", 1, 0) ;
        ValueAnimator va = ObjectAnimator.ofPropertyValuesHolder(pvhX, pvhY) ;
        va.setDuration(1000) ;
        transition.setAnimator(LayoutTransition.DISAPPEARING, va);
        mGridView.setLayoutTransition(transition);
    }

    @Override
    public void onContentChanged() {
        super.onContentChanged();
    }

    @Override
    public void onClick(View v) {
        int childCount = mGridView.getChildCount() ;
        for (int i = 0; i < childCount; i++) {
            final View childView = mGridView.getChildAt(i) ;
            mHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    childView.setVisibility(View.GONE);
                }
            }, 20 * i) ;
        }
    }
}
