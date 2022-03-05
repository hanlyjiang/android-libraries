package com.github.hanlyjiang.lib.common.di.instance;

import android.os.Bundle;
import androidx.annotation.Nullable;
import com.github.hanlyjiang.lib.common.di.mvp.MvpActivity;

import javax.inject.Inject;

public class TestDiMvpActivity extends MvpActivity<TestDiPresenter, TestDiPresenter.TestDiView>
        implements TestDiPresenter.TestDiView {

    @Inject
    TestSingleton testSingleton;

    @Inject
    TestObj testObj;

    @Inject
    public TestDiPresenter mPresenter;

    @Override
    protected void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Test.assertInject(this, testSingleton, testObj);
        mPresenter.assertInject();
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void dismissLoading() {

    }
}
