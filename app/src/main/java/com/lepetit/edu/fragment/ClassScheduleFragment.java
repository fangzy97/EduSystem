package com.lepetit.edu.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.Spinner;

import com.lepetit.edu.R;
import com.lepetit.edu.controller.ClassScheduleController;
import com.lepetit.edu.inter.IClassSchedule;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import butterknife.BindView;
import butterknife.ButterKnife;

public class ClassScheduleFragment extends Fragment {

    @BindView(R.id.class_schedule_term_spinner)
    public Spinner termSpinner;
    @BindView(R.id.class_schedule_week_spinner)
    public Spinner weekSpinner;
    @BindView(R.id.class_schedule_grid)
    public GridLayout gridLayout;
    @BindView(R.id.class_schedule_swipe)
    SwipeRefreshLayout swipeRefreshLayout;

    IClassSchedule classSchedule = new ClassScheduleController();

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.class_schedule_fragment, container, false);
        ButterKnife.bind(this, root);
        setTermSpinner();
        setWeekSpinner();
        return root;
    }

    /*
    * 设置当前的学期
    */
    private void setTermSpinner() {
        classSchedule.setTermSpinner(adapter -> termSpinner.setAdapter(adapter));
    }

    /*
    * 设置当前周
    */
    private void setWeekSpinner() {

    }
}
