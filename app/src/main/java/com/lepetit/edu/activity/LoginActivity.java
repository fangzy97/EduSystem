package com.lepetit.edu.activity;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;

import com.lepetit.edu.R;
import com.lepetit.edu.application.MyApplication;
import com.lepetit.edu.util.LoadingDialogUtil;
import com.lepetit.edu.util.LoginUtil;

import androidx.appcompat.app.AppCompatActivity;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends AppCompatActivity {
    @BindView(R.id.userName)
    EditText userNameText;
    @BindView(R.id.password)
    EditText passwordText;

    private String userName;
    private String password;

    private LoadingDialogUtil dialogUtil = new LoadingDialogUtil(this);

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
            dialogUtil.display();
            LoginUtil loginUtil = new LoginUtil(userName, password, this);
            loginUtil.startLogin();
        }
    }

    public void backToMainActivity() {
        dialogUtil.remove();
        setResult(RESULT_OK);
        finish();
    }

    private boolean isUserInfoEmpty() {
        return userName.equals("") || password.equals("");
    }
}
