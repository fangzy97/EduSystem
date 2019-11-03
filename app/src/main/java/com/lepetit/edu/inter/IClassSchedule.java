package com.lepetit.edu.inter;

import android.widget.ArrayAdapter;

import com.lepetit.edu.callback.SetTermSpinnerCallback;

public interface IClassSchedule {
    /**
     * 设置学期的下拉框内容
     * @param callback 回调函数用以更新界面
     * */
    void setTermSpinner(SetTermSpinnerCallback callback);

    /**
     * 设置周数下拉框默认显示的内容（即当前周数）
     * @return 当前周数的index
     * */
    int setWeekSpinner();

    /**
    * 将课程表显示到屏幕上
    */
    void displayClassSchedule();
}
