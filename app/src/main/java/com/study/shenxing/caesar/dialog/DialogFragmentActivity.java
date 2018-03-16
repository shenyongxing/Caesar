package com.study.shenxing.caesar.dialog;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.study.shenxing.caesar.R;

public class DialogFragmentActivity extends AppCompatActivity {

    private Button mBtnTest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dialog_fragment);

        mBtnTest = (Button) findViewById(R.id.btn_test);
        mBtnTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                TestDialogFragment dialog = new TestDialogFragment();
                dialog.show(getFragmentManager(), "testDialog");
            }
        });
    }
}
