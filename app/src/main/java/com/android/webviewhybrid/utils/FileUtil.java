package com.android.webviewhybrid.utils;

import android.content.Context;
import android.os.Environment;

import java.io.IOException;

public class FileUtil {

    public static boolean isSdCardEnable(){
        return Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState());
    }

    public static String getSDCardPath() throws IOException {
        if (!isSdCardEnable())
            throw new IOException("SDCard is not exist");
        return Environment.getExternalStorageDirectory().getAbsolutePath();
    }
}
