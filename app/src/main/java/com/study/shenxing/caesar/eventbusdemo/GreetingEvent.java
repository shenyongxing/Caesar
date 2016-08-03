package com.study.shenxing.caesar.eventbusdemo;

/**
 * Created by shenxing on 16/8/3.
 * Greeting event. say hello and so on
 */
public class GreetingEvent {
    private String mGreetingInfo;

    public GreetingEvent(String greetingInfo) {
        mGreetingInfo = greetingInfo;
    }

    public String getGreetingInfo() {
        return mGreetingInfo;
    }
}
