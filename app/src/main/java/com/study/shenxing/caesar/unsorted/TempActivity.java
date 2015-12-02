package com.study.shenxing.caesar.unsorted;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.study.shenxing.caesar.R;

public class TempActivity extends AppCompatActivity implements View.OnClickListener {
    private Button mButton ;
    private RelativeLayout mTestArea ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admob_ad_view);

//        mTestArea = (RelativeLayout) findViewById(R.id.test);
//        mButton = (Button) findViewById(R.id.click_button);
//        mButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId() ;
        if (id == R.id.click_button) {
            startCusAnimation() ;
        }
    }

    private void startCusAnimation() {
        ScaleAnimation scaleAnimation = new ScaleAnimation(1.0f, 0.5f, 1.0f, 0.1f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        scaleAnimation.setDuration(2000);
        RotateAnimation rotateAnimation = new RotateAnimation(0, -90, Animation.RELATIVE_TO_SELF, 0, Animation.RELATIVE_TO_SELF, 0.5f);
        rotateAnimation.setDuration(2000);

        AnimationSet set = new AnimationSet(true) ;
        set.addAnimation(scaleAnimation);
        set.addAnimation(rotateAnimation);
        set.setFillAfter(true);
        mTestArea.startAnimation(set);
    }



    // Temporary code snippet
    // DataService.java Line 741
    /*long lastShowTime = preferences.getLong(Const.CHARGING_POP_FLAG, 0);
    if (System.currentTimeMillis() - lastShowTime > 3 * 60 * 1000) {
        intent = new Intent(getApplicationContext(), MainBlackActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);

        SharedPreferences.Editor editor = preferences.edit() ;
        editor.putLong(Const.CHARGING_POP_TIME, System.currentTimeMillis()) ;
        editor.commit();
    }*/

    // MainBlankActivity.java Line 263
    /*
    * goToChargeTab()
    * // 请求广告
		mTabChargingContent.requestAd() ;
		mGotoBaterryInfoTab = false;

		在mTabChargingContent中添加requestAd()方法
    * */
}
