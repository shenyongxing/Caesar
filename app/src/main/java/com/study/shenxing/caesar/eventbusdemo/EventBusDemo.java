package com.study.shenxing.caesar.eventbusdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.study.shenxing.caesar.R;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class EventBusDemo extends AppCompatActivity {
    private TextView mTextView;
    private Button mSendBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_bus_demo);

        mTextView = (TextView) findViewById(R.id.event_bus_text);
        mSendBtn = (Button) findViewById(R.id.send_message);
        mSendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EventBus.getDefault().post(new GreetingEvent("Hello boy. This is EventBus"));
            }
        });

        EventBus.getDefault().register(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void handleGreetingEvent(GreetingEvent event) {
        // 这个方法必须设置为public, 否则会报 super classes have no public methods with the @Subscribe annotation 错误
        String greetingInfo = event.getGreetingInfo();
        mTextView.setText(greetingInfo);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
