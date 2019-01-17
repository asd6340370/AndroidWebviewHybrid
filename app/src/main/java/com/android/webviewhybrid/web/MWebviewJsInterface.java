package com.android.webviewhybrid.web;

import android.content.Context;
import android.webkit.JavascriptInterface;

import com.android.webviewhybrid.utils.FileUtil;

public class MWebviewJsInterface {
    private Context context;
    private MWebview mWebview;

    public MWebviewJsInterface(Context context, MWebview mWebview){
        this.context = context;
        this.mWebview = mWebview;
    }

    @JavascriptInterface
    public String showMessage(){

        return FileUtil.getSDCardPath(context);
    }

}
