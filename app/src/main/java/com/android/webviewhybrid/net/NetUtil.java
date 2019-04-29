package com.android.webviewhybrid.net;

import android.content.Context;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.webkit.MimeTypeMap;


import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by wot_fengchengzhi on 2019/3/19.
 */

public class NetUtil {
    private static Gson gson = new Gson();

    //get请求
    public static void getMethod(String url, Callback callback) {
        OkHttpClient okHttpClient = OkHttpManger.getInstance();
        Request request = new Request.Builder()
                .url(url).get().build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(callback);
    }

    //post同步请求
    public static Response postMethodSync(String url, Map<String, String> params) throws IOException {
        OkHttpClient okHttpClient = OkHttpManger.getInstance();
        FormBody.Builder builder = new FormBody.Builder();
        if (params != null) {
            for (String key :
                    params.keySet()) {
                builder.add(key, TextUtils.isEmpty(params.get(key)) ? "" : params.get(key));
            }
        }
        RequestBody requestBody = builder.build();
        Request request = new Request.Builder()
                .url(url)
                .post(requestBody)
                .build();
        Call call = okHttpClient.newCall(request);
        return call.execute();
    }

    //post异步请求
    public static void postMethod(String url,String paramsJson, Callback callback) {
        Type type = new TypeToken<Map<String, String>>(){}.getType();
        Map<String, String> params = gson.fromJson(paramsJson,type);
        OkHttpClient okHttpClient = OkHttpManger.getInstance();
        FormBody.Builder builder = new FormBody.Builder();
        if (params != null) {
            for (String key :
                    params.keySet()) {
                builder.add(key, TextUtils.isEmpty(params.get(key)) ? "" : params.get(key));
            }
        }
        RequestBody requestBody = builder.build();
        Request request = new Request.Builder()
                .url(url)
                .post(requestBody)
                .build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(callback);
    }






    public static String encodeParams(String strJson) {
        String json = "";
        try {
            strJson = URLEncoder.encode(strJson, "UTF-8");
//            json = Base64.encodeBASE64(strJson);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            json = e.toString();
        }
        return json;
    }

    public static String decodeParams(String strJson) {
        String json = "";
        try {
//            strJson = Base64.decodeBASE64(strJson);
            json = URLDecoder.decode(strJson, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            json = e.toString();
        }
        return json;
    }

    public static String getMimeType(String url) {
        String type = null;
        String extension = MimeTypeMap.getFileExtensionFromUrl(url);
        if (extension != null) {
            MimeTypeMap mime = MimeTypeMap.getSingleton();
            type = mime.getMimeTypeFromExtension(extension);
        }
        return type;
    }

}
