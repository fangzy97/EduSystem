package com.lepetit.edu.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import androidx.annotation.NonNull;
import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.HttpUrl;

class CookiesUtil implements CookieJar {
    private HashMap<String, List<Cookie>> cookiesStore;

    CookiesUtil(){
        this.cookiesStore = new HashMap<>();
    }

    @Override
    public void saveFromResponse(HttpUrl url, @NonNull List<Cookie> cookies) {
        cookiesStore.put(url.host(), cookies);
    }

    @NonNull
    @Override
    public List<Cookie> loadForRequest(HttpUrl url) {
        List<Cookie> list = cookiesStore.get(url.host());
        return list != null ? list : new ArrayList<Cookie>();
    }
}
