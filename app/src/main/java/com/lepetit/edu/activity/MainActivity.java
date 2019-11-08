package com.lepetit.edu.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.bottomnavigation.LabelVisibilityMode;
import com.lepetit.edu.R;
import com.lepetit.edu.application.MyApplication;
import com.lepetit.edu.callback.LoginCallback;
import com.lepetit.edu.controller.LoginController;
import com.lepetit.edu.inter.ILogin;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity {
    private final int LOGIN_REQUEST = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initializeNavigation();
        checkLoginStatus();
    }

    /*
    * 初始化底部导航栏
    */
    private void initializeNavigation() {
        BottomNavigationView navView = findViewById(R.id.nav_view);
        navView.setLabelVisibilityMode(LabelVisibilityMode.LABEL_VISIBILITY_LABELED);
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupWithNavController(navView, navController);
    }

    /**
     * 提交用户名和密码登录到教务处
     * @param username 用户名
     * @param password 密码
     * */
    public void loginToJWC(String username, String password) {
        ILogin login = new LoginController();
        login.startLogin(username, password, new LoginCallback() {
            @Override
            public void onLoginSuccess() {
                MyApplication.setLoginStatus(true);
            }

            @Override
            public void onLoginFailed() {
                MainActivity.super.displayToast("用户名或密码错误！");
            }

            @Override
            public void onLoginNotResponse() {
                MainActivity.super.displayToast("请连接到校园网后重试！");
            }
        });
    }

    /**
     * 用于检查本地是否有可用于登录的信息
     * 若没有则跳转到登录界面
     * */
    private void checkLoginStatus() {
        SharedPreferences preferences = MyApplication.getContext().getSharedPreferences("UserInfo", MODE_PRIVATE);
        String userName = preferences.getString("UserName", "");
        String password = preferences.getString("Password", "");

        if (!isHaveLoginInfo(userName, password)) {
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            startActivityForResult(intent, LOGIN_REQUEST);
        } else {
            loginToJWC(userName, password);
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
