package com.github.hanlyjiang.lib.common.di.test;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import com.github.hanlyjiang.lib.common.di.R;
import com.github.hanlyjiang.lib.common.di.framework.Injectable;

import javax.inject.Inject;

public class TestDiMainActivity extends AppCompatActivity implements Injectable {

    @Inject
    TestSingleton testSingleton;
    @Inject
    TestObj testObj;
    private TestDiBroadcastReceiver receiver;

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

    @Override
    protected void onStart() {
        super.onStart();
        IntentFilter filter = new IntentFilter();
        filter.addAction(TestDiBroadcastReceiver.ACTION);
        receiver = new TestDiBroadcastReceiver();
        registerReceiver(receiver, filter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Intent intent = new Intent();
        intent.setAction(TestDiBroadcastReceiver.ACTION);
        sendBroadcast(intent);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (receiver != null) {
            unregisterReceiver(receiver);
        }
    }
}