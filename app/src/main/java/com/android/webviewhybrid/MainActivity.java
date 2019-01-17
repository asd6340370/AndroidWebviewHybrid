package com.android.webviewhybrid;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.android.webviewhybrid.web.MWebview;

public class MainActivity extends AppCompatActivity {

private MWebview webView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        webView = findViewById(R.id.wv);
        webView.loadUrl("file:///android_asset/index.html");
    }
}
