package com.lepetit.edu.callback;

public interface LoginCallback {
    void onLoginSuccess();
    void onLoginFailed();
    void onLoginNotResponse();
}
