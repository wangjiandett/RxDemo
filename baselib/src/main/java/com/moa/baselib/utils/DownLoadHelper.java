package com.moa.baselib.utils;///*
// *  Copyright (C) 2016-2017 浙江海宁山顶动力网络科技有限公司
// * 文件说明：
// *  <p>
// *  更新说明:
// *
// *  @author wangjian@xiaokebang.com
// *  @version 1.0.0
// *  @create 17-7-13 下午2:34
// *  @see <a href="http://www.top4s.net/">http://www.top4s.net/</a>
// */
//
//package com.moa.rxdemo.utils;
//
//import android.content.res.Resources;
//import android.os.Handler;
//import android.os.Looper;
//import android.util.Log;
//
//import com.moa.rxdemo.BaseApp;
//
//import java.io.File;
//import java.io.FileOutputStream;
//import java.io.IOException;
//import java.io.InputStream;
//import java.security.GeneralSecurityException;
//import java.security.KeyStore;
//import java.security.SecureRandom;
//import java.security.cert.Certificate;
//import java.security.cert.CertificateFactory;
//import java.util.Collection;
//import java.util.HashMap;
//import java.util.concurrent.TimeUnit;
//
//import javax.net.ssl.HostnameVerifier;
//import javax.net.ssl.KeyManagerFactory;
//import javax.net.ssl.SSLContext;
//import javax.net.ssl.SSLSession;
//import javax.net.ssl.TrustManagerFactory;
//
//import okhttp3.Call;
//import okhttp3.MediaType;
//import okhttp3.OkHttpClient;
//import okhttp3.Request;
//import okhttp3.RequestBody;
//import okio.Buffer;
//import okio.BufferedSink;
//import okio.Okio;
//import okio.Source;
//
///**
// * okhttp 上传下载，支持大文件
// *
// * @author wangjian@xiaokebang.com
// * @version 1.0.0
// * @create 17-7-11 下午3:32
// * @see <a href="http://www.top4s.net/">http://www.top4s.net/</a>
// */
//public class DownLoadHelper {
//
//    private static final String TAG = "DownLoadHelper";
//
//    private final MediaType MEDIA_TYPE = MediaType.parse("application/octet-stream");
//
//    private OkHttpClient client = null;
//
//    private Handler handler = null;
//
//    private static DownLoadHelper downLoadHelper;
//
//    private static final String REQUEST_TAG = "requestTag";
//
//    public static DownLoadHelper getDownLoadHelper() {
//        if (downLoadHelper == null) {
//            downLoadHelper = new DownLoadHelper();
//        }
//
//        return downLoadHelper;
//    }
//
//    private DownLoadHelper() {
//        OkHttpClient.Builder builder = new OkHttpClient.Builder();
//
//        Resources resources = BaseApp.getAppContext().getResources();
//        try {
//            String cert = resources.getString(
//                resources.getIdentifier("trusted_pem", "string", BaseApp.getAppContext().getPackageName()));
//            SSLContext sslContext = sslContextForTrustedCertificates(new Buffer().writeUtf8(cert).inputStream());
//            builder.sslSocketFactory(sslContext.getSocketFactory());
//        } catch (Resources.NotFoundException e) {
//            // Just Ignore
//        }
//
//        try {
//            final String trustHostname = resources.getString(
//                resources.getIdentifier("trusted_hostname", "string", BaseApp.getAppContext().getPackageName()));
//            builder.hostnameVerifier(new HostnameVerifier() {
//                @Override
//                public boolean verify(String hostname, SSLSession session) {
//                    return hostname.equals(trustHostname);
//                }
//            });
//        } catch (Resources.NotFoundException e) {
//            // Just Ignore
//        }
//
//        builder.readTimeout(30, TimeUnit.SECONDS);//设置读取超时时间
//        builder.connectTimeout(30, TimeUnit.SECONDS);//设置超时时间
//        builder.writeTimeout(30, TimeUnit.SECONDS);//设置写入超时时间
//
//        client = builder.build();
//
//        handler = new Handler(Looper.getMainLooper());
//    }
//
//    private SSLContext sslContextForTrustedCertificates(InputStream in) {
//        try {
//            CertificateFactory certificateFactory = CertificateFactory.getInstance("X.509");
//            Collection<? extends Certificate> certificates = certificateFactory.generateCertificates(in);
//            if (certificates.isEmpty()) {
//                throw new IllegalArgumentException("expected non-empty set of trusted certificates");
//            }
//
//            // Put the certificates a key store.
//            char[] password = "password".toCharArray(); // Any password will work.
//            KeyStore keyStore = newEmptyKeyStore(password);
//            int index = 0;
//            for (Certificate certificate : certificates) {
//                String certificateAlias = Integer.toString(index++);
//                keyStore.setCertificateEntry(certificateAlias, certificate);
//            }
//
//            // Wrap it up in an SSL context.
//            KeyManagerFactory keyManagerFactory = KeyManagerFactory.getInstance(
//                KeyManagerFactory.getDefaultAlgorithm());
//            keyManagerFactory.init(keyStore, password);
//            TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance(
//                TrustManagerFactory.getDefaultAlgorithm());
//            trustManagerFactory.init(keyStore);
//            SSLContext sslContext = SSLContext.getInstance("TLS");
//            sslContext.init(keyManagerFactory.getKeyManagers(), trustManagerFactory.getTrustManagers(),
//                new SecureRandom());
//            return sslContext;
//        } catch (GeneralSecurityException e) {
//            throw new RuntimeException(e);
//        }
//    }
//
//    private KeyStore newEmptyKeyStore(char[] password) throws GeneralSecurityException {
//        try {
//            KeyStore keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
//            InputStream in = null; // By convention, 'null' creates an empty key store.
//            keyStore.load(in, password);
//            return keyStore;
//        } catch (IOException e) {
//            throw new AssertionError(e);
//        }
//    }
//
//
//    /**
//     * 上传文件
//     *
//     * @param requestUrl 接口地址
//     * @param params     参数和文件
//     */
//    public Call upLoadFile(String requestUrl, HashMap<String, Object> params, final ReqCallBack callBack) {
//
//        MultipartBuilder builder = new MultipartBuilder();
//        //设置类型
//        builder.type(MultipartBuilder.FORM);
//
//        //追加参数
//        for (String key : params.keySet()) {
//            Object object = params.get(key);
//            if (!(object instanceof File)) {
//                builder.addFormDataPart(key, object.toString());
//            }
//            else {
//                File file = (File) object;
//                builder.addFormDataPart(key, file.getName(), createProgressRequestBody(MEDIA_TYPE, file, callBack));
//            }
//        }
//
//        //创建RequestBody
//        RequestBody body = builder.build();
//        //创建Request
//        final Request request = new Request.Builder()//
//            .tag(REQUEST_TAG)//
//            .url(requestUrl)//
//            .post(body)//
//            .build();
//        Call call = client.newCall(request);
//        call.enqueue(new Callback() {
//            @Override
//            public void onFailure(Request request, IOException e) {
//                Log.d(TAG, "Uploading part error: " + request.toString());
//                failedCallBack("upload failure", callBack);
//            }
//
//            @Override
//            public void onResponse(Response response) throws IOException {
//                Log.d(TAG, "Upload part response: " + request.toString() + " -> " + response.toString());
//                if (response != null && response.isSuccessful() && response.code() == 200) {
//                    String string = response.body().string();
//                    successCallBack(string, callBack);
//                }
//                else {
//                    failedCallBack("upload failure", callBack);
//                }
//            }
//        });
//
//        return call;
//    }
//
//    /**
//     * 下载文件
//     *
//     * @param fileUrl     文件url
//     * @param destFileDir 存储目标目录
//     */
//    public Call downLoadFile(String fileUrl, String fileName, final String destFileDir, final ReqCallBack callBack) {
//        final File file = new File(destFileDir, fileName);
//        if (file.exists()) {
//            successCallBack(file.getAbsolutePath(), callBack);
//            return null;
//        }
//
//        final Request request = new Request.Builder()//
//            .tag(REQUEST_TAG)//
//            .url(fileUrl)//
//            .build();
//
//        Call call = client.newCall(request);
//        call.enqueue(new Callback() {
//            @Override
//            public void onFailure(Request request, IOException e) {
//                Log.d(TAG, "download part error: " + request.toString());
//                failedCallBack("download failure", callBack);
//            }
//
//            @Override
//            public void onResponse(Response response) throws IOException {
//                Log.d(TAG, "download part response: " + request.toString() + " -> " + response.toString());
//                InputStream is = null;
//                byte[] buf = new byte[2048];
//                int len = 0;
//                FileOutputStream fos = null;
//                try {
//                    long total = response.body().contentLength();
//                    Log.d(TAG, "total------>" + total);
//
//                    float downloadProgress = 0;
//                    float last = 0;
//                    long current = 0;
//                    is = response.body().byteStream();
//                    fos = new FileOutputStream(file);
//                    while ((len = is.read(buf)) != -1) {
//                        current += len;
//                        fos.write(buf, 0, len);
//
//                        downloadProgress = (float) (current * 100 / total);
//                        Log.d(TAG, "current------>" + current + " ,progress------>" + downloadProgress);
//
//                        // 控制更新进度100次
//                        if (downloadProgress - last >= 1) {
//                            progressCallBack(total, (int) downloadProgress, callBack);
//                            last = downloadProgress;
//                        }
//                    }
//                    fos.flush();
//                    successCallBack(file.getAbsolutePath(), callBack);
//                } catch (IOException e) {
//                    Log.d(TAG, e.toString());
//                    failedCallBack("下载失败", callBack);
//                } finally {DialogUtils
//                    try {
//                        if (is != null) {
//                            is.close();
//                        }
//                        if (fos != null) {
//                            fos.close();
//                        }
//                    } catch (IOException e) {
//                        Log.d(TAG, e.toString());
//                    }
//                }
//            }
//        });
//
//        return call;
//    }
//
//    public void cancelAll(){
//        if(client != null){
//            client.cancel(REQUEST_TAG);
//        }
//    }
//
//    /**
//     * 创建带进度的RequestBody
//     *
//     * @param contentType MediaType
//     * @param file        准备上传的文件
//     * @param callBack    回调
//     * @return
//     */
//    private RequestBody createProgressRequestBody(final MediaType contentType, final File file,
//                                                  final ReqCallBack callBack) {
//        return new RequestBody() {
//            @Override
//            public MediaType contentType() {
//                return contentType;
//            }
//
//            @Override
//            public long contentLength() {
//                return file.length();
//            }
//
//            @Override
//            public void writeTo(BufferedSink sink) throws IOException {
//                Source source;
//                try {
//                    source = Okio.source(file);
//                    Buffer buf = new Buffer();
//                    long remaining = contentLength();
//
//                    float downloadProgress = 0;
//                    float last = 0;
//                    long current = 0;
//                    for (long readCount; (readCount = source.read(buf, 2048)) != -1; ) {
//                        sink.write(buf, readCount);
//                        current += readCount;
//
//                        downloadProgress = (float) (current * 100 / remaining);
//                        Log.d(TAG, "current------>" + current + " ,progress------>" + downloadProgress);
//                        // 控制更新进度100次
//                        if (downloadProgress - last >= 1) {
//                            progressCallBack(remaining, (int) downloadProgress, callBack);
//                            last = downloadProgress;
//                        }
//                    }
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
//        };
//    }
//
//    /**
//     * 统一处理进度信息
//     *
//     * @param total    总计大小
//     * @param progress 当前进度
//     * @param callBack
//     */
//    private void progressCallBack(final long total, final int progress, final ReqCallBack callBack) {
//        handler.post(new Runnable() {
//            @Override
//            public void run() {
//                if (callBack != null) {
//                    callBack.onProgress(total, progress);
//                }
//            }
//        });
//    }
//
//    /**
//     * 统一处理成功信息
//     *
//     * @param result
//     * @param callBack
//     */
//    private void successCallBack(final String result, final ReqCallBack callBack) {
//        handler.post(new Runnable() {
//            @Override
//            public void run() {
//                if (callBack != null) {
//                    callBack.onReqSuccess(result);
//                }
//            }
//        });
//    }
//
//    /**
//     * 统一处理失败信息
//     *
//     * @param errorMsg
//     * @param callBack
//     */
//    private void failedCallBack(final String errorMsg, final ReqCallBack callBack) {
//        handler.post(new Runnable() {
//            @Override
//            public void run() {
//                if (callBack != null) {
//                    callBack.onReqFailed(errorMsg);
//                }
//            }
//        });
//    }
//
//    public interface ReqCallBack {
//        /**
//         * 响应成功
//         */
//        void onReqSuccess(String result);
//
//        /**
//         * 响应失败
//         */
//        void onReqFailed(String errorMsg);
//
//        /**
//         * 响应进度更新
//         */
//        void onProgress(long total, long progress);
//    }
//
//    public static class SimpleReqCallBack implements ReqCallBack{
//
//        @Override
//        public void onReqSuccess(String result) {
//        }
//
//        @Override
//        public void onReqFailed(String errorMsg) {
//        }
//
//        @Override
//        public void onProgress(long total, long progress) {
//        }
//    }
//
//}
