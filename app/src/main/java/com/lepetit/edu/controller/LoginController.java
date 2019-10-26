package com.lepetit.edu.controller;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.lepetit.edu.activity.BaseActivity;
import com.lepetit.edu.activity.LoginActivity;
import com.lepetit.edu.application.MyApplication;
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

public class LoginController extends BaseController {
    private final int GET_LT_SUCCESS = 1;
    private final int GET_LT_FAILED = -1;
    private final int LOGIN_SUCCESS = 2;
    private final int LOGIN_FAILED = -2;

    private final BaseActivity activity;
    private final String userName;
    private final String password;
    private String lt;

    public LoginController(String userName, String password, BaseActivity activity) {
        this.userName = userName;
        this.password = password;
        this.activity = activity;
    }

    private Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message message) {
            switch (message.what) {
                case GET_LT_SUCCESS:
                    loginToSystem();
                    return true;
                case GET_LT_FAILED:
                    activity.displayToast("请连接到校园网后重试！");
                    activity.removeDialog();
                    return false;
                case LOGIN_SUCCESS:
                    activity.removeDialog();
                    if (activity instanceof LoginActivity) {
                        storeUserInfo();
                        ((LoginActivity)activity).backToMainActivity();
                    }
                    return true;
                case LOGIN_FAILED:
                    activity.displayToast("用户名或密码错误！");
                    activity.removeDialog();
                    return false;
            }
            return false;
        }
    });

    /*
    * 开始执行登录操作
    */
    public void startLogin() {
        super.newOKHttpUtilInstance();
        getLtValue();
    }

    /*
    * 登录提交的表单中有一个随机生成的lt
    * 这里发送一个get请求获取页面中的lt值并保存cookie（OKHttp3自动管理）
    */
    private void getLtValue() {
        super.getOKHttpUtil().getAsync(StringUtil.loginUrl, new Callback() {
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

    /*
    * 在获取到lt值之后将用户名和密码提交到教务处服务器
    */
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

        super.getOKHttpUtil().postAsync(StringUtil.loginUrl, formBody, headers, new Callback() {
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
