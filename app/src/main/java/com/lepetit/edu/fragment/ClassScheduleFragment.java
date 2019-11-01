package com.lepetit.edu.fragment;

import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lepetit.edu.view_model.ClassScheduleViewModel;
import com.lepetit.edu.R;

public class ClassScheduleFragment extends Fragment {

    private ClassScheduleViewModel mViewModel;

    public static ClassScheduleFragment newInstance() {
        return new ClassScheduleFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.class_schedule_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(ClassScheduleViewModel.class);
        // TODO: Use the ViewModel
    }

}
