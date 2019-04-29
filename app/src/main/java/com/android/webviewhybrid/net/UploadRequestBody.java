package com.android.webviewhybrid.net;


import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import okio.Buffer;
import okio.BufferedSink;
import okio.ForwardingSink;
import okio.Okio;

/**
 * Created by wot_fengchengzhi on 2019/3/22.
 */

public class UploadRequestBody extends RequestBody {
    private RequestBody requestBody;
    private UploadCallbackImpl listener;
    private BufferedSink bufferedSink;

    public UploadRequestBody(RequestBody requestBody, UploadCallbackImpl listener) {
        this.requestBody = requestBody;
        this.listener = listener;
    }

    @Override
    public MediaType contentType() {
        return requestBody.contentType();
    }

    @Override
    public long contentLength() throws IOException {
        return requestBody.contentLength();
    }


    @Override
    public void writeTo(final BufferedSink sink) throws IOException {
        if (bufferedSink == null) {
            bufferedSink = Okio.buffer(new ForwardingSink(sink) {
                long totalBytesRead = 0L;
                long contentLength = 0L;

                @Override
                public void write(Buffer source, long byteCount) throws IOException {
                    super.write(source, byteCount);
                    if (contentLength == 0)
                        contentLength = contentLength();
                    totalBytesRead += byteCount;

                    listener.loading(totalBytesRead, contentLength);

                }
            });
        }

        requestBody.writeTo(bufferedSink);
        bufferedSink.flush();
    }

    interface UploadCallbackImpl{
        void loading(long current, long total);
    }
}
