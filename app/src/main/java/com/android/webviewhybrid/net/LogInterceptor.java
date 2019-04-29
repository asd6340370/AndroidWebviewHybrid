package com.android.webviewhybrid.net;

import com.android.webviewhybrid.utils.LogUtil;
import java.io.IOException;
import java.util.Locale;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * Created by wot_fengchengzhi on 2019/3/28.
 */

public class LogInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        LogUtil.d(String.format("sending request %s on %s%n%s", request.url(), chain.connection(), request.headers()));
        long t1 = System.nanoTime();
        Response response = chain.proceed(chain.request());
        long t2 = System.nanoTime();
        LogUtil.d(String.format(Locale.getDefault(), "received response for %s in %.1fms%n%s",
                response.request().url(), (t2 - t1) / 1e6d, response.headers()));
        MediaType mediaType = response.body().contentType();
        String content = response.body().string();
        LogUtil.d(String.format("response body : %s", content));
        return response.newBuilder().body(ResponseBody.create(mediaType, content)).build();
    }
}
