package com.lepetit.edu.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.lepetit.edu.R;
import com.lepetit.edu.activity.MainActivity;
import com.lepetit.edu.application.MyApplication;
import com.lepetit.edu.callback.SetWeekSpinnerCallback;
import com.lepetit.edu.controller.ClassScheduleController;
import com.lepetit.edu.inter.IClassSchedule;

import java.text.ParseException;

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
    @BindView(R.id.class_schedule_status)
    public TextView statusText;

    private IClassSchedule classSchedule = new ClassScheduleController();
    private MainActivity activity;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.class_schedule_fragment, container, false);
        ButterKnife.bind(this, root);
        activity = (MainActivity) getActivity();
        setTermSpinner();
        setWeekSpinner();
        return root;
    }

    /*
    * 设置当前的学期
    */
    private void setTermSpinner() {
        classSchedule.setTermSpinner((adapter, index) -> {
            termSpinner.setAdapter(adapter);
            termSpinner.setSelection(index, true);
        });
    }

    /**
     * 设置周数下拉框默认显示的内容
     * */
    private void setWeekSpinner() {
        try {
            classSchedule.setWeekSpinner(new SetWeekSpinnerCallback() {
                @Override
                public void onSuccess(int index, int status) {
                    weekSpinner.setSelection(index, true);
                    if (status == MyApplication.ONLINE) {
                        statusText.setText("最新");
                    } else if (status == MyApplication.LOCAL) {
                        statusText.setText("本地");
                    }
                }

                @Override
                public void onFailed() {
                    activity.displayToast("请连接到校园网后下拉刷新！");
                }
            });
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
}
