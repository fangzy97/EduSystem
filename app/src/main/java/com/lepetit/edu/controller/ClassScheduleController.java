package com.lepetit.edu.controller;

import android.widget.ArrayAdapter;

import com.lepetit.edu.application.MyApplication;
import com.lepetit.edu.callback.SetTermSpinnerCallback;
import com.lepetit.edu.inter.IClassSchedule;
import com.lepetit.edu.util.DateUtil;

import java.util.List;

public class ClassScheduleController implements IClassSchedule {

    private List<String> termList = DateUtil.getInstance().getTermList();

    @Override
    public void setTermSpinner(SetTermSpinnerCallback callback) {
        ArrayAdapter<String> adapter = new ArrayAdapter<>(MyApplication.getContext(), android.R.layout.simple_list_item_1, termList);
        String currentTerm = DateUtil.getInstance().getCurrentTerm();
        int index = termList.indexOf(currentTerm);
        callback.onSetTermSpinner(adapter, index);
    }

    @Override
    public int setWeekSpinner() {
        String currentTerm = DateUtil.getInstance().getCurrentTerm();
        return termList.indexOf(currentTerm);
    }

    @Override
    public void displayClassSchedule() {

    }
}
