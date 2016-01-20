package com.study.shenxing.caesar.viewflip;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.AdapterViewFlipper;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.ViewFlipper;

import com.study.shenxing.caesar.R;

import java.util.ArrayList;
import java.util.List;

public class ViewFlipperActivity extends AppCompatActivity implements View.OnClickListener {

    private ViewFlipper mViewFlipper ;
    private Button mStartButton ;
    private Button mStopButton ;
    private Spinner mSpinner ;

    /**
     * 可以利用adapter提供数据源的ViewFlipper
     */
    private AdapterViewFlipper mAdapterViewFlipper ;
    private CusAdapter mCusAdapter ;

    private int[] mDrawableRes = new int[] {R.drawable.gallery_photo_1, R.drawable.gallery_photo_2,
            R.drawable.gallery_photo_3, R.drawable.gallery_photo_4, R.drawable.gallery_photo_5} ;
    private String[] mLinks = new String[]{
            "https://www.baidu.com/",
            "http://www.sina.com.cn/",
            "http://www.163.com/",
            "http://www.ifeng.com/",
            "http://xuan.3g.cn/"
    } ;
    private String[] mStrings = {
            "Push up", "Push left", "Cross fade", "Hyperspace"};
    private List<DrawableBean> mAdData = new ArrayList<>() ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_flipper);

        mViewFlipper = (ViewFlipper) findViewById(R.id.flipper);
        mAdapterViewFlipper = (AdapterViewFlipper) findViewById(R.id.adapter_view_flipper);
        mStartButton = (Button) findViewById(R.id.start);
        mStartButton.setOnClickListener(this);
        mStopButton = (Button) findViewById(R.id.stop);
        mStopButton.setOnClickListener(this);
        mSpinner = (Spinner) findViewById(R.id.spinner);
        mSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                initCusAnimation(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, mStrings);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpinner.setAdapter(adapter);
        initCusAnimation(0);
        init();
        loadAd();

        mCusAdapter = new CusAdapter(this, mAdData) ;
        mAdapterViewFlipper.setAdapter(mCusAdapter);
    }

    /**
     * 初始化资源
     */
    private void init() {
        if (mLinks.length != mDrawableRes.length) {
            throw new ArrayIndexOutOfBoundsException() ;
        }
        for (int i = 0; i < mDrawableRes.length; i++) {
            DrawableBean bean = new DrawableBean() ;
            bean.setmResId(mDrawableRes[i]);
            bean.setmDrawable(getResources().getDrawable(mDrawableRes[i]));
            bean.setmLink(mLinks[i]);
            mAdData.add(bean) ;
        }
    }

    private void initCusAnimation(int n) {
        switch (n) {
            case 0 :
                mViewFlipper.setInAnimation(AnimationUtils.loadAnimation(this, R.anim.push_up_in));
                mViewFlipper.setOutAnimation(AnimationUtils.loadAnimation(this, R.anim.push_up_out));
                break;
            case 1 :
                mViewFlipper.setInAnimation(AnimationUtils.loadAnimation(this, R.anim.push_left_in));
                mViewFlipper.setOutAnimation(AnimationUtils.loadAnimation(this, R.anim.push_left_out));
                break;
            case 2 :
                mViewFlipper.setInAnimation(AnimationUtils.loadAnimation(this, android.R.anim.fade_in));
                mViewFlipper.setOutAnimation(AnimationUtils.loadAnimation(this, android.R.anim.fade_out));
                break;
            case 3 :
                mViewFlipper.setInAnimation(AnimationUtils.loadAnimation(this, R.anim.hyperspace_in));
                mViewFlipper.setOutAnimation(AnimationUtils.loadAnimation(this, R.anim.hyperspace_out));
                break;
            default:
                mViewFlipper.setInAnimation(AnimationUtils.loadAnimation(this, R.anim.push_up_in));
                mViewFlipper.setOutAnimation(AnimationUtils.loadAnimation(this, R.anim.push_up_out));
                break;
        }
    }

    private void loadAd() {
        ViewFlipper.LayoutParams lp = new ViewFlipper.LayoutParams(
                FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT) ;
        for (final DrawableBean bean : mAdData) {
            ImageView image = new ImageView(this) ;
            image.setBackgroundResource(bean.getmResId());
            image.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    gotoBroswer(bean.getmLink()) ;
                }
            });
            mViewFlipper.addView(image, lp);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.start :
                mViewFlipper.startFlipping();
                break;
            case R.id.stop :
                mViewFlipper.stopFlipping();
                break;
            default:
                break;
        }
    }

    /**
     * 跳转到浏览器
     * @param url
     */
    private void gotoBroswer(String url) {
        Intent it = new Intent(Intent.ACTION_VIEW) ;
        it.setData(Uri.parse(url)) ;
        if (null != it.resolveActivity(getPackageManager())) {
            startActivity(it);
        }
    }

    /**
     * 图片信息实体
     */
    private class DrawableBean {
        private int mResId ;
        private Drawable mDrawable ;
        private String mLink ;

        public int getmResId() {
            return mResId;
        }

        public void setmResId(int mResId) {
            this.mResId = mResId;
        }

        public void setmDrawable(Drawable mDrawable) {
            this.mDrawable = mDrawable;
        }

        public Drawable getmDrawable() {
            return mDrawable;
        }

        public String getmLink() {
            return mLink;
        }

        public void setmLink(String mLink) {
            this.mLink = mLink;
        }
    }

    private class CusAdapter extends BaseAdapter {
        private List<DrawableBean> mDataList ;
        private Context mContext ;
        public CusAdapter(Context context, List<DrawableBean> list) {
            super();
            mContext = context ;
            mDataList = list ;
        }

        @Override
        public int getCount() {
            return mDataList.size();
        }

        @Override
        public Object getItem(int position) {
            return mDataList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            ImageView img  = new ImageView(mContext) ;
            img.setBackgroundDrawable(mDataList.get(position).mDrawable);
            img.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    gotoBroswer(mDataList.get(position).mLink);
                }
            });
            img.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            return img;
        }
    }
}
