package com.android.webviewhybrid.net;
import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.Buffer;
import okio.BufferedSource;
import okio.ForwardingSource;
import okio.Okio;

/**
 * Created by wot_fengchengzhi on 2019/3/21.
 */

public class DownloadResponseBody extends ResponseBody {
    private Response originalResponse;
    private DownloadCallbackImpl listener;

    public DownloadResponseBody(Response originalResponse, DownloadCallbackImpl listener) {
        this.originalResponse = originalResponse;
        this.listener = listener;
    }

    @Override
    public MediaType contentType() {
        return originalResponse.body().contentType();
    }

    @Override
    public long contentLength() {
        return originalResponse.body().contentLength();
    }

    @Override
    public BufferedSource source() {
        return Okio.buffer(new ForwardingSource(originalResponse.body().source()) {
            long totalBytesRead = 0L;
            long contentLength = 0L;

            @Override
            public long read(Buffer sink, long byteCount) throws IOException {
                long bytesRead = super.read(sink, byteCount);
                if (contentLength == 0)
                    contentLength = contentLength();
                totalBytesRead += bytesRead != -1 ? bytesRead : 0;


                listener.loading(totalBytesRead, contentLength);
                return bytesRead;
            }
        });
    }

    public interface DownloadCallbackImpl{
        void loading(long current, long total);
    }
}
