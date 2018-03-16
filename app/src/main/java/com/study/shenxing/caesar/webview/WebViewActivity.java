package com.study.shenxing.caesar.webview;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;

import com.study.shenxing.caesar.R;

public class WebViewActivity extends Activity {

    private WebView mWebView;
    private Button mBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);

        mWebView = (WebView) findViewById(R.id.web_view);
        mBtn = (Button) findViewById(R.id.btn);

        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                Log.i("shenxing", "shouldOverrideUrlLoading: " + url);
                if (url.contains("http:www.baidu.com")) {
                    view.loadUrl("http://www.baidu.com");
                }

                return true;
            }
        });

        test();
    }

    private void test() {
        mBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mWebView.loadUrl("http:www.baidu.com");
            }
        });
    }
}
