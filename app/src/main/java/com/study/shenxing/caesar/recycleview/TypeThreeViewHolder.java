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

public class TypeThreeViewHolder extends AbstractTypeViewHolder<DataModelThree> {
    private ImageView mThumbnail;    // 左边色块
    private TextView mTitle;
    private TextView mDesc;
    private ImageView mContent;    // 左边色块


    public TypeThreeViewHolder(View itemView) {
        super(itemView);

        mTitle = (TextView) itemView.findViewById(R.id.tv_title);
        mDesc = (TextView) itemView.findViewById(R.id.tv_desc);
        mThumbnail = (ImageView) itemView.findViewById(R.id.iv_thumbnail);
        mContent = (ImageView) itemView.findViewById(R.id.iv_content);
        itemView.setBackgroundResource(android.R.color.holo_purple);

    }

    @Override
    public void bindData(DataModelThree model) {
        mThumbnail.setBackgroundResource(android.R.color.holo_blue_bright);
        mTitle.setText(model.mTitle);
        mDesc.setText(model.mDesc);
        mContent.setBackgroundResource(android.R.color.holo_orange_dark);
    }
}
