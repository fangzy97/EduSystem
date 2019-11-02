package com.lepetit.edu.util;

import com.lepetit.edu.inter.IGetDateCallback;

import java.util.Calendar;

/*
* 该类用于处理各种与时间有关的操作
*/
public class DateUtil {
    private Calendar calendar;

    public DateUtil() {
        calendar = Calendar.getInstance();
    }

    /*
    * 获取年、月、日、星期*/
    public void getDate(IGetDateCallback getDateCallback) {
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH) + 1;
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int week = calendar.get(Calendar.DAY_OF_WEEK);
        getDateCallback.onGetDate(year, month, day, weekToString(week));
    }

    /*
    * 将星期信息转换为中文表达
    */
    private String weekToString(int week) {
        switch (week) {
            case 1: return "日";
            case 2: return "一";
            case 3: return "二";
            case 4: return "三";
            case 5: return "四";
            case 6: return "五";
            case 7: return "六";
            default: return "";
        }
    }
}
