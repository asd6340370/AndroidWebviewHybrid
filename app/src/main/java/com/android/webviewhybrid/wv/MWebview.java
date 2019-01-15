package com.android.webviewhybrid.wv;

import android.content.Context;
import android.util.AttributeSet;
import android.webkit.WebView;

public class MWebview extends WebView {

    private Context context;

    public MWebview(Context context) {
        super(context);
        this.context = context;
        init();
    }

    public MWebview(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MWebview(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public MWebview(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    private void init(){

    }


}
