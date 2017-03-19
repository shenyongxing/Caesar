package com.study.shenxing.caesar.asynchttp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.study.shenxing.caesar.R;

import org.json.JSONArray;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class AsyncHttpActivity extends AppCompatActivity {
    public static final String TAG = "sh-http";
    private TextView mRequest;
    private TextView mResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_async_http);

        mRequest = (TextView) findViewById(R.id.http_test);
        mRequest.setOnClickListener(request);
        mRequest = (TextView) findViewById(R.id.http_result);
    }

    private View.OnClickListener request = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
//            requestByAsyncHttp();
            requestZhihu();
            Toast.makeText(AsyncHttpActivity.this, "you clicked it", Toast.LENGTH_SHORT).show();
        }
    };

    private void requestByAsyncHttp() {
        AsyncHttpClient client = new AsyncHttpClient();
        client.get("http://www.baidu.com", new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                if (mRequest != null) {
                    mRequest.setText(new String(responseBody));
                }
                Log.i(TAG, "onSuccess: " + new String(responseBody));
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Log.i(TAG, "onFailure: " + statusCode + ", " + headers.toString() + ", " + responseBody.toString());
            }

            @Override
            public void onRetry(int retryNo) {
                super.onRetry(retryNo);
                Log.i(TAG, "onRetry: ");
            }

            @Override
            public void onStart() {
                super.onStart();
                Log.i(TAG, "onStart: ");
            }
        });
    }

    private void requestZhihu() {
        AsyncHttpClientManager.get("", null, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                Log.i(TAG, "onSuccess: " + response.toString());
                Log.i(TAG, "limit: " + response.optInt("limit"));
                Log.i(TAG, "subscribed: " + response.optJSONArray("subscribed"));
                Log.i(TAG, "others: " + response.optJSONArray("others"));

                JSONArray otherArray = response.optJSONArray("others");
                int length = otherArray.length();
                for (int i = 0; i < length; i++) {
                    JSONObject jsonObject = otherArray.optJSONObject(i);
                    int color = jsonObject.optInt("color");
                    String thumbnail = jsonObject.optString("thumbnail");
                    String desc = jsonObject.optString("description");
                    int id = jsonObject.optInt("id");
                    String name = jsonObject.optString("name");

                    Log.i(TAG, "others item : " + color + ", " + desc + ", " + id + ", " + name);
                }
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                super.onSuccess(statusCode, headers, response);
                Log.i(TAG, "onSuccess: jsonarry:" + response.toString());
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                super.onSuccess(statusCode, headers, responseString);
                Log.i(TAG, "onSuccess: string: " + responseString);
            }
        });
    }
}