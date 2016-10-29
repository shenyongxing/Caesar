package com.study.shenxing.caesar.recycleview;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.study.shenxing.caesar.R;

/**
 * @author shenxing
 * @description
 * @date 2016/10/29
 */

public class TypeTwoViewHolder extends AbstractTypeViewHolder<DataModelTwo> {
    private ImageView mThumbnail;    // 左边色块
    private TextView mTitle;
    private TextView mDesc;

    public TypeTwoViewHolder(View itemView) {
        super(itemView);

        mTitle = (TextView) itemView.findViewById(R.id.tv_title);
        mDesc = (TextView) itemView.findViewById(R.id.tv_desc);
        mThumbnail = (ImageView) itemView.findViewById(R.id.iv_thumbnail);

        itemView.setBackgroundResource(android.R.color.black);

    }

    @Override
    public void bindData(DataModelTwo model) {
        mThumbnail.setBackgroundResource(android.R.color.holo_blue_bright);
        mTitle.setText(model.mTitle);
        mDesc.setText(model.mDesc);
    }
}
