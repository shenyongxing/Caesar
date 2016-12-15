package com.study.shenxing.caesar.interpolator;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.study.shenxing.caesar.R;
import com.study.shenxing.caesar.chargelock.view.anim.EaseCubicInterpolator;
import com.study.shenxing.caesar.chargelock.view.anim.TranslateAnim;

public class InterpolatorActivity extends Activity {
    public static final String TAG = "sh";
    private LinearLayout mActivity_interpolator;
    private TextView mTvPeriod;
    private SeekBar mSbPeriod;
    private TextView mTvOvershot;
    private SeekBar mSbOvershot;
    private TextView mTvTime;
    private SeekBar mSbTime;
    private ImageView mIvObstacle;

    private float mPeriod;
    private float mOvershot;
    private int mTime;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_interpolator);

        bindViews();
    }

    private void bindViews() {
        mActivity_interpolator = (LinearLayout) findViewById(R.id.activity_interpolator);
        mTvPeriod = (TextView) findViewById(R.id.tv_period);
        mSbPeriod = (SeekBar) findViewById(R.id.sb_period);
        mSbPeriod.setOnSeekBarChangeListener(mPeriodChangedListener);
        mTvOvershot = (TextView) findViewById(R.id.tv_overshot);
        mSbOvershot = (SeekBar) findViewById(R.id.sb_overshot);
        mSbOvershot.setOnSeekBarChangeListener(mOvershotChangedListener);
        mTvTime = (TextView) findViewById(R.id.tv_time);
        mSbTime = (SeekBar) findViewById(R.id.sb_time);
        mSbTime.setOnSeekBarChangeListener(mTimeChangedListener);
        mIvObstacle = (ImageView) findViewById(R.id.iv_obstacle);
        mIvObstacle.setOnClickListener(mObstacleListener);
    }


    private SeekBar.OnSeekBarChangeListener mPeriodChangedListener = new SeekBar.OnSeekBarChangeListener() {
        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            Log.i(TAG, "period onProgressChanged: " + progress);
            mPeriod = 1.0f * progress / 100;
            mTvPeriod.setText(String.valueOf(mPeriod));
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {

        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {

        }
    };

    private SeekBar.OnSeekBarChangeListener mOvershotChangedListener = new SeekBar.OnSeekBarChangeListener() {
        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            Log.i(TAG, "overshot onProgressChanged: " + progress);
            mOvershot = 1.0f * progress / 100;
            mTvOvershot.setText(String.valueOf(mOvershot));
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {

        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {

        }
    };

    private SeekBar.OnSeekBarChangeListener mTimeChangedListener = new SeekBar.OnSeekBarChangeListener() {
        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            Log.i(TAG, "time onProgressChanged: " + progress);
            mTime = progress;
            mTvTime.setText(String.valueOf(mTime));
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {

        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {

        }
    };

    private Animation createAnimation() {
        TranslateAnimation anim = new TranslateAnimation(Animation.RELATIVE_TO_PARENT, 0, Animation.RELATIVE_TO_PARENT, 0.5f, Animation.RELATIVE_TO_SELF, 0, Animation.RELATIVE_TO_SELF, 0);
        anim.setDuration(mTime);
        anim.setInterpolator(new ElasticEaseOutInterpolator(mPeriod, mOvershot));
        return anim;
    }

    private View.OnClickListener mObstacleListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            mIvObstacle.startAnimation(createAnimation());
        }
    };
}
