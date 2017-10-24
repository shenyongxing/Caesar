package com.study.shenxing.caesar.volleyokhttpfastjson;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.alibaba.fastjson.JSON;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.Volley;
import com.study.shenxing.caesar.R;

import org.json.JSONObject;

/**
 *
 * @Author shenxing
 * @Date 2017/10/17
 * @Email shen.xing@zyxr.com
 * @Description volley + okhttp + fastjson测试。network包里有另外的网络请求测试代码可以参考学习
 */

public class VolleyOkhttpActivity extends AppCompatActivity {
    public static final String TAG = "shenxing";
    public static final String URL = "http://www.mocky.io/v2/59e5b681110000e10eec69b1";
    private Button mLoad;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_volley_okhttp);

        final RequestQueue mRequestQueue = Volley.newRequestQueue(this, new OkHttp3Stack());


        mLoad = (Button) findViewById(R.id.load);
        mLoad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(URL, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        // 建立基类，将原始jsonobject解析成object对象

                        Log.i(TAG, "onResponse: " + response.toString());


                        User user = JSON.parseObject(response.toString(), User.class);
                        Log.i(TAG, "user : " + user);
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                    }
                });


                JsObjectRequest<User> jsObjectRequest = new JsObjectRequest<User>(URL, User.class, null, new Response.Listener<User>() {
                    @Override
                    public void onResponse(User user) {
                        Log.i(TAG, "=========>  onResponse: " + user);
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                        Log.i(TAG, "onErrorResponse: " + error.toString());
                    }
                });

                mRequestQueue.add(jsObjectRequest);
            }
        });




    }
}
