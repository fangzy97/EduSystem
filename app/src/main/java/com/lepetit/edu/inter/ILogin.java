package com.lepetit.edu.inter;

import org.jetbrains.annotations.NotNull;

public interface ILogin {
    void startLogin(@NotNull String username, @NotNull String password, @NotNull ILoginCallback callback);
}
