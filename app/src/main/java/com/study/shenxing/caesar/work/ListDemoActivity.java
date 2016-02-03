package com.study.shenxing.caesar.work;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.AnimationSet;
import android.view.animation.TranslateAnimation;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.study.shenxing.caesar.R;

import java.util.ArrayList;
import java.util.List;

public class ListDemoActivity extends AppCompatActivity {
    private ListView mHotWordListView ;
    private CusAdpter mAdapter ;
    // 用于无网络或者关键词获取失败时默认关键词
    private final static String[] DEFAULT_HOT_WORDS = {
            "mesothelioma",
            "culinary art school",
            "school online",
            "dui lawyer phoenix",
            "online mba degrees",
            "annuity buyout",
            "online MBA",
            "mesothelioma lawyer",
            "psychology online",
            "car donations",
            "college online",
            "pogo com free games",
            "viagra",
            "donate vehicles",
            "lawyer dui",
            "ecommerce web hosts",
            "online mbas",
            "tax relief help",
            "auto accident claims",
            "vehicle donations",
            "irs tax attorneys",
            "car insurance policy",
            "car auto insurance",
            "refinancing mortgage",
            "irs lawyer",
            "auto home insurance",
            "online stock trades"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_demo);

        mHotWordListView = (ListView) findViewById(R.id.hot_word_list);
        List<String[]> data = wrapperData(DEFAULT_HOT_WORDS) ;
        mAdapter = new CusAdpter(this, data) ;
        mHotWordListView.setAdapter(mAdapter);
    }

    private List<String[]> wrapperData(String[] datas) {
        List<String[]> list = new ArrayList<String[]>() ;
        for (int i = 0; i < DEFAULT_HOT_WORDS.length / 2; i += 2) {
            String[] temp = new String[2] ;
            temp[0] = DEFAULT_HOT_WORDS[i] ;
            temp[1] = DEFAULT_HOT_WORDS[i + 1] ;
            list.add(temp) ;
        }
        return list;
    }

    private class CusAdpter extends BaseAdapter {
        private Context mContext ;
        private List<String[]> mDataList ;
        public CusAdpter(Context context, List<String[]> dataList) {
            super();
            mContext = context ;
            mDataList = dataList ;
        }

        @Override
        public int getCount() {
            return mDataList.size() > 3 ? 3 : mDataList.size();
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
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder viewHolder ;
            if (convertView == null) {
                LayoutInflater inflate = LayoutInflater.from(mContext);
                convertView = inflate.inflate(R.layout.hot_word_item, null) ;
                viewHolder = new ViewHolder() ;
                viewHolder.mIcon1 = (ImageView) convertView.findViewById(R.id.hot_word_icon1);
                viewHolder.mIcon2 = (ImageView) convertView.findViewById(R.id.hot_word_icon2);
                viewHolder.mHotWord1 = (TextView) convertView.findViewById(R.id.hot_word1);
                viewHolder.mHotWord2 = (TextView) convertView.findViewById(R.id.hot_word2);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }

            if (position == 0) {
                viewHolder.mIcon1.setBackgroundResource(R.drawable.hot_word_search_hot_icon);
                viewHolder.mIcon2.setBackgroundResource(R.drawable.hot_word_search_hot_icon);
            } else if (position == 1) {
                viewHolder.mIcon1.setBackgroundResource(R.drawable.little_hot_word_search_hot_icon);
                viewHolder.mIcon2.setBackgroundResource(R.drawable.little_hot_word_search_hot_icon);
            } else if (position == 2) {
                viewHolder.mIcon1.setBackgroundResource(R.drawable.little_hot_word_search_hot_icon);
                viewHolder.mIcon2.setBackgroundResource(R.drawable.hot_word_search_next_batch);
            }

            String[] itemData = mDataList.get(position) ;
            viewHolder.mHotWord1.setText(itemData[0]); ;
            viewHolder.mHotWord2.setText(itemData[1]);

            final AnimationSet as = getAnim();

            int startOffset = 120 ;
            if (position == 1) {
                as.setStartOffset(startOffset);
            } else if (position == 2) {
                as.setStartOffset(startOffset * 2);
            }
            convertView.startAnimation(as);

            return convertView;
        }

        private AnimationSet getAnim() {

            int time = 600;

            AnimationSet as = new AnimationSet(mContext, null);
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
            as.setInterpolator(new EaseCubicInterpolator(0, 1.0f, 0f, 1.0f));
            as.setFillAfter(true);

            return as;
        }
    }

    private static class ViewHolder {
        public ImageView mIcon1 ;
        public ImageView mIcon2 ;
        public TextView mHotWord1 ;
        public TextView mHotWord2 ;
    }
}
