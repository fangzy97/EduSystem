package com.lepetit.edu.controller;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.ArrayAdapter;

import com.lepetit.edu.application.MyApplication;
import com.lepetit.edu.callback.SetTermSpinnerCallback;
import com.lepetit.edu.callback.SetWeekSpinnerCallback;
import com.lepetit.edu.inter.IClassSchedule;
import com.lepetit.edu.inter.IParser;
import com.lepetit.edu.parser.WeekInfoParser;
import com.lepetit.edu.util.DateUtil;
import com.lepetit.edu.util.StringUtil;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.text.ParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class ClassScheduleController extends BaseController implements IClassSchedule {
    private final int GET_WEEK_NOT_RESPONSE = -1;
    private final int GET_WEEK_RESPONSE = 1;

    private SetWeekSpinnerCallback setWeekSpinnerCallback;
    private List<String> termList = DateUtil.getInstance().getTermList();

    private SharedPreferences preferences;

    public ClassScheduleController() {
        preferences = MyApplication.getContext().getSharedPreferences("DateInfo", Context.MODE_PRIVATE);
    }

    private Handler handler = new Handler(msg -> {
        switch (msg.what) {
            case GET_WEEK_NOT_RESPONSE:
                return true;
            case GET_WEEK_RESPONSE:
                Bundle bundle = msg.getData();
                String html = bundle.getString("html");
                getCurrentWeek(html);
                return true;
        }
        return false;
    });

    @Override
    public void setTermSpinner(SetTermSpinnerCallback callback) {
        ArrayAdapter<String> adapter = new ArrayAdapter<>(MyApplication.getContext(), android.R.layout.simple_list_item_1, termList);
        String currentTerm = DateUtil.getInstance().getCurrentTerm();
        int index = termList.indexOf(currentTerm);
        callback.onSetTermSpinner(adapter, index);
    }

    @Override
    public void setWeekSpinner(SetWeekSpinnerCallback callback) throws ParseException {
        this.setWeekSpinnerCallback = callback;
        if (MyApplication.getLoginStatus()) {
            getWeekInfoFromWeb();
        } else {
            Map<String, String> map = getFirstAndLastDay();
            String firstDay = map.get("firstDay");
            String lastDay = map.get("lastDay");
            if (isFirstAndLastDayValid(firstDay, lastDay)) {
                int currentWeek = DateUtil.getInstance().getPastWeek(firstDay, lastDay);
                setWeekSpinnerCallback.onSuccess(currentWeek - 1, MyApplication.LOCAL);
            } else {
                setWeekSpinnerCallback.onFailed();
            }
        }
    }

    @Override
    public void displayClassSchedule() {

    }

    private void getWeekInfoFromWeb() {
        super.getOKHttpUtil().getAsync(StringUtil.weekUrl, new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                Message message = Message.obtain();
                message.what = GET_WEEK_NOT_RESPONSE;
                handler.sendMessage(message);
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                Message message = Message.obtain();
                message.what = GET_WEEK_RESPONSE;
                Bundle bundle = new Bundle();
                bundle.putString("html", Objects.requireNonNull(response.body()).string());
                message.setData(bundle);
                handler.sendMessage(message);
            }
        });
    }

    /**
     * 根据计算得到的日期调用回调函数更新界面
     * @param html 获取到的html代码
     * */
    private void getCurrentWeek(String html) {
        IParser parser = new WeekInfoParser();
        parser.parserHtml(html, bundle -> {
            String firstDay = bundle.getString("firstDay");
            String lastDay = bundle.getString("lastDay");
            int currentWeek = 1;
            if (isFirstAndLastDayValid(firstDay, lastDay)) {
                currentWeek = DateUtil.getInstance().getPastWeek(firstDay, lastDay);
                storeFirstAndLastDay(firstDay, lastDay);
            }
            setWeekSpinnerCallback.onSuccess(currentWeek - 1, MyApplication.ONLINE);
        });
    }

    /**
     * 从本地文件中获取第一天和最后一天
     * @return 包含了firstDay和lastDay两个键值对信息
     * */
    private Map<String, String> getFirstAndLastDay() {
        String firstDay = preferences.getString("firstDay", "");
        String lastDay = preferences.getString("lastDay", "");
        Map<String, String> map = new HashMap<>();
        if (isFirstAndLastDayValid(firstDay, lastDay)) {
            map.put("firstDay", firstDay);
            map.put("lastDay", lastDay);
        }
        return map;
    }

    /**
     * 将开学第一天和最后一天储存下来便于离线状态使用
     * @param firstDay 第一天
     * @param lastDay 最后一天*/
    private void storeFirstAndLastDay(String firstDay, String lastDay) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("firstDay", firstDay);
        editor.putString("lastDay", lastDay);
        editor.apply();
    }

    private boolean isFirstAndLastDayValid(String firstDay, String lastDay) {
        return firstDay != null && lastDay != null && !firstDay.isEmpty() && !lastDay.isEmpty();
    }
}
