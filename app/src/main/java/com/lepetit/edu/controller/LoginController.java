package com.lepetit.edu.controller;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

import com.lepetit.edu.activity.LoginActivity;
import com.lepetit.edu.application.MyApplication;
import com.lepetit.edu.util.OKHttpUtil;
import com.lepetit.edu.util.StringUtil;

import org.jetbrains.annotations.NotNull;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.Headers;
import okhttp3.Response;

public class LoginController {
    private final int GET_LT_SUCCESS = 1;
    private final int GET_LT_FAILED = -1;
    private final int LOGIN_SUCCESS = 2;
    private final int LOGIN_FAILED = -2;

    private final LoginActivity loginActivity;
    private final String userName;
    private final String password;
    private String lt;

    public LoginController(String userName, String password) {
        this.userName = userName;
        this.password = password;
        this.loginActivity = null;
    }

    public LoginController(String userName, String password, LoginActivity loginActivity) {
        this.userName = userName;
        this.password = password;
        this.loginActivity = loginActivity;
    }

    private Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message message) {
            switch (message.what) {
                case GET_LT_SUCCESS:
                    loginToSystem();
                    return true;
                case GET_LT_FAILED:
                    Toast.makeText(MyApplication.getContext(), "请连接到校园网后重试", Toast.LENGTH_SHORT).show();

                    return false;
                case LOGIN_SUCCESS:
                    if (loginActivity != null) {
                        storeUserInfo();
                        loginActivity.backToMainActivity();
                    }
                    return true;
                case LOGIN_FAILED:
                    Toast.makeText(MyApplication.getContext(), "用户名或密码错误", Toast.LENGTH_SHORT).show();
                    return false;
            }
            return false;
        }
    });

    public void startLogin() {
        getLtValue();
    }

    private void getLtValue() {
        OKHttpUtil.getInstance().getAsync(StringUtil.loginUrl, new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                Message message = new Message();
                message.what = GET_LT_FAILED;
                handler.sendMessage(message);
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                lt = getHiddenValue(response.body().string());
                Message message = new Message();
                if (lt == null) {
                    message.what = GET_LT_FAILED;
                } else {
                    message.what = GET_LT_SUCCESS;
                }
                handler.sendMessage(message);
            }
        });
    }

    private void loginToSystem() {
        FormBody formBody = new FormBody.Builder()
                .add("username", userName)
                .add("password", password)
                .add("lt", lt)
                .add("execution", StringUtil.execution)
                .add("_eventId", StringUtil._eventId)
                .add("rmShown", StringUtil.rmShown)
                .build();

        Headers headers = new Headers.Builder()
                .add("User-Agent", StringUtil.userAgent)
                .add("Referer", StringUtil.reference)
                .build();

        OKHttpUtil.getInstance().postAsync(StringUtil.loginUrl, formBody, headers, new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                Message message = new Message();
                message.what = LOGIN_FAILED;
                handler.sendMessage(message);
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                Message message = new Message();
                if (isLoginSuccess(response.body().string())) {
                    message.what = LOGIN_SUCCESS;
                } else {
                    message.what = LOGIN_FAILED;
                }
                handler.sendMessage(message);
            }
        });
    }

    /*
    * 获取lt这个隐藏域的值
    */
    private String getHiddenValue(String html) {
        Document document = Jsoup.parse(html);
        Log.i("lt_msg", document.outerHtml());
        Element element = document.select("input[type=hidden]").first();
        return element.attr("value");
    }

    /*
    * 用于判断登录是否失败
    * 若登录失败，在跳转后的页面仍为登录页面，该页面上能找到username这个字段，返回true
    * 否则返回false
    */
    private boolean isLoginSuccess(String html) {
        Document document = Jsoup.parse(html);
        Log.i("login_msg", document.outerHtml());
        Element element = document.getElementById("username");
        return element == null;
    }

    /*
    * 存储用户名和密码到本地
    */
    private void storeUserInfo() {
        SharedPreferences preferences = MyApplication.getContext().getSharedPreferences("UserInfo", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("UserName", userName);
        editor.putString("Password", password);
        editor.apply();
    }
}
