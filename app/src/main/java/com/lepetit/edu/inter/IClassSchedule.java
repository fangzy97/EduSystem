package com.lepetit.edu.inter;

import com.lepetit.edu.callback.SetTermSpinnerCallback;
import com.lepetit.edu.callback.SetWeekSpinnerCallback;

import java.text.ParseException;

public interface IClassSchedule {
    /**
     * 设置学期的下拉框内容
     * @param callback 回调函数用以更新界面
     * */
    void setTermSpinner(SetTermSpinnerCallback callback);

    /**
     * 设置周数下拉框默认显示的内容（即当前周数）
     * */
    void setWeekSpinner(SetWeekSpinnerCallback callback) throws ParseException;

    /**
    * 将课程表显示到屏幕上
    */
    void displayClassSchedule();
}
