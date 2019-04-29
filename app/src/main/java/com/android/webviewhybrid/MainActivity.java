package com.android.webviewhybrid;

import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;

import com.android.webviewhybrid.web.MWebview;

import java.io.File;

public class MainActivity extends AppCompatActivity {

private MWebview webView;
private String sourcePath;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        webView = findViewById(R.id.wv);

        String path = Environment.getExternalStorageDirectory().getAbsolutePath();
        path = path + File.separator + "webH";

        File file = new File(path);
        if (!file.exists())file.mkdirs();

        File file1 = new File(path, "index.html");
        if (file1.exists()){
            sourcePath = "file://" + file1.getAbsolutePath();
        }else {
           sourcePath = "file:///android_asset/index.html";
        }
        webView.loadUrl(sourcePath);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
