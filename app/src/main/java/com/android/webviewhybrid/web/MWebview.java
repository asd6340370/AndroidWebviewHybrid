package com.android.webviewhybrid.web;

import android.content.Context;
import android.util.AttributeSet;
import android.webkit.WebSettings;
import android.webkit.WebView;

public class MWebview extends WebView {

    private Context context;
    private WebSettings webSettings;


    public MWebview(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        init();
    }

    public MWebview(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public MWebview(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    private void init(){
        webSettings = getSettings();
        webSettings.setJavaScriptEnabled(true);//可执行js代码
        webSettings.setDefaultTextEncodingName("UTF-8");//设置html默认编码格式


        this.setWebChromeClient(new MWebChromeClient());
        this.setWebViewClient(new MWebviewClient());
        this.addJavascriptInterface(new MWebviewJsInterface(context, this),"nativePlugin");

    }


}
