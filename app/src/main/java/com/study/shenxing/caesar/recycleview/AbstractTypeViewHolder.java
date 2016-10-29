package com.study.shenxing.caesar.recycleview;

import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * @author shenxing
 * @description
 * @date 2016/10/29
 */

public abstract class AbstractTypeViewHolder<T> extends RecyclerView.ViewHolder {
    public AbstractTypeViewHolder(View itemView) {
        super(itemView);
    }


    public abstract void bindData(T model);
}
