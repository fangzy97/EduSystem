package com.lepetit.edu.activity;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;

import com.lepetit.edu.R;
import com.lepetit.edu.application.MyApplication;
import com.lepetit.edu.controller.LoadingDialogController;
import com.lepetit.edu.controller.LoginController;

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

    private LoadingDialogController dialogController = new LoadingDialogController(this);

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
            dialogController.display();
            LoginController loginController = new LoginController(userName, password, this);
            loginController.startLogin();
        }
    }

    public void removeDialog() {
        dialogController.remove();
    }

    public void backToMainActivity() {
        removeDialog();
        setResult(RESULT_OK);
        finish();
    }

    private boolean isUserInfoEmpty() {
        return userName.equals("") || password.equals("");
    }
}
