package com.lepetit.edu.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.lepetit.edu.R;
import com.lepetit.edu.application.MyApplication;
import com.lepetit.edu.util.LoginUtil;

import androidx.appcompat.app.AppCompatActivity;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {
    private final int LOGIN_REQUEST = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        checkLoginStatus();
    }

    /*
     * 检查本地是否已经有用户名和密码信息
     * 若存在则执行登录操作
     * 否则跳转到登录界面
     */
    private void checkLoginStatus() {
        SharedPreferences preferences = MyApplication.getContext().getSharedPreferences("UserInfo", MODE_PRIVATE);
        String userName = preferences.getString("UserName", "");
        String password = preferences.getString("Password", "");

        if (isHaveLoginInfo(userName, password)) {
            LoginUtil loginUtil = new LoginUtil(userName, password);
            loginUtil.startLogin();
        } else {
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            startActivityForResult(intent, LOGIN_REQUEST);
        }
    }

    /*
    * 判断本地是否已经存在用户名和密码
    */
    private boolean isHaveLoginInfo(String userName, String password) {
        if (userName == null || password == null) {
            return false;
        } else {
            return !userName.equals("") && !password.equals("");
        }
    }

    /*
    * 处理LoginActivity返回的结果
    */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == LOGIN_REQUEST) {
            MyApplication.setLoginStatus(true);
        }
    }
}
