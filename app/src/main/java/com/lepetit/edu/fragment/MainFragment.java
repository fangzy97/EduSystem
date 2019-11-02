package com.lepetit.edu.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.lepetit.edu.R;
import com.lepetit.edu.adapter.MainRecyclerViewAdapter;
import com.lepetit.edu.info.MainExamInfo;
import com.lepetit.edu.util.DateUtil;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

public class MainFragment extends Fragment {

    @NotNull
    private List<MainExamInfo> mainExamInfoList = new ArrayList<>();

    @BindView(R.id.main_exam_list)
    RecyclerView recyclerView;
    @BindView(R.id.main_month_and_day)
    TextView monthAndDayText;
    @BindView(R.id.main_week)
    TextView weekText;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.main_fragment, container, false);
        ButterKnife.bind(this, root);
        setDateInfoText();
        getMainExamInfo();
        setExamRecyclerView();
        return root;
    }

    /*
    * 该函数用于设置主页顶部的时间信息
    */
    private void setDateInfoText() {
        DateUtil dateUtil = new DateUtil();
        dateUtil.getDate((year, month, day, week) -> {
            String monthAndDay = month + "月" + day + "日";
            monthAndDayText.setText(monthAndDay);

            String mWeek = "星期" + week;
            weekText.setText(mWeek);
        });
    }

    /*
    * 从数据库中获取考试信息并计算近一个月的考试
    * 若没有则显示"最近没有考试"
    * */
    private void getMainExamInfo() {
        MainExamInfo info = new MainExamInfo("最近没有考试", "", "");
        mainExamInfoList.add(info);
    }

    /*
    * 设置显示考试信息的recyclerView
    */
    private void setExamRecyclerView() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);

        MainRecyclerViewAdapter adapter = new MainRecyclerViewAdapter(mainExamInfoList);
        recyclerView.setAdapter(adapter);
    }
}
