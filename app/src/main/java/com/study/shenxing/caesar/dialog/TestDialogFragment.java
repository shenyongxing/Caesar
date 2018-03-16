package com.study.shenxing.caesar.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import com.study.shenxing.caesar.R;

/**
 * Copyright © 2017年 深圳融投天下互联网金融服务有限公司. All rights reserved.
 *
 * @Author shenxing
 * @Date 2018/3/3
 * @Email shen.xing@zyxr.com
 * @Description 阿里巴巴推荐使用DialogFragment显示对话框
 */
public class TestDialogFragment extends DialogFragment {

//    @Nullable
//    @Override
//    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
////        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
//        View view = inflater.inflate(R.layout.test_dialog_fragment_layout, container);
//
//        return view;
//    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = LayoutInflater.from(getActivity());
        View contentView = inflater.inflate(R.layout.test_dialog_fragment_layout, null);
        builder.setView(contentView);
        return builder.create();
    }
}
