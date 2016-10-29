package com.study.shenxing.caesar.recycleview;

import android.content.Context;
import android.support.annotation.IntegerRes;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.study.shenxing.caesar.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author shenxing
 * @description
 * @date 2016/10/29
 */

public class NewVersionDataAdapter extends RecyclerView.Adapter {

    public static final int TYPE_ONE = 1;

    public static final int TYPE_TWO = 2;

    public static final int TYPE_THREE = 3;

    private LayoutInflater mLayoutInflater;
    private List<DataModelOne> mList1;
    private List<DataModelTwo> mList2;
    private List<DataModelThree> mList3;
    private List<Integer> mTypes = new ArrayList<Integer>();
    private Map<Integer, Integer> mPositions = new HashMap<Integer, Integer>();

    public NewVersionDataAdapter(Context context) {
        mLayoutInflater = LayoutInflater.from(context);
    }

    public void addList(List<DataModelOne> list1, List<DataModelTwo> list2, List<DataModelThree> list3) {
        addListByType(TYPE_ONE, list1);
        addListByType(TYPE_TWO, list2);
        addListByType(TYPE_THREE, list3);

        mList1 = list1;
        mList2 = list2;
        mList3 = list3;
    }

    private void addListByType(int type, List list) {
        mPositions.put(type, mTypes.size());    // 注意该数据结构的使用，记录的type改变的起始值,方便将position映射到相应的list上
        for (int i = 0; i < list.size(); i++) {
            mTypes.add(type);
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case TYPE_ONE:
                return new TypeOneViewHolder(mLayoutInflater.inflate(R.layout.recycler_type_one, parent, false));
            case TYPE_TWO:
                return new TypeTwoViewHolder(mLayoutInflater.inflate(R.layout.recycler_type_two, parent, false));
            case TYPE_THREE:
                return new TypeThreeViewHolder(mLayoutInflater.inflate(R.layout.recycler_type_three, parent, false));
            default:
                break;
        }

        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        int viewType = getItemViewType(position);
        int realPosition = position - mPositions.get(viewType);
//        AbstractTypeViewHolder abstractHolder = (AbstractTypeViewHolder) holder;
//        abstractHolder.bindData(mTypes.get(position));
        switch (viewType) {
            case TYPE_ONE:
                ((TypeOneViewHolder) holder).bindData(mList1.get(realPosition));
                break;
            case TYPE_TWO:
                ((TypeTwoViewHolder) holder).bindData(mList2.get(realPosition));
                break;
            case TYPE_THREE:
                ((TypeThreeViewHolder) holder).bindData(mList3.get(realPosition));
                break;
            default:
                break;
        }
    }

    @Override
    public int getItemCount() {
        return mTypes.size();
    }

    @Override
    public int getItemViewType(int position) {
        return mTypes.get(position);
    }
}
