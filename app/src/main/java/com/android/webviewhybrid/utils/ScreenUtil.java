package com.android.webviewhybrid.utils;

import android.app.Activity;
import android.app.Application;
import android.util.DisplayMetrics;
import android.view.WindowManager;

import com.android.webviewhybrid.MApplication;

public class ScreenUtil {

    public static DisplayMetrics getDisplayMetrics(){
        DisplayMetrics displayMetrics = MApplication.getContext().getResources().getDisplayMetrics();
        return displayMetrics;
    }

    public static DisplayMetrics getDisplayMetrics(Activity activity){
        WindowManager manager = activity.getWindowManager();
        DisplayMetrics metrics =new DisplayMetrics();
        manager.getDefaultDisplay().getMetrics(metrics);
        return metrics;
    }
}
