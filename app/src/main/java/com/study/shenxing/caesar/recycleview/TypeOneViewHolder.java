package com.study.shenxing.caesar.recycleview;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.study.shenxing.caesar.R;

/**
 * @author shenxing
 * @description
 * @date 2016/10/29
 */

public class TypeOneViewHolder extends AbstractTypeViewHolder<DataModelOne> {

    private TextView mTitle;
    private TextView mDesc;

    public TypeOneViewHolder(View itemView) {
        super(itemView);

        mTitle = (TextView) itemView.findViewById(R.id.tv_title);
        mDesc = (TextView) itemView.findViewById(R.id.tv_desc);
        itemView.setBackgroundResource(android.R.color.darker_gray);
    }

    @Override
    public void bindData(DataModelOne model) {
        mTitle.setText(model.mTitle);
        mDesc.setText(model.mDesc);
    }
}
