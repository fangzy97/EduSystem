package com.lepetit.edu.inter;

import com.lepetit.edu.callback.LoginCallback;

import org.jetbrains.annotations.NotNull;

public interface ILogin {
    void startLogin(@NotNull String username, @NotNull String password, @NotNull LoginCallback callback);
}
