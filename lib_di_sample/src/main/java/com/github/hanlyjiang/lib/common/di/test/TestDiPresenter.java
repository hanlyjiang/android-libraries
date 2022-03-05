package com.github.hanlyjiang.lib.common.di.test;

import android.content.Context;
import com.github.hanlyjiang.lib.common.di.test.mvp.BasePresenter;
import com.github.hanlyjiang.lib.common.di.test.mvp.BaseView;

import javax.inject.Inject;

public class TestDiPresenter extends BasePresenter<TestDiPresenter.TestDiView> {

    @Inject
    TestSingleton testSingleton;

    @Inject
    TestObj testObj;

    public void assertInject() {
        Test.assertInject(this, testSingleton, testObj);
    }

    @Inject
    public TestDiPresenter(Context context, TestDiView view) {
        super(context, view);
    }

    public interface TestDiView extends BaseView {
    }
}
