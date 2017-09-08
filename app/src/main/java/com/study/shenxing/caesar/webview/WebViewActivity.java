package com.study.shenxing.caesar.webview;

import android.app.Activity;
import android.os.Bundle;
import android.webkit.WebView;

import com.study.shenxing.caesar.R;

public class WebViewActivity extends Activity {

    private WebView mWebView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);

        mWebView = (WebView) findViewById(R.id.web_view);
    }
}
