package com.lepetit.edu.activity;

import android.widget.Toast;

import com.lepetit.edu.application.MyApplication;
import com.lepetit.edu.fragment.LoadingDialogFragment;

import androidx.appcompat.app.AppCompatActivity;

public abstract class BaseActivity extends AppCompatActivity {
    private LoadingDialogFragment dialogFragment = new LoadingDialogFragment();

    /*
    * 用于显示Toast提示框
    */
    public void displayToast(String text) {
        Toast.makeText(getApplicationContext(), text, Toast.LENGTH_SHORT).show();
    }

    /*
    * 用于显示等待提示框
    */
    public void displayDialog() {
        getSupportFragmentManager().beginTransaction().add(dialogFragment, "Loading").commit();
    }

    /*
    * 用于移除等待提示框
    */
    public void removeDialog() {
        getSupportFragmentManager().beginTransaction().remove(dialogFragment).commit();
    }
}
