package com.study.shenxing.caesar.recycleview;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.study.shenxing.caesar.R;
import com.study.shenxing.caesar.common.DataGenerator;
import com.study.shenxing.caesar.swiperefresh.SwipeRefreshActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class RecyclerViewActivity3 extends Activity {
    public static final String TAG = "sh_recycler_count";
    private RecyclerView mRecyclerView ;
    private LinearLayoutManager mLayoutManager;
    private ScheduledExecutorService mScheduleExecutorService;
    private List<CountTimeItemBean> mDatas = new ArrayList<CountTimeItemBean>();
    private Handler mHanlder = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycle_view);

        mRecyclerView = (RecyclerView) findViewById(R.id.recycle_view);
        mRecyclerView.addOnScrollListener(mOnScrollListener);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
//        mRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL));

        Random random = new Random();
        for (int i = 0; i < 100; i++) {
            CountTimeItemBean bean = new CountTimeItemBean();
            bean.mDay = random.nextInt(3);
            bean.mHour = random.nextInt(24);
            bean.mMin = random.nextInt(60);
            bean.mSec = random.nextInt(60);
            mDatas.add(bean);
        }

        mRecyclerView.setAdapter(new RecyclerAdapter(this, mDatas));

//        mScheduleExecutorService = Executors.newScheduledThreadPool(4);
//        mScheduleExecutorService.scheduleAtFixedRate(mCommand, 10, 1000, TimeUnit.MILLISECONDS);
        mHanlder.post(mCommand);
    }


    private class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.MyViewHolder> {
//        private List<String> mDatas ;
        private Context mContext ;

        public RecyclerAdapter(Context context, List<CountTimeItemBean> list) {
            mContext = context ;
            mDatas = list ;
        }

        @Override
        public void onBindViewHolder(RecyclerAdapter.MyViewHolder holder, int position) {
            CountTimeItemBean bean = mDatas.get(position);
            String text = bean.mDay + "天" + bean.mMin + "分" + bean.mSec + "秒";
            holder.mTextView.setText(text);
        }

        @Override
        public RecyclerAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(mContext) ;
            View itemView = inflater.inflate(R.layout.recycler_item_view, null) ;
            MyViewHolder viewHolder = new MyViewHolder(itemView) ;
            return viewHolder;
        }

        @Override
        public int getItemCount() {
            return mDatas.size();
        }

        public class MyViewHolder extends RecyclerView.ViewHolder {
            public TextView mTextView ;

            public MyViewHolder(View itemView) {
                super(itemView);

                mTextView = (TextView) itemView.findViewById(R.id.test_view);
            }
        }
    }


    private Runnable mCommand  = new Runnable() {
        @Override
        public void run() {
            if (mIsIdle) {
                int firstVisibleItemIndex = mLayoutManager.findFirstVisibleItemPosition();
                int lastVisibleItemIndex = mLayoutManager.findLastVisibleItemPosition();

                // 对数据操作
                for (int i = 0; i < mDatas.size(); i++) {
                    CountTimeItemBean bean = mDatas.get(i);
                    bean.mSec -= 1;
                    if (bean.mSec < 0) {
                        bean.mSec = 59;
                    }

                    // 手动更新数据
                    if (i >= firstVisibleItemIndex && i <= lastVisibleItemIndex) {
                        RecyclerAdapter.MyViewHolder holder = (RecyclerAdapter.MyViewHolder) mRecyclerView.findViewHolderForAdapterPosition(i);
                        String text = bean.mDay + "天" + bean.mMin + "分" + bean.mSec + "秒";
                        holder.mTextView.setText(text);
                    }
                }
                Log.i(TAG, "recycler is idle " + firstVisibleItemIndex + ", " + lastVisibleItemIndex);
            } else {
                Log.i(TAG, "recycler view is scrolled");
            }

            mHanlder.postDelayed(this, 1000);
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mHanlder.removeCallbacks(mCommand);
    }

    private boolean mIsIdle = true;
    private RecyclerView.OnScrollListener mOnScrollListener = new RecyclerView.OnScrollListener() {
        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);


            switch (newState) {
                case RecyclerView.SCROLL_STATE_IDLE:
                    mIsIdle = true;
                    break;
                case RecyclerView.SCROLL_STATE_DRAGGING:
                    mIsIdle = false;
                    break;
                case RecyclerView.SCROLL_STATE_SETTLING:
                    mIsIdle = false;
                    break;
            }
        }

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
        }
    };

}
