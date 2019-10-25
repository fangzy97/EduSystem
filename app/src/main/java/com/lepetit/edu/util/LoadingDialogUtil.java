package com.lepetit.edu.util;

import com.lepetit.edu.dialog.LoadingDialog;

import androidx.appcompat.app.AppCompatActivity;

public class LoadingDialogUtil {
    private LoadingDialog dialog = new LoadingDialog();
    private AppCompatActivity activity;

    public LoadingDialogUtil(AppCompatActivity activity) {
        this.activity = activity;
    }

    public void display() {
        activity.getSupportFragmentManager().beginTransaction().add(dialog, "Loading").commit();
    }

    public void remove() {
        activity.getSupportFragmentManager().beginTransaction().remove(dialog).commitAllowingStateLoss();
    }
}
