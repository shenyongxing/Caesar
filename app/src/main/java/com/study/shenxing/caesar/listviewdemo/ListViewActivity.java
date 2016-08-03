package com.study.shenxing.caesar.listviewdemo;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.ListView;

import com.study.shenxing.caesar.R;

import java.util.ArrayList;
import java.util.List;

/**
 * 学习ListView的OnScrollListener
 */
public class ListViewActivity extends Activity {
    private ListView mListView ;
    private ImageView mEmptyView ;


    private boolean mIsScrollDown;
    private int mLastVisibleItem;
    private int mFirstItemTop;
    private ChattingListViewAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_view);

        mListView = (ListView) findViewById(R.id.listView);
        mEmptyView = (ImageView) findViewById(R.id.emptyView);

        // 为ListView设置headView必须要在setAdapter之前,否则会报相关的异常.
        View headView = new View(this) ;
        headView.setLayoutParams(new AbsListView.LayoutParams(AbsListView.LayoutParams.MATCH_PARENT, 100));
        headView.setBackgroundColor(0xff00ff00);
        mListView.addHeaderView(headView);
        // 为ListView设置footView则不必需要一定在setAdapter之前.
        mListView.addFooterView(headView);

        /*mListView.setAdapter(new BaseAdapter() {
            @Override
            public int getCount() {
                return 100;
            }

            @Override
            public Object getItem(int position) {
                return null;
            }

            @Override
            public long getItemId(int position) {
                return position;
            }

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                TextView tv = new TextView(ListViewActivity.this);
                tv.setText("hello world " + position);
                return tv;
            }
        });*/

        adapter = new ChattingListViewAdapter(this, mListView, createTestData()) ;
        mListView.setAdapter(adapter);


        mListView.setEmptyView(mEmptyView);

        mListView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                switch (scrollState) {
                    case AbsListView.OnScrollListener.SCROLL_STATE_IDLE:
                        Log.i("shenxing", "stop scroll");
                        break;
                    case AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL:
                        Log.i("shenxing", "start scroll");
                        break;
                    case AbsListView.OnScrollListener.SCROLL_STATE_FLING:
                        Log.i("shenxing", "fling");
                        break;
                }
            }

            // onScrollStateChanged方法只会被回调2次或者3次.需要注意.

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                if (firstVisibleItem + visibleItemCount == totalItemCount && totalItemCount > 0) {
                    // 此时最后一项已经可见了
                    Log.i("shenxing", "滑到最后一行了");
                }
                Log.i("shenxing", "onScroll, 可视区第一个item的position : " + mListView.getFirstVisiblePosition() + "可视区最后一个 : " + mListView.getLastVisiblePosition());
                // 可以用来计算可视区间有多少个Item.
                int a = mListView.getLastVisiblePosition() - mListView.getFirstVisiblePosition() + 1;   // 则a的值与visibleItemCount相等
                Log.i("shenxing", "可见的item的有" + visibleItemCount + "个" + ", a : " + a);


                View childView = view.getChildAt(0);
                if (childView == null) {
                    return;
                }
                int top = childView.getTop();

                if (firstVisibleItem < mLastVisibleItem) {
                    mIsScrollDown = false;
                } else {
                    mIsScrollDown = firstVisibleItem > mLastVisibleItem || top < mFirstItemTop;
                }

                /**
                 * 仅仅用 mIsScrollDown = firstVisibleItem > mLastVisibleItem || top < mFirstItemTop; 判断是否向下滑动不准确
                 * 根据打印日志看下滑时mIsScrollDown居然也为true
                 * 所以采用以上的代码
                 */
                adapter.setIsScrollDown(mIsScrollDown);
                mLastVisibleItem = firstVisibleItem;
                mFirstItemTop = top;
            }

            // onScroll方法会一直被回调
        });

        mListView.post(new Runnable() {
            @Override
            public void run() {
                // 该值不能再设置了adpter之后马上调用, 和获取空间高度类似.
                // post到线程后可以正常的获取的到最后一个Item的position.
                Log.i("shenxing", "****************** " + mListView.getLastVisiblePosition());
            }
        });


        // api23才有该接口,注意使用场景
        /*mListView.setOnScrollChangeListener(new View.OnScrollChangeListener() {
            @Override
            public void onScrollChange(View v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                Log.i("shenxing", "now x, y : " + scrollX + ", " + scrollY + ", previous x, y : " + oldScrollX + ", " + oldScrollY) ;
            }
        });*/


    }


    private List<ChatItemListViewBean> createTestData() {
        List<ChatItemListViewBean> data = new ArrayList<ChatItemListViewBean>() ;
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.setting_nextlauncher) ;
        for (int i = 0; i < 50; i++) {
            ChatItemListViewBean bean = new ChatItemListViewBean() ;
            bean.setmIcon(bitmap);
            bean.setmMessage("hello world " + i);
            bean.setmType(i % 2 == 0 ? 0 : 1);
            data.add(bean);
        }
        return data ;
    }
}
