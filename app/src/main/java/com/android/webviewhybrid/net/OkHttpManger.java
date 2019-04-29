package com.android.webviewhybrid.net;

import android.content.Context;


import com.android.webviewhybrid.MApplication;

import java.io.IOException;
import java.io.InputStream;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.util.Collections;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManagerFactory;

import okhttp3.ConnectionSpec;
import okhttp3.OkHttpClient;

/**
 * Created by wot_fengchengzhi on 2019/3/19.
 */

public class OkHttpManger {
    public static OkHttpClient getInstance() {
        return Holder.Instance;
    }

    private static class Holder {
        private static OkHttpClient Instance;

        static {
            Instance = new OkHttpClient.Builder()
                    .sslSocketFactory(HttpsTrustManger.createSSLSocketFactory())
                    .hostnameVerifier(new HttpsTrustManger.TrustCorrectHostnameVerifier())
                    .connectTimeout(30, TimeUnit.SECONDS)
                    .readTimeout(30, TimeUnit.SECONDS)
                    .writeTimeout(30, TimeUnit.SECONDS)
                    .cookieJar(new MCookieJar())
                    .build();
        }
    }

    public static void addSSlVerifier(OkHttpClient okHttpClient) {
        OkHttpClient.Builder builder = okHttpClient.newBuilder();
        ConnectionSpec spec = new ConnectionSpec.Builder(ConnectionSpec.MODERN_TLS)
                .allEnabledCipherSuites()
                .allEnabledTlsVersions()
                .build();
        SSLSocketFactory sslSocketFactory = createSSF();
        builder.connectionSpecs(Collections.singletonList(spec))
                .sslSocketFactory(sslSocketFactory)
                .hostnameVerifier(new HostnameVerifier() {
                    @Override
                    public boolean verify(String hostname, SSLSession session) {
                        return true;
                    }
                });
    }

    public static SSLSocketFactory createSSF() {
        SSLSocketFactory sslSocketFactory = null;
        try {
            //设置证书类型
            CertificateFactory factory = CertificateFactory.getInstance("X.509", "BC");
            //打开assets下的证书
            InputStream stream = MApplication.getContext().getAssets().open("ca_220.crt");
            Certificate certificate = factory.generateCertificate(stream);
            //证书类型
            KeyStore keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
            //授权证书,授权证书密码
            keyStore.load(null, null);
            keyStore.setCertificateEntry("ca", certificate);

            TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
            trustManagerFactory.init(keyStore);

//            KeyManagerFactory keyManagerFactory = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
//            keyManagerFactory.init(keyStore,"".toCharArray());

            SSLContext sslContext = SSLContext.getInstance("TLS");
            sslContext.init(null, trustManagerFactory.getTrustManagers(), null);
            sslSocketFactory = sslContext.getSocketFactory();


        } catch (CertificateException e) {
            e.printStackTrace();
        } catch (NoSuchProviderException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (KeyStoreException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (KeyManagementException e) {
            e.printStackTrace();
        }
        return sslSocketFactory;
    }

    public static SSLSocketFactory getSslSocketFactory(Context context, String certName) throws KeyStoreException,
            CertificateException, NoSuchAlgorithmException, IOException, KeyManagementException {
        InputStream inputStream = context.getAssets().open(certName);
        // Create a KeyStore containing our trusted CAs
        KeyStore keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
        keyStore.load(null, null);

        CertificateFactory certificateFactory = CertificateFactory.getInstance("X.509");
        Certificate certificate = certificateFactory.generateCertificate(inputStream);

        keyStore.setCertificateEntry("ca", certificate);
        // Create a TrustManager that trusts the CAs in our KeyStore
        String tmfAlgorithm = TrustManagerFactory.getDefaultAlgorithm();
        TrustManagerFactory tmf = TrustManagerFactory.getInstance(tmfAlgorithm);
        tmf.init(keyStore);

        // Create an SSLContext that uses our TrustManager
        SSLContext sslContext = SSLContext.getInstance("TLS");
        sslContext.init(null, tmf.getTrustManagers(), null);
        return sslContext.getSocketFactory();
    }


}
