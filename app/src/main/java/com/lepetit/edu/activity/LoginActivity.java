package com.lepetit.edu.activity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;

import com.lepetit.edu.R;
import com.lepetit.edu.application.MyApplication;
import com.lepetit.edu.callback.LoginCallback;
import com.lepetit.edu.controller.LoginController;
import com.lepetit.edu.inter.ILogin;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends BaseActivity {
    @BindView(R.id.userName)
    EditText userNameText;
    @BindView(R.id.password)
    EditText passwordText;

    private String userName;
    private String password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.loginButton)
    public void onLoginButtonClick() {
        userName = userNameText.getText().toString();
        password = passwordText.getText().toString();
        if (isUserInfoEmpty()) {
            Toast.makeText(MyApplication.getContext(), "用户名或密码不能为空", Toast.LENGTH_SHORT).show();
        } else {
            super.displayDialog();
            ILogin login = new LoginController();
            login.startLogin(userName, password, new LoginCallback() {
                @Override
                public void onLoginSuccess() {
                    storeUserInfo();
                    LoginActivity.super.removeDialog();
                    backToMainActivity();
                }

                @Override
                public void onLoginFailed() {
                    LoginActivity.super.removeDialog();
                    LoginActivity.super.displayToast("用户名或密码错误！");
                }

                @Override
                public void onLoginNotResponse() {
                    LoginActivity.super.removeDialog();
                    LoginActivity.super.displayToast("请连接到校园网后重试！");
                }
            });
        }
    }

    public void backToMainActivity() {
        removeDialog();
        setResult(RESULT_OK);
        finish();
    }

    private boolean isUserInfoEmpty() {
        return userName.equals("") || password.equals("");
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
