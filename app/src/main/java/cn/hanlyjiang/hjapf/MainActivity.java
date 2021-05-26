package cn.hanlyjiang.hjapf;

import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.github.hanlyjiang.lib.common.utils.ToastUtils;

import java.util.List;

import cn.hanlyjiang.hjapf.databinding.ActivityMainBinding;


public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding vBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        vBinding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(vBinding.getRoot());
        vBinding.startActivity.setOnClickListener(v -> {
            Intent intent = new Intent();
            intent.setComponent(new ComponentName(v.getContext(), MainActivity.class));
            startActivity(intent);
        });
        vBinding.getAppTasks.setOnClickListener(v -> {
            ActivityManager activityManager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
            List<ActivityManager.AppTask> appTasks = activityManager.getAppTasks();
            ToastUtils.showToast(getApplicationContext(), "AppTask size=" + appTasks.size());
        });
    }

}