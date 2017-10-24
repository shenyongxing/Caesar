package com.study.shenxing.caesar.volleyokhttpfastjson;

import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonRequest;

import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * Copyright © 2017年 深圳中业兴融互联网金融服务有限公司. All rights reserved.
 *
 * @Author shenxing
 * @Date 2017/10/17
 * @Email shen.xing@zyxr.com
 * @Description
 */
public class JsObjectRequest<T> extends JsonRequest<T> {

    public static final String TAG = "shenxing";
     private Class<T> mClazz;
     /**
     * Creates a new request.
     * @param method the HTTP method to use
     * @param url URL to fetch the JSON from
     * @param jsonRequest A {@link JSONObject} to post with the request. Null is allowed and
     *   indicates no parameters will be posted along with request.
     * @param listener Listener to receive the JSON response
     * @param errorListener Error listener, or null to ignore errors.
     */
    public JsObjectRequest(int method, String url, Class<T> clazz, JSONObject jsonRequest,
                             Response.Listener<T> listener, Response.ErrorListener errorListener) {
        super(method, url, (jsonRequest == null) ? null : jsonRequest.toString(), listener,
                errorListener);
        this.mClazz = clazz;
    }

    /**
     * Constructor which defaults to <code>GET</code> if <code>jsonRequest</code> is
     * <code>null</code>, <code>POST</code> otherwise.
     *
     * @see #JsObjectRequest(int, String, JSONObject, Response.Listener, Response.ErrorListener)
     */
    public JsObjectRequest(String url, Class<T> clazz, JSONObject jsonRequest, Response.Listener<T> listener,
                             Response.ErrorListener errorListener) {
        this(jsonRequest == null ? Method.GET : Method.POST, url, clazz, jsonRequest,
                listener, errorListener);
        this.mClazz = clazz;
    }

    @Override
    protected Response<T> parseNetworkResponse(NetworkResponse response) {
        try {
            String jsonString = new String(response.data,
                    HttpHeaderParser.parseCharset(response.headers, PROTOCOL_CHARSET));

            Class <T> entityClass = (Class <T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
//            ParameterizedType parameterizedType = (ParameterizedType) (JsObjectRequest.class.getGenericSuperclass());
//            Log.i(TAG, "parseNetworkResponse: " + parameterizedType);
//            Type[] types = parameterizedType.getActualTypeArguments();
//            for (Type t : types) {
//                Log.i(TAG, "type类型：" + t);
//            }



//            Log.i(TAG, "parseNetworkResponse: " + entityClass.getName() + ", " + entityClass);

            T t = JSON.parseObject(jsonString, this.mClazz);
            Log.i(TAG, "parseNetworkResponse: " + (t instanceof User));
            return Response.success(t,
                    HttpHeaderParser.parseCacheHeaders(response));
        } catch (UnsupportedEncodingException e) {
            return Response.error(new ParseError(e));
        }
    }
}
