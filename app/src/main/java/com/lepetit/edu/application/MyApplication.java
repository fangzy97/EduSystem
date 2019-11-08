package com.lepetit.edu.application;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;

import org.litepal.LitePal;

public class MyApplication extends Application {
    @SuppressLint("StaticFieldLeak")
    private static Context context;

    private static boolean loginStatus;

    public static int LOCAL = 1;
    public static int ONLINE = 2;

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
        loginStatus = false;
        LitePal.initialize(context);
    }

    public static Context getContext() {
        return context;
    }

    public static boolean getLoginStatus() {
        return loginStatus;
    }

    public static void setLoginStatus(boolean loginStatus) {
        MyApplication.loginStatus = loginStatus;
    }
}
