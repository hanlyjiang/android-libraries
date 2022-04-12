package com.github.hanlyjiang.lib.common.activity.demo;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

/**
 * 基础的DemoActivity
 */
public class DemoBaseActivity extends AppCompatActivity {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getSupportActionBar() != null) {
            if (titleText() != null) {
                getSupportActionBar().setTitle(titleText());
            }
            if (showTitleBack()) {
                getSupportActionBar().setHomeAsUpIndicator(android.R.drawable.ic_menu_revert);
            }
        }
    }

    public String titleText() {
        return this.getClass().getSimpleName();
    }

    public boolean showTitleBack() {
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
