package com.lepetit.edu.callback;

public interface SetWeekSpinnerCallback {
    /**
     * 回调函数使界面中的周数更新
     * @param index 当前周数在界面中的索引
     * */
    void onSuccess(int index, int status);

    /**
     * 因没有连接到校园网导致更新周数失败
     * */
    void onFailed();
}
