package com.lepetit.edu.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.lepetit.edu.R;
import com.lepetit.edu.info.MainExamInfo;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class MainRecyclerViewAdapter extends RecyclerView.Adapter<MainRecyclerViewAdapter.ViewHolder> {
    private List<MainExamInfo> list;
    private Context context;

    public MainRecyclerViewAdapter(List<MainExamInfo> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (context == null) {
            context = parent.getContext();
        }
        View root = LayoutInflater.from(context).inflate(R.layout.main_exam_item, parent, false);
        return new ViewHolder(root);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        MainExamInfo info = list.get(position);

        String examTime = info.getExamTime();
        holder.examTime.setText(examTime);

        String course = info.getCourse();
        holder.examCourse.setText(course);

        String dayFromExam = info.getDayFromExam();
        holder.dayFromExam.setText(dayFromExam);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        View view;
        TextView examTime;
        TextView examCourse;
        TextView dayFromExam;

        ViewHolder(View itemView) {
            super(itemView);
            view = itemView;
            examTime = itemView.findViewById(R.id.exam_mon);
            examCourse = itemView.findViewById(R.id.exam_course_main);
            dayFromExam = itemView.findViewById(R.id.exam_last_day);
        }
    }
}
