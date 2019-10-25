package com.lepetit.edu.application;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.widget.Toast;

public class MyApplication extends Application {
    @SuppressLint("StaticFieldLeak")
    private static Context context;

    private static boolean loginStatus;

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
        loginStatus = false;
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
