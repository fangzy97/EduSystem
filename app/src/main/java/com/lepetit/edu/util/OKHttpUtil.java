package com.lepetit.edu.util;

import okhttp3.Callback;
import okhttp3.Headers;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

public class OKHttpUtil {

    private OkHttpClient client;

    private OKHttpUtil() {
        client = new OkHttpClient.Builder()
                .cookieJar(new CookiesUtil())
                .build();
    }

    public static OKHttpUtil getInstance() {
        return OKHttpUtilHolder.instance;
    }

    // 异步get请求
    public void getAsync(String url, Callback callback) {
        Request request = new Request.Builder()
                .url(url)
                .get()
                .build();
        client.newCall(request).enqueue(callback);
    }

    // 异步的post请求
    public void postAsync(String url, RequestBody body, Headers headers, Callback callback) {
        Request request = new Request.Builder()
                .url(url)
                .headers(headers)
                .post(body)
                .build();
        client.newCall(request).enqueue(callback);
    }

    private static class OKHttpUtilHolder {
        private static OKHttpUtil instance = new OKHttpUtil();
    }
}
