package com.lepetit.edu.controller;

import android.widget.ArrayAdapter;

import com.lepetit.edu.application.MyApplication;
import com.lepetit.edu.callback.GetTermSpinnerAdapterCallback;
import com.lepetit.edu.inter.IClassSchedule;
import com.lepetit.edu.util.DateUtil;

import java.util.List;

public class ClassScheduleController implements IClassSchedule {
    @Override
    public void setTermSpinner(GetTermSpinnerAdapterCallback callback) {
        List<String> termList = DateUtil.getInstance().getTermList();
        ArrayAdapter<String> adapter = new ArrayAdapter<>(MyApplication.getContext(), android.R.layout.simple_list_item_1, termList);
        callback.onGetTermSpinnerAdapter(adapter);
    }

    @Override
    public void setWeekSpinner() {

    }

    @Override
    public void displayClassSchedule() {

    }
}
