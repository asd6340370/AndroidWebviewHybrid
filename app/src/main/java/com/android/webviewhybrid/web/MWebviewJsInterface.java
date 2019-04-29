package com.android.webviewhybrid.web;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;

import com.android.webviewhybrid.net.NetUtil;
import com.android.webviewhybrid.utils.FileUtil;
import com.android.webviewhybrid.utils.LogUtil;

import java.io.IOException;
import java.lang.ref.WeakReference;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class MWebviewJsInterface {
    private Context context;
    private MWebview mWebview;
    private WeakReference<MWebview> weakReference;
    private Handler handler = new Handler(Looper.getMainLooper());

    public MWebviewJsInterface(Context context, MWebview mWebview){
        this.context = context;
        this.mWebview = mWebview;
        weakReference = new WeakReference<>(mWebview);
    }

    @JavascriptInterface
    public String showMessage(){

        return "";
    }

    @JavascriptInterface
    public void jsGet(String url, final String onSuccess, final String onFailure){

        NetUtil.getMethod(url, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                String err = e.toString();
                jsCallback(onFailure, err);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String result = response.body().string();
                jsCallback(onSuccess, result);
            }
        });
    }

    @JavascriptInterface
    public void jsPost(String url, String paramsJson, final String onSuccess, final String onFailure){
        NetUtil.postMethod(url, paramsJson, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                String err = e.toString();
                jsCallback(onFailure, err);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String result = response.body().string();
                jsCallback(onSuccess, result);
            }
        });
    }

    public void jsCallback(final String methodName,final String msg){
        if (weakReference.get() == null){
            LogUtil.d("webview 引用null");
            return;
        }
        handler.post(new Runnable() {
            @Override
            public void run() {
                String callback = "javascript:cb['" + methodName + "']('"+ msg +"')";
                weakReference.get().loadUrl(callback);
                weakReference.get().loadUrl("javascript:delete cb['" + methodName + "']");
            }
        });
    }

}
