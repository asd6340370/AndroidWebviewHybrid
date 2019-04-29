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

    private void init() {
        webSettings = getSettings();
        webSettings.setJavaScriptEnabled(true);//可执行js代码
        webSettings.setDefaultTextEncodingName("UTF-8");//设置html默认编码格式
        // 是否支持viewport属性，默认值 false
        // 页面通过`<meta name="viewport" ... />`自适应手机屏幕
        // 当值为true且viewport标签不存在或未指定宽度时使用 wide viewport mode

//        webSettings.setUseWideViewPort(true);
        // 是否使用overview mode加载页面，默认值 false
        // 当页面宽度大于WebView宽度时，缩小使页面宽度等于WebView宽度
        webSettings.setLoadWithOverviewMode(true);
        // 设置初始缩放百分比
        // 0表示依赖于setUseWideViewPort和setLoadWithOverviewMode
        // 100表示不缩放
        setInitialScale(100);

        if (isNetworkConnected(context)) {
            // 根据cache-control决定是否从网络上取数据
            webSettings.setCacheMode(WebSettings.LOAD_DEFAULT);
        } else {
            // 没网，离线加载，优先加载缓存(即使已经过期)
            webSettings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        }


        this.setWebChromeClient(new MWebChromeClient());
        this.setWebViewClient(new MWebviewClient());
        this.addJavascriptInterface(new MWebviewJsInterface(context, this), "nativePlugin");

    }

    private boolean isNetworkConnected(Context context) {
        return true;
    }


}
