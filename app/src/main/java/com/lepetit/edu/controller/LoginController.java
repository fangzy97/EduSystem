package com.lepetit.edu.controller;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.lepetit.edu.callback.LoginCallback;
import com.lepetit.edu.inter.ILogin;
import com.lepetit.edu.util.StringUtil;

import org.jetbrains.annotations.NotNull;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;
import java.util.Objects;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.Headers;
import okhttp3.Response;

public class LoginController extends BaseController implements ILogin {
    private final int GET_LT_SUCCESS = 1;
    private final int GET_LT_FAILED = -1;
    private final int LOGIN_SUCCESS = 2;
    private final int LOGIN_FAILED = -2;
    private final int LOGIN_NOT_RESPONSE = -3;

    private LoginCallback loginCallback;
    private String userName;
    private String password;
    private String lt;

    private Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message message) {
            switch (message.what) {
                case GET_LT_SUCCESS:
                    loginToSystem();
                    return true;
                case GET_LT_FAILED:
                case LOGIN_NOT_RESPONSE:
                    loginCallback.onLoginNotResponse();
                    return true;
                case LOGIN_SUCCESS:
                    loginCallback.onLoginSuccess();
                    return true;
                case LOGIN_FAILED:
                    loginCallback.onLoginFailed();
                    return false;
            }
            return false;
        }
    });

    /*
    * 登录提交的表单中有一个随机生成的lt
    * 这里发送一个get请求获取页面中的lt值并保存cookie（OKHttp3自动管理）
    */
    private void getLtValue() {
        super.getOKHttpUtil().getAsync(StringUtil.loginUrl, new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                Message message = Message.obtain();
                message.what = GET_LT_FAILED;
                handler.sendMessage(message);
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                lt = getHiddenValue(Objects.requireNonNull(response.body()).string());
                Message message = Message.obtain();
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
                message.what = LOGIN_NOT_RESPONSE;
                handler.sendMessage(message);
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                Message message = new Message();
                if (isLoginSuccess(Objects.requireNonNull(response.body()).string())) {
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
    * 实现ILogin接口，执行登录操作
    */
    @Override
    public void startLogin(@NotNull String username, @NotNull String password, @NotNull LoginCallback loginCallback) {
        super.newOKHttpUtilInstance();
        this.userName = username;
        this.password = password;
        this.loginCallback = loginCallback;
        getLtValue();
    }
}
