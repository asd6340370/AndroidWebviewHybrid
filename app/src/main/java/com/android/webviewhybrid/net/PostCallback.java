package com.android.webviewhybrid.net;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class PostCallback implements Callback {

    public PostCallback(String onSuccess, String onFailure) {

    }

    @Override
    public void onFailure(Call call, IOException e) {

    }


    @Override
    public void onResponse(Call call, Response response) throws IOException {

    }
}
