package com.github.hanlyjiang.lib.common.di.module.mvp;

import com.github.hanlyjiang.lib.common.di.instance.TestDiMvpActivity;
import com.github.hanlyjiang.lib.common.di.instance.TestDiPresenter;
import com.github.hanlyjiang.lib.common.di.scope.ActivityScope;
import dagger.Module;
import dagger.Provides;

@Module
public class TestDiMvpActivityModule {

    private TestDiMvpActivity activity;

    public TestDiMvpActivityModule(TestDiMvpActivity activity) {
        this.activity = activity;
    }

    @Provides
    @ActivityScope
    TestDiMvpActivity providesTestDiMvpActivity() {
        return activity;
    }

    @Provides
    @ActivityScope
    TestDiPresenter providesTestDiPresenter(TestDiMvpActivity testDiMvpActivity) {
        TestDiPresenter testDiPresenter = new TestDiPresenter(testDiMvpActivity.getApplicationContext(), testDiMvpActivity);
        return testDiPresenter;
    }

    @Provides
    @ActivityScope
    TestDiPresenter.TestDiView providerTestDiView(TestDiMvpActivity testDiMvpActivity) {
        return testDiMvpActivity;
    }

}
