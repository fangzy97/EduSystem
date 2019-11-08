package com.lepetit.edu.inter;

import com.lepetit.edu.callback.LoginCallback;

public interface ILogin {
    void startLogin(String username, String password, LoginCallback callback);
}
