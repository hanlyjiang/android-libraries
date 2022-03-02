package com.github.hanlyjiang.lib.common.di.instance;

import android.os.Bundle;
import android.util.Log;
import androidx.annotation.Nullable;
import com.github.hanlyjiang.lib.common.di.SdkInjector;
import com.github.hanlyjiang.lib.common.di.module.mvp.MvpActivitySubComponent;
import com.github.hanlyjiang.lib.common.di.module.mvp.TestDiMvpActivityModule;
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

    public void assertInject() {
        Log.d(getClass().getSimpleName(), "testSingleton == null: " + (testSingleton == null));
        Log.d(getClass().getSimpleName(), "testObj == null: " + (testObj == null));
    }

    @Override
    protected void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        MvpActivitySubComponent mvpActivitySubComponent = SdkInjector.getSdkComponent()
                .mvpMvpActivitySubComponentBuilder()
                .testDiMvpActivityModule(new TestDiMvpActivityModule(this)).build();
        mvpActivitySubComponent.inject(this);
        mvpActivitySubComponent.inject(mPresenter);
        mPresenter.assertInject();
        super.onCreate(savedInstanceState);
        assertInject();
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void dismissLoading() {

    }
}
