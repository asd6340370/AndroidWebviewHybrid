package com.android.webviewhybrid.web;

public class MWebJsUtil {
    public static String getJsCallBack(String method, String ...param){
        StringBuilder builder = new StringBuilder("javascript:");
        builder.append(method);
        builder.append("(");

        return builder.toString();
    }
}
