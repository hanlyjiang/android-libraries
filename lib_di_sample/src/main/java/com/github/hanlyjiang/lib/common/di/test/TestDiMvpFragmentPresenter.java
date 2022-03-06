package com.github.hanlyjiang.lib.common.di.test;

import android.content.Context;
import android.util.Log;
import com.github.hanlyjiang.lib.common.di.framework.ActivityContext;
import com.github.hanlyjiang.lib.common.di.test.mvp.BasePresenter;
import com.github.hanlyjiang.lib.common.di.test.mvp.BaseView;

import javax.inject.Inject;

/**
 * TestDiMvpFragmentPresenter
 * @author hanlyjiang on 2022/3/5-11:26 PM
 * @version 1.0
 */
public class TestDiMvpFragmentPresenter extends BasePresenter<TestDiMvpFragmentPresenter.View> {

    @Inject
    TestSingleton testSingleton;
    @Inject
    TestObj testObj;

    @Inject
    public TestDiMvpFragmentPresenter(@ActivityContext Context context, View view) {
        super(context, view);
    }

    public void assertInject(){
        Test.assertInject(this,testSingleton,testObj);
        Log.d(getClass().getSimpleName(),"context = " + getContext()+"");
    }

    public interface View extends BaseView {

    }
}
