package com.lepetit.edu.callback;

import android.widget.ArrayAdapter;

public interface SetTermSpinnerCallback {
    /**
     * 用于设置课程表界面中学期下拉框的内容
     * @param adapter 学期下拉框填充的内容
     * @param index 当前学期在下拉框中的索引号
     * */
    void onSetTermSpinner(ArrayAdapter<String> adapter, int index);
}
