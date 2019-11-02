package com.lepetit.edu.inter;

public interface ILoginCallback {
    void onLoginSuccess();
    void onLoginFailed();
    void onLoginNotResponse();
}
