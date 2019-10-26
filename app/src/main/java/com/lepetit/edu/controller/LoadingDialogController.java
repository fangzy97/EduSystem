package com.lepetit.edu.controller;

import com.lepetit.edu.fragment.LoadingDialogFragment;

import androidx.appcompat.app.AppCompatActivity;

public class LoadingDialogController {
    private LoadingDialogFragment dialog = new LoadingDialogFragment();
    private AppCompatActivity activity;

    public LoadingDialogController(AppCompatActivity activity) {
        this.activity = activity;
    }

    public void display() {
        activity.getSupportFragmentManager().beginTransaction().add(dialog, "Loading").commit();
    }

    public void remove() {
        activity.getSupportFragmentManager().beginTransaction().remove(dialog).commitAllowingStateLoss();
    }
}
