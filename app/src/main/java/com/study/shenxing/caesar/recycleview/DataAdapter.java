package com.study.shenxing.caesar.recycleview;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.study.shenxing.caesar.R;

import java.util.ArrayList;
import java.util.List;

/**
 * @author shenxing
 * @description
 * @date 2016/10/29
 */

public class DataAdapter extends RecyclerView.Adapter {
    private LayoutInflater mLayoutInflater;
    private List<DataModel> mList = new ArrayList<DataModel>();

    public DataAdapter(Context context) {
        mLayoutInflater = LayoutInflater.from(context);
    }

    public void addList(List<DataModel> list) {
        mList.addAll(list);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case DataModel.TYPE_ONE:
                return new TypeOneViewHolder(mLayoutInflater.inflate(R.layout.recycler_type_one, parent, false));
            case DataModel.TYPE_TWO:
                return new TypeTwoViewHolder(mLayoutInflater.inflate(R.layout.recycler_type_two, parent, false));
            case DataModel.TYPE_THREE:
                return new TypeThreeViewHolder(mLayoutInflater.inflate(R.layout.recycler_type_three, parent, false));
            default:
                break;
        }

        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        AbstractTypeViewHolder abstractHolder = (AbstractTypeViewHolder) holder;
        abstractHolder.bindData(mList.get(position));
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    @Override
    public int getItemViewType(int position) {
        return mList.get(position).mType;
    }
}
