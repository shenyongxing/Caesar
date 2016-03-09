package com.study.shenxing.caesar.chargelock.activity;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.text.Spanned;
import android.text.SpannedString;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;


import com.study.shenxing.caesar.R;
import com.study.shenxing.caesar.chargelock.view.ShimmerTextView;
import com.study.shenxing.caesar.chargelock.view.anim.PowerSavingAnimScene;
import com.study.shenxing.caesar.chargelock.view.anim.PowerSavingAnimView;
import com.study.shenxing.caesar.chargelock.view.anim.PowerSavingLayer;
import com.study.shenxing.caesar.others.Consts;

import java.util.Calendar;

/**
 * 锁屏主界面，锁屏或者灭屏时启动
 *
 * @author zhanghuijun
 */
public class PowerSavingActivity extends Activity implements OnClickListener, PowerSavingLayer.IAccelAnimation {

    private final static String TAG = "PowerSavingActivity";

    private final static String AD_TAG = "AD_CHARGE";

    /**
     * Extra
     */
    public final static String EXTRA_SHOW_ACCEL_ANIM_MODE = "extra_show_accel_anim_mode";        // 加速模式
    public final static String EXTRA_SCREEN_IS_ON = "extra_screen_is_on";        // 亮屏还是灭屏
    /**
     * 加速动画的模式
     */
    public final static int MODE_NO_ANIM = 0;            // 没有动画
    public final static int MODE_ANIM_WITH_RESULT = 1;    // 有动画，并且有结果反馈
    public final static int MODE_RESULT_ONLY = 2;        // 没有动画，只有结果
    private int mAnimMode = MODE_NO_ANIM;
    /**
     * 背景动画层
     */
    private PowerSavingAnimView mPowerAnimView = null;
    /**
     * 时间
     */
    private TextView mTimeText = null;
    private TextView mDateText = null;
    /**
     * 菜单
     */
    private ImageView mMenuBtn = null;
    /**
     * 菜单项
     */
    private TextView mMenuCloseBtn = null;
    /**
     * 电量
     */
    private TextView mPowerRate = null;
    /**
     * 电量描述
     */
    private TextView mPowerInstrution = null;
    /**
     * 充电模式
     */
    private RelativeLayout mPrcocessContainer = null;
    private RelativeLayout mPrcocessTextContainer = null;
    private ImageView mModeSpeed = null;
    private ImageView mModeContiuous = null;
    private ImageView mModeTrickle = null;
    private TextView mModeSpeedText = null;
    private TextView mModeTrickleText = null;
    private boolean mModeHasLayouted = false;
    /**
     * 点击文案
     */
    private RelativeLayout mModeInstructionContainer = null;
    private TextView mModeInstruction = null;
    private View mTriangelSpeed = null;
    private View mTriangelContiuous = null;
    private View mTriangelTrickle = null;
    /**
     * 解锁View
     */
    private ShimmerTextView mUnlockTextView = null;
    /**
     * ViewPager
     */
    private ViewPager mViewPager = null;
    /**
     * 空白View
     */
    private View mEmptyView = null;
    /**
     * 主界面View
     */
    private View mMainView = null;
    /**
     * ViewPagerAdapter
     */
    private ViewPagerAdapter mViewPagerAdapter = null;

    /**
     * 正在展示说明
     */
    private int mShowInstructionMode = -1;
    private Runnable mShowInstructionRunnable = null;
    /**
     * 加速结果
     */
    private TextView mAccelResultText = null;
    /**
     * 菜单关闭按钮对话框
     */
    private Dialog mCloseConfirmDialog ;
    /**
     * 屏幕是否亮
     */
    private boolean mIsScreenOn = true;
    /**
     * 加速动画是否进行中
     */
    private boolean mIsInAccelAnim = false;


    private View mAdView;
    private View mNoticeShowView;

    /**
     * 快速充电模式
     */
    private static final int MODE_CHARING_SPEED = 0 ;
    /**
     * 持续充电模式
     */
    private static final int MODE_CHARING_CONTIUOUS = 1 ;
    /**
     * 涓流充电模式
     */
    private static final int MODE_CHARING_TRICKLE = 2 ;

    /**
     * Handler
     */
    private Handler mHanlder = new Handler() ;

    /**
     * 时间分隔符
     */
    private static final String TIME_SEPARATOR = ":" ;
    /**
     * date信息
     */
    private int mMonth;
    private int mDay;
    private int mDayOfWeek;

    /**
     * 月份数组
     */
    private String[] mMonthArray ;

    /**
     * 星期数组
     */
    private String[] mWeekArray ;
    /**
     * 充电剩余时间
     */
    private Spanned mChargingLeftTime ;

    /**
     * 剩余可用时间
     */
    private int mAvailableTime ;

    /**
     * 延长的可用时间
     */
    private int mExtendTime ;

    /**
     * 杀死的app个数
     */
    private int mKillAppSize ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate");
        super.onCreate(savedInstanceState);
        // 显示在锁屏上面
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD);
        // 界面部分
        mViewPager = new ViewPager(this);
        mViewPager.setLayoutParams(new ViewGroup.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
        setContentView(mViewPager);
        // 空白透明View
        mEmptyView = new View(this);
        mEmptyView.setLayoutParams(new ViewGroup.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
        // 主界面View
        mMainView = getLayoutInflater().inflate(R.layout.activity_powersaving_main, mViewPager, false);
        mPowerAnimView = (PowerSavingAnimView) mMainView.findViewById(R.id.power_saving_anim_view);
        PowerSavingAnimScene animScene = mPowerAnimView.getmPowerSavingAnimScene();
        animScene.registerAccelAnim(this);
        mTimeText = (TextView) mMainView.findViewById(R.id.poswer_saving_time);
        mDateText = (TextView) mMainView.findViewById(R.id.poswer_saving_date);
        mMenuBtn = (ImageView) mMainView.findViewById(R.id.power_saving_menu_btn);
        mMenuCloseBtn = (TextView) mMainView.findViewById(R.id.power_saving_menu_close_btn);
        mPowerRate = (TextView) mMainView.findViewById(R.id.poswer_saving_rate_num);
        mPowerInstrution = (TextView) mMainView.findViewById(R.id.poswer_saving_charging_text);
        mModeSpeed = (ImageView) mMainView.findViewById(R.id.power_saving_mode_speed);
        mModeContiuous = (ImageView) mMainView.findViewById(R.id.power_saving_mode_contiuous);
        mModeTrickle = (ImageView) mMainView.findViewById(R.id.power_saving_mode_trickle);
        mModeSpeedText = (TextView) mMainView.findViewById(R.id.power_saving_mode_speed_text);
        mModeTrickleText = (TextView) mMainView.findViewById(R.id.power_saving_mode_trickle_text);
        mModeInstructionContainer = (RelativeLayout) mMainView.findViewById(R.id.power_saving_mode_instruction_container);
        mModeInstruction = (TextView) mMainView.findViewById(R.id.power_saving_mode_instruction_text);
        mTriangelSpeed = mMainView.findViewById(R.id.power_saving_mode_triangle_speed);
        mTriangelContiuous = mMainView.findViewById(R.id.power_saving_mode_triangle_contiuous);
        mTriangelTrickle = mMainView.findViewById(R.id.power_saving_mode_triangle_trickle);
        mAccelResultText = (TextView) mMainView.findViewById(R.id.poswer_saving_accel_over_text);
        mMenuBtn = (ImageView) mMainView.findViewById(R.id.power_saving_menu_btn);
        mMenuCloseBtn = (TextView) mMainView.findViewById(R.id.power_saving_menu_close_btn);
        mPrcocessContainer = (RelativeLayout) mMainView.findViewById(R.id.power_saving_process_container);
        mPrcocessTextContainer = (RelativeLayout) mMainView.findViewById(R.id.power_saving_process_text_container);
        mUnlockTextView = (ShimmerTextView) mMainView.findViewById(R.id.power_saving_unlock_text);
        mModeSpeed.setOnClickListener(this);
        mModeContiuous.setOnClickListener(this);
        mModeTrickle.setOnClickListener(this);
        mMenuBtn.setOnClickListener(this);
        mMenuCloseBtn.setOnClickListener(this);
        mMainView.setOnClickListener(this);
        mViewPagerAdapter = new ViewPagerAdapter();
        mViewPager.setAdapter(mViewPagerAdapter);
        mViewPager.setOffscreenPageLimit(2);
        // 默认在主界面
        mViewPager.setCurrentItem(1);
        mViewPager.setOnPageChangeListener(mOnPageChangeListener);
//        mAdContainerView = mMainView.findViewById(R.id.power_saving_ad_container);
//        mAdTouchListener = new AdTouchListener();
//        mAdView.setOnTouchListener(mAdTouchListener);
//        mAdContainerView.setOnTouchListener(mAdTouchListener);
        // 初始化数据
        onNewIntent(getIntent());
        ViewTreeObserver vto = mMainView.getViewTreeObserver();
        vto.addOnGlobalLayoutListener(new OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                mMainView.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                // 调整中间充电进度下面文案的位置，居中
                int marginLeft = (getResources().getDisplayMetrics().widthPixels - mPrcocessContainer.getMeasuredWidth()) / 2;
                int processIconWidth = mModeSpeed.getMeasuredWidth();
                int speedTextWidth = mModeSpeedText.getMeasuredWidth();
                int trickleTextWidth = mModeTrickleText.getMeasuredWidth();
                LayoutParams lp = (LayoutParams) mModeSpeedText.getLayoutParams();
                lp.leftMargin = marginLeft - (speedTextWidth - processIconWidth) / 2;
                mModeSpeedText.setLayoutParams(lp);
                lp = (LayoutParams) mModeTrickleText.getLayoutParams();
                lp.rightMargin = marginLeft - (trickleTextWidth - processIconWidth) / 2;
                mModeTrickleText.setLayoutParams(lp);
                mModeHasLayouted = true;
                // 刷新界面
                updateView();
            }
        });

        initReceiver();
        initTimeAndDate() ;
//        initData();

    }

    @Override
    protected void onResume() {
        Log.d(TAG, "onResume");
        super.onResume();
        mPowerAnimView.onResume();
        mUnlockTextView.start();

        hideStateBarAndNavitionBar();
    }


    /**
     * 初始化时间和日期信息
     */
    private void initTimeAndDate() {
        mMonthArray = getResources().getStringArray(R.array.month_name) ;
        mWeekArray = getResources().getStringArray(R.array.week_name) ;
        long now = System.currentTimeMillis();
        updateDate(now);
        updateTime(now);
    }

    /**
     * 初始化广播接收器
     */
    private void initReceiver() {
        IntentFilter filter = new IntentFilter() ;
        filter.addAction(Intent.ACTION_TIME_TICK);
        filter.addAction(Intent.ACTION_DATE_CHANGED);
        filter.addAction(Intent.ACTION_TIME_CHANGED);
        filter.addAction(Intent.ACTION_POWER_DISCONNECTED);
        filter.addAction("android.intent.action.PHONE_STATE");
    }

    /**
     * 更新视图
     */
    private void updateView() {
        Log.d(TAG, "mIsScreenOn : " + mIsScreenOn);
        mPowerRate.setText(String.valueOf(/*BatteryInfo.getLevelPercent(this)*/70));
        mUnlockTextView.start();
        if (isCharging()) {
            if (/*BatteryInfo.getLevelPercent(this) == 100 || !mModeHasLayouted*/false) {
                // 充满了
                mPowerInstrution.setVisibility(View.INVISIBLE);
                mPrcocessContainer.setVisibility(View.INVISIBLE);
                mPrcocessTextContainer.setVisibility(View.INVISIBLE);
            } else {
                mPowerInstrution.setText(mChargingLeftTime);
                mPowerInstrution.setVisibility(View.VISIBLE);
                mPrcocessContainer.setVisibility(View.VISIBLE);
                mPrcocessTextContainer.setVisibility(View.VISIBLE);
                // 充电进度
                if (/*BatteryInfo.getLevelPercent(this) <= 80*/false) {
                    mModeSpeed.setImageResource(R.drawable.power_saving_speed);
                    mModeContiuous.setImageResource(R.drawable.power_saving_contiuous_2);
                    mModeTrickle.setImageResource(R.drawable.power_saving_trickle_2);
                    mModeContiuous.clearAnimation();
                    mModeTrickle.clearAnimation();
                    if (mModeSpeed.getAnimation() == null) {
                        mModeSpeed.startAnimation(getBreathAnimtion());
                    }
                } else if (/*BatteryInfo.getLevelPercent(this) > 80 && BatteryInfo.getLevelPercent(this) < 100*/true) {
                    mModeSpeed.setImageResource(R.drawable.power_saving_speed);
                    mModeContiuous.setImageResource(R.drawable.power_saving_contiuous);
                    mModeTrickle.setImageResource(R.drawable.power_saving_trickle_2);
                    mModeSpeed.clearAnimation();
                    mModeTrickle.clearAnimation();
                    if (mModeContiuous.getAnimation() == null) {
                        mModeContiuous.startAnimation(getBreathAnimtion());
                    }
                } else {
                    mModeSpeed.setImageResource(R.drawable.power_saving_speed);
                    mModeContiuous.setImageResource(R.drawable.power_saving_contiuous);
                    mModeTrickle.setImageResource(R.drawable.power_saving_trickle);
                    mModeSpeed.clearAnimation();
                    mModeContiuous.clearAnimation();
                    if (mModeTrickle.getAnimation() == null) {
                        mModeTrickle.startAnimation(getBreathAnimtion());
                    }
                }
                if (!mIsScreenOn) {
                    mModeSpeed.clearAnimation();
                    mModeContiuous.clearAnimation();
                    mModeTrickle.clearAnimation();
                }
            }
        } else {
            mPowerInstrution.setText(getAvailableTime());
            mPowerInstrution.setVisibility(View.VISIBLE);
            mPrcocessContainer.setVisibility(View.INVISIBLE);
            mPrcocessTextContainer.setVisibility(View.INVISIBLE);
        }
    }

    /**
     * 生成呼吸动画
     */
    private Animation getBreathAnimtion() {
        AlphaAnimation alphaAnimation = new AlphaAnimation(1f, 0.3f);
        alphaAnimation.setDuration(750);
        alphaAnimation.setRepeatMode(Animation.REVERSE);
        alphaAnimation.setRepeatCount(Animation.INFINITE);
        return alphaAnimation;
    }

    /**
     * 加速反馈结果的出现动画
     *
     * @return
     */
    private AnimationSet getAccelResultAnim() {
        int time = 1000;
        AnimationSet as = new AnimationSet(getApplicationContext(), null);
        TranslateAnimation ta = new TranslateAnimation(
                TranslateAnimation.RELATIVE_TO_SELF, 0f,
                TranslateAnimation.RELATIVE_TO_SELF, 0f,
                TranslateAnimation.RELATIVE_TO_PARENT, 1f,
                TranslateAnimation.RELATIVE_TO_PARENT, 0f);
        ta.setDuration(time);
        AlphaAnimation aa = new AlphaAnimation(0f, 1f);
        aa.setDuration(time);
        as.addAnimation(ta);
        as.addAnimation(aa);
        as.setStartOffset(500);
        return as;
    }

    /**
     * 加速动画结束事件
     */
    public void handleAccelEndEvent(int extendtime, int appsize) {
        // 反馈结果
        mAccelResultText.setText(getExtendTimeSpanString(extendtime, appsize));
        mAccelResultText.setVisibility(View.VISIBLE);
        mAccelResultText.startAnimation(getAccelResultAnim());

        mHanlder.postDelayed(new Runnable() {
            @Override
            public void run() {
                mAccelResultText.clearAnimation();
                mAccelResultText.setVisibility(View.GONE);
                // 展示解锁文字
                mUnlockTextView.setVisibility(View.VISIBLE);
                mIsInAccelAnim = false;

                hideStateBarAndNavitionBar();

                mExtendTime = 0 ;
                mKillAppSize = 0;
            }
        }, 3000) ;
    }

    /**
     * 开始加速动画
     */
    public void startAccelAnimation() {
        Intent it = new Intent(Consts.KEY_START_ACCEL_ANIM) ;
        sendBroadcast(it);
    }

    /**
     * 更新波浪电池高度
     */
    private void updateWaveHeight() {
        PowerSavingAnimScene powerSavingAnimScene = mPowerAnimView.getmPowerSavingAnimScene();
        if (powerSavingAnimScene != null) {
            powerSavingAnimScene.updateWaveHeight();
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        Log.d(TAG, "onNewIntent");
        boolean isAdShowed = false;
        if (intent != null) {
            mAnimMode = intent.getIntExtra(EXTRA_SHOW_ACCEL_ANIM_MODE, MODE_NO_ANIM);
            Log.d(TAG, "mAnimMode : " + mAnimMode);
            if (/*isCharging() && (BatteryInfo.getLevelPercent(this) != 0 && BatteryInfo.getLevelPercent(this) <= 100)*/true) {
                if (mAnimMode == MODE_ANIM_WITH_RESULT) {
                    // 隐藏广告
                    mAdView.setVisibility(View.INVISIBLE);
                    mNoticeShowView.setVisibility(View.INVISIBLE);
                    // 开始动画
                    startAccelAnimation();
                    // 杀进程
//                    sendKillProgramBroadcast(true);
                    // 消失解锁文字
                    mUnlockTextView.setVisibility(View.GONE);
                    mIsInAccelAnim = true;
                    isAdShowed = true ;
                    // 统计
                } else if (mAnimMode == MODE_RESULT_ONLY) {
                    // 只弹结果
                    // 杀进程
                    isAdShowed = true ;
                }
            }
            mIsScreenOn = intent.getBooleanExtra(EXTRA_SCREEN_IS_ON, true);
        }
        if (mIsScreenOn) {
            // 更新
            updateView();
        } else {
//            mAdView.setVisibility(View.INVISIBLE);
            mNoticeShowView.setVisibility(View.INVISIBLE);
            mAdView.clearAnimation();
        }
    }


    /**
     * 获取启动Intent
     *
     * @param showAccelAnimMode 展示加速动画的模式
     * @param isScreenOn    屏幕是否亮
     */
    public static Intent getStartIntent(Context context, int showAccelAnimMode, boolean isScreenOn) {
        Log.d(TAG, "showAccelAnimMode : " + showAccelAnimMode);
        Intent intent = new Intent(context, PowerSavingActivity.class);
        if (!(context instanceof Activity)) {
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        }
        intent.putExtra(EXTRA_SHOW_ACCEL_ANIM_MODE, showAccelAnimMode);
        intent.putExtra(EXTRA_SCREEN_IS_ON, isScreenOn);
        return intent;
    }

    @Override
    protected void onPause() {
        Log.d(TAG, "onPause");
        super.onPause();
        mPowerAnimView.onPause();
        mUnlockTextView.stop();
        // 隐藏广告
//        mAdContainerView.setVisibility(View.GONE);
        // 当锁屏被其他锁屏所覆盖时，广告view会不可见
//        mAdView.setVisibility(View.INVISIBLE);
//        mNoticeShowView.setVisibility(View.INVISIBLE);
//        mAdView.clearAnimation();
    }

    @Override
    protected void onDestroy() {
        Log.d(TAG, "onDestroy");
        super.onDestroy();
        mPowerAnimView.onDestroy();
    }

    /**
     * 我的PagerAdapter
     *
     * @author zhanghuijun
     */
    public class ViewPagerAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return 2;
        }

        @Override
        public boolean isViewFromObject(View view, Object o) {
            return view == o;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            if (position == 0) {
                mViewPager.removeView(mEmptyView);
            } else if (position == 1) {
                mViewPager.removeView(mMainView);
            }
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            if (position == 0) {
                mViewPager.addView(mEmptyView);
                return mEmptyView;
            } else if (position == 1) {
                mViewPager.addView(mMainView);
                return mMainView;
            }
            return super.instantiateItem(container, position);
        }
    }

    /**
     * ViewPager的滑动监听
     */
    private OnPageChangeListener mOnPageChangeListener = new OnPageChangeListener() {

        @Override
        public void onPageSelected(int position) {
            if (position == 0) {
                // 关闭界面
                finish();
            }
        }

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {
        }

        @Override
        public void onPageScrollStateChanged(int arg0) {
        }
    };


    /**
     * 点击事件
     */
    @Override
    public void onClick(View v) {
        if (v == mModeSpeed) {
            mMenuCloseBtn.setVisibility(View.GONE);
            // 展示说明
            showModeInstruction(MODE_CHARING_SPEED);
        } else if (v == mModeContiuous) {
            mMenuCloseBtn.setVisibility(View.GONE);
            // 展示说明
            showModeInstruction(MODE_CHARING_CONTIUOUS);
        } else if (v == mModeTrickle) {
            mMenuCloseBtn.setVisibility(View.GONE);
            // 展示说明
            showModeInstruction(MODE_CHARING_TRICKLE);
        } else if (v == mMenuBtn) {
            if (mMenuCloseBtn.getVisibility() == View.VISIBLE) {
                mMenuCloseBtn.setVisibility(View.GONE);
            } else {
                mMenuCloseBtn.setVisibility(View.VISIBLE);
            }
        } else if (v == mMenuCloseBtn) {
            mMenuCloseBtn.setVisibility(View.GONE);
        } else {
            mMenuCloseBtn.setVisibility(View.GONE);
        }
    }



    /**
     * 展示说明
     */
    private void showModeInstruction(int mode) {
        if (mShowInstructionMode == mode) {
            return;
        }
        LayoutParams lp = null;
        lp = (LayoutParams) mModeInstruction.getLayoutParams();
        switch (mode) {
            case MODE_CHARING_SPEED:
                mModeInstruction.setText("1");
                lp.addRule(RelativeLayout.ALIGN_PARENT_LEFT, RelativeLayout.TRUE);
                lp.addRule(RelativeLayout.CENTER_HORIZONTAL, 0);
                lp.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, 0);
                mTriangelSpeed.setVisibility(View.VISIBLE);
                mTriangelContiuous.setVisibility(View.GONE);
                mTriangelTrickle.setVisibility(View.GONE);
                break;
            case MODE_CHARING_CONTIUOUS:
                mModeInstruction.setText("2");
                lp.addRule(RelativeLayout.ALIGN_PARENT_LEFT, 0);
                lp.addRule(RelativeLayout.CENTER_HORIZONTAL, RelativeLayout.TRUE);
                lp.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, 0);
                mTriangelSpeed.setVisibility(View.GONE);
                mTriangelContiuous.setVisibility(View.VISIBLE);
                mTriangelTrickle.setVisibility(View.GONE);
                break;
            case MODE_CHARING_TRICKLE:
                mModeInstruction.setText("3");
                lp.addRule(RelativeLayout.ALIGN_PARENT_LEFT, 0);
                lp.addRule(RelativeLayout.CENTER_HORIZONTAL, 0);
                lp.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, RelativeLayout.TRUE);
                mTriangelSpeed.setVisibility(View.GONE);
                mTriangelContiuous.setVisibility(View.GONE);
                mTriangelTrickle.setVisibility(View.VISIBLE);
                break;
            default:
                break;
        }
        mModeInstruction.setLayoutParams(lp);
        mModeInstructionContainer.setVisibility(View.VISIBLE);
        mShowInstructionMode = mode;
        if (mShowInstructionRunnable != null) {
            mHanlder.removeCallbacks(mShowInstructionRunnable);
        }
        mShowInstructionRunnable = new Runnable() {

            @Override
            public void run() {
                mModeInstructionContainer.setVisibility(View.GONE);
                mShowInstructionMode = -1;
                mShowInstructionRunnable = null;
            }
        };
        mHanlder.postDelayed(mShowInstructionRunnable, 3000) ;
    }


    /**
     *
     */
    class AdTouchListener implements View.OnTouchListener {

        private float mOrgRawX;
        private float mOrgRawY;

        private boolean mIntercept = true;

        @Override
        public boolean onTouch(View v, MotionEvent event) {

            int action = event.getAction();
            float rawX = event.getRawX();
            float rawY = event.getRawY();

            if (action == MotionEvent.ACTION_DOWN) {
                mOrgRawX = rawX;
                mOrgRawY = rawY;
            } else if (action == MotionEvent.ACTION_MOVE) {
            } else if (action == MotionEvent.ACTION_UP) {
                if (isMove(mOrgRawX, rawX, mOrgRawY, rawY)) {
                    return true;
                } else {
                    v.performClick();
                    return false;
                }

            }
            return true;
        }
    }

    /**
     * 判断是否移动，区分滑动和点击
     *
     * @param orgRawX
     * @param rawX
     * @param orgRawY
     * @param rawY
     * @return
     */
    private boolean isMove(float orgRawX, float rawX, float orgRawY, float rawY) {
        if (Math.abs(orgRawX - rawX) > 5 || Math.abs(orgRawY - rawY) > 5) {
            return true;
        }

        return false;
    }

    /** <br>功能简述:是否正在充电
     * @return
     */
    private boolean isCharging() {
        return /*BatteryInfo.isUsingAcElectricity(this) || BatteryInfo.isUsingUsbElectricity(this);*/true ;
    }



    /**
     * 更新时间信息
     * @param time
     */
    private void updateTime(long time) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(time);

        int hour = calendar.get(Calendar.HOUR_OF_DAY) ;
        String hour_str = hour <= 9 ? "0" + hour : String.valueOf(hour) ;
        int minute = calendar.get(Calendar.MINUTE);
        String minute_str = minute <= 9 ? "0" + minute : String.valueOf(minute) ;
        String time_str = hour_str + TIME_SEPARATOR + minute_str ;
        if (mTimeText != null) {
            mTimeText.setText(time_str);
        }
    }

    /**
     * 更新日期信息
     * @param time
     */
    private void updateDate(long time) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(time);

        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK) - 1 ;

        if (mMonth == month && mDay == day && mDayOfWeek == dayOfWeek) {
            return ;
        }
        mMonth = month;
        mDay = day;
        mDayOfWeek = dayOfWeek ;
        String date_str = mWeekArray[dayOfWeek] + "," + mMonthArray[month] + mDay ;
        if (mDateText != null) {
            mDateText.setText(date_str);
        }
    }

    /**
     * 获取剩余充电时间spanned对象
     * @param seconds
     * @return
     */
    private Spanned getLeftTimeSpanString(long seconds) {

        return new SpannedString("fuck");
    }

    /**
     * 获取清理结果spanned对象
     * @param extendtime
     * @param appsize
     * @return
     */
    private Spanned getExtendTimeSpanString(int extendtime, int appsize) {
        return new SpannedString("一万年够不够");
    }

    /**
     * 获取剩余可用电量
     * @return
     */
    private Spanned getAvailableTime() {
        return new SpannedString("一万年够不够");
    }

    /**
     * 功能描述： 隐藏通知栏和虚拟按键
     */
    public void hideStateBarAndNavitionBar() {
        if (Build.VERSION.SDK_INT > 10) {
            View decorView = getWindow().getDecorView();
            decorView.setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        }
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            hideStateBarAndNavitionBar();
        }
    }

    @Override
    public void onAccelEnd() {
        handleAccelEndEvent(mExtendTime, mKillAppSize);
    }
}
