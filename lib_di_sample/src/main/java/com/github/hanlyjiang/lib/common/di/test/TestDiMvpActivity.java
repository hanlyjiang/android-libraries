package com.github.hanlyjiang.lib.common.di.test;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import com.github.hanlyjiang.lib.common.di.BR;
import com.github.hanlyjiang.lib.common.di.R;
import com.github.hanlyjiang.lib.common.di.test.mvp.MvpActivity;

import javax.inject.Inject;

public class TestDiMvpActivity extends MvpActivity<TestDiMvpActivityPresenter, TestDiMvpActivityPresenter.TestDiView>
        implements TestDiMvpActivityPresenter.TestDiView {

    @Inject
    TestSingleton testSingleton;

    @Inject
    TestObj testObj;

    @Inject
    public TestDiMvpActivityPresenter mPresenter;

    @Override
    protected void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        com.github.hanlyjiang.lib.common.di.databinding.ActivityFragmentEmptyInfoBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_fragment_empty_info);
        boolean injectSuccess = Test.assertInject(this, testSingleton, testObj);
        binding.setVariable(BR.info, injectSuccess ? "inject success" : "inject failed!");
        mPresenter.assertInject();
        getSupportFragmentManager().beginTransaction().add(new TestDiMvpFragment(), "di mvp fragment").commit();
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void dismissLoading() {

    }
}
