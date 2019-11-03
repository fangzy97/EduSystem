package com.lepetit.edu.util;

import com.lepetit.edu.callback.GetDateCallback;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/*
* 该类用于处理各种与时间有关的操作
*/
public class DateUtil {
    private final int year;
    private final int month;
    private final int day;
    private final int week;

    public static DateUtil getInstance() {
        return DateUtilHolder.instance;
    }

    private DateUtil() {
        Calendar calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH) + 1;
        day = calendar.get(Calendar.DAY_OF_MONTH);
        week = calendar.get(Calendar.DAY_OF_WEEK);
    }

    /*
    * 获取年、月、日、星期*/
    public void getDate(GetDateCallback getDateCallback) {
        getDateCallback.onGetDate(year, month, day, weekToString(week));
    }

    /*
    * 获取学期信息的列表，格式如下：
    * 2018-2019-1，2018-2019-2 ……
    */
    public List<String> getTermList() {
        List<String> termList = new ArrayList<>();
        for (int i = 2014; i < year; i++) {
            String term1 = i + "-" + (i + 1) + "-1";
            String term2 = i + "-" + (i + 1) + "-2";
            termList.add(term1);
            termList.add(term2);
        }
        if (month >= 6) {
            String string = year + "-" + (year + 1) + "-1";
            termList.add(string);
        }
        return termList;
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

    private static class DateUtilHolder {
        private static DateUtil instance = new DateUtil();
    }
}
