package com.study.shenxing.caesar.textinputlayout;

import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.study.shenxing.caesar.R;

public class TextInputLayoutActivity extends AppCompatActivity {

    public static final String TAG = "shsh";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text_input_layout);

        test();
    }

    private void test() {
        final TextInputLayout inputLayout = (TextInputLayout) findViewById(R.id.txt_input_layout);
        final LinearLayout ll = (LinearLayout) findViewById(R.id.root_layout);
//        inputLayout.setHintEnabled(false);
//        inputLayout.setActivated(false);
        final EditText editText = (EditText) findViewById(R.id.et_input);
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Log.i(TAG, "onTextChanged: " + s);
                if (null != s && s.length() > 1) {
//                    inputLayout.setHintEnabled(false);
                    editText.requestFocus();
                } else {
//                    inputLayout.setHintEnabled(true);
//                    inputLayout.setHint("input test");
                    ll.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        editText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                editText.setFocusable(true);
//                inputLayout.setActivated(true);
//                Log.i(TAG, "onClick: ");
            }
        });


        editText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                Log.i(TAG, "onFocusChange: " + hasFocus);
            }
        });


        ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG, "onClick: ll");
//                editText.requestFocus();
            }
        });
    }
}
