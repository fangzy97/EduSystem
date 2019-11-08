package com.lepetit.edu.util;

import com.lepetit.edu.callback.GetDateCallback;

import org.jetbrains.annotations.NotNull;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

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

    /**
     * 获取当前学期
     * @return 当前学期格式化之后的字符串
     * */
    public String getCurrentTerm() {
        if (month > 1 && month < 8) {
            return (year - 1) + "-" + year + "-2";
        } else {
            return year + "-" + (year + 1) + "-1";
        }
    }

    /**
     * 计算今天是第几个教学周
     * @param firstDay 开学第一天，一般为周五，不计算在教学周中
     * @param lastDay 教学周最后一天
     * @return 周数*/
    public int getPastWeek(@NotNull String firstDay, @NotNull String lastDay) throws ParseException {
        // 将字符串转换为Date类型
        String today = year + "-" + month + "-" + day;
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA);
        Date mFirstDay = format.parse(firstDay);
        Date mToday = format.parse(today);
        Date mLastDay = format.parse(lastDay);
        // 计算今天和开学第一天相差的时间
        long pastTime;
        long threeDay = 1000 * 3600 * 24 * 3;
        if (mToday.before(mFirstDay)) {
            pastTime = 0;
        } else if (mToday.after(mLastDay)) {
            pastTime = mLastDay.getTime() - mFirstDay.getTime() - threeDay;
        } else {
            pastTime = mToday.getTime() - mFirstDay.getTime() - threeDay;
        }
        return (int)(pastTime / (1000 * 3600 * 24 * 7) + 1);
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
