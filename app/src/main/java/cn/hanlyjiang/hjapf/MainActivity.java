package cn.hanlyjiang.hjapf;

import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.button).setOnClickListener(v -> {
            Intent intent = new Intent();
            intent.setComponent(new ComponentName(v.getContext(), MainActivity.class));
            startActivity(intent);
        });
    }

}