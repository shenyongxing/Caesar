package com.study.shenxing.caesar.swiperefresh;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.study.shenxing.caesar.R;

import java.util.ArrayList;
import java.util.List;

public class SwipeRefreshActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_swipe_refresh);

        mRecyclerView = (RecyclerView) findViewById(R.id.recycle_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        List<String> mData = getTestData();
        MyRecyclerAdapter adapter = new MyRecyclerAdapter(this, mData);
        mRecyclerView.setAdapter(adapter);
    }


    private List<String> getTestData() {
        List<String> tmp = new ArrayList<String>();
        for (int i = 0; i < 10; i++) {
            tmp.add("String : " + i);
        }
        return tmp;
    }


    public class MyRecyclerAdapter extends RecyclerView.Adapter {

        private List<String> mDatas;
        private Context mContext;

        public MyRecyclerAdapter(Context context, List<String> datas) {
            mDatas = datas;
            mContext = context;
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(mContext).inflate(R.layout.swipe_recycler_item_demo, parent, false);
            RecyclerView.ViewHolder myViewHolder = new MyViewHolder(view);
            return myViewHolder;
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            if (holder instanceof MyViewHolder) {
                MyViewHolder myViewHolder = (MyViewHolder) holder;
                myViewHolder.mTextView.setText("position :" + position);
            }
        }

        @Override
        public int getItemCount() {
            return mDatas.size();
        }
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView mTextView;

        public MyViewHolder(View itemView) {
            super(itemView);
            mTextView = (TextView) itemView.findViewById(R.id.tv_content);
        }
    }
}
