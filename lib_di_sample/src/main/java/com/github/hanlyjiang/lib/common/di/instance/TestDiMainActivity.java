package com.github.hanlyjiang.lib.common.di.instance;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import com.github.hanlyjiang.lib.common.di.Injectable;
import com.github.hanlyjiang.lib.common.di.R;

import javax.inject.Inject;

public class TestDiMainActivity extends AppCompatActivity implements Injectable {

    @Inject
    TestSingleton testSingleton;
    @Inject
    TestObj testObj;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_di_main2);
        Test.assertInject(this, testSingleton, testObj);
        getSupportFragmentManager().beginTransaction().add(new TestDiFragment(), "test di fragment").commit();

        // startTestService
        startService(new Intent(this, TestDiService.class));

        findViewById(R.id.openBtn).setOnClickListener(v -> {
            startActivity(new Intent(this, TestDiMvpActivity.class));
        });
    }
}