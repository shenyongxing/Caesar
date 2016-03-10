package com.study.shenxing.caesar.others;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.ScrollView;

import com.study.shenxing.caesar.R;
import com.study.shenxing.caesar.common.CommonTestAdapter;
import com.study.shenxing.caesar.common.DataGenerator;

/**
 * ScrollView和ListView滑动冲突问题实践
 */
public class ScrollAndListViewActivity extends Activity {
    private ScrollView mScrollView ;
    private ListView mListView ;
    private CommonTestAdapter<String> mCommonAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scroll_and_list_view);

        mScrollView = (ScrollView) findViewById(R.id.scroll_view);
        mListView = (ListView) findViewById(R.id.list_view);
        mCommonAdapter = new CommonTestAdapter<String>(this, DataGenerator.getInstance().getListDatas());
        mListView.setAdapter(mCommonAdapter);
//        mListView.setFocusable(false);
//        mScrollView.smoothScrollTo(0, 0);
    }
}

// 结论
// 1. 如果按常规的做法，scrollView中嵌套listview，那么无论scrollview以及listView的宽高是否设置match_parent，listView都只会显示一条数据
//

