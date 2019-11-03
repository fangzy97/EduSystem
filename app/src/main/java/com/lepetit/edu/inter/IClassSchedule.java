package com.lepetit.edu.inter;

import com.lepetit.edu.callback.GetTermSpinnerAdapterCallback;

public interface IClassSchedule {
    /*
    * 设置学期的下拉框内容
    */
    void setTermSpinner(GetTermSpinnerAdapterCallback callback);

    /*
    * 设置默认选择的周数
    */
    void setWeekSpinner();

    /*
    * 将课程表显示到屏幕上
    */
    void displayClassSchedule();
}
