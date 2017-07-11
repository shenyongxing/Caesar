package com.study.shenxing.caesar.recycleview;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.study.shenxing.caesar.R;
import com.study.shenxing.caesar.common.DataGenerator;

import java.util.List;

public class RecyclerViewActivity extends Activity {
    private RecyclerView mRecyclerView ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycle_view);

        mRecyclerView = (RecyclerView) findViewById(R.id.recycle_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
//        mRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL));
        mRecyclerView.setAdapter(new RecyclerAdapter(this, DataGenerator.getInstance().getListDatas()));

    }


    private class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.MyViewHolder> {
        private List<String> mDatas ;
        private Context mContext ;

        public RecyclerAdapter(Context context, List<String> list) {
            mContext = context ;
            mDatas = list ;
        }


        @Override
        public void onBindViewHolder(RecyclerAdapter.MyViewHolder holder, int position) {
            holder.mTextView.setText(mDatas.get(position));
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
}
